package com.envirtual.esf.ReportPages;

import org.openqa.selenium.WebDriver;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Salako on 27/04/2015.
 */
public class BiscuitsPageObjects implements ReportPageObjects {

    private final WebDriver Driver;

    public  BiscuitsPageObjects(WebDriver driver){
        Driver = driver;
    }

    public List<String> journeyList(){
        List<String> data = new ArrayList<String>();
        data.add("Biscuits");
        data.add("Chocolate biscuits");
        data.add("Biscuits multipack");
        data.add("crackers");
        data.add("cream crackers");
        data.add("jaffa cakes");
        data.add("digestives");
        data.add("rich tea");
        data.add("custard creams");
        data.add("digestive biscuits");
        data.add("chocolate digestives");
        return data;
    }

    public List<String> searchTermAnalysisTableNames(){
        List<String> data = new ArrayList<String>();
        data.add("McVities");
        data.add("Jacobs");
        data.add("Cadburys");
        data.add("Nestle");
        data.add("Foxs");
        data.add("Other");
        return data;
    }

    public List<String> productBasicsTableNames(){
        List<String> data = new ArrayList<String>();
        data.add("Mcvities");
        data.add("Jacobs");
        data.add("Cadburys");
        data.add("Nestle");
        data.add("Fox's");
        data.add("Other");
        return data;
    }

    public int noOfLeftNavLinks(){
        return 5;
    }

}
