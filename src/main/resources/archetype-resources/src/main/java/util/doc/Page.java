package ${groupId}.util.doc;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Brigitte Hulliger, <hulliger@puzzle.ch>
 * @version 1.0
 * @since 18.02.2013
 * @documentation This annotation defines the annotated class as a page in an
 *                application. Pages have a name and an ID and can be called by
 *                a request. The annotation can only be placed on types, not
 *                methods.
 */
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface Page {

	/**
	 * @return the page identifier which is a unique String
	 */
	public String id();

	/**
	 * @return the page name.
	 */
	public String name();

}
