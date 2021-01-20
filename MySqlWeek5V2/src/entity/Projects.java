package entity;

public class Projects {
	private String projName;
	
	public Projects() {
		System.out.println("Creating default constructor");
	}
	
	public Projects(String projName) {
		this.projName = projName;
		System.out.println("Also printing some message.");
	}

}
