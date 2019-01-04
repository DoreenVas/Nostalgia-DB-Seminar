package DBConnection;

import Resources.*;

import java.sql.SQLException;
import java.sql.Statement;

class ArtistQueries {
    private static ArtistQueries ourInstance = new ArtistQueries();
    private static Statement myStatement;
    private static String[] allArtistFields = {/*"artist.artist_id", */"artist.artist_name", "artist.familiarity", "artist.hotness"};

    static ArtistQueries getInstance(Statement statement) {
        myStatement = statement;
        return ourInstance;
    }

    private ArtistQueries() {

    }

//    DataContainer getArtists(GenreContainer genre, ArtistContainer artist, AlbumContainer album)
//            throws SQLException {
//
//    }
//
//    DataContainer getArtists(GenreContainer genre, ArtistContainer artist) throws SQLException {
//
//    }
//
//    DataContainer getArtists(GenreContainer genre, AlbumContainer album) throws SQLException {
//
//    }
//
//    DataContainer getArtists(ArtistContainer artist, AlbumContainer album) throws SQLException {
//
//    }

    private StringBuilder attachGenreQueryToSArtistQuery(String artistQuery, GenreContainer genre) {
        StringBuilder query = new StringBuilder("select * from artist, " +
                "(select artist_id from artist_genre, " +
                "(select genre_id from genre " +
                "where ");
        String genres[] = genre.getValue();
        for (int i = 0; i < genres.length - 1; i++) {
            query.append("genre_name=\"").append(genres[i]).append("\" or ");
        }
        query.append("genre_name=\"").append(genres[genres.length - 1]).append("\"");
        query.append(") as ID " +
                "where artist_genre.genre_id=ID.genre_id) as AID " +
                "where AID.artist_id=artist.artist_id ");
        query.append(artistQuery);
        return query;
    }

    DataContainer getArtists(GenreContainer genre, String artistQuery) throws SQLException {


        // *** needs checking***


        StringBuilder query = attachGenreQueryToSArtistQuery(artistQuery, genre);
        String[] res = Executor.executeQuery(myStatement, query.toString(), allArtistFields);
        int count = res.length;
        return new DataContainer(res, allArtistFields, count);
    }

    DataContainer getArtists(AlbumContainer album) throws SQLException {


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
     *
     * @param artist the wanted artists name
     * @return the given artists info
     */
    DataContainer getArtists(ArtistContainer artist) throws SQLException {
        String[] fields = {"*"};
        String[] tables = {"artist"};
        QueryBuilder builder = new QueryBuilder(fields, tables);
        // get songs with given duration plus - minus an amount of time in relation to the given duration
        builder.addWhere().addEqualStatements("artist_name", "\"" + artist.getValue() + "\"");
        String query = builder.build();
        String[] res = Executor.executeQuery(myStatement, query, allArtistFields);
        String[] countField = {"count(*)"};
        int count = Integer.parseInt(Executor.executeQuery(myStatement, builder.addCount(query), countField)[0]);
        return new DataContainer(res, allArtistFields, count);
    }

    DataContainer getArtists(SongContainer song) throws SQLException {
        String query = "select distinct * from song, album_song, artist_album, artist " +
                "where song.name=\"" + song.getValue() + "\" " +
                "and album_song.song_id=song.song_id " +
                "and artist.artist_id=artist_album.artist_id " +
                "and artist_album.album_id=album_song.album_id;";
        String[] res = Executor.executeQuery(this.myStatement, query, allArtistFields);
        int count = res.length;
        return new DataContainer(res, allArtistFields, count);
    }
}