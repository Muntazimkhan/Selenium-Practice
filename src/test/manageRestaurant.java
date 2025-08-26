package test;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.*;
import io.github.cdimascio.dotenv.Dotenv;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import test.loginhelper.LoginHelper;

import java.nio.file.Paths;
import java.time.Duration;

public class manageRestaurant {
    public static void main(String[] args) {
        WebDriverManager.chromedriver().setup();
        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();
        JavascriptExecutor js = (JavascriptExecutor) driver;

        driver.get("https://panel.delmunch.com/login");
        System.out.println("Title: " + driver.getTitle());

        Dotenv dotenv = Dotenv.load();
        String email = dotenv.get("EMAIL");
        String password = dotenv.get("PASSWORD");
        LoginHelper.login(driver, email, password);

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        // Navigate to Restaurants page
        driver.findElement(By.cssSelector("li.mm-active")).click();
        driver.get("https://panel.delmunch.com/restaurant");
        String currentUrl = driver.getCurrentUrl();

        if (currentUrl.contains("/restaurant")) {
            System.out.println("✓ PASS: URL contains '/restaurant'");
        } else {
            System.out.println("✗ FAIL: URL does not contain '/restaurant'");
        }
        System.out.println("Current URL: " + currentUrl);

        //Create a new Restaurant
        driver.findElement(By.cssSelector(".lni.lni-plus.fs-3.me-2.mt-1")).click();
        driver.findElement(By.id("name")).sendKeys("Test Restaurant");
        driver.findElement(By.id("name_ur")).sendKeys("Test Restaurant");
        driver.findElement(By.id("address")).sendKeys("123 Test St, Test City");
        driver.findElement(By.id("address_ur")).sendKeys("123 Test St, Test City");

        driver.findElement(By.id("description")).sendKeys("123 Test St, Test City");
        driver.findElement(By.id("description_ur")).sendKeys("123 Test St, Test City");

        Select emailDropdown = new Select(driver.findElement(By.id("email")));
        emailDropdown.selectByIndex(1);

        driver.findElement(By.id("phone")).sendKeys("923001234567");
        driver.findElement(By.id("latitude")).sendKeys("32.258");
        driver.findElement(By.id("longitude")).sendKeys("74.188");

        // Upload Picture
        WebElement uploadInput = driver.findElement(By.id("image"));
        String picturePath = Paths.get("C:", "Users", "Suvastu tech", "Pictures", "Camera Roll", "2.jpg").toString();
        uploadInput.sendKeys(picturePath);
        System.out.println("Uploaded file: " + uploadInput.getAttribute("value"));

        // Upload Logo
        WebElement uploadLogo = driver.findElement(By.id("logo"));
        String logoPath = Paths.get("C:", "Users", "Suvastu tech", "Pictures", "Camera Roll", "download.jpg").toString();
        uploadLogo.sendKeys(logoPath);
        System.out.println("Uploaded file: " + uploadLogo.getAttribute("value"));

        wait.until(ExpectedConditions.elementToBeClickable(By.id("delivery_enabled"))).click();
        driver.findElement(By.id("allow_decimal_prices")).click();
        WebElement deliveryCharges = wait.until(ExpectedConditions.elementToBeClickable(By.id("delivery_charges_per_km")));
        deliveryCharges.sendKeys("10");
        System.out.println("deliveryCharges: " + deliveryCharges.getAttribute("value"));
        js.executeScript("window.scrollBy(0, 400);");
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        wait.until(ExpectedConditions.elementToBeClickable(By.id("free_delivery"))).click();
        WebElement freeDelivery = wait.until(ExpectedConditions.elementToBeClickable(By.id("km_under_free_delivery")));
        freeDelivery.sendKeys("5");
        System.out.println("freeDelivery: " + freeDelivery.getAttribute("value"));

        js.executeScript("window.scrollBy(0, 2200);");
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //Set availability time
        By setAvailabilityButton = By.cssSelector("button[aria-controls='collapseAvailability']");
        wait.until(ExpectedConditions.elementToBeClickable(setAvailabilityButton)).click();

        WebElement fromSunday = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("from_Sunday")));
        fromSunday.click();
        fromSunday.sendKeys(Keys.chord(Keys.CONTROL, "a"), "01:00PM");

        WebElement toSunday = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("to_Sunday")));
        toSunday.click();
        toSunday.sendKeys(Keys.chord(Keys.CONTROL, "a"), "11:59PM");

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("applyToAll"))).click();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // About Section
        js.executeScript("window.scrollBy(0, 500);");

        driver.findElement(By.id("heading1")).sendKeys("About Us_1");
        driver.findElement(By.id("heading1_ur")).sendKeys("About Us_1+ur");
        driver.findElement(By.id("paragraph1")).sendKeys("Detail about us_1");
        driver.findElement(By.id("paragraph1_ur")).sendKeys("Detail about us_1_ur");

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        js.executeScript("window.scrollBy(0, 500);");
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        driver.findElement(By.xpath("//button[normalize-space()='Submit']")).click();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //Delete the created Restaurant
        WebElement searchBox = driver.findElement(By.cssSelector("input[type='search']"));
        searchBox.sendKeys("Test Restaurant");
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("button[type='submit']"))).click();
        Alert alert = driver.switchTo().alert();
        alert.accept();

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Test Completed");

        driver.quit();
    }
}
