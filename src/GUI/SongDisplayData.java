package GUI;

import java.util.ArrayList;

/***
 * Container for the song information to be displayed in SongInfo Controller.
 */
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

    /**
     * Constructor - Initializing
     * @param data
     * @param artist
     * @param album
     */
    public SongDisplayData(ArrayList<String> data, String artist, String album) {
        this.id = data.get(0);
        this.name = removeNonEnglish(data.get(1));
        this.dancibility = data.get(2);
        setDuration(Float.parseFloat(data.get(3)));
        this.tempo = data.get(4);
        this.hotness = data.get(5);
        this.loudness = data.get(6);
        this.year = data.get(7);
        this.artist = removeNonEnglish(artist);
        this.album = removeNonEnglish(album);
    }

    /**
     * Constructor
     * @param data
     * @param artist
     * @param album
     */
    public SongDisplayData(String[] data, String artist, String album) {
        this.id = data[0];
        this.name = removeNonEnglish(data[1]);
        this.dancibility = data[2];
        setDuration(Float.parseFloat(data[3]));
        this.tempo = data[4];
        this.hotness = data[5];
        this.loudness = data[6];
        this.year = data[7];
        this.artist = removeNonEnglish(artist);
        this.album = removeNonEnglish(album);
    }

    private String removeNonEnglish(String str) {
        StringBuilder builder = new StringBuilder();
        for(String word : str.split(" ")) {
            for (char let : word.toCharArray()) {
                if ((let > 64 && let < 91) || (let > 96 && let < 123)) {
                    builder.append(let);
                }
            }
            builder.append(" ");
        }
        return builder.toString();
    }

    /**
     * Setting the duration of the song to the given duration.
     * @param duration
     */
    private void setDuration(float duration) {
        int minutes = (int)(duration / 60);
        int seconds = (int)(duration - minutes * 60);
        this.duration = String.valueOf(minutes) + "m " + String.valueOf(seconds) + "s";
    }

    /**
     * Getter
     * @return name
     */
    public String getName() {
        if(name == null) {
            return "Sorry, we do not have this songs name.";
        }
        return name;
    }

    /**
     * Getter
     * @return dancibility
     */
    public String getDancibility() {
        if(dancibility == null) {
            return "Sorry, we do not have this songs dancibility.";
        }
        return dancibility;
    }

    /**
     * Getter
     * @return duration
     */
    public String getDuration() {
        if(duration == null) {
            return "Sorry, we do not have this songs duration.";
        }
        return duration;
    }

    /**
     * Getter
     * @return hotness
     */
    public String getHotness() {
        if(hotness == null) {
            return "Sorry, we do not have this songs popularity.";
        }
        return hotness;
    }

    /**
     * Getter
     * @return loudness
     */
    public String getLoudness() {
        if(loudness == null) {
            return "Sorry, we do not have this songs loudness.";
        }
        return loudness;
    }

    /**
     * Getter
     * @return tempo
     */
    public String getTempo() {
        if(tempo == null) {
            return "Sorry, we do not have this songs tempo.";
        }
        return tempo;
    }

    /**
     * Getter
     * @return words
     */
    public String getWords() {
        if(words == null) {
            return "Sorry, we do not have the lyrics for this song.";
        }
        return words;
    }

    /**
     * Getter
     * @return year
     */
    public String getYear() {
        if(year == null) {
            return "Sorry, we do not have this songs year.";
        }
        return year;
    }

    /**
     * Getter
     * @return ID
     */
    public String getId() {
        if(id == null) {
            return "Sorry, we do not have this songs id.";
        }
        return id;
    }

    /**
     * Getter
     * @return artist
     */
    public String getArtist() {
        if(artist == null) {
            return "Sorry, we do not have this songs artist.";
        }
        return artist;
    }

    /**
     * Getter
     * @return album
     */
    public String getAlbum() {
        if(album == null) {
            return "Sorry, we do not have this songs album.";
        }
        return album;
    }

    /**
     * Setter
     * @param words
     */
    public void setWords(String words) {
        this.words = words;
    }
}
