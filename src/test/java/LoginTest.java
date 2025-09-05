package test;

import org.openqa.selenium.WebDriver;
import base.BaseTest;

public class LoginTest {
    public static void main(String[] args) {
        WebDriver driver = null;
        try {
            // Driver setup + login handled in BaseTest
            driver = BaseTest.createLoggedInDriver();

            System.out.println("Title: " + driver.getTitle());
            System.out.println("Login test completed successfully.");
        } finally {
            BaseTest.quit(driver);
        }
    }
}
