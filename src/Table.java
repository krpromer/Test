import javax.swing.*;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Random;

public class Table extends JTable {

    TableModel tm;
    Filter filter;
    public Table(){
        init();
    }

    String makeRandomString(){
        int leftLimit = 97; // letter 'a'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 10;
        Random random = new Random();
        StringBuilder buffer = new StringBuilder(targetStringLength);
        for (int i = 0; i < targetStringLength; i++) {
            int randomLimitedInt = leftLimit + (int)
                    (random.nextFloat() * (rightLimit - leftLimit + 1));
            buffer.append((char) randomLimitedInt);
        }
        return buffer.toString();
    }
    void init(){
        ArrayList<String> list = new ArrayList<>();
        int count = 10000000;
        for(int i = 0; i < count; i++){
            list.add(makeRandomString());
        }

        tm = new TableModel(list);
        setModel(tm);
        filter = new Filter(list, listener);
    }

    public void setFilterString(String str){
        invalidate();
        filter.setFilter(str);
    }
    Filter.ParseListener listener = new Filter.ParseListener() {
        @Override
        public void onParseCompleted(ArrayList<String> list) {
            tm.setList(list);
            repaint();
            changeSelection(0, 0, false, false);
        }
    };
}
