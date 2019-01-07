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
import java.util.concurrent.TimeUnit;

/**
 * In charge of holding the connections to the controller.
 */
public class Connection {
    private static Connection connection;
    private ControllerInterface songController;
    private ControllerInterface artistController;
    private ControllerInterface albumController;

    /**
     *
     * Returns the classes instance.
     * @return the calsses current instance
     * @throws IOException thrown from inner function
     * @throws SQLException thrown from inner function
     */
    public static Connection getInstance() throws IOException, SQLException {
        if(connection == null) {
            connection = new Connection();
        }
        return connection;
    }

    /**
     *
     * Constructor.
     * @throws IOException thrown from inner function
     * @throws SQLException thrown from inner function
     */
    private Connection() throws IOException, SQLException {
        songController = new SongController();
        artistController = new ArtistController();
        albumController = new AlbumController();
    }

    /**
     *
     * Opens a connection to the controllers.
     * @throws SQLException thrown from inner function
     */
    public void OpenConnection() throws SQLException {
        songController.openModelConnection();
    }

    /**
     *
     * Disconnects from the controllers.
     * @throws SQLException thrown from inner function
     */
    public void CloseConnection() throws SQLException {
        songController.closeModelConnection();
    }

    /**
     *
     * Send given information to the controllers.
     * @param map the information for executing a query
     * @param wanted the wanted table string
     * @return the result of the query on given info
     */
    public TableInfo query(Map<String, ArrayList<String>> map, String wanted) {
        TableInfo info = null;
        try {
            // check for wanted table
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
