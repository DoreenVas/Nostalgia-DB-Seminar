package GUI;

import Resources.AlertMessages;
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

/***
 * The Advanced Search Controller - controls the advanced features.
 */
public class AdvancedSearchController extends Search {

    @FXML
    private Slider tempo;
    @FXML
    private Slider popularity;
    @FXML
    private Slider duration;
    @FXML
    private TextField artist_name;
    @FXML
    private TextField album_name;

    public static final int minWidth = 650;
    public static final int minHeight = 660;

    /***
     * Loading the simple search window when the correct button is clicked.
     */
    @FXML
    protected void simpleSearch() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("Search.fxml"));
            AnchorPane root = (AnchorPane) loader.load();
            Stage stage = (Stage) rb1.getScene().getWindow();
            Scene scene = new Scene(root,SearchController.minWidth, SearchController.minHeight);
            SearchController searchController = loader.getController();
            searchController.initialize(era.getValue(), genres.getChildren(), birthYear.getText(), age.getText(),
                    !era.isDisabled());
            scene.getStylesheets().add(getClass().getResource("SearchCss.css").toExternalForm());
            stage.setTitle("Search");
            stage.setScene(scene);
            stage.setMinHeight(SearchController.minHeight);
            stage.setMinWidth(SearchController.minWidth);
            stage.setHeight(SearchController.minHeight);
            stage.setWidth(SearchController.minWidth);
            stage.show();
            Centralizer.setCenter(stage);
        } catch(Exception e) {
            Alerter.showAlert(AlertMessages.pageLoadingFailure(), Alert.AlertType.ERROR);
        }
    }

    /***
     * Adding values to the map that will be send to the Controller for the query.
     * Adding only values that are not null(were given by the user).
     * @param map
     */
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
        //if genres where added than add to map
        if(arr.isEmpty()==false)
            map.put("genre",arr);

        //adding tempo if not 0
        if(tempo.getValue()!=0.0){
            ArrayList<String> arrTempo = new ArrayList<>();
            arrTempo.add(Double.toString(tempo.getValue()));
            map.put("tempo",arrTempo);
        }

        //adding hotness if not 0
        if(popularity.getValue()!=0.0) {
            ArrayList<String> arrHotness = new ArrayList<>();
            arrHotness.add(Double.toString(popularity.getValue()));
            map.put("popularity", arrHotness);
        }

        //adding duration if not 0
        if(duration.getValue()!=0.0) {
            ArrayList<String> arrDuration = new ArrayList<>();
            arrDuration.add(Double.toString(duration.getValue()));
            map.put("duration", arrDuration);
        }

        //adding artist_name if given one
        if(!artist_name.getText().equals("")){
            ArrayList<String> arr_artist_name = new ArrayList<>();
            arr_artist_name.add(artist_name.getText());
            map.put("artist_name",arr_artist_name);
        }

        //adding album_name if given one
        if(!album_name.getText().equals("")) {
            ArrayList<String> arr_album_name = new ArrayList<>();
            arr_album_name.add(album_name.getText());
            map.put("album_name", arr_album_name);
        }
    }
}
