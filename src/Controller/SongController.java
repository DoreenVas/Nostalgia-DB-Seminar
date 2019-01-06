package Controller;

import java.util.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Map;

import Resources.*;

public class SongController extends ControllerAbstract{
    // an array from model: [fields, "count", info1, info2, ...]
    //private ArrayList<String> modelInfo;

    // an object which holds the information needed for the gui
    //private TableInfo infGiv;

    //dictionary from the gui
    //Map<String, ArrayList<String>> infoFromGUI;

    //private Model model;

    public SongController() throws IOException,SQLException {
        //this.infoFromGUI = new HashMap<String, ArrayList<String>>();
        //this.modelInfo = new ArrayList<>();
        //this.model = new DBModel();
            //model.openConnection();
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
        boolean lyricsFlag = false;

        for (Map.Entry<String, ArrayList<String>> entry : infoFromGUI.entrySet())
        {
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
                    duration = minutesToseconds(entry.getValue().get(0));
                    DurationContainer durationContainer = new DurationContainer(duration);
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
                    AlbumIdContainer album = new AlbumIdContainer(temp);
                    queryInfo.setAlbum(album);
                    break;
                case "lyrics":
                    lyricsFlag = true;
                    temp = matchStringToPattern(entry.getValue().get(0));
                    LyricsContainer lyricsContainer = new LyricsContainer(temp);
                    queryInfo.setLyrics(lyricsContainer);
                    break;
            }
        }
        if(yearOrAge){
            year += age;
        }
        queryInfo.setYear(year);
        queryInfo.setFrom(from);
        queryInfo.setTo(to);
        dc = model.getData(queryInfo);

        //create and return the TableInfo
        return createTableInfo(dc,duration,tempo,popularity,lyricsFlag);
    }

    /**
     *
     * @param dc
     * @param duration
     * @param tempo
     * @param popularity
     * @param shouldOrderResults
     * @return a TableInfo object
     */
    private TableInfo createTableInfo(DataContainer dc, float duration, float tempo,
                                      float popularity, boolean shouldOrderResults){
        int row = dc.getCount();
        int col = dc.getColumns().length;
        ArrayList<String> fields = parseToArrayList(dc.getColumns());
        ArrayList<ArrayList<ArrayList<String>>> data;

        //order the rows only if the next conditions are not true
        if(shouldOrderResults){
            data = resultsOfSearch(dc.getData());
        }
        else if((duration == -1 && tempo == -1 && popularity == -1)){
            data = resultsOfSearchUnordered(dc.getData());
        }
        else{
            data = resultsOfSearchOrdered(fields,dc.getData(),duration,tempo,popularity);
        }

        TableInfo ti = new TableInfo(col,row,fields,data);
        return ti;
    }


    /**
     *
     * @param str
     * @return an arraylist of results
     */
    private ArrayList<ArrayList<ArrayList<String>>> resultsOfSearch(String[] str){
        ArrayList<ArrayList<ArrayList<String>>> newArrayList = new ArrayList<>();
        ArrayList<ArrayList<String>> tempArrayList = new ArrayList<>();
        ArrayList<String> newRow = new ArrayList<>();

        newRow.add(str[0]);
        tempArrayList.add(newRow);
        newArrayList.add(tempArrayList);

        return newArrayList;

    }
    /**
     *
     * @param str
     * @return  an unordered arraylist that contains in each cell an arraylists of 50
     * rows. each row is an arraylist of results.
     */
    private ArrayList<ArrayList<ArrayList<String>>> resultsOfSearchUnordered(String[] str){
        ArrayList<ArrayList<ArrayList<String>>> newArrayList = new ArrayList<>();
        ArrayList<ArrayList<String>> tempArrayList = new ArrayList<>();
        ArrayList<String> newRow = null;

        String[] temp = null;
        int countRows = 0;
        for(int i = 0; i < str.length; i++){
            newRow = new ArrayList<String>();
            temp = str[i].split(",");
            for(int j = 0; j < temp.length; j++){
                newRow.add(j,temp[j]);
            }
            if(countRows >= 50){
                countRows = 0;
                newArrayList.add(tempArrayList);
                tempArrayList = new ArrayList<>();
            }
            tempArrayList.add(countRows,newRow);
            countRows++;
        }

        if(tempArrayList.size() > 0){
            newArrayList.add(tempArrayList);
        }

        return newArrayList;
    }


    /**
     *
     * @param duration
     * @return convert time from minutes to seconds
     */
    private float minutesToseconds(String duration){
        String[] time = duration.replace(".", " ").split(" ");
        //time[0] - is the minutes
        //time[1] - the seconds
        float seconds = Float.parseFloat(time[0])*60 + Integer.parseInt(time[1]);;
        return seconds;
    }


    /**
     * update the needed indexes in the Song comparator
     * @param fields
     * @param comparator
     */
    private void updateComparatorIndexes(ArrayList<String> fields, SongComparator comparator){
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
                case "hotness":
                    popularityIndex = i;
                    break;
                default:
                    break;
            }
        }
        comparator.setIndex1(tempoIndex);
        comparator.setIndex2(popularityIndex);
        comparator.setIndex3(durationIndex);
    }


    /**
     * the method uses comparator and priority queue
     * @param fields
     * @param str
     * @param duration
     * @param tempo
     * @param popularity
     * @return an nordered arraylist that contains in each cell an arraylists of 50
     *      * rows. each row is an arraylist of results.
     */
    public ArrayList<ArrayList<ArrayList<String>>> resultsOfSearchOrdered
    (ArrayList<String> fields, String[] str, float duration, float tempo, float popularity){

        SongComparator comparator = new SongComparator();
        updateComparatorIndexes(fields,comparator);
        comparator.setWantedResult1(tempo);
        comparator.setWantedResult2(popularity);
        comparator.setWantedResult3(duration);

        /* order the songs, using a priority queue */
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
