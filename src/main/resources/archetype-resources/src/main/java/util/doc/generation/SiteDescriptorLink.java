package ${groupId}.util.doc.generation;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Brigitte Hulliger, <hulliger@puzzle.ch>
 */
public class SiteDescriptorLink {

	private Class<?> clazz;

	private String id;

	private String linkName;

	private Set<SiteDescriptorLink> subLinks;

	/**
	 * @param clazz
	 * @param id
	 * @param linkName
	 * @param subLinks
	 */
	public SiteDescriptorLink(Class<?> clazz, String id, String linkName,
			Set<SiteDescriptorLink> subLinks) {
		this.clazz = clazz;
		this.id = id;
		this.linkName = linkName;
		this.subLinks = subLinks;
	}

	/**
	 * @param clazz
	 * @param id
	 * @param linkName
	 */
	public SiteDescriptorLink(Class<?> clazz, String id, String linkName) {

		this.clazz = clazz;
		this.id = id;
		this.linkName = linkName;
		this.subLinks = new HashSet<>();
	}

	/**
	 * generates a http-link to add to the site.xml (maven site descriptor)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();

		String filePath = evaluatePath(this.clazz);

		sb.append("\n<item name='").append(this.linkName).append("' href='") //$NON-NLS-1$ //$NON-NLS-2$
				.append("/generated/").append(filePath).append("/").append(this.id) //$NON-NLS-1$ //$NON-NLS-2$
				.append(".html'>"); //$NON-NLS-1$

		for (SiteDescriptorLink subLink : this.subLinks) {
			sb.append(subLink.toString());
		}

		sb.append("</item>"); //$NON-NLS-1$

		return sb.toString();
	}

	/**
	 * generates the output path from the package declaration.
	 * 
	 * @param clazz
	 * @return path
	 */
	protected static String evaluatePath(final Class<?> clazz) {
		// FIXME: windows path
		return clazz.getPackage().toString().replace("package ", "") //$NON-NLS-1$ //$NON-NLS-2$
				.replaceAll("\\.", "/"); //$NON-NLS-1$//$NON-NLS-2$
	}
}
