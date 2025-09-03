package test;

import test.base.BaseTest;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.nio.file.Paths;
import java.time.Duration;
import java.util.Random;

public class manageStaff {
    public static void main(String[] args) {
        WebDriver driver = null;
        try {
            // Login + driver setup handled in BaseTest
            driver = BaseTest.createLoggedInDriver();
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            JavascriptExecutor js = (JavascriptExecutor) driver;

            // Navigate to Staff page
            wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("li.mm-active"))).click();
            driver.get("https://panel.delmunch.com/staff");
            System.out.println("Current URL: " + driver.getCurrentUrl());

            // Create a new Staff member
            WebElement addButton = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".lni.lni-plus.fs-3")));
            js.executeScript("arguments[0].scrollIntoView({block: 'center'});", addButton);
            Thread.sleep(300);
            try {
                addButton.click();
            } catch (ElementClickInterceptedException e) {
                js.executeScript("arguments[0].click();", addButton);
            }

            driver.findElement(By.id("name")).sendKeys("Test Staff");
            String uniqueEmail = "teststaff" + System.currentTimeMillis() + "@test.com";
            driver.findElement(By.id("email")).sendKeys(uniqueEmail);

            // Unique phone number (Pakistani format starting with 92)
            String randomPhone = "92" + (300000000 + new Random().nextInt(699999999));
            driver.findElement(By.id("phone")).sendKeys(randomPhone);
            System.out.println("Generated phone: " + randomPhone);

            // Upload Picture
            WebElement uploadInput = driver.findElement(By.id("profile_pic"));
            String picturePath = Paths.get("C:", "Users", "Suvastu tech", "Pictures", "Camera Roll", "2.jpg").toString();
            uploadInput.sendKeys(picturePath);
            System.out.println("Uploaded file: " + uploadInput.getAttribute("value"));

            driver.findElement(By.id("password")).sendKeys("Test@1234");
            driver.findElement(By.id("password_confirmation")).sendKeys("Test@1234");

            // Select Restaurant from dropdown
            WebElement restaurantDropdown = driver.findElement(By.cssSelector("select[name='restaurant_id']"));
            Select restaurantSelect = new Select(restaurantDropdown);
            restaurantSelect.selectByIndex(2);
            System.out.println("Selected restaurant: " + restaurantSelect.getFirstSelectedOption().getText());

            // Select Role from dropdown
            WebElement roleDropdown = driver.findElement(By.id("internal_role_id"));
            Select roleSelect = new Select(roleDropdown);
            roleSelect.selectByIndex(1);
            System.out.println("Selected role: " + roleSelect.getFirstSelectedOption().getText());

            // Submit the form
            WebElement submitButton = driver.findElement(By.cssSelector("button.btn.btn-primary.px-4"));
            js.executeScript("arguments[0].scrollIntoView({block: 'center'});", submitButton);
            Thread.sleep(300);
            try {
                submitButton.click();
            } catch (ElementClickInterceptedException e) {
                js.executeScript("arguments[0].click();", submitButton);
            }

            Thread.sleep(2000);

            // Search for the created staff member
            WebElement searchBox = driver.findElement(By.cssSelector("input[type='search']"));
            searchBox.clear();
            searchBox.sendKeys("teststaff");
            Thread.sleep(2000);

            // Click delete button safely (assuming there's a submit button for delete)
            WebElement deleteButton = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("button[type='submit']")));
            js.executeScript("arguments[0].scrollIntoView({block: 'center'});", deleteButton);
            Thread.sleep(300);
            try {
                deleteButton.click();
            } catch (ElementClickInterceptedException e) {
                js.executeScript("arguments[0].click();", deleteButton);
            }

            wait.until(ExpectedConditions.alertIsPresent());
            driver.switchTo().alert().accept();

            Thread.sleep(2000);
            System.out.println("Manage Staff test completed successfully.");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            BaseTest.quit(driver);
        }
    }
}
