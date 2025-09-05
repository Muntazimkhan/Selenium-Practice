package base;

import io.github.bonigarcia.wdm.WebDriverManager;
import io.github.cdimascio.dotenv.Dotenv;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import loginhelper.LoginHelper;

public class BaseTest {
    public static WebDriver createLoggedInDriver() {
        WebDriverManager.chromedriver().setup();

        ChromeOptions opts = new ChromeOptions();

        // Read system property for headless mode, default is normal (not headless)
        String browserMode = System.getProperty("selenium.browser", "normal");
        if ("headless".equalsIgnoreCase(browserMode)) {
            opts.addArguments("--headless=new", "--no-sandbox", "--disable-dev-shm-usage");
        }

        WebDriver driver = new ChromeDriver(opts);
        driver.manage().window().maximize();
        driver.manage().deleteAllCookies();

        // Load dotenv for fallback environment variables (local dev)
        Dotenv env = Dotenv.load();

        // First try system environment variables, fallback to .env values
        String loginUrl = System.getenv().getOrDefault("LOGIN_URL", env.get("LOGIN_URL", "https://panel.delmunch.com/login"));
        String email = System.getenv().getOrDefault("EMAIL", env.get("EMAIL"));
        String password = System.getenv().getOrDefault("PASSWORD", env.get("PASSWORD"));

        driver.get(loginUrl);

        LoginHelper.login(driver, email, password);

        return driver;
    }

    public static void quit(WebDriver driver) {
        if (driver != null) {
            try {
                driver.quit();
            } catch (Exception ignored) {}
        }
    }
}
