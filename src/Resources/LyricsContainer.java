package Resources;

/**
 * Holds a songs identifier.
 * Used to distinguish between different data.
 */
public class LyricsContainer implements Container<String> {

    private String song;

    public LyricsContainer(String song) {
        this.song = song;
    }

    @Override
    public String getValue() {
        return song;
    }
}
