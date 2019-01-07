package DBConnection;

import Resources.AlbumIdContainer;
import Resources.DataContainer;
import Resources.SongIdContainer;

import java.sql.SQLException;
import java.sql.Statement;

/**
 * A class holding all of the album table queries, also in charge of executing them.
 */
public class AlbumQueries {
    // members
    private static AlbumQueries ourInstance = new AlbumQueries();
    private static Statement myStatement;
    private static String[] allAlbumFields = {/*"album.album_id", */"album.album_name"};

    /****
     * Constructor singleton
     * @param statement a statement
     * @return this instance of albumQueries
     */
    public static AlbumQueries getInstance(Statement statement) {
        myStatement = statement;
        return ourInstance;
    }

    /****
     * return all info about a given album
     * @param songContainer a song container
     * @return dataContainer of all info about the album
     * @throws SQLException if the query failed
     */
    DataContainer getAlbum(SongIdContainer songContainer) throws SQLException {
        // initialize the query
        String query = "select distinct * from song, album_song, album " +
                "where album.album_id=album_song.album_id " +
                "and album_song.song_id=song.song_id " +
                "and song.song_id=" + Integer.parseInt(songContainer.getValue());
        // execute the query
        String[] res = Executor.executeQuery(myStatement, query, allAlbumFields);
        int count = res.length;
        return new DataContainer(res, allAlbumFields, count);
    }

    /****
     * return all info about a given album
     * @param albumContainer an album container
     * @return dataContainer of all info about the album
     * @throws SQLException if the query failed
     */
    DataContainer getAlbum(AlbumIdContainer albumContainer) throws SQLException {
        // initialize the query
        String query = "select distinct * from album " +
                "where album.album_id=\"" + albumContainer.getValue() + "\"";
        // execute the query
        String[] res = Executor.executeQuery(myStatement, query, allAlbumFields);
        int count = res.length;
        return new DataContainer(res, allAlbumFields, count);
    }
}
