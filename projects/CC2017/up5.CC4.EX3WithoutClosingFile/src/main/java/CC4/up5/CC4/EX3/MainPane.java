package CC4.up5.CC4.EX3;

import java.io.File;

import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;

public class MainPane extends BorderPane {
	public MainPane(File messagesDir){
		TextField console = new TextField();
		console.setPrefColumnCount(50);
		CalcPane calcPane=new CalcPane(console);
		MessagePane messagePane=new MessagePane(console,messagesDir);
		this.setTop(calcPane);
		this.setCenter(messagePane);
		this.setBottom(console);
	}
}