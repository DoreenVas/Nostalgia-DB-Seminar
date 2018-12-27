package GUI;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class SearchController {
    @FXML
    private ChoiceBox<String> era;
    @FXML
    private RadioButton rb1;
    @FXML
    private RadioButton rb2;
    @FXML
    private TextField birthYear;
    @FXML
    private TextField age;
    @FXML
    private Button results;
    @FXML
    private Button advanced;
    @FXML
    private Pane genres;

    private void setCenter(Stage stage) {
        Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
        stage.setX((primScreenBounds.getWidth() - stage.getWidth()) / 2);
        stage.setY((primScreenBounds.getHeight() - stage.getHeight()) / 2);
    }

    @FXML
    protected void home() {
        try {
            Stage stage = (Stage) rb1.getScene().getWindow();
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
    protected void advanced() {
        try {
            Stage stage = (Stage) advanced.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("AdvancedSearch.fxml"));
            AnchorPane root = (AnchorPane) loader.load();
            Scene scene = new Scene(root,650,650);
            AdvancedSearchController advancedSearchController = loader.getController();
            advancedSearchController.init(era.getValue(), genres.getChildren(), birthYear.getText(), age.getText());
            scene.getStylesheets().add(getClass().getResource("SearchCss.css").toExternalForm());
            stage.setTitle("Search");
            stage.setScene(scene);
            setCenter(stage);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    protected void results() {
        try {

            Stage stage = (Stage) results.getScene().getWindow();
            AnchorPane root = (AnchorPane) FXMLLoader.load(getClass().getResource("Results.fxml"));
            Scene scene = new Scene(root,450,500);
            //scene.getStylesheets().add(getClass().getResource("MenuCss.css").toExternalForm());
            stage.setTitle("Nostalgia");
            stage.setScene(scene);
            stage.show();
            setCenter(stage);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    protected void rb1Clicked() {
        // makes radio button able to select only if radio button is clicked
        rb2.setPickOnBounds(false);
        birthYear.setDisable(true);
        age.setDisable(true);
        era.setDisable(false);
    }

    @FXML
    protected void rb2Clicked() {
        // makes radio button able to select only if radio button is clicked
        rb1.setPickOnBounds(false);
        era.setDisable(true);
        birthYear.setDisable(false);
        age.setDisable(false);
    }
}
