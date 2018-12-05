package EX5.CC2016;
public class Util{
/**
* rend la somme des premiers entiers jusqu'à un nombre fourni
* @param limite l'entier le plus grand concerné
* @return la somme des premiers entiers de 1 à l'entier 'limite'
*/
	public static int getSommeDesPremiersEntiers(int Limite){
		int Somme=0;
		for(int i=1;i<=Limite;i++)
			Somme+=i;
		return Somme;
	}
}
