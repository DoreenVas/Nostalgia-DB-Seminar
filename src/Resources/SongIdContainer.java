package Resources;

public class SongIdContainer implements Container<String> {

    private String songName;

    public SongIdContainer(String songName) {
        this.songName = songName;
    }

    @Override
    public String getValue() {
        return songName;
    }
}
