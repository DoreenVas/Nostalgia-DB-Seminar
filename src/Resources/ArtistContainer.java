package Resources;

import Resources.Container;

public class ArtistContainer implements Container<String> {

    private String artist;

    public ArtistContainer(String artist) {
        this.artist = artist;
    }

    @Override
    public String getValue() {
        return this.artist;
    }
}
