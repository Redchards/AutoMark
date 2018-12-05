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

