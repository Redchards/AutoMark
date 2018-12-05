package gui.auto.mark;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class SettingsWindowOpener {
	public static void open(SettingsConfiguration settingsConfiguration, Stage primaryStage) throws IOException {
		FXMLLoader fxmlLoader = new FXMLLoader(SettingsWindowOpener.class.getResource("/settingsWindowScene.fxml"));
		Parent root = fxmlLoader.load();
		SettingsWindowController controller = fxmlLoader.getController();
		controller.setSettingsConfiguration(settingsConfiguration);
		
		Scene scene = new Scene(root);
		
		Stage stage = new Stage();
		stage.initModality(Modality.WINDOW_MODAL);
		stage.initOwner(primaryStage);
		stage.getIcons().add(new Image(SettingsWindowOpener.class.getResourceAsStream( "/icon.png" ))); 
		stage.setTitle("Settings");
		stage.setScene(scene);
		stage.show();
	}
}
