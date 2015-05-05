package ElasticSearch;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class Test {

	// http://localhost:8080/RESTfulExample/json/product/post
	public static void main(String[] args) {

		try {

			URL url = new URL(
					"https://prod-esf.svc.envirtual.co.uk/search/extracted_pages/_search");
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type", "application/json");
			conn.setRequestProperty("Authorization", "Basic ZGFuOmZyb2cwOHRvYXN0JA==");

			InputStream is = null;
			OutputStream os = null;
			try {
				is = Test.class.getResourceAsStream("query.json");
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

			String output;
			System.out.println("Output from Server .... \n");
			while ((output = br.readLine()) != null) {
				System.out.println(output);
			}

			conn.disconnect();

		} catch (MalformedURLException e) {

			e.printStackTrace();

		} catch (IOException e) {

			e.printStackTrace();

		}

	}

	private static void copy(InputStream is, OutputStream os, int bufferSize)
			throws IOException {
		byte[] buffer = new byte[bufferSize];
		int len;
		while ((len = is.read(buffer, 0, bufferSize)) > 0) {
			os.write(buffer, 0, len);
		}
	}

}