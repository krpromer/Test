package Newer.Table.Model;

import javax.swing.table.DefaultTableColumnModel;
import java.util.ArrayList;

public class FieldTableColumnModel extends DefaultTableColumnModel {

    ArrayList<FieldTableColumn> columnList;

    public FieldTableColumnModel(){
        columnList = new ArrayList<>();
    }

    public void addColumn(int field) {
        FieldTableColumn column = new FieldTableColumn(columnList.size(), field);
        super.addColumn(column);
        columnList.add(column);
    }

    @Override
    public FieldTableColumn getColumn(int columnIndex) {
        return columnList.get(columnIndex);
    }
}
