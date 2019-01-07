package Resources;

/**
 * Holds all of the album query info the model can get from the controller.
 */
public class AlbumQueryInfo {
    // members
    private AlbumIdContainer album;
    private SongIdContainer song;
    private ArtistContainer artist;

    /***
     * Constructor
     */
    public AlbumQueryInfo() {
        album = null;
        song = null;
        artist = null;
    }

    /***
     * returns the song id
     * @return song id container
     */
    public SongIdContainer getSong() {
        return song;
    }

    /***
     * returns the artist item
     * @return artist container
     */
    public ArtistContainer getArtist() {
        return artist;
    }

    /***
     * returns the album id
     * @return album id container
     */
    public AlbumIdContainer getAlbum() {
        return album;
    }

    /***
     * sets the song id
     * @param song a songId container
     */
    public void setSong(SongIdContainer song) {
        this.song = song;
    }

    /***
     * sets the artist id
     * @param artist a artistId container
     */
    public void setArtist(ArtistContainer artist) {
        this.artist = artist;
    }

    /***
     * sets the album id
     * @param album a albumId container
     */
    public void setAlbum(AlbumIdContainer album) {
        this.album = album;
    }
}
