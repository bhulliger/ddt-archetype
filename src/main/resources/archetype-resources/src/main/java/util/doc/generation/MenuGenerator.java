/**
 * 
 */
package ${groupId}.util.doc.generation;

import static ${groupId}.util.doc.generation.SiteDescriptorUtils.PAGES_PLACEHOLDER;
import static ${groupId}.util.doc.generation.SiteDescriptorUtils.USE_CASES_PLACEHOLDER;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.reflections.Reflections;

import ${groupId}.util.doc.Page;
import ${groupId}.util.doc.UseCase;

/**
 * Generator for the site.xml file. This main class gets executed in the pre-site lifecycle of the
 * maven site (as configured in the pom.xml)
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
	public static void main(final String[] args) throws FileNotFoundException, IOException {
		final Reflections sources = new Reflections("${groupId}");

		final List<Class<?>> useCaseClasses = new ArrayList<>(sources.getTypesAnnotatedWith(UseCase.class));

		Collections.sort(useCaseClasses, CLASSNAME_COMPARATOR);

		// USE CASES
		final StringBuilder useCasesLinks = new StringBuilder();
		for (final Class<?> usecaseClass : useCaseClasses) {
			final UseCase usecase = usecaseClass.getAnnotation(UseCase.class);

			final String link = SiteDescriptorUtils.generateMenuLink(
					"generated/" + SiteDescriptorUtils.evaluatePath(usecaseClass), usecase.id(), usecase.name(), ": ");
			
			useCasesLinks.append(link);
		}

		SiteDescriptorUtils.generateMenuSnippet(USE_CASES_PLACEHOLDER, useCasesLinks.toString());

		// PAGES
		final List<Class<?>> pagesClasses = new ArrayList<>(sources.getTypesAnnotatedWith(Page.class));
		Collections.sort(pagesClasses, CLASSNAME_COMPARATOR);

		final StringBuilder pagesLinks = new StringBuilder();
		for (final Class<?> pageClass : pagesClasses) {
			final Page page = pageClass.getAnnotation(Page.class);

			final String link = SiteDescriptorUtils.generateMenuLink(
					"generated/" + SiteDescriptorUtils.evaluatePath(pageClass), page.id(), page.name(), ": ");
			pagesLinks.append(link);
		}

		SiteDescriptorUtils.generateMenuSnippet(PAGES_PLACEHOLDER, pagesLinks.toString());

	}
}
