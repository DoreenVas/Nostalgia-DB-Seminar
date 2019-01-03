package Resources;

public class QueryInfo {
    private int year;
    private int from;
    private int to;
    private GenreContainer genere;
    private TempoContainer tempo;
    private PopularityContainer popularity;
    private DurationContainer duration;
    private ArtistContainer artist;
    private AlbumContainer album;
    private LyricsContainer lyrics;
    private HotnessContainer hotness;

    public QueryInfo() {
        this.year = -1;
        this.from = -1;
        this.to = -1;
        this.genere = null;
        this.tempo = null;
        this.artist = null;
        this.popularity = null;
        this.duration = null;
        this.album = null;
        this.lyrics = null;
        this.hotness = null;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getFrom() {
        return from;
    }

    public void setFrom(int from) {
        this.from = from;
    }

    public int getTo() {
        return to;
    }

    public void setTo(int to) {
        this.to = to;
    }

    public GenreContainer getGenere() {
        return genere;
    }

    public void setGenere(GenreContainer genere) {
        this.genere = genere;
    }

    public TempoContainer getTempo() {
        return tempo;
    }

    public void setTempo(TempoContainer tempo) {
        this.tempo = tempo;
    }

    public PopularityContainer getPopularity() {
        return popularity;
    }

    public void setPopularity(PopularityContainer popularity) {
        this.popularity = popularity;
    }

    public DurationContainer getDuration() {
        return duration;
    }

    public void setDuration(DurationContainer duration) {
        this.duration = duration;
    }

    public ArtistContainer getArtist() {
        return artist;
    }

    public void setArtist(ArtistContainer artist) {
        this.artist = artist;
    }

    public AlbumContainer getAlbum() {
        return album;
    }

    public void setAlbum(AlbumContainer album) {
        this.album = album;
    }

    public LyricsContainer getLyrics() {
        return lyrics;
    }

    public void setLyrics(LyricsContainer lyrics) {
        this.lyrics = lyrics;
    }

    public HotnessContainer getHotness() {
        return hotness;
    }

    public void setHotness(HotnessContainer hotness) {
        this.hotness = hotness;
    }
}