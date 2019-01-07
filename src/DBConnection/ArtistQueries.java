package DBConnection;

import Resources.*;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * A class holding all of the artist table queries, also in charge of executing them.
 */
class ArtistQueries {
    // members
    private static ArtistQueries ourInstance = new ArtistQueries();
    private static Statement myStatement;
    private static String[] allArtistFields = {/*"artist.artist_id", */"artist.artist_name", "artist.familiarity", "artist.hotness"};

    /****
     * Constructor of singleton
     * @param statement a statement
     * @return his instance of artistQueries
     */
    static ArtistQueries getInstance(Statement statement) {
        myStatement = statement;
        return ourInstance;
    }

//    DataContainer getArtists(GenreContainer genre, ArtistContainer artist, AlbumIdContainer album)
//            throws SQLException {
//
//    }
//
//    DataContainer getArtists(GenreContainer genre, ArtistContainer artist) throws SQLException {
//
//    }
//
//    DataContainer getArtists(GenreContainer genre, AlbumIdContainer album) throws SQLException {
//
//    }
//
//    DataContainer getArtists(ArtistContainer artist, AlbumIdContainer album) throws SQLException {
//
//    }

    /****
     * Adds genres and conditions on the genres to the query
     * @param artistQuery a query on artist table
     * @param genre a list of genres
     * @return a new query with conditions on genres
     */
    private StringBuilder attachGenreQueryToSArtistQuery(String artistQuery, GenreContainer genre) {
        // initialize the query
        StringBuilder query = new StringBuilder("select * from artist, " +
                "(select artist_id from artist_genre, " +
                "(select genre_id from genre " +
                "where ");
        // add all the genres
        String genres[] = genre.getValue();
        for (int i = 0; i < genres.length - 1; i++) {
            query.append("genre_name=\"").append(genres[i]).append("\" or ");
        }
        query.append("genre_name=\"").append(genres[genres.length - 1]).append("\"");
        // add the rest of the query
        query.append(") as ID " +
                "where artist_genre.genre_id=ID.genre_id) as AID " +
                "where AID.artist_id=artist.artist_id ");
        query.append(artistQuery);
        return query;
    }

    /****
     *
     * @param genre
     * @param artistQuery
     * @return
     * @throws SQLException
     */
    DataContainer getArtists(GenreContainer genre, String artistQuery) throws SQLException {


        // *** needs checking***


        StringBuilder query = attachGenreQueryToSArtistQuery(artistQuery, genre);
        String[] res = Executor.executeQuery(myStatement, query.toString(), allArtistFields);
        int count = res.length;
        return new DataContainer(res, allArtistFields, count);
    }

    /****
     *
     * @param album
     * @return
     * @throws SQLException
     */
    DataContainer getArtists(AlbumIdContainer album) throws SQLException {


        // *** needs checking***


        String[] fields = {"*"};
        String[] tables = {"artist", "artist_album", "album"};
        QueryBuilder builder = new QueryBuilder(fields, tables);
        // get songs with given duration plus - minus an amount of time in relation to the given duration
        builder.addWhere().addEqualStatements("album_name", "\"" + album.getValue() + "\"");
        builder.addWhere().addEqualStatements("artist.artist_id", "artist_album.artist_id");
        builder.addWhere().addEqualStatements("artist_album.album_id", "album.album_id");
        String query = builder.build();
        String[] res = Executor.executeQuery(myStatement, query, allArtistFields);
        String[] countField = {"count(*)"};
        int count = Integer.parseInt(Executor.executeQuery(myStatement, builder.addCount(query), countField)[0]);
        return new DataContainer(res, allArtistFields, count);
    }

    /**
     * returns info about artist given artist name
     * @param artist the wanted artists name
     * @return the given artists info
     */
    DataContainer getArtists(ArtistContainer artist) throws SQLException {
        String[] fields = {"*"};
        String[] tables = {"artist"};
        // initialize the query
        QueryBuilder builder = new QueryBuilder(fields, tables);
        // get songs with given duration plus - minus an amount of time in relation to the given duration
        builder.addWhere().addEqualStatements("artist_name", "\"" + artist.getValue() + "\"");
        String query = builder.build();
        // execute the query
        String[] res = Executor.executeQuery(myStatement, query, allArtistFields);
        String[] countField = {"count(*)"};
        // execute count query on the previous query
        int count = Integer.parseInt(Executor.executeQuery(myStatement, builder.addCount(query), countField)[0]);
        return new DataContainer(res, allArtistFields, count);
    }

    /****
     * return all info about a given artist
     * @param song an song container
     * @return dataContainer of all info about the artist
     * @throws SQLException if the query failed
     */
    DataContainer getArtists(SongIdContainer song) throws SQLException {
        // initialize the query
        String query = "select distinct * from song, album_song, artist_album, artist " +
                "where song.song_id=\"" + song.getValue() + "\" " +
                "and album_song.song_id=song.song_id " +
                "and artist.artist_id=artist_album.artist_id " +
                "and artist_album.album_id=album_song.album_id;";
        // execute the query
        String[] res = Executor.executeQuery(myStatement, query, allArtistFields);
        int count = res.length;
        return new DataContainer(res, allArtistFields, count);
    }
}