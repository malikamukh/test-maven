import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;

public class SeleniumDemo {

    public static void main(String[] args){

        // Assert.assertEquals(10,20);
        WebDriver driver = new ChromeDriver();
        // In the results page, verify the title contains the search term
        // Navigate to google.com
        driver.get("https://www.google.com/");
        // locate the element
        WebElement searchBox = driver.findElement(By.name("q"));
        // Eneter a search term
        String searchTerm = "chatGPT";
        searchBox.sendKeys(searchTerm, Keys.ENTER);

        // In the results page, verify the title contains the search term

        String currentTitle = driver.getTitle(); // returns the title of the page

        if(currentTitle.contains(searchTerm)) {
            System.out.println("TEST PASSED.");
        }else {
            System.out.println("TEST FAILED. The current title is " + currentTitle);

        }




    }
}
