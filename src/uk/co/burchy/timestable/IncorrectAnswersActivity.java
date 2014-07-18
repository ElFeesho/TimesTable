package uk.co.burchy.timestable;

import java.util.ArrayList;

import uk.co.burchy.timestable.model.Question;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class IncorrectAnswersActivity extends Activity {
	
	private	ArrayList<Question>	m_incorrectAnswerList;
	private ListView			m_iaListView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_incorrect_answers);

		// Stream the incorrect answers into an array list 
		m_incorrectAnswerList = getIntent ().getParcelableArrayListExtra(TestActivity.INCORRECT_ANSWERS);
		
		// Create a custom array adapter to build rows with multiple text views within each row to display incorrect question/answers
		ArrayAdapter<Question>	ia_arrayAdapter = new ArrayAdapter<Question> (this, R.layout.ia_row, m_incorrectAnswerList) {
			@Override
			public View getView (int position, View convertView, ViewGroup parent) {
				View row;
				
				if (convertView == null) {
					LayoutInflater inflater = (LayoutInflater)getContext ().getSystemService (Context.LAYOUT_INFLATER_SERVICE);

					row = inflater.inflate(R.layout.ia_row, parent, false);
				}
				else {
					row = convertView;
				}
				
				Question	aquestion 	= getItem (position);
				
				/*bindTextToId(row, aquestion.getQuestion().toString(), R.id.ia_question);
				bindTextToId(row, aquestion.getTable().toString(), R.id.ia_table);
				bindTextToId(row, aquestion.getAnswerGiven().toString(), R.id.ia_answer_given);
				bindTextToId(row, "(Correct = " + aquestion.getCorrectAnswer().toString() + ")", R.id.ia_correct_answer);
				*/
				return row;
			}

			private void bindTextToId(View row, String value, int id) {
				TextView field;
				field 		= (TextView) row.findViewById(id);
				field.setText(value);
			}
		};
		
		// Bind the view resources
		m_iaListView = (ListView) findViewById (R.id.ia_list);
		
		// Create a dynamic button, including the click handler
		Button button = new Button (this);
		button.setOnClickListener(new OnClickListener () {

			@Override
			public void onClick(View arg0) {
				setResult (RESULT_OK);
				finish ();	
			}
				
			});
		
		// Set the text to display on the button
		String buttonText = this.getResources().getString(R.string.ia_test_button);
		button.setText(buttonText);
		button.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
		
		// Set the button as the footer of the list view
		m_iaListView.addFooterView(button);
		
		// Finally set our custom array adapter in the list view 
		m_iaListView.setAdapter(ia_arrayAdapter);		
		
	}

	public void testIncorrect (View view) {
		
	}
}
