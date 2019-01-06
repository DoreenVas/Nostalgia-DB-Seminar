package Controller;

import DBConnection.DBModel;
import DBConnection.Model;
import Resources.TableInfo;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;

public abstract class ControllerAbstract implements ControllerInterface {
    public static Model model;

    public ControllerAbstract() throws IOException,SQLException {
        this.model = new DBModel();
        this.openModelConnection();
    }

    public void closeModelConnection() throws SQLException {
        this.model.closeConnection();
    }

    public void openModelConnection() throws SQLException{
        this.model.openConnection();
    }

    /**
     *
     * @param str
     * @return change all letters to lower case except for the first letter,
     * which should be upper case.
     */
    public String matchStringToPattern(String str){
        str = str.toLowerCase();
        str = Character.toUpperCase(str.charAt(0)) + str.substring(1);
        return str;
    }

    /**
     *
     * @param size
     * @param arr
     * @return a string representation of a given string arraylist
     */
    public String[] parseFromArrayToList(int size, ArrayList<String> arr){
        String[] str = new String[size];
        for(int i = 0; i < size; i++){
            str[i] = arr.get(i);
        }
        return str;
    }


    /**
     *
     * @param str
     * @return parse a given string list into arraylist
     */
    public ArrayList<String> parseToArrayList(String[] str){
        ArrayList<String> newArrayList = new ArrayList<String>();
        int len = str.length;
        String[] temp = null;
        for(int i = 0; i < len; i++){
            if(str[i].contains(".")) {
                temp = str[i].replace(".", " ").split(" ");
                newArrayList.add(temp[1]);
            } else {
                newArrayList.add(str[i]);
            }
        }
        return newArrayList;
    }
}
