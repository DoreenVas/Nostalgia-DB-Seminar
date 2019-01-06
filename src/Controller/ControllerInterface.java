package Controller;

import Resources.TableInfo;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;

public interface ControllerInterface {
    TableInfo getInfoFromGUI(Map<String, ArrayList<String>> infoFromGUI) throws SQLException;
    void closeModelConnection() throws SQLException;
    void openModelConnection() throws SQLException;
}
