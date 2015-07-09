package com.example.docdig;

import android.app.TabActivity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TabHost.TabSpec;
import android.widget.TextView;

public class MainActivity extends TabActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		final TabHost tabHost = getTabHost();
        
		// Tab for Doctors
        TabSpec doctors = tabHost.newTabSpec("Doctors");
        // setting Title and Icon for the Tab
        doctors.setIndicator(prepareTabView("Deals",R.drawable.r));
        Intent doctorsIntent = new Intent(this, DoctorsActivity.class);
        doctors.setContent(doctorsIntent);
        tabHost.addTab(doctors);
       
        
         // Tab for Hospitals
        TabSpec hospitals = tabHost.newTabSpec("Hospitals");        
        hospitals.setIndicator(prepareTabView("Deals",R.drawable.hospitallisticon));
        Intent hospitalsIntent = new Intent(this, HospitalsActivity.class);
        hospitals.setContent(hospitalsIntent);
        tabHost.addTab(hospitals);
        
        // Tab for Specialists
        TabSpec specialists = tabHost.newTabSpec("Specialists");
        specialists.setIndicator(prepareTabView("Deals",R.drawable.hospitalslogo));
        Intent specialistsIntent = new Intent(this, SpecialityActivity.class);
        specialists.setContent(specialistsIntent);
        tabHost.addTab(specialists); // Adding videos tab
        
        //On click listener,for flipping annimation as well as changing background color
        tabHost.setOnTabChangedListener(new OnTabChangeListener() {
            @Override
            public void onTabChanged(String arg0) {
            	int currentTab=getTabHost().getCurrentTab();
                setTabColor(tabHost);
                View currentView = getTabHost().getCurrentView();
                if (getTabHost().getCurrentTab() > currentTab)
                {
                    currentView.setAnimation( inFromRightAnimation() );
                }
                else
                {
                    currentView.setAnimation( outToRightAnimation() );
                }

                currentTab = getTabHost().getCurrentTab();
            }
             });
             setTabColor(tabHost);
      
        }
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
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
	
	//this places the image in the tab
	private View prepareTabView(String text, int resId) {
	    View view = LayoutInflater.from(this).inflate(R.layout.tabs, null);
	    ImageView iv = (ImageView) view.findViewById(R.id.TabImageView);
	    TextView tv = (TextView) view.findViewById(R.id.TabTextView);
	    iv.setImageResource(resId);
	    tv.setText(text);
	    return view;
	}
	//this method sets the background color on selection as well as not selection 
	public void setTabColor(TabHost tabhost) {

	    for(int i=0;i<tabhost.getTabWidget().getChildCount();i++)
	        tabhost.getTabWidget().getChildAt(i).setBackgroundColor(Color.parseColor("#808080")); //unselected

	   
	           tabhost.getTabWidget().getChildAt(tabhost.getCurrentTab()).setBackgroundColor(Color.parseColor("#1568B1")); //1st tab selected
	    
	}
	//this method adds animation which we have used for flipping
	public Animation inFromRightAnimation()
	{
	    Animation inFromRight = new TranslateAnimation(
	            Animation.RELATIVE_TO_PARENT, +1.0f,
	            Animation.RELATIVE_TO_PARENT, 0.0f,
	            Animation.RELATIVE_TO_PARENT, 0.0f,
	            Animation.RELATIVE_TO_PARENT, 0.0f);
	    inFromRight.setDuration(240);
	    inFromRight.setInterpolator(new AccelerateInterpolator());
	    return inFromRight;
	}
	public Animation outToRightAnimation()
	{
	    Animation outtoLeft = new TranslateAnimation(
	            Animation.RELATIVE_TO_PARENT, -1.0f,
	            Animation.RELATIVE_TO_PARENT, 0.0f,
	            Animation.RELATIVE_TO_PARENT, 0.0f,
	            Animation.RELATIVE_TO_PARENT, 0.0f);
	    outtoLeft.setDuration(240);
	    outtoLeft.setInterpolator(new AccelerateInterpolator());
	    return outtoLeft;
	}
}
