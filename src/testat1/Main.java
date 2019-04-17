package testat1;

import uebung1a.Ei;
import uebung1a.Huehnerfarm;
import uebung1a.Ostereifabrik;

public class Main {
	public static final String FILENAME_IN = "eier";
	public static final String FILENAME_OUT = "outEier";
	public static void main(String[] args) {
	aufgabe2();
	}
	public static void aufgabe1() {
		Huehnerfarm farm = new Huehnerfarm();
		Ostereifabrik fabrik = new Ostereifabrik();
		Ei[][] eggs;
		try {
			for (int i = 1; i < 11; i++) {
				eggs = farm.liefereEier(50);
				EierFileConverter.eierToFile(eggs, (FILENAME_IN + i ), true);
			}
			for(int i = 1; i < 11; i++) {
				for(Ei[] entry : EierFileConverter.fileToEier(FILENAME_IN + i)) {
					for(Ei egg : entry) {
						System.out.println(egg.getDefekt());
					}
				}
				//fabrik.wareneingang(EierFileConverter.fileToEier(FILENAME_IN + i));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public static void aufgabe2() {
		Huehnerfarm farm = new Huehnerfarm();
		Ostereifabrik fabrik = new Ostereifabrik();
		Ei[][] eggs;
		try {
			for (int i = 1; i < 11; i++) {
				eggs = farm.liefereEier(50, 50);
				EierFileConverter.eierToFile(eggs, (FILENAME_IN + i ), true);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		CheckRingBuffer crb = new CheckRingBuffer();
		Runnable input1 = new InputModul(FILENAME_IN , crb);
		Runnable input2 = new InputModul(FILENAME_IN , crb);
		Runnable check = new CheckModul(crb);
		Runnable output = new OutputModul(crb, FILENAME_OUT);
		Thread input1Thread = new Thread(input1);
		Thread input2Thread = new Thread(input2);
		Thread checkThread = new Thread(check);
		Thread outputThread = new Thread(output);
		input1Thread.start();
		input2Thread.start();
		checkThread.start();
		outputThread.start();
	}
}
