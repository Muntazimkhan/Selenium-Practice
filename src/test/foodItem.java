package test;

import io.github.bonigarcia.wdm.WebDriverManager;
import io.github.cdimascio.dotenv.Dotenv;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import test.loginhelper.LoginHelper;

import java.nio.file.Paths;

public class foodItem {
    public static void main(String[] args) {
        WebDriverManager.chromedriver().setup();
        ChromeDriver driver = new ChromeDriver();
        driver.manage().window().maximize();
        WebDriverWait wait = new WebDriverWait(driver, java.time.Duration.ofSeconds(10));
        JavascriptExecutor js = (JavascriptExecutor) driver;

        driver.get("https://panel.delmunch.com/login");
        System.out.println("Title: " + driver.getTitle());

        Dotenv dotenv = Dotenv.load();
        String email = dotenv.get("EMAIL");
        String password = dotenv.get("PASSWORD");
        LoginHelper.login(driver,email, password);

        // Navigate to Food Items page
        driver.findElement(By.cssSelector("li.mm-active")).click();
        driver.get("https://panel.delmunch.com/item");
        String currentUrl = driver.getCurrentUrl();
        System.out.println("Current URL: " + currentUrl);

        // Create a new Food Item
        driver.findElement(By.cssSelector(".lni.lni-plus.fs-3")).click();
        driver.findElement(By.id("name")).sendKeys("Test Food Item");
        driver.findElement(By.id("name_ur")).sendKeys("Test Food Item");
        driver.findElement(By.id("description")).sendKeys("This is a test food item.");
        driver.findElement(By.id("description_ur")).sendKeys("This is a test food item.");

        //Select Category
        WebElement categoryDropdown = driver.findElement(By.id("categorySelect"));
        Select selectCategory = new Select(categoryDropdown);
        selectCategory.selectByIndex(1);
        try{
            Thread.sleep(3000);
        }
        catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        //Select Restaurant
        // 1. Click the Select2 dropdown to activate it
        WebElement dropdownTrigger = driver.findElement(By.cssSelector("span.select2-selection"));
        dropdownTrigger.click();

        // 2. Click the first matching result
        WebElement firstOption = driver.findElement(By.cssSelector("ul.select2-results__options li.select2-results__option"));
        firstOption.click();

        // Upload Picture
        WebElement uploadInput = driver.findElement(By.id("image"));
        String picturePath = Paths.get("C:", "Users", "Suvastu tech", "Pictures", "Camera Roll", "2.jpg").toString();
        uploadInput.sendKeys(picturePath);
        System.out.println("Uploaded file: " + uploadInput.getAttribute("value"));

        js.executeScript("window.scrollBy(0, 1200);");
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
        js.executeScript("window.scrollBy(0, 3000);");
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        driver.findElement(By.id("is_popular")).click();
        driver.findElement(By.id("is_new")).click();
        driver.findElement(By.id("gluten")).click();
        driver.findElement(By.id("addon2")).click();
        driver.findElement(By.id("addon12")).click();
        driver.findElement(By.cssSelector("button[type='submit']")).click();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

            //Delete the created food item
        WebElement searchBox = driver.findElement(By.cssSelector("input[type='search']"));
        searchBox.sendKeys("Test Food Item");
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

        System.out.println("Food Item test completed successfully.");

        driver.quit();
    }
}
