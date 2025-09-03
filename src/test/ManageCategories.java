package test;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import test.base.BaseTest;

import java.time.Duration;

public class ManageCategories {
    public static void main(String[] args) {
        WebDriver driver = null;

        try {
            driver = BaseTest.createLoggedInDriver();
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

            navigateToCategoriesPage(driver);
            createCategory(driver, wait);
            deleteCategory(driver, wait);

            System.out.println(" Manage Categories test completed successfully.");

        } catch (Exception e) {
            System.err.println(" Error during Manage Categories test:");
            e.printStackTrace();
        } finally {
            BaseTest.quit(driver);
        }
    }

    public static void navigateToCategoriesPage(WebDriver driver) {
        System.out.println("Navigating to Categories page...");
        driver.findElement(By.cssSelector("li.mm-active")).click();
        driver.get("https://panel.delmunch.com/category");
        System.out.println("Current URL: " + driver.getCurrentUrl());
    }

    public static void createCategory(WebDriver driver, WebDriverWait wait) {
        System.out.println("Creating a new category...");

        driver.findElement(By.cssSelector(".lni.lni-plus.fs-3")).click();

        driver.findElement(By.cssSelector("input[placeholder='Name']")).sendKeys("Test Category");
        driver.findElement(By.cssSelector("input[placeholder='Name in urdu']")).sendKeys("Test Category");
        driver.findElement(By.cssSelector("textarea[placeholder='Description']")).sendKeys("This is a test category.");
        driver.findElement(By.cssSelector("textarea[placeholder='Description in urdu']")).sendKeys("This is a test category.");

        // Select Restaurant (Select2)
        WebElement dropdownTrigger = driver.findElement(By.cssSelector("span.select2-selection"));
        dropdownTrigger.click();
        WebElement firstOption = driver.findElement(By.cssSelector("ul.select2-results__options li.select2-results__option"));
        firstOption.click();

        // Upload Image
        WebElement uploadImage = driver.findElement(By.cssSelector("input[placeholder='Image']"));
        String picturePath = "C:\\Users\\Suvastu tech\\Pictures\\Camera Roll\\2.jpg";
        uploadImage.sendKeys(picturePath);
        System.out.println("Uploaded file: " + uploadImage.getAttribute("value"));

        waitFor(3);

        // Submit form
        driver.findElement(By.cssSelector("button[type='submit']")).click();
        waitFor(2);

        System.out.println(" Category created successfully.");
    }

    public static void deleteCategory(WebDriver driver, WebDriverWait wait) {
        System.out.println("Deleting the created category...");

        WebElement searchBox = driver.findElement(By.cssSelector("input[type='search']"));
        searchBox.sendKeys("Test Category");

        waitFor(2);

        // Click delete button
        driver.findElement(By.cssSelector(".btn.btn-sm.btn-outline-danger.px-2")).click();

        // Confirm delete in modal
        WebElement deleteBtnInModal = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//div[contains(@class, 'modal')]//button[contains(@class, 'btn-danger') and normalize-space()='Delete']")));
        deleteBtnInModal.click();

        waitFor(2);
        System.out.println(" Category deleted successfully.");
    }

    private static void waitFor(int seconds) {
        try {
            Thread.sleep(seconds * 1000L);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
