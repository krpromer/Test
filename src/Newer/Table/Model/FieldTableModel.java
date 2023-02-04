package Newer.Table.Model;

import Newer.Table.Data.Item;
import Newer.Table.Data.Record;
import Newer.Table.Data.Table;

import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableColumnModel;
import java.util.HashSet;
import java.util.Iterator;

public class FieldTableModel extends AbstractTableModel {

    private Table table;
    private FieldTableColumnModel tableColumnModel;

    /* Options */
    private boolean isAutoHeader; // add all headers;

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
        tableColumnModel = new FieldTableColumnModel();
        HashSet<Integer> headers = new HashSet<>();
        if (isAutoHeader) {
            for (Record record : table.getRecordlist()) {
                for (Item item : record.getItemList()) {
                    headers.add(item.getField());
                }
            }
            Iterator<Integer> it = headers.iterator();
            while (it.hasNext()) {
                tableColumnModel.addColumn(it.next());
            }
        }else{
            for(Integer field : table.getHeaderFieldList())
                tableColumnModel.addColumn(field);
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
    public Object getValueAt(int rowIdx, int colIdx) {
        FieldTableColumn column = tableColumnModel.getColumn(colIdx);
        int field = column.getField();
        return table.getRecord(rowIdx).getItemByField(field).getStr();
    }

    public void setAutoHeader(boolean autoHeader) {
        isAutoHeader = autoHeader;
    }
}
