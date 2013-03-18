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
 * @documentation A testcase defines one scenario to test a {@link UseCase},
 *                which must always be referenced in the annotation. The {@link TestCase} annotation
 *                can only be placed on Junit
 *                Test-Methods.
 */
@Target({ ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
public @interface TestCase {

	/**
	 * @return the referenced {@link UseCase} class.
	 */
	public Class<?> useCase();

	/**
	 * @return the unique ID of the testcase
	 */
	public String id();

	/**
	 * @return the name of the testCase
	 */
	public String name();

}
