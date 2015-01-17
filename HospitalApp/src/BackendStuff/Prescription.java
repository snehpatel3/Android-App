package BackendStuff;

public class Prescription {
	
	private String medicineName;
	private String instructions;
	
	public Prescription(String pmedicineName, String pinstructions){
		
		this.setMedicineName(pmedicineName);
		this.setInstructions(pinstructions);
		
	}

	public String getInstructions() {
		return instructions;
	}

	public void setInstructions(String instructions) {
		this.instructions = instructions;
	}

	public String getMedicineName() {
		return medicineName;
	}

	public void setMedicineName(String medicineName) {
		this.medicineName = medicineName;
	}

}