
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
import java.util.concurrent.ExecutionException;
import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingWorker;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;

public class TestTableLoad01 {

    public static void main(String[] args) {
        new TestTableLoad01();
    }

    public TestTableLoad01() {
        JTable table;
        TableSwingWorker worker;
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                final MyTableModel model = new MyTableModel();
                final MyTableModel model2 = new MyTableModel();
                JTable table = new JTable(model);
                JTable table2 = new JTable(model);
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
                        ArrayList<RowData> datas = new ArrayList<>();
                        for (int index = 0; index < 10000000; index++) {
                            datas.add(new RowData(index));
                        }

                        TableSwingWorker worker = new TableSwingWorker(table, model, datas, new TableSwingWorker.Listener() {
                            @Override
                            public void onCompleted() {
                                L.d("completed");
                            }
                        });
                        worker.execute();
                        //TableSwingWorker worker2 = new TableSwingWorker(model2,  "two");
                        //worker2.execute();
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
            return col == 0 ? value.getDate() : value.getValue().split(" ")[1];
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
        private String value;

        public RowData(int row) {
            this.date = new Date();
            this.row = row;
            this.value = row + " " + String.valueOf(row + row);
        }

        public Date getDate() {
            return date;
        }

        public int getRow() {
            return row;
        }

        public String getValue() {
            return value;
        }
    }

    public class TableSwingWorker extends SwingWorker<MyTableModel, RowData> {

        public interface Listener {
            void onCompleted();
        }
        Listener listener;
        private ArrayList<RowData> datas;
        private int count;
        private final MyTableModel tableModel;

        private JTable table;
        public TableSwingWorker(JTable table, MyTableModel tableModel, ArrayList<RowData> datas, Listener listener) {
            this.table = table;
            this.datas = datas;
            this.count = datas.size();
            this.tableModel = tableModel;
            this.listener = listener;
        }

        @Override
        protected MyTableModel doInBackground() throws Exception {

            // This is a deliberate pause to allow the UI time to render
            Thread.sleep(1000);
            System.out.println("Start polulating");

            for(int i = 0; i < datas.size(); i++){
                publish(datas.get(i));
                Thread.yield();
            }
            return tableModel;
        }

        @Override
        protected void process(List<RowData> chunks) {
            count -= chunks.size();
            Runtime r = Runtime.getRuntime();
            L.d("free = " + r.freeMemory());
            System.out.println(" - Adding (" + chunks.size() + " / " + count + ") rows" + " " + r.totalMemory() / 1000 / 1000 + " MB");
            tableModel.addRows(chunks);
            table.changeSelection(tableModel.getRowCount() - 1, 0, false, false);
            if (count <= 0)
                listener.onCompleted();
        }
    }
}
