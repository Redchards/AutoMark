package CC4.up5.CC4.EX3;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;

public class MessagePane extends BorderPane {
	private File messagesDir;
	public MessagePane(TextField console,File dir) {
		this.messagesDir=dir;
		Label labelNombreEnAttente = new Label();
		labelNombreEnAttente.setText(this.getNbFiles()+ " messages à lire");
		TextArea messages = new TextArea();
		Button buttonRecupereUnMessage = new Button("lire un message");
		this.setTop(labelNombreEnAttente);
		this.setCenter(messages);
		this.setBottom(buttonRecupereUnMessage);
		buttonRecupereUnMessage.setOnAction(e->{
			try{
				messages.appendText(getMessage(messagesDir).toString());
			}catch(IOException ex) {
				console.setText("Pas de nouveau message");
			}
		
		});
		
		
		Thread thread =new Thread(() -> {
			int nb=this.getNbFiles();
			while(true) {
				if(!(nb==this.getNbFiles())) {
					nb=this.getNbFiles();
					Platform.runLater( () ->{
						labelNombreEnAttente.setText(this.getNbFiles()+ " messages à lire");
					});
				}
			}
		});
		thread.setDaemon(true);
		thread.start();;

	}
	public int getNbFiles(){
		return messagesDir.listFiles().length;
	}
	
	public static Message getMessage(File messagesDir) throws IOException{
		File [] filesTab = messagesDir.listFiles();
		Arrays.sort(filesTab);
		File file = filesTab[0];
		String fileName=file.getName();
		ArrayList<String> fileContent=new ArrayList<String>(getContentFromFile(file));
		filesTab[0].delete();
		return new Message(fileContent.get(0),Util.getDateLisible(fileName.substring(0, 7)),Util.getStringFromList(fileContent, 1).replace("\r","").replace("\n",""));
	}
	
	
	public static List<String> getContentFromFile(File file) throws IOException{
			BufferedReader br = new BufferedReader(new FileReader(file));
			return Util.getContentFrom(br);
	}
}