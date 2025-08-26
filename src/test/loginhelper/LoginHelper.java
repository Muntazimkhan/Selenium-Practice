package test.loginhelper;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class LoginHelper {
    public static void login(WebDriver driver, String email, String password) {
        driver.findElement(By.id("inputEmailAddress")).sendKeys(email);
        driver.findElement(By.id("inputChoosePassword")).sendKeys(password);
        driver.findElement(By.xpath("//button[text()='Sign in']")).click();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        //Assertion
        boolean loggedIn = wait.until(ExpectedConditions.urlToBe("https://panel.delmunch.com/home"));

        if (loggedIn) {
            System.out.println("Login successful");
        } else {
            System.out.println("Login failed");
        }
    }
}
