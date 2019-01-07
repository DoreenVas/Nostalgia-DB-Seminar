package Resources;

/**
 * Holds an album id.
 * Used to distinguish between different data.
 */
public class AlbumIdContainer implements Container<String> {
    // members
    private String albumId;

    /***
     * Constructor
     * @param albumId the id of the album
     */
    public AlbumIdContainer(String albumId) {
        this.albumId = albumId;
    }

    @Override
    public String getValue() {
        return albumId;
    }
}
