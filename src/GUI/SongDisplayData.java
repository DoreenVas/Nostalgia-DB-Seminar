package GUI;

import java.util.ArrayList;

public class SongDisplayData {
    private String id;
    private String name;
    private String dancibility;
    private String duration;
    private String tempo;
    private String hotness;
    private String loudness;
    private String year;
    private String words = null;
    private String artist;
    private String album;

    public SongDisplayData(ArrayList<String> data, String artist, String album) {
        this.id = data.get(0);
        this.name = data.get(1);
        this.dancibility = data.get(2);
        this.duration = String.valueOf(Float.parseFloat(data.get(3)) / 60);
        this.tempo = data.get(4);
        this.hotness = data.get(5);
        this.loudness = data.get(6);
        this.year = data.get(7);
        this.artist = artist;
        this.album = album;
    }

    public SongDisplayData(String[] data, String artist, String album) {
        this.id = data[0];
        this.name = data[1];
        this.dancibility = data[2];
        this.duration = String.valueOf(Float.parseFloat(data[3]) / 60);
        this.tempo = data[4];
        this.hotness = data[5];
        this.loudness = data[6];
        this.year = data[7];
        this.artist = artist;
        this.album = album;
    }

    public String getName() {
        return name;
    }

    public String getDancibility() {
        return dancibility;
    }

    public String getDuration() {
        return duration;
    }

    public String getHotness() {
        return hotness;
    }

    public String getLoudness() {
        return loudness;
    }

    public String getTempo() {
        return tempo;
    }

    public String getWords() {
        if(words == null) {
            return "Sorry, we do not have the lyrics for this song.";
        }
        return words;
    }

    public String getYear() {
        return year;
    }

    public String getId() {
        return id;
    }

    public String getArtist() {
        return artist;
    }

    public String getAlbum() {
        return album;
    }
}
