import java.io.*;

public class Main {
    public static void main(String[] args)
    {
        //new MainFrame();

        File file = new File("C:/Users/ammyi/Downloads/ideaIC-2022.3.1.exe");
        FileInputStream fis = null;
        DataInputStream dis = null;
        BufferedReader br = null;
        try {
            fis = new FileInputStream(file);

            dis = new DataInputStream(fis);
            br = new BufferedReader(new InputStreamReader(dis));

            long total = dis.available()& 0x00000000ffffffffL;
            long cur = 0;
            String strLine;
            int cnt = 0;
            while((strLine = br.readLine()) != null){
                if (cnt % 10000 == 0) {
                    long avail = dis.available() & 0x00000000ffffffffL;
                    L.d("total = " + total + " avail = " + avail +
                            " diff = " + (total - dis.available()) + " be = " + avail * 100  + " test = " + (avail * 100 / total));
                    L.d(100 - (avail * 100 / total) + "%");
                }
                cnt++;
            }
            L.d(dis.available());


            br.close();
            dis.close();
            fis.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
}