package com.example.numbersorter;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class Game extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Bundle extras = getIntent().getExtras();
		setContentView(R.layout.activity_game);
		
		int height = extras.getInt("height");
		int width = extras.getInt("width");
		
		TextView text = (TextView) findViewById(R.id.test_text);
		text.setText("Building board with height of " + height + " and width of " + width);
		
	}



}
