package Resources;

public class ArtistQueryInfo {
    private ArtistContainer artist;
    private GenreContainer genere;
    private AlbumContainer album;
    private SongContainer song;

    public ArtistQueryInfo() {
        artist = null;
        genere = null;
        album = null;
        song = null;
    }

    public AlbumContainer getAlbum() {
        return album;
    }

    public ArtistContainer getArtist() {
        return artist;
    }

    public GenreContainer getGenere() {
        return genere;
    }

    public SongContainer getSong() {
        return song;
    }

    public void setAlbum(AlbumContainer album) {
        this.album = album;
    }

    public void setArtist(ArtistContainer artist) {
        this.artist = artist;
    }

    public void setGenere(GenreContainer genere) {
        this.genere = genere;
    }

    public void setSong(SongContainer song) {
        this.song = song;
    }
}
