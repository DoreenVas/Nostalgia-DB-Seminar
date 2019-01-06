package Resources;

/**
 * Holds all of the artist query info the model can get from the controller.
 */
public class ArtistQueryInfo {
    private ArtistContainer artist;
    private GenreContainer genere;
    private AlbumIdContainer album;
    private SongIdContainer song;
    private FamiliarityContainer familiarity;
    private PopularityContainer popularity;

    public ArtistQueryInfo() {
        this.artist = null;
        this.genere = null;
        this.album = null;
        this.song = null;
        this.familiarity = null;
        this.popularity = null;
    }

    public PopularityContainer getPopularity() {
        return popularity;
    }

    public void setPopularity(PopularityContainer popularity) {
        this.popularity = popularity;
    }

    public FamiliarityContainer getFamiliarity() {
        return familiarity;
    }

    public void setFamiliarity(FamiliarityContainer familiarity) {
        this.familiarity = familiarity;
    }

    public AlbumIdContainer getAlbum() {
        return album;
    }

    public ArtistContainer getArtist() {
        return artist;
    }

    public GenreContainer getGenere() {
        return genere;
    }

    public SongIdContainer getSong() {
        return song;
    }

    public void setAlbum(AlbumIdContainer album) {
        this.album = album;
    }

    public void setArtist(ArtistContainer artist) {
        this.artist = artist;
    }

    public void setGenere(GenreContainer genere) {
        this.genere = genere;
    }

    public void setSong(SongIdContainer song) {
        this.song = song;
    }
}
