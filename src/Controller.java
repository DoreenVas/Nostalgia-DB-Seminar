import Resources.infoGiver;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import DBConnection.Model;
import com.mysql.cj.conf.PropertyDefinitions;

public class Controller {
    // an array from model: [fields, "count", info1, info2, ...]
    private ArrayList<String> modelInfo;

    // an object which holds the information needed for the gui
    private infoGiver infGiv;

    //dictionary from the gui
    Map<String, ArrayList<String>> infoFromGUI;

    private Model model;

    public Controller(Map<String, ArrayList<String>> map){
        this.infGiv = new infoGiver();
        this.infoFromGUI = map;
        this.modelInfo = new ArrayList<>();
        try{
            this.model = new Model();
        }
        catch (Exception e){
            System.out.println("error");
        }

    }








}


//map.put("dog", "type of animal");