import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;

public class MainFrame extends JFrame {

    public MainFrame(){
        setContentPane(new TablePane(getTestRecord()));
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(800, 600);
        setVisible(true);
    }

    private TableRecord getTestRecord(){
        TableRecord tableRecord = new TableRecord();
        for(int i = 0; i < 10000; i++){
            TableData tableData1 = new TableData(F.FRAME, i);
            TableData tableData2 = new TableData(F.MESSAGE, L.makeRandomString());
            TableRow tableRow = new TableRow();
            tableRow.addTableData(tableData1);
            tableRow.addTableData(tableData2);
            tableRecord.addTableRow(tableRow);
        }
        L.d(tableRecord.getColumnSize());
        return tableRecord;
    }
}
