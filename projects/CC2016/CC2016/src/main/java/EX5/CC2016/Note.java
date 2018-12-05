package EX5.CC2016;

public class Note {
	private double valeur;
	private double coeff;
	
	/**
	 * 
	 * @param valeur valeur de la note
	 * @param coeff coefficient de la note
	 * @throws IllegalArgumentException si les valeur en paramettre ne respectent pas les conventions
	 */
	public Note(double valeur, double coeff) throws IllegalArgumentException {
		if(valeur>20 || valeur<0) {
			throw new IllegalArgumentException("Valeur de la note incorrecte note:"+valeur);
		}
		if(coeff>20 || coeff<1) {
			throw new IllegalArgumentException("coeff de la note incorrecte coeff: "+coeff);
		}
		
		this.valeur=valeur;
		this.coeff=coeff;
	}
	
	public double getValeur() {
	return valeur;
	}
	public double getCoeff() {
	return coeff;
	}
	public String toString(){
		return this.getValeur()+"(coeff "+this.getCoeff()+")";
	}
}

