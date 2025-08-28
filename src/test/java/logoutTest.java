import io.github.bonigarcia.wdm.WebDriverManager;
import io.github.cdimascio.dotenv.Dotenv;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import test.loginhelper.LoginHelper;

import java.time.Duration;

public class logoutTest {
    public static void main(String[] args){
        
        WebDriverManager.chromedriver().setup();
        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();

        driver.get("https://panel.delmunch.com/login");
        System.out.println("Title: " + driver.getTitle());

        Dotenv dotenv = Dotenv.load();
        String email = dotenv.get("EMAIL");
        String password = dotenv.get("PASSWORD");
        LoginHelper.login(driver,email, password);

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        // Logout
        driver.findElement(By.className("user-info")).click();
        driver.findElement(By.cssSelector("a[href='https://panel.delmunch.com/logout']")).click();

        // Wait for logout to complete and redirect to login
        boolean loggedOut = wait.until(ExpectedConditions.urlContains("/login"));

        if (loggedOut) {
            System.out.println("Logout successful");
        } else {
            System.out.println("Logout failed");
        }

        System.out.println("Title (After Logout): " + driver.getTitle());
        driver.quit();
    }

}
