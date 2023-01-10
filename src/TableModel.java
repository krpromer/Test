import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;

public class TableModel extends AbstractTableModel {

    TableRecord tableRecord;

    public TableModel(TableRecord tableRecord){
        this.tableRecord = tableRecord;
    }

    public void setList(TableRecord tableRecord){
        this.tableRecord = tableRecord;
    }

    @Override
    public int getRowCount() {
        return tableRecord.getSize();
    }

    @Override
    public int getColumnCount() {
        return tableRecord.getColumnSize();
    }

    @Override
    public Object getValueAt(int row, int col) {
        return tableRecord.getTableRow(row).getTableDataByIndex(col).getValue();
    }


    // if autoRowSorter enable -> need to set class to sort
    @Override
    public Class<?> getColumnClass(int columnIndex) {
        if (columnIndex == 0)
            return Integer.class;
        return super.getColumnClass(columnIndex);
    }
}
