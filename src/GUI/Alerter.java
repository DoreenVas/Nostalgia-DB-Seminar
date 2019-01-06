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

    public static void showAlert(String message, Alert.AlertType type) {
        Alert alert = new Alert(type, message);
        alert.setHeaderText(null);
        alert.showAndWait();
    }

    public static boolean showAlert(String message, Alert.AlertType type, String buttonTypes) {
        Alert alert = new Alert(type, message);
        ButtonType yes = new ButtonType("Yes"), no = new ButtonType("No");
        if(buttonTypes.replace(" ", "").equals("yesno") ||
                buttonTypes.replace(" ", "").equals("noyes")) {
            alert.getButtonTypes().clear();
            alert.getButtonTypes().add(yes);
            alert.getButtonTypes().add(no);
        }
        alert.setHeaderText(null);
        Optional<ButtonType> result = alert.showAndWait();
//        result.ifPresent(res -> {
//            if(res.equals(yes)) {
//                return true;
//            }
//        });
        return result.isPresent() && result.get().equals(yes);
    }

    public static void showAlert(String message, Alert.AlertType type, ArrayList<ButtonType> buttonTypes) {
        Alert alert = new Alert(type, message);
        if(buttonTypes != null && buttonTypes.size() != 0) {
            alert.getButtonTypes().clear();
            for(ButtonType buttonType : buttonTypes) {
                alert.getButtonTypes().add(buttonType);
            }
        }
        alert.setHeaderText(null);
        alert.showAndWait();
    }

    public static void showAlert(String message, Alert.AlertType type, EventHandler<DialogEvent> onClose) {
        Alert alert = new Alert(type, message);
        if(onClose != null) {
            alert.setOnCloseRequest(onClose);
        }
        alert.setHeaderText(null);
        alert.showAndWait();
    }

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
