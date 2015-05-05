package com.envirtual.esf.ReportPages;

import org.openqa.selenium.WebDriver;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Salako on 27/04/2015.
 */
public class ToothpastePageObjects implements ReportPageObjects{
    private final WebDriver Driver;

    public  ToothpastePageObjects(WebDriver driver){
        Driver = driver;
    }

    public List<String> journeyList(){
        List<String> data = new ArrayList<String>();
        data.add("Toothpaste");
        data.add("Sensitive toothpaste");
        data.add("Whitening toothpaste");
        data.add("Children toothpaste");
        return data;
    }

    public List<String> searchTermAnalysisTableNames(){
        List<String> data = new ArrayList<String>();
        return data;
    }

    public List<String> productBasicsTableNames(){
        List<String> data = new ArrayList<String>();
        data.add("Aquafresh");
        data.add("Sensodyne");
        data.add("Oral-b");
        data.add("Colgate");
        data.add("Other");
        return data;
    }

    public int noOfLeftNavLinks(){
        return 4;
    }

}
