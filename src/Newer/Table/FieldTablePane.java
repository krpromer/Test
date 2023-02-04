package Newer.Table;

import Newer.Table.Data.Item;
import Newer.Table.Data.Record;
import Newer.Table.Data.Table;

import javax.swing.*;
import java.awt.*;

public class FieldTablePane extends JPanel{

    JScrollPane jScrollPane;
    FieldTable fieldTable;

    public static FieldTablePane getInstance(){
        return new FieldTablePane();
    }

    public FieldTablePane(){
        initGui();
    }

    private void initGui(){
        setLayout(new BorderLayout());

        Table table = new Table();

        for(int i = 0; i < 100; i++){
            Record record = new Record();
            record.add(new Item(Field.FRAME, i), new Item(Field.TEST, "Test" + i));
            table.add(record);
        }


        fieldTable = new FieldTable(table);
        jScrollPane = new JScrollPane(fieldTable);
        add(jScrollPane, BorderLayout.CENTER);

    }
}
