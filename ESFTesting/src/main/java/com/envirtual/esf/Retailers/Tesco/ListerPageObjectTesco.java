package com.envirtual.esf.Retailers.Tesco;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Salako on 10/04/2015.
 */
public class ListerPageObjectTesco{

    private WebDriver _driver;
    public static final By productGrid = By.cssSelector("ul.products.grid");
    public static final By allProducts = By.cssSelector("div.allProducts");
    public static final By numberOfProducts = By.cssSelector("h1");
    public static final By mainLogo = By.id("tLogoMain");

    public ListerPageObjectTesco(WebDriver driver){
        _driver = driver;
    }

    public boolean verifyProductGridIsDisplayed(){
        try{
            return _driver.findElement(productGrid).isDisplayed();
        }catch (Exception exp){
            // do nothing
        }
        return false;
    }

    public String getNumberOfListedProducts() {
        try {
            String text = _driver.findElement(numberOfProducts).getText();
            String[] listText = text.split(" ");
            return listText[0]; // return first value in array
        } catch (Exception exp) {
            // do nothing
        }
        return null;
    }

    public boolean verifyProductsAreListed(){
        try{
            List<WebElement> prods =getListedProducts();
            return prods.size() > 0;
        }catch (Exception exp){
            // do nothing
        }
        return false;
    }

    public List<WebElement> getListedProducts(){
        List<WebElement> elements = _driver.findElement(allProducts).findElements(By.cssSelector("li.product.clearfix"));
        return elements;
    }

    public Map<String,WebElement> rankProducts(int count){
        int rank = 1;
        HashMap<String,WebElement> productRank = new HashMap<String,WebElement>();
        List<WebElement> products = getListedProducts();
        for (WebElement prod : products ){
            if (rank == count) return productRank; // returns required number of rank products based on count value provided
            productRank.put(""+rank++,prod);
        }
        return productRank;
    }

    public Map<String, String> compareProductLists(HashMap<String, WebElement> productionList, HashMap<String, WebElement> esfList){
        Map<String, String> diffList = new HashMap<String, String>();
        for(String prod : esfList.keySet()){

            try {
                String productName_esf = esfList.get(prod).getText(); // this should get Webelement produuct name from ESF site
                String productName_production = productionList.get(prod).getText(); // this should get Webelement product name from LIVE data
                if(!productName_esf.toLowerCase().trim().equals(productName_production.toLowerCase().trim()))
                    diffList.put("ESF:- "+prod+" : "+productName_esf,"LIVE:- "+prod+" : "+productName_production);
            } catch (Exception exp){
                // do nothing
            }
        }
        return diffList;
    }

    public String getProductNameFromDictionaryElement(WebElement elem){
        try{
            WebElement tag = elem.findElement(By.tagName("img"));
            if(tag != null) {
                String value = tag.getAttribute("title").replace(" - view full details", "");
                return tag.getAttribute("title").replace(" - view full details", "");
            }
        }catch (Exception exp){}
        return null;
    }

    public boolean isMainLogoDisplayed()
    {
        try{

            return _driver.findElement(mainLogo).isDisplayed();

        }catch (Exception exp){};

        return false;
    }

    public boolean waitForElementToAppear(By element){
        int count = 0;

        do {

            try {

                count++;
                if(count > 10) return false;
                if(_driver.findElement(element).isDisplayed()) return true;
                return false;

            } catch (Exception exp) {

                count++;
                if(count > 10) return false;

            }
        }while (true);
    }
}
