package test;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import test.base.BaseTest;

import java.time.Duration;

public class LogoutTest {
    public static void main(String[] args) {
        WebDriver driver = null;

        try {
            driver = BaseTest.createLoggedInDriver();
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

            performLogout(driver, wait);

            boolean loggedOut = verifyLogout(driver, wait);
            System.out.println(loggedOut ? " Logout successful" : " Logout failed");

            System.out.println("Title after logout: " + driver.getTitle());

        } catch (Exception e) {
            System.err.println(" Exception during logout test:");
            e.printStackTrace();
        } finally {
            BaseTest.quit(driver);
        }
    }

    public static void performLogout(WebDriver driver, WebDriverWait wait) {
        System.out.println("Attempting to logout...");
        driver.findElement(By.className("user-info")).click();
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("a[href='https://panel.delmunch.com/logout']"))).click();
    }

    public static boolean verifyLogout(WebDriver driver, WebDriverWait wait) {
        System.out.println("Verifying logout...");
        return wait.until(ExpectedConditions.urlContains("/login"));
    }
}
