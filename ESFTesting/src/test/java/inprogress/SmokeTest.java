package inprogress;

import com.envirtual.esf.BaseClass.*;
import com.envirtual.esf.ReportPages.*;
import org.apache.commons.lang3.StringUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/**
 * Created by Salako on 23/04/2015.
 */
public class SmokeTest {

    protected WebDriver driver;
    BaseClass_ESF baseClassESF;

    @Before
    public void setUp() throws Exception {
        baseClassESF = new BaseClass_ESF("chrome", BaseClass_ESF.environment.si, null);
        driver = baseClassESF.getDriver();
    }

    @After
    public void tearDown() throws Exception {
        this.driver.quit();
    }

    public SmokeTest() {
    }


    @Test
    public void CokeReportPageTests() {

        try {
            smokeTest(BaseClass_ESF.pages.esfpagecoke.toString(), new CokeReportPageObjects(driver));
        } catch (Exception exp) {
            System.out.println("Coke report - " + exp.toString());
        } finally {
            driver.quit();
        }
    }

    @Test
    public void BirdsEyeReportPageTests() {

        smokeTest(BaseClass_ESF.pages.esfpagebirdseye.toString(), new BirdseyePageObjects(driver));
    }

    @Test
    public void BiscuitsReportPageTests() {

        smokeTest(BaseClass_ESF.pages.esfpagebiscuit.toString(), new BiscuitsPageObjects(driver));
    }

    @Test
    public void LaundryReportPageTests() {

        smokeTest(BaseClass_ESF.pages.esfpagelaundry.toString(), new LaundryPageObjects(driver));
    }

    @Test
    public void NestleReportPageTests() {

        smokeTest(BaseClass_ESF.pages.esfpagenestle.toString(), new NestlePageObjects(driver));
    }

    @Test
    public void SabmillerReportPageTests() {

        smokeTest(BaseClass_ESF.pages.esfpagesabmiller.toString(), new SabmillerPageObjects(driver));
    }

    @Test
    public void SausageReportPageTests() {

        smokeTest(BaseClass_ESF.pages.esfpagesausages.toString(), new SausagePageObjects(driver));
    }

    @Test
    public void ScaReportPageTests() {

        smokeTest(BaseClass_ESF.pages.esfpagesca.toString(), new ScaPageObjects(driver));
    }

    @Test
    public void ToothPasteReportPageTests() {

        smokeTest(BaseClass_ESF.pages.esfpagetoothpaste.toString(), new ToothpastePageObjects(driver));
    }

    private void smokeTest(String reportPage, ReportPageObjects reportPageObjects) {

        // navigate to coke report page
        baseClassESF.navigateToURL(reportPage);

        // get left nav elements
        esfPageObjects esfPage = new esfPageObjects(driver);
        esfPage.waitForLeftNavToLoad();
        List<String> leftnavLinks = null;
        leftnavLinks = esfPage.getAllLeftNavElements();

        // verify total number of require links are displayed
        assert leftnavLinks.size() == reportPageObjects.noOfLeftNavLinks() : "5 left navigation report links are expected. Only " + leftnavLinks.size() + " links are displayed"; // some report pages have 4

        // verify link names
        List<String> links = esfPage.esfLeftNavLinks();
        List<String> result = new ArrayList<String>();
        boolean status = false;
        int count = 0;

        for (String text : leftnavLinks) {
            for (String link : links) {
                count++;
                if (text.contains(link)) {
                    status = true;
                    count = 0;
                    break;
                }
                if (count == links.size() && status == false) result.add(text);
            }
        }
        assert result.size() < 1 : "There is a missing link on left nav";

        if (result.size() > 1) {
            for (String link : result) {
                assert StringUtils.isNotEmpty(link) : link + " link is not displayed on left nav";
            }
        }

        // search term analysis test
        if(reportPageObjects.noOfLeftNavLinks() > 4) {
            SearchTermAnalysisTest(reportPageObjects, reportPage);
        }

        //search term comparison
        SearchTermComparison(reportPageObjects, reportPage);

        //retailer comparison
        RetailerComparison(reportPageObjects, reportPage);

        // product basics
        ProductBasics(reportPageObjects, reportPage);

        // product compliance
        ProductCompliance(reportPageObjects, reportPage);

    }

    public void SearchTermAnalysisTest(ReportPageObjects page, String reportPage) {

        int errorCount = 0;
        boolean status = false;
        esfPageObjects esfpage = new esfPageObjects(driver);

        //click on each link and verify data is displayed
        WebElement leftnav = esfpage.getLeftNavElement(esfPageObjects.leftNavLinks.Search_Term_Analysis);
        leftnav.click();
        esfpage.waitForLeftNavToLoad();
        esfpage.waitForSpinnerToLoad();

        SearchTermAnalysis_PageObjects searchTermAnalysis = new SearchTermAnalysis_PageObjects(driver);

        List<String> journeys = page.journeyList();
        int count = 0;

        for (String localJourney : journeys) {

            try {

                status = esfpage.selectJourney(localJourney);
                assert status : "error occurred selecting " + localJourney;
                if (!status) continue;

                if (count < 1) {
                    // gets todays date from calender
                    String Today = esfpage.selectDayOfMOnth();
                    // select date on calender
                    esfpage.selectDateOnCalender(Today, true);
                }
                count++;

                status = esfpage.isNoProductFoundMessageDisplayed();
                assert !status : "No product found warning selecting " + localJourney + " journey";
                // if no product found message is displayed continue
                if (esfpage.isNoProductFoundMessageDisplayed()) continue;

                // assert correct page is displayed
                String title = esfpage.getPageTitle();
                assert !title.isEmpty() : "Search Term Analysis Page Title should not be null";
                assert title.toLowerCase().equals("search term analysis") : "Incorrect page title for Search Term Analysis. Current title - " + title + "expected Search Term Analysis";

                // assert page objects
                String label = esfpage.getretailerLabel().getText();
                assert label.toLowerCase().equals("journey") : "Incorrect label on Search Term Analysis page. Expected 'Journey' label Actual label " + label;

                WebElement journey = esfpage.getRetailerElement();
                assert journey != null : "Journey drop down element is not available on Search Term Analysis page";

                String dateLabel = esfpage.getCalendarLabel().getText();
                assert dateLabel.toLowerCase().equals("date") : "Incorrect date label on Search Term Analysis page. Expected 'Date' label Actual label " + dateLabel;

                WebElement date = esfpage.getCalendarElement();
                assert date != null : "Calendar element is not available on Search Term Analysis page";

                // verify frames or tables are not null
                List<String> configuredTableNames = page.searchTermAnalysisTableNames();
                HashMap<String, WebElement> sessionTableNames = esfpage.getTableHeaders();

                assert sessionTableNames.size() == configuredTableNames.size() : "No of brand tables displayed is higher than expected number of tables. Expected " + configuredTableNames.size() + " tables "
                        + sessionTableNames.size() + "is displayed";

                // verify brands are displayed
                for (String sessiontableName : sessionTableNames.keySet()) {
                    assert configuredTableNames.contains(sessiontableName) : sessiontableName + " table brand should not be displayed on Search Term Analysis page";
                }

            } catch (AssertionError err) {
                errorCount++;
                System.out.println(reportPage + "-" + localJourney + " - " + err.toString());
            }
        }
        assert errorCount == 0 : errorCount + " error(s) found. Check logs";
    }

    public void SearchTermComparison(ReportPageObjects page, String reportPage) {

        int errorCount = 0;
        boolean status = false;
        esfPageObjects esfpage = new esfPageObjects(driver);

        //click on each link and verify data is displayed
        WebElement leftnav = esfpage.getLeftNavElement(esfPageObjects.leftNavLinks.Search_Term_Comparison);
        leftnav.click();
        esfpage.waitForSpinnerToLoad();
        esfpage.sleepShort();

        // Verify data export link is displayed
        try {
            assert esfpage.getDataExportLink() : "Data export link is not displayed on Search Term Analysis Page";
        } catch (AssertionError err) {
            errorCount++;
            System.out.println(reportPage + " - " + err.toString());
        }

        List<String> retailers = esfpage.esfRetailersList();
        List<String> lowerJourneyList = esfpage.convertListToLower(page.journeyList());

        int count = 0;

        for (String retailer : retailers) {
            try {
                // select retailer
                esfpage.selectJourney(retailer);

                //selects calendar on first journey only
                if (count < 1) {
                    // gets todays date from calender
                    String Today = esfpage.selectDayOfMOnth();
                    // select date on calender
                    esfpage.selectDateOnCalender(Today, true);
                }

                count++;

                status = esfpage.isNoProductFoundMessageDisplayed();
                assert !status : "No product is available on Search Term Analysis for " + retailer;

                if (status) continue;

                // get brand tables
                List<String> brands = esfpage.getTableNames();

                assert brands.size() == lowerJourneyList.size() : "Total number of brands table listed on Search Term Comparison page has failed. Expected " + lowerJourneyList.size() + "Actual " +
                        brands.size();

                for (String brand : brands) {
                    try {
                        // verify table is available
                        assert lowerJourneyList.contains(brand.toLowerCase()) : brand + " table on Search Term Comparison page should not be displayed";
                        //verify table contains products
                        List<String> prodCount = esfpage.getBrandTableProducts(brand);
                        assert prodCount.size() > 0 : brand + " table  on Search Term Comparison page does not have any listed products";
                    } catch (Exception | AssertionError err) {
                        errorCount++;
                        System.out.println(reportPage + "-" + retailer + " - " + err.toString());
                    }
                }
            } catch (AssertionError err) {
                errorCount++;
                System.out.println(reportPage + "-" + retailer + " - " + err.toString());

            }
        }
        assert errorCount == 0 : errorCount + " error(s) found: check logs";
    }

    public void RetailerComparison(ReportPageObjects page, String reportPage) {
        int errorCount = 0;
        String journey = null;
        int count = 0;


        boolean status = false;
        esfPageObjects esfpage = new esfPageObjects(driver);

        //click on each link and verify data is displayed
        WebElement leftnav = esfpage.getLeftNavElement(esfPageObjects.leftNavLinks.Retailer_Comparison);
        leftnav.click();
        esfpage.waitForLeftNavToLoad();
        esfpage.waitForSpinnerToLoad();

        RetailerComparison_PageObjects retailerComparison = new RetailerComparison_PageObjects();
        List<String> journeys = page.journeyList();

        for (String localJourney : journeys) {
            journey = localJourney;

            try {

                // select retailer
                esfpage.selectJourney(localJourney);

                status = esfpage.isNoProductFoundMessageDisplayed();
                assert !status : "No product found warning selecting " + localJourney + " journey";
                // if no product found message is displayed continue
                if (esfpage.isNoProductFoundMessageDisplayed()) continue;

                // assert correct page is displayed
                String title = esfpage.getPageTitle();
                assert !title.isEmpty() : "Retailer Comparison Page Title should not be null";
                assert title.toLowerCase().equals("retailer comparison") : "Incorrect page title for Retailer Comparison. Current title - " + title + "expected Retailer Comparison";

                // assert page objects
                String label = esfpage.getretailerLabel().getText();
                assert label.toLowerCase().equals("journey") : "Incorrect label on Retailer Comparison page. Expected 'Journey' label Actual label " + label;

                WebElement Journey = esfpage.getRetailerElement();
                assert Journey != null : "Journey drop down element is not available on Retailer Comparison page";

                String dateLabel = esfpage.getCalendarLabel().getText();
                assert dateLabel.toLowerCase().equals("date") : "Incorrect date label on Retailer Comparison page. Expected 'Date' label Actual label " + dateLabel;

                WebElement date = esfpage.getCalendarElement();
                assert date != null : "Calendar element is not available on Retailer Comparison page";

                if (count < 1) {
                    // gets todays date from calender
                    String Today = esfpage.selectDayOfMOnth();
                    // select date on calender
                    esfpage.selectDateOnCalender(Today, false);
                }
                count++;

                // verify frames or tables are not null
                List<String> configuredTableNames = esfpage.esfRetailersList();
                HashMap<String, WebElement> sessionTableNames = esfpage.getTableHeaders();

                assert sessionTableNames.size() == configuredTableNames.size() : "No of brand tables displayed is higher than expected number of tables. Expected " + configuredTableNames.size() + " tables "
                        + sessionTableNames.size() + "is displayed";

                // verify brands are displayed
                for (String sessiontableName : sessionTableNames.keySet()) {
                    if (sessiontableName.equals("Asda")) sessiontableName = "ASDA";
                    assert configuredTableNames.contains(sessiontableName) : sessiontableName + " table brand should not be displayed on Retailer Comparison page";
                }

                //selects calendar on first journey only
                if (count < 1) {
                    // gets todays date from calender
                    String Today = esfpage.selectDayOfMOnth();
                    // select date on calender
                    esfpage.selectDateOnCalender(Today, true);
                }

                count++;

                status = esfpage.isNoProductFoundMessageDisplayed();
                assert !status : "No product is available on Retailer Comparison for " + localJourney;

                if (status) continue;

                // List<String> retailers = esfpage.esfRetailersList();
                List<String> lowerJourneyList = esfpage.convertListToLower(esfpage.esfRetailersList());

                // get brand tables
                List<String> retailers = esfpage.getTableNames();

                assert retailers.size() == lowerJourneyList.size() : "Total number of brands table listed on  Retailer Comparison page has failed. Expected " + lowerJourneyList.size() + "Actual " +
                        retailers.size();

                    /*esfpage.softAssert(brands.size() == lowerJourneyList.size(),"Total number of brands table listed on Search Term Comparison page has failed. Expected " + lowerJourneyList.size() + "Actual " +
                            brands.size(),reportPage);*/

                for (String brand : retailers) {
                    try {
                        // verify table is available
                        assert lowerJourneyList.contains(brand.toLowerCase()) : brand + " table on  Retailer Comparison page should not be displayed";
                        //esfpage.softAssert(lowerJourneyList.contains(brand.toLowerCase()),brand + " table on Search Term Comparison page should not be displayed",reportPage);
                        //verify table contains products
                        List<String> prodCount = esfpage.getBrandTableProducts(brand);
                        assert prodCount.size() > 0 : brand + " table  on  Retailer Comparison page does not have any listed products";
                        //esfpage.softAssert( prodCount.size() > 0 ,brand + " table  on Search Term Comparison page does not have any listed products",reportPage);
                    } catch (Exception | AssertionError err) {
                        errorCount++;
                        System.out.println(reportPage + "-" + brand + " - " + err.toString());
                    }
                }

            } catch (AssertionError err) {
                errorCount++;
                System.out.println(reportPage + "-" + journey + " - " + err.toString());
            }
        }
        assert errorCount == 0 : errorCount + " error(s) found. Check logs";
    }

    public void ProductBasics(ReportPageObjects page, String reportPage) {

        int errorCount = 0;
        boolean status = false;
        esfPageObjects esfpage = new esfPageObjects(driver);

        //click on each link and verify data is displayed
        WebElement leftnav = esfpage.getLeftNavElement(esfPageObjects.leftNavLinks.Product_Basics);
        leftnav.click();
        esfpage.waitForSpinnerToLoad();
        esfpage.sleepShort();

        // Verify data export link is displayed
        try {
            assert esfpage.getDataExportLink() : "Data export link is not displayed on Product Basics Page";
        } catch (AssertionError err) {
            errorCount++;
            System.out.println(reportPage + " - " + err.toString());
        }

        List<String> retailers = esfpage.esfRetailersList();
        List<String> lowerJourneyList = esfpage.convertListToLower(page.productBasicsTableNames());

        int count = 0;

        for (String retailer : retailers) {
            try {
                // select retailer
                esfpage.selectJourney(retailer);

                //selects calendar on first journey only
                if (count < 1) {
                    // gets todays date from calender
                    String Today = esfpage.selectDayOfMOnth();
                    // select date on calender
                    esfpage.selectDateOnCalender(Today, true);
                }

                count++;

                status = esfpage.isNoProductFoundMessageDisplayed();
                assert !status : "No product is available on Product Basics for " + retailer;

                if (status) continue;

                // get brand table names
                List<String> brands = esfpage.getTableNames();

                assert brands.size() == lowerJourneyList.size() : "Total number of brands table listed on Product Basics page has failed. Expected " + lowerJourneyList.size() + "Actual " +
                        brands.size();

                for (String brand : brands) {
                    try {
                        // verify table is available
                        assert lowerJourneyList.contains(brand.toLowerCase()) : brand + " table on Product Basics page should not be displayed";
                        //verify table contains products
                        List<String> prodCount = esfpage.getBrandTableProducts(brand);
                        assert prodCount.size() > 0 : brand + " table  on Product Basics page does not have any listed products";
                    } catch (Exception | AssertionError err) {
                        errorCount++;
                        System.out.println(reportPage + "-" + retailer + " - " + err.toString());
                    }
                }
            } catch (AssertionError err) {
                errorCount++;
                System.out.println(reportPage + "-" + retailer + " - " + err.toString());

            }
        }
        assert errorCount == 0 : errorCount + " error(s) found: check logs";
    }

    public void ProductCompliance(ReportPageObjects page, String reportPage) {

        int errorCount = 0;
        boolean status = false;
        esfPageObjects esfpage = new esfPageObjects(driver);

        //click on each link and verify data is displayed
        WebElement leftnav = esfpage.getLeftNavElement(esfPageObjects.leftNavLinks.Product_Compliance);
        leftnav.click();
        esfpage.waitForSpinnerToLoad();
        esfpage.sleepShort();

        // Verify data export link is displayed
        try {
            assert esfpage.getDataExportLink() : "Data export link is not displayed on Product Compliance Page";
        } catch (AssertionError err) {
            errorCount++;
            System.out.println(reportPage + " - " + err.toString());
        }

        List<String> retailers = esfpage.esfRetailersList();
        List<String> lowerJourneyList = esfpage.convertListToLower(page.productBasicsTableNames());

        int count = 0;

        for (String retailer : retailers) {
            try {
                // select retailer
                esfpage.selectJourney(retailer);

                //selects calendar on first journey only
                if (count < 1) {
                    // gets todays date from calender
                    String Today = esfpage.selectDayOfMOnth();
                    // select date on calender
                    esfpage.selectDateOnCalender(Today, true);
                }

                count++;

                status = esfpage.isNoProductFoundMessageDisplayed();
                assert !status : "No product is available on Product Compliance for " + retailer;

                if (status) continue;

                // get brand table names
                List<String> brands = esfpage.getTableNames();

                assert brands.size() == lowerJourneyList.size() : "Total number of brands table listed on Product Compliance page has failed. Expected " + lowerJourneyList.size() + "Actual " +
                        brands.size();

                for (String brand : brands) {
                    try {
                        // verify table is available
                        assert lowerJourneyList.contains(brand.toLowerCase()) : brand + " table on Product Compliance page should not be displayed";
                        //verify table contains products
                        List<String> prodCount = esfpage.getBrandTableProducts(brand);
                        assert prodCount.size() > 0 : brand + " table  on Product Compliance page does not have any listed products";
                    } catch (Exception | AssertionError err) {
                        errorCount++;
                        System.out.println(reportPage + "-" + retailer + " - " + err.toString());
                    }
                }
            } catch (AssertionError err) {
                errorCount++;
                System.out.println(reportPage + "-" + retailer + " - " + err.toString());

            }
        }
        assert errorCount == 0 : errorCount + " error(s) found: check logs";
    }

}

