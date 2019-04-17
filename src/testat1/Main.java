package testat1;

import uebung1a.Ei;
import uebung1a.Huehnerfarm;
import uebung1a.Ostereifabrik;

public class Main {
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
		
		CheckRingBuffer crb = new CheckRingBuffer();
		Runnable input1 = new InputModul("eier" , crb);
		Runnable input2 = new InputModul("eier" , crb);
		Runnable check = new CheckModul(crb);
		Runnable output = new OutputModul(crb, "outEier");
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
