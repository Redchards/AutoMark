package gui.auto.mark;

import java.io.File;
import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.auto.mark.AssignmentCompilationFailure;
import com.auto.mark.CorrectionProcessManager;
import com.auto.mark.IApplicationConfiguration;
import com.auto.mark.InvalidPathException;
import com.auto.mark.utils.CommonDefaultConfigurationValues;
import com.auto.mark.utils.VerboseLevel;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextField;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;

/**
 * TODO : Use a configuration file for the sonar configuration and the marking parameters path.
 * @author Loic
 *
 */
public class MainController implements IApplicationConfiguration {
	private static String MENU_WINDOWS_LABEL = "Windows";
	private static String MENU_SETTINGS_LABEL = "Settings";
	private static String MENU_HELP_LABEL = "Help";
	private static String MENU_ABOUT_LABEL = "About";
	private static String MENU_FILE_LABEL = "File";
	private static String MENU_OPEN_PROJECTS_DIR_LABEL = "Open projects directory";
	private static String MENU_OPEN_TESTS_DIR_LABEL = "Open tests directory";
	private static String MENU_OPEN_MARKING_PARAMETERS_LABEL = "Open marking parameters file";
	
	private SettingsConfiguration settingsConfiguration;
	private Stage primaryStage;
	private boolean analysisLaunched;
	
	@FXML 
	private TextField projectsPath;
	
	@FXML
	private TextField testsPath;
	
	@FXML
	private TextField markingParametersPath;
	
	@FXML
	private Button browseProjectsDirButton;
	
	@FXML
	private Button browseTestsDirButton;
	
	@FXML
	private Button browseMarkingParametersButton;
	
	@FXML
	private Button launchButton;
	
	@FXML
	private ProgressBar progressBar;
	
	@FXML
	private ProgressIndicator progressIndicator;
	
	@FXML
	private GuiProgressHandler progressHandler;
	
	@FXML
	private MenuBar menuBar;
	
	private Logger errorLog = LogManager.getLogger("errorLog");
	
	public MainController() {
		settingsConfiguration = new SettingsConfiguration();
		analysisLaunched = false;
	}
	
	@FXML
	public void initialize() {
		progressHandler = new GuiProgressHandler(progressBar, progressIndicator);

		EventHandler<ActionEvent> browseProjectsDirHandler = (event) -> {
			DirectoryChooser chooser = new DirectoryChooser();
			chooser.setTitle("Browse project directory");
			chooser.setInitialDirectory(new File(System.getProperty("user.dir")));
			
			File result = chooser.showDialog(browseProjectsDirButton.getScene().getWindow());
			
			if(result != null) {
				projectsPath.setText(result.toString());
			}
		};
		
		EventHandler<ActionEvent> browseTestsDirHandler = (event) -> {
			DirectoryChooser chooser = new DirectoryChooser();
			chooser.setTitle("Browse test directory");
			chooser.setInitialDirectory(new File(System.getProperty("user.dir")));
			
			File result = chooser.showDialog(browseTestsDirButton.getScene().getWindow());
			
			if(result != null) {
				testsPath.setText(result.toString());
			}
		};
		
		EventHandler<ActionEvent> browseMarkingParametersHandler = (event) -> {
			FileChooser chooser = new FileChooser();
			chooser.setTitle("Browse marking parameters file");
			chooser.setInitialDirectory(new File(System.getProperty("user.dir")));
			chooser.getExtensionFilters().addAll(
			         new ExtensionFilter("CSV Files", "*.csv"));
			
			File result = chooser.showOpenDialog(browseTestsDirButton.getScene().getWindow());
			
			if(result != null) {
				markingParametersPath.setText(result.toString());
			}
		};
		
		browseProjectsDirButton.setOnAction(browseProjectsDirHandler);
		
		browseTestsDirButton.setOnAction(browseTestsDirHandler);
		
		browseMarkingParametersButton.setOnAction(browseMarkingParametersHandler);
		
		launchButton.setOnAction((event) -> {
			launchAnalysis();
		});
		
		ObservableList<Menu> menuList = menuBar.getMenus();
		
		// Very ugly, need to be more generic !!!
		for(Menu menu : menuList) {
			if(menu.getText().equals(MENU_WINDOWS_LABEL)) {
				for(MenuItem item : menu.getItems()) {
					if(item.getText().equals(MENU_SETTINGS_LABEL)) {
						item.setOnAction((event) -> {
							try {
								SettingsWindowOpener.open(settingsConfiguration, primaryStage);
							} catch (IOException e) {
								errorLog.error(e);
								Platform.runLater(() -> ErrorDisplay.displayError("Analysis failed", e.getMessage()));
							}
						});
					}
				}
			}
			else if(menu.getText().equals(MENU_HELP_LABEL)) {
				for(MenuItem item : menu.getItems()) {
					if(item.getText().equals(MENU_ABOUT_LABEL)) {
						item.setOnAction((event) -> {
							try {
								AboutWindowOpener.open(primaryStage);
							} catch (IOException e) {
								errorLog.error(e);
								Platform.runLater(() -> ErrorDisplay.displayError("Analysis failed", e.getMessage()));
							}
						});
					}
				}
			}
			else if(menu.getText().equals(MENU_FILE_LABEL)) {
				for(MenuItem item : menu.getItems()) {
					if(item.getText().equals(MENU_OPEN_PROJECTS_DIR_LABEL)) {
						item.setOnAction(browseProjectsDirHandler);
					}
					else if(item.getText().equals(MENU_OPEN_TESTS_DIR_LABEL)) {
						item.setOnAction(browseTestsDirHandler);
					}
					else if(item.getText().equals(MENU_OPEN_MARKING_PARAMETERS_LABEL)) {
						item.setOnAction(browseMarkingParametersHandler);
					}
				}
			}
		}
	}
	
	private void launchAnalysis() {
		if(analysisLaunched) {
			return;
		}
		
		new Thread(() -> {
			try {
				analysisLaunched = true;
				Platform.runLater(new Runnable() {
				    @Override
				    public void run() {
				        primaryStage.getScene().setCursor(Cursor.WAIT);
				    }
				});

				if(projectsPath.getText().isEmpty()) {
					throw new DirectoryNotSetException("projects");
				}
				if(testsPath.getText().isEmpty()) {
					throw new DirectoryNotSetException("projects");
				}
				if(!getProjectsDir().exists()) {
					throw new InvalidPathException("the projects directory is invalid");
				}
				if(!getTestsDir().exists()) {
					throw new InvalidPathException("the tests directory is invalid");
				}
				// TODO : Improve to check if there is Java files.
				if(getTestsDir().listFiles().length == 0) {
					throw new EmptyDirectoryException("the tests directory is empty !");
				}
				
		 		CorrectionProcessManager manager = new CorrectionProcessManager(this, progressHandler);
		 		manager.execute();
				
				Platform.runLater(() -> {
					Alert alert = new Alert(AlertType.INFORMATION);
					alert.setTitle("Finished !");
					alert.setHeaderText("Everything processed");
					alert.setContentText("Find your results in " + getOutputDirectory().getAbsolutePath());
					alert.showAndWait();
				});
			}
			catch (DirectoryNotSetException | AssignmentCompilationFailure | InvalidPathException | EmptyDirectoryException e) {
				errorLog.error(e);
				Platform.runLater(() -> ErrorDisplay.displayError("Analysis failed", e.getMessage()));
			}
			finally {
				Platform.runLater(new Runnable() {
				    @Override
				    public void run() {
				        primaryStage.getScene().setCursor(Cursor.DEFAULT);
				    }
				});
				analysisLaunched = false;
			}

		}).start();
	}

	@Override
	public File getProjectsDir() {
		return new File(projectsPath.getText());
	}

	@Override
	public File getTestsDir() {
		return new File(testsPath.getText());
	}

	@Override
	public File getPropertiesFile() {
		return new File(CommonDefaultConfigurationValues.getDefaultSonarPropertiesFileName());
	}

	@Override
	public File getMarkingParametersFile() {
		if((markingParametersPath.getText() != null && markingParametersPath.getText().isEmpty())) {
			return new File(CommonDefaultConfigurationValues.getDefaultMarkingParametersFileName());
		}
		else {
			return new File(markingParametersPath.getText());
		}
	}

	@Override
	public boolean isGui() {
		return true;
	}

	@Override
	public File getOutputDirectory() {
		return new File(CommonDefaultConfigurationValues.getDefaultOutputDirectoryPath());
	}
	
	@Override
	// TODO : Should be configurable
	public VerboseLevel getVerboseLevel() {
		return VerboseLevel.MINIMAL;
	}
	
	public void setSettingsConfiguration(SettingsConfiguration config) {
		settingsConfiguration = config;
	}
	
	public void setStage(Stage primaryStage) {
		this.primaryStage = primaryStage;
	}
}
