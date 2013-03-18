/**
 * 
 */
package ${groupId}.util.doc;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Brigitte Hulliger, <hulliger@puzzle.ch>
 * @version 1.0
 * @since 18.02.2013
 * @documentation This annotation defines the annotated class as a use case in
 *                an application. Use Cases have a unique ID and a name.
 *                Furthermore they can be references by {@link TestCase}s. The {@link UseCase}
 *                annotation can only be placed on types.
 */
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface UseCase {

	/**
	 * @return the unique ID of the use case.
	 */
	public String id();

	/**
	 * @return the name of the use case.
	 */
	public String name();

}
