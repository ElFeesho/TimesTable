package uk.co.burchy.timestable;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

public class MainActivity extends Activity {

	public final static String 	SELECTED_NUM_QUESTIONS = "uk.co.burchy.timestable.SELECTED_NUM_QUESTIONS";
	public final static String 	SELECTED_TIMES_TABLES = "uk.co.burchy.timestable.SELECTED_TIMES_TABLES";
	
	private final		Integer	DEFAULT_NUM_QUESTIONS = 12;
	
	private 	Spinner		m_spinnerNumQuestions;
	private		GridView	m_listSelectedTables;
	private		TextView	m_totalQuestions;
	
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        findViewById(R.id.tt_start_test).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				startTest(v);
			}
		});
        
        // Create the spinner to specify number of questions
        m_spinnerNumQuestions  = (Spinner)  findViewById (R.id.tt_spinner_no_questions);
        ArrayAdapter<CharSequence> numQuestionsAdapter = ArrayAdapter.createFromResource(this, R.array.tt_number_questions, android.R.layout.simple_spinner_item);
        numQuestionsAdapter.setDropDownViewResource (android.R.layout.simple_spinner_dropdown_item);
        m_spinnerNumQuestions.setAdapter (numQuestionsAdapter);
        
        // Set a default for the number of questions (-1 for array index adjustment)
        m_spinnerNumQuestions.setSelection(DEFAULT_NUM_QUESTIONS - 1);       
        
        // Listen to spinner events so can update total number of questions selected
        m_spinnerNumQuestions.setOnItemSelectedListener (new AdapterView.OnItemSelectedListener () {
        	
        	@Override
        	public void onItemSelected (AdapterView<?> parentView, View selectedView, int position, long id) {
        		setTotalQuestions ();
        	}
        	
        	@Override
        	public void onNothingSelected(AdapterView<?> adapterView) {
                return;
        	}
        });

        // Create the list view to select which times tables to test on
        m_listSelectedTables = (GridView) findViewById (R.id.tt_tables_to_test);
        String[] availableTables = this.getResources().getStringArray(R.array.tt_available_tables);
        ArrayAdapter<String> ouradapter = new ArrayAdapter<String> (this,android.R.layout.select_dialog_multichoice,android.R.id.text1, availableTables);
        m_listSelectedTables.setAdapter (ouradapter);
        m_listSelectedTables.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        
        m_listSelectedTables.setOnItemClickListener(new AdapterView.OnItemClickListener() {
        	@Override
        	public void onItemClick (AdapterView<?> parent, View view, int position, long id) {
        		setTotalQuestions ();
        	}
		});
        
        m_totalQuestions = (TextView) findViewById (R.id.tt_total_questions);
        setTotalQuestions ();
    }
        
    
    //TODO - Change times table array to an integer array 
    //TODO - Persist the last selected times table from the spinner
    
    /** Called when the user presses the start button */
    public void startTest (View view) {
    	SparseBooleanArray checkedItems = m_listSelectedTables.getCheckedItemPositions();
    	
    	if (getNumberCheckedItems () > 0) {
    	
	    	// Create an intent to start the test activity and pass in the currently select times table
	    	    Intent intent = new Intent (this, TestActivity.class);

	    	    // Store the number of questions per times table
	    	    intent.putExtra(SELECTED_NUM_QUESTIONS, (String)m_spinnerNumQuestions.getSelectedItem());
	    	    
	    	    ArrayList<Integer> selectedTables = new ArrayList<Integer> ();
	    	    
	    	    // Next serialise the selected times tables to be tested on.  Iterate though retrieved checked items array
	    	    for (int i=0; i<checkedItems.size(); i++) {
	    			if (checkedItems.valueAt(i)) {
	    				int keyAt = checkedItems.keyAt(i);
						String itemAtPosition = (String)m_listSelectedTables.getItemAtPosition(keyAt);
	    				selectedTables.add (Integer.valueOf(itemAtPosition));

						Log.d("checked item: " + itemAtPosition, "CLUCK");
	    			}
	    		}
	    	    intent.putExtra(SELECTED_TIMES_TABLES, selectedTables);
	    	    startActivity (intent);
    	}
    }
    
    private void setTotalQuestions () {
//    	String totalQuestionsText 		= this.getResources().getString(R.string.tt_total_questions);
    	String questionsSetBySpinner 	= (String) m_spinnerNumQuestions.getSelectedItem ();
    	
    	Integer	totalQuestions			= Integer.valueOf(questionsSetBySpinner) * getNumberCheckedItems ();    	    	
    	
    	m_totalQuestions.setText(" " + totalQuestions.toString());
    }

    private Integer getNumberCheckedItems () {
    	SparseBooleanArray	checkedItems 	= m_listSelectedTables.getCheckedItemPositions();
    	Integer				numCheckedItems = 0;
    	
    	for (int i=0; i<checkedItems.size(); i++) {
    		if (checkedItems.valueAt(i))
    			numCheckedItems++;
		}
    	
    	return numCheckedItems;
    }

}
