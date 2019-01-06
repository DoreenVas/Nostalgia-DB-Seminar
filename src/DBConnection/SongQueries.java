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

    /**
     *
     * Gets the class instance
     * @param statement the statement to use for executing queries
     * @return the class instance
     */
    public static SongQueries getInstance(Statement statement) {
        myStatement = statement;
        return ourInstance;
    }

    /**
     *
     * Returns the elements who are the same in the two given arrays.
     * @param first the first array
     * @param second the second array
     * @return the elements who are the same in the two given arrays.
     */
    private String[] getSameLines(String[] first, String[] second) {
        ArrayList<String> newLines = new ArrayList<>();
        // go over the firsts arrays elements
        for(String row1 : first) {
            // go over the second arrays elements
            for(String row2 : second) {
                // compare the elements
                if(row1.equals(row2)) {
                    newLines.add(row2);
                    break;
                }
            }
        }
        // create a new array with the elements from the list and return it
        return newLines.toArray(new String[0]);
    }

    /**
     *
     * Adds "and" to a query string if needed.
     * @param first indicates if "and" needs to be inserted.
     * @param songConditions the query built
     * @return the new query with an "and" attached if needed
     */
    public static boolean needToAddAnd(boolean first, StringBuilder songConditions) {
        if(!first) {
            songConditions.append(" and ");
        }
        return first;
    }

    /**
     *
     * The function returns all of the songs from wanted year.
     * @param year the wanted year
     * @return The songs from the given year
     */
    public DataContainer getSongs(int year) throws SQLException {
        String[] fields = {"*"};
        String[] tables = {"song"};
        // initialize query builder
        QueryBuilder builder = new QueryBuilder(fields, tables);
        builder = builder.addWhere().addEqualStatements("year", year);
        String query = builder.build();
        // execute the query
        String[] res = Executor.executeQuery(myStatement, query, allSongFields);
        String[] countField = {"count(*)"};
        // execute count query on previous query
        int count = Integer.parseInt(Executor.executeQuery(myStatement, builder.addCount(query), countField)[0]);
        return new DataContainer(res, allSongFields, count);
    }

    /**
     *
     * The function returns all of the songs between the given years.
     * @param from start year
     * @param to end year
     * @return The songs between given years
     */
    public DataContainer getSongs(int from, int to) throws SQLException {
        String[] fields = {"*"};
        String[] tables = {"song"};
        // initialize query builder
        QueryBuilder builder = new QueryBuilder(fields, tables);
        builder = builder.addWhere().addBetweenStatements("year", from, to);
        String query = builder.build();
        // execute the query
        String[] res = Executor.executeQuery(myStatement, query, allSongFields);
        String[] countField = {"count(*)"};
        // execute count query on previous query
        int count = Integer.parseInt(Executor.executeQuery(myStatement, builder.addCount(query), countField)[0]);
        return new DataContainer(res, allSongFields, count);
    }

    /**
     * Gets all of the songs that apply to the given genre and song conditions.
     * @param genre the wanted genres
     * @param songConditions the wanted conditions
     * @return the songs that the criteria applies to
     * @throws SQLException throws exception if there was a problem executing the query
     */
    public DataContainer getSongs(GenreContainer genre, String songConditions) throws SQLException {
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

        // initialize query builder
        StringBuilder query = new StringBuilder("select * from song, " +
                "(select distinct song_id from album_song, " +
                "(select album_id from artist_album, " +
                "(select artist_id from artist_genre, " +
                "(select genre_id from genre " +
                "where ");
        // add all genres from he form
        String genres[] = genre.getValue();
        for (int i = 0; i < genres.length - 1; i++) {
            query.append("genre_name=\"").append(genres[i]).append("\" or ");
        }
        query.append("genre_name=\"").append(genres[genres.length - 1]).append("\"");
        // add the rest of the query
        query.append(") as ID " +
                "where artist_genre.genre_id=ID.genre_id) as AID " +
                "where AID.artist_id=artist_album.artist_id) as RES " +
                "where RES.album_id=album_song.album_id) as SID " +
                "where SID.song_id=song.song_id");
        if(!songConditions.equals("")) {
            query.append(" and ").append(songConditions);
        }
        // execute the query
        String[] res = Executor.executeQuery(myStatement, query.toString(), allSongFields);
        int count = res.length;
        return new DataContainer(res,  allSongFields, count);
    }

    /**
     *
     * Gets a given songs lyrics.
     * @param song the wanted song
     * @return the lyrics of the wanted song
     * @throws SQLException throws exception if there was a problem executing the query
     */
    public DataContainer getLyrics(LyricsContainer song) throws SQLException {
        String[] fields = {"*"};
        String[] tables = {"song"};
        // initialize query builder
        QueryBuilder builder = new QueryBuilder(fields, tables);
        builder = builder.addWhere().addEqualStatements("song_id", song.getValue());
        String query = builder.build();
        String[] columns = {"words"};
        // execute teh query
        String[] res = Executor.executeQuery(myStatement, query, columns);
        res = lyricsCombiner(res);
        String[] countField = {"count(*)"};
        // execute count query on previous query
        int count = Integer.parseInt(Executor.executeQuery(myStatement, builder.addCount(query), countField)[0]);
        return new DataContainer(res, columns, count);
    }

    /**
     *
     * Combines a songs lyrics to one string.
     * @param res the result from the lyrics query
     * @return the combined lyrics
     */
    private String[] lyricsCombiner(String[] res) {
        String[] words = new String[1];
        words[0] = "";
        // go over the result
        for(int i = 0; i < res.length; i++) {
            words[0] += res[i] + "\n";
        }
        return words;
    }

    /**
     *
     * Gets all of the songs that apply to the given popularity.
     * @param popularity the wanted popularity of the songs
     * @return all of the songs that are about as popular as given
     */
    public DataContainer getSongs(PopularityContainer popularity) throws SQLException{
        String[] fields = {"*"};
        String[] tables = {"song"};
        // initialize query builder
        QueryBuilder builder = new QueryBuilder(fields, tables);
        builder = builder.addWhere().addBetweenStatements("hotness",
                popularity.getValue() - popularityRate, popularity.getValue() + popularityRate);
        String query = builder.build();
        // execute the query
        String[] res = Executor.executeQuery(myStatement, query, allSongFields);
        String[] countField = {"count(*)"};
        // execute count query on previous query
        int count = Integer.parseInt(Executor.executeQuery(myStatement, builder.addCount(query), countField)[0]);
        return new DataContainer(res, allSongFields, count);
    }

    /**
     *
     * Gets the song that applies to the given song id.
     * @param song the wanted song
     * @return the song that has the given id
     * @throws SQLException throws exception if there was a problem executing the query
     */
    public DataContainer getSongs(SongIdContainer song) throws SQLException {
        String[] fields = {"*"};
        String[] tables = {"song"};
        // initialize query builder
        QueryBuilder builder = new QueryBuilder(fields, tables);
        builder = builder.addWhere().addEqualStatements("name", "\"" + song.getValue() + "\"");
        String query = builder.build();
        // execute the query
        String[] res = Executor.executeQuery(myStatement, query, allSongFields);
        String[] countField = {"count(*)"};
        // execute count query on previous query
        int count = Integer.parseInt(Executor.executeQuery(myStatement, builder.addCount(query), countField)[0]);
        return new DataContainer(res, allSongFields, count);

    }

    /**
     *
     * Gets all of the songs that apply to the given tempo.
     * @param tempo the wanted tempo of the songs
     * @return all of the songs that have a tempo as a function of the tempo
     */
    public DataContainer getSongs(TempoContainer tempo) throws SQLException {
        String[] fields = {"*"};
        String[] tables = {"song"};
        // initialize query builder
        QueryBuilder builder = new QueryBuilder(fields, tables);
        float val = tempo.getValue();
        float epsilon = val * tempoRate;
        builder = builder.addWhere().addBetweenStatements("tempo", val - epsilon, val + epsilon);
        String query = builder.build();
        // execute the query
        String[] res = Executor.executeQuery(myStatement, query, allSongFields);
        String[] countField = {"count(*)"};
        // execute count query on previous query
        int count = Integer.parseInt(Executor.executeQuery(myStatement, builder.addCount(query), countField)[0]);
        return new DataContainer(res, allSongFields, count);
    }

    /**
     *
     * Gets all of the songs that are from the given artist and apply to the given conditions.
     * @param artist the wanted artist
     * @param songConditions the song conditions
     * @return the songs that the criteria applies to
     * @throws SQLException throws exception if there was a problem executing the query
     */
    public DataContainer getSongs(ArtistContainer artist, String songConditions) throws SQLException {
        // initialize the query
        String query = "select * from song, album_song, artist_album, artist " +
                "where artist.artist_name=\"" + artist.getValue() + "\" " +
                "and artist.artist_id=artist_album.artist_id " +
                "and artist_album.album_id=album_song.album_id " +
                "and album_song.song_id=song.song_id";
        if(!songConditions.equals("")) {
            query += " and " + songConditions;
        }
        // execute the query
        String[] res = Executor.executeQuery(myStatement, query, allSongFields);
        int count = res.length;
        return new DataContainer(res, allSongFields, count);
    }

    /**
     *
     * Gets the wanted song with the given artist.
     * @param song the wanted song
     * @param artist the wanted artist
     * @return the songs that the criteria applies to
     * @throws SQLException throws exception if there was a problem executing the query
     */
    public DataContainer getSongs(SongIdContainer song, ArtistContainer artist) throws SQLException {
        // initialize the query
        String query = "select distinct * from song, album_song, artist_album, artist " +
                "where artist.artist_name=\"" + artist.getValue() + "\" " +
                "and artist.artist_id=artist_album.artist_id " +
                "and artist_album.album_id=album_song.album_id " +
                "and album_song.song_id=song.song_id " +
                "and song.name=\"" + song.getValue() + "\" ";
        // execute the query
        String[] res = Executor.executeQuery(myStatement, query, allSongFields);
        int count = res.length;
        return new DataContainer(res, allSongFields, count);
    }

    /**
     *
     * Gets all of the songs with the given duration.
     * @param duration the wanted song duration
     * @return the songs that the criteria applies to
     * @throws SQLException throws exception if there was a problem executing the query
     */
    DataContainer getSongs(DurationContainer duration) throws SQLException {
        String[] fields = {"*"};
        String[] tables = {"song"};
        // initialize query builder
        QueryBuilder builder = new QueryBuilder(fields, tables);
        float value = duration.getValue();
        // get songs with given duration plus - minus an amount of time in relation to the given duration
//        float epsilon = value * (float)0.1;
        builder.addWhere().addBetweenStatements("duration", value - durationRate, value + durationRate);
        String query = builder.build();
        // execute the query
        String[] res = Executor.executeQuery(myStatement, query, allSongFields);
        String[] countField = {"count(*)"};
        // execute count query on previous query
        int count = Integer.parseInt(Executor.executeQuery(myStatement, builder.addCount(query), countField)[0]);
        return new DataContainer(res, allSongFields, count);
    }

    /**
     *
     * Gets all of the songs that are from the given genre and have the given duration.
     * @param genre the wanted genres
     * @param duration the wanted song duration
     * @return the songs that the criteria applies to
     * @throws SQLException throws exception if there was a problem executing the query
     */
    DataContainer getSongs(GenreContainer genre, DurationContainer duration) throws SQLException {
        // initialize the query
        String songQuery = "song.duration between " + (duration.getValue() - durationRate) + " and " +
                (duration.getValue() + durationRate);
        StringBuilder query = attachGenreQueryToSongQuery(songQuery, genre);
        // execute the query
        String[] res = Executor.executeQuery(myStatement, query.toString(), allSongFields);
        int count = res.length;
        return new DataContainer(res, allSongFields, count);
    }

    /**
     *
     * Attaches given song conditions to a genre query.
     * @param songQuery the song conditions
     * @param genre the wanted genres
     * @return the new query with the song conditions
     */
    private StringBuilder attachGenreQueryToSongQuery(String songQuery, GenreContainer genre) {
        // initialize the query
        StringBuilder query = new StringBuilder("select * from song, " +
                "(select distinct song_id from album_song, " +
                "(select album_id from artist_album, " +
                "(select artist_id from artist_genre, " +
                "(select genre_id from genre " +
                "where ");
        // add the genres from the form
        String genres[] = genre.getValue();
        for (int i = 0; i < genres.length - 1; i++) {
            query.append("genre_name=\"").append(genres[i]).append("\" or ");
        }
        query.append("genre_name=\"").append(genres[genres.length - 1]).append("\"");
        // add the rest of the query
        query.append(") as ID " +
                "where artist_genre.genre_id=ID.genre_id) as AID " +
                "where AID.artist_id=artist_album.artist_id) as RES " +
                "where RES.album_id=album_song.album_id) as SID " +
                "where SID.song_id=song.song_id and ");
        query.append(songQuery);
        return query;
    }

    /**
     *
     * Gets all of the songs that apply to the given artist, album and conditions.
     * @param artist the wanted artist
     * @param album the wanted album
     * @param songConditions the song conditions
     * @return the songs that the criteria applies to
     * @throws SQLException throws exception if there was a problem executing the query
     */
    DataContainer getSongs(ArtistContainer artist, AlbumIdContainer album, String songConditions) throws SQLException {
        // initialize the query
        String query = "select * from song, album_song, artist_album, artist, album " +
                "where artist.artist_name=\"" + artist.getValue() + "\" " +
                "and artist.artist_id=artist_album.artist_id " +
                "and artist_album.album_id=album_song.album_id " +
                "and album.album_name=\"" + album.getValue() + "\" " +
                "and album_song.song_id=song.song_id";
        if(!songConditions.equals("")) {
            query += " and " + songConditions;
        }
        // execute the query
        String[] res = Executor.executeQuery(myStatement, query, allSongFields);
        int count = res.length;
        return new DataContainer(res, allSongFields, count);
    }

    /**
     *
     * Gets all of the songs that apply to the given album and conditions.
     * @param album the wanted album
     * @param songConditions the song conditions
     * @return the songs that the criteria applies to
     * @throws SQLException throws exception if there was a problem executing the query
     */
    DataContainer getSongs(AlbumIdContainer album, String songConditions) throws SQLException {
        // initialize the query
        String query = "select * from song, album_song, album " +
                "where album.album_id=album_song.album_id " +
                "and album.album_name=\"" + album.getValue() + "\" " +
                "and album_song.song_id=song.song_id";
        if(!songConditions.equals("")) {
            query += " and " + songConditions;
        }
        // execute the query
        String[] res = Executor.executeQuery(myStatement, query, allSongFields);
        int count = res.length;
        return new DataContainer(res, allSongFields, count);
    }

    /**
     *
     * Gets all of the songs that apply to the given conditions.
     * @param songConditions the song conditions
     * @return the songs that the criteria applies to
     * @throws SQLException throws exception if there was a problem executing the query
     */
    DataContainer getSongs(String songConditions) throws SQLException {
        // initialize the query
        String query = "select * from song";
        if(!songConditions.equals("")) {
            query += " where " + songConditions;
        }
        // execute the query
        String[] res = Executor.executeQuery(myStatement, query, allSongFields);
        int count = res.length;
        return new DataContainer(res, allSongFields, count);
    }

    DataContainer getSongs(int year, GenreContainer genre, DurationContainer duration) throws SQLException {
        // initialize the query
        String songQuery = "song.year=" + year + " and " +
                "song.duration between " + (duration.getValue() - durationRate) + " and " +
                (duration.getValue() + durationRate);
        // add genres to the query
        StringBuilder query = attachGenreQueryToSongQuery(songQuery, genre);
        // execute the query
        String[] res = Executor.executeQuery(myStatement, query.toString(), allSongFields);
        int count = res.length;
        return new DataContainer(res, allSongFields, count);
    }

    DataContainer getSongs(int from, int to, GenreContainer genre, DurationContainer duration) throws SQLException {
        // initialize the query
        String songQuery = "song.year between " + from + " and " + to + " and " +
                "song.duration between " + (duration.getValue() - durationRate) + " and " +
                (duration.getValue() + durationRate);
        // add genres to the query
        StringBuilder query = attachGenreQueryToSongQuery(songQuery, genre);
        // execute the query
        String[] res = Executor.executeQuery(myStatement, query.toString(), allSongFields);
        int count = res.length;
        return new DataContainer(res, allSongFields, count);
    }

    /**
     *
     * Gets all of the songs with the given genres, artist and song conditions.
     * @param genre the wanted genres
     * @param artist the wanted artist
     * @param songConditions the wanted conditions
     * @return the songs that the criteria applies to
     * @throws SQLException throws exception if there was a problem executing the query
     */
    DataContainer getSongs(GenreContainer genre, ArtistContainer artist,
                                  String songConditions) throws SQLException {
        // initialize the query
        StringBuilder query = new StringBuilder("select * from song, " +
                "(select distinct song_id from album_song, " +
                "(select album_id from artist_album, " +
                "(select artist_id from artist_genre, " +
                "(select genre_id from genre " +
                "where ");
        // add genres from the form
        String genres[] = genre.getValue();
        for (int i = 0; i < genres.length - 1; i++) {
            query.append("genre_name=\"").append(genres[i]).append("\" or ");
        }
        query.append("genre_name=\"").append(genres[genres.length - 1]).append("\"");
        // add the rest of the query
        query.append(") as ID " +
                "where artist_genre.genre_id=ID.genre_id) as AID , artist " +
                "where AID.artist_id=artist.artist_id and artist.artist_name=\"" + artist.getValue() + "\" " +
                "and AID.artist_id=artist_album.artist_id) as RES " +
                "where RES.album_id=album_song.album_id) as SID " +
                "where SID.song_id=song.song_id");
        if(!songConditions.equals("")) {
            query.append(" and ").append(songConditions);
        }
        // execute the query
        String[] res = Executor.executeQuery(myStatement, query.toString(), allSongFields);
        int count = res.length;
        return new DataContainer(res, allSongFields, count);
    }

    // get songs by genre, artist
//    public DataContainer getSongs(GenreContainer genre, ArtistContainer artist,
//                                  AlbumIdContainer album) throws SQLException {
//        StringBuilder query = new StringBuilder("select * from song, album, genre, artist, artist_album," +
//                " album_song, artist_genre " +
//                "where ");
//        String genres[] = genre.getValue();
//        for (int i = 0; i < genres.length - 1; i++) {
//            query.append("genre_name=\"").append(genres[i]).append("\" or ");
//        }
//        query.append("genre_name=\"").append(genres[genres.length - 1]).append("\"");
//        query.append(" and artist_genre.genre_id=genre.genre_id " +
//                "and artist_genre.artist_id=artist.artist_id " +
//                "and artist.artist_name=\"" + artist.getValue() + "\" " +
//                "and artist.artist_id=artist_album.artist_id " +
//                "and album.album_name=\"" + album.getValue() + "\" " +
//                "and album.album_id=album_song.album_id " +
//                "and album_song.song_id=song.song_id");
//        String[] res = Executor.executeQuery(myStatement, query.toString(), allSongFields);
//        int count = res.length;
//        return new DataContainer(res, allSongFields, count);
//    }

    /**
     *
     * Gets all of the songs that are from the given genre, by the given artist,
     * from the given album and apply to the given conditions.
     * @param genre the wanted genres
     * @param artist the wanted artist
     * @param album the wanted album
     * @param songConditions the song conditions
     * @return the songs that the criteria applies to
     * @throws SQLException throws exception if there was a problem executing the query
     */
    DataContainer getSongs(GenreContainer genre, ArtistContainer artist,
                           AlbumIdContainer album, String songConditions) throws SQLException {
        // initialize the query
        StringBuilder query = new StringBuilder("select * from song, " +
                "(select distinct song_id from album_song, " +
                "(select artist_album.album_id from artist_album, album, " +
                "(select artist_id from artist_genre, " +
                "(select genre_id from genre " +
                "where ");
        // add the genres from the form
        String genres[] = genre.getValue();
        for (int i = 0; i < genres.length - 1; i++) {
            query.append("genre_name=\"").append(genres[i]).append("\" or ");
        }
        query.append("genre_name=\"").append(genres[genres.length - 1]).append("\"");
        // add the rest of the query
        query.append(") as ID " +
                "where artist_genre.genre_id=ID.genre_id) as AID , artist " +
                "where AID.artist_id=artist.artist_id and artist.artist_name=\"" + artist.getValue() + "\" " +
                "and AID.artist_id=artist_album.artist_id " +
                "and album.album_name=\"" + album.getValue() + "\"" +
                ") as RES " +
                "where RES.album_id=album_song.album_id) as SID " +
                "where SID.song_id=song.song_id");
        if(!songConditions.equals("")) {
            query.append(" and ").append(songConditions);
        }
        // execute the query
        String[] res = Executor.executeQuery(myStatement, query.toString(), allSongFields);
        int count = res.length;
        return new DataContainer(res, allSongFields, count);
    }

    /**
     *
     * Gets all of the songs with the given tempo and duration.
     * @param tempo the wanted tempo
     * @param duration the wanted duration
     * @return the songs that the criteria applies to
     * @throws SQLException throws exception if there was a problem executing the query
     */
    DataContainer getSongs(TempoContainer tempo, DurationContainer duration) throws SQLException {
        String[] fields = {"*"};
        String[] tables = {"song"};
        // initialize query builder
        QueryBuilder builder = new QueryBuilder(fields, tables);
        float durationValue = duration.getValue();
        float tempoValue = tempo.getValue();
        // get songs with given duration plus - minus an amount of time in relation to the given duration
        float epsilonTempo = tempoValue * tempoRate;
        builder.addWhere().addBetweenStatements("duration", durationValue - durationRate,
                durationValue + durationRate);
        builder.addWhere().addBetweenStatements("tempo",tempoValue -epsilonTempo,
                tempoValue+epsilonTempo);
        String query = builder.build();
        // execute the query
        String[] res = Executor.executeQuery(myStatement, query, allSongFields);
        String[] countField = {"count(*)"};
        // execute count query on previous query
        int count = Integer.parseInt(Executor.executeQuery(myStatement, builder.addCount(query), countField)[0]);
        return new DataContainer(res, allSongFields, count);
    }

    /**
     *
     * Gets all of the songs with the given tempo, duration and artist.
     * @param tempo the wanted tempo
     * @param duration the wanted duration
     * @param artist the wanted artist
     * @return the songs that the criteria applies to
     * @throws SQLException throws exception if there was a problem executing the query
     */
    DataContainer getSongs(TempoContainer tempo, DurationContainer duration,
                           ArtistContainer artist) throws SQLException {
        String[] fields = allSongFields;
        String[] tables = {"song","artist","artist_album","album_song"};
        // initialize query builder
        QueryBuilder builder = new QueryBuilder(fields, tables, true);
        float durationValue = duration.getValue();
        float tempoValue = tempo.getValue();
        // get songs with given duration plus - minus an amount of time in relation to the given duration
        float epsilonTempo = tempoValue * tempoRate;
        // add the rest of the query
        builder.addWhere().addEqualStatements("artist.artist_id","artist_album.artist_id");
        builder.addEqualStatements("artist_album.album_id","album_song.album_id");
        builder.addEqualStatements("album_song.song_id","song.song_id");
        builder.addBetweenStatements("duration", durationValue - durationRate,
                durationValue + durationRate);
        builder.addBetweenStatements("tempo",tempoValue -epsilonTempo,tempoValue+epsilonTempo);
        builder.addEqualStatements("artist.artist_name", "\"" + artist.getValue() + "\"");
        String query = builder.build();
        // execute the query
        String[] res = Executor.executeQuery(this.myStatement, query, allSongFields);
        String[] countField = {"count(*)"};
        // execute count query on previous query
        int count = Integer.parseInt(Executor.executeQuery(this.myStatement, builder.addCount(query), countField)[0]);
        return new DataContainer(res, allSongFields, count);
    }

    /**
     *
     * Gets all of the songs with the given genres, popularity, tempo, duration, and artist.
     * @param genre the wanted genres
     * @param popularity the wanted popularity
     * @param tempo the wanted tempo
     * @param duration the wanted duration
     * @param artist the wanted artist
     * @return the songs that the criteria applies to
     * @throws SQLException throws exception if there was a problem executing the query
     */
    DataContainer getSongs(GenreContainer genre, PopularityContainer popularity, TempoContainer tempo,
                                  DurationContainer duration, ArtistContainer artist) throws SQLException {
        // get songs according to parameters
        DataContainer combination1 = getSongs(genre, artist, "");
        DataContainer combination2 = getSongs(tempo, duration);
        DataContainer popularityResult = getSongs(popularity);
        // combine the results of both queries
        String[] data = getSameLines(combination1.getData(), combination2.getData());
        data = getSameLines(data, popularityResult.getData());
        return new DataContainer(data, popularityResult.getColumns(), data.length);
    }
}
