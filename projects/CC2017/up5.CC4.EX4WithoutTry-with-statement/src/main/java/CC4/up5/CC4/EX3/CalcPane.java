package CC4.up5.CC4.EX3;


import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;

public class CalcPane extends FlowPane {

	// pour info :
	//La méthode statique parseInt de la classe Integer prend en parametre une chaine et  renvoie l'entier correspondant à cette chaine.
	//Si la chaine ne représente pas un entier, elle lance une NumberFormatException
	/**
	* retourne la somme de deux entiers fournis sous forme de chaine de caractères
	* @param s1 une chaine représentant un entier
	* @param s2 une autre chaine représentant un entier
	* @return la somme des 2 entiers
	* @throws NumberFormatException si l'une des deux chaines ne represente pas un entier
	*/
	public static int getSomme(String s1,String s2) throws NumberFormatException {
		int somme;
		try {
			somme=Integer.parseInt(s1)+Integer.parseInt(s2);
		} catch(NumberFormatException e) {
			throw new NumberFormatException("s1 or s2 does not represent a number");
		}
		return somme;
		
	}
	
	
	public CalcPane(TextField console) {
		TextField operGauche=new TextField();
		operGauche.setPrefColumnCount(5);
		Label labelOper=new Label("+");
		TextField operDroite=new TextField();
		operDroite.setPrefColumnCount(5);
		Button button = new Button("=");
		Label labelRes=new Label("....");
		this.getChildren().addAll(operGauche,labelOper,operDroite,button,labelRes);
		button.setOnAction(e-> {
			try{
				labelRes.setText(""+getSomme(operGauche.getText(),operDroite.getText()));
			} catch(NumberFormatException ex) {
				console.setText("Erreur :opération impossible");
			}
			
		});
	}
	
	
}
