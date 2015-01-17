package com.example.hospitalapp;

import BackendStuff.DatabaseHelperAdapter;
import BackendStuff.Message;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;

public class PhysicianRecordActivity extends Activity {
	
	DatabaseHelperAdapter databaseHelper = MainActivity.databaseHelper;
	EditText ed1;
	EditText ed2;
	String hc; 
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_physician_record);
		ed1 = (EditText) findViewById(R.id.med_edit); 
		ed2 = (EditText) findViewById(R.id.in_edit);
		Intent newr = getIntent();
		hc = newr.getStringExtra(PatientSearchActivity.HC);

		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.physician_record, menu);
		return true;
	}
	
	public void save_med(View view){
		String medication = ed1.getText().toString();
		String medication_in = ed2.getText().toString();
		databaseHelper.insertMedInfo(hc, medication, medication_in);
		String info = databaseHelper.getMedInfo(hc);
		Message.message(this, "Saved");
		Message.message(this, info);
	}

}
