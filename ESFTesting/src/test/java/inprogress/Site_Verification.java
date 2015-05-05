package inprogress;

import com.envirtual.esf.BaseClass.BaseClass_Retailers;
import org.junit.Test;
import org.openqa.selenium.WebDriver;

/**
 * Created by Salako on 10/04/2015.
 */
public class Site_Verification {

    WebDriver driver;
    BaseClass_Retailers baseClassRetailers;

    public Site_Verification(){
     baseClassRetailers = new BaseClass_Retailers(null);
        driver = baseClassRetailers.getDriver();
    }

    @Test
    public void siteVerificationTesco(){
        baseClassRetailers.navigateToURL(BaseClass_Retailers.pages.mainPageTesco ,null); // null value is passsed as this is not required for main page navigation
        // *** assert that page is loaded and url contains main url. indicate page not loaded on fail;
        String sku = "coke";
        baseClassRetailers.navigateToURL(BaseClass_Retailers.pages.listPageTesco,sku); // sku is added to listpage url to navigate to product list page
        // *** assert that list page is loaded and products are listed. indicate product not listed on fail;
        assert 1 !=2 : "";
    }

    @Test
    public void siteVerificationAmazon(){
        baseClassRetailers.navigateToURL(BaseClass_Retailers.pages.listPageAmazon,null); // null value is passsed as this is not required for main page navigation
        // *** assert that page is loaded and url contains main url. indicate page not loaded on fail;
        String sku = "coke";
        baseClassRetailers.navigateToURL(BaseClass_Retailers.pages.listPageAmazon,sku); // sku is added to listpage url to navigate to product list page
        // *** assert that list page is loaded and products are listed. indicate product not listed on fail;
    }

    @Test
    public void siteVerificationAsda(){
        baseClassRetailers.navigateToURL(BaseClass_Retailers.pages.mainPageAsda,null); // null value is passsed as this is not required for main page navigation
        // *** assert that page is loaded and url contains main url. indicate page not loaded on fail;
        String sku = "coke";
        baseClassRetailers.navigateToURL(BaseClass_Retailers.pages.listPageAsda,sku); // sku is added to listpage url to navigate to product list page
        // *** assert that list page is loaded and products are listed. indicate product not listed on fail;
    }

    @Test
    public void siteVerificationMorrisons(){
        baseClassRetailers.navigateToURL(BaseClass_Retailers.pages.mainPageMorrisons,null); // null value is passsed as this is not required for main page navigation
        // *** assert that page is loaded and url contains main url. indicate page not loaded on fail;
        String sku = "coke";
        baseClassRetailers.navigateToURL(BaseClass_Retailers.pages.listPageMorrisons,sku); // sku is added to listpage url to navigate to product list page
        // *** assert that list page is loaded and products are listed. indicate product not listed on fail;
    }

    @Test
    public void siteVerificationOcado(){
        baseClassRetailers.navigateToURL(BaseClass_Retailers.pages.mainPageOcado,null); // null value is passsed as this is not required for main page navigation
        // *** assert that page is loaded and url contains main url. indicate page not loaded on fail;
        String sku = "coke";
        baseClassRetailers.navigateToURL(BaseClass_Retailers.pages.listPageOcado,sku); // sku is added to listpage url to navigate to product list page
        // *** assert that list page is loaded and products are listed. indicate product not listed on fail;
    }

    @Test
    public void siteVerificationSainsbury(){
        baseClassRetailers.navigateToURL(BaseClass_Retailers.pages.mainPageSainsbury,null); // null value is passsed as this is not required for main page navigation
        // *** assert that page is loaded and url contains main url. indicate page not loaded on fail;
        String sku = "coke";
        baseClassRetailers.navigateToURL(BaseClass_Retailers.pages.listPageSainsbury,sku); // sku is added to listpage url to navigate to product list page
        // *** assert that list page is loaded and products are listed. indicate product not listed on fail;
    }

    @Test
    public void siteVerificationWaitrose(){
        baseClassRetailers.navigateToURL(BaseClass_Retailers.pages.mainPageWaitrose,null); // null value is passsed as this is not required for main page navigation
        // *** assert that page is loaded and url contains main url. indicate page not loaded on fail;
        String sku = "coke";
        baseClassRetailers.navigateToURL(BaseClass_Retailers.pages.listPageWaitrose,sku); // sku is added to listpage url to navigate to product list page
        // *** assert that list page is loaded and products are listed. indicate product not listed on fail;
    }
}
