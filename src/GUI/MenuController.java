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

/***
 * The Menu Controller - displays the main window of the application.
 */
public class MenuController {
    @FXML
    private Button about;
    @FXML
    private Button start;
    @FXML
    private Button exit;

    public static final int minWidth = 450;
    public static final int minHeight = 500;

    /***
     * Loading the about window when the "about" button is clicked.
     */
    @FXML
    protected void about() {
        try {
            Stage stage = (Stage) about.getScene().getWindow();
            Pane root = (Pane) FXMLLoader.load(getClass().getResource("About.fxml"));
            Scene scene = new Scene(root, AboutController.minWidth, AboutController.minHeight);
            scene.getStylesheets().add(getClass().getResource("AboutCss.css").toExternalForm());
            stage.setTitle("About");
            stage.setScene(scene);
            stage.setMinHeight(AboutController.minHeight);
            stage.setMinWidth(AboutController.minWidth);
            stage.setHeight(AboutController.minHeight);
            stage.setWidth(AboutController.minWidth);
            stage.show();
            Centralizer.setCenter(stage);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    /***
     * Loading the Search window when the "start" button is clicked.
     */
    @FXML
    protected void start() {
        try {
            Stage stage = (Stage) start.getScene().getWindow();
            AnchorPane root = (AnchorPane) FXMLLoader.load(getClass().getResource("Search.fxml"));
            Scene scene = new Scene(root,SearchController.minWidth, SearchController.minHeight);
            scene.getStylesheets().add(getClass().getResource("SearchCss.css").toExternalForm());
            stage.setTitle("Search");
            stage.setScene(scene);
            stage.setMinHeight(SearchController.minHeight);
            stage.setMinWidth(SearchController.minWidth);
            stage.setHeight(SearchController.minHeight);
            stage.setWidth(SearchController.minWidth);
            stage.show();
            Centralizer.setCenter(stage);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    /***
     * Exiting the application.
     */
    @FXML
    protected void exit() {
        Stage stage = (Stage) exit.getScene().getWindow();
        stage.close();
    }
}
