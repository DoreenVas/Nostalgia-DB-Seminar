package Controller;

import java.util.ArrayList;
import java.util.Comparator;

public class SongComparator implements Comparator<ArrayList<String>> {

    private int index1;
    private int index2;
    private int index3;

    public SongComparator(){

    }
    public int compare(ArrayList<String> tableVal1, ArrayList<String> tableVal12){
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

    public void setIndex1(int index1) {
        this.index1 = index1;
    }

    public void setIndex2(int index2) {
        this.index2 = index2;
    }

    public void setIndex3(int index3) {
        this.index3 = index3;
    }

    private float calculateDistance(ArrayList<String> val){
        float dist = Float.parseFloat(val.get(this.index1)) -
                Float.parseFloat(val.get(this.index2)) -
                Float.parseFloat(val.get(this.index3));
        return Math.abs(dist);
    }
}
