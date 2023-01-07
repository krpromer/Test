import java.text.SimpleDateFormat;
import java.util.Date;

public class L {

    public static void d() {
        Exception e = new Exception();
        StackTraceElement callerElement = e.getStackTrace()[1];
        System.out.println(getCurrentTime() + "[" +
                callerElement.getFileName() + ":" +
                callerElement.getMethodName() + ":" +
                callerElement.getLineNumber() + "]");
    }

    public static void d(Object strMsg) {
        Exception e = new Exception();
        StackTraceElement callerElement = e.getStackTrace()[1];
        System.out.println(getCurrentTime() + "[" +
                callerElement.getFileName() + ":" +
                callerElement.getMethodName() + ":" +
                callerElement.getLineNumber() + "]" +
                strMsg);
    }

    public static String getCurrentTime() {
        long time = System.currentTimeMillis();
        SimpleDateFormat dayTime = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss.SSS");
        return dayTime.format(new Date(time));

    }
}
