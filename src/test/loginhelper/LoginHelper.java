// src/test/java/test/loginhelper/LoginHelper.java
package test.loginhelper;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class LoginHelper {
    public static void login(WebDriver driver, String email, String password) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5)); // faster wait

        // primary selectors
        By emailBy = By.cssSelector("#inputEmailAddress");
        By passBy  = By.cssSelector("#inputPassword");
        By submit  = By.cssSelector("button[type='submit'], input[type='submit']");

        // Email
        WebElement emailInput;
        try {
            emailInput = wait.until(ExpectedConditions.presenceOfElementLocated(emailBy));
        } catch (TimeoutException e) {
            // fallback if ids changed
            emailBy = By.cssSelector("input#email, input[name='email'], input[type='email']");
            emailInput = wait.until(ExpectedConditions.presenceOfElementLocated(emailBy));
        }
        emailInput.clear();
        emailInput.sendKeys(email);

        // Password
        WebElement passInput;
        try {
            passInput = driver.findElement(passBy); // no need to wait again if page is stable
        } catch (NoSuchElementException e) {
            passBy = By.cssSelector("input#password, input[name='password'], input[type='password']");
            passInput = driver.findElement(passBy);
        }
        passInput.clear();
        passInput.sendKeys(password);

        // Click Sign In
        wait.until(ExpectedConditions.elementToBeClickable(submit)).click();

        // Confirm navigation away from login page
        wait.until(ExpectedConditions.not(
                ExpectedConditions.urlContains("/login")
        ));
    }
}
