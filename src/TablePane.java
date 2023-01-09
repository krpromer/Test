import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class TablePane extends JPanel implements DocumentListener {

    JScrollPane jScrollPane;
    JTable jTable;
    TableModel tableModel;
    TableColumnModel tableColumnModel;
    ArrayList<JTextField> filterTextFieldList;
    Filter filter;
    TableRecord tableRecord;

    public TablePane(TableRecord tableRecord){
        this.tableRecord = tableRecord;
        L.d(tableRecord.getColumnSize());
        init();
    }

    void init() {
        setLayout(new BorderLayout());
        jTable = new JTable();
        tableModel = new TableModel(tableRecord);
        tableColumnModel = new DefaultTableColumnModel();
        setColumns();
        jTable.setModel(tableModel);
        jTable.setColumnModel(tableColumnModel);
        filterTextFieldList = new ArrayList<>();
        setFilterTextField();
        filter = new Filter(tableRecord, filterTextFieldList, listener);
        jScrollPane = new JScrollPane(jTable);
        jScrollPane.setColumnHeader(new JViewport() {
            @Override
            public void setView(Component view) {
                L.d();
                JPanel pane = new JPanel(new BorderLayout());
                JTextField highliter = new JTextField();
                TitledBorder border = BorderFactory.createTitledBorder("Highlighter");
                border.setTitlePosition(TitledBorder.TOP);
                highliter.setBorder(border);
                pane.add(highliter, BorderLayout.NORTH);
                pane.add(jTable.getTableHeader(), BorderLayout.CENTER);
                JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
                for(JTextField jTextField : filterTextFieldList)
                    panel.add(jTextField);

                //pane.add(panel, BorderLayout.SOUTH);
//                JPanel utilPane = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
                JPanel utilPane = new JPanel();

                JCheckBox box = new JCheckBox("Mark Only");
               // box.setVerticalTextPosition(SwingConstants.TOP);
                utilPane.add(box);
                JSeparator js = new JSeparator();
                js.setOrientation(JSeparator.VERTICAL);
                Dimension d = js.getPreferredSize();
                d.height = box.getPreferredSize().height;
                js.setPreferredSize(d);

                utilPane.add(js);
                utilPane.add(new JTextField("test"));
                pane.add(utilPane, BorderLayout.SOUTH);
                super.setView(pane);
            }
        });
        add(jScrollPane, BorderLayout.CENTER);
    }

    void setColumns(){
        L.d("Column Size = " + tableRecord.getColumnSize());
        for(int i = 0; i < tableRecord.getColumnSize(); i++){
            TableColumn tableColumn = new TableColumn(i);
            tableColumn.setWidth(50 + i * 10);
            tableColumn.setPreferredWidth(50 + i * 10);
            tableColumn.setHeaderValue(String.valueOf(i));
            tableColumnModel.addColumn(tableColumn);
        }
    }
    void setFilterTextField(){
        int columnSize = tableRecord.getColumnSize();
        for(int i = 0; i < columnSize; i++){
            JTextField jTextField = new JTextField();
            jTextField.getDocument().addDocumentListener(this);
            TableColumn tableColumn = tableColumnModel.getColumn(i);
            jTextField.setSize(tableColumn.getPreferredWidth(), 20);
            jTextField.setPreferredSize(new Dimension(tableColumn.getWidth(), 20));
            filterTextFieldList.add(jTextField);
        }
    }
    Filter.ParseListener listener = new Filter.ParseListener() {
        @Override
        public void onParseCompleted(TableRecord tableRecord) {
            invalidate();
            tableModel.setList(tableRecord);
            repaint();
            jTable.changeSelection(0, 0, false, false);
        }
    };

    // For filterTextField
    @Override
    public void insertUpdate(DocumentEvent documentEvent) {
        filter.startFilter();
    }

    @Override
    public void removeUpdate(DocumentEvent documentEvent) {
        filter.startFilter();
    }

    @Override
    public void changedUpdate(DocumentEvent documentEvent) {

    }
}
