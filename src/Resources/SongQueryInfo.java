package Resources;


/**
 * Holds all of the song query info the model can get from the controller.
 */
public class SongQueryInfo {
    // members
    private int year;
    private int from;
    private int to;
    private GenreContainer genre;
    private TempoContainer tempo;
    private PopularityContainer popularity;
    private DurationContainer duration;
    private ArtistContainer artist;
    private AlbumIdContainer album;
    private LyricsContainer lyrics;

    /***
     * Constructor
     */
    public SongQueryInfo() {
        this.year = -1;
        this.from = -1;
        this.to = -1;
        this.genre = null;
        this.tempo = null;
        this.artist = null;
        this.popularity = null;
        this.duration = null;
        this.album = null;
        this.lyrics = null;
    }

    /***
     * returns the year of the song
     * @return the year of the song
     */
    public int getYear() {
        return year;
    }

    /***
     * sets the year of the song
     * @param year the new year of the song
     */
    public void setYear(int year) {
        this.year = year;
    }

    /***
     * returns the year of start
     * @return the year of start
     */
    public int getFrom() {
        return from;
    }

    /***
     * sets the year of start
     * @param from the new year of start
     */
    public void setFrom(int from) {
        this.from = from;
    }

    /***
     * returns the year of end
     * @return the year of end
     */
    public int getTo() {
        return to;
    }

    /***
     * sets the year of the end
     * @param to the new year of the end
     */
    public void setTo(int to) {
        this.to = to;
    }

    /***
     * returns the genres of the artist
     * @return the genres of the artist
     */
    public GenreContainer getGenre() {
        return genre;
    }

    /***
     * sets the genres of the artist
     * @param genre the genres of the artist
     */
    public void setGenere(GenreContainer genre) {
        this.genre = genre;
    }

    /***
     * returns the tempo of the song
     * @return the tempo of the song
     */
    public TempoContainer getTempo() {
        return tempo;
    }

    /***
     * sets the tempo of the song
     * @param tempo the new tempo of the song
     */
    public void setTempo(TempoContainer tempo) {
        this.tempo = tempo;
    }

    /***
     * returns the popularity of the artist
     * @return the popularity of the artist
     */
    public PopularityContainer getPopularity() {
        return popularity;
    }

    /***
     * sets the popularity of the artist
     * @param popularity the new popularity of the artist
     */
    public void setPopularity(PopularityContainer popularity) {
        this.popularity = popularity;
    }

    /***
     * returns the duration of the song
     * @return the duration of the song
     */
    public DurationContainer getDuration() {
        return duration;
    }

    /***
     * sets the duration of the song
     * @param duration the new duration of the song
     */
    public void setDuration(DurationContainer duration) {
        this.duration = duration;
    }

    /***
     * returns the artist
     * @return the artist container
     */
    public ArtistContainer getArtist() {
        return artist;
    }

    /***
     * sets the artist
     * @param artist the new artist container
     */
    public void setArtist(ArtistContainer artist) {
        this.artist = artist;
    }

    /***
     * returns the album
     * @return the album container
     */
    public AlbumIdContainer getAlbum() {
        return album;
    }

    /***
     * sets the album
     * @param album the new album container
     */
    public void setAlbum(AlbumIdContainer album) {
        this.album = album;
    }

    /***
     * returns the lyrics of the song
     * @return the lyrics of the song
     */
    public LyricsContainer getLyrics() {
        return lyrics;
    }

    /***
     * sets the lyrics of the song
     * @param lyrics the new lyrics of the song
     */
    public void setLyrics(LyricsContainer lyrics) {
        this.lyrics = lyrics;
    }
}