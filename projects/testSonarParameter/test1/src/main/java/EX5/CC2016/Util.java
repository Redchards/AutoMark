package EX5.CC2016;



public class Util{
/**
* rend la somme des premiers entiers jusqu'à un nombre fourni
* @param limite l'entier le plus grand concerné
* @return la somme des premiers entiers de 1 à l'entier 'limite'
*/
	public static int getSommeDesPremiersEntiers(int limite){
		int somme=0;
		for(int i=1;i<=limite;i++)
			somme+=i;
		return somme;
	}
}
