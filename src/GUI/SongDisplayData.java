package GUI;

import java.util.ArrayList;

public class SongDisplayData {
    private String id = null;
    private String name = null;
    private String dancibility = null;
    private String duration = null;
    private String tempo = null;
    private String hotness = null;
    private String loudness = null;
    private String year = null;
    private String words = null;
    private String artist = null;
    private String album = null;

    public SongDisplayData(ArrayList<String> data, String artist, String album) {
        this.id = data.get(0);
        this.name = data.get(1);
        this.dancibility = data.get(2);
        setDuration(Float.parseFloat(data.get(3)));
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
        setDuration(Float.parseFloat(data[3]));
        this.tempo = data[4];
        this.hotness = data[5];
        this.loudness = data[6];
        this.year = data[7];
        this.artist = artist;
        this.album = album;
    }

    private void setDuration(float duration) {
        int minutes = (int)(duration / 60);
        int seconds = (int)(duration - minutes * 60);
        this.duration = String.valueOf(minutes) + "m " + String.valueOf(seconds) + "s";
    }

    public String getName() {
        if(name == null) {
            return "Sorry, we do not have this songs name.";
        }
        return name;
    }

    public String getDancibility() {
        if(dancibility == null) {
            return "Sorry, we do not have this songs dancibility.";
        }
        return dancibility;
    }

    public String getDuration() {
        if(duration == null) {
            return "Sorry, we do not have this songs duration.";
        }
        return duration;
    }

    public String getHotness() {
        if(hotness == null) {
            return "Sorry, we do not have this songs popularity.";
        }
        return hotness;
    }

    public String getLoudness() {
        if(loudness == null) {
            return "Sorry, we do not have this songs loudness.";
        }
        return loudness;
    }

    public String getTempo() {
        if(tempo == null) {
            return "Sorry, we do not have this songs tempo.";
        }
        return tempo;
    }

    public String getWords() {
        if(words == null) {
            return "Sorry, we do not have the lyrics for this song.";
        }
        return words;
    }

    public String getYear() {
        if(year == null) {
            return "Sorry, we do not have this songs year.";
        }
        return year;
    }

    public String getId() {
        if(id == null) {
            return "Sorry, we do not have this songs id.";
        }
        return id;
    }

    public String getArtist() {
        if(artist == null) {
            return "Sorry, we do not have this songs artist.";
        }
        return artist;
    }

    public String getAlbum() {
        if(album == null) {
            return "Sorry, we do not have this songs album.";
        }
        return album;
    }

    public void setWords(String words) {
        this.words = words;
    }
}
