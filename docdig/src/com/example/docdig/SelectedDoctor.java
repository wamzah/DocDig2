package com.example.docdig;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class SelectedDoctor extends ActionBarActivity {

	private static Button icon;
	private static Button call;
	private static TextView main;
	private static TextView name;
	private static TextView spec;
	private static TextView gender;
	private static TextView hospital;
	private static TextView timings;
	private static TextView experience;
			
	private static Doctor doc;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		ActionBar actionBar=getSupportActionBar();
		actionBar.hide();
		setContentView(R.layout.activity_selected_doctor);		
		
		//call = (Button)findViewById(R.id.button2);
		name=(TextView)findViewById(R.id.textView1);
		spec=(TextView)findViewById(R.id.textView3);		
		gender=(TextView)findViewById(R.id.TextView02);
		hospital=(TextView)findViewById(R.id.textView8);
		timings=(TextView)findViewById(R.id.textView9);
		experience=(TextView)findViewById(R.id.textView10);
		icon = (Button)findViewById(R.id.button1);
		
		Bundle bundle = getIntent().getExtras();
		doc=new Doctor();
		doc = bundle.getParcelable("doctor");
		if(doc.gender.equals("male"))
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
		name.setText(doc.getname());
		spec.setText(doc.getjob());
		gender.setText(doc.getgender());
		hospital.setText(doc.gethospital());
		timings.setText("9-5");
		experience.setText("N.A");

		//Toast.makeText(this, doc.getname(), Toast.LENGTH_SHORT).show();
	}

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
}
