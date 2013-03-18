package ${groupId}.usecases;

import org.openqa.selenium.WebDriver;

import ${groupId}.pages.GooglePage;
import ${groupId}.util.doc.UseCase;

/**
 * @author Brigitte Hulliger, <hulliger@puzzle.ch>
 * 
 * @version 1.0
 * @since 18.02.2013
 * 
 * @abstract A user logs in to the application with his provided credentials.
 * 
 * @id UC-000
 * @name Login
 * 
 * @goal A valid user is logged in.
 * @actors a user of the application
 * @status Done
 * 
 * @preconditions -
 * @postconditions the user is logged in.
 * 
 * @normalFlow [[1]] The user enters his/her credentials on the login page .
 * 
 *             [[2]] The user submits his credentials with a click on the
 *             "Login" Button.
 * 
 *             [[3]] The system checks the provided credentials and redirects
 *             the logged in user to the dashboard page.
 * 
 * @alternativeFlow If the entered credentials are wrong or the user does not
 *                  have an account, the system displays a error message on the
 *                  login page.
 * 
 * @useCaseComment -
 * 
 * @useCaseHistory 18.02.2013 - 1.0 - initial version - Brigitte Hulliger
 */
@UseCase(id = "UC-000", name = "Demo")
public class DemoUseCase extends AbstractUseCase {

	/**
	 * @param driver
	 * @param properties
	 */
	public DemoUseCase(final WebDriver driver) {
		super(driver);
	}

	/**
	 * @param searchArgument
	 */
	public void searchOnGoogleAndSelectFirstResult(final String searchArgument) {
		final GooglePage page = new GooglePage(this.getDriver());
		page.search(searchArgument).selectResult(1);
	}

}
