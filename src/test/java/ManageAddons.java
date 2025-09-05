package test;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import base.BaseTest;

import java.time.Duration;

import static org.openqa.selenium.support.ui.ExpectedConditions.*;

public class ManageAddons {

    public static void main(String[] args) {
        WebDriver driver = null;
        try {
            // Setup driver and login (handled inside BaseTest)
            driver = BaseTest.createLoggedInDriver();
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

            // Navigate to Addons management page
            navigateToAddonsPage(driver, wait);

            // Create a new Addon
            createAddon(driver, wait, "Test Addon", "Test Addon Urdu", "48");

            // Reload addons list to reflect changes
            navigateToAddonsPage(driver, wait);

            // Delete the created Addon
            deleteAddon(driver, wait, "Test Addon");

        } catch (Exception e) {
            System.err.println("Error encountered:");
            e.printStackTrace();
        } finally {
            if (driver != null) {
                driver.quit();
                System.out.println("Driver closed.");
            }
        }
    }

    /**
     * Navigate to the Manage Addons page by clicking its menu item.
     */
    public static void navigateToAddonsPage(WebDriver driver, WebDriverWait wait) {
        System.out.println("Navigating to Manage Addons page...");
        wait.until(elementToBeClickable(By.xpath("//div[normalize-space()='Manage Addons']"))).click();
        System.out.println("Current URL: " + driver.getCurrentUrl());
    }

    /**
     * Creates a new Addon with the specified details.
     */
    public static void createAddon(WebDriver driver, WebDriverWait wait, String name, String nameUrdu, String price) {
        System.out.println("Starting Addon creation...");
        wait.until(elementToBeClickable(By.cssSelector(".lni.lni-plus.fs-3"))).click();

        wait.until(elementToBeClickable(By.id("name"))).sendKeys(name);
        wait.until(elementToBeClickable(By.id("name_ur"))).sendKeys(nameUrdu);
        wait.until(elementToBeClickable(By.id("price"))).sendKeys(price);
        System.out.println("Entered Addon name, Urdu name, and price.");

        // Select restaurant from the dropdown
        WebElement dropdownTrigger = driver.findElement(By.cssSelector("input[placeholder='Select a restaurant']"));
        dropdownTrigger.click();

        WebElement firstOption = wait.until(elementToBeClickable(
                By.cssSelector("ul.select2-results__options li.select2-results__option")));
        String selectedRestaurant = firstOption.getText();
        firstOption.click();
        System.out.println("Selected restaurant: " + selectedRestaurant);

        // Submit the addon form
        wait.until(visibilityOfElementLocated(By.cssSelector("button[type='submit']"))).click();
        System.out.println("Addon created successfully.");
    }

    /**
     * Deletes an Addon by searching its name and accepting confirmation alert.
     */
    public static void deleteAddon(WebDriver driver, WebDriverWait wait, String addonName) {
        System.out.println("Starting Addon deletion for: " + addonName);

        WebElement searchBox = wait.until(visibilityOfElementLocated(By.cssSelector("input[type='search']")));
        searchBox.clear();
        searchBox.sendKeys(addonName);
        System.out.println("Searching for Addon: " + addonName);

        // Assuming delete button available after search
        wait.until(elementToBeClickable(By.cssSelector("button[type='submit']"))).click();

        // Accept the confirmation alert
        wait.until(alertIsPresent()).accept();
        System.out.println("Addon deleted successfully.");
    }
}
