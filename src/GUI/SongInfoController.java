package GUI;

import Resources.AlertMessages;
import Resources.TableInfo;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Map;

/**
 * The song information controller.
 */
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
    private Map<String, ArrayList<String>> map;
    private TableInfo data;
    private int index;

    public static final int minWidth = 450;
    public static final int minHeight = 500;

    /**
     * Initializing the data
     * @param displayData
     * @param data
     * @param map
     * @param index
     */
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

    /***
     * Loading the results window when the "back" button is clicked.
     */
    @FXML
    private void goBack() {
        try {
            Stage stage = (Stage)ret.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("Results.fxml"));
            AnchorPane root = (AnchorPane) loader.load();
            Scene scene = new Scene(root,ResultsController.minWidth, ResultsController.minHeight);
            ResultsController resultsController = loader.getController();
            resultsController.initialize(index);
            resultsController.addData(data, map);
            scene.getStylesheets().add(getClass().getResource("ResultCss.css").toExternalForm());
            stage.setTitle("Results");
            stage.setScene(scene);
            stage.setResizable(false);
            stage.show();
            Centralizer.setCenter(stage);
        } catch(Exception e) {
            Alerter.showAlert(AlertMessages.pageLoadingFailure(), Alert.AlertType.ERROR);
        }
    }
}
