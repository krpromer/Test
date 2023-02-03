package Newer.Table.Model;

import Newer.Table.Data.Item;
import Newer.Table.Data.Record;
import Newer.Table.Data.Table;
import Newer.Table.L;

import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.TableColumnModel;
import java.util.HashSet;
import java.util.Iterator;

public class FieldTableModel extends AbstractTableModel {

    private Table table;
    private TableColumnModel tableColumnModel;

    /* Options */
    private boolean isAutoHeader;

    public FieldTableModel(Table table){
        this.table = table;
        init();
    }

    private void init(){
        initOptions();
        initHeader();
    }
    private void initOptions(){
        isAutoHeader = true;
    }

    private void initHeader(){
        tableColumnModel = new DefaultTableColumnModel();

        HashSet<Integer> headers = new HashSet<>();
        if (isAutoHeader) {
            for (Record record : table.getRecordlist()) {
                for (Item item : record.getItemList()) {
                    headers.add(item.getField());
                }
            }
            Iterator<Integer> it = headers.iterator();
            int modelIdx = 0;
            while (it.hasNext()) {
                FieldTableColumn column = new FieldTableColumn(modelIdx++, it.next());
                column.setHeaderValue("Test");
                tableColumnModel.addColumn(column);

                L.d(column);
            }
        }
    }
    public TableColumnModel getTableColumnModel() {
        return tableColumnModel;
    }

    @Override
    public int getRowCount() {
        return table.getSize();
    }

    @Override
    public int getColumnCount() {
        return tableColumnModel.getColumnCount();
    }

    @Override
    public String getColumnName(int column) {
        return "Test";
    }

    @Override
    public Object getValueAt(int rowIdx, int colIdx) {
        FieldTableColumn column = (FieldTableColumn) tableColumnModel.getColumn(colIdx);
        int field = column.getField();
        L.d("col =" + colIdx + " field =" + field);
        return table.getRecord(rowIdx).getItemByField(field).getStr();
    }

    public void setAutoHeader(boolean autoHeader) {
        isAutoHeader = autoHeader;
    }
}
