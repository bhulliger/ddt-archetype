package ${groupId}.usecases;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Rule;

import ch.puzzle.doc.reports.ReportWatcher;
import ch.puzzle.selenium.BaseSeleniumTest;

/**
 * @author Brigitte Hulliger, <hulliger@puzzle.ch>
 * 
 * @version 1.0
 * @since 05.03.2013
 * 
 * @documentation Base Class of all use cases
 */
public abstract class BaseUseCaseTest extends BaseSeleniumTest {

	@Rule
	public ReportWatcher reportWatcher = new ReportWatcher() {

		/** Release Build ID. Default is the date */
		private String currentReleaseBuild;

		@Override
		public String getCurrentReleaseBuild() {
			if (this.currentReleaseBuild == null) {
				// TODO: insert your release build here if you have something
				// better than the date.
				currentReleaseBuild = new SimpleDateFormat("dd.MM.yyyy")
						.format(new Date());
			}
			return this.currentReleaseBuild;
		}
	};

}

