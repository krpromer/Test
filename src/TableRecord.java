import java.util.ArrayList;

public class TableRecord {

    ArrayList<TableRow> record;
    private int columnSize;

    public TableRecord(){
        record = new ArrayList<>();
        columnSize = 0;
    }

    public int getColumnSize() {
        return columnSize;
    }

    public void addTableRow(TableRow tableRow){
        record.add(tableRow);
        int size = tableRow.getSize();
        if (columnSize < size)
            columnSize = size;
    }

    public TableRow getTableRow(int index){
        return record.get(index);
    }

    public int getSize(){
        return record.size();
    }

    public void clear(){
        record.clear();
    }
}
