package com.envirtual.esf.ReportPages;

import org.openqa.selenium.WebDriver;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Salako on 27/04/2015.
 */
public class SausagePageObjects implements ReportPageObjects{
    private final WebDriver Driver;

    public  SausagePageObjects(WebDriver driver){
        Driver = driver;
    }

    public List<String> journeyList(){
        List<String> data = new ArrayList<String>();
        data.add("Sausages");
        data.add("Walls");
        data.add("Mattesons");
        data.add("Richmond");
        data.add("mini sausages");
        data.add("thick sausages");
        data.add("thin sausages");
        return data;
    }

    public List<String> searchTermAnalysisTableNames(){
        List<String> data = new ArrayList<String>();
        data.add("Walls");
        data.add("Mattesons");
        data.add("Richmond");
        data.add("Other");
        return data;
    }

    public List<String> productBasicsTableNames(){
        List<String> data = new ArrayList<String>();
        data.add("Walls");
        data.add("Mattesons");
        data.add("Richmond");
        data.add("Other");
        return data;
    }

    public int noOfLeftNavLinks(){
        return 5;
    }

}
