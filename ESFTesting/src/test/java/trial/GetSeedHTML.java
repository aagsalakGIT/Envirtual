package trial;

import org.junit.Test;

import java.io.*;
import java.net.URL;

/**
 * Created by Salako on 09/04/2015.
 */
public class GetSeedHTML {

    @Test
    public void testGoogleSearch() throws IOException {

        URL oracle = new URL("http://www.bbc.co.uk");

        BufferedReader in = new BufferedReader(new InputStreamReader(oracle.openStream()));
        BufferedWriter writer = new BufferedWriter(new FileWriter("page.html"));

        String inputLine;
        while ((inputLine = in.readLine()) != null){
            try{
                writer.write(inputLine);
            }
            catch(IOException e){
                e.printStackTrace();
                return;
            }
        }
        in.close();
        writer.close();
    }

    public void MoveFileToResourceFolder()
    {
        InputStream in = null;
        OutputStream out = null;

        try {

            File oldFileLocation = new File("page.html");
            File newFileLocation = new File("/page.html");

            in = new FileInputStream(oldFileLocation);
            out = new FileOutputStream(newFileLocation);

            byte[] moveBuff = new byte[1024];

            int butesRead;

            while ((butesRead = in.read(moveBuff)) > 0){
                out.write(moveBuff,0,butesRead);
            }

            in.close();
            out.close();

            oldFileLocation.delete();


        }catch(IOException e){
            e.printStackTrace();
        }

    }

    @Test
    public void ReadFile(){

        try {
            FileReader data = new FileReader("page.html");
            System.out.println(data);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

}
