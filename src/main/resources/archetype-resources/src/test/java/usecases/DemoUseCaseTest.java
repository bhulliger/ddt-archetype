package ${package}.usecases;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import ch.puzzle.annotations.TestCase;

/**
 * @author Brigitte Hulliger, <hulliger@puzzle.ch>
 * 
 */
public class DemoUseCaseTest extends BaseUseCaseTest {

	private DemoUseCase useCase;

	@Before
	public void setUp() {
		this.useCase = new DemoUseCase(getDriver());
	}

	/**
	 ** @author Brigitte Hulliger, <hulliger@puzzle.ch>
	 * 
	 * @version 1.0
	 * @since 05.03.2013
	 * 
	 * @id TC-000-1
	 * @name Search on Google.ch
	 * @usecase {@link DemoUseCase}
	 * 
	 * @abstract Search on Google and nagivate to the first result.
	 * 
	 * @preconditions -
	 * 
	 * @inputdata search string: <<<puzzle itc gmbh>>>
	 * 
	 * @actions [[1]] The user enters a search string to google and hits enter.
	 * 
	 *          [[2]] the system displays the matching results.
	 * 
	 *          [[3]] the user clicks on the first result.
	 * 
	 * @expectedResult the website of {{www.puzzle.ch}}
	 * 
	 * @postconditions -
	 * 
	 * @testCaseComment -
	 * 
	 * @testCaseHistory 05.03.2013 - 1.0 - initial Version - Brigitte Hulliger
	 */
	@Test
	@TestCase(id = "TC-000-1", useCase = DemoUseCase.class)
	public void shouldSearchOnGoogleAndReturnPuzzleWebsite() {
		// GIVEN

		// WHEN
		this.useCase.searchOnGoogleAndSelectFirstResult("puzzle itc gmbh"); //$NON-NLS-1$

		// THEN
		assertEquals("http://www.puzzle.ch/", getDriver().getCurrentUrl()); //$NON-NLS-1$
	}

}