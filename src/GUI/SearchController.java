package GUI;

import Resources.AlertMessages;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import java.util.ArrayList;
import java.util.Map;

/***
 * The Search Controller - controls the search features.
 */
public class SearchController extends Search {

    public static final int minWidth = 650;
    public static final int minHeight = 480;

    /***
     * Loading the advanced search window when the correct button is clicked.
     */
    @FXML
    protected void advanced() {
        try {
            Stage stage = (Stage) advanced.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("AdvancedSearch.fxml"));
            AnchorPane root = (AnchorPane) loader.load();
            Scene scene = new Scene(root,AdvancedSearchController.minWidth, AdvancedSearchController.minHeight);
            AdvancedSearchController advancedSearchController = loader.getController();
            advancedSearchController.initialize(era.getValue(), genres.getChildren(), birthYear.getText(), age.getText(),
                    !era.isDisabled());
            scene.getStylesheets().add(getClass().getResource("SearchCss.css").toExternalForm());
            stage.setTitle("Search");
            stage.setScene(scene);
            stage.setResizable(false);
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
    protected void addValues(Map<String, ArrayList<String>> map) {
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
    }

}
