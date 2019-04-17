package testat1;

import uebung1a.*;

public class Hauptprogramm {
	public static void main(String[] args) {
		Huehnerfarm farm = new Huehnerfarm();
		Ostereifabrik fabrik = new Ostereifabrik();
		Ei[][] eggs;
		try {
			for (int i = 1; i < 11; i++) {
				eggs = farm.liefereEier(50);
				EierFileConverter.eierToFile(eggs, ("eier" + i ), true);
			}
			for(int i = 1; i < 11; i++) {
				fabrik.wareneingang(EierFileConverter.fileToEier("eier" + i));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
