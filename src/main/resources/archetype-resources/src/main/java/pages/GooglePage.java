package ${groupId}.pages;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import ${groupId}.util.doc.Page;

/**
 * @author Brigitte Hulliger, <hulliger@puzzle.ch>
 * 
 * @documentation This page describes the Google search page... Everything that
 *                is described in this documentation-tag will be displayed on
 *                the documentation page.
 */
@Page(id = "Google", name = "Google")
public class GooglePage extends AbstractPage {

	public GooglePage(final WebDriver driver) {
		super(driver);

		this.getDriver().get("http://www.google.ch"); //$NON-NLS-1$
	}

	/**
	 * searches on google for the provided searchargument
	 * 
	 * @param searchArgument
	 * @return the {@link GooglePage}
	 */
	public GooglePage search(final String searchArgument) {
		final WebElement qField = this.getDriver().findElement(By.name("q")); //$NON-NLS-1$
		qField.sendKeys(searchArgument);
		qField.submit();
		return this;
	}

	/**
	 * clicks the result with the given index (starting by 1)
	 * 
	 * @param index
	 * @return {@link GooglePage}
	 */
	public GooglePage selectResult(final int index) {
		// give google the time to find the result.
		this.getDriver().manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
		this.getDriver().findElement(By.xpath("//*[@id=\"rso\"]/li[" + index + "]/div/h3/a")).click(); //$NON-NLS-1$ //$NON-NLS-2$
		return this;
	}

}
