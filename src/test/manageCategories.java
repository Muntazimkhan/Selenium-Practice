package test;

import io.github.bonigarcia.wdm.WebDriverManager;
import io.github.cdimascio.dotenv.Dotenv;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import test.loginhelper.LoginHelper;

import java.time.Duration;

public class manageCategories {
    public static void main(String [] args) {
        WebDriverManager.chromedriver().setup();
        ChromeDriver driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://panel.delmunch.com/login");
        System.out.println("Title: " + driver.getTitle());

        Dotenv dotenv = Dotenv.load();
        String email = dotenv.get("EMAIL");
        String password = dotenv.get("PASSWORD");
        LoginHelper.login(driver,email, password);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        // Navigate to Categories page
        driver.findElement(By.cssSelector("li.mm-active")).click();
        driver.get("https://panel.delmunch.com/category");
        String currentUrl = driver.getCurrentUrl();
        System.out.println("Current URL: " + currentUrl);

        // Create a new Category
        driver.findElement(By.cssSelector(".lni.lni-plus.fs-3")).click();
        driver.findElement(By.cssSelector("input[placeholder='Name']")).sendKeys("Test Category");
        driver.findElement(By.cssSelector("input[placeholder='Name in urdu']")).sendKeys("Test Category");
        driver.findElement(By.cssSelector("textarea[placeholder='Description']")).sendKeys("This is a test category.");
        driver.findElement(By.cssSelector("textarea[placeholder='Description in urdu']")).sendKeys("This is a test category.");

        // 1. Click the Select2 dropdown to activate it
        WebElement dropdownTrigger = driver.findElement(By.cssSelector("span.select2-selection"));
        dropdownTrigger.click();

        // 2. Click the first matching result
        WebElement firstOption = driver.findElement(By.cssSelector("ul.select2-results__options li.select2-results__option"));
        firstOption.click();


        WebElement UploadImage = driver.findElement(By.cssSelector("input[placeholder='Image']"));
        String picturePath = "C:\\Users\\Suvastu tech\\Pictures\\Camera Roll\\2.jpg";
        UploadImage.sendKeys(picturePath);
        System.out.println("Uploaded file: " + UploadImage.getAttribute("value"));


        try{
            Thread.sleep(3000);
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }

        driver.findElement(By.cssSelector("button[type='submit']")).click();

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //Delete the created category
        WebElement searchBox = driver.findElement(By.cssSelector("input[type='search']"));
        searchBox.sendKeys("Test Category");
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
// Click the red "Delete" button from the category table
        driver.findElement(By.cssSelector(".btn.btn-sm.btn-outline-danger.px-2")).click();

// Wait for the modal's Delete button to appear
        WebElement deleteBtnInModal = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//div[contains(@class, 'modal')]//button[contains(@class, 'btn-danger') and normalize-space()='Delete']")));

// Click it
        deleteBtnInModal.click();


        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        driver.quit();

    }
}
