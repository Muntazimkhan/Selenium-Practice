package test;

import io.github.bonigarcia.wdm.WebDriverManager;
import io.github.cdimascio.dotenv.Dotenv;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import test.loginhelper.LoginHelper;
import org.openqa.selenium.support.ui.Select;
import java.time.Duration;
import java.util.Random;

public class manageUsers {
    public static void main(String[] args) {
        WebDriverManager.chromedriver().setup();
        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();

        driver.get("https://panel.delmunch.com/login");
        System.out.println("Title: " + driver.getTitle());

        Dotenv dotenv = Dotenv.load();
        String email = dotenv.get("EMAIL");
        String password = dotenv.get("PASSWORD");
        LoginHelper.login(driver,email, password);

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        driver.findElement(By.cssSelector("li.mm-active")).click();
        driver.get("https://panel.delmunch.com/users");

        String currentUrl = driver.getCurrentUrl();

        if (currentUrl.contains("/users")) {
            System.out.println("✓ PASS: URL contains '/users'");
        } else {
            System.out.println("✗ FAIL: URL does not contain '/users'");
        }
        System.out.println("Current URL: " + currentUrl);

        //Create a new User
        driver.findElement(By.cssSelector(".lni.lni-plus.fs-3")).click();
        String uniqueEmail = "user" + System.currentTimeMillis() + "@test.com";
        String uniqueName = "TestUser" + System.currentTimeMillis();

        driver.findElement(By.id("name")).sendKeys(uniqueName);
        driver.findElement(By.id("email")).sendKeys(uniqueEmail);

        //Random phone number generation
        Random rand = new Random();
        String randomPhone = "9" + (100000000 + rand.nextInt(900000000));
        driver.findElement(By.id("phone")).sendKeys(randomPhone);

        //password and role
        driver.findElement(By.id("inputChoosePassword")).sendKeys("Test@1234");
        driver.findElement(By.id("confirm-password")).sendKeys("Test@1234");
        WebElement roleDropdown = driver.findElement(By.name("role"));
        Select selectRole = new Select(roleDropdown);
        selectRole.selectByVisibleText("Admin");
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        driver.findElement(By.cssSelector("button[type='submit']")).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".toast-message")));

        //Delete the created user
        WebElement searchBox = driver.findElement(By.cssSelector("input[type='search']"));
        searchBox.sendKeys(uniqueEmail);
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        driver.findElement(By.cssSelector("button[type='submit']")).click();
        Alert alert = driver.switchTo().alert();
        alert.accept();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        driver.quit();

    }
}
