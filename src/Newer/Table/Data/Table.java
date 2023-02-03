package Newer.Table.Data;

import java.util.ArrayList;
import java.util.Collections;

public class Table {
    private ArrayList<Record> recordlist;

    public Table(){
        recordlist = new ArrayList<>();
    }

    public ArrayList<Record> getRecordlist() {
        return recordlist;
    }

    public void add(Record ...records){
        Collections.addAll(recordlist, records);
    }

    public Record getRecord(int index){
        return recordlist.get(index);
    }

    public int getSize(){
        return recordlist.size();
    }
}
