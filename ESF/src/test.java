
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.WebDriver;

import BaseClass.BaseClass;

public class test {
    
	public static BaseClass baseclass;
   	WebDriver driver;
		
   	  @BeforeClass
	  public static void createAndStartService() {
	     baseclass = new BaseClass("qa","firefox"); 	       
	  }
	
	  @AfterClass
	  public static void createAndStopService() {
	    
	  }
	
	  @Before
	  public void createDriver() {
	   
	  }
	
	  @After
	  public void quitDriver() {
	    driver.quit();
	  }
	
	  @Test
	  public void testGoogleSearch() throws IOException {
	   
		    URL oracle = new URL("http://www.oracle.com/");
	        BufferedReader in = new BufferedReader(
	        new InputStreamReader(oracle.openStream()));
	        BufferedWriter writer = new BufferedWriter(new FileWriter("outputfile.txt"));

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
    
}