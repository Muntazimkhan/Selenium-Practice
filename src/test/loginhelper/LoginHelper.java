package test.loginhelper;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class LoginHelper {
    public static void login(WebDriver driver, String email, String password) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10)); // longer default

        By emailBy = By.cssSelector("#inputEmailAddress");
        By passBy = By.cssSelector("#inputPassword");
        By submit = By.cssSelector("button[type='submit'], input[type='submit']");

        // Email field
        WebElement emailInput;
        try {
            emailInput = wait.until(ExpectedConditions.visibilityOfElementLocated(emailBy));
        } catch (TimeoutException e) {
            // fallback
            emailBy = By.cssSelector("input#email, input[name='email'], input[type='email']");
            emailInput = wait.until(ExpectedConditions.visibilityOfElementLocated(emailBy));
        }
        emailInput.clear();
        emailInput.sendKeys(email);

        // Password field
        WebElement passInput;
        try {
            passInput = wait.until(ExpectedConditions.visibilityOfElementLocated(passBy));
        } catch (TimeoutException e) {
            passBy = By.cssSelector("input#password, input[name='password'], input[type='password']");
            passInput = wait.until(ExpectedConditions.visibilityOfElementLocated(passBy));
        }
        passInput.clear();
        passInput.sendKeys(password);

        // Submit form
        wait.until(ExpectedConditions.elementToBeClickable(submit)).click();

        // Optional: wait for redirect
        wait.until(ExpectedConditions.not(ExpectedConditions.urlContains("/login")));

        System.out.println("Login successful, current URL: " + driver.getCurrentUrl());
    }
}
