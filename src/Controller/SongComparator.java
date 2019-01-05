package Controller;

import java.util.ArrayList;
import java.util.Comparator;

public class SongComparator implements Comparator<ArrayList<String>> {

    private int index1;
    private int index2;
    private int index3;

    private float wantedResult1;
    private float wantedResult2;
    private float wantedResult3;

    public SongComparator(){
        this.wantedResult1 = -1;
        this.wantedResult2 = -1;
        this.wantedResult3 = -1;
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

    public void setWantedResult1(float wantedResult1) {
        this.wantedResult1 = wantedResult1;
    }

    public void setWantedResult2(float wantedResult2) {
        this.wantedResult2 = wantedResult2;
    }

    public void setWantedResult3(float wantedResult3) {
        this.wantedResult3 = wantedResult3;
    }

    private float calculateDistance(ArrayList<String> val){
        float result1 = 0;
        float result2 = 0;
        float result3 = 0;

        if(this.index1 > -1 && this.wantedResult1 > -1){
            result1 = Math.abs(this.wantedResult1) -
                    Math.abs(Float.parseFloat(val.get(this.index1)));
        }

        if(this.index2 > -1 && this.wantedResult2 > -1){
            result2 = Math.abs(this.wantedResult2) -
                    Math.abs(Float.parseFloat(val.get(this.index2)));
        }

        if(this.index3 > -1 && this.wantedResult3 > -1){
            result3 = Math.abs(this.wantedResult3) -
                    Math.abs(Float.parseFloat(val.get(this.index3)));
        }

        float dist = result1 + result2 + result3;
        return dist;
    }
}
