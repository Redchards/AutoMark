package gui.auto.mark;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

/**
 * Simple utility class used to display an error pop window.
 */
public class ErrorDisplay {
	public static void displayError(String heading, String content) {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Error");
		alert.setHeaderText(heading);
		alert.setContentText(content);
		
		alert.showAndWait();
	}
}
