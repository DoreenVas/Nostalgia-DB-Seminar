package GUI;

import Resources.AlertMessages;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.*;
import java.net.URL;
import java.util.ResourceBundle;

public class AboutController implements Initializable {
    @FXML
    private Button back;
    @FXML
    private TextArea aboutText;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        String text = "", line = "";
        File aboutFile = new File("About.txt");
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(aboutFile));
            while(line != null) {
                line = bufferedReader.readLine();
                if(line != null) {
                    text = text.concat(line);
                    text = text.concat("\n");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        aboutText.setText(text);
    }

    @FXML
    protected void back() {
        try {
            Stage stage = (Stage) back.getScene().getWindow();
            VBox root = (VBox) FXMLLoader.load(getClass().getResource("Menu.fxml"));
            Scene scene = new Scene(root,450,500);
            scene.getStylesheets().add(getClass().getResource("MenuCss.css").toExternalForm());
            stage.setTitle("Nostalgia");
            stage.setScene(scene);
            stage.show();
            Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
            stage.setX((primScreenBounds.getWidth() - stage.getWidth()) / 2);
            stage.setY((primScreenBounds.getHeight() - stage.getHeight()) / 2);
        } catch(Exception e) {
            Alerter.showAlert(AlertMessages.pageLoadingFailure(), Alert.AlertType.ERROR);
        }
    }

}
