package com.example.numbersorter;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StreamCorruptedException;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

public class WinActivity extends Activity {
	
	private int moveCount;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_win);
		
		Bundle extras = getIntent().getExtras();
		moveCount = extras.getInt("moveCount");
	}

	@Override
	public void onBackPressed() {

	}
	
	public void saveScorePressed(View view){
		
		EditText textEntry = (EditText) findViewById(R.id.enter_name);
		String name = textEntry.getText().toString();
		saveNewBoard(name);
		deleteFile(MainActivity.SAVED_GAME);
		Intent i = new Intent(this, LeaderboardActivity.class);
		startActivityForResult(i, 0);
		
	}
	
	private void saveNewBoard(String name){
		
		FileInputStream gameFile;
		ObjectInputStream ois;
		Leaderboard board;
		
		try {
			
			gameFile = openFileInput(MainActivity.LEADERBOARD_SAVE);
			ois = new ObjectInputStream(gameFile);
			board = (Leaderboard) ois.readObject();
			ois.close();
			
		} catch (FileNotFoundException e) {
			board = new Leaderboard();
		} catch (StreamCorruptedException e) {
			board = new Leaderboard();
		} catch (IOException e) {
			Log.d("Leaderboard exception", e.getMessage(), e);
			board = new Leaderboard();
		} catch (ClassNotFoundException e) {
			board = new Leaderboard();
		}
		
		board.addEntry(new LeaderboardEntry(name, moveCount));
		
		FileOutputStream fos;
		ObjectOutputStream oos;
		try {
			
			fos = openFileOutput(MainActivity.LEADERBOARD_SAVE, Context.MODE_PRIVATE);
			oos = new ObjectOutputStream(fos);
			oos.writeObject(board);
			oos.close();
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			Log.d("SaveIOerror", e.toString(), e);
		}
	}
	
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	    if(resultCode==2){
	    	setResult(2);
	        finish();
	    }
	}

}
