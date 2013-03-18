/**
 * 
 */
package ${groupId}.util.doc;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.rules.TestWatcher;
import org.junit.runner.Description;

import ${groupId}.util.TestPropertyUtils;
import ${groupId}.util.report.TestResultSaver;

/**
 * Saves the TestResult to the testresult.csv file.
 * 
 * @author Brigitte Hulliger, <hulliger@puzzle.ch>
 * 
 */
public class ReportWatcher extends TestWatcher {

	/**
	 * FIXME: By default, the current date is used as an identification of the release.
	 * If you have a Build date or similar, you may want to use that.
	 */
	private static final String currentRelease = new SimpleDateFormat("dd.MM.yyyy").format(new Date());

	/**
	 * @see org.junit.rules.TestWatcher#succeeded(org.junit.runner.Description)
	 */
	@Override
	protected void succeeded(final Description description) {
		if ("on".equals(TestPropertyUtils.getPropertyValue("reports"))) {
			TestResultSaver.getInstance().succeeded(currentRelease, description.getAnnotation(TestCase.class).id());
		}
		super.succeeded(description);
	}

	/**
	 * @see org.junit.rules.TestWatcher#failed(java.lang.Throwable, org.junit.runner.Description)
	 */
	@Override
	protected void failed(final Throwable e, final Description description) {
		if ("on".equals(TestPropertyUtils.getPropertyValue("reports"))) {
			TestResultSaver.getInstance().failed(currentRelease, description.getAnnotation(TestCase.class).id());
		}
		super.failed(e, description);
	}

}
