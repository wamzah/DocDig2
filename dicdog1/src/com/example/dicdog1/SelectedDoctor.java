package com.example.dicdog1;

import java.util.List;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.parse.FindCallback;
import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;

public class SelectedDoctor extends ActionBarActivity {

	private static ImageButton icon;
	private static Button call;
	private static Button Schedule;
	private static TextView main;
	private static TextView name;
	private static TextView spec;
	private static TextView gender;
	private static TextView hospital;
	private static TextView timings;
	private static TextView experience;
	private static EditText edit1;
	private static Button button1;
	private static ImageView callimage;
	private static Doctor doc;
	private static String schedule;
	private static String mainSchedule;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		ActionBar actionBar=getSupportActionBar();
		actionBar.hide();
		setContentView(R.layout.activity_selected_doctor);		
		/*Button home=(Button) findViewById(R.id.homebitton);
		home=(Button)findViewById(R.id.button2);
		home.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i=new Intent(SelectedDoctor.this,DashboardActivity.class);
				startActivity(i);
			}
		});*/
		//call = (Button)findViewById(R.id.button2);
		callimage=(ImageView)findViewById(R.id.imageView2);
		callimage.setClickable(true);
		callimage.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String number = "tel:" + "051 111 644 911";				
				Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse(number));
			    startActivity(intent);
			}
		});
		
		name=(TextView)findViewById(R.id.textView1);
		spec=(TextView)findViewById(R.id.textView3);		
		gender=(TextView)findViewById(R.id.TextView02);
		hospital=(TextView)findViewById(R.id.textView8);
		timings=(TextView)findViewById(R.id.textView10);
		experience=(TextView)findViewById(R.id.textView9);
		icon = (ImageButton)findViewById(R.id.ImageButton);
		icon.setClickable(false);
		Schedule=(Button)findViewById(R.id.buttonschedule);
		Schedule.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String sched="";
				schedule=mainSchedule;				
				//Toast.makeText(getApplicationContext(),"name: "+name.getText()+"\nSched: "+schedule, Toast.LENGTH_LONG).show();
				while(true)
				{				
					if(schedule==null || schedule.equals("end"))
						break;									
					if(schedule.contains("/"))
					{
						int index=schedule.indexOf("/");
						String temp="",end="";
						temp+="\n"+schedule.substring(0, 3);
						end+=schedule.substring(4, index-1);
						sched+=temp;
						sched+="            "+end;
						//Toast.makeText(getApplicationContext(),"\nSched: "+sched, Toast.LENGTH_LONG).show();
						if((schedule=schedule.substring(index+1)).equals("/"))
							break;							
						//Toast.makeText(getApplicationContext(),"\nSched: "+schedule, Toast.LENGTH_LONG).show();
					}
					else
						break;
				}
				AlertDialog.Builder alertDialog = new AlertDialog.Builder(SelectedDoctor.this);								
				alertDialog.setIcon(R.drawable.schedule).setTitle("Schedule")
	            .setMessage("\n"+sched+"\n\n")	            
	            .setPositiveButton("Ok", null).show();
			}
		});
		button1=(Button)findViewById(R.id.homebitton);
		button1.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i=new Intent(SelectedDoctor.this,DashboardActivity.class);
				startActivity(i);
			}
		});
		Intent intent=getIntent();
		String selectedValue=intent.getExtras().getString("doctor");
		//Toast.makeText(getApplicationContext(), selectedValue, Toast.LENGTH_LONG).show();
		ParseQuery<ParseObject> query = ParseQuery.getQuery("DoctorsTable");
		query.whereEqualTo("Name", selectedValue);
		query.findInBackground(new FindCallback<ParseObject>() {
			@Override
			public void done(List<ParseObject> la,
					com.parse.ParseException e) {
				// TODO Auto-generated method stub
				 if(la!=null){
					 //Toast.makeText(getApplicationContext(),"Name: "+la.get(0).getString("Name"), Toast.LENGTH_LONG).show();
					 name.setText("Dr. " + la.get(0).getString("Name"));
						spec.setText(la.get(0).getString("Job"));
						gender.setText(la.get(0).getString("Gender"));
						hospital.setText(la.get(0).getString("Hospital"));
						mainSchedule=la.get(0).getString("Scehdule");
						//Toast.makeText(getApplicationContext(),"name: "+la.get(0).getString("Name")+"\nSched: "+la.get(0).getString("Scehdule"), Toast.LENGTH_LONG).show();
						//timings.setText("9-5");
						experience.setMovementMethod(new ScrollingMovementMethod());
						experience.setText(la.get(0).getString("Qualifications"));
						ParseFile fileObject = (ParseFile) la.get(0).get("Images");
						fileObject.getDataInBackground(new GetDataCallback() {									
									@Override
									public void done(byte[] arg0,
											ParseException arg1) {
										// TODO Auto-generated method stub
										if (arg1 == null) {
											//Log.d("test","We've got data in data.");
											// Decode the Byte[] into
											// Bitmap
											Bitmap bmp = BitmapFactory.decodeByteArray(arg0, 0,arg0.length);
                                            BitmapDrawable b=new BitmapDrawable(getResources(),bmp);
											icon.setBackgroundDrawable(b);
    										//icon.setImageDrawable(b);
											//icon.setImageBitmap(bmp);
											
										}
										
									}
						
					
                         });}
                  else{//handle the error}
                	  //Toast.makeText(getApplicationContext(),"hello valuesdf", Toast.LENGTH_LONG).show();
                            }
				
			}
		});
	}
		
		/*if(doc.gender.equals("male"))
		{
			icon.setBackgroundResource(R.drawable.doctor);
		}
		else if(doc.gender.equals("female"))
		{
			icon.setBackgroundResource(R.drawable.doctor);
		}
		icon.setClickable(false);
		/*call.setBackgroundResource(R.drawable.doctor);
		call.setOnClickListener(new View.OnClickListener() {	
			@Override
			public void onClick(View v) {				
				// TODO Auto-generated method stub
				String num="";
		        num=doc.getphone();
		        String number = "tel:" + num.trim();				
				Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse(number));
			    startActivity(intent);
			}
		});*/
		
		//Toast.makeText(this, doc.getname(), Toast.LENGTH_SHORT).show();
	

	/*@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.selected_doctor, menu);
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
	
	public void onBackPressed() {
      //  Intent intent = new Intent(this, DashboardActivity.class);
        //startActivity(intent);
        finish();

	} 
}
