package ${groupId}.util.screenshots;

/**
 * @author Brigitte Hulliger, <hulliger@puzzle.ch>
 */
public interface Screenshotable {

	/**
	 * Takes a Screenshot of the current page and stores it to the given destination.
	 * 
	 * @param destinationFile the destinationpath where to store the screenshot
	 */
	void takeScreenshot(final String destinationFile);

}
