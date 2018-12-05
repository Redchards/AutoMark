package up5.cadrage;

import java.io.IOException;

/**
 * une classe spécialisée dans le cadrage de chaîne de caractères
 * @author Yannick Parchemal
 *
 */
public class Cadrage {

	private char var0;

	public Cadrage(char remplissage){
		this.var0=remplissage;
	}


	public String cadrerAGauche(String var1, int var2){
		int i;		
		if (var1.length()>=var2)
			return var1.substring(0,var2);
		else {
			StringBuilder var3 = new StringBuilder();
			for (i=0;i<var1.length()-var2;i++) var3.append(this.var0);
			return var1+var3;
		}
	}
	
	public String cadrerADroite(String var1, int var2) throws IOException{
		int i;		
		if (var1.length()>=var2)
			return var1.substring(0,var2);
		else {
			StringBuilder var3 = new StringBuilder();
			for (i=0;i<var1.length()-var2;i++) var3.append(this.var0);
			return var3+var1;
	}
	}

}
