package GUI;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;

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
        connection.OpenConnection();
    }

    @Override
    public void stop() throws Exception {
        Connection connection = Connection.getInstance();
        connection.CloseConnection();
        super.stop();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
