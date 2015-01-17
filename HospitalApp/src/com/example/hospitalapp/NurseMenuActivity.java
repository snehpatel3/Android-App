package com.example.hospitalapp;

import BackendStuff.DatabaseHelperAdapter;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;

public class NurseMenuActivity extends Activity {
	
	public static final String LIST = "ListPatients";
	DatabaseHelperAdapter databaseHelper = MainActivity.databaseHelper;
	String status = null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_nurse_menu);
		Intent nurseIntent = getIntent();
		status = nurseIntent.getStringExtra(MainActivity.STATUS);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.nurse_menu, menu);
		return true;
	}
	
	public void logout2(View view){
		Intent logout_2 = new Intent(this, MainActivity.class);
		startActivity(logout_2);
	}
	
	public void nurse_search(View view){
		Intent nurse_sc = new Intent(this, PatientSearchActivity.class);
		nurse_sc.putExtra(MainActivity.STATUS, status);
		startActivity(nurse_sc);	
	}
	
	public void add_Patient(View view){
		Intent addPatient = new Intent(this, NewPatientActivity.class);
		startActivity(addPatient);
	}
	
	public void display_unseen(View view){
		/*
		ArrayList<Patient> patients = databaseHelper.getPatientsUnseen();
		StringBuffer listP = new StringBuffer(); 
		for(Patient x: patients){
			listP.append(x.getName() + "  " + databaseHelper.getUrgency(x.getHealthCardNo()) + "\n");
		}
		Intent goToUrgency = new Intent(this, UnseenDisplayActivity.class); 
		String list_patients = listP.toString();
		goToUrgency.putExtra(LIST, list_patients);
		startActivity(goToUrgency); 
		*/
	}

}
