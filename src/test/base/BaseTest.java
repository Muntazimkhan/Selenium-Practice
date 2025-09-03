package test.base;

import io.github.bonigarcia.wdm.WebDriverManager;
import io.github.cdimascio.dotenv.Dotenv;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import test.loginhelper.LoginHelper;

public class BaseTest {
    public static WebDriver createLoggedInDriver() {
        WebDriverManager.chromedriver().setup();

        ChromeOptions opts = new ChromeOptions();
        if (Boolean.parseBoolean(System.getenv().getOrDefault("HEADLESS","false"))) {
            opts.addArguments("--headless=new","--no-sandbox","--disable-dev-shm-usage");
        }

        WebDriver driver = new ChromeDriver(opts);
        driver.manage().window().maximize();

        Dotenv env = Dotenv.load();
        String loginUrl = env.get("LOGIN_URL", "https://panel.delmunch.com/login");
        String email    = env.get("EMAIL");
        String password = env.get("PASSWORD");

        // 👉 Go to the login page BEFORE calling the helper
        driver.get(loginUrl);

        LoginHelper.login(driver, email, password);
        return driver;
    }

    public static void quit(WebDriver driver) {
        if (driver != null) try { driver.quit(); } catch (Exception ignored) {}
    }
}
