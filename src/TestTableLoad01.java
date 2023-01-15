

Stack Overflow
Products
Delayed response to JTable row selection event under a huge data load
Asked 9 years, 6 months ago
Modified 9 years, 6 months ago
Viewed 3k times

5


I have a Swing JTable dynamically updated with a big amount of data—new rows are live added constantly, and some 1000-2000 rows can be added during a few minutes. I have registered a Listener to reponse for use's single row selection event to perform some staff. I have used Observer pattern for Swing data binding and table's model is backed by a WritableList implementation. So new items are added to the table from its own Realm. And Listener was added from the SWT UI thread. The problem is, that when new rows are added to table, it doesn't respond at once on user row selection event. Only when stop updating table model, table will respond on user selection- some times with delay more then 30-60 seconds. Please, help me undersand why my table model doesn't respond at once to user selection when intensively updated, and how to overcome this limitation. Any suggestions will be appreciated.

javaswing
Share
Follow
edited Jun 29, 2013 at 20:10
trashgod's user avatar
trashgod
202k2929 gold badges242242 silver badges10231023 bronze badges
asked Jun 29, 2013 at 19:08
kioria's user avatar
kioria
5311 silver badge55 bronze badges
Add a comment
1 Answer
Sorted by:

Highest score (default)

12


Use SwingWorker to publish() your rows in the background and update the TableModel in your implementation of process(). SwingWorker will limit updates to a sustainable rate. Profile to ensure that you are not blocking the event dispatch thread.

Addendum: The GUI remains responsive with this 1,000,000-row variation, as tested. When profiling, note that each click of the "Go" button starts a new worker.

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingWorker;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;

/** @see https://stackoverflow.com/a/17415635/230513 */
public class TestTableLoad01 {

    public static void main(String[] args) {
        new TestTableLoad01();
    }

    public TestTableLoad01() {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                final MyTableModel model = new MyTableModel();
                JTable table = new JTable(model);
                table.setDefaultRenderer(Date.class, new TimeCellRenderer());

                JFrame frame = new JFrame("Testing");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setLayout(new BorderLayout());
                frame.add(new JScrollPane(table));
                frame.pack();
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
                frame.add(new JButton(new AbstractAction("Go") {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        TableSwingWorker worker = new TableSwingWorker(model);
                        worker.execute();
                    }
                }), BorderLayout.SOUTH);
            }
        });
    }

    public class TimeCellRenderer extends DefaultTableCellRenderer {

        private DateFormat df;

        public TimeCellRenderer() {
            df = new SimpleDateFormat("HH:mm:ss");
        }

        @Override
        public Component getTableCellRendererComponent(JTable table,
            Object value, boolean isSelected, boolean hasFocus, int row, int column) {

            if (value instanceof Date) {

                value = df.format(value);

            }

            super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

            return this;

        }
    }

    public class MyTableModel extends AbstractTableModel {

        private String[] columnNames = new String[]{"Date", "Row"};
        private List<RowData> data;

        public MyTableModel() {
            data = new ArrayList<RowData>(25);
        }

        @Override
        public Class<?> getColumnClass(int columnIndex) {
            return columnIndex == 0 ? Date.class : Integer.class;
        }

        @Override
        public String getColumnName(int col) {
            return columnNames[col];
        }

        @Override
        public int getColumnCount() {
            return columnNames.length;
        }

        @Override
        public int getRowCount() {
            return data.size();
        }

        @Override
        public Object getValueAt(int row, int col) {
            RowData value = data.get(row);
            return col == 0 ? value.getDate() : value.getRow();
        }

        public void addRow(RowData value) {
            int rowCount = getRowCount();
            data.add(value);
            fireTableRowsInserted(rowCount, rowCount);
        }

        public void addRows(RowData... value) {
            addRows(Arrays.asList(value));
        }

        private void addRows(List<RowData> rows) {
            int rowCount = getRowCount();
            data.addAll(rows);
            fireTableRowsInserted(rowCount, getRowCount() - 1);
        }
    }

    public class RowData {

        private Date date;
        private int row;

        public RowData(int row) {
            this.date = new Date();
            this.row = row;
        }

        public Date getDate() {
            return date;
        }

        public int getRow() {
            return row;
        }
    }

    public class TableSwingWorker extends SwingWorker<MyTableModel, RowData> {

        private final MyTableModel tableModel;

        public TableSwingWorker(MyTableModel tableModel) {
            this.tableModel = tableModel;
        }

        @Override
        protected MyTableModel doInBackground() throws Exception {

            // This is a deliberate pause to allow the UI time to render
            Thread.sleep(1000);

            System.out.println("Start polulating");

            for (int index = 0; index < 1000000; index++) {

                RowData data = new RowData(index);
                publish(data);
                Thread.yield();
            }
            return tableModel;
        }

        @Override
        protected void process(List<RowData> chunks) {
            System.out.println("Adding " + chunks.size() + " rows");
            tableModel.addRows(chunks);
        }
    }
}
