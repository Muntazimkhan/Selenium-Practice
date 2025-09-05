package test;

import base.BaseTest;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.nio.file.Paths;
import java.time.Duration;

public class ManageRestaurants {
    public static void main(String[] args) {
        WebDriver driver = null;
        try {
            // ✅ Login + driver setup handled in BaseTest
            driver = BaseTest.createLoggedInDriver();
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            JavascriptExecutor js = (JavascriptExecutor) driver;

            // Navigate to Restaurants page
            driver.findElement(By.cssSelector("li.mm-active")).click();
            driver.get("https://panel.delmunch.com/restaurant");
            String currentUrl = driver.getCurrentUrl();

            if (currentUrl.contains("/restaurant")) {
                System.out.println("✓ PASS: URL contains '/restaurant'");
            } else {
                System.out.println("✗ FAIL: URL does not contain '/restaurant'");
            }
            System.out.println("Current URL: " + currentUrl);

            // Create a new Restaurant
            WebElement addButton = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".lni.lni-plus.fs-3.me-2.mt-1")));
            js.executeScript("arguments[0].scrollIntoView({block: 'center'});", addButton);
            Thread.sleep(500);
            addButton.click();

            driver.findElement(By.id("name")).sendKeys("Test Restaurant");
            driver.findElement(By.id("name_ur")).sendKeys("Test Restaurant");
            driver.findElement(By.id("address")).sendKeys("123 Test St, Test City");
            driver.findElement(By.id("address_ur")).sendKeys("123 Test St, Test City");
            driver.findElement(By.id("description")).sendKeys("123 Test St, Test City");
            driver.findElement(By.id("description_ur")).sendKeys("123 Test St, Test City");

            Select emailDropdown = new Select(driver.findElement(By.id("email")));
            emailDropdown.selectByIndex(1);

            driver.findElement(By.id("phone")).sendKeys("923001234567");
            driver.findElement(By.id("latitude")).sendKeys("32.258");
            driver.findElement(By.id("longitude")).sendKeys("74.188");

            // Upload Picture
            WebElement uploadInput = driver.findElement(By.id("image"));
            String picturePath = Paths.get("C:", "Users", "Suvastu tech", "Pictures", "Camera Roll", "2.jpg").toString();
            uploadInput.sendKeys(picturePath);
            System.out.println("Uploaded file: " + uploadInput.getAttribute("value"));

            // Upload Logo
            WebElement uploadLogo = driver.findElement(By.id("logo"));
            String logoPath = Paths.get("C:", "Users", "Suvastu tech", "Pictures", "Camera Roll", "download.jpg").toString();
            uploadLogo.sendKeys(logoPath);
            System.out.println("Uploaded file: " + uploadLogo.getAttribute("value"));

            // Scroll and click delivery_enabled checkbox
            WebElement deliveryEnabled = wait.until(ExpectedConditions.elementToBeClickable(By.id("delivery_enabled")));
            js.executeScript("arguments[0].scrollIntoView({block: 'center'});", deliveryEnabled);
            Thread.sleep(500);
            deliveryEnabled.click();

            WebElement allowDecimalPrices = driver.findElement(By.id("allow_decimal_prices"));
            js.executeScript("arguments[0].scrollIntoView({block: 'center'});", allowDecimalPrices);
            Thread.sleep(300);
            allowDecimalPrices.click();

            WebElement deliveryCharges = wait.until(ExpectedConditions.elementToBeClickable(By.id("delivery_charges_per_km")));
            js.executeScript("arguments[0].scrollIntoView({block: 'center'});", deliveryCharges);
            Thread.sleep(300);
            deliveryCharges.sendKeys("10");
            System.out.println("Delivery charges per km set to: " + deliveryCharges.getAttribute("value"));

            // Scroll down before free_delivery checkbox and related input
            WebElement freeDeliveryCheckbox = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("free_delivery")));
            js.executeScript("arguments[0].scrollIntoView({block: 'center'});", freeDeliveryCheckbox);
            Thread.sleep(500);
            wait.until(ExpectedConditions.elementToBeClickable(freeDeliveryCheckbox)).click();

            WebElement freeDeliveryDistance = wait.until(ExpectedConditions.elementToBeClickable(By.id("km_under_free_delivery")));
            js.executeScript("arguments[0].scrollIntoView({block: 'center'});", freeDeliveryDistance);
            Thread.sleep(300);
            freeDeliveryDistance.sendKeys("5");
            System.out.println("Free delivery distance set to: " + freeDeliveryDistance.getAttribute("value"));

            // Scroll down to availability section and set time
            By setAvailabilityButton = By.cssSelector("button[aria-controls='collapseAvailability']");
            WebElement availabilityBtn = wait.until(ExpectedConditions.elementToBeClickable(setAvailabilityButton));
            js.executeScript("arguments[0].scrollIntoView({block: 'center'});", availabilityBtn);
            Thread.sleep(500);
            availabilityBtn.click();

            WebElement fromSunday = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("from_Sunday")));
            js.executeScript("arguments[0].scrollIntoView({block: 'center'});", fromSunday);
            Thread.sleep(300);
            fromSunday.sendKeys(Keys.chord(Keys.CONTROL, "a"), "01:00PM");

            WebElement toSunday = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("to_Sunday")));
            js.executeScript("arguments[0].scrollIntoView({block: 'center'});", toSunday);
            Thread.sleep(300);
            toSunday.sendKeys(Keys.chord(Keys.CONTROL, "a"), "11:59PM");

            WebElement applyToAll = driver.findElement(By.id("applyToAll"));
            js.executeScript("arguments[0].scrollIntoView({block: 'center'});", applyToAll);
            Thread.sleep(300);
            applyToAll.click();

            Thread.sleep(2000);  // Wait to ensure time applied

            // Scroll down to About Section
            js.executeScript("window.scrollBy(0, 500);");
            Thread.sleep(500);

            WebElement heading1 = driver.findElement(By.id("heading1"));
            js.executeScript("arguments[0].scrollIntoView({block: 'center'});", heading1);
            heading1.sendKeys("About Us_1");

            WebElement heading1Ur = driver.findElement(By.id("heading1_ur"));
            heading1Ur.sendKeys("About Us_1+ur");

            WebElement paragraph1 = driver.findElement(By.id("paragraph1"));
            paragraph1.sendKeys("Detail about us_1");

            WebElement paragraph1Ur = driver.findElement(By.id("paragraph1_ur"));
            paragraph1Ur.sendKeys("Detail about us_1_ur");

            Thread.sleep(2000);

            // Scroll down before submit button
            WebElement submitBtn = driver.findElement(By.xpath("//button[normalize-space()='Submit']"));
            js.executeScript("arguments[0].scrollIntoView({block: 'center'});", submitBtn);
            Thread.sleep(500);
            submitBtn.click();

            Thread.sleep(2000);

            // Search and delete created Restaurant
            WebElement searchBox = driver.findElement(By.cssSelector("input[type='search']"));
            js.executeScript("arguments[0].scrollIntoView({block: 'center'});", searchBox);
            searchBox.sendKeys("Test Restaurant");
            Thread.sleep(2000);

            WebElement searchBtn = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("button[type='submit']")));
            js.executeScript("arguments[0].scrollIntoView({block: 'center'});", searchBtn);
            Thread.sleep(300);
            searchBtn.click();

            // Handle alert for delete confirmation
            Alert alert = wait.until(ExpectedConditions.alertIsPresent());
            alert.accept();

            Thread.sleep(2000);

            System.out.println("Deleted the created restaurant.");
            System.out.println("Manage Restaurant test completed successfully.");
        } catch (Exception e) {
            System.err.println("Error encountered during test:");
            e.printStackTrace();
        } finally {
            BaseTest.quit(driver);
            System.out.println("Driver closed.");
        }
    }
}
