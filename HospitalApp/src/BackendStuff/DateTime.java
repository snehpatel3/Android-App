package BackendStuff;

import android.annotation.SuppressLint;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

 
public class DateTime {
	
	@SuppressLint("SimpleDateFormat")
	DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	 
	//get current date time with Calendar()
	Calendar cal = Calendar.getInstance();
	public String currentTime = dateFormat.format(cal.getTime());
	
  public DateTime() {
 
  }
  
}