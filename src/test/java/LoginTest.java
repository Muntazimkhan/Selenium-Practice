import io.github.bonigarcia.wdm.WebDriverManager;
import io.github.cdimascio.dotenv.Dotenv;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import test.loginhelper.LoginHelper;

public class LoginTest {
    public static void main(String[] args) {
        // Automatically sets up ChromeDriver
        WebDriverManager.chromedriver().setup();

        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();

        driver.get("https://panel.delmunch.com/login");
        System.out.println("Title: " + driver.getTitle());

        Dotenv dotenv = Dotenv.load();
        String email = dotenv.get("EMAIL");
        String password = dotenv.get("PASSWORD");
        LoginHelper.login(driver, email, password);

        driver.quit();
    }
}
