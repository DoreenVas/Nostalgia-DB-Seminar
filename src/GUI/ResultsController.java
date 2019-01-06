package GUI;

import Resources.DataContainer;
import Resources.TableInfo;
import com.sun.deploy.util.ArrayUtil;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.PickResult;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.util.*;

public class ResultsController {

    @FXML
    private TableView<SongDisplayData> results;
    @FXML
    private Label countLabel;
    @FXML
    private Button goBackButton;
    @FXML
    private Button goForwardButton;
    Map<String, ArrayList<String>> map;
    private TableInfo data = null;
    private int groupIndex = 0;

    private static String[] displayColumns = {"name", "artist", "album", "year"};

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
            Scene scene = new Scene(root,450,500);
            SongInfoController songInfoController = loader.getController();

            Connection connection = Connection.getInstance();
            Map<String, ArrayList<String>> requestLyrics = new HashMap<>();
            ArrayList<String> values = new ArrayList<>();
            values.add(row.getId());
            requestLyrics.put("lyrics", values);
            TableInfo info = connection.query(requestLyrics, "song");
            String words = info.getFieldsValues().get(0).get(0).get(0);
            if(words.equals("null") || words.equals("null\n") || words.equals("\nnull")) {
                words = "The lyrics for this song are unavailable.\nSorry for the inconvenience.";
            }

            songInfoController.initialize(row, data, map);
            stage.setTitle("Song");
            stage.setScene(scene);
            stage.show();
            Centralizer.setCenter(stage);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    protected void home() {
        try {
            Stage stage = (Stage) results.getScene().getWindow();
            VBox root = (VBox) FXMLLoader.load(getClass().getResource("Menu.fxml"));
            Scene scene = new Scene(root,450,500);
            scene.getStylesheets().add(getClass().getResource("MenuCss.css").toExternalForm());
            stage.setTitle("Nostalgia");
            stage.setScene(scene);
            stage.show();
            Centralizer.setCenter(stage);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    protected void simpleSearch() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("Search.fxml"));
            AnchorPane root = (AnchorPane) loader.load();
            Stage stage = (Stage) results.getScene().getWindow();
            Scene scene = new Scene(root,650,450);
            scene.getStylesheets().add(getClass().getResource("SearchCss.css").toExternalForm());
            stage.setTitle("Search");
            stage.setScene(scene);
            stage.show();
            Centralizer.setCenter(stage);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    protected void backToTheFuture() {
        int year;
        if(map.containsKey("era")) {
            String[] era = map.get("era").get(0).replace("'", " ").split(" ");
            int num = Integer.parseInt(era[0]) + 10;
            ArrayList<String> arr = new ArrayList<>();
            arr.add(String.valueOf(num) + "'" + era[1]);
            map.replace("era", arr);
        } else if(map.containsKey("birthYear")) {
            String age = map.get("age").get(0);
            ArrayList<String> arr = new ArrayList<>();
            arr.add(String.valueOf(Integer.parseInt(age) + 10));
            map.replace("age", arr);
        } else {
            year = Integer.parseInt(data.getFieldsValues().get(0).get(0).get(6));
            ArrayList<String> arr = new ArrayList<>();
            year += 10;
            arr.add(String.valueOf(year));
            map.put("from", arr);
            arr = new ArrayList<>();
            arr.add(String.valueOf(year + 9));
            map.put("to", arr);
        }
        TableInfo info = Connection.getInstance().query(map, "song");
        if(info == null) {
            return;
        }
        addData(Connection.getInstance().query(map, "song"), map);
    }

    void addData(TableInfo info, Map<String, ArrayList<String>> map) {
        this.results.getItems().clear();
        this.results.getColumns().clear();
        if(data == null) {
            goBackButton.setDisable(true);
        }
        data = info;
        this.map = map;
        ArrayList<String> fields = info.getFields();

//        for (int i = 1; i < fields.size(); i++) {
//            String field = fields.get(i);
//            addColumn(field, field);
//        }

        for (String field : displayColumns) {
            addColumn(field, field);
        }

        ArrayList<ArrayList<String>> group = info.getFieldsValues().get(groupIndex);
        countLabel.setText("Displaying " + group.size() + " results out of " + info.getRowsNum());

        for (ArrayList<String> row : group) {
            SongDisplayData item = new SongDisplayData(row, getArtist(row.get(0)), getAlbum(row.get(0)));
            this.results.getItems().addAll(item);
        }
        this.results.setOnMouseClicked((event) -> {
            if(event.getClickCount() == 2) {
                display();
            }
        });
    }

    private String getArtist(String songId) {
        Connection connection = Connection.getInstance();
        Map<String, ArrayList<String>> map = new HashMap<>();
        ArrayList<String> values = new ArrayList<>();
        values.add(songId);
        map.put("song_id", values);
        TableInfo artistInfo = connection.query(map, "artist");
        if(artistInfo == null) {
            return "Artist not in DataBase";
        }
        return artistInfo.getFieldsValues().get(0).get(0).get(0);
    }

    private String getAlbum(String songId) {
        Connection connection = Connection.getInstance();
        Map<String, ArrayList<String>> map = new HashMap<>();
        ArrayList<String> values = new ArrayList<>();
        values.add(songId);
        map.put("song_id", values);
        TableInfo albumInfo = connection.query(map, "album");
        if(albumInfo == null) {
            return "Album not in DataBase";
        }
        return albumInfo.getFieldsValues().get(0).get(0).get(0);
    }

    @FXML
    protected void goForward() {
        int size = data.getFieldsValues().size();
        if(groupIndex < size) {
            groupIndex++;
            goBackButton.setDisable(false);
            addData(data, map);
        }
        if(groupIndex == size - 1) {
            goForwardButton.setDisable(true);
        }
    }

    @FXML
    protected void goBack() {
        if(groupIndex > 0) {
            --groupIndex;
            goForwardButton.setDisable(false);
            addData(data, map);
        }
        if(groupIndex == 0) {
            goBackButton.setDisable(true);
        }
    }

    @FXML
    protected void addColumn(String field, String displayName) {
        TableColumn<SongDisplayData, String> column = new TableColumn<>(field);
        column.setCellValueFactory(new PropertyValueFactory<>(field));
        column.setText(displayName);
        this.results.getColumns().add(column);
    }

//    public class SongRow {
//        private String name;
//        private String duration;
//        private String year;
//        private String artist;
//        private String album;
//
//        SongRow(ArrayList<String> data) {
//            this.name = data.get(0);
//            this.duration = String.valueOf(Float.parseFloat(data.get(2)) / 60);
//            this.year = data.get(6);
//        }
//
//        public SongRow(String[] data) {
//            this.name = data[0];
//            this.duration = String.valueOf(Float.parseFloat(data[2]) / 60);
//            this.year = data[6];
//        }
//
//        public String getName() {
//            return name;
//        }
//
//        public String getDuration() {
//            return duration;
//        }
//
//        public String getYear() {
//            return year;
//        }
//
//        public String getAlbum() {
//            return album;
//        }
//
//        public String getArtist() {
//            return artist;
//        }
//    }

//    public class SongRow { //public class SongData {
//        private String id;
//        private String name;
//        private String dancibility;
//        private String duration;
//        private String tempo;
//        private String hotness;
//        private String loudness;
//        private String year;
//        private String words = null;
//        private String artist;
//        private String album;
//
//        public SongRow(ArrayList<String> data, String artist, String album) {
//            this.id = data.get(0);
//            this.name = data.get(1);
//            this.dancibility = data.get(2);
//            this.duration = String.valueOf(Float.parseFloat(data.get(3)) / 60);
//            this.tempo = data.get(4);
//            this.hotness = data.get(5);
//            this.loudness = data.get(6);
//            this.year = data.get(7);
//            this.artist = artist;
//            this.album = album;
//        }
//
//        public SongRow(String[] data, String artist, String album) {
//            this.id = data[0];
//            this.name = data[1];
//            this.dancibility = data[2];
//            this.duration = String.valueOf(Float.parseFloat(data[3]) / 60);
//            this.tempo = data[4];
//            this.hotness = data[5];
//            this.loudness = data[6];
//            this.year = data[7];
//            this.artist = artist;
//            this.album = album;
//        }
//
//        public String getName() {
//            return name;
//        }
//
//        public String getDancibility() {
//            return dancibility;
//        }
//
//        public String getDuration() {
//            return duration;
//        }
//
//        public String getHotness() {
//            return hotness;
//        }
//
//        public String getLoudness() {
//            return loudness;
//        }
//
//        public String getTempo() {
//            return tempo;
//        }
//
//        public String getWords() {
//            if(words == null) {
//                return "Sorry, we do not have the lyrics for this song.";
//            }
//            return words;
//        }
//
//        public String getYear() {
//            return year;
//        }
//
//        public String getId() {
//            return id;
//        }
//
//        public String getArtist() {
//            return artist;
//        }
//
//        public String getAlbum() {
//            return album;
//        }
//    }
}
