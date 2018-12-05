package EX5.CC2016;

public class Util{
/**
* rend la somme des premiers entiers jusqu'à un nombre fourni
* @param limite l'entier le plus grand concerné
* @return la somme des premiers entiers de 1 à l'entier 'limite'
*/
	public static int GetSommeDesPremiersEntiers(int Limite){
		int Somme=0;
		int Im=0;
		for(Im=1;Im<=Limite;Im++)
			Somme+=Im;
		return Somme;
	}
}
