package GUI;

import Controller.AlbumController;
import Controller.ArtistController;
import Controller.ControllerInterface;
import Controller.SongController;
import Resources.TableInfo;
import javafx.scene.control.Alert;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;

/**
 *
 */
public class Connection {
    private static Connection connection;
    private ControllerInterface songController;
    private ControllerInterface artistController;
    private ControllerInterface albumController;

    public static Connection getInstance() throws IOException, SQLException {
        if(connection == null) {
            connection = new Connection();
        }
        return connection;
    }

    private Connection() throws IOException, SQLException {
        songController = new SongController();
        artistController = new ArtistController();
        albumController = new AlbumController();
    }

    public void OpenConnection() throws SQLException {
        songController.openModelConnection();
    }

    public void CloseConnection() throws SQLException {
        songController.closeModelConnection();
    }

    public TableInfo query(Map<String, ArrayList<String>> map, String wanted) {
        TableInfo info = null;
        try {
            switch(wanted) {
                case "song":
                    info = this.songController.getInfoFromGUI(map);
                    break;
                case "artist":
                    info = this.artistController.getInfoFromGUI(map);
                    break;
                case "album":
                    info = this.albumController.getInfoFromGUI(map);
                    break;
            }
        } catch (Exception e) {
            Alerter.showAlert(e.getMessage(), Alert.AlertType.ERROR);
            return null;
        }
        return info;
    }
}
