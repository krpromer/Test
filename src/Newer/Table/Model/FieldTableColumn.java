package Newer.Table.Model;

import javax.swing.table.TableColumn;

public class FieldTableColumn extends TableColumn {

    int field;
    public FieldTableColumn(int modelIndex, int field){
        super(modelIndex);
        this.field = field;
    }

    public int getField() {
        return field;
    }

    @Override
    public String toString() {
        return "Field = " + field;
    }
}
