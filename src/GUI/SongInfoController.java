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
import java.util.Map;

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
    private Label tempo;
    @FXML
    private Label artist;
    @FXML
    private Label album;
    @FXML
    private TextArea words;
    @FXML
    private Button ret;
    Map<String, ArrayList<String>> map;
    private TableInfo data;
    private int index;

    public void initialize(SongDisplayData displayData, TableInfo data,
                           Map<String, ArrayList<String>> map, int index) {
        this.index = index;
        this.name.setText(displayData.getName());
        this.name.setAlignment(Pos.CENTER);
        this.duration.setText(displayData.getDuration());
        this.hotness.setText(displayData.getHotness());
        this.year.setText(displayData.getYear());
        this.words.setText(displayData.getWords());
        this.tempo.setText(displayData.getTempo());
        this.artist.setText(displayData.getArtist());
        this.album.setText(displayData.getAlbum());
        this.data = data;
        this.map = map;
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
            resultsController.initialize(index);
            resultsController.addData(data, map);

            stage.setTitle("Results");
            stage.setScene(scene);
            stage.show();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}
