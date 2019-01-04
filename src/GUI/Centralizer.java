package GUI;

import javafx.geometry.Rectangle2D;
import javafx.stage.Screen;
import javafx.stage.Stage;

/**
 * A Method holder.
 * In charge of centering.
 */
public class Centralizer {

    /**
     *
     * Sets the given stage to the center of th screen.
     * @param stage the stage to centralize.
     */
    public static void setCenter(Stage stage) {
        Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
        stage.setX((primScreenBounds.getWidth() - stage.getWidth()) / 2);
        stage.setY((primScreenBounds.getHeight() - stage.getHeight()) / 2);
    }
}
