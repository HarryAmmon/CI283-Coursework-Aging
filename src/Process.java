public class Process implements Comparable<Process>{
	private int pid;
	private int arrivalTime;
	private int burstTime;
	private int executionTime;
	private int waitTime;
	private int priority;
	private int originalQueue;
	private int currentQueue;
	private int intendedQueue;
	
	Process(String process){
		String[] fields = process.split(",");
		this.pid = Integer.parseInt(fields[0]);
		this.arrivalTime = Integer.parseInt(fields[1]);
		this.burstTime = Integer.parseInt(fields[2]);
		this.priority = Integer.parseInt(fields[3]);
		this.waitTime = 0;
		this.originalQueue = calculateQueue();
		this.currentQueue = -1;	// If the process has not yet been added a queue this will have the value of -1
		this.intendedQueue = this.originalQueue;
	}
	
	public int getPid() {
		return this.pid;
	}
	
	public int getArrivalTime() {
		return this.arrivalTime;
	}
	
	public int getBurstTime() {
		return this.burstTime;
		}
	
	public int getPriority() {
		return this.priority;
	}
	
	public int getExecutionTime() {
		return this.executionTime;
	}
	
	public int getOriginalQueue() {
		return this.originalQueue;
	}
	
	public int getCurrentQueue() {
		return this.currentQueue;
	}
	
	public void setCurrentQueue(int i) {
		this.currentQueue = i;
	}
	
	public int getIntendedQueue() {
		return this.intendedQueue;
	}
	
	public boolean age(int sClock) {
		int oldWaitTime = this.waitTime;
		this.waitTime = sClock - this.arrivalTime;
		
		if (this.waitTime - oldWaitTime > 10 && this.priority > 0) {
			this.priority--;
			if(calculateQueue() != currentQueue) {
				this.intendedQueue = calculateQueue();
				return true;
			}

		}
		return false;
	}
	
	private int calculateQueue() {
		if(this.priority <= 42) {return 1;}
		else if(this.priority > 42 && this.priority <= 84) {return 2;}
		else if(this.priority > 84) {return 3;}
		else {return -1;}
	}
	@Override
	public int compareTo(Process comparePr) {
		int compareArrivalTime = comparePr.getArrivalTime();
		return this.arrivalTime - compareArrivalTime;
	}
	
	public void execute(int sClock){
		this.waitTime = sClock - arrivalTime; // Update the length of time taken to execute
		System.out.printf("%4d|%12d|%13d|%9d|%10d|%9d|%14d|%13d|%n", this.pid,this.arrivalTime,sClock,this.waitTime,this.burstTime,this.priority,this.originalQueue,this.currentQueue);
	}
	

}
