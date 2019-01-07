package Resources;

/**
 * Holds all of the artist query info the model can get from the controller.
 */
public class ArtistQueryInfo {
    // members
    private ArtistContainer artist;
    private GenreContainer genere;
    private AlbumIdContainer album;
    private SongIdContainer song;
    private FamiliarityContainer familiarity;
    private PopularityContainer popularity;

    /***
     * Constructor
     */
    public ArtistQueryInfo() {
        this.artist = null;
        this.genere = null;
        this.album = null;
        this.song = null;
        this.familiarity = null;
        this.popularity = null;
    }

    /***
     * returns the popularity rate of the artist
     * @return the popularity rate
     */
    public PopularityContainer getPopularity() {
        return popularity;
    }

    public void setPopularity(PopularityContainer popularity) {
        this.popularity = popularity;
    }

    /***
     * returns the familiarity rate of the artist
     * @return the familiarity rate
     */
    public FamiliarityContainer getFamiliarity() {
        return familiarity;
    }

    /***
     * sets the familiarity rate of the artist
     * @param familiarity the new familiarity rate
     */
    public void setFamiliarity(FamiliarityContainer familiarity) {
        this.familiarity = familiarity;
    }

    /***
     * returns the album of the artist
     * @return the album name
     */
    public AlbumIdContainer getAlbum() {
        return album;
    }

    /***
     * returns the artist
     * @return the artist name
     */
    public ArtistContainer getArtist() {
        return artist;
    }

    /***
     * returns the genres of the artist
     * @return the genres list
     */
    public GenreContainer getGenere() {
        return genere;
    }

    /***
     * returns the song id of the artist
     * @return the song id
     */
    public SongIdContainer getSong() {
        return song;
    }

    /***
     * sets the album name for the current artist
     * @param album an album container
     */
    public void setAlbum(AlbumIdContainer album) {
        this.album = album;
    }

    /****
     * sets the artist name for the current artist
     * @param artist an artist container
     */
    public void setArtist(ArtistContainer artist) {
        this.artist = artist;
    }

    /****
     * sets the genre list for the current artist
     * @param genre a genre container
     */
    public void setGenere(GenreContainer genre) {
        this.genere = genre;
    }

    /****
     * sets the song for the current artist
     * @param song a song container
     */
    public void setSong(SongIdContainer song) {
        this.song = song;
    }
}
