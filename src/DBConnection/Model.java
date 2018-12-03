package DBConnection;

import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.*;

public class Model {
    public static void main(String[] args) {
        BufferedReader br;
        String user, password;
        String fileName = "src/DBConnection/dbConnectionConfig";
        try {
            br = new BufferedReader(new FileReader(fileName));
            user = br.readLine().replace("username: ", "");
            password = br.readLine().replace("password: ", "");
            br.close();
        } catch (Exception e) {
            System.out.println("File " + fileName + " not found");
            return;
        }

        try {
            // start connection to DBConnection
            Connection myConn = DriverManager.getConnection("jdbc:mysql://localhost/seminardb?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", user, password);
////            // create a statement
//            Statement myStatement = myConn.createStatement();
////            // execute query
//            ResultSet myRes = myStatement.executeQuery("select * from song");

            StatementHolder holder = new StatementHolder(myConn);
            PreparedStatement statement = holder.get();
            statement.setString(1, "name");
            statement.setString(2, "barbie girl");
            ResultSet myRes = statement.executeQuery();

            // process the result
            while(myRes.next()) {
                System.out.println(myRes.getString("name"));
            }
//            myRes.close();
//            myStatement.close();
            myConn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

//        String[] res = BasicSearchQueries.getHotArtists(myStatement);
//        for (String line : res) {
//            System.out.println(line);
//        }
//        res = BasicSearchQueries.getAlbumsTable(myStatement);
//        for (String line : res) {
//            System.out.println(line);
//        }


//        try {
//            // start connection to DBConnection
//            Connection myConn = DriverManager.getConnection("path_to_db");
//            // create a statement
//            Statement myStatement = myConn.createStatement();
//            // execute query
//            ResultSet myRes = myStatement.executeQuery("select * from table");
//            // process the result
//            while(myRes.next()) {
//                System.out.println(myRes.getString("last_name" /*field name*/) + "," + myRes.getString("first_name" /*field name*/));
//            }
//            myRes.close();
//            myStatement.close();
//            myConn.close();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }
}