package Resources;

/**
 * Holds an album id.
 * Used to distinguish between different data.
 */
public class AlbumIdContainer implements Container<String> {

    private String albumId;

    public AlbumIdContainer(String albumId) {
        this.albumId = albumId;
    }

    @Override
    public String getValue() {
        return albumId;
    }
}
