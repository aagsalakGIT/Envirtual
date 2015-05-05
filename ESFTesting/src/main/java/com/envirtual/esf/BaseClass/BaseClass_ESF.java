package com.envirtual.esf.BaseClass;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;

import java.util.*;

/**
 * Created by Salako on 10/04/2015.
 */
public class BaseClass_ESF {

    public static String esfPageCoke = "-esf.svc.envirtual.co.uk/report/reports/cocacola123987/#/";
    public static String esfPageBirdseye = "-esf.svc.envirtual.co.uk/report/reports/birdseye854934/#/";
    public static String esfPageBiscuits = "-esf.svc.envirtual.co.uk/report/reports/biscuits321689/#/";
    public static String esfPageNestle = "-esf.svc.envirtual.co.uk/report/reports/nestle789123/#/";
    public static String esfPageSca = "-esf.svc.envirtual.co.uk/report/reports/sca456321/#/";
    public static String esfPageSabmiller = "-esf.svc.envirtual.co.uk/report/reports/sabmiller753053/#/";
    public static String esfPageToothpaste = "-esf.svc.envirtual.co.uk/report/reports/toothpaste032664/#/";
    public static String esfPageLaundry = "-esf.svc.envirtual.co.uk/report/reports/laundry937502/#/";
    public static String esfPageSausages = "-esf.svc.envirtual.co.uk/report/reports/sausages573064/#/";
    private String username = "dan";
    private String passsword = "frog08toast$";
    public String evironemnt;
    private final WebDriver Driver;

    public BaseClass_ESF(String browser, Enum env, WebDriver driver){ // browser can be null. This should use default browser Firefox.
        Driver = driver != null ? driver : launchBrowser(browser);
        evironemnt = evironment(env.toString());
    }

    public WebDriver getDriver() {
        return Driver;
    }

    public WebDriver launchBrowser(String browser){
        switch (browser.toLowerCase()){
            case "firefox": FirefoxProfile profile = new FirefoxProfile();
                profile.setPreference("network.http.phishy-userpass-length", 255);
                return new FirefoxDriver(profile);

            case "chrome": System.setProperty("webdriver.chrome.driver", getClass().getResource("/chromedriver.exe").getPath());
                             return new ChromeDriver();

            case "ie":

            default: profile = new FirefoxProfile();
                    profile.setPreference("network.http.phishy-userpass-length", 255);
                    return new FirefoxDriver(profile);
        }
    }


    private String evironment(String env){
        switch (env.toLowerCase()){
            case "test": return "test";
            case "prod": return "prod";
            case "si": return "si";
            case "ci": return "ci";
        }
        return null;
    }

    public WebDriver navigateToURL(String page){
        Driver.manage().window().maximize();
        switch (page.toLowerCase()){ // page uses enum pages value which decices which page to navigate to using main variable
            case "esfpagecoke": Driver.navigate().to("https://"+username+":"+passsword+"@"+evironemnt+esfPageCoke); break;
            case "esfpagebirdseye": Driver.navigate().to("https://"+username+":"+passsword+"@"+evironemnt+esfPageBirdseye); break;
            case "esfpagebiscuit": Driver.navigate().to("https://"+username+":"+passsword+"@"+evironemnt+esfPageBiscuits); break;
            case "esfpagenestle": Driver.navigate().to("https://"+username+":"+passsword+"@"+evironemnt+esfPageNestle); break;
            case "esfpagesca": Driver.navigate().to("https://"+username+":"+passsword+"@"+evironemnt+esfPageSca); break;
            case "esfpagesabmiller": Driver.navigate().to("https://"+username+":"+passsword+"@"+evironemnt+esfPageSabmiller); break;
            case "esfpagetoothpaste": Driver.navigate().to("https://"+username+":"+passsword+"@"+evironemnt+esfPageToothpaste); break;
            case "esfpagelaundry": Driver.navigate().to("https://"+username+":"+passsword+"@"+evironemnt+esfPageLaundry); break;
            case "esfpagesausages": Driver.navigate().to("https://"+username+":"+passsword+"@"+evironemnt+esfPageSausages); break;
        }

        return Driver;
    }

    public Set<String> getWindowOpenWindows(){

        String a = Driver.getCurrentUrl();

        Set<String> handles = Driver.getWindowHandles();

        String firstWinHandle = Driver.getWindowHandle();

        handles.remove(firstWinHandle);

        String winHandle = handles.iterator().next();

        if (winHandle!=firstWinHandle)

       // return Driver.getWindowHandles();
       return handles;

        return handles;
    }

    public void switchToWindow(String windowName){
        Driver.switchTo().window(windowName);
    }

    public enum pages{
        esfpagecoke,esfpagebirdseye,esfpagebiscuit,esfpagenestle,esfpagesca,esfpagesabmiller,esfpagetoothpaste,esfpagelaundry,esfpagesausages
    }

    public enum environment{
        test,prod,si,ci
    }


}
