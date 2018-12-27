package GUI;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

public class SongInfoController {

    @FXML
    private Label name;
    @FXML
    private Label duration;
    @FXML
    private Label hotness;
    @FXML
    private Label year;
    @FXML
    private TextArea words;
    private String tempo;
    private String loudness;
    private String dancibility;

    public SongInfoController() {
    }

    public void initialize(String name, String dancibility, String duration, String tempo,
                           String hotness, String loudness, String year, String words) {
        this.name.setText(name);
//        this.dancibility = dancibility;
        this.duration.setText(duration);
//        this.tempo = tempo;
        this.hotness.setText(hotness);
//        this.loudness = loudness;
        this.year.setText(year);
        this.words.setText(words);
    }

    public void initialize(String[] data) {
        this.name.setText(data[0]);
//        this.dancibility = data[1];
        this.duration.setText(data[2]);
//        this.tempo = data[3];
        this.hotness.setText(data[4]);
//        this.loudness = data[5];
        this.year.setText(data[6]);
        this.words.setText(data[7]);
    }
}
