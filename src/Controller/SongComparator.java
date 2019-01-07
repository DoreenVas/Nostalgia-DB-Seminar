package Controller;

import java.util.ArrayList;
import java.util.Comparator;

/***
 * A song comparator class
 */
public class SongComparator implements Comparator<ArrayList<String>> {
    // members
    private int index1;
    private int index2;
    private int index3;

    private float wantedResult1;
    private float wantedResult2;
    private float wantedResult3;

    /***
     * Constructor
     */
    public SongComparator(){
        this.wantedResult1 = -1;
        this.wantedResult2 = -1;
        this.wantedResult3 = -1;
    }

    /***
     * the function determines given to 2 rows from a table which one comes first
     * @param tableVal1 a row from a table
     * @param tableVal12 a row from a table
     * @return 1 if the first row comes first, -1 if the second row comes first and 0 if both rows are equal
     */
    public int compare(ArrayList<String> tableVal1, ArrayList<String> tableVal12){
        // calculate the "distance" of the rows
        float dist1 = calculateDistance(tableVal1);
        float dist2 = calculateDistance(tableVal12);
        if(dist1 > dist2){
            return 1;
        }
        else if (dist1 < dist2){
            return -1;
        }
        return 0;
    }

    /***
     * sets the index of the first row
     * @param index1 the index of the first row
     */
    public void setIndex1(int index1) {
        this.index1 = index1;
    }

    /***
     * sets the index of the second row
     * @param index2 the index of the second row
     */
    public void setIndex2(int index2) {
        this.index2 = index2;
    }

    /***
     * sets the index of the third row
     * @param index3 the index of the third row
     */
    public void setIndex3(int index3) {
        this.index3 = index3;
    }

    /***
     * sets the wanted result from the first row
     * @param wantedResult1 the wanted result from the first row
     */
    public void setWantedResult1(float wantedResult1) {
        this.wantedResult1 = wantedResult1;
    }

    /***
     * sets the wanted result from the second row
     * @param wantedResult2 the wanted result from the second row
     */
    public void setWantedResult2(float wantedResult2) {
        this.wantedResult2 = wantedResult2;
    }

    /***
     * sets the wanted result from the third row
     * @param wantedResult3 the wanted result from the third row
     */
    public void setWantedResult3(float wantedResult3) {
        this.wantedResult3 = wantedResult3;
    }

    /***
     * the function calculates the distance of the row
     * @param val the given row
     * @return the distance of the row
     */
    private float calculateDistance(ArrayList<String> val){
        float result1 = 0;
        float result2 = 0;
        float result3 = 0;

        if(this.index1 > -1 && this.wantedResult1 > -1){
            result1 = Math.abs(this.wantedResult1 - Float.parseFloat(val.get(this.index1)));
        }

        if(this.index2 > -1 && this.wantedResult2 > -1){
            result2 = Math.abs(this.wantedResult2 - Float.parseFloat(val.get(this.index2)));
        }

        if(this.index3 > -1 && this.wantedResult3 > -1){
            result3 = Math.abs(this.wantedResult3 - Float.parseFloat(val.get(this.index3)));
        }

        float dist = result1 + result2 + result3;
        return dist;
    }
}
