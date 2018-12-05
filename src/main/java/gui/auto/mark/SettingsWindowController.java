package gui.auto.mark;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class SettingsWindowController {
	@FXML
	private ListView<String> settingsList;
	
	@FXML
	private AnchorPane settingsDisplayZone;
	
	@FXML
	private Button applyButton;
	
	@FXML
	private Button okButton;
	
	private SettingsConfiguration settings;
	
	private ISettingsController currentController;
	
	private Logger errorLog = LogManager.getLogger("errorLog");
	
	private boolean savingScheduled;
	
	public SettingsWindowController() {
		settings = null;
		currentController = null;
		savingScheduled = false;
	}
	
	@FXML
	public void initialize() {
		settingsList.setOnMouseClicked((event) -> {
			String settingsCategory = settingsList.getSelectionModel().getSelectedItem();

			FXMLLoader loader = new FXMLLoader(getClass().getResource(settings.getSettingsFXMLFile(settingsCategory)));
			try {
				if(currentController != null && currentController.wasModified()) {
					askForSaveChange();
					
					if(savingScheduled) {
						currentController.save();
						savingScheduled = false;
					}
				}
				settingsDisplayZone.getChildren().setAll(loader.<Node>load());
				currentController = loader.getController();
			} catch (IOException e) {
				errorLog.error(e);
				Platform.runLater(() -> ErrorDisplay.displayError("Settings error", e.getMessage()));			}
		});
		
		applyButton.setOnAction((event) -> {
			if(currentController != null) {
				currentController.save();
			}
		});
		
		okButton.setOnAction((event) -> {
			if(currentController != null) {
				currentController.save();
			}
			
			 Stage stage = (Stage) okButton.getScene().getWindow();
			 stage.close();
		});
	}
	
	public void askForSaveChange() {
		ButtonType saveButton = new ButtonType("save");
		ButtonType discardButton = new ButtonType("discard");
		
		Alert alert = new Alert(AlertType.INFORMATION, "Change detected", saveButton, discardButton);
		alert.setTitle("Save");
		alert.setHeaderText("The settings were modified !");
		alert.setContentText("Do you want to save them ?");
		alert.showAndWait().ifPresent(response -> {
			savingScheduled = response == saveButton;
		});
	}
	
	public void setSettingsConfiguration(SettingsConfiguration settings) {
		settingsList.setItems(FXCollections.observableArrayList(settings.getAllSettingsNames()));
		this.settings = settings;
	}
}
