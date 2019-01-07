package GUI;

import Resources.AlertMessages;
import Resources.TableInfo;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import sun.awt.Mutex;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * The Waiting controller - displayed while waiting for the query to return.
 */
public class WaitingController {

    @FXML
    private Label loading;
    private Map<String, ArrayList<String>> map;
    private Stage prev = null;

    public static final int minWidth = 160;
    public static final int minHeight = 200;

    public void stop(TableInfo info, Stage prev2) {
        try {
            // if the information wasn't defined
            if(info == null) {
                prev2.show();
                Alerter.showAlert(AlertMessages.emptyResult(), Alert.AlertType.INFORMATION);
                return;
            }
            // switch to results view
            Stage prev = (Stage) loading.getScene().getWindow();
            Stage stage = new Stage();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("Results.fxml"));
            AnchorPane root = (AnchorPane) loader.load();
            Scene scene = new Scene(root, ResultsController.minWidth, ResultsController.minHeight);
            scene.getStylesheets().add(getClass().getResource("ResultCss.css").toExternalForm());
            stage.setTitle("Results");
            stage.setScene(scene);
            stage.setResizable(false);
            ResultsController resultsController = loader.getController();
            resultsController.addData(info, map);
            stage.show();
            Centralizer.setCenter(stage);
            prev.close();
        } catch (Exception e) {
            Alerter.showAlert(AlertMessages.pageLoadingFailure(), Alert.AlertType.ERROR);
        }
    }

    public void activateWaiting(Map<String, ArrayList<String>> map) throws IOException, SQLException {
        this.map = map;
    }

}
