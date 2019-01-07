package GUI;

import Resources.AlertMessages;
import Resources.DBConnectionException;
import Resources.TableInfo;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.*;


public abstract class Search {

    @FXML
    protected ChoiceBox<String> era;
    @FXML
    protected RadioButton rb1;
    @FXML
    protected RadioButton rb2;
    @FXML
    protected TextField birthYear;
    @FXML
    protected TextField age;
    @FXML
    protected Button results;
    @FXML
    protected Button advanced;
    @FXML
    protected Pane genres;
    @FXML
    protected ImageView loading;

    /**
     *
     * Initialize the search page.
     * Add the given information to the page.
     * @param era the era selected
     * @param list the list of genre objects
     * @param birthYear the birth year inserted
     * @param age the age inserted
     * @param rb1Selected the select value of rb1 button
     */
    public void initialize(String era, ObservableList list, String birthYear, String age, boolean rb1Selected) {
        // if rb1 is selected
        if(rb1Selected) {
            rb1Clicked();
            rb1.selectedProperty().setValue(true);
            rb2.selectedProperty().setValue(false);
        } else {
            rb2Clicked();
            rb1.selectedProperty().setValue(false);
            rb2.selectedProperty().setValue(true);
        }
        this.era.setValue(era);
        genres.getChildren().setAll(list);
        this.birthYear.setText(birthYear);
        this.age.setText(age);
    }

    @FXML
    protected void home() {
        try {
            // switch to main menu
            Stage stage = (Stage) rb1.getScene().getWindow();
            VBox root = (VBox) FXMLLoader.load(getClass().getResource("Menu.fxml"));
            Scene scene = new Scene(root, MenuController.minWidth, MenuController.minHeight);
            scene.getStylesheets().add(getClass().getResource("MenuCss.css").toExternalForm());
            stage.setTitle("Nostalgia");
            stage.setScene(scene);

            stage.show();
            Centralizer.setCenter(stage);
        } catch(Exception e) {
            Alerter.showAlert(AlertMessages.pageLoadingFailure(), Alert.AlertType.ERROR);
        }
    }

    @FXML
    protected void rb1Clicked() {
        // makes radio button able to select only if radio button is clicked
        rb2.setPickOnBounds(false);
        birthYear.setDisable(true);
        age.setDisable(true);
        era.setDisable(false);
    }

    @FXML
    protected void rb2Clicked() {
        // makes radio button able to select only if radio button is clicked
        rb1.setPickOnBounds(false);
        era.setDisable(true);
        birthYear.setDisable(false);
        age.setDisable(false);
    }

    /**
     *
     * loads waiting window untill results page loads
     * @return true is was successful, else false
     */
    @FXML
    protected boolean results() {
        Stage prev = (Stage)rb1.getScene().getWindow();
        Stage stage = new Stage();
        try {
            if(checkValues() == false) {
                return false;
            }
            Map<String, ArrayList<String>> map = new HashMap<>();
            addValues(map);

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("Waiting.fxml"));
            AnchorPane root = (AnchorPane) loader.load();
            Scene scene = new Scene(root, WaitingController.minWidth, WaitingController.minHeight);
            stage.setResizable(false);
            WaitingController waitingController = loader.getController();
            waitingController.activateWaiting(map);

            // create a new thread that runs the switching of the scenes
            Thread t = new Thread(() -> {
                try {
                    TimeUnit.SECONDS.sleep(1);
                    Connection connection = Connection.getInstance();
                    TableInfo info = connection.query(map, "song");
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            waitingController.stop(info, prev);
                        }
                    });
                } catch (Exception e) {
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            stage.close();
                            prev.show();
                            // check the type of error
                            if(e.getClass() == DBConnectionException.class && e.getMessage().contains("no results")) {
                                Alerter.showAlert(e.getMessage(), Alert.AlertType.INFORMATION);
                            } else {
                                Alerter.showAlert(e.getMessage(), Alert.AlertType.ERROR);
                            }
                        }
                    });
                }
            });
            // activate thread
            t.start();
            stage.setTitle("Loading");
            stage.setScene(scene);
            stage.show();
            Centralizer.setCenter(stage);

            // close current window
            prev.close();
        } catch(Exception e) {
            stage.close();
            // check the type of error
            if(e.getClass() == DBConnectionException.class && e.getMessage().contains("no results")) {
                Alerter.showAlert(e.getMessage(), Alert.AlertType.INFORMATION);
            } else {
                Alerter.showAlert(e.getMessage(), Alert.AlertType.ERROR);
            }
            return false;
        }
        return true;
    }

    /**
     *
     * Checks the values that the user filled in the fields.
     * @return true if all information inserted is valid, false otherwise
     */
    protected boolean checkValues(){
        if (era.isDisable() == true){
            String birthYear = this.birthYear.getText();
            String age = this.age.getText();
            if( birthYear.equals("") || Integer.parseInt(birthYear) > 2018 || Integer.parseInt(birthYear) < 1900){
                Alerter.showAlert("birth year is invalid", Alert.AlertType.WARNING);
                return false;
            }
            if( age.equals("") || Integer.parseInt(age)<1 || Integer.parseInt(age)>120 ){
                Alerter.showAlert("age is invalid", Alert.AlertType.WARNING);
                return false;
            }
            if(Integer.parseInt(birthYear)+Integer.parseInt(age)>2019){
                Alerter.showAlert("birth year or age is incorrect", Alert.AlertType.WARNING);
                return false;
            }
        }
        return true;
    }

    abstract void addValues(Map<String, ArrayList<String>> map);
}