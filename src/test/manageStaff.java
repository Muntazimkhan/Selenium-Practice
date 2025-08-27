package test;

import io.github.bonigarcia.wdm.WebDriverManager;
import io.github.cdimascio.dotenv.Dotenv;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import test.loginhelper.LoginHelper;

import java.nio.file.Paths;
import java.time.Duration;

public class manageStaff {
    public static void main(String[] args) {
        WebDriverManager.chromedriver().setup();
        ChromeDriver driver = new ChromeDriver();
        driver.manage().window().maximize();
        JavascriptExecutor js = (JavascriptExecutor) driver;


        driver.get("https://panel.delmunch.com/login");
        System.out.println("Title: " + driver.getTitle());

        Dotenv dotenv = Dotenv.load();
        String email = dotenv.get("EMAIL");
        String password = dotenv.get("PASSWORD");
        LoginHelper.login(driver, email, password);

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        // Navigate to Staff page
        wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(By.cssSelector("li.mm-active"))));
        driver.findElement(By.cssSelector("li.mm-active")).click();
        driver.get("https://panel.delmunch.com/staff");
        String currentUrl = driver.getCurrentUrl();
        System.out.println("Current URL: " + currentUrl);

        // Create a new Staff member
        wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(By.cssSelector(".lni.lni-plus.fs-3"))));
        driver.findElement(By.cssSelector(".lni.lni-plus.fs-3")).click();
        driver.findElement(By.id("name")).sendKeys("Test Staff");
        driver.findElement(By.id("email")).sendKeys("teststaff" + System.currentTimeMillis() + "@test.com");
        driver.findElement(By.id("phone")).sendKeys("923001234567");

        // Upload Picture
        WebElement uploadInput = driver.findElement(By.id("profile_pic"));
        String picturePath = Paths.get("C:", "Users", "Suvastu tech", "Pictures", "Camera Roll", "2.jpg").toString();
        uploadInput.sendKeys(picturePath);
        System.out.println("Uploaded file: " + uploadInput.getAttribute("value"));

        driver.findElement(By.id("password")).sendKeys("Test@1234");
        driver.findElement(By.id("password_confirmation")).sendKeys("Test@1234");

        // Select Restaurant from dropdown
        WebElement Restaurantdropdown = driver.findElement(By.cssSelector("select[name='restaurant_id']"));
        Select restaurantdropdown = new Select(Restaurantdropdown);
        restaurantdropdown.selectByIndex(2);
        System.out.println("Selected restaurant: " + restaurantdropdown.getFirstSelectedOption().getText());

        // Select Role from dropdown
        WebElement Roledropdown = driver.findElement(By.id("internal_role_id"));
        Select roledropdown = new Select(Roledropdown);
        roledropdown.selectByIndex(1);
        System.out.println("Selected role: " + roledropdown.getFirstSelectedOption().getText());

        // Submit the form
        driver.findElement(By.cssSelector("button[class='btn btn-primary px-4']")).click();

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //Delete the created staff member
        WebElement searchBox = driver.findElement(By.cssSelector("input[type='search']"));
        searchBox.sendKeys("teststaff");
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {

            e.printStackTrace();
        }
        driver.findElement(By.cssSelector("button[type='submit']")).click();

        wait.until(ExpectedConditions.alertIsPresent());
        driver.switchTo().alert().accept();

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        driver.quit();

    }
}
