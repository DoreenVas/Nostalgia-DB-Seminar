package DBConnection;

import Resources.DBConnectionException;
import Resources.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;

public class DBModel implements Model {
    // all of the song table fields to get when executing a query
    private static String[] allSongFields = {"song.song_id", "song.name", "song.dancibility", "song.duration", "song.tempo", "song.hotness",
            "song.loudness", "song.year"/*, "song.words"*/};
    // all of the artist table fields to get when executing a query
    private static String[] allArtistFields = {"artist.artist_id", "artist.artist_name", "artist.familiarity", "artist.hotness"};
    // all of the genre table fields to get when executing a query
    private static String[] allGenreFields = {"genre.genre_id", "genre.genre_name"};
    // all of the album table fields to get when executing a query
    private static String[] allAlbumFields = {"album.album_id", "album.album_name"};
    // epsilon values for range of queries
    private static final float tempoRate = 30;
    private static final int durationRate = 30;
    private static final float popularityRate = (float)0.05;

    // current connection used for queries
    private Connection myConn;
    // current statement used for queries
    private Statement myStatement;
    private String url, user, password;

    /**
     *
     * DBModel constructor.
     * Reads needed information from the DB configuration file.
     * @throws IOException thrown if there was an issue reading from the configuration file
     */
    public DBModel() throws IOException {
        BufferedReader br;
        // the location of the configuration file
        String fileName = "src/DBConnection/dbConnectionConfig";
        try {
            // open a reader to the file
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

    /**
     *
     * Opens a connection to the DB.
     * @throws DBConnectionException thrown if there was an error connecting to the DB
     */
    public void openConnection() throws DBConnectionException {
        try {
            // start connection to DBConnection
            this.myConn = DriverManager.getConnection(this.url, this.user, this.password);
            // create a statement
            this.myStatement = this.myConn.createStatement();
        } catch (Exception e) {
            throw new DBConnectionException(AlertMessages.failedConnection(), e);
        }
    }

    /**
     *
     * Closes the connection to the DB.
     * @throws DBConnectionException thrown if there was an error disconnecting from the DB
     */
    public void closeConnection() throws DBConnectionException {
        try {
            // close resources
            this.myStatement.close();
            this.myConn.close();
        } catch (Exception e) {
            throw new DBConnectionException(AlertMessages.failedDisconnection(), e);
        }
    }

    @Override
    public DataContainer getData(SongQueryInfo info) throws SQLException {
        DataContainer dataContainer = activateAppropriateSongQuery(info);
        // if the container is empty
        if(dataContainer == null) {
            return null;
        }
        if(dataContainer.getCount() == 1 && dataContainer.getData()[0].equals("")) {
            throw new DBConnectionException(AlertMessages.emptyResult());
        }
        return dataContainer;
    }

    @Override
    public DataContainer getData(ArtistQueryInfo info) throws SQLException {
        DataContainer dataContainer = activateAppropriateArtistQuery(info);
        // if the container is empty
        if(dataContainer == null) {
            return null;
        }
        if(dataContainer.getCount() == 1 && dataContainer.getData()[0].equals("")) {
            throw new DBConnectionException(AlertMessages.emptyResult());
        }
        return dataContainer;
    }

    @Override
    public DataContainer getData(AlbumQueryInfo info) throws SQLException {
        DataContainer dataContainer = activateAppropriateAlbumQuery(info);
        // if the container is empty
        if(dataContainer == null) {
            return null;
        }
        if(dataContainer.getCount() == 1 && dataContainer.getData()[0].equals("")) {
            throw new DBConnectionException(AlertMessages.emptyResult());
        }
        return dataContainer;
    }

    /**
     *
     * Activates the appropriate album query according to the given information.
     * @param info the info for executing the query
     * @return the results of the query
     * @throws SQLException throws exception if there was a problem executing the query
     */
    private DataContainer activateAppropriateAlbumQuery(AlbumQueryInfo info) throws SQLException {
        DataContainer dataContainer = null;

        // TODO:*** needs to be implemented***

        // if the info contains a songs info
        if(info.getSong() != null) {
            dataContainer = getAlbum(info.getSong());
            if(dataContainer.getCount() == 1 && dataContainer.getData()[0].equals("")) {
                return null;
            }
        }
        // if the info contains an albums info
        else if(info.getAlbum() != null) {
            dataContainer = getAlbum(info.getAlbum());
        }

        return dataContainer;
    }

    /**
     *
     * Activates the appropriate artist query according to the given information.
     * @param info the info for executing the query
     * @return the results of the query
     * @throws SQLException throws exception if there was a problem executing the query
     */
    private DataContainer activateAppropriateArtistQuery(ArtistQueryInfo info) throws SQLException {
        DataContainer dataContainer = null;

        // TODO:*** needs to be implemented***

        // if the info contains a songs info
        if(info.getSong() != null) {
            dataContainer = getArtists(info.getSong());
            if(dataContainer.getCount() == 1 && dataContainer.getData()[0].equals("")) {
                return null;
            }
        }
        // if the info contains an artists info
        else if(info.getArtist() != null) {
            dataContainer = getArtists(info.getArtist());
        }

        return dataContainer;
    }

    /**
     *
     * Activates the appropriate song query according to the given information.
     * @param info the info for executing the query
     * @return the results of the query
     * @throws SQLException throws exception if there was a problem executing the query
     */
    private DataContainer activateAppropriateSongQuery(SongQueryInfo info) throws SQLException {
        DataContainer dataContainer;
        boolean first = true, yearInserted = false;
        float val;
        StringBuilder songConditions = new StringBuilder();
        // if the info contains a years info
        if(info.getYear() != -1) {
            songConditions.append("song.year=").append(info.getYear());
            first = false;
            yearInserted = true;
        }
        // if the info contains a range of years info
        else if(info.getFrom() != -1) {
            songConditions.append("song.year between ").append(info.getFrom()).append(" and ").append(info.getTo());
            first = false;
            yearInserted = true;
        }
        // if the info contains lyric info
        else if(info.getLyrics() != null) {
            return getLyrics(info.getLyrics());
        }

        // if the info contains tempos info
        if(info.getTempo() != null) {
            first = SongQueries.needToAddAnd(first, songConditions);
            val = info.getTempo().getValue();
            songConditions.append("song.tempo between ").append(val - tempoRate).append(" and ")
                    .append(val + tempoRate);
        }
        // if the info contains popularity info
        if(info.getPopularity() != null) {
            first = SongQueries.needToAddAnd(first, songConditions);
            val = info.getPopularity().getValue();
            songConditions.append("song.hotness between ").append(val - popularityRate).append(" and ")
                    .append(val + popularityRate);
        }
        // if the info contains duration info
        if(info.getDuration() != null) {
            first = SongQueries.needToAddAnd(first, songConditions);
            val = info.getDuration().getValue();
            songConditions.append("song.duration between ").append(val - durationRate).append(" and ")
                    .append(val + durationRate);
        }
        // if a year value was inserted then order the results by ascending order
        if(yearInserted) {
            songConditions.append(" order by song.year asc ");
        }

        // if the info contains genre info
        if(info.getGenre() != null) {
            // if the info contains artist info
            if(info.getArtist() != null) {
                // if the info contains album info
                if(info.getAlbum() != null) {
                    dataContainer = getSongs(info.getGenre(), info.getArtist(), info.getAlbum(), songConditions.toString());
                    return dataContainer;
                }
                // if the info doesn't contain tempos info
                dataContainer = getSongs(info.getGenre(), info.getArtist(), songConditions.toString());
                return dataContainer;
            }
            // if the info doesn't contain artist info
            dataContainer = getSongs(info.getGenre(), songConditions.toString());
            return dataContainer;
        }
        // if the info contains artist info
        if(info.getArtist() != null) {
            // if the info contains album info
            if(info.getAlbum() != null) {
                dataContainer = getSongs(info.getArtist(), info.getAlbum(), songConditions.toString());
                return dataContainer;
            }
            // if the info doesn't contain album info
            dataContainer = getSongs(info.getArtist(), songConditions.toString());
            return dataContainer;
        }
        // if the info contains album info
        if(info.getAlbum() != null) {
            dataContainer = getSongs(info.getAlbum(), songConditions.toString());
            return dataContainer;
        }

        // if none of the above happened, then get the songs by the wanted song parameters
        dataContainer = getSongs(songConditions.toString());
        return dataContainer;
    }

    /**
     *
     * Checks if the given array contains the given element
     * @param holder the array to check in
     * @param element the element to check for
     * @param <T> the generic type
     * @return true if the array contains the element, false otherwise
     */
    private <T>boolean contains(T[] holder, T element) {
        for (T item : holder) {
            if(item.equals(element)) {
                return true;
            }
        }
        return false;
    }

    /**
     *
     * Check that the fields given are correct (apply to the wanted table), and returns the corrected fields.
     * @param table the wanted table to query
     * @param columns the wanted columns from the table
     * @return the correct fields for the wanted table according to given columns.
     */
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
        // if the wanted table is invalid
        if(fields == null) {
            return null;
        }
        // if there are more than one wanted column
        if(columns.length > 1) {
            // go over the given columns
            for (String column : columns) {
                // if the table doesn't contain the column
                if(!contains(fields, column)) {
                    return null;
                }
            }
        }
        // if there is only one wanted column and it isn't "*"
        else if(!columns[0].equals("*")) {
            // if the table doesn't contain the column
            if(!contains(fields, columns[0])) {
                return null;
            }
        }
        return fields;
    }

    /**
     *
     * Returns the wanted tables content.
     * @param table the wanted table
     * @return the wanted tables content
     * @throws SQLException throws exception if there was a problem executing the query
     */
    private DataContainer getTable(String table) throws SQLException {
        String[] fields = {"*"};
        String[] tables = {table};
        // initialize the query builder
        QueryBuilder builder = new QueryBuilder(fields, tables);
        String query = builder.build();
        // get the tables fields
        fields = getFields(table, fields);
        // execute the query
        String[] res = Executor.executeQuery(this.myStatement, query, fields);
        String[] countField = {"count(*)"};
        // execute a count query
        int count = Integer.parseInt(Executor.executeQuery(this.myStatement, builder.addCount(query), countField)[0]);
        return new DataContainer(res, fields, count);
    }

    /**
     *
     * The function returns all of the songs from wanted year.
     * @param year the wanted year
     * @return The songs from the given year
     */
    private DataContainer getSongs(int year) throws SQLException {
        return SongQueries.getInstance(myStatement).getSongs(year);
    }

    /**
     *
     * The function returns all of the songs between the given years.
     *
     * @param from start year
     * @param to end year
     * @return The songs between given years
     */
    private DataContainer getSongs(int from, int to) throws SQLException {
        return SongQueries.getInstance(myStatement).getSongs(from, to);
    }

    /**
     *
     * Calls the function that gets all of the songs that apply to the given genre and song conditions.
     * @param genre the wanted genres
     * @param songConditions the wanted conditions
     * @return the songs that the criteria applies to
     * @throws SQLException throws exception if there was a problem executing the query
     */
    private DataContainer getSongs(GenreContainer genre, String songConditions) throws SQLException {
        return SongQueries.getInstance(myStatement).getSongs(genre, songConditions);
    }

    /**
     *
     * Calls the function that gets a given songs lyrics.
     * @param song the wanted song
     * @return the lyrics of the wanted song
     * @throws SQLException throws exception if there was a problem executing the query
     */
    private DataContainer getLyrics(LyricsContainer song) throws SQLException {
        return SongQueries.getInstance(myStatement).getLyrics(song);
    }

    /**
     *
     * Calls the function that gets all of the songs that apply to the given popularity.
     * @param popularity the wanted popularity of the songs
     * @return all of the songs that are about as popular as given
     */
    private DataContainer getSongs(PopularityContainer popularity) throws SQLException{
        return SongQueries.getInstance(myStatement).getSongs(popularity);
    }

    /**
     *
     * Calls the function that gets all of the songs that apply to the given tempo.
     * @param tempo the wanted tempo of the songs
     * @return all of the songs that have a tempo as a function of the tempo
     */
    private DataContainer getSongs(TempoContainer tempo) throws SQLException {
        return SongQueries.getInstance(myStatement).getSongs(tempo);
    }

    /**
     *
     * Calls the function that gets all of the songs that apply to the given artist, album and conditions.
     * @param artist the wanted artist
     * @param album the wanted album
     * @param songConditions the song conditions
     * @return the songs that the criteria applies to
     * @throws SQLException throws exception if there was a problem executing the query
     */
    private DataContainer getSongs(ArtistContainer artist, AlbumIdContainer album, String songConditions) throws SQLException {
        return SongQueries.getInstance(myStatement).getSongs(artist, album, songConditions);
    }

    /**
     *
     * Calls the function that gets the song that applies to the given song id.
     * @param song the wanted song
     * @return the song that has the given id
     * @throws SQLException throws exception if there was a problem executing the query
     */
    private DataContainer getSongs(SongIdContainer song) throws SQLException {
        return SongQueries.getInstance(myStatement).getSongs(song);
    }

    /**
     *
     * Calls the function that gets all of the songs that apply to the given album and conditions.
     * @param album the wanted album
     * @param songConditions the song conditions
     * @return the songs that the criteria applies to
     * @throws SQLException throws exception if there was a problem executing the query
     */
    private DataContainer getSongs(AlbumIdContainer album, String songConditions) throws SQLException {
        return SongQueries.getInstance(myStatement).getSongs(album, songConditions);
    }

    /**
     *
     * Calls the function that gets all of the songs that apply to the given conditions.
     * @param songConditions the song conditions
     * @return the songs that the criteria applies to
     * @throws SQLException throws exception if there was a problem executing the query
     */
    private DataContainer getSongs(String songConditions) throws SQLException {
        return SongQueries.getInstance(myStatement).getSongs(songConditions);
    }

    /**
     *
     * Calls the function that gets all of the songs that are from the given artist and apply to the given conditions.
     * @param artist the wanted artist
     * @param songConditions the song conditions
     * @return the songs that the criteria applies to
     * @throws SQLException throws exception if there was a problem executing the query
     */
    private DataContainer getSongs(ArtistContainer artist, String songConditions) throws SQLException {
        return SongQueries.getInstance(myStatement).getSongs(artist, songConditions);
    }

    /**
     *
     * Calls the function that gets all of the songs with the given duration.
     * @param duration the wanted song duration
     * @return the songs that the criteria applies to
     * @throws SQLException throws exception if there was a problem executing the query
     */
    private DataContainer getSongs(DurationContainer duration) throws SQLException {
        return SongQueries.getInstance(myStatement).getSongs(duration);
    }

    /**
     *
     * Calls the function that gets all of the songs with the given genres, popularity, tempo, duration, and artist.
     * @param genre the wanted genres
     * @param popularity the wanted popularity
     * @param tempo the wanted tempo
     * @param duration the wanted duration
     * @param artist the wanted artist
     * @return the songs that the criteria applies to
     * @throws SQLException throws exception if there was a problem executing the query
     */
    private DataContainer getSongs(GenreContainer genre, PopularityContainer popularity, TempoContainer tempo,
                             DurationContainer duration, ArtistContainer artist) throws SQLException {
        return SongQueries.getInstance(myStatement).getSongs(genre, popularity, tempo, duration, artist);
    }

    /**
     *
     * Calls the function that gets all of the songs with the given genres, artist and song conditions.
     * @param genre the wanted genres
     * @param artist the wanted artist
     * @param songConditions the wanted conditions
     * @return the songs that the criteria applies to
     * @throws SQLException throws exception if there was a problem executing the query
     */
    private DataContainer getSongs(GenreContainer genre, ArtistContainer artist, String songConditions) throws SQLException {
        return SongQueries.getInstance(myStatement).getSongs(genre, artist, songConditions);
    }

    /**
     *
     * Calls the function that gets the wanted song with the given artist.
     * @param song the wanted song
     * @param artist the wanted artist
     * @return the songs that the criteria applies to
     * @throws SQLException throws exception if there was a problem executing the query
     */
    private DataContainer getSongs(SongIdContainer song, ArtistContainer artist) throws SQLException {
        return SongQueries.getInstance(myStatement).getSongs(song, artist);
    }

    /**
     *
     * Calls the function that gets all of the songs with the given tempo and duration.
     * @param tempo the wanted tempo
     * @param duration the wanted duration
     * @return the songs that the criteria applies to
     * @throws SQLException throws exception if there was a problem executing the query
     */
    private DataContainer getSongs(TempoContainer tempo, DurationContainer duration) throws SQLException {
        return SongQueries.getInstance(myStatement).getSongs(tempo, duration);
    }

    /**
     *
     * Calls the function that gets all of the songs with the given tempo, duration and artist.
     * @param tempo the wanted tempo
     * @param duration the wanted duration
     * @param artist the wanted artist
     * @return the songs that the criteria applies to
     * @throws SQLException throws exception if there was a problem executing the query
     */
    private DataContainer getSongs(TempoContainer tempo, DurationContainer duration, ArtistContainer artist) throws SQLException {
        return SongQueries.getInstance(myStatement).getSongs(tempo, duration, artist);
    }

    private DataContainer getSongs(int year, GenreContainer genre, DurationContainer duration) throws SQLException {
        return SongQueries.getInstance(myStatement).getSongs(year, genre, duration);
    }

    /**
     *
     * Calls the function that gets all of the songs that are from the given genre and have the given duration.
     * @param genre the wanted genres
     * @param duration the wanted song duration
     * @return the songs that the criteria applies to
     * @throws SQLException throws exception if there was a problem executing the query
     */
    private DataContainer getSongs(GenreContainer genre, DurationContainer duration) throws SQLException {
        return SongQueries.getInstance(myStatement).getSongs(genre, duration);
    }

    /**
     *
     * Calls the function that gets all of the songs that are from the given genre, by the given artist,
     * from the given album and apply to the given conditions.
     * @param genre the wanted genres
     * @param artist the wanted artist
     * @param album the wanted album
     * @param songConditions the song conditions
     * @return the songs that the criteria applies to
     * @throws SQLException throws exception if there was a problem executing the query
     */
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
     * Calls the function that gets a given songs artist information.
     * @param songName the song to look for its artist
     * @return the given songs artists info
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
     * Calls the function that gets an albums info by id.
     * @param albumContainer the wanted album
     * @return the albums info
     * @throws SQLException throws exception if there was a problem executing the query
     */
    private DataContainer getAlbum(AlbumIdContainer albumContainer) throws SQLException {
        return AlbumQueries.getInstance(myStatement).getAlbum(albumContainer);
    }
}