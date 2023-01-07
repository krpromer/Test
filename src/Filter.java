import java.util.ArrayList;

public class Filter implements Runnable{

    private static final int STATUS_READY = 0;
    private static final int STATUS_PARSING = 1;
    private static final int STATUS_CHANGE = 2;

    public interface ParseListener {
        public void onParseCompleted(ArrayList<String> list);
    }
    Object FILTER_LOCK;
    ArrayList<String> list;
    ArrayList<String> filterList;
    private int status;
    private String filterString;

    ParseListener listener;
    Thread filterThread;
    public Filter(ArrayList<String> list, ParseListener listener){
        this.list = list;
        this.listener = listener;
        filterList = new ArrayList<>();
        FILTER_LOCK = new Object();
        status = STATUS_READY;
        filterString = "";
        filterThread = new Thread(this);
        filterThread.start();
    }

    public void setFilter(String filterString){
        this.filterString = filterString.toLowerCase();
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

                    filterList.clear();
                    L.d("Parsing");

                    if (filterString.length() == 0){
                        L.d("No filter String");
                        listener.onParseCompleted(list);
                        status = STATUS_READY;
                        continue;
                    }

                    int nRowCount = list.size();
                    for(int nIndex = 0; nIndex < nRowCount; nIndex++)
                    {
                        if(nIndex % 10000 == 0)
                            Thread.sleep(1);
                        if(status == STATUS_CHANGE)
                        {
                            L.d("Change.. break");
                            break;
                        }
                        String row = list.get(nIndex);
                        if (row.toLowerCase().contains(filterString))
                            filterList.add(row);
                    }
                    if(status == STATUS_PARSING)
                    {
                        L.d("Parse Complete");
                        listener.onParseCompleted(filterList);
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
