package Controller;

import java.util.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Map;
import DBConnection.DBModel;
import DBConnection.Model;
import Resources.*;

public class SongController {
    // an array from model: [fields, "count", info1, info2, ...]
    //private ArrayList<String> modelInfo;

    // an object which holds the information needed for the gui
    //private TableInfo infGiv;

    //dictionary from the gui
    //Map<String, ArrayList<String>> infoFromGUI;

    private Model model;

    public SongController() throws IOException {
        //this.infoFromGUI = new HashMap<String, ArrayList<String>>();
        //this.modelInfo = new ArrayList<>();
        this.model = new DBModel();
            //model.openConnection();
    }

    public void closeModelConnection() throws SQLException {
        this.model.closeConnection();
    }

    public void openModelConnection() throws SQLException{
        this.model.openConnection();
    }

    /**
     * get the values from the model.
     * then, needs to parse the info:
     * first line is the field, the second to end is: "value1", "value2"...
     * @param infoFromGUI
     */
    public TableInfo getInfoFromGUI(Map<String, ArrayList<String>> infoFromGUI) throws SQLException {
        SongQueryInfo queryInfo = new SongQueryInfo();
        boolean yearOrAge = false;
        int year = -1;
        int age = -1;
        int from = -1;
        int to =-1;
        float tempo = -1;
        String[] str;
        String temp = null;
        DataContainer dc = null;
        float duration = -1;
        float popularity = -1;


        for (Map.Entry<String, ArrayList<String>> entry : infoFromGUI.entrySet())
        {
            System.out.println(entry.getKey() + "/" + entry.getValue());
            switch (entry.getKey()){
                case "genre":
                    int len = entry.getValue().size();
                    GenreContainer gen = new GenreContainer(parseFromArrayToList(len,entry.getValue()));
                    queryInfo.setGenere(gen);
                    break;
                case "era":
                    str = entry.getValue().get(0).split("'");
                    from = Integer.parseInt(str[0]);
                    //int to;
                    if(from < 2000){
                        from += 1900;
                        to = from + 9;
                    }
                    else{
                        to = Calendar.getInstance().get(Calendar.YEAR);
                    }
                    break;
                case "birthYear":
                    yearOrAge = true;
                    str = entry.getValue().get(0).split(",");
                    year = Integer.parseInt(str[0]);
                    break;
                case "age":
                    yearOrAge = true;
                    str = entry.getValue().get(0).split(",");
                    age = Integer.parseInt(str[0]);
                    break;
                case "duration":
                    float time = minutesToseconds(entry.getValue().get(0));
                    DurationContainer durationContainer = new DurationContainer(time);
                    queryInfo.setDuration(durationContainer);
                    break;
                case "tempo":
                    tempo = Float.parseFloat(entry.getValue().get(0));
                    TempoContainer tempoContainer = new TempoContainer(tempo);
                    queryInfo.setTempo(tempoContainer);
                    break;
                case "artist_name":
                    temp = matchStringToPattern(entry.getValue().get(0));
                    ArtistContainer artistContainer = new ArtistContainer(temp);
                    queryInfo.setArtist(artistContainer);
                    break;
                case "popularity":
                    popularity = Float.parseFloat(entry.getValue().get(0));
                    PopularityContainer popularityContainer = new PopularityContainer(popularity);
                    queryInfo.setPopularity(popularityContainer);
                    break;
                case "album_name":
                    temp = matchStringToPattern(entry.getValue().get(0));
                    AlbumContainer album = new AlbumContainer(temp);
                    queryInfo.setAlbum(album);
                    break;
                case "lyrics":
                    temp = matchStringToPattern(entry.getValue().get(0));
                    LyricsContainer lyricsContainer = new LyricsContainer(temp);
                    queryInfo.setLyrics(lyricsContainer);
                    break;
//                case "hotness":
//                    tempo = Float.parseFloat(entry.getValue().get(0));
//                    HotnessContainer hotnessContainer = new HotnessContainer(tempo);
//                    queryInfo.setHotness(hotnessContainer);
//                    break;
            }
        }
        if(yearOrAge){
            year += age;
        }
        queryInfo.setYear(year);
        queryInfo.setFrom(from);
        queryInfo.setTo(to);
        dc = this.model.getData(queryInfo);

        int row = dc.getCount();
        int col = dc.getColumns().length;
        ArrayList<String> fields = parseToArrayList(dc.getColumns());
        //ArrayList<ArrayList<String>> data = parse2DArrayList(dc.getData());
        ArrayList<ArrayList<ArrayList<String>>> data = resultsOfSearch(fields,
                dc.getData(),duration,tempo,popularity);
        TableInfo ti = new TableInfo(col,row,fields,data);
        return ti;
    }


    private String[] parseFromArrayToList(int size, ArrayList<String> arr){
        String[] str = new String[size];
        for(int i = 0; i < size; i++){
            str[i] = arr.get(i);
        }
        return str;
    }

    private ArrayList<String> parseToArrayList(String[] str){
        ArrayList<String> newArrayList = new ArrayList<String>();
        int len = str.length;
        String[] temp = null;
        for(int i = 0; i < len; i++){
            if(str[i].contains(".")) {
                temp = str[i].replace(".", " ").split(" ");
                newArrayList.add(temp[1]);
            } else {
                newArrayList.add(str[i]);
            }
        }
        return newArrayList;
    }

    /*
    private ArrayList<ArrayList<String>> parse2DArrayList(String[] str){
        ArrayList<ArrayList<String>> newArrayList = new ArrayList<>();
        ArrayList<String> newRow = null;

        String[] temp = null;
        for(int i = 0; i < str.length; i++){
            newRow = new ArrayList<String>();
            temp = str[i].split(",");
            for(int j = 0; j < temp.length; j++){
                newRow.add(j,temp[j]);
            }
            newArrayList.add(i,newRow);
        }
        return newArrayList;
    }
*/
    // method to change letters to lower and then only the first letter to upper
    private String matchStringToPattern(String str){
        str = str.toLowerCase();
        str = Character.toUpperCase(str.charAt(0)) + str.substring(1);
        return str;
    }

    // method to change time from minutes to seconds
    private float minutesToseconds(String duration){
        String[] time = duration.replace(".", " ").split(" ");
        //time[0] - is the minutes
        //time[1] - the seconds
        float seconds = Float.parseFloat(time[0])*60 + Integer.parseInt(time[1]);;
        return seconds;
    }


// should return the data in arraylist in which every place is 50 rows of songs
    public ArrayList<ArrayList<ArrayList<String>>> resultsOfSearch
    (ArrayList<String> fields, String[] str, float duration, float tempo, float popularity){

        SongComparator comparator = new SongComparator();

        //get the index of tempo, duration, popularity and update the comparator values accordingly
        int tempoIndex = -1;
        int durationIndex = -1;
        int popularityIndex = -1;

        for(int i=0; i<fields.size(); i++){
            switch(fields.get(i).toLowerCase()){
                case "tempo":
                    tempoIndex = i;
                    break;
                case "duration":
                    durationIndex = i;
                    break;
                case "popularity":
                    popularityIndex = i;
                    break;
                default:
                    break;
            }
        }
        comparator.setIndex1(tempoIndex);
        comparator.setIndex2(popularityIndex);
        comparator.setIndex3(durationIndex);
        comparator.setWantedResult1(tempo);
        comparator.setWantedResult2(popularity);
        comparator.setWantedResult3(duration);

        /*
        order the songs, using a priority queue
         */
        PriorityQueue<ArrayList<String>> pQueue =
                new PriorityQueue<ArrayList<String>>(str.length,comparator);

        ArrayList<ArrayList<ArrayList<String>>> newArrayList = new ArrayList<>();
        ArrayList<ArrayList<String>> tempArrayList = new ArrayList<>();
        ArrayList<String> newRow = null;

        String[] temp = null;
        for(int i = 0; i < str.length; i++){
            newRow = new ArrayList<String>();
            temp = str[i].split(",");
            for(int j = 0; j < temp.length; j++){
                newRow.add(j,temp[j]);
            }
            pQueue.add(newRow);
        }

        /*
        now all all ordered rows to an array list:
        every 50 rows are collected into one index of an arraylist.
         */
        int i = 0;
        while(!pQueue.isEmpty()){
            if(i<50){
                tempArrayList.add(i,pQueue.poll());
                i++;
            }
            else{
                i=0;
                newArrayList.add(tempArrayList);
                tempArrayList = new ArrayList<>();
            }
        }
        if(tempArrayList.size() > 0){
            newArrayList.add(tempArrayList);
        }

        return newArrayList;
    }
}
