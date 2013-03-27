package com.example.numbersorter;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.StreamCorruptedException;
import java.util.ArrayList;

import android.os.Bundle;
import android.app.Activity;
import android.graphics.Color;
import android.graphics.Typeface;

import android.view.Gravity;
import android.view.View;
import android.widget.TableRow.LayoutParams;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

/**
 * Displays the leaderboard on the screen
 * 
 * @author Ricky
 *
 */
public class LeaderboardActivity extends Activity {

	private Leaderboard board;
	private int currentSize = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_leaderboard);
		
		board = loadLeaderboard();
		ArrayList<LeaderboardEntry> entries = board.getEntries();
		
		// Get the TableLayout
        TableLayout tl = (TableLayout) findViewById(R.id.leaderboard);

        // Go through each item in the array
        for (int current = 0; current < entries.size(); current++)
        {
        	
        	LeaderboardEntry entry = entries.get(current);
        	
        	
        	if(entry.getBoardSize() != currentSize){
        		
        		//if it is a new grid size, we need a new header to show this
        		
        		TableRow tr = new TableRow(this);
                tr.setId(100+current);
                TableRow.LayoutParams params = new LayoutParams(
                        LayoutParams.WRAP_CONTENT,
                        LayoutParams.WRAP_CONTENT);
                params.span = 2;
                tr.setLayoutParams(params);   

                // Create a TextView to house the name of player
                TextView labelTV = new TextView(this);
                labelTV.setId(300+current);
                labelTV.setText("" + entry.getBoardSize() + " Elements");
                labelTV.setTextColor(Color.BLACK);
                labelTV.setGravity(Gravity.CENTER);
                labelTV.setTextSize(15);
                labelTV.setTypeface(null, Typeface.BOLD);
                labelTV.setLayoutParams(new LayoutParams(
                        LayoutParams.WRAP_CONTENT,
                        LayoutParams.WRAP_CONTENT));
                tr.addView(labelTV);
                
                tl.addView(tr, new TableLayout.LayoutParams(
                		LayoutParams.MATCH_PARENT,
                        LayoutParams.WRAP_CONTENT));
                
                currentSize = entry.getBoardSize(); //update the new boardsize
        		
        		
        	}
        	
        	int colour = 0;
        	
        	if(board.getEntries().get(current) == board.getLastAdded()){
        		
        		colour = Color.RED; //make the last added entry use red text
        		
        	}
        	else
        	{
        		colour = Color.BLACK;
        	}
            // Create a TableRow and give it an ID
            TableRow tr = new TableRow(this);
            tr.setId(100+current);
            tr.setLayoutParams(new LayoutParams(
                    LayoutParams.WRAP_CONTENT,
                    LayoutParams.WRAP_CONTENT));   

            // Create a TextView to house the name of player
            TextView labelTV = new TextView(this);
            labelTV.setId(200+current);
            labelTV.setText(entries.get(current).getName());
            labelTV.setTextColor(colour);
            labelTV.setGravity(Gravity.CENTER);
            labelTV.setLayoutParams(new LayoutParams(
                    LayoutParams.WRAP_CONTENT,
                    LayoutParams.WRAP_CONTENT));
            tr.addView(labelTV);

            // Create a TextView to house the moveCount
            TextView valueTV = new TextView(this);
            valueTV.setId(current);
            valueTV.setText(Integer.toString(entries.get(current).getMoveCount()));
            valueTV.setTextColor(colour);
            valueTV.setGravity(Gravity.CENTER);
            valueTV.setLayoutParams(new LayoutParams(
                    LayoutParams.WRAP_CONTENT,
                    LayoutParams.WRAP_CONTENT));
            tr.addView(valueTV);

            // Add the TableRow to the TableLayout
            tl.addView(tr, new TableLayout.LayoutParams(
            		LayoutParams.MATCH_PARENT,
                    LayoutParams.WRAP_CONTENT));
        }
		
		
	}

	/**
	 * loads the leaderboard from file
	 * @return the Leaderboard object containing the leaderboard
	 */
	private Leaderboard loadLeaderboard() {
		
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
			board = new Leaderboard();
		} catch (ClassNotFoundException e) {
			board = new Leaderboard();
		}
		
		return board;
	}
	

	/**
	 * called when the user presses the exit button. Returns them to the main menu
	 * 
	 * @param view
	 */
	public void exitGamePressed(View view){
		
		setResult(2);
		finish();
	}

	@Override
	public void onBackPressed(){
		
		setResult(2);
		finish();
	}
}
