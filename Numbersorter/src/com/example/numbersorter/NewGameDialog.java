package com.example.numbersorter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.TextView;

/**
 * Represents the activity which is started when the new game button is pressed
 * on the main menu. Lets the user specify a grid size and whether they want only 
 * solvable grids before they start the game.
 * 
 * @author Ricky
 *
 */
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
	
	/**
	 * called when teh new game button is pressed, gets the information from the spinners
	 * and checkbox and starts game with that information.
	 * @param view
	 */
	public void newGamePressed(View view){
		
		int height = Integer.parseInt(heightspinner.getSelectedItem().toString());
		int width = Integer.parseInt(widthspinner.getSelectedItem().toString());
		CheckBox checkbox = (CheckBox) findViewById(R.id.checkbox_solve);
		boolean solvableOnly = checkbox.isChecked();
		Intent i = new Intent(this, Game.class);
		i.putExtra("height", height);
		i.putExtra("width", width);
		i.putExtra("solvableOnly", solvableOnly);
		startActivityForResult(i, 0);

	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	    if(resultCode==2){
	        finish();
	    }
	}
	
	
	public void cancelPressed(View view){
		
		this.finish();
		
	}

}