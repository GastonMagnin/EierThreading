package testat1;

import java.nio.BufferUnderflowException;

import uebung1a.*;

public class OutputModul implements Runnable {
	//CheckRingBuffer this OutputModul gets eggs from
	CheckRingBuffer crb;
	//Name of the file(s) this OutputModul writes the eggs to 
	String filename;

	public OutputModul(CheckRingBuffer crb, String filename) {
		this.crb = crb;
		if (filename.substring(filename.length() - 4, filename.length()).equals(".txt"))
			filename = filename.substring(filename.length() - 4);
		this.filename = filename;
	}

	@Override
	public void run() {
		long lastEgg = 0;
		int fileCounter = 0;
		int counter = 0;
		Ei[][] eggs = new Ei[4][25];
		while (true) {
			try {
				//Get Ei from Buffer and put it in eggs
				eggs[counter / 25][counter % 25] = crb.dequeue();
				//increase counter if there is no exception
				counter++;
				//Update lastEgg Time
				lastEgg = System.currentTimeMillis();
				Thread.sleep(100);
			} catch (BufferUnderflowException | InterruptedException e) {
			}
			// If the Array is full or the array is not empty and it has been 10 seconds
			// since the last Egg was received write eggs to file
			if (counter >= 100 || (System.currentTimeMillis() - lastEgg >= 10000 && counter > 0)) {
				EierFileConverter.eierToFile(eggs, (filename + fileCounter + ".txt"), true);
				eggs = new Ei[4][25];
				fileCounter++;
				counter = 0;
				System.out.println("Eierkarton in Datei verpackt");
			}
		}

	}

}
