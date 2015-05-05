package com.envirtual.esf.ReportPages;

import org.openqa.selenium.WebDriver;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Salako on 27/04/2015.
 */
public class SabmillerPageObjects implements ReportPageObjects {
    private final WebDriver Driver;

    public  SabmillerPageObjects(WebDriver driver){
        Driver = driver;
    }

    public List<String> journeyList(){
        List<String> data = new ArrayList<String>();
        data.add("Peroni");
        data.add("Beer");
        data.add("Lager");
        return data;
    }
    public List<String> searchTermAnalysisTableNames(){
        List<String> data = new ArrayList<String>();
        return data;
    }


    public List<String> productBasicsTableNames(){
        List<String> data = new ArrayList<String>();
        data.add("Heineken");
        data.add("Fosters");
        data.add("Peroni");
        data.add("Miller genuine draft");
        data.add("Tyskie");
        data.add("Lech");
        data.add("Budweiser");
        data.add("Stella artois");
        data.add("Carlsberg");
        data.add("Carling");
        data.add("Other");
        return data;
    }

    public int noOfLeftNavLinks(){
        return 4;
    }
}
