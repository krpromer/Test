package Newer.Table;

import Newer.Table.Data.Table;
import Newer.Table.Model.FieldTableModel;

import javax.swing.*;
import java.awt.*;

public class FieldTable extends JTable{

    private Table table;
    private FieldTableModel fieldTableModel;

    public FieldTable(Table table){
        this.table = table;
        initGui();
    }

    private void initGui(){
        fieldTableModel = new FieldTableModel(table);
        setFieldTableModel(fieldTableModel);


        setOpaque(true);
        setIntercellSpacing(new Dimension(0, 0));
        setDoubleBuffered(true);
        setIgnoreRepaint(true);
        setShowGrid(false);
        setAutoResizeMode(AUTO_RESIZE_OFF);
        setRowSelectionAllowed(true);
    }

    private void setFieldTableModel(FieldTableModel keyTableModel){
        setModel(keyTableModel);
        setColumnModel(keyTableModel.getTableColumnModel());
    }
}
