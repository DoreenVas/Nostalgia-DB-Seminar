package DBConnection;
import java.sql.ResultSet;
import java.sql.Statement;

public class Executor {

    /**
     *
     * @param myStatement The sql statement
     * @param query The sql query
     * @param columns The wanted columns from the table
     * @return A string array of all the lines from the query's answer
     *
     * Executes given query and returns an array of all the lines of the result.
     */
    public static String[] executeQuery(Statement myStatement, String query, String[] columns) {
        // initialize builder
        StringBuilder builder = new StringBuilder();
        try {
            // execute query and get result
            ResultSet myRes = myStatement.executeQuery(query);

            // go over results
            while(myRes.next()) {
                // append each result to builder
                for (int i = 0; i < columns.length - 1; i++) {
                    builder.append(myRes.getString(columns[i] /*field name*/)).append(", ");
                }
                builder.append(myRes.getString(columns[columns.length - 1] /*field name*/));
                builder.append('\n');
            }
            // close result
            myRes.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        // return all result lines as array
        return builder.toString().split("\n");
    }
}
