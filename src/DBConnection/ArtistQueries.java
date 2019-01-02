package DBConnection;

import Resources.DataContainer;

import java.sql.SQLException;
import java.sql.Statement;

public class ArtistQueries {
    private static ArtistQueries ourInstance = new ArtistQueries();
    private static Statement myStatement;
    private static String[] allArtistFields = {"artist.artist_id", "artist.artist_name", "artist.familiarity", "artist.hotness"};

    public static ArtistQueries getInstance(Statement statement) {
        myStatement = statement;
        return ourInstance;
    }

    private ArtistQueries() {

    }

    /**
     *
     * @param artist the wanted artists name
     * @return the given artists info
     */
    public DataContainer getArtists(ArtistContainer artist) throws SQLException {
        String[] fields = {"*"};
        String[] tables = {"artist"};
        QueryBuilder builder = new QueryBuilder(fields, tables);
        // get songs with given duration plus - minus an amount of time in relation to the given duration
        builder.addWhere().addEqualStatements("artist_name", "\"" + artist.getValue() + "\"");
        String query = builder.build();
        String[] res = Executor.executeQuery(this.myStatement, query, allArtistFields);
        String[] countField = {"count(*)"};
        int count = Integer.parseInt(Executor.executeQuery(this.myStatement, builder.addCount(query), countField)[0]);
        return new DataContainer(res, allArtistFields, count);
    }
}