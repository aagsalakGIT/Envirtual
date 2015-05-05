package com.envirtual.esf.BaseClass;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;

/**
 * Created by Salako on 10/04/2015.
 */
public class BaseClass_Retailers {

    public static String mainPageURLTesco = "http://www.tesco.com/groceries";
    public static String listPageURLTesco = "http://www.tesco.com/groceries/product/search/default.aspx?searchBox=";
    public static String mainPageURLAsda = "http://groceries.asda.com";
    public static String listPageURLAsda =
            "http://groceries.asda.com/asda-webstore/landing/home.shtml?cmpid=ahc-_-ghs-sna1-_-asdacom-dsk-_-hp#/search/";
    public static String mainPageURLMorrisons = "https://groceries.morrisons.com/webshop/startWebshop.do?dnr=y";
    public static String listPageURLMorrisons =
            "https://groceries.morrisons.com/webshop/getSearchProducts.do?clearTabs=yes&isFreshSearch=true&entry=";
    public static String mainPageURLOcado = "https://www.ocado.com/webshop/startWebshop.do";
    public static String listPageURLOcado =
            "https://www.ocado.com/webshop/getSearchProducts.do?clearTabs=yes&isFreshSearch=true&entry=";
    public static String mainPageURLSainsbury = "http://www.sainsburys.co.uk/shop/gb/groceries";
    public static String listPageURLSainsbury =
            "http://www.sainsburys.co.uk/webapp/wcs/stores/servlet/SearchDisplayView?catalogId=10122&langId=44&storeId=10151&orderBy=RELEVANCE&searchTerm=";
    public static String mainPageURLWaitrose = "http://www.waitrose.com/shop/Browse/Groceries";
    public static String listPageURLWaitrose = "http://www.waitrose.com/shop/HeaderSearchCmd?searchTerm=";
    public static String mainPageURLAmazon = "http://www.amazon.co.uk";
    public static String listPageURLAmazon =
            "http://www.amazon.co.uk/s/ref=nb_sb_noss/278-7072634-2603231?url=search-alias%3Daps&field-keywords=";

    private final WebDriver driver;
    public static String Browser;

    public BaseClass_Retailers(Enum browser){ // browser can be null. This should use default browser Firefox.
        Browser = browser.toString();
        driver = launchBrowser(Browser);
    }

    public WebDriver getDriver() {
        return driver;
    }

    private WebDriver launchBrowser(String browser){
        if(browser == null) browser = "";
        switch (browser.toLowerCase()){
            case "firefox": return new FirefoxDriver();
            case "chrome": return new ChromeDriver();
            case "ie": return new InternetExplorerDriver();
            default: return new FirefoxDriver();
        }
    }

    public void navigateToURL(Enum page, String searchTerm){
        if(searchTerm == null) searchTerm = "";
        driver.manage().window().maximize();
        switch (page.toString().toLowerCase()){ // page uses enum pages value which decices which page to navigate to using main variable
            case "mainpageamazon": driver.navigate().to(mainPageURLAmazon); break;
            case "listpageamazon": driver.navigate().to(listPageURLAmazon+searchTerm); break;
            case "mainpageasda": driver.navigate().to(mainPageURLAsda); break;
            case "listpageasda": driver.navigate().to(listPageURLAsda+searchTerm); break;
            case "mainpagemorrisons": driver.navigate().to(mainPageURLMorrisons); break;
            case "listpagemorrisons": driver.navigate().to(listPageURLMorrisons+searchTerm); break;
            case "mainpageocado": driver.navigate().to(mainPageURLOcado); break;
            case "listpageocado": driver.navigate().to(listPageURLOcado+searchTerm); break;
            case "mainpagesainsbury": driver.navigate().to(mainPageURLSainsbury); break;
            case "listpagesainsbury": driver.navigate().to(listPageURLSainsbury+searchTerm); break;
            case "mainpagetesco": driver.navigate().to(mainPageURLTesco); break;
            case "listpagetesco": driver.navigate().to(listPageURLTesco+searchTerm); break;
            case "mainPageWaitrose": driver.navigate().to(mainPageURLWaitrose); break;
            case "listPageWaitrose": driver.navigate().to(listPageURLWaitrose+searchTerm); break;
        }
    }

    public enum selectBrowser{
        firefox,ie,chrome
    }

    public enum pages{
        mainPageAmazon, listPageAmazon,mainPageAsda, listPageAsda,mainPageMorrisons, listPageMorrisons,
        mainPageOcado, listPageOcado,mainPageSainsbury, listPageSainsbury,mainPageTesco, listPageTesco,
        mainPageWaitrose, listPageWaitrose
    }
}
