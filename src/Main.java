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

            String strLine;
            int cnt = 0;
            while((strLine = br.readLine()) != null){
                if (cnt % 10000 == 0)
                    L.d(dis.available());
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