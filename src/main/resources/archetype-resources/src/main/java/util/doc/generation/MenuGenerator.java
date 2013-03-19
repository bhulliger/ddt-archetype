/**
 * 
 */
package ${groupId}.util.doc.generation;

import static ${groupId}.util.doc.generation.SiteDescriptorConstants.PAGES_PLACEHOLDER;
import static ${groupId}.util.doc.generation.SiteDescriptorConstants.SITE_XML_SNIPPETS;
import static ${groupId}.util.doc.generation.SiteDescriptorConstants.SITE_XML_TEMPLATES;
import static ${groupId}.util.doc.generation.SiteDescriptorConstants.USE_CASES_PLACEHOLDER;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.reflections.Reflections;
import org.reflections.scanners.MethodAnnotationsScanner;

import ${groupId}.util.doc.Page;
import ${groupId}.util.doc.TestCase;
import ${groupId}.util.doc.UseCase;

/**
 * Generator for the site.xml file. This main class gets executed in the
 * pre-site lifecycle of the maven site (as configured in the pom.xml)
 * 
 * @author Brigitte Hulliger, <hulliger@puzzle.ch>
 */
public class MenuGenerator {

	private static final Comparator<Class<?>> CLASSNAME_COMPARATOR = new Comparator<Class<?>>() {
		@Override
		public int compare(final Class<?> o1, final Class<?> o2) {
			return o1.getCanonicalName().compareTo(o2.getCanonicalName());
		}
	};

	/**
	 * @param args
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public static void main(final String[] args) throws FileNotFoundException,
			IOException {
		final Reflections sources = new Reflections("${groupId}"); //$NON-NLS-1$

		Set<SiteDescriptorLink> menuLinks = new HashSet<>();

		final List<Class<?>> useCaseClasses = new ArrayList<>(
				sources.getTypesAnnotatedWith(UseCase.class));
		Collections.sort(useCaseClasses, CLASSNAME_COMPARATOR);

		Set<Method> testCaseMethods = new Reflections("${groupId}", //$NON-NLS-1$
				new MethodAnnotationsScanner())
				.getMethodsAnnotatedWith(TestCase.class);

		Map<Class<?>, Set<TestCase>> testCasesMap = new HashMap<>();

		for (Method testCaseMethod : testCaseMethods) {
			TestCase testCase = testCaseMethod.getAnnotation(TestCase.class);
			Class<?> useCase = testCase.useCase();
			if (!testCasesMap.containsKey(useCase))
				testCasesMap.put(useCase, new HashSet<TestCase>());
			testCasesMap.get(useCase).add(testCase);
		}

		// USE CASES
		for (final Class<?> usecaseClass : useCaseClasses) {

			Set<SiteDescriptorLink> subLinks = new HashSet<>();

			if (testCasesMap.containsKey(usecaseClass)) {
				for (TestCase testCase : testCasesMap.get(usecaseClass)) {
					subLinks.add(new SiteDescriptorLink(usecaseClass, testCase
							.id(), testCase.id()));
				}
			}

			final UseCase usecase = usecaseClass.getAnnotation(UseCase.class);
			menuLinks.add(new SiteDescriptorLink(usecaseClass, usecase.id(),
					usecase.id() + ":" + usecase.name(), subLinks)); //$NON-NLS-1$

		}

		generateMenuSnippet(USE_CASES_PLACEHOLDER, menuLinks);

		// PAGES
		final List<Class<?>> pagesClasses = new ArrayList<>(
				sources.getTypesAnnotatedWith(Page.class));
		Collections.sort(pagesClasses, CLASSNAME_COMPARATOR);

		final Set<SiteDescriptorLink> pagesLinks = new HashSet<>();
		for (final Class<?> pageClass : pagesClasses) {
			final Page page = pageClass.getAnnotation(Page.class);

			pagesLinks.add(new SiteDescriptorLink(pageClass, page.id(), page
					.id() + " / " + page.name())); //$NON-NLS-1$
		}

		generateMenuSnippet(PAGES_PLACEHOLDER, pagesLinks);

	}

	/**
	 * Generates the menu snippets for later include in the sitedescriptor.
	 * 
	 * @param placeHolder
	 * @param menuLinks
	 * @throws IOException
	 */
	protected static void generateMenuSnippet(final String placeHolder,
			Set<SiteDescriptorLink> links) throws IOException {

		StringBuilder menuSb = new StringBuilder();

		for (SiteDescriptorLink siteDescriptorLink : links) {
			menuSb.append(siteDescriptorLink.toString());
		}

		try (final BufferedReader reader = new BufferedReader(new FileReader(
				SITE_XML_TEMPLATES.get(placeHolder)));
				final PrintWriter writer = new PrintWriter(new FileWriter(
						SITE_XML_SNIPPETS.get(placeHolder)))) {
			String line = null;
			while ((line = reader.readLine()) != null) {
				line = line.contains(placeHolder) ? line.replace(placeHolder,
						menuSb.toString()) : line;
				writer.println(line);
			}
			reader.close();
			writer.close();
		}
	}
}
