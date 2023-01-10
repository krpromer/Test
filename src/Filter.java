import javax.swing.*;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class Filter implements Runnable{


    public static final Object FILTER_AND = "AND";
    public static final Object FILTER_OR = "OR";

    public static Object filterOption = FILTER_OR;
    public static final Object[] filterOptions = {FILTER_AND, FILTER_OR};
    private static final int STATUS_READY = 0;
    private static final int STATUS_PARSING = 1;
    private static final int STATUS_CHANGE = 2;

    public interface ParseListener {
        public void onParseCompleted(TableRecord tableRecord);
    }
    Object FILTER_LOCK;
    TableRecord tableRecord;
    TableRecord filterTableRecord;
    ArrayList<JTextField> filterTextFieldList;
 //   private int filterType;
    private int status;
    ParseListener listener;
    Thread filterThread;
    public Filter(TableRecord tableRecord, ArrayList<JTextField> filterTextFieldList, ParseListener listener){
        this.tableRecord = tableRecord;
        this.filterTextFieldList = filterTextFieldList;
        this.listener = listener;
        filterTableRecord = new TableRecord();

        FILTER_LOCK = new Object();
        //filterType = FILTER_OR;
        status = STATUS_READY;
        filterThread = new Thread(this);
        filterThread.start();
    }

    public void startFilter(){
        status = STATUS_CHANGE;
        runFilter();
    }

    void runFilter()
    {
        while(status == STATUS_PARSING)
            try
            {
                Thread.sleep(100);
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
        synchronized(FILTER_LOCK)
        {
            FILTER_LOCK.notify();
        }
    }

    public boolean noFilterExist(){
        boolean exist = true;
        for(JTextField jTextField : filterTextFieldList){
            if (jTextField.getText().length() != 0)
                return false;
        }
        return exist;
    }
    @Override
    public void run() {
        try {
            while(true)
            {
                synchronized(FILTER_LOCK)
                {
                    status = STATUS_READY;
                    FILTER_LOCK.wait();

                    status = STATUS_PARSING;

                    filterTableRecord.clear();
                    L.d("Parsing");

                    if (noFilterExist()){
                        L.d("No filter exist");
                        listener.onParseCompleted(tableRecord);
                        status = STATUS_READY;
                        continue;
                    }

                    int nRowCount = tableRecord.getSize();
                    for(int nIndex = 0; nIndex < nRowCount; nIndex++)
                    {
                        if(nIndex % 10000 == 0)
                            Thread.sleep(1);
                        if(status == STATUS_CHANGE)
                        {
                            L.d("Change.. break");
                            break;
                        }
                        boolean toAdd = true;

                        TableRow tableRow = tableRecord.getTableRow(nIndex);
                        for(int fIndex = 0; fIndex < filterTextFieldList.size(); fIndex++){
                            //if (filterType == FILTER_AND && toAdd == false)
                            if (filterOption.equals(FILTER_AND) && toAdd == false)
                                break;
                            String filterString = filterTextFieldList.get(fIndex).getText().toString().toLowerCase();
                            if (filterString.length() == 0) continue;
                            if (tableRow.getTableDataByIndex(fIndex).getValue().toString().toLowerCase().contains(filterString)){
                                //if (filterType != FILTER_AND)
                                if (!filterOption.equals(FILTER_AND))
                                    filterTableRecord.addTableRow(tableRow);
                            }else {
                                toAdd = false;
                            }
                        }
                        //if (filterType == FILTER_AND && toAdd)
                        if (filterOption.equals(FILTER_AND) && toAdd)
                            filterTableRecord.addTableRow(tableRow);
                    }
                    if(status == STATUS_PARSING)
                    {
                        L.d("Parse Complete, filter size = " + filterTableRecord.getSize());
                        listener.onParseCompleted(filterTableRecord);
                        status = STATUS_READY;
                    }
                }
            }
        } catch(Exception e) {
            L.d(e);
            e.printStackTrace();
        }
        System.out.println("End thread");
    }
}
