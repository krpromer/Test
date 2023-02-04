package Newer.Table.Data;

import java.util.ArrayList;
import java.util.Collections;

public class Table {
    private ArrayList<Record> recordlist;
    private ArrayList<Integer> headerFieldList;

    public Table(){
        recordlist = new ArrayList<>();
        headerFieldList = new ArrayList<>();
    }

    public ArrayList<Record> getRecordlist() {
        return recordlist;
    }

    public ArrayList<Integer> getHeaderFieldList() {
        return headerFieldList;
    }

    public void add(Record ...records){
        Collections.addAll(recordlist, records);
    }

    public void addHeaderField(Integer ...headerFields){
        Collections.addAll(headerFieldList, headerFields);
    }
    public Record getRecord(int index){
        return recordlist.get(index);
    }

    public int getSize(){
        return recordlist.size();
    }
}
