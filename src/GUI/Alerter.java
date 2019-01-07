package GUI;

import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogEvent;
import java.util.ArrayList;
import java.util.Optional;

/**
 * Holds all of the alerts the application can invoke.
 */
public class Alerter {

    /**
     *
     * Show an alert message of given type with given message.
     * @param message the message to display
     * @param type the wanted alert type
     */
    public static void showAlert(String message, Alert.AlertType type) {
        Alert alert = new Alert(type, message);
        alert.setHeaderText(null);
        alert.showAndWait();
    }

    /**
     *
     * Show an alert message of given type with given message.
     * Also alters the alerts buttons to given buttonTypes.
     * @param message the message to display
     * @param type the wanted alert type
     * @param buttonTypes the wanted buttons to display
     * @return True or False according to the clicked button by the user
     */
    public static boolean showAlert(String message, Alert.AlertType type, String buttonTypes) {
        Alert alert = new Alert(type, message);
        ButtonType yes = new ButtonType("Yes"), no = new ButtonType("No");
        // check wanted buttons
        if(buttonTypes.replace(" ", "").equals("yesno") ||
                buttonTypes.replace(" ", "").equals("noyes")) {
            // clear alert buttons and add wanted buttons
            alert.getButtonTypes().clear();
            alert.getButtonTypes().add(yes);
            alert.getButtonTypes().add(no);
        }
        alert.setHeaderText(null);
        Optional<ButtonType> result = alert.showAndWait();
        return result.isPresent() && result.get().equals(yes);
    }

    /**
     *
     * Show an alert message of given type with given message.
     * Also alters the alerts buttons to given buttonTypes.
     * @param message the message to display
     * @param type the wanted alert type
     * @param buttonTypes the wanted buttons to display
     */
    public static void showAlert(String message, Alert.AlertType type, ArrayList<ButtonType> buttonTypes) {
        Alert alert = new Alert(type, message);
        // if buttonTypes is not empty
        if(buttonTypes != null && buttonTypes.size() != 0) {
            // clear alert buttons
            alert.getButtonTypes().clear();
            // add all given buttons
            for(ButtonType buttonType : buttonTypes) {
                alert.getButtonTypes().add(buttonType);
            }
        }
        alert.setHeaderText(null);
        alert.showAndWait();
    }

    /**
     *
     * Show an alert message of given type with given message.
     * Also sets an event to happen when alert is closed.
     * @param message the message to display
     * @param type the wanted alert type
     * @param onClose the event to fire when alert closes
     */
    public static void showAlert(String message, Alert.AlertType type, EventHandler<DialogEvent> onClose) {
        Alert alert = new Alert(type, message);
        if(onClose != null) {
            alert.setOnCloseRequest(onClose);
        }
        alert.setHeaderText(null);
        alert.showAndWait();
    }

    /**
     *
     * Show an alert message of given type with given message.
     * Also sets an event to happen when alert is closed and alters the alerts buttons to given buttonTypes.
     * @param message the message to display
     * @param type the wanted alert type
     * @param onClose the event to fire when alert closes
     */
    public static void showAlert(String message, Alert.AlertType type, ArrayList<ButtonType> buttonTypes
                                 , EventHandler<DialogEvent> onClose) {
        Alert alert = new Alert(type, message);
        if(onClose != null) {
            alert.setOnCloseRequest(onClose);
        }
        if(buttonTypes != null && buttonTypes.size() != 0) {
            alert.getButtonTypes().clear();
            for(ButtonType buttonType : buttonTypes) {
                alert.getButtonTypes().add(buttonType);
            }
        }
        alert.setHeaderText(null);
        alert.showAndWait();
    }
}
