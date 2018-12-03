package DBConnection;
import java.sql.Statement;

public class BasicSearchQueries {
    private static String[] allSongFields = {"song_id", "name", "dencability", "duration", "tempo", "hotness",
            "loudness", "year", "words"};
    private static String[] allArtistFields = {"artist_id", "artist_name", "familiarity", "hotness"};
    private static String[] allGenreFields = {"genre_id", "genre_name"};
    private static String[] allAlbumFields = {"album_id", "album_name"};

    /**
     *
     * @param myStatement The statement to use for query.
     * @return  A string array of all the albums.
     */
    public static String[] getAlbums(Statement myStatement) {
        String[] fields = {"*"};
        String[] tables = {"album"};
        QueryBuilder builder = new QueryBuilder(fields, tables);
        String query = builder.build();
        return Executor.executeQuery(myStatement, query, allAlbumFields);
    }

    /**
     *
     * @param myStatement The statement to use for query.
     * @return  A string array of all the songs.
     */
    public static String[] getSongs(Statement myStatement) {
        String[] fields = {"*"};
        String[] tables = {"song"};
        QueryBuilder builder = new QueryBuilder(fields, tables);
        String query = builder.build();
        return Executor.executeQuery(myStatement, query, allSongFields);
    }

    /**
     *
     * @param myStatement The statement to use for query.
     * @return  A string array of all the genres.
     */
    public static String[] getGenres(Statement myStatement) {
        String[] fields = {"*"};
        String[] tables = {"genre"};
        QueryBuilder builder = new QueryBuilder(fields, tables);
        String query = builder.build();
        return Executor.executeQuery(myStatement, query, allGenreFields);
    }

    /**
     *
     * @param myStatement The statement to use for query.
     * @return  A string array of all the artists.
     */
    public static String[] getArtists(Statement myStatement) {
        String[] fields = {"*"};
        String[] tables = {"artist"};
        QueryBuilder builder = new QueryBuilder(fields, tables);
        String query = builder.build();
        return Executor.executeQuery(myStatement, query, allArtistFields);
    }

    /**
     *
     * @param myStatement
     * @param year the wanted year
     * @return The songs from the given year
     *
     * The function returns all of the songs from wanted year.
     */
    public static String[] getSongs(Statement myStatement, int year) {
        String[] fields = {"*"};
        String[] tables = {"song"};
        QueryBuilder builder = new QueryBuilder(fields, tables);
        builder = builder.addWhere().addEqualStatements("year", year);
        String query = builder.build();
        return Executor.executeQuery(myStatement, query, allSongFields);
    }

    /**
     *
     * @param myStatement
     * @param from start year
     * @param to end year
     * @return The songs between given years
     *
     * The function returns all of the songs between the given years.
     */
    public static String[] getSongs(Statement myStatement, int from, int to) {
        String[] fields = {"*"};
        String[] tables = {"song"};
        QueryBuilder builder = new QueryBuilder(fields, tables);
        builder = builder.addWhere().addBetweenStatements("year", from, to);
        String query = builder.build();
        //String query = "select * from song where year between " + from + " and " + to;
        return Executor.executeQuery(myStatement, query, allSongFields);
    }

    // gets a specific song's lyrics
//    public static String[] getSongs(Statement myStatement, String artistId, String songName) {
//
//    }

    // gets songs by genre
//   public static String[] getSongs(Statement myStatement, String genre) {
//
//    }

    // gets popular songs by artist hotness and familiarity
//   public static String[] getSongs(Statement myStatement, float hotness, float familiarity) {
//
//    }

    // gets popular songs by artist hotness and familiarity from specific genre
//   public static String[] getSongs(Statement myStatement, float hotness, float familiarity, String genre) {
//
//    }

    // get songs by song name
//    public static String[] getSongs(Statement myStatement, String genreId /*or genreName*/) {
//
//    }


    // !!! Irrelevant for now!!!
    /**
     *
     * @param myStatement The statement to use for query.
     * @return  A string array of all the hot artists.
     */
    /*public static String[] getHotArtists(Statement myStatement, float familiarity) {
        String query = "select * from artist where familiarity > " + familiarity;
        return Executor.executeQuery(myStatement, query, allArtistFields);
    }*/
    // !!! do not touch !!!
}
