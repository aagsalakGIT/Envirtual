package com.envirtual.esf.ReportPages;

import org.openqa.selenium.WebDriver;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Salako on 27/04/2015.
 */
public class BirdseyePageObjects implements ReportPageObjects {

    private final WebDriver Driver;

    public BirdseyePageObjects(WebDriver driver){
        Driver = driver;
    }

    public List<String> journeyList(){
        List<String> data = new ArrayList<String>();
        data.add("Fish");
        data.add("Fish fingers");
        data.add("Battered fish");
        data.add("Breaded fish");
        data.add("Fish cakes");
        return data;
    }

    public List<String> searchTermAnalysisTableNames(){
        List<String> data = new ArrayList<String>();
        data.add("Birds Eye");
        data.add("Youngs");
        data.add("Other");
        return data;
    }

    public List<String> productBasicsTableNames(){
        List<String> data = new ArrayList<String>();
        data.add("birds eye");
        data.add("young's");
        data.add("other");
        return data;
    }

    public int noOfLeftNavLinks(){
        return 5;
    }

}
