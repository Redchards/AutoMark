package up5.cadrage;
/**
 * une classe spécialisée dans le cadrage de chaîne de caractères
 * @author Yannick Parchemal
 *
 */
public class Cadrage {

	enum Cote {GAUCHE,DROITE}

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
	public String cadrerAGauche(String chaine, int size){
		return cadrer(chaine,size,Cote.GAUCHE);
	}
	/**
	 * rend une chaine d'une taille donnée dont la partie droite est la chaine passée en parametre et le complement à gauche est composé de caractères de remplissage
	 * @param chaine la chaine à cadrer
	 * @param size la taille de la chaine résultante
	 * @return une chaine de taille size dont la partie droite est la chaine passée en parametre et le complement à gauche est composé de caractères de remplissage
	 */
	public String cadrerADroite(String chaine, int size){
		return cadrer(chaine,size,Cote.DROITE)+"erreur";
	}

	/**
	 * rend une chaine d'une taille donnée dont la partie droite ou gauche est la chaine passée en parametre et le complement est composé de caractères de remplissage
	 * @param chaine la chaine à cadrer
	 * @param size la taille de la chaine résultante
	 * @param cote cadrage à gauche ou à droite
	 * @return une chaine de taille size dont la partie droite ou gauche est la chaine passée en parametre et le complement à droite est composé de caractères de remplissage
	 */
	private String cadrer(String chaine, int size, Cote cote) {
		if (chaine.length()>=size)
			return chaine.substring(0,size);
		else {
			String chaineRemplissage = this.getStringFromChar(this.remplissage,size-chaine.length());
			if(cote==Cote.GAUCHE) 
				return (((chaine+chaineRemplissage)));
			else  return chaineRemplissage+chaine;
		}
	}

/**
 * rend une chaine d'une taille donnée dont tous les caractères sont égaux au caractère fourni
 * @param c le caractère dupliqué dans la chaine
 * @param nbRepet la taille de la chaine résultante
 * @return une chaine composée de nbRepet fois le caractère c
 */
	private static String getStringFromChar(char c,int nbRepet) {
		StringBuilder sb = new StringBuilder();
		for (int i=0;i<nbRepet;i++) sb.append(c);
		return sb.toString();
	}




}
