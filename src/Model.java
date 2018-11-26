import java.sql.*;

public class Model {
    public static void main(String[] args) {
        try {
            // start connection to DB
            Connection myConn = DriverManager.getConnection("path_to_db");
            // create a statement
            Statement myStatement = myConn.createStatement();
            // execute query
            ResultSet myRes = myStatement.executeQuery("select * from table");
            // process the result
            while(myRes.next()) {
                System.out.println(myRes.getString("last_name" /*field name*/) + "," + myRes.getString("first_name" /*field name*/));
            }
            myRes.close();
            myStatement.close();
            myConn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}