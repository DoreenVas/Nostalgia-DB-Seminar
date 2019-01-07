package GUI;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Screen;
import javafx.stage.Stage;

/**
 * The main class.
 */
public class Main extends Application {

    /**
     * Displays the Menu of the application.
     * @param primaryStage
     * @throws Exception
     */
    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("Menu.fxml"));
        Scene scene = new Scene(root, MenuController.minWidth, MenuController.minHeight);
        scene.getStylesheets().add(getClass().getResource("MenuCss.css").toExternalForm());
        primaryStage.setTitle("Nostalgia");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
        Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
        primaryStage.setX((primScreenBounds.getWidth() - primaryStage.getWidth()) / 2);
        primaryStage.setY((primScreenBounds.getHeight() - primaryStage.getHeight()) / 2);
        Connection connection = null;
        try {
            connection = Connection.getInstance();
        } catch (Exception e) {
            Alerter.showAlert(e.getMessage(), Alert.AlertType.ERROR);
            primaryStage.close();
            stop();
            return;
        }
        if(connection == null) {
            primaryStage.close();
            stop();
            return;
        }
        boolean tryAgain = true;
        while(tryAgain) {
            // try to open a connection to the controller
            try {
                connection.OpenConnection();
                tryAgain = false;
            } catch (Exception e) {
                // display appropriate message
                tryAgain = Alerter.showAlert(e.getMessage(), Alert.AlertType.ERROR, "yesno");
                // if user clicked no then close the application
                if(!tryAgain) {
                    primaryStage.close();
                }
            }
        }
    }

    /**
     * Stopping the connection to the DB when the application is closed.
     * @throws Exception
     */
    @Override
    public void stop() throws Exception {
        try {
            Connection connection = Connection.getInstance();
            // try to close the connection to the controller
            connection.CloseConnection();
        } catch (Exception e) {

        }
        // stop the application
        super.stop();
    }

    /**
     * Activates the application.
     * @param args the arguments to the main
     */
    public static void main(String[] args) {
        launch(args);
    }
}
