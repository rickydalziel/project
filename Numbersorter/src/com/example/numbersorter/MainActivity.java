package com.example.numbersorter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button loadbutton = (Button)findViewById(R.id.continue_game);
        if(!hasSavedGame()){
        	
        	
        	loadbutton.setVisibility(View.INVISIBLE);
        }
        else{
        	
        	loadbutton.setVisibility(View.VISIBLE);
        	
        }
    }

    private boolean hasSavedGame() {
    	
		return false;
		
	}

	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
    
    public void exitPressed(View v){
    	
    	finish();
    	System.exit(0);
    }
    
    public void aboutPressed(View v){
    	
    	Intent i = new Intent(this, AboutActivity.class);
    	startActivity(i);
    	
    }
}
