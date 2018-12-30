package GUI;

import Controller.Controller;

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

    public void query(Map<String, ArrayList<String>> map){
        this.controller.getInfoFromGUI(map);
    }
}