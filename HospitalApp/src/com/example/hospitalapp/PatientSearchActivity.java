package com.example.hospitalapp;

import java.util.ArrayList;
import BackendStuff.DatabaseHelperAdapter;
import BackendStuff.Patient;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;

public class PatientSearchActivity extends Activity {

	public static final String STAT = "status";
	public static final String NAME = "name";
	public static final String HC = "hc";
	public static final String DOB = "dob";
	ArrayList<Patient> patients = new ArrayList<Patient>(); 
	String[] storage; 
	DatabaseHelperAdapter databaseHelper = MainActivity.databaseHelper;
	String status; 
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_patient_search);
		Intent nurse_sc = getIntent(); 
		Intent doc_sc = getIntent();
		Intent nexti = getIntent();
		if (nurse_sc != null){
			status = nurse_sc.getStringExtra(MainActivity.STATUS);
		}
		else if(doc_sc != null){
			status = doc_sc.getStringExtra(MainActivity.STATUS);
		}else if(nexti != null){
			status = nexti.getStringExtra(MainActivity.STATUS);
		}
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.patient_search, menu);
		return true;
	}
	
	public void search(View view){
		Intent intent_sc = new Intent(this, PatientActivity.class);
		EditText hc_num = (EditText) findViewById(R.id.hc_search); 
		String hc = hc_num.getText().toString();
		String info = databaseHelper.getPatientInfo(hc);
		String[] data; 
		data = info.split(",");
		Patient p = new Patient(data[1], data[0], data[2]);
		String name_p = p.getName();
		String hc_p = p.getHealthCardNo();
		String dob_p = p.getDOB();
		intent_sc.putExtra(NAME, name_p);
		intent_sc.putExtra(HC, hc_p);
		intent_sc.putExtra(DOB, dob_p);
		intent_sc.putExtra(MainActivity.STATUS, status); 
		startActivity(intent_sc);
	}
}
