public class Process implements Comparable<Process>{
	private int pid;
	private int arrivalTime;
	private int burstTime;
	private int executionTime;
	private int waitTime;
	private int priority;
	
	Process(String process){
		String[] fields = process.split(",");
		this.pid = Integer.parseInt(fields[0]);
		this.arrivalTime = Integer.parseInt(fields[1]);
		this.burstTime = Integer.parseInt(fields[2]);
		this.priority = Integer.parseInt(fields[3]);
		this.executionTime = 0;
		this.waitTime = 0;
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
	
	public void setPriority(int i) {
		this.priority = i;
	}
	
	public int getExecutionTime() {
		return this.executionTime;
	}
	
	public void age() {
		this.waitTime++;
		if (this.waitTime % 20 == 0 && this.priority > 0) {
			this.priority--;
			//System.out.printf("PRIORITY DECREASED FOR PID: %4d%n", this.pid); //REMOVE
		}
	}
	@Override
	public int compareTo(Process comparePr) {
		int compareArrivalTime = comparePr.getArrivalTime();
		return this.arrivalTime - compareArrivalTime;
	}
	
	public void execute(int systemTime){
		this.executionTime = systemTime;
		System.out.printf("PID: %4d | ArrivalTime: %5d | ExecutionTime: %6d | WaitTime: %5d | BurstTime: %4d | Priority: %2d%n", this.pid,this.arrivalTime,this.executionTime,this.waitTime,this.burstTime,this.priority);
	}
	

}
