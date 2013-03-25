package ${groupId}.util.doc.generation;

import static ch.puzzle.util.DocletPropertyUtils.getPropertyValue;

import java.util.HashMap;
import java.util.Map;

public class SiteDescriptorConstants {

	/**
	 * The placeholder where to put the pages menu in the site descriptor
	 * (site.xml)
	 */
	static final String PAGES_PLACEHOLDER = "${PAGES}";

	/**
	 * The placeholder where to put the use cases menu in the site descriptor
	 * (site.xml)
	 */
	static final String USE_CASES_PLACEHOLDER = "${USE_CASES}";

	/** placeholder for the menus in the site.xml page descriptor */
	static final Map<String, String> SITE_XML_SNIPPETS = new HashMap<>();
	static {
		SITE_XML_SNIPPETS.put(USE_CASES_PLACEHOLDER,
				getPropertyValue("site.descriptor.menu.usecases"));
		SITE_XML_SNIPPETS.put(PAGES_PLACEHOLDER,
				getPropertyValue("site.descriptor.menu.pages"));
	}

	/** placeholder for the menus in the site.xml page descriptor */
	static final Map<String, String> SITE_XML_TEMPLATES = new HashMap<>();
	static {
		SITE_XML_TEMPLATES.put(USE_CASES_PLACEHOLDER,
				getPropertyValue("site.descriptor.menu.usecases.template"));
		SITE_XML_TEMPLATES.put(PAGES_PLACEHOLDER,
				getPropertyValue("site.descriptor.menu.pages.template"));
	}

}
