package GUI;

import Controller.SongController;
import Resources.TableInfo;
import javafx.scene.control.Alert;

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
        try {
            controller = new SongController();
        } catch (Exception e) {
            Alerter.showAlert(e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    public void OpenConnection() throws SQLException {
        controller.openModelConnection();
    }

    public void CloseConnection() throws SQLException {
        controller.closeModelConnection();
    }

    public TableInfo query(Map<String, ArrayList<String>> map, String wanted) {
        TableInfo info = null;
        try {
            switch(wanted) {
                case "song":
                    info = this.controller.getInfoFromGUI(map);
                    break;
                case "artist":
                    // TODO
                    break;
                case "album":
                    // TODO
                    break;
            }
        } catch (Exception e) {
            Alerter.showAlert(e.getMessage(), Alert.AlertType.ERROR);
            return null;
        }
        return info;
    }
}
