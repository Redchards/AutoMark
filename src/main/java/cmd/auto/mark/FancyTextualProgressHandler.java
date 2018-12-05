package cmd.auto.mark;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * A textual progress handler with a nice loading indicator.
 */
public class FancyTextualProgressHandler extends TextualProgressHandler {

	Thread waitThread = null;
	WaitIndicator waitRunnable = null;
	
	private static final Logger errorLog = LogManager.getLogger("errorLog");
	
	public FancyTextualProgressHandler() {
		super();
		setAdditionalMessage("  ");
	}
	
	/**
	 * Create a new progress handler, using a sample count.
	 * @param sampleCount the number of operations ({@link #update()}) to make the complete the task.
	 */
	public FancyTextualProgressHandler(int sampleCount) {
		super(sampleCount);
		setAdditionalMessage("  ");
	}
	
	/**
	 * Signal that we completed one operation.
	 */
	@Override
	public void update() {
		super.update();
		
		if(currentPercentage <= 0.0f) {
			startWaitThread();
		}
	}
	
	/**
	 * End the progress handler, performing some operations, then resetting it.
	 */
	@Override
	public void end() {
		try {
			if(waitRunnable != null && waitRunnable.isRunning()) {
				stopWaitThread();
			}
		} catch (TextualProgressHandlerException e) {
			errorLog.error(e);
		}
		super.end();
	}
	
	/**
	 * Reset the progress handler.
	 */
	@Override
	public void reset() {
		try {
			if(waitRunnable != null && waitRunnable.isRunning()) {
				stopWaitThread();
			}
		} catch (TextualProgressHandlerException e) {
			errorLog.error(e);
		}
		super.reset();
	}
	
	/*
	 * Start the wait thread (show the wait indicator).
	 */
	private void startWaitThread() {
		waitRunnable = new WaitIndicator();
		waitThread = new Thread(waitRunnable);
		waitThread.setDaemon(true);
		waitThread.start();
	}
	
	/**
	 * Stop the wait thread (hide the wait indicator).
	 * @throws TextualProgressHandlerException 
	 */
	private void stopWaitThread() throws TextualProgressHandlerException {
		if(waitThread != null) {
			try {
				waitRunnable.terminate();
				waitThread.join();
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
				throw new TextualProgressHandlerException(e);
			}
		}
	}
	
	/*
	 * The runnable class used to show the wait indicator.
	 */
	private class WaitIndicator implements Runnable {

		private int currentState;
		private volatile boolean running;
		
		private final char[] states = new char[]{
			'-',
			'\\',
			'|',
			'/',
		};
		
		private final Logger waitLog = LogManager.getLogger(TextualProgressHandler.class);
		
		/*
		 * Create the wait indicator, ready to start.
		 */
		public WaitIndicator() {
			currentState = 0;
			this.running = true;
		}
		
		/*
		 * Set the running flag to false to stop the runnable.
		 */
		public void terminate() {
			this.running = false;
		}
		
		/*
		 * @return Is the thread running ? 
		 */
		public boolean isRunning() {
			return this.running;
		}
		
		/*
		 * The method used to display our loader.
		 * @see java.lang.Runnable#run()
		 */
		@Override
		public void run() {
			try {
				while(running) {
					waitLog.info(states[currentState]);
					currentState++;
					
					if(currentState >= states.length) {
						currentState = 0;
					}
					Thread.sleep(100);
				}
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
				errorLog.error(new TextualProgressHandlerException(e));
			}
		}
		
	}
	
}
