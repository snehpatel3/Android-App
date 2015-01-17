package BackendStuff;

public class Urgency {
	private int UrgencyPt;
	private String Temp;
	private String Systolic;
	private String Diastolic;
	private String Hr;
	private String TodaysDate;
	private String DOB;
	
	public Urgency(String dOB, String temperature, String systolic, String diastolic, String heartrate, String date){
		UrgencyPt = 0;
		this.setTemp(temperature);
		this.setSystolic(systolic);
		this.setDiastolic(diastolic);
		this.setHr(heartrate);
		this.setTodaysDate(date);
		this.setDOB(dOB);
		
		// Urgency Point for Age
		if (Integer.parseInt(this.getTodaysDate().substring(0,3)) - Integer.parseInt(this.getDOB().substring(0,3)) < 2){
			UrgencyPt += 1;
		}
		else if (Integer.parseInt(this.getTodaysDate().substring(0,3)) - Integer.parseInt(this.getDOB().substring(0,3)) == 2){
			if (Integer.parseInt(this.getTodaysDate().substring(4,6)) == Integer.parseInt(this.getDOB().substring(4,6))) {
				if (Integer.parseInt(this.getTodaysDate().substring(7,9)) > Integer.parseInt(this.getDOB().substring(7,9))) {
					UrgencyPt += 1;
				}
			}
			else if (Integer.parseInt(this.getTodaysDate().substring(4,6)) > Integer.parseInt(this.getDOB().substring(4,6))) {
				UrgencyPt += 1;
			}
		}
		
		// Urgency Point for Temperature
		if (Integer.parseInt(this.getTemp()) >= 39){
			UrgencyPt += 1;
		}
		
		// Urgency Point for Blood Pressure
		if ((Integer.parseInt(this.getSystolic()) >= 140) || Integer.parseInt(this.getDiastolic()) >= 90) {
			UrgencyPt += 1;
		}

		//Urgency Point for Heart Rate
		if ((Integer.parseInt(this.getHr()) >= 100) || (Integer.parseInt(this.getHr()) <= 50)) {
			UrgencyPt += 1;
		}
		
		///return Integer.toString(UrgencyPt);
	}
	public String getUrgency(){
		return Integer.toString(this.UrgencyPt);
	}
	
	public String getDOB() {
		return DOB;
	}
	public void setDOB(String dOB) {
		DOB = dOB;
	} 
	public String getTemp() {
		return Temp;
	}
	public void setTemp(String temp) {
		Temp = temp;
	}
	public String getHr() {
		return Hr;
	}
	public void setHr(String hr) {
		Hr = hr;
	}
	public String getTodaysDate() {
		return TodaysDate;
	}
	public void setTodaysDate(String todaysDate) {
		TodaysDate = todaysDate;
	}
	public String getSystolic() {
		return Systolic;
	}
	public void setSystolic(String systolic) {
		Systolic = systolic;
	}
	public String getDiastolic() {
		return Diastolic;
	}
	public void setDiastolic(String diastolic) {
		Diastolic = diastolic;
	}
		
}
