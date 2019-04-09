import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;


public class Main {
	static ArrayList<Process> processes = new ArrayList<Process>();
	public static void main(String[] args) throws IOException {
		long startTime = System.nanoTime();
		Schedular schedular = new Schedular();
		readCSV("./processes.csv");
		for (int i = 0; i<processes.size();i++) {
			schedular.addToQueue(processes.get(i));
		}
		//schedular.showAllQueues();
		schedular.sortQueue(1);
		schedular.sortQueue(2);
		schedular.sortQueue(3);
		System.out.println("Queues sorted");
		schedular.start();
		long endTime   = System.nanoTime();
		long totalTime = endTime - startTime;
		
		System.out.printf("Calculated in %d milleseconds%n",totalTime/1000000);
	}
	
	private static boolean readCSV(String path) throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader(path));
		String line = null;
		while((line = reader.readLine()) != null) {
			processes.add(new Process(line));
		}
		reader.close();
		return true;
	}
	
}
