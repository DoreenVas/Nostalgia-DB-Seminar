package DatabaseConnection;

import java.sql.ResultSet;
import java.sql.Statement;


/**
 * artist fields: {"artist_id", "artist_name", "familiarity", "hotness"}
 * album fields: {"album_id", "album_name"}
 * genre fields: {"genre_id", "genre_name"}
 * song fields: {"song_id", "name", "dencability", "duration", "tempo", "hotness", "loudness", "year", "words"}
 */
public class Queries {

    /**
     *
     * @param myStatement The sql statement
     * @param query The sql query
     * @param columns The wanted columns from the table
     * @return A string of all the lines from the query's answer
     */
    private static String[] executeQuery(Statement myStatement, String query, String[] columns) {
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

    public static String[] getHotArtists(Statement myStatement) {
        String query = "select * from artist where familiarity > 6";
        String[] columns = {"artist_id", "artist_name", "familiarity", "hotness"};
        return executeQuery(myStatement, query, columns);
    }

    public static String[] getAlbumsTable(Statement myStatement) {
        String query = "select * from album";
        String[] columns = {"album_id", "album_name"};
        return executeQuery(myStatement, query, columns);
    }
}
