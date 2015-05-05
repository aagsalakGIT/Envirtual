package com.envirtual.esf.ReportPages;

import org.openqa.selenium.WebDriver;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Salako on 27/04/2015.
 */
public class LaundryPageObjects implements ReportPageObjects {

    private final WebDriver Driver;

    public  LaundryPageObjects(WebDriver driver){
        Driver = driver;
    }

    public List<String> journeyList(){
        List<String> data = new ArrayList<String>();
        data.add("laundry");
        data.add("detergent");
        data.add("laundry powder");
        data.add("Persil");
        data.add("Ariel");
        data.add("Bold");
        data.add("Non-Bio");
        return data;
    }

    public List<String> searchTermAnalysisTableNames(){
        List<String> data = new ArrayList<String>();
        data.add("Persil");
        data.add("Ariel");
        data.add("Bold");
        data.add("Fairy");
        data.add("Daz");
        data.add("Other");
        return data;
    }

    public List<String> productBasicsTableNames(){
        List<String> data = new ArrayList<String>();
        data.add("Persil");
        data.add("Ariel");
        data.add("Bold");
        data.add("Fairy");
        data.add("Daz");
        data.add("Other");
        return data;
    }

    public int noOfLeftNavLinks(){
        return 5;
    }
}
