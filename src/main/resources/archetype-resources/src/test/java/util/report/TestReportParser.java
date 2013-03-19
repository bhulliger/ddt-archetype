package ${groupId}.util.report;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import au.com.bytecode.opencsv.CSVReader;
import au.com.bytecode.opencsv.CSVWriter;
import ${groupId}.util.doc.generation.SitePropertyUtils;

public class TestReportParser {

	private static final Logger log = Logger.getLogger(TestReportParser.class);

	/**
	 * Saves the given testReport Data to the Test Report File.
	 * 
	 * @param testReport
	 * @param separator
	 */
	static void saveTestReport(
			final Map<String, Map<String, TestResult>> testReport) {

		final List<String> releases = new ArrayList<>(testReport.keySet());
		Collections.sort(releases);

		final Set<String> testCaseSet = new HashSet<>();

		// collect all testcases that ever existed.
		for (final Map<String, TestResult> testRun : testReport.values()) {
			testCaseSet.addAll(testRun.keySet());
		}

		final List<String> testCases = new ArrayList<>(testCaseSet);
		Collections.sort(testCases);

		// init csvwriter
		try {
			final File file = initFile();

			try (final CSVWriter writer = new CSVWriter(new FileWriter(file))) {

				// print header row
				final String[] headerRow = new String[releases.size() + 1];
				for (int i = 0; i < releases.size(); i++) {
					headerRow[i + 1] = releases.get(i);
				}
				writer.writeNext(headerRow);

				// print test cases

				for (int i = 0; i < testCases.size(); i++) {
					final String[] testCaseRow = new String[releases.size() + 1];
					final String currentTestCase = testCases.get(i);
					testCaseRow[0] = currentTestCase;

					for (int column = 1; column <= releases.size(); column++) {
						final String release = releases.get(column - 1);
						if (testReport.get(release)
								.containsKey(currentTestCase)) {
							testCaseRow[column] = testReport.get(release)
									.get(currentTestCase).getValue();
						} else {
							testCaseRow[column] = TestResult.NOT_AVAILABLE
									.getValue();
						}
					}

					writer.writeNext(testCaseRow);
				}
			} catch (final IOException e) {
				log.error(e);
			}
		} catch (final IOException e) {
			log.error(e);
		}

	}

	/**
	 * Searches for a Test report File and reads it.
	 * 
	 * @return the parsed testresult.csv as a map
	 */
	public static Map<String, Map<String, TestResult>> parseExistingTestReport() {
		final Map<String, Map<String, TestResult>> testReport = new HashMap<>();
		final List<String> releases = new ArrayList<>();

		try {

			final File file = initFile();

			try (final CSVReader reader = new CSVReader(new FileReader(file))) {
				final List<String[]> allRows = reader.readAll();

				if (!allRows.isEmpty()) {
					// init releases
					for (int i = 1; i < allRows.get(0).length; i++) {
						releases.add(allRows.get(0)[i]);
					}

					for (int i = 1; i < allRows.size(); i++) {
						// parse result
						final String[] currentRow = allRows.get(i);
						if (currentRow.length < releases.size() + 1) {
							System.out.println("invalid format. aborting."); //$NON-NLS-1$
							break;
						}
						final String testCase = currentRow[0];
						// parse through columns
						for (int j = 1; j < currentRow.length; j++) {
							final String release = releases.get(j - 1);
							final TestResult testResult = TestResult
									.getInstanceByValue(currentRow[j]);

							if (testReport.get(release) == null) {
								testReport.put(release,
										new HashMap<String, TestResult>());
							}

							testReport.get(release).put(testCase, testResult);
						}
					}
				}
				reader.close();
			}

		} catch (final Exception e) {
			log.error(e);
		}

		return testReport;
	}

	/**
	 * @throws IOException
	 */
	private static File initFile() throws IOException {
		final File file = new File(
				SitePropertyUtils.getPropertyValue("site.report.testreport")); //$NON-NLS-1$
		if (!file.exists()) {
			file.getParentFile().mkdirs();
			file.createNewFile();
		}
		return file;
	}

}
