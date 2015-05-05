package com.envirtual.esf.BaseClass;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by Salako on 27/04/2015.
 */
public class SearchTermComparison_PageObjects {

    private final WebDriver Driver;





    public SearchTermComparison_PageObjects(WebDriver driver){
        Driver = driver;
    }



  /*  public List<String> getTableNames(){

        List<String> data = new ArrayList<String>();
        try {
            List<WebElement> tablenames = Driver.findElements(By.cssSelector("strong"));

            for (WebElement elem : tablenames) {
                String name = elem.getText();
                if (StringUtils.isNotEmpty(name)) data.add(name);
            }
        }
        catch (Exception exp){
            System.out.println("Exception error occurred "+ Driver.getCurrentUrl() +" "+ exp.toString());
        }
        return data;

        Driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
        List<String> data = new ArrayList<String>();
        List<WebElement> tables = Driver.findElements(brandTables);
        if(tables.size() < 1) return null;
        for(WebElement tableName : tables){
            List<WebElement> name = tableName.findElements(By.tagName("strong"));
            if(name.size() < 1) continue;
            String nameText = name.get(0).getText();
            if(StringUtils.isNotEmpty(nameText)) data.add(nameText.toLowerCase());
        }
        Driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        return data;
    }*/





}
