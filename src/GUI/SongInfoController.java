package GUI;

import Resources.TableInfo;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.util.ArrayList;

public class SongInfoController {

    @FXML
    private Label name;
    @FXML
    private Label duration;
    @FXML
    private Label hotness;
    @FXML
    private Label year;
    @FXML
    private TextArea words;
    @FXML
    private Button ret;
    private String tempo;
    private String loudness;
    private String dancibility;
    private TableInfo data;

    public void initialize(String name, String dancibility, String duration, String tempo,
                           String hotness, String loudness, String year, String words, TableInfo data) {
        this.name.setText(name);
        this.name.setAlignment(Pos.CENTER);
//        this.dancibility = dancibility;
        this.duration.setText(duration);
//        this.tempo = tempo;
        this.hotness.setText(hotness);
//        this.loudness = loudness;
        this.year.setText(year);
        this.words.setText(words);
        this.data = data;
        ret.setAlignment(Pos.CENTER);
    }

    @FXML
    private void goBack() {
        try {
            Stage stage = (Stage)ret.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("Results.fxml"));
            AnchorPane root = (AnchorPane) loader.load();
            Scene scene = new Scene(root,450,500);
            ResultsController resultsController = loader.getController();
            resultsController.addData(data);

            stage.setTitle("Results");
            stage.setScene(scene);
            stage.show();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

//    public void initialize(String[] data) {
//        this.name.setText(data[0]);
////        this.dancibility = data[1];
//        this.duration.setText(data[2]);
////        this.tempo = data[3];
//        this.hotness.setText(data[4]);
////        this.loudness = data[5];
//        this.year.setText(data[6]);
//        this.words.setText(data[7]);
//    }
}
