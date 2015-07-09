package com.example.docdig;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

public class DoctorsActivity extends ActionBarActivity {
private static Button button1;
private static List<Doctor> doctorList;
private static String searchJob;
private static String searchGender;
private static String searchHospital;
private static Spinner spinnerDoctor;
private static Spinner spinnerHospital;
private static Spinner spinnerGender;
private static Button button2;
private static Intent intent;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		ActionBar actionBar = getSupportActionBar();
				actionBar.hide();
		setContentView(R.layout.activity_doctors);
		overridePendingTransition(R.layout.rotate_out, R.layout.rotatein);
		
		//adding first job spinner
		Spinner spinner = (Spinner) findViewById(R.id.spinner1);
		List<String> jobspec=new ArrayList<String>();
  		DatabaseHandler db=new DatabaseHandler(this);
        db.getReadableDatabase();
        jobspec=db.getJobList();
        db.close();
		//array adapter for adding string
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,R.layout.spinner_item_text, jobspec);
	    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	    //calling nothingclass for setting the default value on spinner
	    spinner.setAdapter( new NothingSelectedSpinnerAdapter(adapter, R.layout.contact_spinner_row_nothing_selected,this));
	
	  //adding second gender spinner
	  		Spinner genderspinner = (Spinner) findViewById(R.id.Spinner02);
	  		String genderspec[]={"male","female","both"};
	  		//array adapter for adding string
	  		ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this,R.layout.spinner2_item_text,genderspec);
	  	    adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	  	    //calling nothingclass for setting the default value on spinner
	  	    genderspinner.setAdapter( new NothingSelectedSpinnerAdapter(adapter2, R.layout.contact_spinner2_row_nothing_selected,this));
       
	  	//adding hospital spinner
	  		Spinner hospitalspinner = (Spinner) findViewById(R.id.Spinner01);
	  		List<String> hospspec=new ArrayList<String>();
	  		DatabaseHandler db1=new DatabaseHandler(this);
	        db1.getReadableDatabase();
	        hospspec=db1.getHospitalList();
	        db1.close();
	  		//array adapter for adding string
	  		ArrayAdapter<String> adapter3 = new ArrayAdapter<String>(this,R.layout.spinner3_item_text,hospspec);
	  	    adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	  	    //calling nothingclass for setting the default value on spinner
	  	    hospitalspinner.setAdapter( new NothingSelectedSpinnerAdapter(adapter3, R.layout.contact_spinner3_row_nothing_selected,this));
	  	    
	  	    
	  	    //setting home button. it directs to dashboard
	  	  button1=(Button)findViewById(R.id.button2);
			button1.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Intent i=new Intent(DoctorsActivity.this,DashboardActivity.class);
					startActivity(i);
				}
			});
			//search operation: calling all the spinners 
			spinnerDoctor=(Spinner)findViewById(R.id.spinner1);
			spinnerHospital=(Spinner)findViewById(R.id.Spinner01);
			spinnerGender=(Spinner)findViewById(R.id.Spinner02);
			//initializing doctorlist,intent
			doctorList=new ArrayList<Doctor>();
			intent=new Intent(this,DoctorsList.class);
			//search button
			button2=(Button)findViewById(R.id.button1);
			button2.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					button_performed();
				}
			});
	
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.doctors, menu);
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
	//button for extracting data from database and showing on listview
	public void button_performed()
	{
		searchGender=(String)spinnerGender.getSelectedItem();
		searchJob = (String) spinnerDoctor.getSelectedItem();
		searchHospital = (String) spinnerHospital.getSelectedItem();
		DatabaseHandler db=new DatabaseHandler(this);
		db.getReadableDatabase();	        	        
        doctorList=db.getAllContacts(searchJob,searchGender,searchHospital);
       
        if(doctorList!=null )
        {	  	        		        	
        	String speciality=searchJob;
     		String gender=searchGender;
     		String hosp=searchHospital;
          	intent.putExtra("speciality", speciality);
          	intent.putExtra("gender", gender);
          	intent.putExtra("hospital", hosp);
          	Toast.makeText(this, searchJob+" "+searchHospital, Toast.LENGTH_SHORT).show();
          	startActivity(intent);		        	  		        			        
        			        	   		        			       
        }
        
	    else
	    {
	       	Toast.makeText(this, "There is no doctor of this category ", Toast.LENGTH_LONG).show();			     
	    }
        db.close();
	}
}
