package uiautomation.Common;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.google.common.base.Function;

public class Base {

	static final int EXPLICIT_WAIT_TIMEOUT = 50;
	static final int EXPLICIT_POLLING_TIMEOUT = 1;
	static final int IMPLICIT_WAIT_TIMEOUT = 10;
	static final String PROPERTY_FILE_PATH = "resources//prop.properties";
	public static Properties prop;
	static WebDriverWait w;
	public static WebDriver driver; 

	public static Properties readProperties() throws IOException {

		prop = new Properties();
		FileInputStream file = new FileInputStream(PROPERTY_FILE_PATH);
		prop.load(file);
		return prop;

	}

	public WebDriver initializeDriver() throws IOException {

		readProperties();
		String browser = prop.getProperty("browser");

		if (browser.equals("chrome")) {

			System.setProperty("webdriver.chrome.driver", "resources//chromedriver.exe");
			driver = new ChromeDriver();

		} else if (browser.equals("firefox")) {

			// add firefox driver
			driver = new FirefoxDriver();

		} else if (browser.equals("ie")) {

			// add ie driver
			driver = new InternetExplorerDriver();

		}

		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(IMPLICIT_WAIT_TIMEOUT,
		TimeUnit.SECONDS); 
		return driver;

	}

	public void quitDriver(WebDriver driver) {
		driver.quit();

	}


	public WebElement waitToClick(WebDriver driver, By by) {

		Wait<WebDriver> fwait = new FluentWait<WebDriver>(driver).withTimeout(Duration.ofSeconds(EXPLICIT_WAIT_TIMEOUT))
				.pollingEvery(Duration.ofSeconds(EXPLICIT_POLLING_TIMEOUT)).ignoring(NoSuchElementException.class);
		WebElement element = fwait.until(new Function<WebDriver, WebElement>() {
			public WebElement apply(WebDriver webDriver) {
				return webDriver.findElement(by);
			}
		});
		return element;

	}

	public WebElement waitTillPresent(WebDriver driver, By by) {
		w = new WebDriverWait(driver, EXPLICIT_WAIT_TIMEOUT);
		return w.until(ExpectedConditions.presenceOfElementLocated(by));
	}

	public List<WebElement> waitTillAllPresent(WebDriver driver, By by) {
		w = new WebDriverWait(driver, EXPLICIT_WAIT_TIMEOUT);
		return w.until(ExpectedConditions.presenceOfAllElementsLocatedBy(by));

	}
}
