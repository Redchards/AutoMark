package EX5.CC2016;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ListNote {

	Map<String,List<Note>> map = new HashMap<>();
	
	
	

	public void add(String student, double valeur, double coeff){
		if(!(map.containsKey(student))) {
			map.put(student, new ArrayList<Note>());
		}
		map.get(student).add(new Note(valeur,coeff));
	}

	public Note getMoyenne(String student) throws UnknownStudentException{
		if(!(map.containsKey(student))) {
			throw new UnknownStudentException("L'etudiant :"+ student +" n'existe pas");
		}
		return UtilNote.getMoyennePonderee(map.get(student));
	}

}
