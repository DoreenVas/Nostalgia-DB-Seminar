package DBConnection;

import java.sql.*;

public class StatementHolder {

    private Connection myConn;

    public StatementHolder(Connection connection) {
        this.myConn = connection;
    }

//    StatementHolder holder = new StatementHolder(myConn);
//    PreparedStatement statement = holder.getTable();
//    String table = "song";
//    statement.setString(1, table);
//    ResultSet myRes = statement.executeQuery();

    public PreparedStatement get() {
        PreparedStatement statement = null;
        try {
            statement = this.myConn.prepareStatement("select * from song where ?=?");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return statement;
    }
}
