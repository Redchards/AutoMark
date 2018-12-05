package CC4.up5.CC4.EX3;

public class Message {
	private String expediteur,date,texte;
	public Message(String expediteur, String date, String texte) {
		this.expediteur = expediteur;
		this.date = date;
		this.texte = texte;
	}
	public String getExpediteur() {
		return expediteur;	
	}
	public String getDate() {
		return date;
	}
	public String getTexte() {
		return texte;
	}
	public String toString(){
		String sep = "\n-------------------\n";
		return sep+date+" de la part de "+expediteur+"\n"+texte+"\n";
	}
}

