package com.auto.mark;

/**
 * Common interface for progress handlers.
 * @see cmd.auto.mark.TextualProgressHandler
 * @see cmd.auto.mark.FancyTextualProgressHandler
 * @see gui.auto.mark.GuiProgressHandler
 */
public interface ProgressHandlerInterface {
	public void update();
	public void reset();
	public void end();
	public void refresh();
	public void setSampleCount(int sampleCount);
	public int getSampleCount();
}
