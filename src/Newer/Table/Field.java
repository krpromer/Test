package Newer.Table;

import java.util.HashMap;
import java.util.Map;

public class Field {

    private static int IDX = 0;
    private static int AUTO(){ return IDX++; }
    public static final int FRAME = AUTO();
    public static final int TEST = AUTO();


    private static Map<Integer, Field> config = new HashMap<>(){
        {
            put(FRAME, new Field().headerName("Frame").headerWidth(80));
            put(TEST, new Field().headerName("Test").headerWidth(150));
        }
    };

    public static String getHeaderName(int field){
        return config.get(field).headerName;
    }

    public static int getHeaderWidth(int field) {
        return config.get(field).headerWidth;
    }

    public String headerName;
    public Field headerName(String headerName){
        this.headerName = headerName;
        return this;
    }

    public int headerWidth;
    public Field headerWidth(int headerWidth){
        this.headerWidth = headerWidth;
        return this;
    }

}
