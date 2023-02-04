package Newer.Table.Model;

import Newer.Table.Field;

import javax.swing.table.TableColumn;

public class FieldTableColumn extends TableColumn {

    int field;

    public FieldTableColumn(int modelIndex, int field){
        super(modelIndex);
        this.field = field;
        init();
    }

    private void init(){
        setHeaderValue(Field.getHeaderName(field));
        setColumnWidth(Field.getHeaderWidth(field));
    }

    public void setColumnWidth(int width){
        setWidth(width);
        setPreferredWidth(width);
    }

    public int getField() {
        return field;
    }

    @Override
    public String toString() {
        return "[Header] Field = " + field + ", Name = " + getHeaderValue();
    }
}
