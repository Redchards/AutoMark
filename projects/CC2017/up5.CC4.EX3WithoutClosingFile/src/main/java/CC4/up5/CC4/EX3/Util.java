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

	
	public static String getDateLisible(String dateAAAAMMJJ) throws IllegalArgumentException {
		if (dateAAAAMMJJ.length()!=8)
			throw new IllegalArgumentException("Le nombre de caractères doit être de 8 mais il est de " + dateAAAAMMJJ.length());		
		return dateAAAAMMJJ.substring(6,8)+"/"+dateAAAAMMJJ.substring(0,4)+"/"+dateAAAAMMJJ.substring(4,6);
	}
}
