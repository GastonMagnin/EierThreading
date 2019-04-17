package testat1;

import uebung1a.*;
import java.io.File;
import java.io.IOException;
import java.nio.BufferOverflowException;
import java.util.ArrayList;

public class InputModul implements Runnable {
	String filename;
	CheckRingBuffer crb;
	ArrayList<String> paths = new ArrayList<String>();

	public InputModul(String filename, CheckRingBuffer crb) {
		// Remove .txt extension from filename
		if (filename.substring(filename.length() - 4, filename.length()).equals(".txt"))
			filename = filename.substring(filename.length() - 4);
		this.filename = filename;
		this.crb = crb;
		this.paths = crb.getPaths();
	}

	/**
	 * Generates a list of files matching the path(filename) and writes them to
	 * paths
	 * 
	 * @param filename name of the file(s) containing eggs (without numbers if there
	 *                 are multiple)
	 * @param crb      CheckRingBuffer the CheckRingBuffer this InputModul writes to
	 */
	private void generatePaths(String filename, CheckRingBuffer crb) {
		synchronized (paths) {
			if (crb.getPathsGenerated())
				return;
			File dir = new File(".");
			// Lambda implementing the FilenameFilter interface checking for files in dir
			// matching the pattern
			File[] files = dir.listFiles((d, name) -> name.matches("^" + filename + "[0-9]*\\.txt$"));
			// Add the paths of all found files to the crb.paths ArrayList
			for (File file : files) {
				System.out.println(file.getName());
				paths.add(file.getPath());

			}
			// Update pathsGenerated
			crb.setPathsGenerated(true);

		}

	}

	public void run() {
		System.out.println("running");
		generatePaths(filename, crb);
		Ei[][] eggs = null;
		// Repeat while there are files that haven't been converted to Eggs
		while (!paths.isEmpty()) {
			synchronized (paths) {
				// make sure paths isn't empty
				if (paths.isEmpty())
					continue;
				try {
					eggs = EierFileConverter.fileToEier(paths.get(0));
					paths.remove(0);
				} catch (IOException e) {
					System.out.println("ioioioiioii");
				}
			}
			for (Ei[] karton : eggs) {
				for (Ei egg : karton) {
					// Enqueue egg, if egg can't be enqueued wait 50ms and try again
					while (!(egg == null)) {
						try {
							crb.enqueue(egg);
							egg = null;

						} catch (BufferOverflowException e) {
							System.out.println("Karton voll");
						}
					}
					try {
						Thread.sleep(100);
					} catch (InterruptedException e) {
					}
				}
			}

		}
		System.out.println(Thread.currentThread() + " out");

	}

}