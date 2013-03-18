package ${groupId}.usecases;

import static ${groupId}.util.TestPropertyUtils.getPropertyValue;

import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.junit.AfterClass;
import org.junit.Rule;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

import ${groupId}.util.doc.ReportWatcher;
import ${groupId}.util.doc.ScreencastWatcher;
import ${groupId}.util.doc.ScreenshotWatcher;
import ${groupId}.util.doc.TestCase;
import ${groupId}.util.report.TestResultSaver;
import ${groupId}.util.screenshots.ScreenshotDriver;

/**
 * @author Brigitte Hulliger, <hulliger@puzzle.ch>
 * 
 * @version 1.0
 * @since 05.03.2013
 * 
 * @documentation Base Class of all use cases
 */
public abstract class BaseUseCaseTest {

	private static final Logger log = Logger.getLogger(BaseUseCaseTest.class);

	/** static driver instance */
	private static WebDriver driverInstance;

	public static TestCase currentTestCase;

	@Rule
	public TestWatcher currentTest = new TestWatcher() {

		@Override
		protected void starting(final Description description) {
			BaseUseCaseTest.currentTestCase = description.getAnnotation(TestCase.class);
			super.starting(description);
		}

	};

	@Rule
	public ScreenshotWatcher screenshotWatcher = new ScreenshotWatcher();

	@Rule
	public ScreencastWatcher screencastWatcher = new ScreencastWatcher();

	@Rule
	public ReportWatcher reportWatcher = new ReportWatcher();

	/**
	 * close all drivers and windows after the test class has finished
	 */
	@AfterClass
	public static void closeBrowser() {
		if (driverInstance != null) {
			driverInstance.close();
			driverInstance.quit();
			driverInstance = null;
		}
	}

	@AfterClass
	public static void updateReports() {
		if ("on".equals(getPropertyValue("reports"))) {
			TestResultSaver.getInstance().updateAndSaveTestReport();
		}
	}

	/**
	 * @return the initialized (singleton) driver for RE7
	 */
	protected static WebDriver getDriver() {
		if (driverInstance == null) {
			driverInstance = initDriver();
		}
		return driverInstance;
	}

	/**
	 * initialize the driver as defined in the properties file.
	 * 
	 * @return the initialized driver.
	 */
	private static WebDriver initDriver() {

		WebDriver driver = null;
		if ("on".equals(getPropertyValue("screenshots"))) {
			driver = new ScreenshotDriver();
			return driver;
		}

		// headless firefox driver
		if (getPropertyValue("driverClass").equals("org.openqa.selenium.chrome.ChromeDriver")) {
			final DesiredCapabilities capabilities = DesiredCapabilities.chrome();
			capabilities.setCapability("chrome.binary", getPropertyValue("chrome.binary"));
			final String webdriverLocation = getPropertyValue("chrome.webdriver.binary");
			if (webdriverLocation != null) {
				System.setProperty("webdriver.chrome.driver", webdriverLocation);
			}
			driver = new ChromeDriver(capabilities);
		}
		else {
			try {
				driver = (WebDriver) Class.forName(getPropertyValue("driverClass")).newInstance();
				driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
			}
			catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
				log.error(e);
				e.printStackTrace();
			}
		}

		// enable javascript for htmlUntidriver
		if (driver instanceof HtmlUnitDriver) {
			final HtmlUnitDriver htmlUnitDriver = (HtmlUnitDriver) driver;
			htmlUnitDriver.setJavascriptEnabled(true);
		}

		return driver;
	}

}
