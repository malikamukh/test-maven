import com.github.javafaker.Faker;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

public class ProjectOne {

    @Test
    public void test1() throws InterruptedException {
        //1. Navigate to http://duotify.us-east-2.elasticbeanstalk.com/register.php
        WebDriver driver = new ChromeDriver();
        driver.get("http://duotify.us-east-2.elasticbeanstalk.com/register.php");

        //2. Verify the the title is "Welcome to Duotify!"
        Thread.sleep(1000);
        String expectedT = "Welcome to Duotify!";
        String actualT = driver.getTitle();
        Assert.assertEquals(actualT, expectedT, "Title verification failed");

        //3. Click on Signup here
        Thread.sleep(2000);
        WebElement signUp = driver.findElement(By.id("hideLogin"));
        signUp.click();

        //4. Fill out the form with the required info using Faker class
        Faker faker = new Faker();
        Thread.sleep(1000);
        String userName = faker.name().username();
        WebElement usernameField = driver.findElement(By.id("username"));
        usernameField.sendKeys(userName);

        WebElement firstNaF = driver.findElement(By.id("firstName"));
        firstNaF.sendKeys(faker.name().firstName());

        WebElement lastNaF = driver.findElement(By.id("lastName"));
        lastNaF.sendKeys(faker.name().lastName());

        String email = faker.internet().emailAddress();
        //email
        driver.findElement(By.id("email")).sendKeys(email);
        //confermation
        driver.findElement(By.name("email2")).sendKeys(email);

        String passWord = faker.internet().password();
        //password
        driver.findElement(By.id("password")).sendKeys(passWord);
        //confermation
        driver.findElement(By.id("password2")).sendKeys(passWord);

        //5. Click on Sign up
        Thread.sleep(1000);
        WebElement registerButton =  driver.findElement(By.name("registerButton"));
        registerButton.click();

        //6. Once logged in to the application, verify that the URL is:
        //http://duotify.us-east-2.elasticbeanstalk.com/browse.php?
        String currentUrl = driver.getCurrentUrl();
        Assert.assertEquals(currentUrl, "http://duotify.us-east-2.elasticbeanstalk.com/browse.php?", "URL verification failed");

        //7. In the left navigation bar, verify that your first and last name is the same the first and last name that you used when signing up. (use getText() method to extract the text of the element)
        Thread.sleep(1000);
        driver.findElement(By.id("nameFirstAndLast")).getText();

        //8. Click on the first and last name on the left navigation bar and verify the first and last name on the main window is correct and then click logout.

        driver.findElement(By.id("nameFirstAndLast")).click();
        Thread.sleep(2000);
        WebElement profileNameElement = driver.findElement(By.xpath("/html[1]/body[1]/div[1]/div[1]/div[2]/div[1]/div[1]/div[1]/div[1]/h1[1]"));

        Thread.sleep(1000);
        WebElement logoutLink = driver.findElement(By.xpath("(//button[normalize-space()='LOGOUT'])[1]"));
        logoutLink.click();

        //9. Verify that you are logged out by verifying the URL is:
        //http://duotify.us-east-2.elasticbeanstalk.com/register.php
        Thread.sleep(1000);
        String currentUrl1 = driver.getCurrentUrl();
        Assert.assertEquals(currentUrl1, "http://duotify.us-east-2.elasticbeanstalk.com/register.php", "URL verification failed");

        //10
        Thread.sleep(1000);
        WebElement usernameLogin = driver.findElement(By.cssSelector("#loginUsername"));
        usernameLogin.sendKeys(userName);
        Thread.sleep(1000);
        WebElement password = driver.findElement(By.id("loginPassword"));
        password.sendKeys(passWord);
        Thread.sleep(1000);
        WebElement loginB = driver.findElement(By.name("loginButton"));
        loginB.click();

        //11
        Thread.sleep(1000);
        WebElement homeText = driver.findElement(By.xpath("//h1[normalize-space()='You Might Also Like']"));
        String homeTextContent = homeText.getText();
        if (homeTextContent.equals("You Might Also Like")) {
            System.out.println("Login verification passed.");
        } else {
            System.out.println("Login verification failed.");
        }

        //12
        Thread.sleep(1000);
        driver.findElement(By.id("nameFirstAndLast")).click();
        Thread.sleep(1000);
        WebElement logoutLink1 = driver.findElement(By.xpath("(//button[normalize-space()='LOGOUT'])[1]"));
        logoutLink1.click();
        Thread.sleep(1000);
        String expectedUrl = "http://duotify.us-east-2.elasticbeanstalk.com/register.php";
        String actualUrl = driver.getCurrentUrl();
        Assert.assertEquals(actualUrl,expectedUrl,"Logout verification failed.");
        driver.close();
    }
}


