import java.util.ArrayList;

public class TableRow {

    ArrayList<TableData> tableDataList;

    public TableRow(){
        tableDataList = new ArrayList<>();
    }

    public void addTableData(TableData tableData){
        tableDataList.add(tableData);
    }

    public TableData getTableDataByIndex(int index){
        return tableDataList.get(index);
    }

    public TableData getTableDataByField(int field){
        for(TableData tableData : tableDataList){
            if (tableData.getField() == field)
                return tableData;
        }
        return null;
    }

    public int getSize(){
        return tableDataList.size();
    }
}
