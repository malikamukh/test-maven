import com.github.javafaker.Faker;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringJoiner;

public class UsedCarSearch {
    @Test
    public void creatingOrder() throws InterruptedException {
        WebDriver driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        driver.manage().window().maximize();

        driver.get("https://www.cargurus.com/");

        driver.findElement(By.xpath("//span[.='Buy Used']")).click();

        String actualAllMakes =  new Select(driver.findElement(By.id("carPickerUsed_makerSelect"))).getFirstSelectedOption().getText();
        Assert.assertEquals(actualAllMakes,"All Makes");

        WebElement allMakesDropdown = driver.findElement(By.id("carPickerUsed_makerSelect"));
        Select select = new Select(allMakesDropdown);
        select.selectByVisibleText("Lamborghini");

        String actualAllModels =  new Select(driver.findElement(By.id("carPickerUsed_modelSelect"))).getFirstSelectedOption().getText();
        Assert.assertEquals(actualAllModels,"All Models");


        List<String> expectedValues = List.of("All Models", "Aventador", "Huracan", "Urus", "400GT", "Centenario", "Countach", "Diablo", "Espada", "Gallardo", "Murcielago");


        List<WebElement> modelOption= new Select (driver.findElement(By.id("carPickerUsed_modelSelect"))).getOptions();


        List<String> actualValues =  new ArrayList<>();

        for (WebElement option : modelOption) {
            actualValues.add( option.getText() );
        }

        Assert.assertEquals( actualValues, expectedValues);

        new Select(driver.findElement(By.id("carPickerUsed_modelSelect"))).selectByVisibleText("Gallardo");

        driver.findElement(By.id("dealFinderZipUsedId_dealFinderForm")).sendKeys("22031", Keys.ENTER);

        Thread.sleep(1000);
        List <WebElement> elements = driver.findElements(By.xpath("//a[@data-cg-ft='car-blade-link'][not(contains(@href, 'FEATURED'))]"));
        if (elements.size()==0){
            throw new RuntimeException("List is empty");
        }else{
            System.out.println("There are " + elements.size() + " search results in this page.");
        }

        Assert.assertEquals(elements.size(), 15);

        Thread.sleep(1000);
        List <WebElement> titleElements = driver.findElements(By.xpath("//h4[@class='vO42pn']/ancestor::a[not(contains(@href, 'FEATURED'))]"));
        for (WebElement titleElement: titleElements){
            Assert.assertTrue(titleElement.getText().contains("Lamborghini Gallardo"), "The title contains Lamborghini Gallardo");
        }
        //List <WebElement> titleElements = driver.findElements(By.xpath("//h4[@class='vO42pn']"));
        //for (int i=1; i<titleElements.size(); i++){
        //Assert.assertTrue(titleElements.get(i).getText().contains("Lamborghini Gallardo"), "The title contains Lamborghini Gallardo");
        //}


        new Select(driver.findElement(By.id("sort-listing"))).selectByVisibleText("Lowest price first");
        Thread.sleep(2000);
        List <WebElement> lpfElements =driver.findElements(By.xpath("//span[@class='JzvPHo']"));
        List <Double> prices =new ArrayList<>();
        for (int i=1; i<lpfElements.size(); i++){
            String price = lpfElements.get(i).getText();
            price = price.split(" ")[0];
            prices.add(Double.parseDouble(price.replace("$","").replace(",", "")));

        }
        Thread.sleep(1000);
        for (int i=1; i< prices.size(); i++){
            Assert.assertTrue(prices.get(i-1)<= prices.get(i), "Checking result are sorted in ascending order.");
        }

        new Select(driver.findElement(By.id("sort-listing"))).selectByVisibleText("Highest mileage first");
        Thread.sleep(2000);
        List <WebElement> hmfElements =driver.findElements(By.xpath("//p[@class='JKzfU4 umcYBP']"));
        List <Double> mileages =new ArrayList<>();
        for (int i=1; i<hmfElements.size(); i++){
            String mileage = hmfElements.get(i).getText();
            mileages.add(Double.parseDouble(mileage.replace(" mi","").replace(",", "")));

        }
        Thread.sleep(1000);
        for (int i=1; i< mileages.size(); i++){
            Assert.assertTrue(mileages.get(i-1)>= mileages.get(i), "Checking result are sorted in descending order.");
        }
        Thread.sleep(2000);
        WebElement awdCheckbox = driver.findElement(By.xpath("//input[@value='Coupe AWD']"));
        Thread.sleep(2000);
        if (!(awdCheckbox.isSelected())){
            driver.findElement(By.xpath("//input[@value='Coupe AWD']/following-sibling::p")).click();
        }



        Thread.sleep(5000);
        List <WebElement> awdElements = driver.findElements(By.xpath("//h4[@class='vO42pn']"));
        for (int i=0; i<awdElements.size(); i++){
            Assert.assertTrue(awdElements.get(i).getText().contains("Coupe AWD"), "The title contains Coupe AWD");
        }
        Thread.sleep(2000);
        awdElements.get(awdElements.size()-1).click();

        driver.findElement(By.xpath("//button[@class='r1inOn']")).click(); // go back to the result page
        Thread.sleep(2000);
        List <WebElement> images = driver.findElements(By.xpath("//img[@class='C6f2e2 bmTmAy']"));

        String viewedtext = images.get(images.size()-1).findElement(By.xpath("//parent ::div/following-sibling::div/p")).getText(); //find the "Viewed" of the last search result
        Assert.assertEquals(viewedtext, "Viewed", "The link is not viewed");

        driver.quit();





    }
}
