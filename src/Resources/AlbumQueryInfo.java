package Resources;

public class AlbumQueryInfo {
    private AlbumContainer album;
    private SongContainer song;
    private ArtistContainer artist;

    public AlbumQueryInfo() {
        album = null;
        song = null;
        artist = null;
    }

    public SongContainer getSong() {
        return song;
    }

    public ArtistContainer getArtist() {
        return artist;
    }

    public AlbumContainer getAlbum() {
        return album;
    }

    public void setSong(SongContainer song) {
        this.song = song;
    }

    public void setArtist(ArtistContainer artist) {
        this.artist = artist;
    }

    public void setAlbum(AlbumContainer album) {
        this.album = album;
    }
}
