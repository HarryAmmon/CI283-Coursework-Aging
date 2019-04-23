import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;


public class Main {
	static Schedular schedular = new Schedular();
	public static void main(String[] args) throws IOException {
		readCSV("./MOCK_DATA.csv"); 			// Calls readCSV
		long startTime = System.nanoTime(); 	// Get the current time
		schedular.start();						// Start the schedular
		long endTime   = System.nanoTime();		// Get the current time
		long totalTime = endTime - startTime;	// Calculate time taken in nanoseconds
		System.out.printf("Calculated in %d milleseconds%n",totalTime/1000000);
	}
	
	/**
	 * For each line in CSV file
	 * 	* Read line and store in variable line
	 *  * Create new process object with information stored in line
	 *  * Add new object to staged area in Schedular
	 * @param path
	 * @return boolean true
	 * @throws IOException
	 */
	private static boolean readCSV(String path) throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader(path));
		String line = null;
		while((line = reader.readLine()) != null) {
			schedular.stage(new Process(line));
		}
		reader.close();
		return true;
	}
	
}
