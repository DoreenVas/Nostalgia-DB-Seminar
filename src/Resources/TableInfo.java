package Resources;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class TableInfo {

    //number of info columns
    private int colsNum;
    // number of info rows
    private int rowsNum;
    // the given fields in the info, such as name, etc...
    private ArrayList<String> fields;
    /*
    the values of the info. each cell is a line, in which the values are in the order of the
    fields.
    for example, the fields: [name, id,....] the values will be: [[name,id...],[name,id...]...]
     */
    private ArrayList<ArrayList<String>> fieldsValues;

    //dictionary from the gui

    public TableInfo(int colsNum, int rowsNum, ArrayList<String> fields, ArrayList<ArrayList<String>> fieldsValues){
        this.colsNum = colsNum;
        this.rowsNum = rowsNum;
        this.fields = fields;
        this.fieldsValues = fieldsValues;
    }

    public int getColsNum(){
        return this.colsNum;
    }

    public int getRowsNumNum(){
        return this.rowsNum;
    }

    public ArrayList<String> getFields(){
        return this.fields;
    }

    public ArrayList<ArrayList<String>> getFieldsValues(){
        return this.fieldsValues;
    }

    public void setColsNum(int col){
        this.colsNum = col;
    }

    public void setRowsNum(int row){
        this.rowsNum = row;
    }

    public void addField(String field){
        this.fields.add(field);
    }

    public void addValue(ArrayList<String> val){
        this.fieldsValues.add(val);
    }

    //public void addFieldsAndValues(String )
}
