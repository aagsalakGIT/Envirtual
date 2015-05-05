package com.envirtual.esf.BaseClass;

import com.envirtual.esf.ReportPages.ReportPageObjects;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by Salako on 15/04/2015.
 */
public class esfPageObjects {

    protected WebDriver Driver;

    public esfPageObjects(WebDriver driver) {
        Driver = driver;
    }

    private By PageTitle = By.cssSelector("small.ng-binding");
    private By tableNames = By.cssSelector("h3");
    private By brandTables = By.tagName("table");
    private By leftNav = By.cssSelector("li.ng-scope");
    private By SpinnerCompleted = By.cssSelector("i.fa.fa-spinner.fa-lg.fa-pulse.ng-hide");
    private By SpinnerLoading = By.cssSelector("i.fa.fa-spinner.fa-lg.fa-pulse");
    private By JourneyList = By.cssSelector("select.form-control");
    private By Calender = By.cssSelector("div.input-group.date.ng-valid");
    private By noProductFound = By.cssSelector("div.alert.alert-danger");
    private By FormGroups = By.cssSelector("div.form-group");
    private By exportdataLink = By.cssSelector("i.fa.fa-download");

    public enum leftNavLinks {
        Search_Term_Comparison, Retailer_Comparison, Product_Basics, Product_Compliance, Search_Term_Analysis
    }

    public String getPageTitle() {

        return Driver.findElement(PageTitle).getText();
    }

    public WebElement getLeftNavElement(Enum text) {

        List<WebElement> leftnavElements = Driver.findElements(leftNav);
        if (leftnavElements.size() < 3) return null;
        for (WebElement links : leftnavElements) {
            String linkText = links.findElement(By.tagName("a")).getAttribute("href");
            if (linkText.toString().toLowerCase().contains("shareofvoice") && text.toString().toLowerCase().contains("search_term_analysis"))
                return links.findElement(By.tagName("a"));// work around for inconsistent href name
            if (StringUtils.isNotEmpty(linkText) && linkText.toLowerCase().contains(text.toString().toLowerCase().replace("_", "")))
                return links.findElement(By.tagName("a")); // returns element with matching name from Text parameter
        }
        return null;
    }

    public List<String> getAllLeftNavElements() {
        List<String> data = new ArrayList<String>();
        List<WebElement> elements = Driver.findElements(leftNav);
        for (WebElement link : elements) {
            String linkText = link.findElement(By.tagName("a")).getAttribute("href");
            data.add(linkText.toString().toLowerCase());
        }
        return data;
    }

    public WebElement getretailerLabel() {

        List<WebElement> forms = Driver.findElements(FormGroups);
        if (forms.size() < 2) return null;
        for (WebElement element : forms) {
            WebElement label = element.findElement(By.tagName("label"));
            if (label.getText().toLowerCase().equals("journey")) return label;
        }
        return null;
    }

    public WebElement getCalendarLabel() {

        List<WebElement> forms = Driver.findElements(FormGroups);
        if (forms.size() < 2) return null;
        for (WebElement element : forms) {
            WebElement label = element.findElement(By.tagName("label"));
            if (label.getText().toLowerCase().equals("date")) return label;
        }
        return null;
    }

    public WebElement getRetailerElement() {

        List<WebElement> forms = Driver.findElements(FormGroups);
        if (forms.size() < 2) return null;
        for (WebElement element : forms) {
            WebElement select = element.findElement(By.tagName("select"));
            if (select.getAttribute("ng-model").equals("filter.journey")) return element;
        }
        return null;
    }

    public HashMap<String, WebElement> getTableHeaders() {

        int count = 0;
        HashMap<String, WebElement> data = new HashMap<String, WebElement>();
        List<WebElement> tableHeaders = Driver.findElements(tableNames);
        if (tableHeaders.size() < 1) return null;
        for (WebElement header : tableHeaders) {
            String text = header.findElement(By.tagName("strong")).getText();
            if (StringUtils.isNotEmpty(text)) data.put(text, header);
        }
        return data;
    }

    public List<String> getBrandTableProducts(String brand) {
        Driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
        List<String> data = new ArrayList<String>();
        List<WebElement> tables = Driver.findElements(brandTables);
        if (tables.size() < 1) return null;
        for (WebElement tableName : tables) {
            List<WebElement> name = tableName.findElements(By.tagName("strong"));
            if (name.size() < 1) return null;
            if (StringUtils.isEmpty(name.get(0).getText())) return null;
            String text = name.get(0).getText();
            if (text.toLowerCase().equals(brand.toLowerCase())) {
                return returnProductsFromTable(tableName);
            }
        }
        return data;
    }

    private List<String> returnProductsFromTable(WebElement table) {
        Driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
        List<String> data = new ArrayList<String>();
        WebElement body = table.findElement(By.tagName("tbody"));
        List<WebElement> products = body.findElements(By.tagName("a"));
        for (WebElement product : products) {
            String name = product.getText();
            if (StringUtils.isNotEmpty(name)) data.add(name);
        }
        return data;
    }

    public List<String> getTableNames() {

        List<String> data = new ArrayList<String>();
        try {
            List<WebElement> tablenames = Driver.findElements(By.cssSelector("strong"));

            for (WebElement elem : tablenames) {
                String name = elem.getText();
                if (StringUtils.isNotEmpty(name)) data.add(name);
            }
        } catch (Exception exp) {
            System.out.println("Exception error occurred " + Driver.getCurrentUrl() + " " + exp.toString());
        }
        return data;
    }

    public WebElement getCalendarElement() {
        Driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
        List<WebElement> forms = Driver.findElements(FormGroups);
        if (forms.size() < 2) return null;
        for (WebElement element : forms) {
            List<WebElement> select = element.findElements(By.tagName("input"));
            if (select.size() < 1) continue;
            if (select.get(0).getAttribute("data-ng-model").equals("date")) {
                Driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
                return element;
            }
        }
        return null;

    }

    public boolean getDataExportLink() {
        try{
            return Driver.findElement(exportdataLink).isDisplayed();
        }catch (Exception exp){
            return false;
        }

    }

    public List<String> esfLeftNavLinks() {
        List<String> links = new ArrayList<String>();
        links.add("shareofvoice");
        links.add("searchtermcomparison");
        links.add("retailercomparison");
        links.add("productbasics");
        links.add("productcompliance");
        return links;
    }

    public boolean waitForSpinnerToLoad() {
        Driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
        int count = 0;
        do {
            try {
                count++;
                if (count > 20) return false;
                Thread.sleep(1200);
                if (Driver.findElement(SpinnerCompleted).isDisplayed()) return true;
                if (Driver.findElement(SpinnerLoading).isDisplayed()) continue;
                return true;
            } catch (Exception exp) {
                count++;
                if (count > 20) return false;
            }

        } while (true);
    }

    public List<String> esfRetailersList() {
        List<String> data = new ArrayList<String>();
        data.add("Tesco");
        data.add("Sainsburys");
        data.add("Morrisons");
        data.add("Waitrose");
        data.add("Ocado");
        data.add("Amazon");
        data.add("ASDA");
        return data;
    }

    public boolean waitForLeftNavToLoad() {
        List<WebElement> leftnav = null;
        int count = 0;
        do {
            try {

                count++;
                if (count > 10) return false;
                Thread.sleep(600);
                leftnav = Driver.findElements(leftNav);
                if (leftnav.size() < 4) continue;
                if (leftnav.size() > 4) return true;

            } catch (Exception exp) {
                count++;
                if (count > 10) return false;
            }

        } while (true);
    }

    public List<WebElement> getJourneyList() {
        // Driver.findElement(JourneyList).findElement(By.tagName("select")).click();
        return Driver.findElement(JourneyList).findElements(By.tagName("option"));
    }

    public void selectDropDown(WebElement element, String value) {
        Select dropdown = new Select(element);
        dropdown.selectByVisibleText(value);
        waitForSpinnerToLoad();
    }

    public boolean selectJourney(String brand) {
        try {
            Driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
            WebElement dropdown = Driver.findElement(JourneyList);//.findElement(By.tagName("select"));
            moveAndClickAtElement(dropdown);
            selectDropDown(dropdown, brand);
            Driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
            return true;
        } catch (Exception exp) {
            System.out.println("Exception error occurred " + Driver.getCurrentUrl() + " " + exp.toString());
        }
        return false;
    }

    public boolean softAssert(boolean status, String message, String reportPage) {

        ErrorLogs log = new ErrorLogs();

        try {
            assert status : message;
            return true;
        } catch (AssertionError err) {
            System.out.println("Exception error occurred on  " + err.toString());
            log.LogError(err.toString(), reportPage);
        }
        return false;
    }

    public void sleepShort() {
        try {
            Thread.sleep(600);
        } catch (Exception exp) {
        }

    }

    public void sleepLong() throws InterruptedException {
        try {
            Thread.sleep(1200);
        } catch (Exception exp) {
        }

    }

    public List<String> convertListToLower(List<String> products) {
        List<String> data = new ArrayList<String>();
        for (String prod : products) {
            data.add(prod.toLowerCase());
        }
        return data;
    }

    public void selectDateOnCalender(String dayOfMonth, boolean workAround) {
        List<WebElement> dateElement = new ArrayList<WebElement>();
        WebElement calenderView = Driver.findElement(Calender);
        if (!workAround) calenderView.findElement(By.tagName("span")).click();
        WebElement calenderFrame = Driver.findElement(By.cssSelector("div.bootstrap-datetimepicker-widget"));
        List<WebElement> dates = calenderDays(calenderFrame);
        if(!workAround) {
            for (WebElement days : dates) {
                String a = days.getText();
                if (days.getText().equals(dayOfMonth)) {
                    dateElement.add(days);
                /*days.click();
                waitForSpinnerToLoad();*/
                    continue;
                }
            }
            if (dateElement.size() == 1) dateElement.get(0).click();
            if (dateElement.size() == 2) dateElement.get(1).click();
            waitForSpinnerToLoad();
        }
        // work around for unable to get text on days
        if (workAround) {
            List<WebElement> input = Driver.findElements(By.tagName("input"));
            if (input.size() > 0) {
                try {
                    String initialText = getDateOfMonth();
                    String[] textArray = initialText.toString().split("/");
                    input.get(0).clear();
                    waitForSpinnerToLoad();
                    input.get(0).sendKeys(dayOfMonth + "-" + textArray[1] + "-" + textArray[0]);
                    waitForSpinnerToLoad();
                } catch (Exception exp) {
                }

            }
        }
    }

    public List<WebElement> calenderDays(WebElement Days) {
        return Days.findElements(By.tagName("td"));
    }

    public String getDateOfMonth() throws ParseException {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        Date date = new Date();
        return dateFormat.format(date);
    }

    public void moveAndClickAtElement(WebElement element) {
        Actions action = new Actions(Driver);
        action.moveToElement(element).click(element).build().perform();
    }

    public void clickAtElement(WebElement element) {
        Actions action = new Actions(Driver);
        action.click(element).build().perform();
    }

    public boolean isNoProductFoundMessageDisplayed() {
        try {
            return Driver.findElement(noProductFound).isDisplayed();
        } catch (Exception exp) {

        }
        return false;
    }

    public String selectDayOfMOnth() {
        String DateOfMonth = null;

        try {

            DateOfMonth = getDateOfMonth();

        } catch (ParseException e) {
            e.printStackTrace();
        }
        String[] dateString = DateOfMonth.toString().split("/");

        return dateString[dateString.length - 1];
    }
}
