package com.example.docdig;

import java.util.List;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class DoctorListAdapter extends ArrayAdapter<String> {
	private final Context context;
	private final List<String> name;
	public final List<String> values;
	public final List<String> gender;
	public final List<String> hosp;
	
 
	public DoctorListAdapter(Context context,List<String> names, List<String> values,List<String> gender,
			List<String> hospit) {
		super(context, R.layout.activity_doctors_list, names);
		this.context = context;
		this.name=names;
		this.values = values;
		this.gender=gender;
		this.hosp=hospit;
	}	
 
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.activity_doctors_list, parent, false);
		TextView nameView = (TextView) rowView.findViewById(R.id.name);
		TextView textView = (TextView) rowView.findViewById(R.id.label);	
		ImageView imageView = (ImageView) rowView.findViewById(R.id.logo);
		
		nameView.setText(name.get(position));
		textView.setText(values.get(position)); 
		String s = gender.get(position); 		
		if(s.contains("female"))
		{
			imageView.setImageResource(R.drawable.doctor);
		}
		else if(s.contains("male"))
		{
			imageView.setImageResource(R.drawable.doctor);
		}		
 
		return rowView;
	}
}