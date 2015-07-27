package com.example.dicdog1;

import java.util.ArrayList;
import java.util.List;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseObject;
import com.parse.ParseQuery;

public class Landingpage extends ActionBarActivity {
private static DatabaseHandler db;
private static ProgressBar  mProgressBar;
public static List<String> hospital_list;
protected static final int TIMER_RUNTIME = 10000; // in ms —> 10s
@SuppressWarnings("unused")
private static ParseObject doctordata;
private static TextView text;
protected boolean mbActive;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_landingpage);
		Parse.enableLocalDatastore(this);
        Parse.initialize(this,"bWBg10fbvRRs1E6DLjSTQNVoctDfp5UU7oZXNaNx","22qCsWfIKoHxXRjaIe0afLuINwqCGj0HgSPomDym" );
        //ParseUser.enableAutomaticUser();
        //ParseACL defaultACL = new ParseACL();
        //defaultACL.setPublicReadAccess(true);
        //ParseACL.setDefaultACL(defaultACL, true);
        
		ActionBar actionbar=getSupportActionBar();
		actionbar.hide();
		db=new DatabaseHandler(this);
		text=(TextView) findViewById(R.id.textView1);
		//text.setLayoutParams();
	     mProgressBar = (ProgressBar)findViewById(R.id.progress);
//a complete method for loading and opening the page
	     final Thread timerThread = new Thread() {
	               @Override
	               public void run() {
	            	 doWork();
	                   mbActive = true;
	                   try {
	                       int waited = 0;
	                       while(mbActive && (waited < TIMER_RUNTIME)) {
	                           sleep(200);
	                           if(mbActive) {
	                               waited += 200;
	                               updateProgress(waited);
	                           }
	                       }
	               } catch(InterruptedException e) {
	                   // do nothing
	               } finally {
	                   onContinue();
	               }
	             }
	          };
	          timerThread.start();
	        }
	        @Override
	        public void onDestroy() {
	            super.onDestroy();
	        }
	        public void updateProgress(final int timePassed) {
	            if(null != mProgressBar) {
	                // Ignore rounding error here
	                final int progress = mProgressBar.getMax() * timePassed / TIMER_RUNTIME;
	                mProgressBar.setProgress(progress);
	            }
	        }

	     public void onContinue() {
	          // perform any final actions here
	    	 Intent i=new Intent(Landingpage.this,DashboardActivity.class);
	    	 startActivity(i);
	    	 finish();
	        }
	     //end of that method
	
/*
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.landingpage, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}*/
	//loading database data
	public void doWork()
	{
		hospital_list=new ArrayList<String>();
		ParseQuery<ParseObject> query = ParseQuery.getQuery("DoctorsTable");
		query.whereExists("Hospital");
		query.findInBackground(new FindCallback<ParseObject>() {
			@Override
			public void done(List<ParseObject> la,
					com.parse.ParseException e) {
				// TODO Auto-generated method stub
				 if(la!=null){
					 
					 for(int i=0;i<la.size();i++)
						{
						 String s=la.get(i).getString("Hospital");
						 if(!(hospital_list.contains(s)))
						 {
							 hospital_list.add(s);
							 //Toast.makeText(getApplicationContext(), hospital_list.get(0), Toast.LENGTH_LONG).show();
							 
						 }};}
                  else{//handle the error}
                	  Toast.makeText(getApplicationContext(),"Network Problem.Please Check Your Internet Connection", Toast.LENGTH_LONG).show();
                	  showSettingsAlert("Internet");
                            }
				
			}
		});
		
		
			


		
		db.getWritableDatabase();
        db.deleteTable(db.getWritableDatabase());                
        //33.711778, 73.057217  current location
        db.addContact("Ali", "Dentist", "male", 33.676477, 73.066024, "0344 553 2806","Shifa International","Specialist");  //Shifa
        db.addContact("Omer", "Dentist", "male", 33.702456, 73.053946, "0344 553 2806","PIMS","Specialist");  //PIMS
        db.addContact("Rabia", "Dentist", "female", 33.652332, 73.015801, "0344 553 2806","PAEC","Specialist");  //PAEC 
        db.addContact("Alia", "Cardiologist", "female", 33.649471, 73.017311, "0344 553 2806","NESCOM","Specialist"); //NESCOM
        db.addContact("Wamzay", "Orthopedics", "female", 33.580738, 73.047892, "0344 553 2806","CMH Rawalpindi","Specialist"); //CMH
        db.addContact("Usman", "Dentist", "male", 33.711385, 73.041675, "0344 553 2806","Islamabad Diagnostic","Specialist");  
        db.addContact("Nauman", "Eye Specialist", "male", 33.640906, 73.057455, "0344 553 2806","Holy Family Rwp","Specialist");  //Holy Family
        db.addContact("Irfan", "Eye Specialist", "male", 33.678234, 73.031512, "0344 553 2806","KRL Hospital","Specialist");  
        db.addContact("Bilal", "Eye Specialist", "male", 33.710528, 73.042057, "0344 553 2806","Ali Medical","Specialist");  
        db.addContact("Fatima", "Orthopedics", "female", 33.554166, 73.095568, "0344 553 2806","Fauji Foundation","Specialist");
        db.addContact("Zeeshan", "NeuroSurgeon", "male", 34.003330, 71.542214, "0344 553 2806","CMH Peshawar","Specialist"); //CMH Peshawar  
        db.close();        
        
	}
	 public void showSettingsAlert(String provider) {
			AlertDialog.Builder alertDialog = new AlertDialog.Builder(Landingpage.this);
			alertDialog.setTitle(provider + " SETTINGS");
			alertDialog.setMessage(provider + " is not enabled! Want to go to settings menu?");
			alertDialog.setPositiveButton("Settings",
			new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int which) {
					Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
					Landingpage.this.startActivity(intent);
				}});

			alertDialog.setNegativeButton("Cancel",new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int which) {
					dialog.cancel();
				}});
			alertDialog.show();
		}   
}
