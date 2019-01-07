package GUI;

import Resources.AlertMessages;
import Resources.TableInfo;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import javax.swing.text.html.ImageView;
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
    private Button tempB;

    private Map<String, ArrayList<String>> map;
    private TableInfo info;
    private String table;

    public static final int minWidth = 350;
    public static final int minHeight = 350;

    @FXML
    private void click() {
//        try {
//
//            Connection connection = Connection.getInstance();
//            info = connection.query(map, table);
//            if(info == null) {
////            stage.close();
//                return;
//            }
//
////            Stage stage = (Stage) tempB.getScene().getWindow();
////            FXMLLoader loader = new FXMLLoader();
////            loader.setLocation(getClass().getResource("Results.fxml"));
//////        Stage stage = new Stage();
////            AnchorPane root = (AnchorPane) loader.load();
////            Scene scene = new Scene(root, ResultsController.minWidth, ResultsController.minHeight);
//////        prev.setScene(scene);
////            ResultsController resultsController = loader.getController();
////            resultsController.addData(info, map);
////            stage.setTitle("Results");
////            stage.setScene(scene);
////            stage.setMinHeight(ResultsController.minHeight);
////            stage.setMinWidth(ResultsController.minWidth);
////            stage.setHeight(ResultsController.minHeight);
////            stage.setWidth(ResultsController.minWidth);
////            stage.show();
////            Centralizer.setCenter(stage);
//        } catch (Exception e) {
//            Alerter.showAlert(AlertMessages.pageLoadingFailure(), Alert.AlertType.ERROR);
//        }
    }

    public void activateWaiting(Map<String, ArrayList<String>> map, String table)throws IOException, SQLException {
        this.map = map;
        this.table = table;

        //TODO delete

//        stage.close();

//        Stage stage = (Stage)tempB.getScene().getWindow();

        Stage stage = (Stage) tempB.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("Results.fxml"));
//        Stage stage = new Stage();
        AnchorPane root = (AnchorPane) loader.load();
        Scene scene = new Scene(root, ResultsController.minWidth, ResultsController.minHeight);
//        prev.setScene(scene);
        ResultsController resultsController = loader.getController();
        resultsController.addData(info, map);
        stage.setTitle("Results");
        stage.setScene(scene);
        stage.setMinHeight(ResultsController.minHeight);
        stage.setMinWidth(ResultsController.minWidth);
        stage.setHeight(ResultsController.minHeight);
        stage.setWidth(ResultsController.minWidth);
        stage.show();
        Centralizer.setCenter(stage);
    }

}
