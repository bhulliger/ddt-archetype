package ${groupId}.util.doc.generation;

import static ${groupId}.util.doc.generation.SiteDescriptorConstants.SITE_XML_SNIPPETS;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map.Entry;

import org.apache.commons.io.FileUtils;

import ${groupId}.util.doc.generation.SitePropertyUtils;

/**
 * Generator for the site.xml file. This main class gets executed in the
 * pre-site lifecycle of the maven site (as configured in the pom.xml)
 * 
 * @author Brigitte Hulliger, <hulliger@puzzle.ch>
 */
public class GenerateSiteDescriptor {

	/**
	 * Generates the site descriptor (site.xml) from the templates. Replaces the
	 * Placeholders with the menu items.
	 * 
	 * Expects that the menus are ready in separate files and replaces the
	 * placeholders with the appropriate file content.
	 * 
	 * @param args
	 * @throws IOException
	 */
	public static void main(final String[] args) throws IOException {
		try (final BufferedReader reader = new BufferedReader(new FileReader(
				SitePropertyUtils.getPropertyValue("site.descriptor.template"))); //$NON-NLS-1$
				final PrintWriter writer = new PrintWriter(new FileWriter(
						"src/site/site.xml"))) { //$NON-NLS-1$
			String line = null;
			while ((line = reader.readLine()) != null) {
				boolean replacement = false;
				for (final Entry<String, String> xmlPlaceHolder : SITE_XML_SNIPPETS
						.entrySet()) {
					File file = null;
					if (line.contains(xmlPlaceHolder.getKey())) {
						replacement = true;
						if ((file = new File(xmlPlaceHolder.getValue()))
								.exists()) {
							writer.println(line.replace(
									xmlPlaceHolder.getKey(),
									FileUtils.readFileToString(file)));
						}
					}
				}
				if (!replacement) {
					writer.println(line);
				}
				replacement = false;
			}
		}
	}

}
