import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainFrame extends JFrame {
    TablePane tp;
    public MainFrame(){
        tp = new TablePane(getTestRecord());
        setContentPane(tp);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(800, 600);
        setVisible(true);

        JMenuBar menuBar = new JMenuBar();
        JMenu ji = new JMenu("Settings");
        JMenuItem jim = new JMenuItem("Filter Options");
        jim.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                String select =
                        (String) JOptionPane.showInputDialog(null, "Select", "Filter options",
                                JOptionPane.PLAIN_MESSAGE, null, Filter.filterOptions, Filter.filterOption);
                if (select != null) {
                    Filter.filterOption = select.equals(Filter.filterOptions[0]) ? Filter.FILTER_AND : Filter.FILTER_OR;
                    tp.startFilter();
                }
            }
        });

        ji.add(jim);
        menuBar.add(ji);
        setJMenuBar(menuBar);
//        String frame = (String) JOptionPane.showInputDialog(this, "Input frame number", "Go To Line", JOptionPane.PLAIN_MESSAGE, null, (Object[])null, (Object) null);
//        L.d(frame);

    }

    private TableRecord getTestRecord(){
        TableRecord tableRecord = new TableRecord();
        for(int i = 0; i < 10000; i++){
            TableData tableData1 = new TableData(F.FRAME, i);
            TableData tableData2 = new TableData(F.MESSAGE, L.makeRandomString());
            TableData tableData3 = new TableData(F.INDEX, i);
            TableRow tableRow = new TableRow();
            tableRow.addTableData(tableData1);
            tableRow.addTableData(tableData2);
            tableRow.addTableData(tableData3);
            tableRecord.addTableRow(tableRow);
        }
        L.d(tableRecord.getColumnSize());
        return tableRecord;
    }
}
