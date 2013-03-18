package ${groupId}.util.doc.generation;

// TODO: replace package
import static ${groupId}.util.doc.generation.SiteDescriptorUtils.TEST_CASES_PLACEHOLDER;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import org.reflections.Reflections;
import org.reflections.scanners.MethodAnnotationsScanner;

import ${groupId}.util.doc.TestCase;

public class TestMenuGenerator {

	/**
	 * @param args
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public static void main(final String[] args) throws FileNotFoundException, IOException {
		// TODO: replace package with ${groupId}
		final Reflections sources = new Reflections("${groupId}", new MethodAnnotationsScanner());

		final ArrayList<Method> testCaseMethods = new ArrayList<>(sources.getMethodsAnnotatedWith(TestCase.class));

		Collections.sort(testCaseMethods, new Comparator<Method>() {

			@Override
			public int compare(final Method o1, final Method o2) {
				return o1.getAnnotation(TestCase.class).id().compareTo(o2.getAnnotation(TestCase.class).id());
			}
		});

		// TEST CASES
		final StringBuilder testCasesLinks = new StringBuilder();
		for (final Method testcaseMethod : testCaseMethods) {
			final TestCase testcase = testcaseMethod.getAnnotation(TestCase.class);

			final String link = SiteDescriptorUtils.generateMenuLink(
					"generated/" + SiteDescriptorUtils.evaluatePath(testcase.useCase()), testcase.id(), "", "");

			testCasesLinks.append(link);
		}

		SiteDescriptorUtils.generateMenuSnippet(TEST_CASES_PLACEHOLDER, testCasesLinks.toString());

	}

}
