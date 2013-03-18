package ${groupId}.util.report;

/**
 * Enum for the testresult in the report.
 * 
 * @author Brigitte Hulliger, <hulliger@puzzle.ch>
 * 
 */
public enum TestResult {

	SUCCEEDED("OK"),

	FAILED("NOK"),

	NOT_AVAILABLE("n/a");

	private String value;

	private TestResult(final String result) {
		this.value = result;
	}

	public static TestResult getInstanceByValue(final String value) {
		for (final TestResult testResult : values()) {
			if (value.equals(testResult.value)) {
				return testResult;
			}
		}
		throw new IllegalArgumentException("unsupported value: " + value);
	}

	public String getValue() {
		return this.value;
	}

}
