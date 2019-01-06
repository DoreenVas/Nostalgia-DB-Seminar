package Controller;

import Resources.*;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Map;

public class ArtistController extends ControllerAbstract {

    public ArtistController() throws IOException, SQLException{

    }


    @Override
    public TableInfo getInfoFromGUI(Map<String, ArrayList<String>> infoFromGUI) throws SQLException {
        ArtistQueryInfo queryInfo = new ArtistQueryInfo();
        String temp;
        DataContainer dc = null;

        for (Map.Entry<String, ArrayList<String>> entry : infoFromGUI.entrySet())
        {
            switch (entry.getKey()){
                case "familiriaty":
                    float val = entry.getValue().size();
                    FamiliarityContainer familiarityContainer = new FamiliarityContainer(val);

                    break;
                case "artist_name":
                    temp = matchStringToPattern(entry.getValue().get(0));
                    ArtistContainer artistContainer = new ArtistContainer(temp);
                    queryInfo.setArtist(artistContainer);
                    break;
                case "popularity":
                    float popularity = Float.parseFloat(entry.getValue().get(0));
                    PopularityContainer popularityContainer = new PopularityContainer(popularity);
                    queryInfo.setPopularity(popularityContainer);

                    break;
                case "song_name":
                    temp = matchStringToPattern(entry.getValue().get(0));
                    SongContainer song = new SongContainer(temp);
                    queryInfo.setSong(song);
                    break;
            }
        }

        dc = model.getData(queryInfo);

        //create and return the TableInfo
        return createTableInfo(dc,duration,tempo,popularity,lyricsFlag);
    }



    /**
     *
     * @param dc
     * @param duration
     * @param tempo
     * @param popularity
     * @param shouldOrderResults
     * @return a TableInfo object
     */
    private TableInfo createTableInfo(DataContainer dc, float duration, float tempo,
                                      float popularity, boolean shouldOrderResults){
        int row = dc.getCount();
        int col = dc.getColumns().length;
        ArrayList<String> fields = parseToArrayList(dc.getColumns());
        ArrayList<ArrayList<ArrayList<String>>> data;

        //order the rows only if the next conditions are not true
        if(shouldOrderResults){
            data = resultsOfSearch(dc.getData());
        }
        else if((duration == -1 && tempo == -1 && popularity == -1)){
            data = resultsOfSearchUnordered(dc.getData());
        }
        else{
            data = resultsOfSearchOrdered(fields,dc.getData(),duration,tempo,popularity);
        }

        TableInfo ti = new TableInfo(col,row,fields,data);
        return ti;
    }
}
