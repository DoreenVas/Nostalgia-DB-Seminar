package GUI;

import Resources.TableInfo;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class WaitingController {
    @FXML
    private Label loading;

    public static final int minWidth = 350;
    public static final int minHeight = 350;

    public TableInfo activateWaiting(Map<String, ArrayList<String>> map, String table)throws IOException, SQLException {
//        Stage stage = (Stage) loading.getScene().getWindow();
        Stage stage = new Stage();
        AnchorPane root = (AnchorPane) FXMLLoader.load(getClass().getResource("Waiting.fxml"));
        Scene scene = new Scene(root, WaitingController.minWidth, WaitingController.minHeight);
        stage.setTitle("Waiting");
        stage.setScene(scene);
        stage.setMinHeight(WaitingController.minHeight);
        stage.setMinWidth(WaitingController.minWidth);
        stage.setHeight(WaitingController.minHeight);
        stage.setWidth(WaitingController.minWidth);
        stage.show();
        Centralizer.setCenter(stage);

        //TODO delete
        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Connection connection = Connection.getInstance();
        TableInfo info = connection.query(map, table);
        if(info == null) {
            stage.close();
            return null;
        }
        stage.close();
        return info;
    }

}
