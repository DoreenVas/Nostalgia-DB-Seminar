package Controller;

import Resources.TableInfo;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;

public class ControllerAbstract implements ControllerInterface {

    @Override
    public TableInfo getInfoFromGUI(Map<String, ArrayList<String>> infoFromGUI) throws SQLException {
        return null;
    }
}
