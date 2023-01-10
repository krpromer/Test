import java.util.HashMap;
import java.util.Map;

public class F {

    public static int IDX = 0;
    public static final int FRAME = IDX++;
    public static final int MESSAGE = IDX++;

    public static final int INDEX = IDX++;


    public static Map<Integer, Class> mapClass = new HashMap<>(){
        {
            put(FRAME, Integer.class);

        }
    };
    public static Class getClass(int field){
        Class c = mapClass.get(field);
        return c == null ? String.class : c;
    }
}
