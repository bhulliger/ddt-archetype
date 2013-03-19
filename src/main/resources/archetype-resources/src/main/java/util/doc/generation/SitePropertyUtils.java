package ${groupId}.util.doc.generation;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.Logger;

/**
 * @author Brigitte Hulliger, <hulliger@puzzle.ch>
 * 
 */
public class SitePropertyUtils {

	private static final Logger log = Logger.getLogger(SitePropertyUtils.class);

	private static Properties properties;

	/**
	 * @param property
	 * @return the value to the provided property
	 */
	public static String getPropertyValue(final String property) {
		return getProperties().getProperty(property);
	}

	/**
	 * Initializes the properties if not done yet.
	 * 
	 * @return the (initialized) properties.
	 */
	private static Properties getProperties() {
		if (properties == null) {
			properties = new Properties();
			try (final InputStream resourceAsStream = SitePropertyUtils.class
					.getResourceAsStream("/site.properties")) { //$NON-NLS-1$
				properties.load(resourceAsStream);
				resourceAsStream.close();
			} catch (IOException e) {
				log.error("could not read property file [site.properties]", e); //$NON-NLS-1$
			}
		}
		return properties;
	}
}
