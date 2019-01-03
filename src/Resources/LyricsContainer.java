package Resources;

public class LyricsContainer implements Container<String> {

    private String songName;

    public LyricsContainer(String songName) {
        this.songName = songName;
    }

    @Override
    public String getValue() {
        return songName;
    }
}
