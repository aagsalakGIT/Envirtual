package com.envirtual.esf.Retailers.Tesco;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Salako on 20/04/2015.
 */
public class TescoSovScore {

    private HashMap<String,String> TescoRanktable;
    protected WebDriver Driver;

    public TescoSovScore(WebDriver driver){
        Driver = driver;
    }

    public HashMap<String,String> getTescoRanktable(){
        return tescoRanktableSetUp();
    }

    private HashMap<String,String> tescoRanktableSetUp(){
        TescoRanktable = new HashMap<String,String>();

        // Add datata to Tesco rank table
        TescoRanktable.put("1","4");
        TescoRanktable.put("2","4");
        TescoRanktable.put("3","4");
        TescoRanktable.put("4","4");
        TescoRanktable.put("5","4");
        TescoRanktable.put("6","3");
        TescoRanktable.put("7","3");
        TescoRanktable.put("8","3");
        TescoRanktable.put("9","3");
        TescoRanktable.put("10","3");
        TescoRanktable.put("11","2.25");
        TescoRanktable.put("12","2.25");
        TescoRanktable.put("13","2.25");
        TescoRanktable.put("14","2.25");
        TescoRanktable.put("15","2.25");
        TescoRanktable.put("16","1.69");
        TescoRanktable.put("17","1.69");
        TescoRanktable.put("18","1.69");
        TescoRanktable.put("19","1.69");
        TescoRanktable.put("20","1.69");

        // return table
        return TescoRanktable;
    }

    public HashMap<String,String> getRetailerProductRanking(String retailer){
        // get list of tables on Rank comparison page
        List<WebElement> table = Driver.findElements(By.tagName("table"));
        if(table.size() < 1 ) return null;

        // locate specified product table
        for(WebElement elem : table){
            String text = elem.findElement(By.tagName("h3")).getText();

            // if product name matches table name from h3 text get list of products
            if(text.toLowerCase().equals(retailer.toLowerCase())) return getProductsOnTable(elem);
        }
        return null;
    }

    public HashMap<String,String> getProductsOnTable(WebElement table){
        HashMap<String,String> data = new HashMap<String,String>();

        try {

            List<WebElement> body = table.findElements(By.tagName("tbody")); // gets product table
            if(body.size() < 1) return null;
            List<WebElement> elements = body.get(0).findElements(By.tagName("tr"));

            for( WebElement element : elements){ // gets all element is table and extract rank and product name
                List<WebElement> values = element.findElements(By.tagName("td"));
                if(values.size() < 2) return null;
                String rank = values.get(0).findElement(By.tagName("span")).getText(); // gets rank text value
                String productName = values.get(1).findElement(By.tagName("a")).getText(); // gets product name text
                if(Integer.parseInt(rank) / Integer.parseInt(rank) == 1 && StringUtils.isNotEmpty(productName))
                    data.put(productName,rank); // adds data to dictionary
            }

        }catch (Exception exp){
            // do nothing
        }
        return data;
    }

    public HashMap<String,String> getTableHeaders(){
        int count = 0;
        HashMap<String,String> data = new HashMap<String,String>();
        WebElement table = Driver.findElement(By.id("Table"));
        WebElement Headers = table.findElement(By.tagName("thead"));
        List<WebElement> columnHeaders = Headers.findElements(By.cssSelector("th.ng-binding.ng-scope"));
        if(columnHeaders.size() < 7) return null;
        for (WebElement header : columnHeaders){
            count++; // increment count;
            String text = header.getText().trim();
            if(StringUtils.isNotEmpty(text)) data.put(text.toLowerCase(),""+count);
        }
        return data;
    }


    public HashMap<String,String> getCokeSubBrandsAndValue(String retailer){
        int count = 0;
        int indexing = 0;
        int retailerColumnValue = Integer.parseInt(getTableHeaders().get(retailer.toLowerCase()));

        HashMap<String,String> data = new HashMap<String,String>();

        WebElement table = Driver.findElement(By.id("Table"));

        WebElement Headers = table.findElement(By.tagName("tbody"));
        List<WebElement> columnSubBrands = Headers.findElements(By.tagName("tr"));

        if(columnSubBrands.size() < 7) return null;

        for (WebElement value : columnSubBrands){

            count++; // increment count;
            indexing = 0;

            List<WebElement> tds = value.findElements(By.tagName("td"));

            String subBrand = null;

            for (WebElement column : tds){

                String perceValue = null;

                //Assigns value to the sub-brand
                if(indexing == 0 && StringUtils.isEmpty(subBrand))
                    subBrand = column.getText();

                // keeps count of row numbers to decide on when to add element text to dictionary
                if(retailerColumnValue == indexing){
                    perceValue = column.getText().trim();
                    if(StringUtils.isNotEmpty(perceValue)){
                        if(subBrand.toLowerCase().equals("diet caffeine free")){
                            subBrand = "diet caffeine";
                        }
                        data.put(subBrand.toLowerCase(),perceValue);
                    }
                    indexing = 0;
                    break;
                }
                indexing ++;
            }
        }
        return data;
    }

    public List<String> getProductsFromDictionary(HashMap<String,String> dictionary){
        List<String> productList = new ArrayList<String>();

        for(String prod: dictionary.keySet()){
         productList.add(prod);
        }
        return productList;
    }

    public HashMap<String,String> categoriseProductsFromProductList(List<String> productList, List<String> categoryList) {
        HashMap<String,String> data = new HashMap<String,String>();
        String newProduct = "";
        String subCategory = "";
        int match = 0;
        ArrayList<String> matchList = new ArrayList<String>();

        for(String products : productList){

            /*if(products.toLowerCase().equals("coca cola diet coke caffeine free 1.75l")){ // remove after debugging
                String a = "";
            }*/

           newProduct = products.toLowerCase().replace("coke", "").replace("coca cola","regular").replace("coca-cola","regular").replace("  "," ").trim();

            if(products.toLowerCase().contains("zest")){
                data.put(products,"other");
                continue;
            }

            for (String category : categoryList) {

                subCategory = category.toLowerCase();
                if(newProduct.contains(subCategory)){
                    match++;
                    matchList.add(subCategory);
                }
            }

            if(match == 1) {
                data.put(products,matchList.get(0));
                matchList.clear();
                match = 0;
            }

            if(match == 2){

                boolean status = false;

                for (String subBrand : matchList){
                    String[] stringCount = subBrand.split(" ");
                    if(stringCount.length > 1){
                        data.put(products, subBrand.replace("free",""));
                        match = 0;
                        matchList.clear();
                        status = true;
                        break;
                    }
                }

                if(!status) {
                    matchList.remove("regular");
                    data.put(products, matchList.get(0));
                    matchList.clear();
                    match = 0;
                }
            }

            if(match > 2){

                matchList.remove("regular");
                if(matchList.contains("diet caffeine free")) data.put(products,"diet caffeine");
                if(matchList.contains("diet caffeine")) data.put(products,"diet caffeine");
                else if(matchList.contains("diet cherry")) data.put(products,"diet cherry");
                else if(matchList.contains("zero") && matchList.contains("cherry"))data.put(products,"other");
                else if(matchList.contains("citrus") && matchList.contains("zest")) data.put(products,"other");
                matchList.clear();
                match = 0;
            }
        }

        {
           for(String brand : categoryList){
               if(!data.values().contains(brand.toLowerCase())){
                   data.put("",brand.toLowerCase());
               }
           }
        }


        return data;
    }

   /* public HashMap<String,String> categoriseProductsFromProductList(List<String> productList, List<String> categoryList){
        HashMap<String,String> data = new HashMap<String,String>();
        int match = 0;
        ArrayList<String> matchList = new ArrayList<String>();
        String prodCategory = "";
        String newProduct = "";
        for(String products : productList){

            newProduct = products.toLowerCase().replace("coke","");

            for (String category : categoryList) {

               prodCategory = category.toLowerCase();

                if (prodCategory.equals("regular")) prodCategory = "coca-cola";

                if (newProduct.contains(prodCategory)) {
                    match++;
                    matchList.add(prodCategory);
                }
            }
                // verifies only one sub category mach is found
                if(match == 1){
                    data.put(products,matchList.get(0));
                    match = 0;
                    matchList.clear();
                }

                // if more than one match is found
                if(match > 1 ){

                    // remove coca-cola to make other sub brands priority
                   newProduct = newProduct.replace("coca-cola","");

                    // verifies subcategory on amended product name
                    for (String matches : matchList){
                          if(newProduct.contains(matches.toLowerCase())){
                            data.put(products,matches);
                            match = 0;
                        }
                    }
                    matchList.clear();
                }

        }
        return data;
    }*/
}
