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
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

public class SearchDoctorsActivity extends ActionBarActivity {
	
private static Button button1;
private static List<Doctor> doctorList;
private static String searchJob;
private static String searchGender;
private static String searchName;
private static Spinner spinnerDoctor;
//private static Spinner spinnerHospital;
private static Spinner spinnerGender;
private static Button button2;
private static Intent intent;
private static AutoCompleteTextView textView;
List<String> jobspec;
List<String> names;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		ActionBar actionBar = getSupportActionBar();
				actionBar.hide();
		setContentView(R.layout.activity_search_doctors);
		overridePendingTransition(R.layout.rotate_out, R.layout.rotatein);
		
		//adding first job spinner
		jobspec=new ArrayList<String>();
		names=new ArrayList<String>();
		DatabaseHandler db=new DatabaseHandler(this);
        db.getReadableDatabase();
        jobspec=db.getJobList();        
        names=db.getNamesList();
        db.close();
        if(jobspec.contains("All"))
        {
        	jobspec.remove("All");
        }
		Spinner spinner = (Spinner) findViewById(R.id.spinner1);
		
  		
		//array adapter for adding string
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,R.layout.spinner_item_text, jobspec);
	    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	    //calling nothingclass for setting the default value on spinner
	    spinner.setAdapter( new NothingSelectedSpinnerAdapter(adapter, R.layout.contact_spinner_row_nothing_selected,this));
	
	  //adding second gender spinner
	  		Spinner genderspinner = (Spinner) findViewById(R.id.Spinner02);
	  		String genderspec[]={"male","female"};
	  		//array adapter for adding string
	  		ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this,R.layout.spinner2_item_text,genderspec);
	  	    adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	  	    //calling nothingclass for setting the default value on spinner
	  	    genderspinner.setAdapter( new NothingSelectedSpinnerAdapter(adapter2, R.layout.contact_spinner2_row_nothing_selected,this));
       
	  	    
	  	  textView = (AutoCompleteTextView) findViewById(R.id.doctorNameautoComplete);
		  	// Get the string array
		  	//String[] countries = getResources().getStringArray(R.array.countries_array);
		  	  		  	  		  	  		  	  		  	  
	  		  // 	Create the adapter and set it to the AutoCompleteTextView 
  		  ArrayAdapter<String> nameadapter = 
              new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, names);
  		  textView.setAdapter(nameadapter);
		  	  	  	    	  	    	  	    	  	    
	 /* 	//adding hospital spinner
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
	  	*/    
	  	    
	  	    //setting home button. it directs to dashboard
	  	    button1=(Button)findViewById(R.id.button2);
			button1.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Intent i=new Intent(SearchDoctorsActivity.this,DashboardActivity.class);
					startActivity(i);
				}
			});
			//search operation: calling all the spinners 
			spinnerDoctor=(Spinner)findViewById(R.id.spinner1);
			//spinnerHospital=(Spinner)findViewById(R.id.Spinner01);
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
		searchName = textView.getText().toString();
		Toast.makeText(this, searchJob+" "+searchName, Toast.LENGTH_SHORT).show();
		DatabaseHandler db=new DatabaseHandler(this);
		db.getReadableDatabase();	        		
        //doctorList=db.getAllContacts(searchJob,searchGender,searchName,1);
        doctorList=db.getContact(searchName);
        if(doctorList!=null )
        {
        	for(int i=0;i<doctorList.size();i++)
        	{
        		if(doctorList.get(i).getjob().equals(searchJob) && 
        			doctorList.get(i).getgender().equals(searchGender))
        		{
        			String speciality=searchJob;
             		String gender=searchGender;
             		String hosp=doctorList.get(i).gethospital();
                  	intent.putExtra("speciality", speciality);
                  	intent.putExtra("gender", gender);
                  	intent.putExtra("hospital", hosp);
                  	Toast.makeText(this, searchJob+" "+searchName, Toast.LENGTH_SHORT).show();
                  	startActivity(intent);		        	  		        
        		}        		
        	}        				                			        	   		        			      
        }
        
	    else
	    {
	       	Toast.makeText(this, "There is no doctor of this category ", Toast.LENGTH_LONG).show();			     
	    }
        db.close();
	}
}
