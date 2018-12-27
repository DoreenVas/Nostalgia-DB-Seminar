import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

public class Controller {
    // an array from model: [fields, "count, info1, info2, ...]
    private ArrayList<String> modelInfo;

    // an object which holds the information needed for the gui
    private infoGiver infGiv;

    //dictionary from the gui
    Map<String, String> infoFromGUI;

    private Model


    public Controller(infoGiver info){
        this.infGiv = info;
        this.infoFromGUI = new HashMap<String, String>();
        this.modelInfo = new ArrayList<>();
    }



}


//map.put("dog", "type of animal");