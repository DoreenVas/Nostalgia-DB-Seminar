package DBConnection;

import Resources.AlbumQueryInfo;
import Resources.ArtistQueryInfo;
import Resources.DataContainer;
import Resources.SongQueryInfo;

import java.sql.SQLException;

public interface Model {

    DataContainer getData(SongQueryInfo info) throws SQLException;
    DataContainer getData(ArtistQueryInfo info) throws SQLException;
    DataContainer getData(AlbumQueryInfo info) throws SQLException;
    void closeConnection() throws SQLException;
    void openConnection() throws SQLException;
}
