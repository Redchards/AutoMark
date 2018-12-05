package cmd.auto.mark;

import org.apache.logging.log4j.Logger;

import com.auto.mark.ProgressHandlerInterface;

import org.apache.logging.log4j.LogManager;

/**
 * A progress handler using a textual interface to keep track of a {@link PDFWriter} progress.
 * @see ProgressHandlerInterface
 * @see CorrectionProcessManager
 */
public class TextualProgressHandler implements ProgressHandlerInterface {
	private static final Logger progressLogger = LogManager.getLogger(TextualProgressHandler.class);
	
	private static final String DEFAULT_PROGRESS_BAR = "[--------------------]";
	private static final float PROGRESS_BAR_SIZE = 20f;
	private static final float FILL_UNIT_VALUE = 100f / PROGRESS_BAR_SIZE;
	private static final char FILL_CHAR = '#';
	
	int sampleCount;
	float currentPercentage;
	int currentSampleCount;
	String currentProgressBar;
	int fillCount;
	String additionalMessage;
	
	boolean empty;
	
	/**
	 * Create a new progress handler with a sample count equal to 0.
	 * You should use {@link #setSampleCount(int)} prior to use it. Otherwise, the behaviour is undefined.
	 */
	public TextualProgressHandler() {
		this(0);
	}
	
	/**
	 * Create a new progress handler, using a sample count.
	 * @param sampleCount the number of operations ({@link #update()}) to make the complete the task.
	 */
	public TextualProgressHandler(int sampleCount) {
		if(sampleCount > 1) {
			this.sampleCount = sampleCount;
		}
		else {
			this.sampleCount = 1;
		}
		this.currentPercentage = 0.0f;
		this.currentSampleCount = 0;
		this.currentProgressBar = "[";
		this.fillCount = 0;
		this.empty = true;
		this.additionalMessage = "";
	}
	
	/**
	 * Signal that we completed one operation.
	 */
	@Override
	public void update() {
		if(empty) {
			refresh();
			empty = false;
			return;
		}
		
		final float previousPercentage = currentPercentage;
		final float sample = 100f / sampleCount;
		currentSampleCount++;
		currentPercentage += sample;
		
		if(currentSampleCount == sampleCount) {
			currentPercentage = 100.0f;
		}
		
		if(previousPercentage >= 100f) {
			progressLogger.info("\n");
		}
		else if(currentPercentage >= ((fillCount + 1) * FILL_UNIT_VALUE)) {
			int unitToAdd = (int)((currentPercentage - (fillCount * FILL_UNIT_VALUE)) / FILL_UNIT_VALUE);
			fillCount += unitToAdd;
			currentProgressBar += new String(new char[unitToAdd]).replace('\0', FILL_CHAR);
			
			refresh();
		}
	}
	
	/**
	 * End the progress handler, performing some operations, then resetting it.
	 */
	@Override
	public void end() {
		progressLogger.info("\n");
		reset();
	}
	
	/**
	 * Reset the progress handler.
	 */
	@Override
	public void reset() {
		currentPercentage = 0;
		currentSampleCount = 0;
		currentProgressBar = "[";
		fillCount = 0;
		empty = true;
	}
	
	/**
	 * Refresh the progress handler display.
	 */
	@Override
	public void refresh() {
		String percentageStr = String.valueOf((int)Math.floor(currentPercentage));
		String additionalSpaces = new String(new char[4 - percentageStr.length()]).replace('\0', ' ');
		
		progressLogger.info(additionalMessage + percentageStr + "%" + additionalSpaces + DEFAULT_PROGRESS_BAR);
		if(currentPercentage > 0.0f) {
			progressLogger.info(additionalMessage + percentageStr + "%" + additionalSpaces + currentProgressBar);
		}
	}
	
	public void setAdditionalMessage(String msg) {
		additionalMessage = msg;
	}
	
	public String getAdditionalMessage() {
		return additionalMessage;
	}
	
	/**
	 * Update the sample count.
	 * @param sampleCount the new sample count.
	 */
	@Override
	public void setSampleCount(int sampleCount) {
		this.sampleCount = sampleCount;
	}
	
	/**
	 * @return the current sample count.
	 */
	@Override
	public int getSampleCount() {
		return sampleCount;
	}
}
