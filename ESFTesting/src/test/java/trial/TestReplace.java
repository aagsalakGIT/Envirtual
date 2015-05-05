package trial;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.Test;
import java.io.BufferedReader;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Salako on 09/04/2015.
 */
public class TestReplace {

    @Test
    public void test(){
        //String text = "{ \"retailer\": { \"name\":  \"(@retailer@)\" } }";

        JSONParser parser = new JSONParser();

        JSONObject jsonObject = null;
        try {
            String data = ReadFile();
            jsonObject = (JSONObject) parser.parse(data);

        } catch (ParseException e) {
            e.printStackTrace();
        }
        jsonObject.put("retailer", "Asda");
        System.out.println(jsonObject.toJSONString());
    }

    public String ReadFile()
    {

        try {

            URL url = new URL("https://prod-esf.svc.envirtual.co.uk/search/extracted_pages/_search");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Authorization", "Basic ZGFuOmZyb2cwOHRvYXN0JA==");

            InputStream is = null;
            OutputStream os = null;

            try {
                is = TestReplace.class.getResourceAsStream("/repo/retailer.json");
                os = conn.getOutputStream();
                copy(is, os, 1024);

            } finally {
                if (is != null)
                    is.close();
                if (os != null)
                    os.close();
            }
            if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
                throw new RuntimeException("Failed : HTTP error code : "
                        + conn.getResponseCode());
            }

            BufferedReader br = new BufferedReader(new InputStreamReader(
                    (conn.getInputStream())));


            System.out.println("Output from Server .... \n");

            String output;

            while ((output = br.readLine()) != null) {
                System.out.println(output);
                return output;
            }

            conn.disconnect();

        } catch (MalformedURLException e) {

            e.printStackTrace();

        } catch (IOException e) {

            e.printStackTrace();

        }

        return null;
    }

    private static void copy(InputStream is, OutputStream os, int bufferSize) throws IOException {

        byte[] buffer = new byte[bufferSize];
        int len;
        while ((len = is.read(buffer, 0, bufferSize)) > 0) {
            os.write(buffer, 0, len);
        }
    }
}
