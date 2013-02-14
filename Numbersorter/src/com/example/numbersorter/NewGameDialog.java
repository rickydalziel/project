package com.example.numbersorter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

public class NewGameDialog extends Activity {

	TextView selection;
	Spinner heightspinner, widthspinner;
	

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.new_game_dialog);
	
		heightspinner = (Spinner) findViewById(R.id.heightspinner);
		// Create an ArrayAdapter using the string array and a default spinner layout
		ArrayAdapter<CharSequence> heightadapter = ArrayAdapter.createFromResource(this,
		        R.array.size_array, android.R.layout.simple_spinner_item);
		// Specify the layout to use when the list of choices appears
		heightadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// Apply the adapter to the spinner
		heightspinner.setAdapter(heightadapter);
		
		
		//do the same for the width spinner
		widthspinner = (Spinner) findViewById(R.id.widthspinner);
		ArrayAdapter<CharSequence> widthadapter = ArrayAdapter.createFromResource(this,
		        R.array.size_array, android.R.layout.simple_spinner_item);
		widthadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		widthspinner.setAdapter(widthadapter);
		
		
	}
	
	public void newGamePressed(View view){
		
		int height = Integer.parseInt(heightspinner.getSelectedItem().toString());
		int width = Integer.parseInt(widthspinner.getSelectedItem().toString());
		Intent i = new Intent(this, Game.class);
		i.putExtra("height", height);
		i.putExtra("width", width);
		startActivity(i);

	}
	
	
	public void cancelPressed(View view){
		
		this.finish();
		
	}

}