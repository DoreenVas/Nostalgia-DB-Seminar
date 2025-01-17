package Resources;

/**
 * Holds an artist information.
 * Used to distinguish between different data.
 */
public class ArtistContainer implements Container<String> {
    // members
    private String artist;

    /***
     * Constructor
     * @param artist the artist name
     */
    public ArtistContainer(String artist) {
        this.artist = artist;
    }

    @Override
    public String getValue() {
        return this.artist;
    }
}
