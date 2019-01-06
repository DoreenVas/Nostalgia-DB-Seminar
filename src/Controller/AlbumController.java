package Controller;

import Resources.*;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;

public class AlbumController extends ControllerAbstract {

    public AlbumController() throws IOException, SQLException {
    }

    @Override
    public TableInfo getInfoFromGUI(Map<String, ArrayList<String>> infoFromGUI) throws SQLException {
        AlbumQueryInfo queryInfo = new AlbumQueryInfo();
        String temp;
        DataContainer dc = null;

        for (Map.Entry<String, ArrayList<String>> entry : infoFromGUI.entrySet()) {
            switch (entry.getKey()) {
                case "album_name":
                    temp = matchStringToPattern(entry.getValue().get(0));
                    AlbumContainer albumContainer = new AlbumContainer(temp);
                    queryInfo.setAlbum(albumContainer);
                    break;
                case "artist_name":
                    temp = matchStringToPattern(entry.getValue().get(0));
                    ArtistContainer artistContainer = new ArtistContainer(temp);
                    queryInfo.setArtist(artistContainer);
                    break;
                case "song_name":
                    temp = matchStringToPattern(entry.getValue().get(0));
                    SongIdContainer song = new SongIdContainer(temp);
                    queryInfo.setSong(song);
                    break;
            }
        }

        dc = model.getData(queryInfo);

        //create and return the TableInfo
        return createTableInfo(dc);
    }


    /**
     * @param dc
     * @return
     */
    private TableInfo createTableInfo(DataContainer dc) {
        int row = dc.getCount();
        int col = dc.getColumns().length;
        ArrayList<String> fields = parseToArrayList(dc.getColumns());
        ArrayList<ArrayList<ArrayList<String>>> data = new ArrayList<>();
        ArrayList<ArrayList<String>> tempArrayList = new ArrayList<>();

        tempArrayList.add(parseToArrayList(dc.getData()));
        data.add(tempArrayList);

        TableInfo ti = new TableInfo(col, row, fields, data);
        return ti;
    }
}