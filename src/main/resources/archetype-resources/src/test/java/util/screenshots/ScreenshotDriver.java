package ${groupId}.util.screenshots;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

/**
 * Driver used to take screenshots. It wrappes a {@link FirefoxDriver} and adds
 * a pointcut around (@see {@link ScreenshotAspect}).
 * 
 * @author Brigitte Hulliger, <hulliger@puzzle.ch>
 * 
 */
public class ScreenshotDriver extends FirefoxDriver implements Screenshotable {

	private static final Logger log = Logger.getLogger(ScreenshotDriver.class);

	@Override
	public WebElement findElement(final By by) {
		return new ScreenshotWebElement(super.findElement(by), this);
	}

	/**
	 * @see ${groupId}.util.screenshots.Screenshotable#takeScreenshot(java.lang.String)
	 */
	@Override
	public void takeScreenshot(final String destinationFile) {
		try {
			final File scrFile = ((TakesScreenshot) this)
					.getScreenshotAs(OutputType.FILE);

			FileUtils.copyFile(scrFile, new File(destinationFile));
		} catch (final IOException e) {
			log.error("could not copy screenshot file.", e); //$NON-NLS-1$
		}

	}

}