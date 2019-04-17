package uebung1a;

import java.util.Calendar;
import java.util.Random;

@SuppressWarnings("WeakerAccess")
public class Ei {
	
	private String legedatum = null;
	private int gewicht = 0;
	private boolean defekt = false;
	
	public Ei() {
		legeEi(5);		
	}
	
	public Ei(int ausschussrate) {		
		legeEi(ausschussrate);		
	}
	
	public Ei (Ei ei) {
		this.gewicht = ei.getGewicht();
		this.legedatum = ei.getLegedatum();
		this.defekt = ei.getDefekt();
	}
	public Ei(String gewicht, String legedatum, String defekt) {
		this.gewicht = Integer.parseInt(gewicht);
		this.legedatum = legedatum;
		this.defekt = defekt.equals(true);
	}

	private void legeEi(int ausschussrate) {
		int minGewicht = 40;
		int maxGewicht = 80;		
		Random rand = new Random();
		setGewicht(rand.nextInt(maxGewicht-minGewicht)+minGewicht);

		//prozentuale Wahrscheinlichkeit auf einen Defekt
		if (rand.nextInt(100) < ausschussrate) {
			defekt = true;
		}			
			
		this.legedatum = Calendar.getInstance().getTime().toString();
	}
	
	
	public Eigroesse getGroesse() {
		Eigroesse groesse;
		if (gewicht < 53) {
			groesse = Eigroesse.S;
		}
		else if (gewicht < 63) {
			groesse = Eigroesse.M;
		}
		else if (gewicht < 74) {
			groesse = Eigroesse.L;
		}
		else {
			groesse = Eigroesse.XL;
		}
		return groesse;
	}
	
	public String getLegedatum() {
		return legedatum;
	}
	
	public int getGewicht() {
		return gewicht;
	}
	
	public boolean getDefekt() {
		return defekt;
	}

	
	public void setLegedatum(String legedatum) {
		if (this.legedatum == null) {
			this.legedatum = legedatum;
		}		
	}
	
	public void setGewicht(int gewicht) {
		if (this.gewicht <= 0) {
			this.gewicht = gewicht;
		}
	}
	
	public void setDefekt(boolean defekt) {
		if (!this.defekt) {
			this.defekt = defekt;
		}								
	}

	static public int codeid() {
		return 1742;
	}
}
