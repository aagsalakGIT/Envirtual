package com.envirtual.esf.ReportPages;

import org.openqa.selenium.WebDriver;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Salako on 27/04/2015.
 */
public class ScaPageObjects implements ReportPageObjects{

    private final WebDriver Driver;

    public  ScaPageObjects(WebDriver driver){
        Driver = driver;
    }

    public List<String> journeyList(){
        List<String> data = new ArrayList<String>();
        data.add("Toilet roll");
        data.add("Loo roll");
        data.add("Velvet");
        return data;
    }

    public List<String> searchTermAnalysisTableNames(){
        List<String> data = new ArrayList<String>();
        data.add("Cushelle");
        data.add("Velvet");
        data.add("Andrex");
        data.add("Other");
        return data;
    }

    public List<String> productBasicsTableNames(){
        List<String> data = new ArrayList<String>();
        data.add("Cushelle");
        data.add("Velvet");
        data.add("Andrex");
        data.add("Other");
        return data;
    }

    public int noOfLeftNavLinks(){
        return 5;
    }
}
