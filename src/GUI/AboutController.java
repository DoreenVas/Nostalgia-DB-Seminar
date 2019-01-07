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
import javafx.scene.effect.BlendMode;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.*;
import java.net.URL;
import java.util.ResourceBundle;

/***
 * The about Controller - controls the information window.
 */
public class AboutController implements Initializable {
    @FXML
    private Button back;
    @FXML
    private TextArea aboutText;

    public static final int minWidth = 600;
    public static final int minHeight = 410;

    /***
     * Reading the information from the About.txt file
     * @param location
     * @param resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
//        aboutText.setBackground(Background.EMPTY);
//        aboutText.setBlendMode(BlendMode.OVERLAY);
//        aboutText.setBackground(new Background(new BackgroundFill(Paint.valueOf("white"))));
        String text = "", line = "";
        File aboutFile = new File("src/GUI/About.txt");
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
            Alerter.showAlert(AlertMessages.readFromFileError("About"), Alert.AlertType.ERROR);
        }
        aboutText.setText(text);
    }

    /***
     * Returning to the Menu window.
     */
    @FXML
    protected void back() {
        try {
            Stage stage = (Stage) back.getScene().getWindow();
            VBox root = (VBox) FXMLLoader.load(getClass().getResource("Menu.fxml"));
            Scene scene = new Scene(root,MenuController.minWidth, MenuController.minHeight);
            scene.getStylesheets().add(getClass().getResource("MenuCss.css").toExternalForm());
            stage.setTitle("Nostalgia");
            stage.setScene(scene);
            stage.setMinHeight(MenuController.minHeight);
            stage.setMinWidth(MenuController.minWidth);
            stage.setHeight(MenuController.minHeight);
            stage.setWidth(MenuController.minWidth);
            stage.show();
            Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
            stage.setX((primScreenBounds.getWidth() - stage.getWidth()) / 2);
            stage.setY((primScreenBounds.getHeight() - stage.getHeight()) / 2);
        } catch(Exception e) {
            Alerter.showAlert(AlertMessages.pageLoadingFailure(), Alert.AlertType.ERROR);
        }
    }

}
