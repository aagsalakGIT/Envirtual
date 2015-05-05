package inprogress;

import com.envirtual.esf.BaseClass.BaseClass_ESF;
import com.envirtual.esf.BaseClass.BaseClass_Retailers;
import com.envirtual.esf.BaseClass.esfPageObjects;
import com.envirtual.esf.Retailers.Tesco.ListerPageObjectTesco;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Salako on 10/04/2015.
 */
public class Product_Ranking {

    protected WebDriver driver;
    BaseClass_Retailers baseClassRetailers;

    public Product_Ranking(){
        baseClassRetailers = new BaseClass_Retailers(BaseClass_Retailers.selectBrowser.firefox);
        driver = baseClassRetailers.getDriver();
    }

    @Test
    public void rankingTestTesco(){

        // lauch retailer
        baseClassRetailers.navigateToURL(BaseClass_Retailers.pages.mainPageTesco,null); // null value is passsed as this is not required for main page navigation

        // instantiate Tesco lister page assign value to page
        ListerPageObjectTesco page = new ListerPageObjectTesco(driver);

        // Assert page url and main logo
        assert driver.getCurrentUrl().toLowerCase().contains("tesco") && driver.getCurrentUrl().toLowerCase().contains("groceries"): "Tesco page URL issue";

        // product to use for test is assigned to sku variable
        String sku = "coke";

        // navigate to retailer search result page
        baseClassRetailers.navigateToURL(BaseClass_Retailers.pages.listPageTesco, sku); // sku is added to listpage url to navigate to product list page

        // wait until search result is displayed and get value
        page.waitForElementToAppear(page.mainLogo);

        assert page.isMainLogoDisplayed() == true : "Tesco page failed load , Main logo is amended or not displayed";

        // get search result
        String searchResult = page.getNumberOfListedProducts();
        assert StringUtils.isNotEmpty(searchResult) : "Tesco Search result value is empty or null";

        //Ensure list page structure is as expected in page object
        assert page.verifyProductGridIsDisplayed() : "Tesco product grid on list page is not displayed or page element structure might have changed";

        // get list of listed products
        List<WebElement> products = page.getListedProducts();
        assert products.size() > 0 : "Tesco: no products available on lister page. Page element structure might have been amended";

        // rank list and add to a dictionary for referencing
        int count = 11; // count is the total number of products to add to productRanking list
        Map<String, WebElement> productRanking = page.rankProducts(count);
        Map<String, String> productRankingReference = new HashMap<String,String>();
        for (String ranking: productRanking.keySet()){
            assert StringUtils.isNotEmpty(ranking) : "Tesco rank is null from list";
            String value = page.getProductNameFromDictionaryElement(productRanking.get(""+ranking));
            assert StringUtils.isNotEmpty(value) : "Tesco product name is null from list";
            productRankingReference.put(ranking,value);
        }

         // navigate to esf result page of sku variable listed above
        BaseClass_ESF esf = new BaseClass_ESF(null, BaseClass_ESF.environment.prod,driver);

        // navigate to report page for a specific brand
        driver = esf.navigateToURL(BaseClass_ESF.pages.esfpagecoke.toString());

        // get new window
        // Set<String> windows = esf.getWindowOpenWindows();
        // assert windows.size() > 0 : "ESF window is not launched as expected";

        // switch focus to new(esf) window
        //  esf.switchToWindow(windows.iterator().next());

        // instantiate esfpage object and get left nav elements
        esfPageObjects esfPage = new esfPageObjects(driver);
        WebElement leftnav = esfPage.getLeftNavElement(esfPageObjects.leftNavLinks.Retailer_Comparison);
        assert leftnav != null : "ESF page: No left nav elements are returned";
        leftnav.click();
        esfPage.waitForSpinnerToLoad();


        // get list of ranked products on esf website
        // compare list using rank number and key for both. If match is false add to a new list and return user friendly result
    }

}
