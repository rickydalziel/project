package com.example.numbersorter;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class WinActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_win);
	}

	public void exitPressed(){
		
		setResult(2);
		finish();
		
	}

}
