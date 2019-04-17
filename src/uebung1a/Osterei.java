package uebung1a;

@SuppressWarnings("WeakerAccess")
public class Osterei extends Ei {
	private Ostereifarbe farbe = null;
	private boolean gekocht = false;	
	
	
	public Osterei() {		
	}
	
	public Osterei(Ei rohei) {
		super(rohei);		
	}
	
	public void kochen() throws EggCrackException {
		if (getDefekt() || getGekocht() ) {
			//Defekt oder bereits gekocht
			throw new EggCrackException();
		}
		else {
			//Ei kochen
			gekocht = true;			
		}
	}
	
	public void faerben(Ostereifarbe farbe) {		
		if (this.farbe == null) {
			//ungefärbtes Ei
			this.farbe = farbe;
		}
		else {
			//bereits gefärbtes Ei
			this.farbe = Ostereifarbe.UGLY;
		}
	}
	
	public Ostereifarbe getFarbe() {
		return farbe;
	}
	
	public boolean getGekocht() {
		return gekocht;
	}
	
	
}
