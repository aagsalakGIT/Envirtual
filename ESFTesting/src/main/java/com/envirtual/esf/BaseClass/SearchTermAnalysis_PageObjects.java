package com.envirtual.esf.BaseClass;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by Salako on 27/04/2015.
 */
public class SearchTermAnalysis_PageObjects {

    private final WebDriver Driver;

    public SearchTermAnalysis_PageObjects(WebDriver driver){

        Driver = driver;
    }

    public HashMap<String,String> getTableColumnHeaders(){

        int count = 0;
        HashMap<String,String> data = new HashMap<String,String>();
        WebElement table = Driver.findElement(By.id("Table"));
        WebElement Headers = table.findElement(By.tagName("thead"));
        List<WebElement> columnHeaders = Headers.findElements(By.cssSelector("th.ng-binding.ng-scope"));
        if(columnHeaders.size() < 7) return null;
        for (WebElement header : columnHeaders){
            count++; // increment count;
            String text = header.getText().trim();
            if(StringUtils.isNotEmpty(text)) data.put(text.toLowerCase(),""+count);
        }
        return data;
    }



    public HashMap<String,String> getSubBrandsAndValue(String retailer){

        int count = 0;
        int indexing = 0;
        int retailerColumnValue = Integer.parseInt(getTableColumnHeaders().get(retailer.toLowerCase()));

        HashMap<String,String> data = new HashMap<String,String>();

        WebElement table = Driver.findElement(By.id("Table"));

        WebElement Headers = table.findElement(By.tagName("tbody"));
        List<WebElement> columnSubBrands = Headers.findElements(By.tagName("tr"));

        if(columnSubBrands.size() < 7) return null;

        for (WebElement value : columnSubBrands){

            count++; // increment count;
            indexing = 0;

            List<WebElement> tds = value.findElements(By.tagName("td"));

            String subBrand = null;

            for (WebElement column : tds){

                String perceValue = null;

                //Assigns value to the sub-brand
                if(indexing == 0 && StringUtils.isEmpty(subBrand))
                    subBrand = column.getText();

                // keeps count of row numbers to decide on when to add element text to dictionary
                if(retailerColumnValue == indexing){
                    perceValue = column.getText().trim();
                    if(StringUtils.isNotEmpty(perceValue)){
                        if(subBrand.toLowerCase().equals("diet caffeine free")){
                            subBrand = "diet caffeine";
                        }
                        data.put(subBrand.toLowerCase(),perceValue);
                    }
                    indexing = 0;
                    break;
                }
                indexing ++;
            }
        }
        return data;
    }

    public HashMap<String,String> getProductsOnTable(WebElement table){

        HashMap<String,String> data = new HashMap<String,String>();

        try {

            List<WebElement> body = table.findElements(By.tagName("tbody")); // gets product table
            if(body.size() < 1) return null;
            List<WebElement> elements = body.get(0).findElements(By.tagName("tr"));

            for( WebElement element : elements){ // gets all element is table and extract rank and product name
                List<WebElement> values = element.findElements(By.tagName("td"));
                if(values.size() < 2) return null;
                String rank = values.get(0).findElement(By.tagName("span")).getText(); // gets rank text value
                String productName = values.get(1).findElement(By.tagName("a")).getText(); // gets product name text
                if(Integer.parseInt(rank) / Integer.parseInt(rank) == 1 && StringUtils.isNotEmpty(productName))
                    data.put(productName,rank); // adds data to dictionary
            }

        }catch (Exception exp){
            // do nothing
        }
        return data;
    }

}
