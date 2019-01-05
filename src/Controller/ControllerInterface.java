package Controller;

import Resources.TableInfo;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;

public interface ControllerInterface {
    public TableInfo getInfoFromGUI(Map<String, ArrayList<String>> infoFromGUI) throws SQLException;
}
