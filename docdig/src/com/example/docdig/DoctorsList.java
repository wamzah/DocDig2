package com.example.docdig;

import java.util.ArrayList;
import java.util.List;

import android.support.v7.app.ActionBarActivity;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

public class DoctorsList extends ListActivity {
	String speciality;
	String gender;
	String hosp;
	String name;
	DoctorListAdapter DocAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//setContentView(R.layout.activity_doctor_list);
		 List<String> jobList =new ArrayList<String>();
		 List<String> nameList =new ArrayList<String>();
		 List<String> genderList =new ArrayList<String>();
		 List<String> hospitalList =new ArrayList<String>();
		 List<Doctor> doctorList =new ArrayList<Doctor>();
		 
		Intent intent=getIntent();
		speciality=intent.getExtras().getString("speciality");
		if(!speciality.equals("None"))
		{
			gender=intent.getExtras().getString("gender");
			hosp=intent.getExtras().getString("hospital");
			DatabaseHandler db=new DatabaseHandler(this);
			db.getWritableDatabase();		
			doctorList=db.getAllContacts(speciality,gender,hosp);			
			db.close();
		}
		else 
		{
			//Bundle bundle = getIntent().getExtras();			
			doctorList = getIntent().getParcelableArrayListExtra("doctor");
		}
		 		 				
		
		for(int i=0;i<doctorList.size();i++)
		{
			nameList.add(doctorList.get(i).getname());
			jobList.add("\n"+doctorList.get(i).getjob());
			genderList.add(doctorList.get(i).getgender());
			hospitalList.add(doctorList.get(i).gethospital());
		}
		//jobList=db.getJobList();
		DocAdapter=new DoctorListAdapter(this, nameList,jobList,genderList,hospitalList);
		setListAdapter(DocAdapter);
	}
	
	protected void onListItemClick(ListView l, View v, int position, long id) { 
		//get selected items
		//String selectedValue = (String) getListAdapter().getItem(position);
		String selectedValue = (String) getListAdapter().getItem(position);			
		String spec = DocAdapter.values.get(position);
		String hospital = DocAdapter.hosp.get(position);		
		DatabaseHandler db=new DatabaseHandler(this);
		db.getWritableDatabase();
		List<Doctor>doctorList=db.getContact(selectedValue);
		db.close();
        if(doctorList!=null )
        {
        	
        	for(int i=0;i<doctorList.size();i++)
        	{        		
        		if(spec.contains(doctorList.get(i).getjob()) &&         		
            			doctorList.get(i).gethospital().equals(hospital))
        		{
        			Toast.makeText(this,doctorList.get(i).getname(), Toast.LENGTH_SHORT).show();
        			Intent intent=new Intent(DoctorsList.this,SelectedDoctor.class);
        			Bundle bun=new Bundle();
        	    	bun.putParcelable("doctor", doctorList.get(i));
        	    	intent.putExtras(bun);
        	    	Toast.makeText(this, doctorList.get(i).getname(), Toast.LENGTH_SHORT).show();
        	    	this.startActivity(intent);
        		}
    		}
        	/*if(!((speciality.equals("All") || gender.equals("both") || hosp.equals("All"))))
    		{
	        	for(int i=0;i<doctorList.size();i++)
	        	{        		
	        		if(doctorList.get(i).getjob().equals(speciality) && 
	        			doctorList.get(i).getgender().equals(gender) && 
	        			doctorList.get(i).gethospital().equals(hosp))
	        		{
	        			Intent intent=new Intent(DoctorsList.this,SelectedDoctor.class);
	        			Bundle bun=new Bundle();
	        	    	bun.putParcelable("doctor", doctorList.get(i));
	        	    	intent.putExtras(bun);
	        	    	Toast.makeText(this, doctorList.get(i).getname(), Toast.LENGTH_SHORT).show();
	        	    	this.startActivity(intent);
	        		}
        		}
    		}
        	else if(((speciality.equals("All") && gender.equals("both") && hosp.equals("All"))))
        	{            	        		
   	        	for(int i=0;i<doctorList.size();i++)
   	        	{            		
   	        		if(doctorList.get(i).getname().equals(selectedValue) && 
   	        			doctorList.get(i).getjob().equals(spec) && 
   	        			doctorList.get(i).gethospital().equals(hospital)
   	        				)
   	        		{
    	        		Intent intent=new Intent(DoctorsList.this,SelectedDoctor.class);
    	        		Bundle bun=new Bundle();
    	        	   	bun.putParcelable("doctor", doctorList.get(i));
    	        	    intent.putExtras(bun);
    	            	Toast.makeText(this, doctorList.get(i).getname(), Toast.LENGTH_SHORT).show();
    	            	this.startActivity(intent);
    	        	}
            	}
        	}
        		
        	else
        	{
        		
        		db.getWritableDatabase();
	    		Doctor doc=db.getOneContact(selectedValue);
	    		db.close();
	    		Intent intent=new Intent(DoctorsList.this,SelectedDoctor.class);
	    		Bundle bun=new Bundle();
	        	bun.putParcelable("doctor", doc);
	        	intent.putExtras(bun);
	        	Toast.makeText(this, doc.getname(), Toast.LENGTH_SHORT).show();
	        	this.startActivity(intent);
        	}*/
        	
        }
        else
        	Toast.makeText(this, "Null doctor Lit", Toast.LENGTH_SHORT).show();
		
    	
		
		//
 
	}

}
