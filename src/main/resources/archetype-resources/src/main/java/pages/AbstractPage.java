package ${groupId}.pages;

import org.apache.log4j.Logger;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

import ${groupId}.util.doc.Page;

/**
 * Abstract superclass for each Web Page to test. All pages that can be
 * instantiated and should be documented should be annotated with the {@link Page} annotation.
 * 
 * @author Brigitte Hulliger, <hulliger@puzzle.ch>
 * 
 * @version 1.0
 * @since 19.02.2013
 */
public abstract class AbstractPage {

	private static final Logger log = Logger.getLogger(AbstractPage.class);

	/** Selenium Webdriver */
	private final WebDriver driver;

	// --------------------------- constructor --------------------------------

	/**
	 * @param driver
	 */
	public AbstractPage(final WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(this.driver, this);
	}

	/**
	 * @return the driver
	 */
	public WebDriver getDriver() {
		return this.driver;
	}

	// --------------------------- utility methods ----------------------------
	/**
	 * @param element
	 *            the element in question
	 * @return whether the provided element can be found and is displayed.
	 */
	public boolean isElementDisplayed(final WebElement element) {
		try {
			return element.isDisplayed();
		}
		catch (final NoSuchElementException e) {
			// element could not be found, therefore not displayed
			return false;
		}
	}

	/**
	 * Creates a {@link Select} from a Webelement and selects an entry by
	 * visible text. This method is needed since it's not possible to initialize {@link Select}s
	 * with annotations and {@link PageFactory}
	 * 
	 * @param dropDownElement
	 * @param visibleText
	 */
	public void selectInDropDown(final WebElement dropDownElement, final String visibleText) {
		try {
			new Select(dropDownElement).selectByVisibleText(visibleText);
		}
		catch (final Exception e) {
			log.debug("[" + visibleText + "] could not be selected in the dropdown.", e);  //$NON-NLS-1$//$NON-NLS-2$
		}
	}

	/**
	 * Creates a {@link Select} from a Webelement and selects an entry by
	 * visible text. This method is needed since it's not possible to initialize {@link Select}s
	 * with annotations and {@link PageFactory}
	 * 
	 * @param dropDownElement
	 * @param visibleText
	 */
	public void selectInDropDown(final WebElement dropDownElement, final int index) {

		try {
			new Select(dropDownElement).selectByIndex(index);
		}
		catch (final Exception e) {
			log.debug("[index " + index + "] could not be selected in the dropdown.", e); //$NON-NLS-1$ //$NON-NLS-2$
		}
	}

	/**
	 * Creates a {@link Select} from a Webelement and selects an entry by
	 * visible text. This method is needed since it's not possible to initialize {@link Select}s
	 * with annotations and {@link PageFactory}
	 * 
	 * @param dropDownElement
	 * @param visibleText
	 */
	public void selectInDropDownByValue(final WebElement dropDownElement, final String value) {
		try {

			new Select(dropDownElement).selectByValue(value);
		}
		catch (final Exception e) {
			log.debug("[value " + value + "] could not be selected in the dropdown.", e); //$NON-NLS-1$ //$NON-NLS-2$
		}
	}

}
