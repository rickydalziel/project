import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.AdapterView.OnItemSelectedListener;

public class NewGameDialog extends Activity implements OnItemSelectedListener {

	TextView selection;
	Spinner spin;
	String[] items = R.string.;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.android_spinner);

		selection = (TextView) findViewById(R.id.selection);

		Spinner spin = (Spinner) findViewById(R.id.spinner);
		spin.setOnItemSelectedListener(this);

		ArrayAdapter<String> aa = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, items);

		spin.setAdapter(aa);
	}

	@Override
	public void onItemSelected(AdapterView<?> parent, View v, int position, long id) {
		// TODO Auto-generated method stub
		selection.setText(items[position]);

	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		// TODO Auto-generated method stub
		selection.setText("");

	}
}