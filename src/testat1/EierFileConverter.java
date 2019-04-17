package testat1;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import uebung1a.*;

public class EierFileConverter {
	public static final String separator = "|";

	public static void main(String[] args) throws IOException {
		Huehnerfarm h = new Huehnerfarm();
		try {
			Ei[][] start = h.liefereEier(50);
			System.out.println(eierToFile(start, "bananen", false));
			System.out.println("start");
			Ei[][] end = fileToEier("bananen");
			int maxSize = start.length > end.length ? start.length : end.length;
			for(int i = 0; i < maxSize; i++) {
				for(int j = 0; j < start[i].length ;j++) {
					Ei egg = start[i][j];
					Ei egg2 = end[i][j];
					System.out.println(egg.getGewicht() == egg2.getGewicht());
					System.out.println(egg.getDefekt() == egg2.getDefekt());
					System.out.println(egg.getLegedatum().equals(egg2.getLegedatum()));
					System.out.println();
					
				}
			}
		} catch (Exception e) {
			e.printStackTrace(System.err);
		}
	}
	/**
	 *  Converts a Ei[][] to Strings and writes them to a new file
	 * @param eierkartonstapel
	 * @param filename
	 * @param delete
	 * @return
	 */
	public static boolean eierToFile(Ei[][] eierkartonstapel, String filename, boolean delete) {
		if (!filename.substring(filename.length() - 4, filename.length()).equals(".txt"))
			filename += ".txt";
		File f = new File(filename);
		if (f.exists()) {
			if(delete) {
				f.delete();
			}else{
				return false;
			}
		}
		try (BufferedWriter bw = new BufferedWriter(new FileWriter(f));) {
			for (int i = 0; i < eierkartonstapel.length; i++) {
				for (int j = 0; j < eierkartonstapel[i].length; j++) {
					Ei egg = eierkartonstapel[i][j];
					if(egg == null) continue;
					bw.write(i + separator + j + separator + egg.getGewicht() + separator + egg.getGroesse() + separator
							+ egg.getLegedatum() + separator + egg.getDefekt());
					bw.newLine();
				}
			}
			bw.flush();
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}

		return true;
	}

	/**
	 * 
	 * @param filename String containing the name of a file that contains Ei objects
	 *                 saved by eierToFile
	 * @return Ei[][] with the Ei objects from file filename, null if file is empty
	 * @throws IOException
	 */
	public static Ei[][] fileToEier(String filename) throws IOException {
		filename += filename.substring(filename.length() - 4, filename.length()).equals(".txt") ? "" : ".txt";

		ArrayList<String> content = new ArrayList<String>();
		File f = new File(filename);
		
		try (BufferedReader br = new BufferedReader(new FileReader(f));) {
			String line;
			while ((line = br.readLine()) != null) {
				content.add(line);
			}
		}
		// If the file was empty return null
		if (content.size() == 0)
			return null;
		int arraySize = Integer.parseInt(content.get(content.size() - 1).split("\\|")[0]) + 1;
		int kartonSize = Integer.parseInt(content.get(content.size() - 1).split("\\|")[1]) + 1;
		// If there are multiple Kartons get max size
		if (arraySize > 1) {
			for (int i = content.size() - 1; i >= 0; i--) {
				// Get last full karton and set karton size to its size
				if (Integer.parseInt(content.get(i).split("\\|")[0]) + 1 < arraySize) {
					kartonSize = Integer.parseInt(content.get(i).split("\\|")[1]) + 1;
					// terminate loop
					i = -1;
				}
			}

		}

		Ei[][] output = new Ei[arraySize][kartonSize];
		if (Integer.parseInt(content.get(content.size() - 1).split("\\|")[1]) + 1 != kartonSize)
			output[arraySize - 1] = new Ei[Integer.parseInt(content.get(content.size() - 1).split("\\|")[1]) + 1];
		int x;
		int y;
		for(String line : content) {
			String[] currentLine = line.split("\\|");
			x = Integer.parseInt(currentLine[0]);
			y = Integer.parseInt(currentLine[1]);
			output[x][y] = new Ei(currentLine[2], currentLine[4], currentLine[5]);
		}
		return output;
	}
}
