package GUI;

import Resources.TableInfo;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SearchController {
    @FXML
    private ChoiceBox<String> era;
    @FXML
    private RadioButton rb1;
    @FXML
    private RadioButton rb2;
    @FXML
    private TextField birthYear;
    @FXML
    private TextField age;
    @FXML
    private Button results;
    @FXML
    private Button advanced;
    @FXML
    private Pane genres;

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

    private void setCenter(Stage stage) {
        Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
        stage.setX((primScreenBounds.getWidth() - stage.getWidth()) / 2);
        stage.setY((primScreenBounds.getHeight() - stage.getHeight()) / 2);
    }

    @FXML
    protected void home() {
        try {
            Stage stage = (Stage) rb1.getScene().getWindow();
            VBox root = (VBox) FXMLLoader.load(getClass().getResource("Menu.fxml"));
            Scene scene = new Scene(root,450,500);
            scene.getStylesheets().add(getClass().getResource("MenuCss.css").toExternalForm());
            stage.setTitle("Nostalgia");
            stage.setScene(scene);
            stage.show();
            setCenter(stage);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    protected void advanced() {
        try {
            Stage stage = (Stage) advanced.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("AdvancedSearch.fxml"));
            AnchorPane root = (AnchorPane) loader.load();
            Scene scene = new Scene(root,650,650);
            AdvancedSearchController advancedSearchController = loader.getController();
            advancedSearchController.init(era.getValue(), genres.getChildren(), birthYear.getText(), age.getText(),
                    !era.isDisabled());
            scene.getStylesheets().add(getClass().getResource("SearchCss.css").toExternalForm());
            stage.setTitle("Search");
            stage.setScene(scene);
            setCenter(stage);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
    private void addValues(Map<String, ArrayList<String>> map ){
        if (era.isDisable() == false){
            ArrayList<String> arr = new ArrayList<>();
            arr.add(era.getValue());
            map.put("era",arr);
        }
        else{
            ArrayList<String> arr = new ArrayList<>();
            arr.add(birthYear.getText());
            map.put("birthYear",arr);
            arr = new ArrayList<>();
            arr.add(age.getText());
            map.put("age",arr);
        }
        ArrayList<String> arr = new ArrayList<>();
        for (Node genre :genres.getChildren()){
            if(((CheckBox)genre).isSelected()){
                String g = ((CheckBox) genre).getText();
                arr.add(g);
            }
        }
        map.put("genre",arr);
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private boolean checkValues(){
        if (era.isDisable() == true){
            String birthYear = this.birthYear.getText();
            String age = this.age.getText();
            if( birthYear.equals("") || Integer.parseInt(birthYear) > 2018 || Integer.parseInt(birthYear) < 1900){
                showAlert("birth year is invalid");
                return false;
            }
            if( age.equals("") || Integer.parseInt(age)<1 || Integer.parseInt(age)>120 ){
                showAlert("age is invalid");
                return false;
            }
            if(Integer.parseInt(birthYear)+Integer.parseInt(age)>2019){
                showAlert("birth year or age is incorrect");
                return false;
            }
        }
        return true;
    }

    @FXML
    protected void results() {
        try {
            if(checkValues() == false)
                return;
            Connection connection = Connection.getInstance();
            Map<String, ArrayList<String>> map = new HashMap<>();
            addValues(map);
            TableInfo info = connection.query(map);

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("Results.fxml"));
            Stage stage = (Stage) results.getScene().getWindow();
            AnchorPane root = (AnchorPane) loader.load();
            Scene scene = new Scene(root,450,500);

            ResultsController resultsController = loader.getController();
            resultsController.addData(info);

            stage.setTitle("Nostalgia");
            stage.setScene(scene);
            stage.show();
            setCenter(stage);
        } catch(Exception e) {
            e.printStackTrace();
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
}
