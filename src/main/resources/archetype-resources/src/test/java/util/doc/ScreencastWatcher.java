package ${groupId}.util.doc;

import static ${groupId}.util.TestPropertyUtils.getPropertyValue;

import java.awt.AWTException;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsEnvironment;
import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;
import org.monte.media.Format;
import org.monte.media.FormatKeys;
import org.monte.media.FormatKeys.MediaType;
import org.monte.media.VideoFormatKeys;
import org.monte.media.math.Rational;
import org.monte.screenrecorder.ScreenRecorder;

import ${groupId}.util.TestPropertyUtils;
import ${groupId}.util.doc.generation.SitePropertyUtils;

/**
 * @author Brigitte Hulliger, <hulliger@puzzle.ch>
 * 
 */
public class ScreencastWatcher extends TestWatcher {

	private static final Logger log = Logger.getLogger(ScreencastWatcher.class);

	private ScreenRecorder screenRecorder;

	@Override
	protected void starting(final Description description) {
		if (!"on".equals(getPropertyValue("screencasts"))) { //$NON-NLS-1$//$NON-NLS-2$
			return;
		}
		try {

			// Create a instance of GraphicsConfiguration to get the Graphics
			// configuration
			// of the Screen. This is needed for ScreenRecorder class.
			final GraphicsConfiguration gc = GraphicsEnvironment//
					.getLocalGraphicsEnvironment()//
					.getDefaultScreenDevice()//
					.getDefaultConfiguration();

			final Format fileFormat = new Format(FormatKeys.MediaTypeKey,
					MediaType.FILE, FormatKeys.MimeTypeKey, FormatKeys.MIME_AVI);

			final Format screenFormat = new Format(FormatKeys.MediaTypeKey,
					MediaType.VIDEO, FormatKeys.EncodingKey,
					VideoFormatKeys.ENCODING_AVI_TECHSMITH_SCREEN_CAPTURE,
					VideoFormatKeys.CompressorNameKey,
					VideoFormatKeys.ENCODING_AVI_TECHSMITH_SCREEN_CAPTURE,
					VideoFormatKeys.DepthKey, Integer.valueOf(24),
					FormatKeys.FrameRateKey, Rational.valueOf(15),
					VideoFormatKeys.QualityKey, Float.valueOf(1.0f),
					FormatKeys.KeyFrameIntervalKey, Integer.valueOf(15 * 60));

			final Format mouseFormat = new Format(FormatKeys.MediaTypeKey,
					MediaType.VIDEO, FormatKeys.EncodingKey,
					"black", FormatKeys.FrameRateKey, Rational.valueOf(30)); //$NON-NLS-1$

			final Format audioFormat = null;

			this.screenRecorder = new ScreenRecorder(gc, fileFormat,
					screenFormat, mouseFormat, audioFormat);

			// Call the start method of ScreenRecorder to begin recording
			this.screenRecorder.start();
		} catch (IOException | AWTException e) {
			log.error(e);
		}

		super.starting(description);
	}

	@Override
	protected void finished(final Description description) {

		if (!"on".equals(TestPropertyUtils.getPropertyValue("screencasts"))) { //$NON-NLS-1$ //$NON-NLS-2$
			return;
		}
		String testcaseId = description.getAnnotation(TestCase.class).id();
		// Call the stop method of ScreenRecorder to end the recording
		File destinationFile;
		try {
			this.screenRecorder.stop();
			final String destinationDir = SitePropertyUtils
					.getPropertyValue("site.resources.output.screencasts"); //$NON-NLS-1$
			final File sourceFile = this.screenRecorder.getCreatedMovieFiles()
					.get(0);

			destinationFile = new File(destinationDir + testcaseId + ".avi"); //$NON-NLS-1$

			if (destinationFile.exists()) {
				destinationFile.delete();
			}
			FileUtils.moveFile(sourceFile, destinationFile);
		} catch (final IOException e) {
			log.error("could not move generated screencast.", e); //$NON-NLS-1$
			return;
		}

		// convert to ogg
		try {
			Runtime.getRuntime().exec(
					"ffmpeg -i " + destinationFile.getAbsolutePath() + " " //$NON-NLS-1$ //$NON-NLS-2$
							+ destinationFile.getParentFile().getAbsolutePath()
							+ "/" + testcaseId + ".ogg"); //$NON-NLS-1$ //$NON-NLS-2$

		} catch (final IOException e) {
			log.warn(
					"install ffmpeg if you want to include the screencast in your documentation as video.", //$NON-NLS-1$
					e);
		}

		super.finished(description);
	}
}
