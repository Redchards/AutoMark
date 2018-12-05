package TestD.up5;

import java.util.ArrayList;
import java.util.Arrays;


public class Tri {
		

	public static String[] triSelec(String[] s) {
		for(int i=1;i<=s.length-1;i++) {
			int indiceDep =i-1;
			int minimum= indiceDuPlusPetit(s,indiceDep,s.length-1);
			echange(s,indiceDep,minimum);
		}
		return s;
	}
	
	public static int indiceDuPlusPetit(String[] s, int imin, int imax) {
		int indice=imin;
		for(int i=indice+1;i<=imax;i++) {
			if(s[i].charAt(0)<s[indice].charAt(0)) {
				indice=i;
			}
		}
		
		return indice;
		
	}
	
	public static String[] echange(String[] s, int x,int y) {
		String temp=s[x];
		s[x]=s[y];
		s[y]=temp;
		return s;
	}
	
	
}
	
	