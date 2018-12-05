package gui.auto.mark;

import com.auto.mark.ProgressHandlerInterface;

import javafx.application.Platform;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ProgressIndicator;

public class GuiProgressHandler implements ProgressHandlerInterface {

	private int sampleCount;
	private int currentSampleCount;
	private float sampleSize;
	private ProgressBar progressBar;
	private ProgressIndicator progressIndicator;
	
	public GuiProgressHandler(ProgressBar progressBar, ProgressIndicator progressIndicator) {
		this(0, progressBar, progressIndicator);
	}
	
	public GuiProgressHandler(int sampleCount, ProgressBar progressBar, ProgressIndicator progressIndicator) {
		setSampleCount(sampleCount);
		this.progressBar = progressBar;
		this.progressIndicator = progressIndicator;
		Platform.runLater(() -> {
			progressIndicator.setProgress(-1.0);
			progressIndicator.setVisible(false);
		});
	}
	
	@Override
	public void update() {
		float currentPercentage;
		
		if(!progressIndicator.isVisible()) {
			Platform.runLater(() -> {
				progressIndicator.setVisible(true);
			});
		}
		
		if(currentSampleCount == sampleCount) {
			currentPercentage = 1.0f;
		}
		else {
			currentPercentage = currentSampleCount * sampleSize;
		}
		
		Platform.runLater(() -> {
			setProgress(currentPercentage);
			if(currentPercentage >= 1.0f) {
				progressIndicator.setProgress(1.0f);
			}
		});
		currentSampleCount++;
	}

	@Override
	public void reset() {
		currentSampleCount = 0;
		
		Platform.runLater(() -> {
			setProgress(0.0f);
			progressIndicator.setVisible(false);
			progressIndicator.setProgress(-1.0);
		});
	}

	@Override
	public void end() {
		reset();
	}

	@Override
	public void refresh() {}

	@Override
	public void setSampleCount(int sampleCount) {
		this.sampleCount = sampleCount;
		
		if(sampleCount != 0) {
			sampleSize = 1.0f / sampleCount;
		}
	}

	@Override
	public int getSampleCount() {
		return sampleCount;
	}
	
	private void setProgress(float progress) {
		progressBar.setProgress(progress);
	}

}
