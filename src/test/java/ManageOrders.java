package test;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import base.BaseTest;

import static org.openqa.selenium.support.ui.ExpectedConditions.elementToBeClickable;

public class ManageOrders {
    public static void main(String[] args) {
        WebDriver driver = null;
        try {
            // Setup driver and login (handled inside BaseTest)
            driver = BaseTest.createLoggedInDriver();
            WebDriverWait wait = new WebDriverWait(driver, java.time.Duration.ofSeconds(10));

            // Navigate to Orders page
            navigateToOrdersPage(driver, wait);

            // Filter Order
            filterOrderDetails(driver, wait);

            // View Order Details
            viewOrderDetails(driver, wait);

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

    // Navigate to the Orders page by clicking its menu item.
    public static void navigateToOrdersPage(WebDriver driver, WebDriverWait wait) {
        System.out.println("Navigating to Orders page...");
        wait.until(elementToBeClickable(By.xpath("//div[normalize-space()='Manage orders']"))).click();
        System.out.println("Current URL: " + driver.getCurrentUrl());
    }

    //Filter order
    public static void filterOrderDetails(WebDriver driver, WebDriverWait wait) {
        System.out.println("Filtering orders by 'Pending' status...");

        // Wait for the dropdown to be visible/clickable
        WebElement statusDropdown = wait.until(ExpectedConditions.elementToBeClickable(By.id("status")));

        // Select 'Pending' from the dropdown
        Select select = new Select(statusDropdown);
        select.selectByVisibleText("Pending");

        // Optional: wait for the page to reload (can check URL or wait for some element)
        wait.until(ExpectedConditions.urlContains("status=pending"));

        System.out.println("Filtered orders by 'Pending' status.");
    }

    //View Detail of order
    public static void viewOrderDetails(WebDriver driver, WebDriverWait wait) {
        System.out.println("Viewing details of the first order...");

        // Wait for the first order's 'View' button to be clickable
        WebElement viewButton = wait.until(elementToBeClickable(By.cssSelector(".btn.btn-sm.btn-outline-success.px-2")));

        // Click the 'View' button
        viewButton.click();

        // Optional: wait for the order details page to load (can check URL or wait for some element)
        wait.until(ExpectedConditions.urlMatches(".*/order/show/\\d+"));

        System.out.println("Viewing order details.");
    }
}
