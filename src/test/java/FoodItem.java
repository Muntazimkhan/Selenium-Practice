package java;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import base.BaseTest;

import java.nio.file.Paths;
import java.time.Duration;

public class FoodItem {
    public static void main(String[] args) {
        WebDriver driver = null;

        try {
            driver = BaseTest.createLoggedInDriver();
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            JavascriptExecutor js = (JavascriptExecutor) driver;

            navigateToFoodItemsPage(driver, wait);
            createFoodItem(driver, wait, js);
            deleteFoodItem(driver, wait);

            System.out.println(" Food Item test completed successfully.");

        } catch (Exception e) {
            System.err.println(" Error during test execution:");
            e.printStackTrace();
        } finally {
            BaseTest.quit(driver);
        }
    }

    public static void navigateToFoodItemsPage(WebDriver driver, WebDriverWait wait) {
        System.out.println("Navigating to Food Items page...");
        driver.findElement(By.cssSelector("li.mm-active")).click();
        driver.get("https://panel.delmunch.com/item");
        System.out.println("Current URL: " + driver.getCurrentUrl());
    }

    public static void createFoodItem(WebDriver driver, WebDriverWait wait, JavascriptExecutor js) {
        System.out.println("Creating a new food item...");

        driver.findElement(By.cssSelector(".lni.lni-plus.fs-3")).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("name"))).sendKeys("Test Food Item");
        driver.findElement(By.id("name_ur")).sendKeys("Test Food Item");
        driver.findElement(By.id("description")).sendKeys("This is a test food item.");
        driver.findElement(By.id("description_ur")).sendKeys("This is a test food item.");

        // Select category
        Select category = new Select(driver.findElement(By.id("categorySelect")));
        category.selectByIndex(1);

        waitFor(2);

        // Select restaurant (Select2)
        WebElement dropdownTrigger = driver.findElement(By.cssSelector("span.select2-selection"));
        dropdownTrigger.click();
        WebElement firstOption = driver.findElement(By.cssSelector("ul.select2-results__options li.select2-results__option"));
        firstOption.click();

        // Upload image
        WebElement uploadInput = driver.findElement(By.id("image"));
        String picturePath = Paths.get("C:", "Users", "Suvastu tech", "Pictures", "Camera Roll", "2.jpg").toString();
        uploadInput.sendKeys(picturePath);
        System.out.println("Uploaded file: " + uploadInput.getAttribute("value"));

        // Set availability
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("button[aria-controls='collapseAvailability']"))).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("from_Sunday")))
                .sendKeys(Keys.chord(Keys.CONTROL, "a"), "01:00PM");

        driver.findElement(By.id("to_Sunday"))
                .sendKeys(Keys.chord(Keys.CONTROL, "a"), "11:59PM");

        driver.findElement(By.id("applyToAll")).click();

        // Scroll to reveal flags
        js.executeScript("window.scrollBy(0, 2500);");
        waitFor(2);

        // Flags
        driver.findElement(By.id("is_popular")).click();
        driver.findElement(By.id("is_new")).click();
        driver.findElement(By.id("gluten")).click();
        driver.findElement(By.id("addon2")).click();
        driver.findElement(By.id("addon12")).click();

        // Submit
        driver.findElement(By.cssSelector("button[type='submit']")).click();
        waitFor(2);

        System.out.println("Food item created.");
    }

    public static void deleteFoodItem(WebDriver driver, WebDriverWait wait) {
        System.out.println("Deleting the test food item...");

        WebElement searchBox = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("input[type='search']")));
        searchBox.sendKeys("Test Food Item");

        WebElement deleteButton = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("button[type='submit']")));
        deleteButton.click();

        wait.until(ExpectedConditions.alertIsPresent()).accept();

        waitFor(2);
        System.out.println("🗑️ Test food item deleted.");
    }

    private static void waitFor(int seconds) {
        try {
            Thread.sleep(seconds * 1000L);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
