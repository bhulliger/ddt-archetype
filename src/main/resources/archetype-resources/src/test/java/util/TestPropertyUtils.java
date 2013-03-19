package ${groupId}.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.Logger;

import ${groupId}.util.doc.generation.SitePropertyUtils;

public class TestPropertyUtils {

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
			try (final InputStream resourceAsStream = TestPropertyUtils.class
					.getResourceAsStream("/config.properties")) { //$NON-NLS-1$
				properties.load(resourceAsStream);
				resourceAsStream.close();
			} catch (IOException e) {
				log.error("could not read property file [config.properties]", e); //$NON-NLS-1$
			}
		}
		return properties;
	}

}
