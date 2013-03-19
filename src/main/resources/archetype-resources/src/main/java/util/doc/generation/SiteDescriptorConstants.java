package ${groupId}.util.doc.generation;

import static ${groupId}.util.doc.generation.SitePropertyUtils.getPropertyValue;

import java.util.HashMap;
import java.util.Map;

public class SiteDescriptorConstants {

	/**
	 * The placeholder where to put the pages menu in the site descriptor
	 * (site.xml)
	 */
	static final String PAGES_PLACEHOLDER = "${PAGES}"; //$NON-NLS-1$

	/**
	 * The placeholder where to put the use cases menu in the site descriptor
	 * (site.xml)
	 */
	static final String USE_CASES_PLACEHOLDER = "${USE_CASES}"; //$NON-NLS-1$

	/** placeholder for the menus in the site.xml page descriptor */
	static final Map<String, String> SITE_XML_SNIPPETS = new HashMap<>();
	static {
		SITE_XML_SNIPPETS.put(USE_CASES_PLACEHOLDER,
				getPropertyValue("site.descriptor.menu.usecases")); //$NON-NLS-1$
		SITE_XML_SNIPPETS.put(PAGES_PLACEHOLDER,
				getPropertyValue("site.descriptor.menu.pages")); //$NON-NLS-1$
	}

	/** placeholder for the menus in the site.xml page descriptor */
	static final Map<String, String> SITE_XML_TEMPLATES = new HashMap<>();
	static {
		SITE_XML_TEMPLATES.put(USE_CASES_PLACEHOLDER,
				getPropertyValue("site.descriptor.menu.usecases.template")); //$NON-NLS-1$
		SITE_XML_TEMPLATES.put(PAGES_PLACEHOLDER,
				getPropertyValue("site.descriptor.menu.pages.template")); //$NON-NLS-1$
	}

}
