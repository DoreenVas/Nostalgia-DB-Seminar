package DBConnection;

import Resources.DBConnectionException;
import Resources.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;

public class Model {
    private static String[] allSongFields = {"song.song_id", "song.name", "song.dancibility", "song.duration", "song.tempo", "song.hotness",
            "song.loudness", "song.year"/*, "song.words"*/};
    private static String[] allArtistFields = {"artist.artist_id", "artist.artist_name", "artist.familiarity", "artist.hotness"};
    private static String[] allGenreFields = {"genre.genre_id", "genre.genre_name"};
    private static String[] allAlbumFields = {"album.album_id", "album.album_name"};

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
    public DataContainer getTable(String table) throws SQLException {
        String[] fields = {"*"};
        String[] tables = {table};
        QueryBuilder builder = new QueryBuilder(fields, tables);
        String query = builder.build();
        fields = getFields(table, fields);
        String[] res = Executor.executeQuery(this.myStatement, query, fields);
        String[] countField = {"count(*)"};
        int count = Integer.parseInt(Executor.executeQuery(this.myStatement, builder.addCount(query), countField)[0]);
        return new DataContainer(res, fields, count);
    }

    /**
     * @param year        the wanted year
     * @return The songs from the given year
     * <p>
     * The function returns all of the songs from wanted year.
     */
    public DataContainer getSongs(int year) throws SQLException {
        return SongQueries.getInstance(myStatement).getSongs(year);
    }

    /**
     * @param from        start year
     * @param to          end year
     * @return The songs between given years
     * <p>
     * The function returns all of the songs between the given years.
     */
    public DataContainer getSongs(int from, int to) throws SQLException {
        return SongQueries.getInstance(myStatement).getSongs(from, to);
    }

    // gets songs by genre
    public DataContainer getSongs(GenreContainer genre) throws SQLException {
        return SongQueries.getInstance(myStatement).getSongs(genre);
    }

    public DataContainer getLyrics(String songName) throws SQLException {
        return SongQueries.getInstance(myStatement).getLyrics(songName);
    }

    /**
     *
     * @param popularity the wanted popularity of the songs
     * @return all of the songs that are about as popular as given
     */
    public DataContainer getSongs(PopularityContainer popularity) throws SQLException{
        return SongQueries.getInstance(myStatement).getSongs(popularity);
    }

    /**
     *
     * @param tempo the wanted tempo of the songs
     * @return all of the songs that have a tempo as a function of the tempo
     */
    public DataContainer getSongs(TempoContainer tempo) throws SQLException {
        return SongQueries.getInstance(myStatement).getSongs(tempo);
    }

    // gets a specific song's lyrics by song name
    // NADAV
    // gets a specific song's lyrics
//    public String[] getSongs(ArtistContainer artist, String songName) throws SQLException {
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
    public DataContainer getSongs(String songName) throws SQLException {
        return SongQueries.getInstance(myStatement).getSongs(songName);
    }

    // get songs by artist
    public DataContainer getSongs(ArtistContainer artist) throws SQLException {
        return SongQueries.getInstance(myStatement).getSongs(artist);
    }

    // get songs by song length
    public DataContainer getSongs(DurationContainer duration) throws SQLException {
        return SongQueries.getInstance(myStatement).getSongs(duration);
    }

    // get songs by genre, popularity, tempo, length, artist
    public DataContainer getSongs(GenreContainer genre, PopularityContainer popularity, TempoContainer tempo,
                             DurationContainer duration, ArtistContainer artist) throws SQLException {
        return SongQueries.getInstance(myStatement).getSongs(genre, popularity, tempo, duration, artist);
    }
//
//    // get songs by popularity, tempo, length, artist
//    public String[] getSongs(PopularityContainer popularity, TempoContainer tempo, DurationContainer duration,
//                             ArtistContainer artist) throws SQLException {
//
//    }
//
//    // get songs by genre, tempo, length, artist
//    public String[] getSongs(GenreContainer genre, TempoContainer tempo, DurationContainer duration,
//                             ArtistContainer artist) throws SQLException {
//
//    }
//
//    // NADAV
//    // get songs by genre, popularity, length, artist
//    public String[] getSongs(GenreContainer genre, PopularityContainer popularity, DurationContainer duration,
//                             ArtistContainer artist) throws SQLException {
//
//    }
//
//    //LITAL
//    // get songs by genre, popularity, tempo, artist
//    public String[] getSongs(GenreContainer genre, PopularityContainer popularity, TempoContainer tempo,
//                             ArtistContainer artist) throws SQLException {
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

    // get songs by genre, length
    public DataContainer getSongs(GenreContainer genre, DurationContainer duration) throws SQLException {
        return SongQueries.getInstance(myStatement).getSongs(genre, duration);
    }

    // get songs by genre, artist
    public DataContainer getSongs(GenreContainer genre, ArtistContainer artist) throws SQLException {
        return SongQueries.getInstance(myStatement).getSongs(genre, artist);
    }

    public DataContainer getSongs(SongContainer songName, ArtistContainer artist) throws SQLException {
        return SongQueries.getInstance(myStatement).getSongs(songName, artist);
    }
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
//    public String[] getSongs(PopularityContainer popularity, ArtistContainer artist) throws SQLException {
//
//    }

    // get songs by tempo, length
    public DataContainer getSongs(TempoContainer tempo, DurationContainer duration) throws SQLException {
        return SongQueries.getInstance(myStatement).getSongs(tempo, duration);
    }
//
//    // LITAL
//    // get songs by tempo, artist
//    public String[] getSongs(TempoContainer tempo, ArtistContainer artist) throws SQLException {
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
//    public String[] getSongs(GenreContainer genre, PopularityContainer popularity, ArtistContainer artist) throws SQLException {
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
//    public String[] getSongs(GenreContainer genre, TempoContainer tempo, ArtistContainer artist) throws SQLException {
//
//    }
//
//    // get songs by genre, length, artist
//    public String[] getSongs(GenreContainer genre, DurationContainer duration, ArtistContainer artist) throws SQLException {
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
//    public String[] getSongs(PopularityContainer popularity, TempoContainer tempo, ArtistContainer artist) throws SQLException {
//
//    }

    // get songs by tempo, length, artist
    public DataContainer getSongs(TempoContainer tempo, DurationContainer duration, ArtistContainer artist) throws SQLException {
        return SongQueries.getInstance(myStatement).getSongs(tempo, duration, artist);
    }


    // !!! Irrelevant for now!!!
    /**
     *
     * @return A string array of all the hot artists.
     */
    /*public String[] getHotArtists(double familiarity) {
        String query = "select * from artist where familiarity > " + familiarity;
        return Executor.executeQuery(myStatement, query, allArtistFields);
    }*/
    // !!! do not touch !!!

    /**
     *
     * @param artist the wanted artists name
     * @return the given artists info
     */
    public DataContainer getArtists(ArtistContainer artist) throws SQLException{
        return ArtistQueries.getInstance(myStatement).getArtists(artist);
    }

    public DataContainer getArtists(SongContainer songName) throws SQLException{
        return ArtistQueries.getInstance(myStatement).getArtists(songName);
    }
}