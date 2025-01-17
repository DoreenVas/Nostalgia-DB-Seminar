package Controller;

import DBConnection.DBModel;
import DBConnection.Model;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

/***
 * An abstract class for the controller
 */
public abstract class ControllerAbstract implements ControllerInterface {
    // members
    public static Model model;

    /***
     * Constructor for singleton
     * @throws IOException
     */
    public ControllerAbstract() throws IOException {
        model = new DBModel();
    }

    /***
     * closes the connection to the database
     * @throws SQLException if closing the connection failed
     */
    public void closeModelConnection() throws SQLException {
        model.closeConnection();
    }

    /***
     * opens the connection to the database
     * @throws SQLException if opening the connection to the database failed
     */
    public void openModelConnection() throws SQLException{
        model.openConnection();
    }

    /**
     *  change all letters to lower case except for the first letter,
     *  which should be upper case.
     * @param str the string
     * @return the string where letters are modified to lower case except for the first letter,
     * which should be upper case.
     */
    protected String matchStringToPattern(String str){
        str = str.toLowerCase();
        str = Character.toUpperCase(str.charAt(0)) + str.substring(1);
        return str;
    }

    /**
     *
     * @param size the size of the array
     * @param arr the array
     * @return a string representation of a given string arraylist
     */
    protected String[] parseFromArrayToList(int size, ArrayList<String> arr){
        String[] str = new String[size];
        for(int i = 0; i < size; i++){
            str[i] = arr.get(i);
        }
        return str;
    }

    /**
     *
     * @param str the string
     * @return parse a given string list into arraylist
     */
    protected ArrayList<String> parseToArrayList(String[] str){
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

    /**
     *
     * @param str
     * @return  an unordered arraylist that contains in each cell an arraylists of 50
     * rows. each row is an arraylist of results.
     */
    private ArrayList<ArrayList<ArrayList<String>>> resultsOfSearchUnordered(String[] str){
        ArrayList<ArrayList<ArrayList<String>>> newArrayList = new ArrayList<>();
        ArrayList<ArrayList<String>> tempArrayList = new ArrayList<>();
        ArrayList<String> newRow = null;

        String[] temp = null;
        int countRows = 0;
        for(int i = 0; i < str.length; i++){
            newRow = new ArrayList<String>();
            temp = str[i].split(",");
            for(int j = 0; j < temp.length; j++){
                newRow.add(j,temp[j]);
            }
            if(countRows >= 50){
                countRows = 0;
                newArrayList.add(tempArrayList);
                tempArrayList = new ArrayList<>();
            }
            tempArrayList.add(countRows,newRow);
            countRows++;
        }

        if(tempArrayList.size() > 0){
            newArrayList.add(tempArrayList);
        }

        return newArrayList;
    }
}
