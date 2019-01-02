package Controller;

import java.util.ArrayList;
import java.util.Map;
import DBConnection.Model;
import Resources.*;

public class SongController {
    // an array from model: [fields, "count", info1, info2, ...]
    private ArrayList<String> modelInfo;

    // an object which holds the information needed for the gui
    private TableInfo infGiv;

    //dictionary from the gui
    //Map<String, ArrayList<String>> infoFromGUI;

    private Model model;

    public SongController(){
        //this.infoFromGUI = new HashMap<String, ArrayList<String>>();
        this.modelInfo = new ArrayList<>();
        try{
            this.model = new Model();
        }
        catch (Exception e){
            System.out.println("error");
        }
    }


    /**
     * get the values from the model.
     * then, needs to parse the info:
     * first line is the field, the second to end is: "value1", "value2"...
     * @param infoFromGUI
     */
    public TableInfo getInfoFromGUI(Map<String, ArrayList<String>> infoFromGUI){
        boolean yearOrAge = false;
        int year = 0;
        int age = 0;
        String[] str;
        DataContainer dc = null;

        for (Map.Entry<String, ArrayList<String>> entry : infoFromGUI.entrySet())
        {
            System.out.println(entry.getKey() + "/" + entry.getValue());
            switch (entry.getKey()){
                case "genre":
                    int len = entry.getValue().size();
                    GenreContainer gen = new GenreContainer(parseFromArrayToList(len,entry.getValue()));
                    try{
                        dc = model.getSongs(gen);
                    }
                    catch(Exception e){

                    }
                case "era":
                    str = entry.getValue().get(0).split("'");
                    year = Integer.parseInt(str[0]);
                    try{
                        dc = model.getSongs(year,year + 9);
                    }
                    catch(Exception e){

                    }
                case "year":
                    yearOrAge = true;
                    str = entry.getValue().get(0).split(",");
                    year = Integer.parseInt(str[0]);
                case "age":
                    yearOrAge = true;
                    str = entry.getValue().get(0).split(",");
                    age = Integer.parseInt(str[0]);
            }

            if(yearOrAge){
                try{
                    dc = model.getSongs(year, year + age);
                }
                catch(Exception e){

                }
            }

        }
        int row = dc.getCount();
        int col = dc.getColumns().length;
        ArrayList<String> fields = parseToArrayList(dc.getColumns());
        ArrayList<ArrayList<String>> data = parse2DArrayList(dc.getData());
        TableInfo ti = new TableInfo(col,row,fields,data);
        return ti;
    }

    private String[] parseFromArrayToList(int size, ArrayList<String> arr){
        String[] str = new String[size];
        for(int i = 0; i <=size; i++){
            str[i] = arr.get(i);
        }
        return str;
    }

    private ArrayList<String> parseToArrayList(String[] str){
        ArrayList<String> newArrayList = new ArrayList<String>();
        int len = str.length;
        for(int i = 0; i < len; i++){
            newArrayList.add(str[i]);
        }
        return newArrayList;
    }

    private ArrayList<ArrayList<String>> parse2DArrayList(String[] str){
        ArrayList<ArrayList<String>> newArrayList = new ArrayList<ArrayList<String>>();
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

    // method to change letters to lower and then only the first letter to upper

    //method to change time from minutes to seconds







}


//map.put("dog", "type of animal");