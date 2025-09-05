package test;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import base.BaseTest;

import java.nio.file.Paths;
import java.time.Duration;

import static org.openqa.selenium.support.ui.ExpectedConditions.*;

public class ManageDeal {

    public static void main(String[] args) {
        WebDriver driver = null;
        try {
            // Setup driver and login (handled inside BaseTest)
            driver = BaseTest.createLoggedInDriver();
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

            // Navigate to Deals page
            navigateToDealsPage(driver, wait);

            // Create a new deal
            createNewDeal(driver, wait);

            // Delete the created deal
            deleteDeal(driver, wait);

        } catch (Exception e) {
            System.err.println("Test encountered an error:");
            e.printStackTrace();
        } finally {
            if (driver != null) {
                driver.quit();
                System.out.println("Driver closed.");
            }
        }
    }


     //Navigate to the Deals page

    public static void navigateToDealsPage(WebDriver driver, WebDriverWait wait) {
        System.out.println("Navigating to Deals page...");
        wait.until(elementToBeClickable(By.cssSelector("a[href$='/deal'] .menu-title"))).click();
        System.out.println("Current URL: " + driver.getCurrentUrl());
    }

    public static void createNewDeal(WebDriver driver, WebDriverWait wait) {
        System.out.println("Starting deal creation...");

        wait.until(elementToBeClickable(By.cssSelector(".lni.lni-plus.fs-3"))).click();
        System.out.println("Clicked 'Add Deal' button.");

        wait.until(elementToBeClickable(By.id("title"))).sendKeys("Test Deal");
        wait.until(elementToBeClickable(By.id("title_ur"))).sendKeys("Test Deal Urdu");
        wait.until(elementToBeClickable(By.id("description"))).sendKeys("This is a test deal.");
        wait.until(elementToBeClickable(By.id("description_ur"))).sendKeys("This is a test deal Urdu.");
        System.out.println("Entered deal titles and descriptions.");

        // Select Restaurant (select2 dropdown)
        WebElement dropdownTrigger = driver.findElement(By.cssSelector("span.select2-selection"));
        dropdownTrigger.click();
        WebElement firstOption = wait.until(elementToBeClickable(By.cssSelector("ul.select2-results__options li.select2-results__option")));
        firstOption.click();
        System.out.println("Selected first restaurant option from dropdown.");

        // Preparation Time
        Select prepTimeSelect = new Select(wait.until(visibilityOfElementLocated(By.id("preparation_range"))));
        prepTimeSelect.selectByIndex(1);
        System.out.println("Selected preparation time: " + prepTimeSelect.getFirstSelectedOption().getText());

        // Deal Validity - set via JavaScript due to date-time picker complexities
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("document.getElementById('valid_until').value = '2025-12-31T14:30';");
        System.out.println("Set deal validity date via JavaScript.");

        // Price + Discount
        wait.until(visibilityOfElementLocated(By.id("price"))).sendKeys("1201");
        Select discountTypeSelect = new Select(wait.until(elementToBeClickable(By.cssSelector("select[name='discount_type']"))));
        discountTypeSelect.selectByIndex(1);
        wait.until(visibilityOfElementLocated(By.id("discount"))).sendKeys("12");
        System.out.println("Entered price and discount details.");

        // Image Upload - scroll element into view before uploading
        WebElement uploadImage = driver.findElement(By.id("image"));
        String picturePath = Paths.get("C:", "Users", "Suvastu tech", "Pictures", "Camera Roll", "2.jpg").toString();
        js.executeScript("arguments[0].scrollIntoView(true);", uploadImage);
        uploadImage.sendKeys(picturePath);
        System.out.println("Uploaded image from path: " + picturePath);

        // Scroll down a bit to reveal more elements
        js.executeScript("window.scrollBy(0, 400);");

        // Deal Item
        wait.until(elementToBeClickable(By.id("deal_item"))).sendKeys("New Deal Item");
        System.out.println("Entered deal item.");

        // Submit form
        wait.until(elementToBeClickable(By.cssSelector("button[type='submit']"))).click();
        System.out.println("Clicked submit button. Deal creation complete.");
    }

    /**
     * Deletes the deal by searching and accepting the confirmation alert.
     */
    public static void deleteDeal(WebDriver driver, WebDriverWait wait) {
        System.out.println("Starting deal deletion...");

        WebElement searchBox = wait.until(visibilityOfElementLocated(By.cssSelector("input[type='search']")));
        searchBox.clear();
        searchBox.sendKeys("Test Deal");
        System.out.println("Searched for deal with name: 'Test Deal'");

        wait.until(elementToBeClickable(By.cssSelector("button[type='submit']"))).click();

        // Accept alert popup confirming deletion
        wait.until(alertIsPresent()).accept();
        System.out.println("Deal deleted after accepting confirmation alert.");
    }
}
