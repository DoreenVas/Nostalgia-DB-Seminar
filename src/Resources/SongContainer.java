package Resources;

public class SongContainer implements Container<String> {

    private String songName;

    public SongContainer(String songName) {
        this.songName = songName;
    }

    @Override
    public String getValue() {
        return songName;
    }
}
