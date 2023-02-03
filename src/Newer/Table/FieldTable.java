package Newer.Table;

import Newer.Table.Data.Table;
import Newer.Table.Model.FieldTableModel;

import javax.swing.*;
import javax.swing.table.JTableHeader;

public class FieldTable extends JTable{

    private Table table;
    private FieldTableModel keyTableModel;

    public FieldTable(Table table){
        this.table = table;
        initGui();
    }

    private void initGui(){
        keyTableModel = new FieldTableModel(table);
        setFieldTableModel(keyTableModel);
    }

    private void setFieldTableModel(FieldTableModel keyTableModel){
        setModel(keyTableModel);
        setColumnModel(keyTableModel.getTableColumnModel());
    }
}
