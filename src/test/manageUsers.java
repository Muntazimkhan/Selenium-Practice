package test;

import test.base.BaseTest;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.Random;

public class manageUsers {
    public static void main(String[] args) {
        WebDriver driver = null;
        try {
            // Login + driver setup handled in BaseTest
            driver = BaseTest.createLoggedInDriver();
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            JavascriptExecutor js = (JavascriptExecutor) driver;

            // Navigate to Users page
            WebElement menuActive = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("li.mm-active")));
            menuActive.click();
            driver.get("https://panel.delmunch.com/users");

            String currentUrl = driver.getCurrentUrl();
            if (currentUrl.contains("/users")) {
                System.out.println("✓ PASS: URL contains '/users'");
            } else {
                System.out.println("✗ FAIL: URL does not contain '/users'");
            }
            System.out.println("Current URL: " + currentUrl);

            // Create a new User
            WebElement addUserBtn = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".lni.lni-plus.fs-3")));
            js.executeScript("arguments[0].scrollIntoView({block: 'center'});", addUserBtn);
            Thread.sleep(300);
            try {
                addUserBtn.click();
            } catch (ElementClickInterceptedException e) {
                js.executeScript("arguments[0].click();", addUserBtn);
            }

            String uniqueEmail = "user" + System.currentTimeMillis() + "@test.com";
            String uniqueName = "TestUser" + System.currentTimeMillis();

            driver.findElement(By.id("name")).sendKeys(uniqueName);
            driver.findElement(By.id("email")).sendKeys(uniqueEmail);

            // Random phone number generation (starts with 9 + 9 digit number)
            Random rand = new Random();
            String randomPhone = "9" + (100000000 + rand.nextInt(900000000));
            driver.findElement(By.id("phone")).sendKeys(randomPhone);

            driver.findElement(By.id("inputChoosePassword")).sendKeys("Test@1234");
            driver.findElement(By.id("confirm-password")).sendKeys("Test@1234");

            WebElement roleDropdown = driver.findElement(By.name("role"));
            Select selectRole = new Select(roleDropdown);
            selectRole.selectByVisibleText("Admin");

            Thread.sleep(2000);

            WebElement submitBtn = driver.findElement(By.cssSelector("button[type='submit']"));
            js.executeScript("arguments[0].scrollIntoView({block: 'center'});", submitBtn);
            Thread.sleep(300);
            try {
                submitBtn.click();
            } catch (ElementClickInterceptedException e) {
                js.executeScript("arguments[0].click();", submitBtn);
            }

            wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".toast-message")));

            // Search and delete the created user
            WebElement searchBox = driver.findElement(By.cssSelector("input[type='search']"));
            searchBox.clear();
            searchBox.sendKeys(uniqueEmail);
            Thread.sleep(2000);

            WebElement deleteBtn = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("button[type='submit']")));
            js.executeScript("arguments[0].scrollIntoView({block: 'center'});", deleteBtn);
            Thread.sleep(300);
            try {
                deleteBtn.click();
            } catch (ElementClickInterceptedException e) {
                js.executeScript("arguments[0].click();", deleteBtn);
            }

            wait.until(ExpectedConditions.alertIsPresent());
            Alert alert = driver.switchTo().alert();
            alert.accept();

            Thread.sleep(2000);
            System.out.println("Manage Users test completed successfully.");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            BaseTest.quit(driver);
        }
    }
}
