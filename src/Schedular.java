import java.util.ArrayList;
import java.util.Collections;

public class Schedular {
	private ArrayList<Process> lowPriority; 
	private ArrayList<Process> mediumPriority; 
	private ArrayList<Process> highPriority;
	
	Schedular(){
		lowPriority = new ArrayList<Process>();
		mediumPriority = new ArrayList<Process>();
		highPriority = new ArrayList<Process>();
	}
	/**
	 * Will add Process pr to to correct queue based on its priority
	 * @param pr
	 */
	public void addToQueue(Process pr) {
		if (pr.getPriority() <= 42) {highPriority.add(pr);}
		else if (pr.getPriority() > 42 && pr.getPriority() <= 82) {mediumPriority.add(pr);}
		else if (pr.getPriority() > 82) {lowPriority.add(pr);}
		else {
			System.out.println("UNABLE TO ADD TO QUEUE");
			System.out.printf("PID: %4d| ArrivalTime: %4d| BurnTime: %3d| Priority: %3d%n",pr.getPid(),pr.getArrivalTime(),pr.getBurstTime(),pr.getPriority());
		}
	}
	
	/**
	 * This will sort the given priority queue (i) so that arrival time is in ascending order
	 * @param i Where 1 = highPriority, 2 = mediumPriority, 3 = lowPriority
	 */
	public void sortQueue(int i) {
		switch(i) {
		case 1:
			Collections.sort(highPriority);
			break;
		case 2:
			Collections.sort(mediumPriority);
			break;
		case 3:
			Collections.sort(lowPriority);
			break;
			}
	}
	
	/**
	 * Method that prints out the contents of all queues
	 * Mainly for testing and demonstration purposes
	 */
	public void showAllQueues() {
		System.out.println("These are high priority processes");
		for (int j = 0;j<highPriority.size();j++) {
			System.out.printf("PID: %4d| ArrivalTime: %4d| BurnTime: %3d| Priority: %1d%n",highPriority.get(j).getPid(),highPriority.get(j).getArrivalTime(),highPriority.get(j).getBurstTime(),highPriority.get(j).getPriority());
		}
		System.out.println("These are medium priority processes");
		for (int k = 0;k<mediumPriority.size();k++) {
			System.out.printf("PID: %4d| ArrivalTime: %4d| BurnTime: %3d |Priority: %1d%n",mediumPriority.get(k).getPid(),mediumPriority.get(k).getArrivalTime(),mediumPriority.get(k).getBurstTime(),mediumPriority.get(k).getPriority());
		}
		System.out.println("These are low priority proccesses");
		for (int l = 0;l<lowPriority.size();l++) {
			System.out.printf("PID: %4d| ArrivalTime: %4d| BurnTime: %3d |Priority: %1d%n",lowPriority.get(l).getPid(),lowPriority.get(l).getArrivalTime(),lowPriority.get(l).getBurstTime(),lowPriority.get(l).getPriority());
		}
	}
	
	public void start() {
		System.out.println("**********************EXECUTION ORDER**********************");
		int systemClock = 0; // Simulates a clock that the system uses so processes are run when they "arrive"
		int executed = 0;
		int high = 0; //REMOVE
		int med = 0; //REMOVE
		int low = 0; //REMOVE
		while(true) {
					// If the queue isn't empty and the next process has the arrival time of the System clock
				if (highPriority.size() != 0 && highPriority.get(0).getArrivalTime() <= systemClock) {
					highPriority.get(0).execute(systemClock);
					systemClock += highPriority.get(0).getBurstTime();
					highPriority.remove(0);
					executed++;
					high++;//REMOVE
					}
				else if(mediumPriority.size() != 0 && mediumPriority.get(0).getArrivalTime() <= systemClock){
					mediumPriority.get(0).execute(systemClock);
					systemClock += mediumPriority.get(0).getBurstTime();
					mediumPriority.remove(0);
					executed++;
					med++; //REMOVE
					}
				else if(lowPriority.size() != 0 && lowPriority.get(0).getArrivalTime() <= systemClock) {
					lowPriority.get(0).execute(systemClock);
					systemClock += lowPriority.get(0).getBurstTime();
					lowPriority.remove(0);
					executed++;
					low++; //REMOVE
					}
				else if(highPriority.isEmpty() && mediumPriority.isEmpty() && lowPriority.isEmpty()){ // If all queues are empty then there are no more processes to execute so program can stop
					System.out.println("All queues are empty");
					System.out.printf("%4d process have been executed%n",executed);
					System.out.printf("High: %3d|Med: %3d|Low %3d%n",high,med,low);
					break;
					}
				else {systemClock++;}
				
		
			}

		}
	}
