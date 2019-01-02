package DBConnection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

class Executor {

    /**
     *
     * @param myStatement The sql statement
     * @param query The sql query
     * @param columns The wanted columns from the table
     * @return A string array of all the lines from the query's answer
     *
     * Executes given query and returns an array of all the lines of the result.
     */
    static String[] executeQuery(Statement myStatement, String query, String[] columns) throws SQLException {
        // initialize builder
        StringBuilder builder = new StringBuilder();
        int j = 0;
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
                j++;
            }
            // close result
            myRes.close();
        } catch (Exception e) {
            throw new SQLException("Failed to execute a query.", e);
        }
        String res;
        if(j == 0 || query.toLowerCase().contains("count(*)")) {
            res = builder.toString();
        } else {
            res = addFirstRow(columns).append(builder.toString()).toString();
        }
        // return all result lines as array
        return res.split("\n");
    }

    private static StringBuilder addFirstRow(String[] columns) {
        StringBuilder builder = new StringBuilder();
        for(int i = 0; i < columns.length - 1; i++) {
            builder.append(columns[i]).append(", ");
        }
        builder.append(columns[columns.length - 1]).append("\n");
        return builder;
    }
}
