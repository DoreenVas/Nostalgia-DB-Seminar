package DBConnection;

import Resources.DataContainer;
import Resources.SongIdContainer;

import java.sql.SQLException;
import java.sql.Statement;

public class AlbumQueries {
    private static AlbumQueries ourInstance = new AlbumQueries();
    private static Statement myStatement;
    private static String[] allAlbumFields = {"album.album_id", "album.album_name"};

    public static AlbumQueries getInstance(Statement statement) {
        myStatement = statement;
        return ourInstance;
    }

    public DataContainer getAlbum(SongIdContainer songContainer) throws SQLException{
        String query = "select distinct * from song, album_song, album " +
                "where album.album_id=album_song.album_id" +
                "and album_song.song_id=song.song_id " +
                "and song.song_id=\"" + songContainer.getValue() + "\" ";
        String[] res = Executor.executeQuery(myStatement, query, allAlbumFields);
        int count = res.length;
        return new DataContainer(res, allAlbumFields, count);
    }

    private AlbumQueries() {

    }
}
