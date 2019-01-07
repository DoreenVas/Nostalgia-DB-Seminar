package Resources;

/**
 * Holds a songs identifier.
 * Used to distinguish between different data.
 */
public class LyricsContainer implements Container<String> {
    // members
    private String song;

    /***
     * Constructor
     * @param song the lyrics of the song
     */
    public LyricsContainer(String song) {
        this.song = song;
    }

    @Override
    public String getValue() {
        return song;
    }
}
