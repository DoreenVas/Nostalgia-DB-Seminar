package GUI;

import Resources.AlertMessages;
import Resources.TableInfo;
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

import java.io.IOException;
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

    public void initialize(String era, ObservableList list, String birthYear, String age, boolean rb1Selected) {
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
            Stage stage = (Stage) rb1.getScene().getWindow();
            VBox root = (VBox) FXMLLoader.load(getClass().getResource("Menu.fxml"));
            Scene scene = new Scene(root, MenuController.minWidth, MenuController.minHeight);
            scene.getStylesheets().add(getClass().getResource("MenuCss.css").toExternalForm());
            stage.setTitle("Nostalgia");
            stage.setScene(scene);
            stage.setMinHeight(MenuController.minHeight);
            stage.setMinWidth(MenuController.minWidth);
            stage.setHeight(MenuController.minHeight);
            stage.setWidth(MenuController.minWidth);
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

    @FXML
    protected boolean results() {
        try {
            if(checkValues() == false) {
                return false;
            }
            Map<String, ArrayList<String>> map = new HashMap<>();
            addValues(map);

            Stage stage = (Stage)rb1.getScene().getWindow();
//            FXMLLoader loader = new FXMLLoader();
//            loader.setLocation(getClass().getResource("Waiting.fxml"));
////            AnchorPane root = (AnchorPane)FXMLLoader.load(getClass().getResource("Waiting.fxml"));
//            AnchorPane root = (AnchorPane) loader.load();
//            Scene scene = new Scene(root, WaitingController.minWidth, WaitingController.minHeight);
//            stage.setMinHeight(WaitingController.minHeight);
//            stage.setMinWidth(WaitingController.minWidth);
//            stage.setHeight(WaitingController.minHeight);
//            stage.setWidth(WaitingController.minWidth);
//            WaitingController waitingController = loader.getController();
//            waitingController.activateWaiting(map, "song");
//            stage.setTitle("Loading");
//            stage.setScene(scene);
//            stage.show();
//            Centralizer.setCenter(stage);

//            new Thread(() -> Alerter.showAlert("Loading", Alert.AlertType.INFORMATION)).start();

//            ExecutorService executorService = Executors.newSingleThreadExecutor();
//            Callable<TableInfo> callable = new Callable<TableInfo>() {
//                @Override
//                public TableInfo call() throws Exception {
//                    try {
//                        TimeUnit.SECONDS.sleep(5);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                    Connection connection = Connection.getInstance();
//                    TableInfo info = connection.query(map, "song");
//                    if(info == null) {
//                        return null;
//                    }
//                    return info;
//                }
//            };
//            Future<TableInfo> future = executorService.submit(callable);

//            Alerter.showAlert("Loading", Alert.AlertType.INFORMATION);
            Connection connection = Connection.getInstance();
            TableInfo info = connection.query(map, "song");
//            TableInfo info = future.get();
            if(info == null) {
                return false;
            }

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("Results.fxml"));
            AnchorPane root = (AnchorPane) loader.load();
            Scene scene = new Scene(root, ResultsController.minWidth, ResultsController.minHeight);
            ResultsController resultsController = loader.getController();
            resultsController.addData(info, map);
            stage.setTitle("Nostalgia");
            stage.setScene(scene);
            stage.setMinHeight(ResultsController.minHeight);
            stage.setMinWidth(ResultsController.minWidth);
            stage.setHeight(ResultsController.minHeight);
            stage.setWidth(ResultsController.minWidth);
            stage.show();
            Centralizer.setCenter(stage);
        } catch(Exception e) {
            Alerter.showAlert(AlertMessages.pageLoadingFailure(), Alert.AlertType.ERROR);
            return false;
        }
        return true;
    }

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