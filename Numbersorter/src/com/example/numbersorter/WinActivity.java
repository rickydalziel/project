package com.example.numbersorter;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;

public class WinActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_win);
	}

	@Override
	public void onBackPressed() {

	}
	
	public void saveScorePressed(View view){
		
		deleteFile(MainActivity.SAVED_GAME);
		setResult(2);
		finish();
	}

}
