package DBConnection;

public class ArtisitContainer implements Container<String> {

    private String artist;

    public ArtisitContainer(String genre) {
        this.artist = genre;
    }

    @Override
    public String getValue() {
        return this.artist;
    }
}
