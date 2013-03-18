package ${groupId}.util.screenshots;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

import ${groupId}.usecases.BaseUseCaseTest;
import ${groupId}.util.doc.TestCase;
import ${groupId}.util.doc.generation.SitePropertyUtils;

/**
 * @author Brigitte Hulliger, <hulliger@puzzle.ch>
 * 
 */
@Aspect
public class ScreenshotAspect {

	@Pointcut("execution(public * org.openqa.selenium.WebElement.submit())	|| execution(public * org.openqa.selenium.WebElement.click())")
	public void submitOrClick() {
		// pointcut method.
	}

	@After("submitOrClick()")
	public void submitOrClickAfter(final JoinPoint thisJoinPoint) throws Throwable {
		((Screenshotable) thisJoinPoint.getThis()).takeScreenshot(this.getFilePath());
	}

	@Before("submitOrClick()")
	public void submitOrClickBefore(final JoinPoint thisJoinPoint) throws Throwable {
		((Screenshotable) thisJoinPoint.getThis()).takeScreenshot(this.getFilePath());
	}

	/**
	 * @param testCase
	 * @return the path where to put the screenshot
	 */
	private String getFilePath() {
		TestCase testCase = BaseUseCaseTest.currentTestCase;
		String screenshotDirectory = SitePropertyUtils.getPropertyValue("site.resources.output.screenshots");
		final StringBuilder sb = new StringBuilder(screenshotDirectory);
		if (testCase != null) {
			sb.append(testCase.id()).append("/");
			sb.append(testCase.useCase().getSimpleName());
			sb.append("_");
			sb.append(testCase.id());
			sb.append("-");
		}
		sb.append(new SimpleDateFormat("yyyyMMdd-HHmmss_S").format(new Date()));

		sb.append(".png");
		System.out.println(sb.toString());
		return sb.toString();
	}

}
