package GUI;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.HBox;
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
            TitledPane root = (TitledPane) FXMLLoader.load(getClass().getResource("About.fxml"));
            Scene scene = new Scene(root,600,400);
            stage.setTitle("About");
            stage.setScene(scene);
            stage.show();

        } catch(Exception e) {
            e.printStackTrace();
        }

    }

    @FXML
    protected void start() {

    }

    @FXML
    protected void exit() {
        Stage stage = (Stage) exit.getScene().getWindow();
        stage.close();
    }
}
