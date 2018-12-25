package DBConnection;

public class ArtisitContainer implements Container<String> {

    private String artist;

    public ArtisitContainer(String artist) {
        this.artist = artist;
    }

    @Override
    public String getValue() {
        return this.artist;
    }
}
