package Resources;

/**
 * Holds a songs id.
 * Used to distinguish between different data.
 */
public class SongIdContainer implements Container<String> {

    private String songId;

    public SongIdContainer(String songId) {
        this.songId = songId;
    }

    @Override
    public String getValue() {
        return songId;
    }
}
