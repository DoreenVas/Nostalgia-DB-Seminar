package DBConnection;

import Resources.AlbumContainer;
import Resources.ArtistContainer;
import Resources.DataContainer;
import Resources.GenreContainer;

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

    private AlbumQueries() {

    }
}
