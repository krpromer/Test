import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;

public class MainFrame extends JFrame {
    Table table;
    JScrollPane scrollPane;
    JTextField filterField;

    public MainFrame(){
        L.d("Test");

        table = new Table();
        scrollPane = new JScrollPane(table);
        filterField = new JTextField();

        RowNumberTable rowTable = new RowNumberTable(table);
        scrollPane.setRowHeaderView(rowTable);
        scrollPane.setCorner(JScrollPane.UPPER_LEFT_CORNER,
                rowTable.getTableHeader());
        scrollPane.setColumnHeader(new JViewport(){
            @Override
            public void setView(Component view) {
                JPanel pane = new JPanel(new BorderLayout());
                pane.add(table.getTableHeader(), BorderLayout.NORTH);
                pane.add(filterField, BorderLayout.CENTER);
                super.setView(pane);
            }
        } );

        setContentPane(scrollPane);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(800, 600);
        setVisible(true);

        filterField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent documentEvent) {
                L.d(filterField.getText().toString());
                table.setFilterString(filterField.getText().toString());
            }

            @Override
            public void removeUpdate(DocumentEvent documentEvent) {
                L.d(filterField.getText().toString());
                table.setFilterString(filterField.getText().toString());
            }

            @Override
            public void changedUpdate(DocumentEvent documentEvent) {

            }
        });

    }
}
