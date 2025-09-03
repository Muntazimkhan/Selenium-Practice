package test;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import test.base.BaseTest;

import java.time.Duration;

import static org.openqa.selenium.support.ui.ExpectedConditions.alertIsPresent;

public class foodVarieties {
    public static void main(String[] args) {
        WebDriver driver = null;

        try {
            driver = BaseTest.createLoggedInDriver();
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

            navigateToFoodVarietiesPage(driver);
            createFoodVariety(driver, wait);
            deleteFoodVariety(driver, wait);

            System.out.println(" Food Variety test completed successfully.");

        } catch (Exception e) {
            System.err.println(" Error during test:");
            e.printStackTrace();
        } finally {
            if (driver != null) {
                driver.quit();
                System.out.println("Driver closed.");
            }
        }
    }

    public static void navigateToFoodVarietiesPage(WebDriver driver) {
        System.out.println("Navigating to Food Varieties page...");
        driver.findElement(By.cssSelector("li.mm-active")).click();
        driver.get("https://panel.delmunch.com/fooditem-variety");
        System.out.println("Current URL: " + driver.getCurrentUrl());
    }

    public static void createFoodVariety(WebDriver driver, WebDriverWait wait) {
        System.out.println("Creating a new Food Variety...");

        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".lni.lni-plus.fs-3"))).click();

        wait.until(ExpectedConditions.elementToBeClickable(By.id("name"))).sendKeys("Test Variety");
        wait.until(ExpectedConditions.elementToBeClickable(By.id("name_ur"))).sendKeys("Test Variety Urdu");
        wait.until(ExpectedConditions.elementToBeClickable(By.id("description"))).sendKeys("Test Description");
        wait.until(ExpectedConditions.elementToBeClickable(By.id("description_ur"))).sendKeys("Test Description Urdu");
        wait.until(ExpectedConditions.elementToBeClickable(By.id("price"))).sendKeys("105");
        wait.until(ExpectedConditions.elementToBeClickable(By.id("discount"))).sendKeys("10");

        // Select Food Item
        Select foodItemSelect = new Select(wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("select[name='food_item_id']"))));
        foodItemSelect.selectByIndex(1);
        System.out.println("Selected food item: " + foodItemSelect.getFirstSelectedOption().getText());

        // Select Discount Type
        Select discountTypeSelect = new Select(wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("select[name='discount_type']"))));
        discountTypeSelect.selectByIndex(1);
        System.out.println("Selected discount type: " + discountTypeSelect.getFirstSelectedOption().getText());

        // Select Preparation Time
        Select prepTimeSelect = new Select(wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("preparation_range"))));
        prepTimeSelect.selectByIndex(1);
        System.out.println("Selected preparation time: " + prepTimeSelect.getFirstSelectedOption().getText());

        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("button[type='submit']"))).click();

        // Verify creation
        WebElement createdVariety = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//td[contains(text(), 'Test Variety')]")
        ));
        assert createdVariety.isDisplayed() : "Food Variety not created";
        System.out.println(" Food Variety created.");
    }

    public static void deleteFoodVariety(WebDriver driver, WebDriverWait wait) {
        System.out.println("Deleting the created Food Variety...");

        WebElement searchBox = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("input[type='search']")));
        searchBox.clear();
        searchBox.sendKeys("Test Variety");

        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("button[type='submit']"))).click();
        wait.until(alertIsPresent()).accept();

        // Verify deletion
        boolean isDeleted = driver.findElements(By.xpath("//td[contains(text(),'Test Variety')]")).isEmpty();
        assert isDeleted : " Food Variety was not deleted!";
        System.out.println(" Food Variety deleted.");
    }
}
