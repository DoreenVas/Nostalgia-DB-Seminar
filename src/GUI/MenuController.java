package GUI;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class MenuController {
    @FXML
    private Button about;
    @FXML
    private Button start;
    @FXML
    private Button exit;

    @FXML
    protected void about() {
        try {
            Stage stage = (Stage) about.getScene().getWindow();
            Pane root = (Pane) FXMLLoader.load(getClass().getResource("About.fxml"));
            Scene scene = new Scene(root,600,400);
            scene.getStylesheets().add(getClass().getResource("AboutCss.css").toExternalForm());
            stage.setTitle("About");
            stage.setScene(scene);
            stage.show();
            Centralizer.setCenter(stage);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    protected void start() {
        try {
            Stage stage = (Stage) start.getScene().getWindow();
            AnchorPane root = (AnchorPane) FXMLLoader.load(getClass().getResource("Search.fxml"));
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
    protected void exit() {
        Stage stage = (Stage) exit.getScene().getWindow();
        stage.close();
    }
}
