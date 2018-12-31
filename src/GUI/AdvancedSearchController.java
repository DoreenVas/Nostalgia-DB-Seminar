package GUI;

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

public class AdvancedSearchController {
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
    @FXML
    private Slider tempo;
    @FXML
    private Slider hotness;
    @FXML
    private Slider duration;
    @FXML
    private TextField artist_name;
    @FXML
    private TextField album_name;


    protected void init(String era, ObservableList list, String birthYear, String age) {
        this.era.setValue(era);
        this.genres.getChildren().setAll(list);
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
    protected void simpleSearch() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("Search.fxml"));
            AnchorPane root = (AnchorPane) loader.load();
            Stage stage = (Stage) rb1.getScene().getWindow();
            Scene scene = new Scene(root,650,450);
            SearchController searchController = loader.getController();
            searchController.initialize(era.getValue(), genres.getChildren(), birthYear.getText(), age.getText());
            scene.getStylesheets().add(getClass().getResource("SearchCss.css").toExternalForm());
            stage.setTitle("Search");
            stage.setScene(scene);
            stage.show();
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
        //adding tempo
        ArrayList<String> arrTempo = new ArrayList<>();
        arrTempo.add(Double.toString(tempo.getValue()));
        map.put("tempo",arrTempo);

        //adding hotness
        ArrayList<String> arrHotness = new ArrayList<>();
        arrHotness.add(Double.toString(hotness.getValue()));
        map.put("hotness",arrHotness);

        //adding duration
        ArrayList<String> arrDuration = new ArrayList<>();
        arrDuration.add(Double.toString(duration.getValue()));
        map.put("duration",arrDuration);

        //adding artist_name
        ArrayList<String> arr_artist_name = new ArrayList<>();
        arr_artist_name.add(artist_name.getText());
        map.put("artist_name",arr_artist_name);

        //adding album_name
        ArrayList<String> arr_album_name = new ArrayList<>();
        arr_album_name.add(album_name.getText());
        map.put("album_name",arr_album_name);

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
            connection.query(map);

            Stage stage = (Stage) results.getScene().getWindow();
            AnchorPane root = (AnchorPane) FXMLLoader.load(getClass().getResource("Results.fxml"));
            Scene scene = new Scene(root,450,500);
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
