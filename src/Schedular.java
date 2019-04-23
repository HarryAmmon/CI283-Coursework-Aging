import java.util.ArrayList;
import java.util.Collections;

public class Schedular {
	private ArrayList<Process> staged;
	private ArrayList<Process> lowPriority; 
	private ArrayList<Process> mediumPriority; 
	private ArrayList<Process> highPriority;
	
	Schedular(){
		staged = new ArrayList<Process>();
		lowPriority = new ArrayList<Process>();
		mediumPriority = new ArrayList<Process>();
		highPriority = new ArrayList<Process>();
	}
	/**
	 * Will add Process pr to an ArrayList of not yet executed processes
	 * @param pr
	 */
	public void stage(Process pr) {
		staged.add(pr);
	}
	
	/**
	 * Will add Process pr to to correct queue based on its priority
	 * @param pr
	 */
	private void addToQueue(Process pr) {
		switch(pr.getOriginalQueue()) {
		case 1:
			pr.setCurrentQueue(1);
			highPriority.add(pr);
			break;
		case 2:
			pr.setCurrentQueue(2);
			mediumPriority.add(pr);
			break;
		case 3: 
			pr.setCurrentQueue(3);
			lowPriority.add(pr);
			break;
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
		System.out.println("These processes are staged");
		for (int j = 0;j<staged.size();j++) {
			System.out.printf("PID: %4d| ArrivalTime: %4d| BurnTime: %3d| Priority: %1d%n",staged.get(j).getPid(),staged.get(j).getArrivalTime(),staged.get(j).getBurstTime(),staged.get(j).getPriority());
		}
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
	private boolean loadProcesses(int sClock) {
		if(staged.isEmpty()) {
			return false;
		}
		while(staged.get(0).getArrivalTime() <= sClock) {
			addToQueue(staged.get(0));
			staged.remove(0);
			if(staged.isEmpty()) {
				return false;
				}
		}
		return true;
	}
	int processChangedQueue=0; //Counter
	private void updateProcesses(int sClock) {
		
		for(int i = 0;i<highPriority.size();i++) {
			highPriority.get(i).age(sClock);
		}
		for(int i = 0;i<mediumPriority.size();i++) {
			if(mediumPriority.get(i).age(sClock)) {
				Process temp = mediumPriority.remove(i);
				temp.setCurrentQueue(1);
				highPriority.add(temp);
				Collections.sort(highPriority);
				processChangedQueue++;
			}
		}
		for(int i = 0;i<lowPriority.size();i++) {
			if(lowPriority.get(i).age(sClock)) {
				Process temp = lowPriority.remove(i);
				temp.setCurrentQueue(2);
				mediumPriority.add(temp);
				Collections.sort(mediumPriority);
				processChangedQueue++;
			}
		}
	}
	
	public void start() {
		Collections.sort(staged); // Sort the staged arraylist so all processes are in ascending order based on arrival time
		System.out.println("EXECUTION ORDER:");
		System.out.println(" PID| ArrivalTime| ExecutedTime| WaitTime| BurstTime| Priority| OriginalQueue| CurrentQueue|");
		// Lets initialise some variables. These are just counters for demonstration purposes
		int systemClock = 0;
		while(true) { // Could change this so its more descriptive 
				loadProcesses(systemClock);

				if (highPriority.size() != 0 && highPriority.get(0).getArrivalTime() <= systemClock) { 					// If the queue isn't empty and the next process has the arrival time of the System clock or later
					highPriority.get(0).execute(systemClock);
					updateProcesses(systemClock);
					systemClock += highPriority.get(0).getBurstTime();
					highPriority.remove(0);
					}
				
				else if(mediumPriority.size() != 0 && mediumPriority.get(0).getArrivalTime() <= systemClock){
					mediumPriority.get(0).execute(systemClock);
					updateProcesses(systemClock);
					systemClock += mediumPriority.get(0).getBurstTime();
					mediumPriority.remove(0);			
					}
				
				else if(lowPriority.size() != 0 && lowPriority.get(0).getArrivalTime() <= systemClock) {
					lowPriority.get(0).execute(systemClock);
					updateProcesses(systemClock);
					systemClock += lowPriority.get(0).getBurstTime();
					lowPriority.remove(0);		
					}
				else if(highPriority.isEmpty() && mediumPriority.isEmpty() && lowPriority.isEmpty() && staged.isEmpty()){ // If all queues are empty then there are no more processes to execute so program can stop
					System.out.println("All queues are empty. All process have been executed.");
					//System.out.printf("The system would take %5d seconds to execute these processes.%n", systemClock);
					//System.out.printf("High: %3d | Med: %3d | Low %3d%n",high,med,low);
					System.out.printf("Processes changed queues %d times.%n ",processChangedQueue);
					break;
					}
				else {systemClock++;}
			
				
		
			}

		}
	}
