package CC4.up5.CC4.EX3;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.*;

public class Util {
	public static String getStringFromList(List<String> content, int from) {
		String s="";
		for(int i=from;i<content.size();i++) {
			s+=content.get(i)+"\n";
		}
		return s;
	}
	
	public static List<String> getContentFrom(BufferedReader br) throws IOException{
		if(!(br.ready())) {
			throw new IOException("empty buffer");
		}
		ArrayList<String> content=new ArrayList<String>();
		while(br.ready()) {
				content.add(br.readLine());
		}
			
		return content;
		
		
	}
	/**
	* étant donnée une chaine de 8 caractères de la forme AAAAMMJJ, rend une chaine de
	la forme JJ/MM/AAAA
	* exemple : si la chaine fourni est 20161223, le résultat est 23/12/2016
	* @param dateAAAAMMJJ une date sous la forme AAAAMMJJ
	* @return une date sous la forme JJ/MM/AAAA
	* @throws IllegalArgumentException si la chaine n'est pas composée de 8 caractères
	(aucune autre vérification n'est effectuée)
	*/
	public static String getDateLisible(String dateAAAAMMJJ) throws IllegalArgumentException {
		if (dateAAAAMMJJ.length()!=8)
			throw new IllegalArgumentException("Le nombre de caractères doit être de 8 mais il est de " + dateAAAAMMJJ.length());
		
		return dateAAAAMMJJ.substring(6,8)+"/"+dateAAAAMMJJ.substring(4,6)+"/"+dateAAAAMMJJ.substring(0,4);
	}
}
