package com.example.hospitalapp;

import BackendStuff.DatabaseHelperAdapter;
import BackendStuff.Message;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

public class PatientActivity extends Activity {
	
	public static final String RECORD = "Record";
	DatabaseHelperAdapter databaseHelper = MainActivity.databaseHelper;
	String status; 
	String name_p; 
	String hc_p; 
	String dob_p;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		TextView tv1; 
		TextView tv2;
		TextView tv3;
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_patient);
		tv1 = (TextView) findViewById(R.id.pname);
		tv2 = (TextView) findViewById(R.id.phc);
		tv3 = (TextView) findViewById(R.id.pdob); 
		Intent intent_sc = getIntent();
		name_p = intent_sc.getStringExtra(PatientSearchActivity.NAME); 
		hc_p = intent_sc.getStringExtra(PatientSearchActivity.HC); 
		dob_p = intent_sc.getStringExtra(PatientSearchActivity.DOB); 
		status = intent_sc.getStringExtra(MainActivity.STATUS);

		tv1.setText(name_p); 
		tv2.setText(hc_p); 
		tv3.setText(dob_p);
		//Message.message(this, status);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.patient, menu);
		return true;
	}
	public void SeeRecs(View view){
		Intent see = new Intent(this, RecordsActivity.class);
		String rec = databaseHelper.getPatientRecord(hc_p);
		see.putExtra(RECORD, rec);
		startActivity(see);
		//Message.message(this, rec);
		
	}
	
	public void NewRec(View view){
		if(status.contentEquals("Physician")){
			Intent newr = new Intent(this, PhysicianRecordActivity.class); 
			newr.putExtra(PatientSearchActivity.HC, hc_p); 
			startActivity(newr); 
		}else if (status.contentEquals("Nurse")){
			Intent newr = new Intent(this, NurseRecordActivity.class); 
			newr.putExtra(PatientSearchActivity.HC, hc_p);
			startActivity(newr); 
		}
		
	}
	public void goToSeen(View view){ 
		if(status.contentEquals("Nurse")){
			Intent setTimeDate = new Intent(this, DateTimeActivity.class); 
			setTimeDate.putExtra(PatientSearchActivity.HC, hc_p);
			startActivity(setTimeDate); 
		}else{
			Message.message(this, "You're not a nurse!");
		}
	}

}
