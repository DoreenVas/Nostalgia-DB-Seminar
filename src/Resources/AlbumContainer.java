package Resources;

public class AlbumContainer implements Container<String> {

    private String albumName;

    public AlbumContainer(String albumName) {
        this.albumName = albumName;
    }

    @Override
    public String getValue() {
        return albumName;
    }
}
