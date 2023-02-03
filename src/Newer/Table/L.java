package Newer.Table;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

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

    public static String makeRandomString(){
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
}
