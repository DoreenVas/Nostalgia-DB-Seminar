import Resources.infoGiver;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import DBConnection.Model;

public class Controller {
    // an array from model: [fields, "count", info1, info2, ...]
    private ArrayList<String> modelInfo;

    // an object which holds the information needed for the gui
    private infoGiver infGiv;

    //dictionary from the gui
    Map<String, String> infoFromGUI;

    private Model model;

    public Controller(infoGiver info){
        this.infGiv = info;
        this.infoFromGUI = new HashMap<String, String>();
        this.modelInfo = new ArrayList<>();
    }
}


//map.put("dog", "type of animal");