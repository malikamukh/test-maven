import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Test;
public class BasicMethodsSelenium {

    public class BasicMethods {

        @Test
        public void basics(){
            WebDriver driver = new ChromeDriver();
            driver.get("https://www.tesla.com/");
            String currentUrl = driver.getCurrentUrl();
            Assert.assertEquals(currentUrl, "https://www.tesla.com/)");

            String pageSource = driver.getPageSource();
            System.out.println(pageSource);
            String text = "Until March 2023, new Model 3 and Model Y vehicles qualify for a $7,500 federal tax credit for eligible buyers";
        }
    }
}
