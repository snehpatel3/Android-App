package com.example.hospitalapp;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Scanner;

import BackendStuff.DatabaseHelperAdapter;
import BackendStuff.DateTime;
import BackendStuff.Message;
import BackendStuff.Patient;
import BackendStuff.Users;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends Activity {
	
	public final static String DATABASE = "Database";
	public final static String STATUS = "status";
	String[] store; 	
	ArrayList<Users> users = new ArrayList<Users>();
	ArrayList<Patient> patients = new ArrayList<Patient>(); 
	String[] storage; 
	public static DatabaseHelperAdapter databaseHelper;
	//DatabaseHelperAdapter databaseHelper; 
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        DateTime d = new BackendStuff.DateTime();
        String t = d.currentTime;
        Message.message(this, t);
        databaseHelper = new DatabaseHelperAdapter(this); 
        InputStream is = this.getResources().openRawResource(R.raw.passwords);
		Scanner scanner = new Scanner(new InputStreamReader(is));
		if(is != null){
			while(scanner.hasNextLine()){
					store = scanner.nextLine().split(",");
					users.add(new Users(store[0], store[1], store[2]));
			}
			scanner.close();
		}
		InputStream is1 = this.getResources().openRawResource(R.raw.patient_records);
		Scanner scanner1 = new Scanner(new InputStreamReader(is1));
		if(is1 != null){
			while(scanner1.hasNextLine()){
					storage = scanner1.nextLine().split(",");
					patients.add(new Patient(storage[1], storage[0], storage[2]));
			}
			scanner1.close();
		}
		for (Patient x: patients){
			databaseHelper.insertData(x.getName(), x.getHealthCardNo(), x.getDOB());
		}
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    public void login(View view){
    	Intent nurseIntent = new Intent(this, NurseMenuActivity.class);
    	Intent phyIntent = new Intent(this, PhysicianMenuActivity.class);
    	EditText user = (EditText) findViewById(R.id.username_edit);
    	EditText pass = (EditText) findViewById(R.id.password_edit);
    	
    	String username = user.getText().toString();
    	String password = pass.getText().toString();
    	
    	for(Users x: users){
    		if((x.getUsername().contentEquals(username)) 
    				&& (x.getPassword().contentEquals(password))){
    			String status = x.getPosition();
				if (x.getPosition().contentEquals("nurse")){
					status = "Nurse";
				}else if(x.getPosition().contentEquals("physician")){
					status = "Physician";
				}
				if(status == "Nurse"){
					nurseIntent.putExtra(STATUS, status);
					startActivity(nurseIntent);
				}else if(status == "Physician"){
					phyIntent.putExtra(STATUS, status);
					startActivity(phyIntent);
				}
    		}
    	}
    }
}
