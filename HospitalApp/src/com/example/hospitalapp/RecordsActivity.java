package com.example.hospitalapp;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.widget.TextView;

public class RecordsActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		TextView textView;
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_records);
		textView = (TextView) findViewById(R.id.dis_message);
		Intent see = getIntent();
		String rec = see.getStringExtra(PatientActivity.RECORD);
		textView.setText(rec);
		//Message.message(this, rec);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.records, menu);
		return true;
	}

}
