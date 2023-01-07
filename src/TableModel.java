import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;

public class TableModel extends AbstractTableModel {

    ArrayList<String> list;

    public TableModel(ArrayList<String> list){
        this.list = list;
    }

    public void setList(ArrayList<String> list) {
        this.list = list;
    }

    @Override
    public int getRowCount() {
        return list.size();
    }

    @Override
    public int getColumnCount() {
        return 1;
    }

    @Override
    public Object getValueAt(int row, int col) {
        return list.get(row);
    }

    @Override
    public String getColumnName(int column) {
        return "TestColumn";
    }
}
