package DBConnection;

import Resources.DBConnectionException;
import Resources.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;

public class DBModel implements Model {
    private static String[] allSongFields = {/*"song.song_id", */"song.name", "song.dancibility", "song.duration", "song.tempo", "song.hotness",
            "song.loudness", "song.year"/*, "song.words"*/};
    private static String[] allArtistFields = {"artist.artist_id", "artist.artist_name", "artist.familiarity", "artist.hotness"};
    private static String[] allGenreFields = {"genre.genre_id", "genre.genre_name"};
    private static String[] allAlbumFields = {"album.album_id", "album.album_name"};
    private static final float tempoRate = (float)0.3;
    private static final int durationRate = 30;
    private static final float popularityRate = (float)0.05;

    private Connection myConn;
    private Statement myStatement;
    private String url, user, password;

    public DBModel() throws IOException {
        BufferedReader br;
        String fileName = "src/DBConnection/dbConnectionConfig";
        try {
            br = new BufferedReader(new FileReader(fileName));
            // get needed parameters for connection to DB from file
            this.url = br.readLine().replace("url: ", "");
            this.user = br.readLine().replace("username: ", "");
            this.password = br.readLine().replace("password: ", "");
            br.close();
        } catch(Exception e) {
            throw new IOException(AlertMessages.failedConnection("couldn't read from configuration file.",
                    false));
        }
    }

    public void openConnection() throws DBConnectionException {
        try {
            // start connection to DBConnection
            this.myConn = DriverManager.getConnection(this.url /*"jdbc:mysql://localhost/seminardb?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC"*/, this.user, this.password);
            // create a statement
            this.myStatement = this.myConn.createStatement();
        } catch (Exception e) {
            throw new DBConnectionException(AlertMessages.failedConnection(), e);
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
            throw new DBConnectionException(AlertMessages.failedDisconnection(), e);
        }
    }

    private boolean needToAddAnd(boolean first, StringBuilder songConditions) {
        if(!first) {
            songConditions.append(" and ");
        }
        return first;
    }

    @Override
    public DataContainer getData(SongQueryInfo info) throws SQLException {
        DataContainer dataContainer = activateAppropriateSongQuery(info);
        if(dataContainer.getCount() == 1 && dataContainer.getData()[0].equals("")) {
            throw new SQLException(AlertMessages.emptyResult());
        }
        return dataContainer;
    }

    @Override
    public DataContainer getData(ArtistQueryInfo info) throws SQLException {
        DataContainer dataContainer = activateAppropriateArtistQuery(info);
        if(dataContainer.getCount() == 1 && dataContainer.getData()[0].equals("")) {
            throw new SQLException(AlertMessages.emptyResult());
        }
        return dataContainer;
    }

    @Override
    public DataContainer getData(AlbumQueryInfo info) throws SQLException {
        DataContainer dataContainer = activateAppropriateAlbumQuery(info);
        if(dataContainer.getCount() == 1 && dataContainer.getData()[0].equals("")) {
            throw new SQLException(AlertMessages.emptyResult());
        }
        return dataContainer;
    }

    private DataContainer activateAppropriateAlbumQuery(AlbumQueryInfo info) throws SQLException {
        DataContainer dataContainer = null;

        // TODO:*** needs to be implemented***

        if(info.getSong() != null) {
            dataContainer = getAlbum(info.getSong());
        }
        else if(info.getAlbum() != null) {
            dataContainer = getAlbum(info.getAlbum());
        }

        return dataContainer;
    }

    private DataContainer activateAppropriateArtistQuery(ArtistQueryInfo info) throws SQLException {
        DataContainer dataContainer = null;
        //StringBuilder artistConditions = new StringBuilder();

        // TODO:*** needs to be implemented***

        if(info.getSong() != null) {
            dataContainer = getArtists(info.getSong());
        }
        else if(info.getArtist() != null) {
            dataContainer = getArtists(info.getArtist());
        }

        return dataContainer;
    }

    private DataContainer activateAppropriateSongQuery(SongQueryInfo info) throws SQLException {
        DataContainer dataContainer = null;
        boolean first = true, yearInserted = false;
        float val;
        StringBuilder songConditions = new StringBuilder();
        if(info.getYear() != -1) {
            songConditions.append("song.year=").append(info.getYear());
            first = false;
            yearInserted = true;
        } else if(info.getFrom() != -1) {
            songConditions.append("song.year between ").append(info.getFrom()).append(" and ").append(info.getTo());
            first = false;
            yearInserted = true;
        } else if(info.getLyrics() != null) {
            return getLyrics(info.getLyrics());
        }

        if(info.getTempo() != null) {
            first = needToAddAnd(first, songConditions);
            val = info.getTempo().getValue();
            songConditions.append("song.tempo between ").append(val - val * tempoRate).append(" and ")
                    .append(val + val * tempoRate);
        }
        if(info.getPopularity() != null) {
            first = needToAddAnd(first, songConditions);
            val = info.getPopularity().getValue();
            songConditions.append("song.hotness between ").append(val - popularityRate).append(" and ")
                    .append(val + popularityRate);
        }
        if(info.getDuration() != null) {
            first = needToAddAnd(first, songConditions);
            val = info.getDuration().getValue();
            songConditions.append("song.duration between ").append(val - durationRate).append(" and ")
                    .append(val + durationRate);
        }
        if(yearInserted) {
            songConditions.append(" order by song.year asc ");
        }

        if(info.getGenere() != null) {
            if(info.getArtist() != null) {
                if(info.getAlbum() != null) {
                    dataContainer = getSongs(info.getGenere(), info.getArtist(), info.getAlbum(), songConditions.toString());
                    return dataContainer;
                }
                dataContainer = getSongs(info.getGenere(), info.getArtist(), songConditions.toString());
                return dataContainer;
            }
            dataContainer = getSongs(info.getGenere(), songConditions.toString());
            return dataContainer;
        }
        if(info.getArtist() != null) {
            if(info.getAlbum() != null) {
                dataContainer = getSongs(info.getArtist(), info.getAlbum(), songConditions.toString());
                return dataContainer;
            }
            dataContainer = getSongs(info.getArtist(), songConditions.toString());
            return dataContainer;
        }
        if(info.getAlbum() != null) {
            dataContainer = getSongs(info.getAlbum(), songConditions.toString());
            return dataContainer;
        }

        dataContainer = getSongs(songConditions.toString());
        return dataContainer;
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
    private DataContainer getTable(String table) throws SQLException {
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
    private DataContainer getSongs(int year) throws SQLException {
        return SongQueries.getInstance(myStatement).getSongs(year);
    }

    /**
     * @param from        start year
     * @param to          end year
     * @return The songs between given years
     * <p>
     * The function returns all of the songs between the given years.
     */
    private DataContainer getSongs(int from, int to) throws SQLException {
        return SongQueries.getInstance(myStatement).getSongs(from, to);
    }

    // gets songs by genre
    private DataContainer getSongs(GenreContainer genre, String songConditions) throws SQLException {
        return SongQueries.getInstance(myStatement).getSongs(genre, songConditions);
    }

    private DataContainer getLyrics(LyricsContainer song) throws SQLException {
        return SongQueries.getInstance(myStatement).getLyrics(song);
    }

    /**
     *
     * @param popularity the wanted popularity of the songs
     * @return all of the songs that are about as popular as given
     */
    private DataContainer getSongs(PopularityContainer popularity) throws SQLException{
        return SongQueries.getInstance(myStatement).getSongs(popularity);
    }

    /**
     *
     * @param tempo the wanted tempo of the songs
     * @return all of the songs that have a tempo as a function of the tempo
     */
    private DataContainer getSongs(TempoContainer tempo) throws SQLException {
        return SongQueries.getInstance(myStatement).getSongs(tempo);
    }

    private DataContainer getSongs(ArtistContainer artist, AlbumIdContainer album, String songConditions) throws SQLException {
        return SongQueries.getInstance(myStatement).getSongs(artist, album, songConditions);
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
    private DataContainer getSongs(SongIdContainer song) throws SQLException {
        return SongQueries.getInstance(myStatement).getSongs(song);
    }

    private DataContainer getSongs(AlbumIdContainer album, String songConditions) throws SQLException {
        return SongQueries.getInstance(myStatement).getSongs(album, songConditions);
    }

    private DataContainer getSongs(String songConditions) throws SQLException {
        return SongQueries.getInstance(myStatement).getSongs(songConditions);
    }

    // get songs by artist
    private DataContainer getSongs(ArtistContainer artist, String songConditions) throws SQLException {
        return SongQueries.getInstance(myStatement).getSongs(artist, songConditions);
    }

    // get songs by song length
    private DataContainer getSongs(DurationContainer duration) throws SQLException {
        return SongQueries.getInstance(myStatement).getSongs(duration);
    }

    // get songs by genre, popularity, tempo, length, artist
    private DataContainer getSongs(GenreContainer genre, PopularityContainer popularity, TempoContainer tempo,
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
    private DataContainer getSongs(GenreContainer genre, DurationContainer duration) throws SQLException {
        return SongQueries.getInstance(myStatement).getSongs(genre, duration);
    }

    // get songs by genre, artist
    private DataContainer getSongs(GenreContainer genre, ArtistContainer artist, String songConditions) throws SQLException {
        return SongQueries.getInstance(myStatement).getSongs(genre, artist, songConditions);
    }

    private DataContainer getSongs(SongIdContainer songName, ArtistContainer artist) throws SQLException {
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
    private DataContainer getSongs(TempoContainer tempo, DurationContainer duration) throws SQLException {
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
    private DataContainer getSongs(TempoContainer tempo, DurationContainer duration, ArtistContainer artist) throws SQLException {
        return SongQueries.getInstance(myStatement).getSongs(tempo, duration, artist);
    }

    private DataContainer getSongs(int year, GenreContainer genre, DurationContainer duration) throws SQLException {
        return SongQueries.getInstance(myStatement).getSongs(year, genre, duration);
    }

    private DataContainer getSongs(GenreContainer genre, ArtistContainer artist,
                                   AlbumIdContainer album, String songConditions) throws SQLException {
        return SongQueries.getInstance(myStatement).getSongs(genre, artist, album, songConditions);
    }

    /**
     *
     * @param artist the wanted artists name
     * @return the given artists info
     * @throws SQLException throws exception if there was a problem executing the query
     */
    private DataContainer getArtists(ArtistContainer artist) throws SQLException{
        return ArtistQueries.getInstance(myStatement).getArtists(artist);
    }

    /**
     *
     * @param songName the song to look for its artist
     * @return the songs artists info
     * @throws SQLException throws exception if there was a problem executing the query
     */
    private DataContainer getArtists(SongIdContainer songName) throws SQLException{
        return ArtistQueries.getInstance(myStatement).getArtists(songName);
    }

    private DataContainer getAlbum(SongIdContainer songContainer) throws SQLException{
        return AlbumQueries.getInstance(myStatement).getAlbum(songContainer);
    }

    /**
     *
     * Gets an albums info by id
     * @param albumContainer the wanted album
     * @return the albums info
     * @throws SQLException throws exception if there was a problem executing the query
     */
    private DataContainer getAlbum(AlbumIdContainer albumContainer) throws SQLException {
        return AlbumQueries.getInstance(myStatement).getAlbum(albumContainer);
    }
}