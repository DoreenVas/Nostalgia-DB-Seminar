package GUI;

import Resources.AlertMessages;
import Resources.TableInfo;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.util.*;

/***
 * The result Controller - controls the display of information received from the queries.
 */
public class ResultsController {

    @FXML
    private TableView<SongDisplayData> results;
    @FXML
    private Label countLabel;
    @FXML
    private Button goBackButton;
    @FXML
    private Button goForwardButton;

    private Map<String, ArrayList<String>> map;
    private TableInfo data = null;
    // the index of the current batch of results to display
    private int groupIndex = 0;

    public static final int minWidth = 730;
    public static final int minHeight = 500;

    // the columns to display in the results
    private static String[] displayColumns = {"name", "duration", "artist", "album", "year"};

    /**
     * Initializes needed parameters.
     * @param index the starting index for displaying
     */
    void initialize(int index) {
        groupIndex = index;
    }

    /***
     * Loading the song info window to display specific song information, depending on the clicked song.
     */
    @FXML
    private void display() {
        TableView.TableViewSelectionModel selectionModel = results.getSelectionModel();
        SongDisplayData row;
        if(selectionModel.getSelectedItems().size() != 0) {
            row = results.getSelectionModel().getSelectedItem();
        } else {
            Alerter.showAlert("Please select a song to display its information.", Alert.AlertType.INFORMATION);
            return;
        }
        try {
            Stage stage = (Stage)results.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("SongInfo.fxml"));
            AnchorPane root = (AnchorPane) loader.load();
            Scene scene = new Scene(root,SongInfoController.minWidth, SongInfoController.minHeight);
            SongInfoController songInfoController = loader.getController();
            Connection connection = Connection.getInstance();
            Map<String, ArrayList<String>> requestLyrics = new HashMap<>();
            ArrayList<String> values = new ArrayList<>();
            values.add(row.getId());
            requestLyrics.put("lyrics", values);
            TableInfo info = connection.query(requestLyrics, "song");
            String words = info.getFieldsValues().get(0).get(0).get(0);
            if(words.equals("null") || words.equals("null\n") || words.equals("\nnull") || words.equals("\n")) {
                words = "The lyrics for this song are unavailable.\nSorry for the inconvenience.";
            }
            row.setWords(words);

            songInfoController.initialize(row, data, map, groupIndex);
            stage.setTitle("Song");
            stage.setScene(scene);
            stage.setResizable(false);
            stage.show();
            Centralizer.setCenter(stage);
        } catch(Exception e) {
            Alerter.showAlert(AlertMessages.pageLoadingFailure(), Alert.AlertType.ERROR);
        }
    }

    /***
     * Loading the menu window when the "home" symbol or button is clicked.
     */
    @FXML
    protected void home() {
        try {
            Stage stage = (Stage) results.getScene().getWindow();
            VBox root = (VBox) FXMLLoader.load(getClass().getResource("Menu.fxml"));
            Scene scene = new Scene(root,MenuController.minWidth, MenuController.minHeight);
            scene.getStylesheets().add(getClass().getResource("MenuCss.css").toExternalForm());
            stage.setTitle("Nostalgia");
            stage.setScene(scene);
            stage.setResizable(false);
            stage.show();
            // set stage to the center of the screen
            Centralizer.setCenter(stage);
        } catch(Exception e) {
            Alerter.showAlert(AlertMessages.pageLoadingFailure(), Alert.AlertType.ERROR);
        }
    }

    /***
     * Loading the search window when the "arrow" symbol  is clicked.
     */
    @FXML
    protected void simpleSearch() {
        try {
            // load the simple search
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("Search.fxml"));
            AnchorPane root = (AnchorPane) loader.load();
            Stage stage = (Stage) results.getScene().getWindow();
            Scene scene = new Scene(root,SearchController.minWidth, SearchController.minHeight);
            scene.getStylesheets().add(getClass().getResource("SearchCss.css").toExternalForm());
            stage.setTitle("Search");
            stage.setScene(scene);
            stage.setResizable(false);
            stage.show();
            // set stage to the center of the screen
            Centralizer.setCenter(stage);
        } catch(Exception e) {
            Alerter.showAlert(AlertMessages.pageLoadingFailure(), Alert.AlertType.ERROR);
        }
    }

    /***
     * returning same query properties results only for the next decade.
     */
    @FXML
    protected void backToTheFuture() {
        ArrayList<String> arr = new ArrayList<>();
        groupIndex = 0;
        int year;
        // check for maps parameters (manipulates them to get the next 10 years)
        if(map.containsKey("era")) {
            // get the era
            String[] era = map.get("era").get(0).replace("'", " ").split(" ");
            int num = Integer.parseInt(era[0]) + 10;
            arr.add(String.valueOf(num) + "'" + era[1]);
            map.replace("era", arr);
        } else if(map.containsKey("birthYear")) {
            // get the age
            String age = map.get("age").get(0);
            arr.add(String.valueOf(Integer.parseInt(age) + 10));
            map.replace("age", arr);
        } else {
            // get the year
            year = Integer.parseInt(data.getFieldsValues().get(0).get(0).get(6));
            year += 10;
            arr.add(String.valueOf(year));
            map.put("from", arr);
            arr = new ArrayList<>();
            arr.add(String.valueOf(year + 9));
            map.put("to", arr);
        }
        // send new query information to the connection
        TableInfo info = null;
        try {
            info = Connection.getInstance().query(map, "song");
        } catch (Exception e) {
            Alerter.showAlert(e.getMessage(), Alert.AlertType.INFORMATION);
        }
        // if the result was empty
        if(info == null) {
            return;
        }
        // add the returned data to the table
        try {
            addData(Connection.getInstance().query(map, "song"), map);
        } catch (Exception e) {
            Alerter.showAlert(e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    /**
     * Adds data from the given information to the table.
     * @param info the data
     * @param map the queries info to save
     */
    void addData(TableInfo info, Map<String, ArrayList<String>> map) {
        // clear the table
        this.results.getItems().clear();
        this.results.getColumns().clear();
        // if an invalid index was given
        if(groupIndex >= info.getFieldsValues().size() || groupIndex < 0) {
            groupIndex = 0;
        }
        // if the current index is the first
        if(groupIndex == 0) {
            goBackButton.setDisable(true);
        }
        // if the current index is the last
        if(info.getFieldsValues().size() - 1 == groupIndex) {
            goForwardButton.setDisable(true);
        }
        data = info;
        this.map = map;
        //ArrayList<String> fields = info.getFields();

//        for (int i = 1; i < fields.size(); i++) {
//            String field = fields.get(i);
//            addColumn(field, field);
//        }

        // add all of the columns to display to the tables
        for (String field : displayColumns) {
            addColumn(field, field);
        }
        // get the group to display
        ArrayList<ArrayList<String>> group = info.getFieldsValues().get(groupIndex);
        countLabel.setText("Displaying " + group.size() + " results out of " + info.getRowsNum());
        // go over the groups items and add them to the table
        for (ArrayList<String> row : group) {
            SongDisplayData item = new SongDisplayData(row, getArtist(row.get(0)), getAlbum(row.get(0)));
            this.results.getItems().addAll(item);
        }
        // set each results clicked event
        this.results.setOnMouseClicked((event) -> {
            // if the result was double clicked
            if(event.getClickCount() == 2) {
                display();
            }
        });
    }

    /**
     * Requests the given songs artist from the connection to the model and returns its result.
     * @param songId the wanted song
     * @return the given songs artist
     */
    private String getArtist(String songId) {
        // get the connection
        Connection connection = getConnection();
        if(connection == null) {
            return "";
        }
        Map<String, ArrayList<String>> map = new HashMap<>();
        ArrayList<String> values = new ArrayList<>();
        // add the given song to the map
        values.add(songId);
        map.put("song_id", values);
        // ask for the result
        TableInfo artistInfo = null;
        try {
            artistInfo = connection.query(map, "artist");
        } catch (SQLException e) {
            Alerter.showAlert(e.getMessage(), Alert.AlertType.ERROR);
            return "Artist not in DataBase";
        }
        // if there was no result
        if(artistInfo == null) {
            return "Artist not in DataBase";
        }
        return artistInfo.getFieldsValues().get(0).get(0).get(0);
    }

    /**
     * Requests the given songs album from the connection to the model and returns its result.
     * @param songId the wanted song
     * @return the given songs album
     */
    private String getAlbum(String songId) {
        // get the connection
        Connection connection = getConnection();
        if(connection == null) {
            return "";
        }
        Map<String, ArrayList<String>> map = new HashMap<>();
        ArrayList<String> values = new ArrayList<>();
        // add the given song to the map
        values.add(songId);
        map.put("song_id", values);
        // ask for the result
        TableInfo albumInfo = null;
        try {
            albumInfo = connection.query(map, "album");
        } catch (SQLException e) {
            Alerter.showAlert(e.getMessage(), Alert.AlertType.ERROR);
            return "Album not in DataBase";
        }
        // if there was no result
        if(albumInfo == null) {
            return "Album not in DataBase";
        }
        return albumInfo.getFieldsValues().get(0).get(0).get(0);
    }

    /**
     * Gets the connection to the model.
     * @return the connection to the model if could connect, null otherwise
     */
    private Connection getConnection() {
        Connection connection = null;
        try {
            connection = Connection.getInstance();
        } catch (Exception e) {
            Alerter.showAlert(e.getMessage(), Alert.AlertType.ERROR);
        }
        if(connection == null) {
            return null;
        }
        return connection;
    }

    /***
     * next batch of results
     */
    @FXML
    protected void goForward() {
        int size = data.getFieldsValues().size();
        // if the index is smaller than the amount of the groups
        if(groupIndex < size) {
            groupIndex++;
            goBackButton.setDisable(false);
            addData(data, map);
        }
    }

    /***
     * previous batch of results.
     */
    @FXML
    protected void goBack() {
        // if the index is bigger than 0
        if(groupIndex > 0) {
            --groupIndex;
            goForwardButton.setDisable(false);
            addData(data, map);
        }
    }

    /***
     * Adds the given field as a column with the display name.
     * @param field
     * @param displayName
     */
    @FXML
    private void addColumn(String field, String displayName) {
        // create a new table column
        TableColumn<SongDisplayData, String> column = new TableColumn<>(field);
        column.setCellValueFactory(new PropertyValueFactory<>(field));
        column.setText(displayName);
        // set the column to be ib the table
        this.results.getColumns().add(column);
    }
}
