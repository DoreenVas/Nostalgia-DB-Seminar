package DBConnection;

import Resources.*;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class SongQueries {
    private static SongQueries ourInstance = new SongQueries();
    private static Statement myStatement;
    private static String[] allSongFields = {"song.song_id", "song.name", "song.dancibility", "song.duration", "song.tempo", "song.hotness",
            "song.loudness", "song.year"/*, "song.words"*/};
    private static final float tempoRate = (float)0.3;
    private static final int durationRate = 30;
    private static final float popularityRate = (float)0.0035;

    public static SongQueries getInstance(Statement statement) {
        myStatement = statement;
        return ourInstance;
    }

    private SongQueries() {

    }

    private String[] getSameLines(String[] first, String[] second) {
        ArrayList<String> newLines = new ArrayList<>();
        for(String row1 : first) {
            for(String row2 : second) {
                if(row1.equals(row2)) {
                    newLines.add(row2);
                    break;
                }
            }
        }
        return newLines.toArray(new String[0]);
    }

    /**
     * @param year        the wanted year
     * @return The songs from the given year
     * <p>
     * The function returns all of the songs from wanted year.
     */
    public DataContainer getSongs(int year) throws SQLException {
        String[] fields = {"*"};
        String[] tables = {"song"};
        QueryBuilder builder = new QueryBuilder(fields, tables);
        builder = builder.addWhere().addEqualStatements("year", year);
        String query = builder.build();
        String[] res = Executor.executeQuery(this.myStatement, query, allSongFields);
        String[] countField = {"count(*)"};
        int count = Integer.parseInt(Executor.executeQuery(this.myStatement, builder.addCount(query), countField)[0]);
        return new DataContainer(res, allSongFields, count);
    }

    /**
     * @param from        start year
     * @param to          end year
     * @return The songs between given years
     * <p>
     * The function returns all of the songs between the given years.
     */
    public DataContainer getSongs(int from, int to) throws SQLException {
        String[] fields = {"*"};
        String[] tables = {"song"};
        QueryBuilder builder = new QueryBuilder(fields, tables);
        builder = builder.addWhere().addBetweenStatements("year", from, to);
        String query = builder.build();
        String[] res = Executor.executeQuery(this.myStatement, query, allSongFields);
        String[] countField = {"count(*)"};
        int count = Integer.parseInt(Executor.executeQuery(this.myStatement, builder.addCount(query), countField)[0]);
        return new DataContainer(res, allSongFields, count);
    }

    // gets songs by genre
    public DataContainer getSongs(GenreContainer genre) throws SQLException {

        // needs checking!!!

        // original, only one genre
//        String query = "select * from song, " +
//                "(select distinct song_id from album_song, " +
//                "(select album_id from artist_album, " +
//                "(select artist_id from artist_genre, " +
//                "(select genre_id from genre " +
//                "where genre_name=\"" + genre.getValue()[0] + "\") as ID " +
//                "where artist_genre.genre_id=ID.genre_id) as AID " +
//                "where AID.artist_id=artist_album.artist_id) as RES " +
//                "where RES.album_id=album_song.album_id) as SID " +
//                "where SID.song_id=song.song_id";

        StringBuilder query = new StringBuilder("select * from song, " +
                "(select distinct song_id from album_song, " +
                "(select album_id from artist_album, " +
                "(select artist_id from artist_genre, " +
                "(select genre_id from genre " +
                "where ");
        String genres[] = genre.getValue();
        for (int i = 0; i < genres.length - 1; i++) {
            query.append("genre_name=\"").append(genres[i]).append("\" or ");
        }
        query.append("genre_name=\"").append(genres[genres.length - 1]).append("\"");
        query.append(") as ID " +
                "where artist_genre.genre_id=ID.genre_id) as AID " +
                "where AID.artist_id=artist_album.artist_id) as RES " +
                "where RES.album_id=album_song.album_id) as SID " +
                "where SID.song_id=song.song_id");

        // was testing
//        String query2 = "select * from song, album_song, artist_album, artist_genre, genre " +
//                "where genre.genre_name=\"" + genre.getValue()[0] + "\" and artist_genre.genre_id=genre.genre_id " +
//                "and artist_genre.artist_id=artist_album.artist_id " +
//                "and artist_album.album_id=album_song.album_id " +
//                "and album_song.song_id=song.song_id";
        String[] res = Executor.executeQuery(this.myStatement, query.toString(), allSongFields);
        int count = res.length;
        return new DataContainer(res,  allSongFields, count);
    }

    public DataContainer getLyrics(String songName) throws SQLException {
        String[] fields = {"*"};
        String[] tables = {"song"};
        QueryBuilder builder = new QueryBuilder(fields, tables);
        builder = builder.addWhere().addEqualStatements("name", "\"" + songName + "\"");
        String query = builder.build();
        String[] columns = {"words"};
        String[] res = Executor.executeQuery(this.myStatement, query, columns);
        String[] countField = {"count(*)"};
        int count = Integer.parseInt(Executor.executeQuery(this.myStatement, builder.addCount(query), countField)[0]);
        return new DataContainer(res, columns, count);
    }

    /**
     *
     * @param popularity the wanted popularity of the songs
     * @return all of the songs that are about as popular as given
     */
    public DataContainer getSongs(PopularityContainer popularity) throws SQLException{
        String[] fields = {"*"};
        String[] tables = {"song"};
        QueryBuilder builder = new QueryBuilder(fields, tables);
        builder = builder.addWhere().addBetweenStatements("hotness",
                popularity.getValue() - popularityRate, popularity.getValue() + popularityRate);
        String query = builder.build();
        String[] res = Executor.executeQuery(this.myStatement, query, allSongFields);
        String[] countField = {"count(*)"};
        int count = Integer.parseInt(Executor.executeQuery(this.myStatement, builder.addCount(query), countField)[0]);
        return new DataContainer(res, allSongFields, count);
    }

    // get songs by song name
    public DataContainer getSongs(String songName) throws SQLException {
        String[] fields = {"*"};
        String[] tables = {"song"};
        QueryBuilder builder = new QueryBuilder(fields, tables);
        builder = builder.addWhere().addEqualStatements("name", "\"" + songName + "\"");
        String query = builder.build();
        String[] res = Executor.executeQuery(this.myStatement, query, allSongFields);
        String[] countField = {"count(*)"};
        int count = Integer.parseInt(Executor.executeQuery(this.myStatement, builder.addCount(query), countField)[0]);
        return new DataContainer(res, allSongFields, count);

    }

    // get songs by tempo
    public DataContainer getSongs(TempoContainer tempo) throws SQLException {
        String[] fields = {"*"};
        String[] tables = {"song"};
        QueryBuilder builder = new QueryBuilder(fields, tables);
        float val = tempo.getValue();
        float epsilon = val * tempoRate;
        builder = builder.addWhere().addBetweenStatements("tempo", val - epsilon, val + epsilon);
        String query = builder.build();
        String[] res = Executor.executeQuery(this.myStatement, query, allSongFields);
        String[] countField = {"count(*)"};
        int count = Integer.parseInt(Executor.executeQuery(this.myStatement, builder.addCount(query), countField)[0]);
        return new DataContainer(res, allSongFields, count);
    }

    // get songs by artist
    public DataContainer getSongs(ArtistContainer artist) throws SQLException {
        String query = "select * from song, album_song, artist_album, artist " +
                "where artist.artist_name=\"" + artist.getValue() + "\" " +
                "and artist.artist_id=artist_album.artist_id " +
                "and artist_album.album_id=album_song.album_id " +
                "and album_song.song_id=song.song_id";
        String[] res = Executor.executeQuery(this.myStatement, query, allSongFields);
        int count = res.length;
        return new DataContainer(res, allSongFields, count);
    }

    // get songs by artist
    public DataContainer getSongs(SongContainer songName, ArtistContainer artist) throws SQLException {
        String query = "select distinct * from song, album_song, artist_album, artist " +
                "where artist.artist_name=\"" + artist.getValue() + "\" " +
                "and artist.artist_id=artist_album.artist_id " +
                "and artist_album.album_id=album_song.album_id " +
                "and album_song.song_id=song.song_id " +
                "and song.name=\"" + songName.getValue() + "\" ";
        String[] res = Executor.executeQuery(this.myStatement, query, allSongFields);
        int count = res.length;
        return new DataContainer(res, allSongFields, count);
    }

    // get songs by song length
    public DataContainer getSongs(DurationContainer duration) throws SQLException {
        String[] fields = {"*"};
        String[] tables = {"song"};
        QueryBuilder builder = new QueryBuilder(fields, tables);
        float value = duration.getValue();
        // get songs with given duration plus - minus an amount of time in relation to the given duration
//        float epsilon = value * (float)0.1;
        builder.addWhere().addBetweenStatements("duration", value - durationRate, value + durationRate);
        String query = builder.build();
        String[] res = Executor.executeQuery(this.myStatement, query, allSongFields);
        String[] countField = {"count(*)"};
        int count = Integer.parseInt(Executor.executeQuery(this.myStatement, builder.addCount(query), countField)[0]);
        return new DataContainer(res, allSongFields, count);
    }

    // get songs by genre, length
    public DataContainer getSongs(GenreContainer genre, DurationContainer duration) throws SQLException {
        StringBuilder query = new StringBuilder("select * from song, " +
                "(select distinct song_id from album_song, " +
                "(select album_id from artist_album, " +
                "(select artist_id from artist_genre, " +
                "(select genre_id from genre " +
                "where ");
        String genres[] = genre.getValue();
        for (int i = 0; i < genres.length - 1; i++) {
            query.append("genre_name=\"").append(genres[i]).append("\" or ");
        }
        query.append("genre_name=\"").append(genres[genres.length - 1]).append("\"");
        query.append(") as ID " +
                "where artist_genre.genre_id=ID.genre_id) as AID " +
                "where AID.artist_id=artist_album.artist_id) as RES " +
                "where RES.album_id=album_song.album_id) as SID " +
                "where SID.song_id=song.song_id and song.duration between " + duration.getValue() * 0.9 + " and " + duration.getValue() * 1.1);
        String[] res = Executor.executeQuery(this.myStatement, query.toString(), allSongFields);
        int count = res.length;
        return new DataContainer(res, allSongFields, count);
    }

    // get songs by genre, artist
    public DataContainer getSongs(GenreContainer genre, ArtistContainer artist) throws SQLException {
        StringBuilder query = new StringBuilder("select * from song, " +
                "(select distinct song_id from album_song, " +
                "(select album_id from artist_album, " +
                "(select artist_id from artist_genre, " +
                "(select genre_id from genre " +
                "where ");
        String genres[] = genre.getValue();
        for (int i = 0; i < genres.length - 1; i++) {
            query.append("genre_name=\"").append(genres[i]).append("\" or ");
        }
        query.append("genre_name=\"").append(genres[genres.length - 1]).append("\"");
        query.append(") as ID " +
                "where artist_genre.genre_id=ID.genre_id) as AID , artist " +
                "where AID.artist_id=artist.artist_id and artist.artist_name=\"" + artist.getValue() + "\" " +
                "and AID.artist_id=artist_album.artist_id) as RES " +
                "where RES.album_id=album_song.album_id) as SID " +
                "where SID.song_id=song.song_id");
        String[] res = Executor.executeQuery(this.myStatement, query.toString(), allSongFields);
        int count = res.length;
        return new DataContainer(res, allSongFields, count);
    }

    // get songs by tempo, length
    public DataContainer getSongs(TempoContainer tempo, DurationContainer duration) throws SQLException {
        String[] fields = {"*"};
        String[] tables = {"song"};
        QueryBuilder builder = new QueryBuilder(fields, tables);
        float durationValue = duration.getValue();
        float tempoValue = tempo.getValue();
        // get songs with given duration plus - minus an amount of time in relation to the given duration
        float epsilonTempo = tempoValue * tempoRate;
        builder.addWhere().addBetweenStatements("duration", durationValue - durationRate, durationValue + durationRate);
        builder.addWhere().addBetweenStatements("tempo",tempoValue -epsilonTempo,tempoValue+epsilonTempo);
        String query = builder.build();
        String[] res = Executor.executeQuery(this.myStatement, query, allSongFields);
        String[] countField = {"count(*)"};
        int count = Integer.parseInt(Executor.executeQuery(this.myStatement, builder.addCount(query), countField)[0]);
        return new DataContainer(res, allSongFields, count);
    }

    // get songs by tempo, length, artist
    public DataContainer getSongs(TempoContainer tempo, DurationContainer duration, ArtistContainer artist) throws SQLException {
        String[] fields = allSongFields;
        String[] tables = {"song","artist","artist_album","album_song"};
        QueryBuilder builder = new QueryBuilder(fields, tables, true);
        float durationValue = duration.getValue();
        float tempoValue = tempo.getValue();
        // get songs with given duration plus - minus an amount of time in relation to the given duration
        float epsilonTempo = tempoValue * tempoRate;
        builder.addWhere().addEqualStatements("artist.artist_id","artist_album.artist_id");
        builder.addEqualStatements("artist_album.album_id","album_song.album_id");
        builder.addEqualStatements("album_song.song_id","song.song_id");
        builder.addBetweenStatements("duration", durationValue - durationRate, durationValue + durationRate);
        builder.addBetweenStatements("tempo",tempoValue -epsilonTempo,tempoValue+epsilonTempo);
        builder.addEqualStatements("artist.artist_name", "\"" + artist.getValue() + "\"");
        String query = builder.build();
        String[] res = Executor.executeQuery(this.myStatement, query, allSongFields);
        String[] countField = {"count(*)"};
        int count = Integer.parseInt(Executor.executeQuery(this.myStatement, builder.addCount(query), countField)[0]);
        return new DataContainer(res, allSongFields, count);
    }

    public DataContainer getSongs(GenreContainer genre, PopularityContainer popularity, TempoContainer tempo,
                                  DurationContainer duration, ArtistContainer artist) throws SQLException {
        DataContainer genreResult = getSongs(genre);
        DataContainer popularityResult = getSongs(popularity);
        DataContainer tempoResult = getSongs(tempo);
        DataContainer durationResult = getSongs(duration);
        DataContainer artistResult = getSongs(artist);
        String[] data = getSameLines(genreResult.getData(), popularityResult.getData());
        data = getSameLines(data, tempoResult.getData());
        data = getSameLines(data, durationResult.getData());
        data = getSameLines(data, artistResult.getData());
        return new DataContainer(data, genreResult.getColumns(), data.length);
    }
}
