package GUI;
import Controller.Controller;

import java.util.ArrayList;
import java.util.Map;

public class Connection {

    private static Connection connection = null;
    private Controller controller;

    private Connection() {
        controller = new Controller();
    }

    public static Connection connection() {
        if(connection == null) {
            connection = new Connection();
        }
        return connection;
    }

    public void query(Map<String, ArrayList<String>> map){
        this.controller.getInfoFromGUI(map);
    }

}
