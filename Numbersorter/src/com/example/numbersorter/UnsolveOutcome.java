package com.example.numbersorter;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

/**
 * Activity which tells the user if they made the right decision 
 * when they decided the board was unsolavable.
 * @author Ricky
 *
 */
public class UnsolveOutcome extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_unsolve_outcome);
		Bundle extras = getIntent().getExtras();
		boolean unsolveable = extras.getBoolean("unsolveable");
		
		TextView outcome = (TextView) findViewById(R.id.outcometext);
		
		if(unsolveable){ //produce the appropriate response
			
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
	
	/**
	 * called when the exit game button is pressed. Closes the activity.
	 * @param view
	 */
	public void exitGamePressed(View view){
		
		deleteFile(MainActivity.SAVED_GAME);
		setResult(2);
		finish();
	}



}
