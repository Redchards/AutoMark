package gui.auto.mark;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class App extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO : Test if stream is not null (i.e, if resource is present).
		// Otherwise, issue a warning.
		SettingsConfiguration config = new SettingsConfiguration();
		
		config.addSettings("SonarQube", "/sonarSettings.fxml");
		config.addSettings("Others", "/otherSettings.fxml");

		FXMLLoader loader = new FXMLLoader(getClass().getResource("/mainScene.fxml"));
		Parent root = loader.load();
		MainController controller = loader.getController();
		
		controller.setSettingsConfiguration(config);
		controller.setStage(primaryStage);
		primaryStage.getIcons().add(new Image(getClass().getResourceAsStream( "/icon.png" ))); 
		
		Scene scene = new Scene(root);
	
		primaryStage.setTitle("AutoMark");
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	
	public static void main(String[] args) {
		launch(args);
	}

}
