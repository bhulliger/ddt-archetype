/**
 * 
 */
package ${groupId}.util.doc;

import java.io.File;

import org.junit.rules.TestWatcher;
import org.junit.runner.Description;

import ${groupId}.util.TestPropertyUtils;
import ${groupId}.util.doc.generation.SitePropertyUtils;

/**
 * @author Brigitte Hulliger, <hulliger@puzzle.ch>
 */
public class ScreenshotWatcher extends TestWatcher {

	/**
	 * Deletes existing screenshots before creating new ones.
	 * 
	 * @see org.junit.rules.TestWatcher#starting(org.junit.runner.Description)
	 */
	@Override
	protected void starting(final Description description) {

		if ("on".equals(TestPropertyUtils.getPropertyValue("screenshots"))) { //$NON-NLS-1$ //$NON-NLS-2$

			final TestCase currentTestCase = description
					.getAnnotation(TestCase.class);

			// if screenshots are enabled, and the output directory already
			// exists, then delete the existing screenshots first.

			String screenshotDirectory = SitePropertyUtils
					.getPropertyValue("site.resources.output.screenshots"); //$NON-NLS-1$

			final File outputFolder = new File(screenshotDirectory
					+ currentTestCase.id());
			if (outputFolder.exists()) {
				final File[] files = outputFolder.listFiles();
				for (final File file : files) {
					file.delete();
				}
			}
		}

		super.starting(description);
	}
}
