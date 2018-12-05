package up5.cadrage;
/**
 * une classe spécialisée dans le cadrage de chaîne de caractères
 * @author Yannick Parchemal
 *
 */
public class Cadrage {

	private char remplissage;

	public Cadrage(char remplissage){
		this.remplissage=remplissage;
	}

	/**
	 * rend une chaine d'une taille donnée dont la partie gauche est la chaine passée en parametre et le complement à droite est composé de caractères de remplissage
	 * @param chaine la chaine à cadrer
	 * @param size la taille de la chaine résultante
	 * @return une chaine de taille size dont la partie gauche est la chaine passée en parametre et le complement à droite est composé de caractères de remplissage
	 */
	public String cadrerAuCentre(String chaine, int size){
		if (chaine.length()>=size)
			return chaine.substring(0,size);
		else {
			StringBuilder chaineRemplissage = new StringBuilder();
			for (int i=0;i<size-chaine.length();i++) chaineRemplissage.append(this.remplissage);
			return chaine+chaineRemplissage;
		}
	}
	
	/**
	 * rend une chaine d'une taille donnée dont la partie droite est la chaine passée en parametre et le complement à gauche est composé de caractères de remplissage
	 * @param chaine la chaine à cadrer
	 * @param size la taille de la chaine résultante
	 * @return une chaine de taille size dont la partie droite est la chaine passée en parametre et le complement à gauche est composé de caractères de remplissage
	 */
	public String cadrerADroite(String chaine, int size){
		if (chaine.length()>=size)
			return chaine.substring(0,size);
		else {
			StringBuilder chaineRemplissage = new StringBuilder();
			for (int i=0;i<size-chaine.length();i++) chaineRemplissage.append(this.remplissage);
			return chaineRemplissage+chaine;
	}
	}

}
