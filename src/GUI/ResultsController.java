package GUI;

import Resources.DataContainer;
import Resources.TableInfo;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.PickResult;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.util.*;

public class ResultsController {

    @FXML
    private TableView<SongRow> results;
    @FXML
    private Label countLabel;
    Map<String, ArrayList<String>> map;
    private TableInfo data;
    private int doubleClick = 0;

    @FXML
    private void display() {
        TableView.TableViewSelectionModel selectionModel = results.getSelectionModel();
        SongRow row;
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
            values.add(row.getName());
            requestLyrics.put("lyrics", values);
            TableInfo info = connection.query(requestLyrics);
            String words = info.getFieldsValues().get(0).get(0);
            if(words.equals("null")) {
                words = "The lyrics for this song are unavailable.\nSorry for the inconvenience";
            }

            songInfoController.initialize(row.getName(), row.getDancibility(), row.getDuration(), row.getTempo(),
                    row.getHotness(), row.getLoudness(), row.getYear(), words, data, map);
            stage.setTitle("Song");
            stage.setScene(scene);
            stage.show();
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
            year = Integer.parseInt(data.getFieldsValues().get(0).get(6));
            ArrayList<String> arr = new ArrayList<>();
            year += 10;
            arr.add(String.valueOf(year));
            map.put("from", arr);
            arr = new ArrayList<>();
            arr.add(String.valueOf(year + 9));
            map.put("to", arr);
        }
        TableInfo info = Connection.getInstance().query(map);
        if(info == null) {
            return;
        }
        addData(Connection.getInstance().query(map), map);
    }

    protected void addData(TableInfo info, Map<String, ArrayList<String>> map) {
        countLabel.setText("Displaying " + info.getFieldsValues().size() + " results out of " + info.getRowsNum());
        this.results.getItems().clear();
        this.results.getColumns().clear();
        data = info;
        this.map = map;
        ArrayList<String> fields = info.getFields();

        for (String field : fields) {
            addColumn(field, field);
        }

        for (ArrayList<String> row : info.getFieldsValues()) {
            SongRow item = new SongRow(row);
            this.results.getItems().addAll(item);
        }
        this.results.setOnMouseClicked((event) -> {
            if(event.getClickCount() == 2) {
                display();
            }
        });
    }

//    @FXML
//    protected void addRow() {
//        ArrayList<String> fields = new ArrayList<>();
//        String[] fieldsList = {"name", "dancibility", "duration", "tempo", "hotness",
//                "loudness", "year"};
//
//        for(String item : fieldsList) {
//            fields.add(item);
//        }
//        String[][] data = {{"9996", "The Hanged Man", "0.90607", "386.194", "140.185", "0.15253", "-8.087", "1998", "I am the best.\nI will win all of my battles.\nI am the best person alive, and whoever wishes to oppose me shall perish.\nIlove the color pink."},
//                {"9997", "The Wonderful World Of The Young", "0.75450", "168.019", "77.072", "0.10955", "-14.517", "1998", null},
//                {"9998", "Sentimental Man", "0.83061", "193.724", "118.123", "0.98842", "-12.087", "2017", null},
//                {"9999", "Zydeco In D-Minor", "0.29329", "300.826", "137.663", "0.04028", "-12.574", "1941", null},
//                {"10000", "Shattered Life", "0.70568", "209.737", "150.575", "0.32301", "-5.324", "2005", null},
//                {"9996", "The Hanged Man", "0.90607", "386.194", "140.185", "0.15253", "-8.087", "1998", null},
//                {"9997", "The Wonderful World Of The Young", "0.75450", "168.019", "77.072", "0.10955", "-14.517", "1998", null},
//                {"9998", "Sentimental Man", "0.83061", "193.724", "118.123", "0.98842", "-12.087", "2017", null},
//                {"9999", "Zydeco In D-Minor", "0.29329", "300.826", "137.663", "0.04028", "-12.574", "1941", null},
//                {"10000", "Shattered Life", "0.70568", "209.737", "150.575", "0.32301", "-5.324", "2005", null},
//                {"9996", "The Hanged Man", "0.90607", "386.194", "140.185", "0.15253", "-8.087", "1998", null},
//                {"9997", "The Wonderful World Of The Young", "0.75450", "168.019", "77.072", "0.10955", "-14.517", "1998", null},
//                {"9998", "Sentimental Man", "0.83061", "193.724", "118.123", "0.98842", "-12.087", "2017", null},
//                {"9999", "Zydeco In D-Minor", "0.29329", "300.826", "137.663", "0.04028", "-12.574", "1941", null},
//                {"10000", "Shattered Life", "0.70568", "209.737", "150.575", "0.32301", "-5.324", "2005", null},
//                {"9996", "The Hanged Man", "0.90607", "386.194", "140.185", "0.15253", "-8.087", "1998", null},
//                {"9997", "The Wonderful World Of The Young", "0.75450", "168.019", "77.072", "0.10955", "-14.517", "1998", null},
//                {"9998", "Sentimental Man", "0.83061", "193.724", "118.123", "0.98842", "-12.087", "2017", null},
//                {"9999", "Zydeco In D-Minor", "0.29329", "300.826", "137.663", "0.04028", "-12.574", "1941", null},
//                {"10000", "Shattered Life", "0.70568", "209.737", "150.575", "0.32301", "-5.324", "2005", null},
//                {"9996", "The Hanged Man", "0.90607", "386.194", "140.185", "0.15253", "-8.087", "1998", null},
//                {"9997", "The Wonderful World Of The Young", "0.75450", "168.019", "77.072", "0.10955", "-14.517", "1998", null},
//                {"9998", "Sentimental Man", "0.83061", "193.724", "118.123", "0.98842", "-12.087", "2017", null},
//                {"9999", "Zydeco In D-Minor", "0.29329", "300.826", "137.663", "0.04028", "-12.574", "1941", null},
//                {"10000", "Shattered Life", "0.70568", "209.737", "150.575", "0.32301", "-5.324", "2005", null},
//                {"9996", "The Hanged Man", "0.90607", "386.194", "140.185", "0.15253", "-8.087", "1998", null},
//                {"9997", "The Wonderful World Of The Young", "0.75450", "168.019", "77.072", "0.10955", "-14.517", "1998", null},
//                {"9998", "Sentimental Man", "0.83061", "193.724", "118.123", "0.98842", "-12.087", "2017", null},
//                {"9999", "Zydeco In D-Minor", "0.29329", "300.826", "137.663", "0.04028", "-12.574", "1941", null},
//                {"10000", "Shattered Life", "0.70568", "209.737", "150.575", "0.32301", "-5.324", "2005", null},
//                {"9996", "The Hanged Man", "0.90607", "386.194", "140.185", "0.15253", "-8.087", "1998", null},
//                {"9997", "The Wonderful World Of The Young", "0.75450", "168.019", "77.072", "0.10955", "-14.517", "1998", null},
//                {"9998", "Sentimental Man", "0.83061", "193.724", "118.123", "0.98842", "-12.087", "2017", null},
//                {"9999", "Zydeco In D-Minor", "0.29329", "300.826", "137.663", "0.04028", "-12.574", "1941", null},
//                {"10000", "Shattered Life", "0.70568", "209.737", "150.575", "0.32301", "-5.324", "2005", null},
//                {"9996", "The Hanged Man", "0.90607", "386.194", "140.185", "0.15253", "-8.087", "1998", null},
//                {"9997", "The Wonderful World Of The Young", "0.75450", "168.019", "77.072", "0.10955", "-14.517", "1998", null},
//                {"9998", "Sentimental Man", "0.83061", "193.724", "118.123", "0.98842", "-12.087", "2017", null},
//                {"9999", "Zydeco In D-Minor", "0.29329", "300.826", "137.663", "0.04028", "-12.574", "1941", null},
//                {"10000", "Shattered Life", "0.70568", "209.737", "150.575", "0.32301", "-5.324", "2005", null}};
//        //this.data = data;
//
//        for (String field : fields) {
//            addColumn(field, field);
//        }
//
//        for (String[] row : data) {
//            SongRow item = new SongRow(row);
//            this.results.getItems().addAll(item);
//        }
//        this.name.setText("");
//        this.surname.setText("");
//    }

    @FXML
    protected void addColumn(String field, String displayName) {
//        TableColumn<Person, String> nameColumn = new TableColumn<>("Name");
//        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
//        TableColumn<Person, String> surnameColumn = new TableColumn<>("Surname");
//        surnameColumn.setCellValueFactory(new PropertyValueFactory<>("surname"));
//        this.results.getColumns().add(nameColumn);
//        this.results.getColumns().add(surnameColumn);

        TableColumn<SongRow, String> column = new TableColumn<>(field);
        column.setCellValueFactory(new PropertyValueFactory<>(field));
        column.setText(displayName);
        this.results.getColumns().add(column);

//        TableColumn<Table, String> column = new TableColumn<>(this.column.getText());
//        column.setCellValueFactory(new PropertyValueFactory<>(this.column.getText()));
//        this.results.getColumns().add(column);
//        this.column.setText("");
    }

    public class SongRow {

        private String song_id;
        private String name;
        private String dancibility;
        private String duration;
        private String tempo;
        private String hotness;
        private String loudness;
        private String year;
        private String words = null;

//        public SongRow(String song_id, String name, String dancibility, String duration, String tempo,
//                       String hotness, String loudness, String year, String words) {
//            this.song_id = song_id;
//            this.name = name;
//            this.dancibility = dancibility;
//            this.duration = duration;
//            this.tempo = tempo;
//            this.hotness = hotness;
//            this.loudness = loudness;
//            this.year = year;
//            this.words = words;
//        }

        public SongRow(ArrayList<String> data) {
            //this.song_id = data.get(0);
            this.name = data.get(0);
            this.dancibility = data.get(1);
            this.duration = String.valueOf(Float.parseFloat(data.get(2)) / 60);
            this.tempo = data.get(3);
            this.hotness = data.get(4);
            this.loudness = data.get(5);
            this.year = data.get(6);
//            this.words = data.get(8);
        }

        public SongRow(String[] data) {
            //this.song_id = data[0];
            this.name = data[0];
            this.dancibility = data[1];
            this.duration = String.valueOf(Float.parseFloat(data[2]) / 60);
            this.tempo = data[3];
            this.hotness = data[4];
            this.loudness = data[5];
            this.year = data[6];
//            this.words = data[8];
        }

        public String getName() {
            return name;
        }

        public String getDancibility() {
            return dancibility;
        }

        public String getDuration() {
            return duration;
        }

        public String getHotness() {
            return hotness;
        }

        public String getLoudness() {
            return loudness;
        }

        public String getSong_id() {
            return song_id;
        }

        public String getTempo() {
            return tempo;
        }

        public String getWords() {
            if(words == null) {
                return "Sorry, we do not have the lyrics for this song.";
            }
            return words;
        }

        public String getYear() {
            return year;
        }
    }
}
