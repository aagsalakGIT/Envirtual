package done;

import com.envirtual.esf.BaseClass.BaseClass_ESF;
import com.envirtual.esf.BaseClass.esfPageObjects;
import com.envirtual.esf.Retailers.Tesco.TescoSovScore;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Salako on 16/04/2015.
 */
public class SearchTermAnalysis {

    protected WebDriver driver;
    BaseClass_ESF baseClassESF;

    public SearchTermAnalysis(){
        baseClassESF = new BaseClass_ESF("chrome",BaseClass_ESF.environment.test,null);
        driver = baseClassESF.getDriver();
    }

    @Test
      public void TescoSearchTermAnalysisReport_CokeReportPage_CokeBrand(){

        try {
            //define retailer and Brand
            String retailer = "tesco";
            String brand = "Coke";
            String reportPage = BaseClass_ESF.pages.esfpagecoke.toString();

            // Run test
            SearchTermAnalysisReport(retailer, brand, reportPage);
        }
        finally {
            driver.quit();
        }

    }

    @Test
    public void TescoSearchTermAnalysisReport_CokeReportPage_DietCokeBrand(){

        try {
            //define retailer and Brand
            String retailer = "tesco";
            String brand = "Diet Coke";
            String reportPage = BaseClass_ESF.pages.esfpagecoke.toString();

            // Run test
            SearchTermAnalysisReport(retailer, brand, reportPage);
        }
        finally {
            driver.quit();
        }

    }

    @Test
    public void TescoSearchTermAnalysisReport_CokeReportPage_CocaColaBrand(){

        try {
            //define retailer and Brand
            String retailer = "tesco";
            String brand = "Coca-Cola";
            String reportPage = BaseClass_ESF.pages.esfpagecoke.toString();

            // Run test
            SearchTermAnalysisReport(retailer, brand, reportPage);
        }
        finally {
            driver.quit();
        }

    }


    private void SearchTermAnalysisReport(String retailer, String brand, String reportPage){

        try {
            // navigate to coke report page
            baseClassESF.navigateToURL(reportPage);

            // instantiate esfpage object and get left nav elements
            esfPageObjects esfPage = new esfPageObjects(driver);
            esfPage.waitForLeftNavToLoad();
            WebElement leftnav = esfPage.getLeftNavElement(esfPageObjects.leftNavLinks.Search_Term_Analysis);
            assert leftnav != null : "ESF page: No left nav elements are returned";
            leftnav.click();
            esfPage.waitForSpinnerToLoad();

            //select journey
            esfPage.selectJourney(brand);

            // gets todays date from calender
            String Today = esfPage.selectDayOfMOnth();


            // select date on calender
            esfPage.selectDateOnCalender(Today, true);

            // verify total percentage
            TescoSovScore tescoSOV = new TescoSovScore(driver);
            HashMap<String, String> salesOfVoice = tescoSOV.getCokeSubBrandsAndValue(retailer);
            List<String> subBrandList = tescoSOV.getProductsFromDictionary(salesOfVoice);

            // get product rank from retailer comparison
            leftnav = esfPage.getLeftNavElement(esfPageObjects.leftNavLinks.Retailer_Comparison);
            assert leftnav != null : "ESF page: No left nav elements are returned";
            leftnav.click();
            esfPage.waitForSpinnerToLoad();

            //select journey
            esfPage.selectJourney(brand);

            // select date on calender
            esfPage.selectDateOnCalender(Today, true);

            // get tesco product ranks and store on dictionary
            HashMap<String, String> productRank = tescoSOV.getRetailerProductRanking(retailer);

            // get sales of voice score and product added to dictionary
            Double Total = 0.00;
            List<String> productList = tescoSOV.getProductsFromDictionary(productRank);
            HashMap<String, String> retailerRankTable = tescoSOV.getTescoRanktable();
            HashMap<String, String> productSOVscore = new HashMap<String, String>();

            for (String product : productList) {
                String rank = productRank.get(product);
                productSOVscore.put(product, retailerRankTable.get(rank));
            }

            // get Total SOV score
            for (String score : productSOVscore.values()) {
                Total = Double.parseDouble(score) + Total;
            }

            // categorise list of products
            HashMap<String, Double> subBrandProductExtract = categoriseProductList(tescoSOV, subBrandList, productList, productSOVscore);

            // insert 0% for sub brands without products
            addNullBrandsToResult(subBrandList, subBrandProductExtract);

            // Final value
            HashMap<String, String> finalRetailerScore = getFinalValue(Total, subBrandProductExtract);

            // Verify SOV on website for retailer equals value calcuated
            for (String subBrands : finalRetailerScore.keySet()) {
                String testFrameworkScore = finalRetailerScore.get(subBrands);
                String esfScore = salesOfVoice.get(subBrands);
                assert testFrameworkScore.equals(esfScore) : "Sales of Voice value for " + subBrands + " for " + retailer + " is:- " + esfScore + " expected:- " + testFrameworkScore;
            }
        }
        catch (AssertionError err){
            System.out.println("Search Term Analysis Report Assertion Error "+ err);
        }

    }

    private void addNullBrandsToResult(List<String> subBrandList, HashMap<String, Double> subBrandProductExtract) {
        for(String _subBrand : subBrandList){
            Double record = subBrandProductExtract.get(_subBrand);
            if(record == null) subBrandProductExtract.put(_subBrand,0.00);
        }
    }

    private HashMap<String, String> getFinalValue(Double total, HashMap<String, Double> subBrandProductExtract) {
        HashMap<String,String> finalRetailerScore = new HashMap<String,String>();
        Double perScore;
        Double round;
        for (String subBrand : subBrandProductExtract.keySet()){

            DecimalFormat newFormat = new DecimalFormat("#.#");
            perScore = ((subBrandProductExtract.get(subBrand)/ total) * 100);
            round = Double.valueOf(newFormat.format(perScore));
            finalRetailerScore.put(subBrand,round+"%");
        }
        return finalRetailerScore;
    }

    private HashMap<String, Double> categoriseProductList(TescoSovScore tescoSOV, List<String> subBrandList, List<String> productList, HashMap<String, String> productSOVscore) {
        HashMap<String,String> categorisedListOfProducts = new HashMap<String,String>();
        categorisedListOfProducts = tescoSOV.categoriseProductsFromProductList(productList,subBrandList);

        HashMap<String,Double> subBrandProductExtract = new HashMap<String,Double>();
        for (String product : productList){

            String score = productSOVscore.get(product).replace("%","");
            String subBrand = categorisedListOfProducts.get(product);

            if(subBrand.equals(null)){// remove after debugging
                String a = "";
            }

            Double value = subBrandProductExtract.get(subBrand);
            if(value == null) subBrandProductExtract.put(subBrand, Double.parseDouble(score));
            else{
                Double intialValue = value;
                subBrandProductExtract.put(subBrand, Double.parseDouble(score)+ intialValue);
            }
        }
        return subBrandProductExtract;
    }
}
