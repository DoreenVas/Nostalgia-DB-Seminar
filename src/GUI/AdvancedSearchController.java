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

public class AdvancedSearchController extends Search {

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

    @FXML
    protected void simpleSearch() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("Search.fxml"));
            AnchorPane root = (AnchorPane) loader.load();
            Stage stage = (Stage) rb1.getScene().getWindow();
            Scene scene = new Scene(root,650,450);
            SearchController searchController = loader.getController();
            searchController.initialize(era.getValue(), genres.getChildren(), birthYear.getText(), age.getText(),
                    !era.isDisabled());
            scene.getStylesheets().add(getClass().getResource("SearchCss.css").toExternalForm());
            stage.setTitle("Search");
            stage.setScene(scene);
            stage.show();
            setCenter(stage);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    protected void addValues(Map<String, ArrayList<String>> map ){
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

    protected boolean checkValues() {
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
}
