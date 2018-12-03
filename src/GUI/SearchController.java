package GUI;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
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

    /*private SearchController(){
        if(rb1.isSelected()){
            rb1Clicked();
        }
        if(rb2.isSelected()){
            rb2Clicked();
        }
    }*/

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
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    protected void rb1Clicked() {
        rb2.disableProperty();
        birthYear.disableProperty();
        age.disableProperty();
    }

    @FXML
    protected void rb2Clicked() {
        rb1.disableProperty();
    }
}
