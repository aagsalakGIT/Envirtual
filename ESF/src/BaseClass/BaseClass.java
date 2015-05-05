package BaseClass;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class BaseClass {
	
	public static WebDriver driver;
	public String envURL;
	
	
	public BaseClass(String env, String browser) {
		driver = launchBrowser(browser);
		driver.manage().window().maximize();
		envURL = GetEnvUrl(env);
		driver.navigate().to(envURL);	
	}

	private static WebDriver launchBrowser(String browser)	{
		
		switch (browser.toLowerCase()) {
		
		case "firefox": return new FirefoxDriver();
		
		case "chrome": return new ChromeDriver();
		
		default: return new FirefoxDriver();
		}
	}
	
	private static String GetEnvUrl(String env){
		switch (env.toLowerCase()) {
		
		case "qa": return "http:\\www.arsenal.com";
		
		case "stagging": return"http:\\www.bbc.co.uk";
		
		case "dev": return "";
		
		default: return "";
		}
	}
	
	public enum Environments{
		qa,stagging,dev
	}
	
	public enum Browsers{
		chrome,firefox
	}
	
}
