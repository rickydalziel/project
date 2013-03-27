package com.example.numbersorter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Represents the dialog box which is displayed when the unsolvable button is pressed
 * 
 * @author Ricky
 *
 */
public class UnsolveDialog extends Activity {
	
	private boolean unsolvable;
	private Button yesbutton, quitbutton, cancelbutton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_unsolve_dialog);
		Bundle extras = getIntent().getExtras();

		unsolvable = extras.getBoolean("unsolveable");
		

	}
	
	/**
	 * called when the cancel button is pressed, closes the activity
	 * @param v
	 */
	public void cancelPressed(View v){
		
		finish();
		
	}
	
	/**
	 * called when the yes button is pressed, starts a new activity showing the outcome of the check.
	 * @param v
	 */
	public void yesPressed(View v){
		
		Intent i = new Intent(this, UnsolveOutcome.class); //create the intent based off of the unsolve outcome class
		i.putExtra("unsolveable", unsolvable);
    	startActivityForResult(i, 0); //start it which calls onCreate() in the unsolve outcome class, which in turn will display it.

	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	    if(resultCode==2){
	    	setResult(2);
	        finish();
	    }
	}



}
