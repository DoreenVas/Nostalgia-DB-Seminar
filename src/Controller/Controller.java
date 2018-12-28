package Controller;

import java.util.ArrayList;
import java.util.Map;
import DBConnection.Model;
import Resources.*;

public class Controller {
    // an array from model: [fields, "count", info1, info2, ...]
    private ArrayList<String> modelInfo;

    // an object which holds the information needed for the gui
    private TableInfo infGiv;

    //dictionary from the gui
    //Map<String, ArrayList<String>> infoFromGUI;

    private Model model;

    public Controller(){
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
     * first line is the field, the second to end is: "count, value", "count, value"...
     * @param infoFromGUI
     */
    public void getInfoFromGUI(Map<String, ArrayList<String>> infoFromGUI){
        boolean yearOrAge = false;
        int year = 0;
        int age = 0;
        String[] str;

        for (Map.Entry<String, ArrayList<String>> entry : infoFromGUI.entrySet())
        {
            System.out.println(entry.getKey() + "/" + entry.getValue());
            switch (entry.getKey()){
                case "genre":
                    int len = entry.getValue().size();
                    GenreContainer gen = new GenreContainer(parseFromArrayToList(len,entry.getValue()));
                    try{
                        model.getSongs(gen);
                    }
                    catch(Exception e){

                    }
                case "era":
                    str = entry.getValue().get(0).split(",");
                    year = Integer.parseInt(str[0]);
                    try{
                        model.getSongs(year,year + 9);
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
                    model.getSongs(year, year + age);
                }
                catch(Exception e){

                }
            }

        }

    }

    private String[] parseFromArrayToList(int size, ArrayList<String> arr){
        String[] str = new String[size];
        for(int i = 0; i <=size; i++){
            str[i] = arr.get(i);
        }
        return str;
    }







}


//map.put("dog", "type of animal");