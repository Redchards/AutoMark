package gui.auto.mark;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class AboutWindowOpener {
	public static void open(Stage primaryStage) throws IOException {
		FXMLLoader fxmlLoader = new FXMLLoader(SettingsWindowOpener.class.getResource("/aboutScene.fxml"));
		Parent root = fxmlLoader.load();
		
		Scene scene = new Scene(root);
		
		Stage stage = new Stage();
		stage.initModality(Modality.WINDOW_MODAL);
		stage.initOwner(primaryStage);
		stage.getIcons().add(new Image(SettingsWindowOpener.class.getResourceAsStream( "/icon.png" ))); 
		stage.setTitle("About AutoMark");
		stage.setScene(scene);
		stage.show();
	}
}
