package GUI;

import Controller.SongController;
import Resources.TableInfo;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;

public class Connection {
    private static Connection connection = new Connection();
    private SongController controller;

    public static Connection getInstance() {
        return connection;
    }

    private Connection() {
        controller = new SongController();
    }

    public void OpenConnection() throws SQLException {
        //throw new SQLException("Couldn't open connection to db.\nTry again?");
        controller.openModelConnection();
    }

    public void CloseConnection() throws SQLException {
        //throw new SQLException("Couldn't close connection to db");
        controller.closeModelConnection();
    }

    public TableInfo query(Map<String, ArrayList<String>> map){
        // for debugging
//        String[] fields = {"song_id", "name", "dancibility", "duration", "tempo", "hotness",
//                "loudness", "year"};
//        ArrayList<String> fieldsList = new ArrayList<>();
//        ArrayList<ArrayList<String>> data = new ArrayList<>();
//        for(String field : fields) {
//            fieldsList.add(field);
//        }
//        for(int i = 0; i < 100; i++) {
//            ArrayList<String> row = new ArrayList<>();
//            for(String field: fields) {
//                row.add(field);
//            }
//            data.add(row);
//        }
//        TableInfo info = new TableInfo(fields.length, 100, fieldsList, data);
//        return info;
        return this.controller.getInfoFromGUI(map);
    }
}
