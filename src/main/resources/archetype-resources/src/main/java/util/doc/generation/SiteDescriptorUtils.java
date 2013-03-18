package ${groupId}.util.doc.generation;

import static ${groupId}.util.doc.generation.SitePropertyUtils.getPropertyValue;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

public class SiteDescriptorUtils {

	/** The placeholder where to put the pages menu in the site descriptor (site.xml) */
	protected static final String PAGES_PLACEHOLDER = "${PAGES}";

	/** The placeholder where to put the test cases menu in the site descriptor (site.xml) */
	protected static final String TEST_CASES_PLACEHOLDER = "${TEST_CASES}";

	/** The placeholder where to put the use cases menu in the site descriptor (site.xml) */
	protected static final String USE_CASES_PLACEHOLDER = "${USE_CASES}";

	/** placeholder for the menus in the site.xml page descriptor */
	static final Map<String, String> SITE_XML_SNIPPETS = new HashMap<>();
	static {
		SITE_XML_SNIPPETS.put(USE_CASES_PLACEHOLDER, getPropertyValue("site.descriptor.menu.usecases"));
		SITE_XML_SNIPPETS.put(TEST_CASES_PLACEHOLDER, getPropertyValue("site.descriptor.menu.testcases"));
		SITE_XML_SNIPPETS.put(PAGES_PLACEHOLDER, getPropertyValue("site.descriptor.menu.pages"));
	}

	/** placeholder for the menus in the site.xml page descriptor */
	private static final Map<String, String> SITE_XML_TEMPLATES = new HashMap<>();
	static {
		SITE_XML_TEMPLATES.put(USE_CASES_PLACEHOLDER, getPropertyValue("site.descriptor.menu.usecases.template"));
		SITE_XML_TEMPLATES.put(TEST_CASES_PLACEHOLDER, getPropertyValue("site.descriptor.menu.testcases.template"));
		SITE_XML_TEMPLATES.put(PAGES_PLACEHOLDER, getPropertyValue("site.descriptor.menu.pages.template"));
	}

	/**
	 * @param path the parent path of the generated apt-file
	 * @param id the id of the item. describes the filename on the one hand and is used to generate
	 *            the link name together with the name param.
	 * @param name the name to display
	 * @param separator the separator between the id and the name.
	 * @return a {@link String} with the html link to add to the site descriptor.
	 */
	public static String generateMenuLink(final String path, final String id, final String name, final String separator) {
		final StringBuilder linksString = new StringBuilder();
		linksString.append("<item name='").append(id).append(separator).append(name).append("' href='").append(path)
				.append("/").append(id).append(".html").append("'/>").append("\n");

		return linksString.toString();

	}

	/**
	 * generates the output path from the package declaration.
	 * 
	 * @param clazz
	 * @return path
	 */
	protected static String evaluatePath(final Class<?> clazz) {
		return clazz.getPackage().toString().replace("package ", "").replaceAll("\\.", "/");
	}

	/**
	 * Generates the menu snippets for later include in the sitedescriptor.
	 * 
	 * @param placeHolder
	 * @param menuLinks
	 * @throws IOException
	 */
	protected static void generateMenuSnippet(final String placeHolder, final String menuLinks) throws IOException {
		try (final BufferedReader reader = new BufferedReader(new FileReader(SITE_XML_TEMPLATES.get(placeHolder)));
				final PrintWriter writer = new PrintWriter(new FileWriter(SITE_XML_SNIPPETS.get(placeHolder)))) {
			String line = null;
			while ((line = reader.readLine()) != null) {
				line = line.contains(placeHolder) ? line.replace(placeHolder, menuLinks) : line;
				writer.println(line);
			}
			reader.close();
			writer.close();
		}
	}

}
