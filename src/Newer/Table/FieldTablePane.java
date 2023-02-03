package Newer.Table;

import Newer.Table.Data.Item;
import Newer.Table.Data.Record;
import Newer.Table.Data.Table;

import javax.swing.*;
import java.awt.*;

public class FieldTablePane extends JPanel{

    JScrollPane jScrollPane;
    FieldTable keyTable;

    public static FieldTablePane getInstance(){
        return new FieldTablePane();
    }

    public FieldTablePane(){
        initGui();
    }

    private void initGui(){
        setLayout(new BorderLayout());

        Table table = new Table();
        Record record = new Record();
        record.add(new Item(Field.FRAME, 1), new Item(Field.TEST, "Test"));
        table.add(record);

        Record record2 = new Record();
        record2.add(new Item(Field.FRAME, 4), new Item(Field.TEST, "Test2"));
        table.add(record2);

        keyTable = new FieldTable(table);
        jScrollPane = new JScrollPane(keyTable);
        add(keyTable, BorderLayout.CENTER);

    }
}
