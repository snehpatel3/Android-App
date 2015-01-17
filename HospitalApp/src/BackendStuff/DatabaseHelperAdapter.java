package BackendStuff;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelperAdapter{
	
	DatabaseHelper database;
	public DatabaseHelperAdapter(Context context){
		database = new DatabaseHelper(context);
	}
	public void insertData(String name, String healthcard, String dob){
		SQLiteDatabase db = database.getWritableDatabase();
		ContentValues contentValues = new ContentValues();
		contentValues.put(DatabaseHelper.NAME, name);
		contentValues.put(DatabaseHelper.HEALTHCARD, healthcard);
		contentValues.put(DatabaseHelper.DOB, dob);
		db.insert(DatabaseHelper.TABLE_PATIENT, null, contentValues);
	}
	
	public void insertNDataToLog(String healthcard, String temperature, String heart_rate, String sy_measure, String dy_measure, String log_time, String log_date){
		SQLiteDatabase db = database.getWritableDatabase();
		ContentValues content_values = new ContentValues();
		content_values.put(DatabaseHelper.HC, healthcard); 
		content_values.put(DatabaseHelper.TEMPERATURE, temperature);
		content_values.put(DatabaseHelper.HEARTRATE, heart_rate);
		content_values.put(DatabaseHelper.SYSBP, sy_measure);
		content_values.put(DatabaseHelper.DYBP, dy_measure);
		content_values.put(DatabaseHelper.LOGTIME, log_time);
		content_values.put(DatabaseHelper.LOGDATE, log_date);
		db.insert(DatabaseHelper.LOG_TABLE, null, content_values); 
	}
	
	public String getPatientRecord(String healthcard){
		SQLiteDatabase db = database.getWritableDatabase(); 
		String[] columns = {DatabaseHelper.HC, DatabaseHelper.TEMPERATURE, DatabaseHelper.HEARTRATE, DatabaseHelper.SYSBP, DatabaseHelper.DYBP, DatabaseHelper.LOGTIME, DatabaseHelper.LOGDATE};
		Cursor cursor = db.query(DatabaseHelper.LOG_TABLE, columns, null, null, null, null, null, null); 
		StringBuffer records = new StringBuffer(); 
		while(cursor.moveToNext()){
			int index1 = cursor.getColumnIndex(DatabaseHelper.HC); 
			int index2 = cursor.getColumnIndex(DatabaseHelper.TEMPERATURE); 
			int index3 = cursor.getColumnIndex(DatabaseHelper.HEARTRATE); 
			int index4 = cursor.getColumnIndex(DatabaseHelper.DYBP); 
			int index5 = cursor.getColumnIndex(DatabaseHelper.SYSBP); 
			int index6 = cursor.getColumnIndex(DatabaseHelper.LOGTIME); 
			int index7 = cursor.getColumnIndex(DatabaseHelper.LOGDATE); 
			if(cursor.getString(index1).contentEquals(healthcard)){
				String t = cursor.getString(index2);
				String h = cursor.getString(index3);
				String dp = cursor.getString(index4);
				String sp = cursor.getString(index5);
				String tm = cursor.getString(index6);
				String dt = cursor.getString(index7);
				String temp = "Temperature: " + t; 
				String heartr = "Heart Rate: " + h; 
				String dybp = "Diastolic BP: " + dp; 
				String sybp = "Systolic BP: " + sp; 
				String logt = "Time: " + tm; 
				String logd = "Date: " + dt;
				records.append(logt + "\n" + logd + "\n" + dybp + "\n" + sybp + "\n" + temp + "\n" + heartr + "\n" + "\n");
			}
		}
		records.append(this.getMedInfo(healthcard));
		records.append(this.getTimeSeen(healthcard));
		records.append(this.getDateSeen(healthcard)); 
		return records.toString();
	}
	
	public String getPatientInfo(String healthcard){
		SQLiteDatabase db = database.getWritableDatabase();
		String[] columns = {DatabaseHelper.NAME, DatabaseHelper.HEALTHCARD, DatabaseHelper.DOB};
		Cursor cursor = db.query(DatabaseHelper.TABLE_PATIENT, columns, null, null, null, null, null, null);
		String full_info = "No such patient was found.";
		while(cursor.moveToNext()){
			int index1 = cursor.getColumnIndex(DatabaseHelper.HEALTHCARD);
			int index2 = cursor.getColumnIndex(DatabaseHelper.NAME); 
			int index3 = cursor.getColumnIndex(DatabaseHelper.DOB); 
			if (cursor.getString(index1).contentEquals(healthcard)){
				String health_card = cursor.getString(index1);
				String name = cursor.getString(index2);
				String dob = cursor.getString(index3);
				full_info = health_card + "," + name + "," + dob;
			}
		}
		return full_info;
	}
	public Patient getPatient(String healthcard){
		SQLiteDatabase db = database.getWritableDatabase();
		String[] columns = {DatabaseHelper.NAME, DatabaseHelper.HEALTHCARD, DatabaseHelper.DOB};
		Cursor cursor = db.query(DatabaseHelper.TABLE_PATIENT, columns, null, null, null, null, null, null);
		Patient p = new Patient(null, null, null);
		while(cursor.moveToNext()){
			int index1 = cursor.getColumnIndex(DatabaseHelper.HEALTHCARD);
			int index2 = cursor.getColumnIndex(DatabaseHelper.NAME); 
			int index3 = cursor.getColumnIndex(DatabaseHelper.DOB); 
			if (cursor.getString(index1).contentEquals(healthcard)){
				String health_card = cursor.getString(index1);
				String name = cursor.getString(index2);
				String dob = cursor.getString(index3);
				p.setDOB(dob);
				p.setHealthCardNo(health_card);
				p.setName(name);
			}
		}
		return p;
	}
	public String[] getParticular(String healthcard){
		SQLiteDatabase db = database.getWritableDatabase();
		String[] columns = {DatabaseHelper.HC, DatabaseHelper.TEMPERATURE, DatabaseHelper.HEARTRATE, DatabaseHelper.SYSBP, DatabaseHelper.DYBP, DatabaseHelper.LOGTIME, DatabaseHelper.LOGDATE};
		String[] args = {healthcard};
		Cursor cursor = db.query(DatabaseHelper.LOG_TABLE, columns, DatabaseHelper.HC+" =? ", args, null, null, null);
		String[] rec = new String[6];
		while(cursor.moveToNext()){
			int index2 = cursor.getColumnIndex(DatabaseHelper.TEMPERATURE);
			int index3 = cursor.getColumnIndex(DatabaseHelper.HEARTRATE); 
			int index4 = cursor.getColumnIndex(DatabaseHelper.DYBP); 
			int index5 = cursor.getColumnIndex(DatabaseHelper.SYSBP); 
			int index6 = cursor.getColumnIndex(DatabaseHelper.LOGTIME); 
			int index7 = cursor.getColumnIndex(DatabaseHelper.LOGDATE); 
			rec[0] = cursor.getString(index2); 
			rec[1] = cursor.getString(index3); 
			rec[2] = cursor.getString(index4);
			rec[3] = cursor.getString(index5); 
			rec[4] = cursor.getString(index6);
			rec[5] = cursor.getString(index7);
		}
		return rec;
	}
	
	public void insertUrgency(String healthcard){
		SQLiteDatabase db = database.getWritableDatabase();
		ContentValues ct = new ContentValues();
		Patient q = this.getPatient(healthcard);
		String[] ps = this.getParticular(healthcard);
		Urgency u = new Urgency(q.getDOB(), ps[0], ps[3], ps[2], ps[1], ps[5]);
		String un = u.getUrgency();
		ct.put(DatabaseHelper.URGENCY, un);
		String[] whereArgs = {healthcard};
		db.update(DatabaseHelper.TABLE_PATIENT, ct, DatabaseHelper.HEALTHCARD+" =? ", whereArgs);
	}
	public String getUrgency(String healthcard){
		SQLiteDatabase db = database.getWritableDatabase();
		String[] columns = {DatabaseHelper.URGENCY}; 
		String[] args = {healthcard}; 
		Cursor cursor = db.query(DatabaseHelper.TABLE_PATIENT, columns, DatabaseHelper.HEALTHCARD+" =? ", args, null, null, null);
		String urgency = "0";
		while(cursor.moveToNext()){
			int index = cursor.getColumnIndex(DatabaseHelper.URGENCY); 
			urgency = cursor.getString(index); 
		}
		return urgency;
	}
	
	public void insertTimeSeen(String healthcard, String timeSeen){
		SQLiteDatabase db = database.getWritableDatabase();
		ContentValues content = new ContentValues(); 
		content.put(DatabaseHelper.TIME_SEEN, timeSeen);
		String[] whereArgs = {healthcard};
		db.update(DatabaseHelper.TABLE_PATIENT, content, DatabaseHelper.HEALTHCARD+" =? ", whereArgs);
	}
	public String getTimeSeen(String healthcard){
		SQLiteDatabase db = database.getWritableDatabase(); 
		String[] columns = {DatabaseHelper.TIME_SEEN}; 
		String[] args = {healthcard};
		Cursor cursor = db.query(DatabaseHelper.TABLE_PATIENT, columns, DatabaseHelper.HEALTHCARD+" =? ", args, null, null, null);
		String time = ""; 
		while(cursor.moveToNext()){
			int index = cursor.getColumnIndex(DatabaseHelper.TIME_SEEN); 
			time = cursor.getString(index); 
		}
		return "Time seen: " + time + "\n"; 
	}
	public String getDateSeen(String healthcard){
		SQLiteDatabase db = database.getWritableDatabase(); 
		String[] columns = {DatabaseHelper.DATE_SEEN}; 
		String[] args = {healthcard};
		Cursor cursor = db.query(DatabaseHelper.TABLE_PATIENT, columns, DatabaseHelper.HEALTHCARD+" =? ", args, null, null, null);
		String date = ""; 
		while(cursor.moveToNext()){
			int index = cursor.getColumnIndex(DatabaseHelper.DATE_SEEN); 
			date = cursor.getString(index); 
		}
		return "Date seen: " + date + "\n"; 
	}
	
	public void insertDateSeen(String healthcard, String dateSeen){
		SQLiteDatabase db = database.getWritableDatabase();
		ContentValues content = new ContentValues(); 
		content.put(DatabaseHelper.DATE_SEEN, dateSeen);
		String[] whereArgs = {healthcard};
		db.update(DatabaseHelper.TABLE_PATIENT, content, DatabaseHelper.HEALTHCARD+" =? ", whereArgs);
	}
	
	@SuppressWarnings("null")
	public ArrayList<Patient> getPatientsUnseen(){
		SQLiteDatabase db = database.getWritableDatabase();
		String[] columns = {DatabaseHelper.NAME, DatabaseHelper.HEALTHCARD, DatabaseHelper.DOB};
		String[] args = {null};
		Cursor cursor = db.query(DatabaseHelper.TABLE_PATIENT, columns, DatabaseHelper.TIME_SEEN+" =? ", args, null, null, null);
		ArrayList<Patient> patients_unseen = null;
		while(cursor.moveToNext()){
			int index = cursor.getColumnIndex(DatabaseHelper.HEALTHCARD);
			String hc = cursor.getString(index);
			Patient x = this.getPatient(hc);
			patients_unseen.add(x);
		}
		return patients_unseen;
	}
	
	public void insertMedInfo(String healthcard, String med, String med_in){
		SQLiteDatabase db = database.getWritableDatabase();
		ContentValues contentvalues = new ContentValues(); 
		contentvalues.put(DatabaseHelper.MED, med); 
		contentvalues.put(DatabaseHelper.MEDIN, med_in); 
		String[] args = {healthcard};
		db.update(DatabaseHelper.LOG_TABLE, contentvalues, DatabaseHelper.HC+" =? ", args);
	}
	
	public String getMedInfo(String healthcard){
		SQLiteDatabase db = database.getWritableDatabase(); 
		String[] columns = {DatabaseHelper.MED, DatabaseHelper.MEDIN}; 
		String[] args = {healthcard};
		Cursor cursor = db.query(DatabaseHelper.LOG_TABLE, columns, DatabaseHelper.HC+" =? ", args, null, null, null);
		int loop = 0;
		String med_info = ""; 
		String medication = null;
		String medication_in = null; 
		while(cursor.moveToNext() && (loop==0)){
			int index1 = cursor.getColumnIndex(DatabaseHelper.MED); 
			int index2 = cursor.getColumnIndex(DatabaseHelper.MEDIN); 
			medication = cursor.getString(index1); 
			medication_in = cursor.getString(index2);
			loop++; 
		}
		med_info = "Medicine: " + medication + "\n" + "Medication info: " + medication_in + "\n" + "\n";
		return med_info;
		}
	/*
	public String getAllData(SQLiteDatabase db){
		String[] columns = {DatabaseHelper.NAME};
		Cursor cursor = db.query(DatabaseHelper.TABLE1_NAME, columns, null, null, null, null, null);
		StringBuffer buffer = new StringBuffer();
		while(cursor.moveToNext()){
			int ind = cursor.getColumnIndex(DatabaseHelper.NAME);
			String name = cursor.getString(ind);
			buffer.append(name + "\n");
		}
		return buffer.toString();
	}
	
	
	public boolean contains(SQLiteDatabase db, String healthcard){
		String[] columns = {DatabaseHelper.HEALTHCARD};
		Cursor cursor = db.query(DatabaseHelper.TABLE1_NAME, columns, null, null, null, null, null); 
		while(cursor.moveToNext()){
			int index = cursor.getColumnIndex(DatabaseHelper.HEALTHCARD); 
			if(healthcard.contentEquals(cursor.getString(index))){
				return true;
			}
		}
		return false; 
	}
	public long insertPhyLog(String med, String in){
		SQLiteDatabase db = database.getWritableDatabase(); 
		ContentValues contentValues4 = new ContentValues();
		contentValues4.put(DatabaseHelper.MED, med); 
		contentValues4.put(DatabaseHelper.MEDIN, in);
		long id4 = db.insert(DatabaseHelper.TABLE2_NAME, null, contentValues4);
		return id4;
	}
	*/
	static class DatabaseHelper extends SQLiteOpenHelper{
		private static final String DATABASE_NAME = "Hospital Database";
		private static final String TABLE_PATIENT = "PATIENTTABLE";
		private static final String LOG_TABLE = "LOGTABLE";
		private static final int DATABASE_VERSION = 1; 
		private static final String UID = "_id"; 
		private static final String NAME = "Name"; 
		private static final String HEALTHCARD = "HealthCard";
		private static final String DOB = "Birthdate";  
		private static final String HC = "Healthcard";
		private static final String TEMPERATURE = "Temperature";
		private static final String HEARTRATE = "HeartRate";
		private static final String SYSBP = "SystolicBP";
		private static final String DYBP = "DiastolicBP";
		private static final String LOGTIME = "LogTime";
		private static final String LOGDATE = "LogDate";
		private static final String MED = "Medication";
		private static final String MEDIN = "MedicationInformation";
		private static final String URGENCY = "Urgency";
		private static final String TIME_SEEN = "TimeSeen";
		private static final String DATE_SEEN = "DateSeen";
		private static final String VARCHAR = " VARCHAR(100), ";
		private static final String CREATE_PATIENT = "CREATE TABLE "+TABLE_PATIENT+" ("+UID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+NAME+VARCHAR+HEALTHCARD+VARCHAR+DOB+VARCHAR+URGENCY+VARCHAR+TIME_SEEN+VARCHAR+DATE_SEEN+" VARCHAR(50));";
		private static final String DROP_PATIENT = "DROP TABLE IF EXISTS " + TABLE_PATIENT;
		private static final String CREATE_LOG = "CREATE TABLE "+LOG_TABLE+" ("+UID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+HC+VARCHAR+TEMPERATURE+VARCHAR+HEARTRATE+VARCHAR+SYSBP+VARCHAR+DYBP+VARCHAR+LOGTIME+VARCHAR+LOGDATE+VARCHAR+MED+VARCHAR+MEDIN+" VARCHAR(100));";
		private static final String DROP_LOG = "DROP TABLE IF EXISTS " + LOG_TABLE;
		private Context context; 
		
		public DatabaseHelper(Context context){
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
			this.context = context;
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			// TODO Auto-generated method stub
			try { 
				db.execSQL(CREATE_PATIENT);
				Message.message(context, "Table 1 Created");
			}catch(SQLException e){
				Message.message(context, ""+e); 
			}
			try{
				db.execSQL(CREATE_LOG);
				Message.message(context, "Table 2 Created");
			}catch(SQLException e){
				Message.message(context, ""+e);
			}
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			// TODO Auto-generated method stub
			try{
				db.execSQL(DROP_PATIENT); 
				onCreate(db); 
				Message.message(context, "onUpgrade Called");
			}catch(SQLException e){
				Message.message(context, ""+e);
			}
			try{
				db.execSQL(DROP_LOG);
				onCreate(db);
				Message.message(context, "onUpgrade Called");
			}catch(SQLException e){
				Message.message(context, ""+e);
			}
		}
		
		
	}
}