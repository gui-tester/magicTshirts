package nz.co.sample;

import org.openqa.selenium.Alert;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URLEncoder;
import java.time.Duration;
import java.util.*;

public class TestDriver {

    private static WebDriver driver = null;

    private TestDriver() {}

    public static void stopDriver() {
        if (driver != null) {
            driver.quit();
            driver = null;
        }
    }

    private static String getUrl(Properties envProperties) throws MalformedURLException {
        URI uri = URI.create(envProperties.getProperty("url"));

        String user = envProperties.getProperty("user");
        String userDomain = envProperties.getProperty("userDomain");
        String userPassword = envProperties.getProperty("userPassword");

        if(user == null || userDomain == null || userPassword == null){
            return  uri.toString();
        } else {
            try {
                return String.format("https://%s:%s@%s%s", URLEncoder.encode(userDomain+"\\"+user, "UTF-8"),  URLEncoder.encode(userPassword, "UTF-8"), uri.getHost(), uri.getPath());
            } catch (Exception e) {
                throw new MalformedURLException(e.getMessage());
            }
        }
    }

    public static void setDriver() throws IOException, IllegalAccessException {
        if (driver == null) {

            Properties envProperties = WorkingEnvironment.getProperties();

            String browser;

            browser = envProperties.getProperty("browser");   // use browser from selected environment
            browser = browser.toUpperCase();
            if ("CHROME".equals(browser)) {
                driver = setupChrome();
           } else if ("EDGE".equals(browser)) {
                driver = setupEdge();
            } else if ("FIREFOX".equals(browser)) {
                driver = setupFirefox();
            }
            if (driver == null) {
                throw new IllegalAccessException("Unable to create an instance of the WebDriver");
            }

        } else {
            throw new IllegalAccessException("An instance of the driver is already running");
        }
    }

    public static WebDriver getDriver() {
        return driver;
    }

    private static WebDriver setupChrome() {
        System.setProperty("webdriver.chrome.driver", "webdrivers/chromedriver.exe");

        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("--window-size=1640,1200");
        chromeOptions.addArguments("--remote-allow-origins=*");
        chromeOptions.addArguments("--disable-gpu");
        chromeOptions.addArguments("--enable-automation");
        ChromeDriver driver = new ChromeDriver(chromeOptions);
        return driver;
    }

    private static WebDriver setupEdge() {
        System.setProperty("webdriver.edge.driver", "webdrivers/msedgedriver.exe");
        EdgeOptions edgeOptions = new EdgeOptions();
        edgeOptions.addArguments("--window-size=1640,1200");
        edgeOptions.addArguments("--disable-gpu");
//        edgeOptions.addArguments("headless");
//        edgeOptions.setHeadless(true);
        return new EdgeDriver(edgeOptions);
    }

    private static WebDriver setupHeadless() {
        System.setProperty("webdriver.headless.driver", "webdrivers/chrome-headless-shell.exe");
        return driver;
    }

    private static WebDriver setupFirefox() {
        FirefoxOptions browserOptions = new FirefoxOptions();
        //browserOptions.setCapability("geo.enabled", false);
        System.setProperty("webdriver.gecko.driver", "webdrivers/geckodriver.exe");
        return new FirefoxDriver(browserOptions);
    }

    public static String launchChildWindow() {
        String mainWindow = driver.getWindowHandle();
        // To handle all new opened window.
        Set<String> s1 = driver.getWindowHandles();
        Iterator<String> i1 = s1.iterator();
        while (i1.hasNext()) {
            String ChildWindow = i1.next();
            if (!mainWindow.equalsIgnoreCase(ChildWindow)) {
                // Switching to Child window
                driver.switchTo().window(ChildWindow);
            }
        }
        return mainWindow;
    }

    public static void closeChildWindow(String mainWindow) {
        // Closing the Child Window.
        driver.switchTo().window(mainWindow);
    }

    public static void switchToDefaultTab() throws InterruptedException {
        Thread.sleep(3000);
        List<String> browserTabs = new ArrayList<String>(driver.getWindowHandles());
        driver.switchTo().window(browserTabs.get(0));
    }

    public static void switchToTab(Integer tabId) throws InterruptedException {
        Thread.sleep(3000);
        List<String> browserTabs = new ArrayList<String>(driver.getWindowHandles());
        driver.switchTo().window(browserTabs.get(tabId));
    }

    public static void acceptAlert() throws InterruptedException {
//        Thread.sleep(3000);
//        try {
            Wait<WebDriver> wait = new FluentWait<>(TestDriver.getDriver())
                    // Specify the timeout of the wait
                    .withTimeout(Duration.ofSeconds(60))
                    // Specify polling time
                    .pollingEvery(Duration.ofMillis(250))
                    // Specify what exceptions to ignore
                    .ignoring(NoSuchElementException.class);

            //This is how we specify the condition to wait on.
            wait.until(ExpectedConditions.alertIsPresent());

            Alert alert = driver.switchTo().alert();
            alert.accept();
    }

    public static void scrollToTopOfPage() {
        JavascriptExecutor js = ((JavascriptExecutor) TestDriver.getDriver());
        js.executeScript("window.scrollTo(0, 0)");
    }

    public static String getURL() {
        String url = driver.getCurrentUrl();
        return url;
    }


    public static void close() {
        driver.close();
    }

    public static void quit() {
        driver.quit();
    }

}
