package DBConnection;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Model {
    private static String[] allSongFields = {"song_id", "name", "dencability", "duration", "tempo", "hotness",
            "loudness", "year", "words"};
    private static String[] allArtistFields = {"artist_id", "artist_name", "familiarity", "hotness"};
    private static String[] allGenreFields = {"genre_id", "genre_name"};
    private static String[] allAlbumFields = {"album_id", "album_name"};

    private Connection myConn;
    private Statement myStatement;
    private String fileName, user, password;

    public Model() throws IOException {
        BufferedReader br;
        this.fileName = "src/DBConnection/dbConnectionConfig";
        br = new BufferedReader(new FileReader(this.fileName));
        user = br.readLine().replace("username: ", "");
        password = br.readLine().replace("password: ", "");
        br.close();
    }

    public void openConnection() throws SQLException {
        // start connection to DBConnection
        this.myConn = DriverManager.getConnection("jdbc:mysql://localhost/seminardb?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", this.user, this.password);
        // create a statement
        this.myStatement = this.myConn.createStatement();
////            // execute query
//            ResultSet myRes = myStatement.executeQuery("select * from song");

        // process the result
//            while(myRes.next()) {
//                System.out.println(myRes.getString("name"));
//            }
//         myRes.close();
    }

     public void closeConnection() throws SQLException {
        this.myStatement.close();
        this.myConn.close();
     }

//        String[] res = searchQueries.getHotArtists(myStatement);
//        for (String line : res) {
//            System.out.println(line);
//        }
//        res = searchQueries.getAlbumsTable(myStatement);
//        for (String line : res) {
//            System.out.println(line);
//        }

    private <T>boolean contains(T[] holder, T element) {
        for (T item : holder) {
            if(item.equals(element)) {
                return true;
            }
        }
        return false;
    }

    private String[] getFeilds(String table, String[] columns) {
        String[] fields = null;
        switch (table) {
            case "song":
                fields = allSongFields;
                break;
            case "artist":
                fields =  allArtistFields;
                break;
            case "genre":
                fields =  allGenreFields;
                break;
            case "album":
                fields =  allAlbumFields;
                break;
        }
        if(fields == null) {
            return null;
        }
        if(columns.length > 1) {
            for (String column : columns) {
                if(!contains(fields, column)) {
                    return null;
                }
            }
        } else if(!columns[0].equals("*")) {
            if(!contains(fields, columns[0])) {
                return null;
            }
        }
        return fields;
    }

    /**
     * @return A string array of all the given tables rows.
     */
    public String[] getTable(String table) {
        String[] fields = {"*"};
        String[] tables = {table};
        QueryBuilder builder = new QueryBuilder(fields, tables);
        String query = builder.build();
        fields = getFeilds(table, fields);
        return Executor.executeQuery(this.myStatement, query, fields);
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