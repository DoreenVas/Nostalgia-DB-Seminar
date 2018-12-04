package DBConnection;

import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.*;

public class Model {
    private static String[] allSongFields = {"song_id", "name", "dencability", "duration", "tempo", "hotness",
            "loudness", "year", "words"};
    private static String[] allArtistFields = {"artist_id", "artist_name", "familiarity", "hotness"};
    private static String[] allGenreFields = {"genre_id", "genre_name"};
    private static String[] allAlbumFields = {"album_id", "album_name"};

    private Connection myConn;
    private Statement myStatement;

    public Model() {
        BufferedReader br;
        String user, password;
        String fileName = "src/DBConnection/dbConnectionConfig";
        try {
            br = new BufferedReader(new FileReader(fileName));
            user = br.readLine().replace("username: ", "");
            password = br.readLine().replace("password: ", "");
            br.close();
        } catch (Exception e) {
            System.out.println("File " + fileName + " not found");
            return;
        }

        try {
            // start connection to DBConnection
            this.myConn = DriverManager.getConnection("jdbc:mysql://localhost/seminardb?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", user, password);
            // create a statement
            this.myStatement = this.myConn.createStatement();
////            // execute query
//            ResultSet myRes = myStatement.executeQuery("select * from song");

            // process the result
//            while(myRes.next()) {
//                System.out.println(myRes.getString("name"));
//            }
//            myRes.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

     public void closeConnection() {
        try {
            this.myStatement.close();
            this.myConn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
     }

//        String[] res = searchQueries.getHotArtists(myStatement);
//        for (String line : res) {
//            System.out.println(line);
//        }
//        res = searchQueries.getAlbumsTable(myStatement);
//        for (String line : res) {
//            System.out.println(line);
//        }

    /**
     * @return A string array of all the albums.
     */
    public String[] getAlbums() {
        String[] fields = {"*"};
        String[] tables = {"album"};
        QueryBuilder builder = new QueryBuilder(fields, tables);
        String query = builder.build();
        return Executor.executeQuery(this.myStatement, query, allAlbumFields);
    }

    /**
     * @return A string array of all the songs.
     */
    public String[] getSongs() {
        String[] fields = {"*"};
        String[] tables = {"song"};
        QueryBuilder builder = new QueryBuilder(fields, tables);
        String query = builder.build();
        return Executor.executeQuery(this.myStatement, query, allSongFields);
    }

    /**
     * @return A string array of all the genres.
     */
    public String[] getGenres() {
        String[] fields = {"*"};
        String[] tables = {"genre"};
        QueryBuilder builder = new QueryBuilder(fields, tables);
        String query = builder.build();
        return Executor.executeQuery(this.myStatement, query, allGenreFields);
    }

    /**
     * @return A string array of all the artists.
     */
    public String[] getArtists() {
        String[] fields = {"*"};
        String[] tables = {"artist"};
        QueryBuilder builder = new QueryBuilder(fields, tables);
        String query = builder.build();
        return Executor.executeQuery(this.myStatement, query, allArtistFields);
    }

    /**
     * @param year        the wanted year
     * @return The songs from the given year
     * <p>
     * The function returns all of the songs from wanted year.
     */
    public String[] getSongs(int year) {
        String[] fields = {"*"};
        String[] tables = {"song"};
        QueryBuilder builder = new QueryBuilder(fields, tables);
        builder = builder.addWhere().addEqualStatements("year", year);
        String query = builder.build();
        return Executor.executeQuery(this.myStatement, query, allSongFields);
    }

    /**
     * @param from        start year
     * @param to          end year
     * @return The songs between given years
     * <p>
     * The function returns all of the songs between the given years.
     */
    public String[] getSongs(int from, int to) {
        String[] fields = {"*"};
        String[] tables = {"song"};
        QueryBuilder builder = new QueryBuilder(fields, tables);
        builder = builder.addWhere().addBetweenStatements("year", from, to);
        String query = builder.build();
        return Executor.executeQuery(this.myStatement, query, allSongFields);
    }

    // NADAV
    // gets a specific song's lyrics
//    public String[] getSongs(Statement myStatement, String artistId, String songName) {
//
//    }

    // LITAL
    // gets songs by genre
//   public String[] getSongs(Statement myStatement, String genre) {
//
//    }

    // DORIN
    // gets popular songs by artist hotness and familiarity
//   public String[] getSongs(Statement myStatement, float hotness, float familiarity) {
//
//    }

    // LITAL
    // gets popular songs by artist hotness and familiarity from specific genre
//   public String[] getSongs(Statement myStatement, float hotness, float familiarity, String genre) {
//
//    }

    // NADAV
    // get songs by song name
//    public String[] getSongs(Statement myStatement, String genreId /*or genreName*/) {
//
//    }


    //LITAL
    // get songs by artist
//    public String[] getSongs(Statement myStatement, String genreId /*or genreName*/) {
//
//    }

    // get songs by song length
//    public String[] getSongs(Statement myStatement, String genreId /*or genreName*/) {
//
//    }

    // DORIN
    // get songs by tempo
//    public String[] getSongs(Statement myStatement, String genreId /*or genreName*/) {
//
//    }

    // NADAV
    // get songs by genre, popularity, tempo, length, artist
//    public String[] getSongs(Statement myStatement, String genreId /*or genreName*/) {
//
//    }

    // get songs by popularity, tempo, length, artist
//    public String[] getSongs(Statement myStatement, String genreId /*or genreName*/) {
//
//    }

    // get songs by genre, tempo, length, artist
//    public String[] getSongs(Statement myStatement, String genreId /*or genreName*/) {
//
//    }

    // NADAV
    // get songs by genre, popularity, length, artist
//    public String[] getSongs(Statement myStatement, String genreId /*or genreName*/) {
//
//    }

    //LITAL
    // get songs by genre, popularity, tempo, artist
//    public String[] getSongs(Statement myStatement, String genreId /*or genreName*/) {
//
//    }

    // DORIN
    // get songs by genre, popularity, tempo, length
//    public String[] getSongs(Statement myStatement, String genreId /*or genreName*/) {
//
//    }

    // get songs by genre, popularity
//    public String[] getSongs(Statement myStatement, String genreId /*or genreName*/) {
//
//    }

    // get songs by genre, tempo
//    public String[] getSongs(Statement myStatement, String genreId /*or genreName*/) {
//
//    }

    // NADAV
    // get songs by genre, length
//    public String[] getSongs(Statement myStatement, String genreId /*or genreName*/) {
//
//    }

    // NADAV
    // get songs by genre, artist
//    public String[] getSongs(Statement myStatement, String genreId /*or genreName*/) {
//
//    }

    //LITAL
    // get songs by popularity, tempo
//    public String[] getSongs(Statement myStatement, String genreId /*or genreName*/) {
//
//    }

    // get songs by popularity, length
//    public String[] getSongs(Statement myStatement, String genreId /*or genreName*/) {
//
//    }

    // get songs by popularity, artist
//    public String[] getSongs(Statement myStatement, String genreId /*or genreName*/) {
//
//    }

    // DORIN
    // get songs by tempo, length
//    public String[] getSongs(Statement myStatement, String genreId /*or genreName*/) {
//
//    }

    // LITAL
    // get songs by tempo, artist
//    public String[] getSongs(Statement myStatement, String genreId /*or genreName*/) {
//
//    }


    // get songs by genre, popularity, tempo
//    public String[] getSongs(Statement myStatement, String genreId /*or genreName*/) {
//
//    }

    // get songs by genre, popularity, length
//    public String[] getSongs(Statement myStatement, String genreId /*or genreName*/) {
//
//    }

    // LITAL
    // get songs by genre, popularity, artist
//    public String[] getSongs(Statement myStatement, String genreId /*or genreName*/) {
//
//    }

    // DORIN
    // get songs by genre, tempo, length
//    public String[] getSongs(Statement myStatement, String genreId /*or genreName*/) {
//
//    }

    // NADAV
    // get songs by genre, tempo, artist
//    public String[] getSongs(Statement myStatement, String genreId /*or genreName*/) {
//
//    }

    // get songs by genre, length, artist
//    public String[] getSongs(Statement myStatement, String genreId /*or genreName*/) {
//
//    }

    // LITAL
    // get songs by popularity, tempo, length
//    public String[] getSongs(Statement myStatement, String genreId /*or genreName*/) {
//
//    }

    // NADAV
    // get songs by popularity, tempo, artist
//    public String[] getSongs(Statement myStatement, String genreId /*or genreName*/) {
//
//    }

    // DORIN
    // get songs by tempo, length, artist
//    public String[] getSongs(Statement myStatement, String genreId /*or genreName*/) {
//
//    }


    // !!! Irrelevant for now!!!
    /**
     *
     * @param myStatement The statement to use for query.
     * @return A string array of all the hot artists.
     */
    /*public String[] getHotArtists(Statement myStatement, float familiarity) {
        String query = "select * from artist where familiarity > " + familiarity;
        return Executor.executeQuery(myStatement, query, allArtistFields);
    }*/
    // !!! do not touch !!!
}