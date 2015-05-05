package com.envirtual.esf.ReportPages;

import org.openqa.selenium.WebDriver;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Salako on 27/04/2015.
 */
public class NestlePageObjects implements ReportPageObjects {
    private final WebDriver Driver;

    public  NestlePageObjects(WebDriver driver){
        Driver = driver;
    }

    public List<String> journeyList(){
        List<String> data = new ArrayList<String>();
        data.add("Coffee");
        data.add("Hot Drinks");
        data.add("nescafe");
        data.add("coffee pods");
        data.add("cappuccino");
        return data;
    }

    public List<String> searchTermAnalysisTableNames(){
        List<String> data = new ArrayList<String>();
        data.add("Nescafe");
        data.add("Other");
        return data;
    }

    public List<String> productBasicsTableNames(){
        List<String> data = new ArrayList<String>();
        data.add("original");
        data.add("gold blend");
        data.add("azera");
        data.add("mocha");
        data.add("dolce gusto");
        data.add("douwe egberts");
        data.add("other");
        return data;
    }

    public int noOfLeftNavLinks(){
        return 5;
    }

}
