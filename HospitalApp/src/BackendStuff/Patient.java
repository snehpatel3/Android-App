package BackendStuff;

public class Patient {
	private String Name; 
	private String HealthCardNo;
	private String DOB;
	
	public Patient(String pname, String phealthcardno, String dob){
		this.setName(pname); 
		this.setHealthCardNo(phealthcardno);
		this.setDOB(dob);
		
	}

	public String getName() {
		return Name;
	}
	public void setName(String name) {
		Name = name;
	}
	public String getHealthCardNo() {
		return HealthCardNo;
	}
	public void setHealthCardNo(String healthCardNo) {
		HealthCardNo = healthCardNo;
	}
	public String getDOB() {
		return DOB;
	}
	public void setDOB(String dOB) {
		DOB = dOB;
	} 
	public String toString(){
		return "Patient(" + this.getName()+ ", " + this.getDOB() + ", " + this.getHealthCardNo()+")";
	}
}

