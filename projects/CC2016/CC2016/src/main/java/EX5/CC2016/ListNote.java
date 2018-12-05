package EX5.CC2016;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ListNote {

	Map<String,List<Note>> map = new HashMap<>();
	
	
	
	/**
	* ajoute une note d'une valeur et d'un coefficient donné pour un étudiant donné
	* @param student l'étudiant concerné
	* @param valeur la valeur de la note entre 0 et 20
	* @param coeff le coefficient de la note entre 1 et 10
	*/
	public void add(String student, double valeur, double coeff){
		if(!(map.containsKey(student))) {
			map.put(student, new ArrayList<Note>());
		}
		map.get(student).add(new Note(valeur,coeff));
	}
	
	/**
	* rend la moyenne des notes d'un étudiant
	* @param student l'étudiant pour lequel on veut la moyenne
	* @return la Note correspondant à cette moyenne
	* @throws UnknownStudentException si l'étudiant n'a pas de note
	*/
	public Note getMoyenne(String student) throws UnknownStudentException{
		if(!(map.containsKey(student))) {
			throw new UnknownStudentException("L'etudiant :"+ student +" n'existe pas");
		}
		return UtilNote.getMoyennePonderee(map.get(student));
	}

}
