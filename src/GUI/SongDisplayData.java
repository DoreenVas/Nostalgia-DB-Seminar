package GUI;

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
}
