package DBConnection;

import Resources.DBConnectionException;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;

public class Model {
    private static String[] allSongFields = {"song_id", "name", "dancibility", "duration", "tempo", "hotness",
            "loudness", "year", "words"};
    private static String[] allArtistFields = {"artist_id", "artist_name", "familiarity", "hotness"};
    private static String[] allGenreFields = {"genre_id", "genre_name"};
    private static String[] allAlbumFields = {"album_id", "album_name"};

    private Connection myConn;
    private Statement myStatement;
    private String url, user, password;

    public Model() throws IOException {
        BufferedReader br;
        String fileName = "src/DBConnection/dbConnectionConfig";
        br = new BufferedReader(new FileReader(fileName));
        // get needed parameters for connection to DB from file
        this.url = br.readLine().replace("url: ", "");
        this.user = br.readLine().replace("username: ", "");
        this.password = br.readLine().replace("password: ", "");
        br.close();
    }

    public void openConnection() throws DBConnectionException {
        try {
            // start connection to DBConnection
            this.myConn = DriverManager.getConnection(this.url /*"jdbc:mysql://localhost/seminardb?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC"*/, this.user, this.password);
            // create a statement
            this.myStatement = this.myConn.createStatement();
        } catch (Exception e) {
            throw new DBConnectionException("Failed to open a connection to the Database.", e);
        }
////            // execute query
//            ResultSet myRes = myStatement.executeQuery("select * from song");

        // process the result
//            while(myRes.next()) {
//                System.out.println(myRes.getString("name"));
//            }
//         myRes.close();
    }

    public void closeConnection() throws DBConnectionException {
        try {
            this.myStatement.close();
            this.myConn.close();
        } catch (Exception e) {
            throw new DBConnectionException("Failed to close the connection to the Database.", e);
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

    private <T>boolean contains(T[] holder, T element) {
        for (T item : holder) {
            if(item.equals(element)) {
                return true;
            }
        }
        return false;
    }

    private String[] getFields(String table, String[] columns) {
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
    public String[] getTable(String table) throws SQLException {
        String[] fields = {"*"};
        String[] tables = {table};
        QueryBuilder builder = new QueryBuilder(fields, tables);
        String query = builder.build();
        fields = getFields(table, fields);
        return Executor.executeQuery(this.myStatement, query, fields);
    }

    /**
     * @param year        the wanted year
     * @return The songs from the given year
     * <p>
     * The function returns all of the songs from wanted year.
     */
    public String[] getSongs(int year) throws SQLException {
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
    public String[] getSongs(int from, int to) throws SQLException {
        String[] fields = {"*"};
        String[] tables = {"song"};
        QueryBuilder builder = new QueryBuilder(fields, tables);
        builder = builder.addWhere().addBetweenStatements("year", from, to);
        String query = builder.build();
        return Executor.executeQuery(this.myStatement, query, allSongFields);
    }

    // gets songs by genre
    public String[] getSongs(GenreContainer genre) throws SQLException {

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
        return Executor.executeQuery(this.myStatement, query.toString(), allSongFields);
    }

    public String[] getLyrics(String songName) throws SQLException {
        String[] fields = {"*"};
        String[] tables = {"song"};
        QueryBuilder builder = new QueryBuilder(fields, tables);
        builder = builder.addWhere().addEqualStatements("name", "\"" + songName + "\"");
        String query = builder.build();
        String[] columns = {"words"};
        return Executor.executeQuery(this.myStatement, query, columns);
    }

    /**
     *
     * @param popularity the wanted popularity of the songs
     * @return all of the songs that are about as popular as given
     */
//    public String[] getSongs(PopularityContainer popularity) {
//
//    }

    /**
     *
     * @param tempo the wanted tempo of the songs
     * @return all of the songs that have a tempo as a function of the tempo
     */
//    public String[] getSongs(TempoContainer tempo) {
//
//    }

    // gets a specific song's lyrics by song name
    // NADAV
    // gets a specific song's lyrics
//    public String[] getSongs(ArtisitContainer artist, String songName) throws SQLException {
//
//    }

    // gets popular songs by artist hotness and familiarity
//    public String[] getSongs(HotnessContainer hotness, float familiarity) throws SQLException {
//
//    }

    // gets popular songs by artist hotness and familiarity from specific genre
//   public String[] getSongs(float hotness, float familiarity, String genre) throws SQLException {
//
//    }

    // get songs by song name
    public String[] getSongs(String songName) throws SQLException {
        String[] fields = {"*"};
        String[] tables = {"song"};
        QueryBuilder builder = new QueryBuilder(fields, tables);
        builder = builder.addWhere().addEqualStatements("name", "\"" + songName + "\"");
        String query = builder.build();
        return Executor.executeQuery(this.myStatement, query, allSongFields);
    }

    // get songs by artist
    public String[] getSongs(ArtisitContainer artisitContainer) throws SQLException {
        String query = "select * from song, album_song, artist_album, artist " +
                "where artist.artist_name=\"" + artisitContainer.getValue() + "\" " +
                "and artist.artist_id=artist_album.artist_id " +
                "and artist_album.album_id=album_song.album_id " +
                "and album_song.song_id=song.song_id";
        return Executor.executeQuery(this.myStatement, query, allSongFields);
    }

    // get songs by song length
    public String[] getSongs(DurationContainer duration) throws SQLException {
        String[] fields = {"*"};
        String[] tables = {"song"};
        QueryBuilder builder = new QueryBuilder(fields, tables);
        float value = duration.getValue();
        // get songs with given duration plus - minus an amount of time in relation to the given duration
        float epsilon = value * (float)0.1;
        builder.addWhere().addBetweenStatements("duration", value - epsilon, value + epsilon);
        String query = builder.build();
        return Executor.executeQuery(this.myStatement, query, allSongFields);
    }
//
//    // NADAV
//    // get songs by genre, popularity, tempo, length, artist
//    public String[] getSongs(GenreContainer genre, PopularityContainer popularity, TempoContainer tempo,
//                             DurationContainer duration, ArtisitContainer artist) throws SQLException {
//
//    }
//
//    // get songs by popularity, tempo, length, artist
//    public String[] getSongs(PopularityContainer popularity, TempoContainer tempo, DurationContainer duration,
//                             ArtisitContainer artist) throws SQLException {
//
//    }
//
//    // get songs by genre, tempo, length, artist
//    public String[] getSongs(GenreContainer genre, TempoContainer tempo, DurationContainer duration,
//                             ArtisitContainer artist) throws SQLException {
//
//    }
//
//    // NADAV
//    // get songs by genre, popularity, length, artist
//    public String[] getSongs(GenreContainer genre, PopularityContainer popularity, DurationContainer duration,
//                             ArtisitContainer artist) throws SQLException {
//
//    }
//
//    //LITAL
//    // get songs by genre, popularity, tempo, artist
//    public String[] getSongs(GenreContainer genre, PopularityContainer popularity, TempoContainer tempo,
//                             ArtisitContainer artist) throws SQLException {
//
//    }
//
//    // DORIN
//    // get songs by genre, popularity, tempo, length
//    public String[] getSongs(GenreContainer genre, PopularityContainer popularity, TempoContainer tempo,
//                             DurationContainer duration) throws SQLException {
//
//    }
//
//    // get songs by genre, popularity
//    public String[] getSongs(GenreContainer genre, PopularityContainer popularity) throws SQLException {
//
//    }
//
//    // get songs by genre, tempo
//    public String[] getSongs(GenreContainer genre, TempoContainer tempo) throws SQLException {
//
//    }
//
//    // NADAV
//    // get songs by genre, length
//    public String[] getSongs(GenreContainer genre, DurationContainer duration) throws SQLException {
//
//    }
//
//    // NADAV
//    // get songs by genre, artist
//    public String[] getSongs(GenreContainer genre, ArtisitContainer artist) throws SQLException {
//
//    }
//
//    //LITAL
//    // get songs by popularity, tempo
//    public String[] getSongs(PopularityContainer popularity, TempoContainer tempo) throws SQLException {
//
//    }
//
//    // get songs by popularity, length
//    public String[] getSongs(PopularityContainer popularity, DurationContainer duration) throws SQLException {
//
//    }
//
//    // get songs by popularity, artist
//    public String[] getSongs(PopularityContainer popularity, ArtisitContainer artist) throws SQLException {
//
//    }
//
//    // DORIN
//    // get songs by tempo, length
//    public String[] getSongs(TempoContainer tempo, DurationContainer duration) throws SQLException {
//
//    }
//
//    // LITAL
//    // get songs by tempo, artist
//    public String[] getSongs(TempoContainer tempo, ArtisitContainer artist) throws SQLException {
//
//    }
//
//
//    // get songs by genre, popularity, tempo
//    public String[] getSongs(GenreContainer genre, PopularityContainer popularity, TempoContainer tempo) throws SQLException {
//
//    }
//
//    // get songs by genre, popularity, length
//    public String[] getSongs(GenreContainer genre, PopularityContainer popularity, DurationContainer duration) throws SQLException {
//
//    }
//
//    // LITAL
//    // get songs by genre, popularity, artist
//    public String[] getSongs(GenreContainer genre, PopularityContainer popularity, ArtisitContainer artist) throws SQLException {
//
//    }
//
//    // DORIN
//    // get songs by genre, tempo, length
//    public String[] getSongs(GenreContainer genre, TempoContainer tempo, DurationContainer duration) throws SQLException {
//
//    }
//
//    // NADAV
//    // get songs by genre, tempo, artist
//    public String[] getSongs(GenreContainer genre, TempoContainer tempo, ArtisitContainer artist) throws SQLException {
//
//    }
//
//    // get songs by genre, length, artist
//    public String[] getSongs(GenreContainer genre, DurationContainer duration, ArtisitContainer artist) throws SQLException {
//
//    }
//
//    // LITAL
//    // get songs by popularity, tempo, length
//    public String[] getSongs(PopularityContainer popularity, TempoContainer tempo, DurationContainer duration) throws SQLException {
//
//    }
//
//    // NADAV
//    // get songs by popularity, tempo, artist
//    public String[] getSongs(PopularityContainer popularity, TempoContainer tempo, ArtisitContainer artist) throws SQLException {
//
//    }
//
//    // DORIN
//    // get songs by tempo, length, artist
//    public String[] getSongs(TempoContainer tempo, DurationContainer duration, ArtisitContainer artist) throws SQLException {
//
//    }


    // !!! Irrelevant for now!!!
    /**
     *
     * @return A string array of all the hot artists.
     */
    /*public String[] getHotArtists(float familiarity) {
        String query = "select * from artist where familiarity > " + familiarity;
        return Executor.executeQuery(myStatement, query, allArtistFields);
    }*/
    // !!! do not touch !!!

    /**
     *
     * @param artist the wanted artists name
     * @return the given artists info
     */
//    public String[] getArtists(ArtisitContainer artist) {
//
//    }
}