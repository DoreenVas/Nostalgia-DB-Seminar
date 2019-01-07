package Resources;

/**
 * Holds a songs id.
 * Used to distinguish between different data.
 */
public class SongIdContainer implements Container<String> {
    // members
    private String songId;

    /***
     * Constructor
     * @param songId the song id
     */
    public SongIdContainer(String songId) {
        this.songId = songId;
    }

    @Override
    public String getValue() {
        return songId;
    }
}
