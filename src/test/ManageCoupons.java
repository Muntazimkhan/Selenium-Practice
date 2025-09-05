package test;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import test.base.BaseTest;

public class ManageCoupons {
    public static void main(String []args){
        WebDriver driver = null;
        try {
            // Setup driver and login (handled inside BaseTest)
            driver = BaseTest.createLoggedInDriver();
            WebDriverWait wait = new WebDriverWait(driver, java.time.Duration.ofSeconds(10));
            System.out.println("Manage Coupons Test");

            // Navigate to Coupons page
            navigateToCouponsPage(wait, driver);
            // Add New Coupon
            addNewCoupon(wait, driver);
            // Delete Coupon
            DeleteCoupon(wait, driver);


        } catch (Exception e) {
            System.err.println("Error encountered:");
            e.printStackTrace();
        }finally {
            if (driver != null) {
                driver.quit();
                System.out.println("Driver closed.");
            }
        }
    }


    // Navigate to Coupons Page
    public static void navigateToCouponsPage(WebDriverWait wait, WebDriver driver){
    wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[normalize-space()='Manage Coupons']"))).click();
    System.out.println("Current URL: " + driver.getCurrentUrl());

    }

    // Add New Coupon
    public static void addNewCoupon(WebDriverWait wait, WebDriver driver){

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".lni.lni-plus.fs-3"))).click();
        System.out.println("Add New Coupon clicked");

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("input[name='code']"))).sendKeys("ABCD1234");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("input[name='discount_amount']"))).sendKeys("11");


        //Add Validity Date From
        WebElement validityDateInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("input[name='valid_from']")));

        String datetimeValue = "2025-09-04T12:00";

        ((JavascriptExecutor) driver).executeScript("arguments[0].value = arguments[1];", validityDateInput, datetimeValue);

        System.out.println("Date set via JavaScript.");

        String valueSet = validityDateInput.getAttribute("value");
        System.out.println("Value set in input: " + valueSet);

        //Add Validity Date To
        WebElement validityDateToInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("input[name='valid_until']")));
        String datetimeToValue = "2025-11-10T12:00";
        ((JavascriptExecutor) driver).executeScript("arguments[0].value = arguments[1];", validityDateToInput, datetimeToValue);
        System.out.println("Date set via JavaScript.");
        String toValueSet = validityDateToInput.getAttribute("value");
        System.out.println("Value set in input: " + toValueSet);

        //Select Restaurant
        // 1. Click to open the dropdown
        WebElement dropdown = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("span.select2-selection--multiple")));
        dropdown.click();

        // 2. Wait for the options to appear and select desired option by visible text
        String restaurantName = "Delmunch Restaurant";

        WebElement option = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//li[contains(@id,'select2-restaurantSelect-result') and text()='" + restaurantName + "']")
        ));
        option.click();

        System.out.println("Restaurant selected: " + restaurantName);

        //Select Limit Per User
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("input[name='limit']"))).sendKeys("5");
        System.out.println("Limit Per User selected: 5");
        //Submit the form
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("button[class='btn btn-primary']"))).click();
        System.out.println("Form submitted.");

    }

    // Delete coupon
    public static void DeleteCoupon(WebDriverWait wait, WebDriver driver){
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("input[type='search']"))).sendKeys("ABCD1234");
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".btn.btn-danger.btn-sm"))).click();
        // Handle alert for delete confirmation
        Alert alert = wait.until(ExpectedConditions.alertIsPresent());
        alert.accept();
        System.out.println("Coupon deleted.");





    }

}

