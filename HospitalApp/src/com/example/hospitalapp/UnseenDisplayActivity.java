package com.example.hospitalapp;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.widget.TextView;

public class UnseenDisplayActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		TextView tv; 
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_unseen_display);
		tv = (TextView) findViewById(R.id.patient_dis); 
		Intent goToUrgency = getIntent(); 
		String list_patients = goToUrgency.getStringExtra(NurseMenuActivity.LIST); 
		
		tv.setText(list_patients);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.unseen_display, menu);
		return true;
	}

}
