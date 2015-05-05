package com.envirtual.esf.ReportPages;

import org.openqa.selenium.WebDriver;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Salako on 27/04/2015.
 */
public class CokeReportPageObjects implements ReportPageObjects {

    private final WebDriver Driver;

    public  CokeReportPageObjects(WebDriver driver){
        Driver = driver;
    }

    public List<String> journeyList(){
        List<String> data = new ArrayList<String>();
        data.add("Squash");
        data.add("Coke");
        data.add("Water");
        data.add("Orange juice");
        data.add("Lemonade");
        data.add("Diet Coke");
        data.add("apple juice");
        data.add("sparkling water");
        data.add("Coca-Cola");
        data.add("Pepsi");
        data.add("Pepsi Max");
        data.add("Diet Pepsi");
        return data;
    }

    public List<String> searchTermAnalysisTableNames(){
        List<String> data = new ArrayList<String>();
        data.add("Coca-Cola");
        data.add("Pepsi");
        data.add("Other");
        return data;
    }

    public List<String> productBasicsTableNames(){
        List<String> data = new ArrayList<String>();
        data.add("regular");
        data.add("diet");
        data.add("zero");
        data.add("life");
        data.add("cherry");
        data.add("other");
        return data;
    }

    public int noOfLeftNavLinks(){
        return 5;
    }

}
