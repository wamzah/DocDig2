package com.example.docdig;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ProgressBar;

public class Landingpage extends ActionBarActivity {
private static DatabaseHandler db;
private static ProgressBar  mProgressBar;
protected static final int TIMER_RUNTIME = 10000; // in ms —> 10s

protected boolean mbActive;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_landingpage);
		ActionBar actionbar=getSupportActionBar();
		actionbar.hide();
		db=new DatabaseHandler(this);
		
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
	        }
	     //end of that method
	

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
	}
	//loading database data
	public void doWork()
	{
		db.getWritableDatabase();
        db.deleteTable(db.getWritableDatabase());                
        //33.711778, 73.057217  current location
        db.addContact("Ali", "Dentist", "male", 33.676477, 73.066024, "0344 553 2806","Shifa International","Specialist");  //Shifa
        db.addContact("Ali", "Dentist", "male", 33.702456, 73.053946, "0344 553 2806","PIMS","Specialist");  //Shifa        
        db.addContact("Ali", "Orthopedics", "male", 33.702456, 73.053946, "0344 553 2806","PIMS","Specialist");  //Shifa
        db.addContact("Omer", "Dentist", "male", 33.702456, 73.053946, "0344 553 2806","PIMS","Specialist");  //PIMS
        db.addContact("Rabia", "Dentist", "female", 33.702456, 73.053946, "0344 553 2806","PIMS","Specialist");  //PAEC 
        db.addContact("Alia", "Cardiologist", "female", 33.702456, 73.053946, "0344 553 2806","PIMS","Specialist"); //NESCOM
        db.addContact("Wamzay", "Orthopedics", "female", 33.702456, 73.053946, "0344 553 2806","PIMS","Specialist"); //CMH
        db.addContact("Usman", "Dentist", "male", 33.702456, 73.053946, "0344 553 2806","PIMS","Specialist");  
        db.addContact("Nauman", "Eye Specialist", "male", 33.702456, 73.053946, "0344 553 2806","PIMS","Specialist");  //Holy Family
        db.addContact("Irfan", "Eye Specialist", "male", 33.702456, 73.053946, "0344 553 2806","PIMS","Specialist");  
        db.addContact("Bilal", "Eye Specialist", "male", 33.702456, 73.053946, "0344 553 2806","PIMS","Specialist");          
        db.addContact("Zeeshan", "NeuroSurgeon", "male", 33.702456, 73.053946, "0344 553 2806","PIMS","Specialist"); //CMH Peshawar
        db.addContact("Fatima", "Orthopedics", "female", 33.554166, 73.095568, "0344 553 2806","Fauji Foundation","Specialist");
        db.close();        
        
	}
}
