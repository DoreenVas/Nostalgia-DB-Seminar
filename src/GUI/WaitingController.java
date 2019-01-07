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
    private TableInfo info;

    public static final int minWidth = 250;
    public static final int minHeight = 10;

    public void stop() {
        try {
            Stage perv = (Stage) loading.getScene().getWindow();
            Stage stage = new Stage();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("Results.fxml"));
            AnchorPane root = (AnchorPane) loader.load();
            Scene scene = new Scene(root, ResultsController.minWidth, ResultsController.minHeight);
            stage.setTitle("Results");
            stage.setScene(scene);
            stage.setMinHeight(ResultsController.minHeight);
            stage.setMinWidth(ResultsController.minWidth);
            stage.setHeight(ResultsController.minHeight);
            stage.setWidth(ResultsController.minWidth);
            stage.show();
            Centralizer.setCenter(stage);
            ResultsController resultsController = loader.getController();
            resultsController.addData(info, map);
            perv.close();
        } catch (Exception e) {
            Alerter.showAlert(AlertMessages.pageLoadingFailure(), Alert.AlertType.ERROR);
        }
    }


    public TableInfo activateWaiting(Map<String, ArrayList<String>> map) throws IOException, SQLException {
//        try {
//            TimeUnit.SECONDS.sleep(3);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
        this.map = map;
        Connection connection = Connection.getInstance();
        info = connection.query(map, "song");

        return info;
    }

}
