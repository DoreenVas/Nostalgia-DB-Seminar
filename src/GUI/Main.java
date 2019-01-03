package GUI;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("Menu.fxml"));
        Scene scene = new Scene(root,450,500);
        scene.getStylesheets().add(getClass().getResource("MenuCss.css").toExternalForm());
        primaryStage.setTitle("Nostalgia");
        primaryStage.setScene(scene);
        primaryStage.show();
        Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
        primaryStage.setX((primScreenBounds.getWidth() - primaryStage.getWidth()) / 2);
        primaryStage.setY((primScreenBounds.getHeight() - primaryStage.getHeight()) / 2);
        Connection connection = Connection.getInstance();
        boolean tryAgain = true;
        while(tryAgain) {
            try {
                connection.OpenConnection();
                tryAgain = false;
            } catch (Exception e) {
                tryAgain = Alerter.showAlert(e.getMessage(), Alert.AlertType.ERROR, "yesno");
            }
        }
    }

    @Override
    public void stop() throws Exception {
        Connection connection = Connection.getInstance();
        try {
            connection.CloseConnection();
        } catch (Exception e) {
            e.toString();
        }
        super.stop();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
