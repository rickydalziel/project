package com.example.numbersorter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

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
	
	public void cancelPressed(View v){
		
		finish();
		
	}
	
	public void yesPressed(View v){
		
		Intent i = new Intent(this, UnsolveOutcome.class); //create the intent based off of the about class
		i.putExtra("unsolveable", unsolvable);
    	startActivityForResult(i, 0); //start it which calls onCreate() in the about class, which in turn will display it.

	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	    if(resultCode==2){
	    	setResult(2);
	        finish();
	    }
	}



}
