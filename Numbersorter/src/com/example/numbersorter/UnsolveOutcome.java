package com.example.numbersorter;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class UnsolveOutcome extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_unsolve_outcome);
		Bundle extras = getIntent().getExtras();
		boolean unsolveable = extras.getBoolean("unsolveable");
		
		TextView outcome = (TextView) findViewById(R.id.outcometext);
		
		if(unsolveable){
			
			outcome.setText("Correct! Click exit to finish game");
		}
		else{
			
			outcome.setText("Incorrect! Click exit to finish game");
		}
	}
	
	@Override
	public void onBackPressed() {
		
		setResult(2);
		finish();
	}
	
	public void exitGamePressed(View view){
		
		setResult(2);
		finish();
	}



}
