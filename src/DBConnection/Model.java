package DBConnection;

import Resources.DBConnectionException;
import Resources.DataContainer;
import Resources.QueryInfo;

import java.sql.SQLException;

public interface Model {

    DataContainer getData(QueryInfo info);
    void closeConnection() throws SQLException;
    void openConnection() throws SQLException;
}
