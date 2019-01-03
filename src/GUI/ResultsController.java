package GUI;

import Resources.TableInfo;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import javafx.stage.Stage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ResultsController {

    @FXML
    private TableView<SongRow> results;
    @FXML
    private TextArea column;
    @FXML
    private TextArea name;
    @FXML
    private TextArea surname;
    private TableInfo data;

    @FXML
    private void display() {
        SongRow row = results.getSelectionModel().getSelectedItem();
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

            songInfoController.initialize(row.getName(), row.getDancibility(), row.getDuration(), row.getTempo(),
                    row.getHotness(), row.getLoudness(), row.getYear(), words, data);
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
            setCenter(stage);
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
            setCenter(stage);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    private void setCenter(Stage stage) {
        Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
        stage.setX((primScreenBounds.getWidth() - stage.getWidth()) / 2);
        stage.setY((primScreenBounds.getHeight() - stage.getHeight()) / 2);
    }

    protected void addData(TableInfo info) {
        data = info;
        ArrayList<String> fields = info.getFields();

        for (String field : fields) {
            addColumn(field, field);
        }

        for (ArrayList<String> row : info.getFieldsValues()) {
            SongRow item = new SongRow(row);
            this.results.getItems().addAll(item);
        }
        this.name.setText("");
        this.surname.setText("");
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
    protected void clearAll() {
        this.results.getItems().clear();
    }

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
        this.column.setText("");

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
            this.song_id = data.get(0);
            this.name = data.get(1);
            this.dancibility = data.get(2);
            this.duration = data.get(3);
            this.tempo = data.get(4);
            this.hotness = data.get(5);
            this.loudness = data.get(6);
            this.year = data.get(7);
//            this.words = data.get(8);
        }

        public SongRow(String[] data) {
            this.song_id = data[0];
            this.name = data[1];
            this.dancibility = data[2];
            this.duration = data[3];
            this.tempo = data[4];
            this.hotness = data[5];
            this.loudness = data[6];
            this.year = data[7];
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
            return words;
        }

        public String getYear() {
            return year;
        }
    }
}
