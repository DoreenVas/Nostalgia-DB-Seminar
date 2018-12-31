package GUI;

import Controller.Controller;
import Resources.TableInfo;

import java.util.ArrayList;
import java.util.Map;

public class Connection {
    private static Connection connection = new Connection();
    private Controller controller;

    public static Connection getInstance() {
        return connection;
    }

    private Connection() {
        controller = new Controller();
    }

    public TableInfo query(Map<String, ArrayList<String>> map){
        // for debugging
        TableInfo info = new TableInfo();
        String[] fields = {"song_id", "name", "dancibility", "duration", "tempo", "hotness",
                "loudness", "year"};
        for(String field: fields) {
            info.addField(field);
        }
        for(int i = 0; i < 100; i++) {
            ArrayList<String> row = new ArrayList<>();
            for(String field: fields) {
                row.add(field);
            }
            info.addValue(row);
        }
        return info;
        //return this.controller.getInfoFromGUI(map);
    }
}
