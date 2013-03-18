package ${groupId}.usecases;

import org.openqa.selenium.WebDriver;

/**
 * @author Brigitte Hulliger, <hulliger@puzzle.ch>
 */
public abstract class AbstractUseCase {

	private final WebDriver driver;

	/**
	 * @param driver
	 */
	public AbstractUseCase(final WebDriver driver) {
		this.driver = driver;
	}

	/**
	 * @return driver
	 */
	public WebDriver getDriver() {
		return this.driver;
	}
}
