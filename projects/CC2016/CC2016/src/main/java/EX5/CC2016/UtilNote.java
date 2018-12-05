package EX5.CC2016;

import java.util.List;

/**
 * Hello world!
 *
 */
public class UtilNote 
{
	/**
	 * Method qui calcul la moyenne d'une liste de Note et retoure une note moyenne
	 * sous la forme d'un objet Note
	 * @return Note moyenne
	 */
	public static Note getMoyennePonderee (List<Note> list) {
		double somme=0,coeff=0;
		for (Note note:list){
			somme+=note.getValeur()*note.getCoeff();
			coeff+=note.getCoeff();
		}
		return new Note(somme/coeff,coeff);
	}
}
