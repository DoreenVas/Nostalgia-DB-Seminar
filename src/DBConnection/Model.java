package DBConnection;

import Resources.AlbumQueryInfo;
import Resources.ArtistQueryInfo;
import Resources.DataContainer;
import Resources.SongQueryInfo;

import java.sql.SQLException;

public interface Model {

    /**
     *
     * Asks the model to execute a song query and return its results.
     * @param info the information for executing the wanted query
     * @return A container of the data returned from the executed query
     * @throws SQLException thrown if unable to execute the wanted query
     */
    DataContainer getData(SongQueryInfo info) throws SQLException;

    /**
     *
     * Asks the model to execute an artist query and return its results.
     * @param info the information for executing the wanted query
     * @return A container of the data returned from the executed query
     * @throws SQLException thrown if unable to execute the wanted query
     */
    DataContainer getData(ArtistQueryInfo info) throws SQLException;

    /**
     *
     * Asks the model to execute an album query and return its results.
     * @param info the information for executing the wanted query
     * @return A container of the data returned from the executed query
     * @throws SQLException thrown if unable to execute the wanted query
     */
    DataContainer getData(AlbumQueryInfo info) throws SQLException;

    /**
     *
     * Closes the current connection to the DB
     * @throws SQLException thrown if unable to connect to the DB
     */
    void closeConnection() throws SQLException;

    /**
     *
     * Opens a connection to the DB.
     * @throws SQLException thrown if unable to disconnect to the DB
     */
    void openConnection() throws SQLException;
}
