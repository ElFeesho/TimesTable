package uk.co.burchy.timestable;

import uk.co.burchy.timestable.model.TestBuilder;
import uk.co.burchy.timestable.model.TestBuilder.TestBuilderConfigurationException;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity
{

	public final static String	SELECTED_NUM_QUESTIONS	= "uk.co.burchy.timestable.SELECTED_NUM_QUESTIONS";
	public final static String	SELECTED_TIMES_TABLES	= "uk.co.burchy.timestable.SELECTED_TIMES_TABLES";

	private final Integer		DEFAULT_NUM_QUESTIONS	= 12;

	private Spinner				m_spinnerNumQuestions;
	private GridView			m_listSelectedTables;
	private TextView			m_totalQuestions;

	private TestBuilder			m_testBuilder;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		m_testBuilder = new TestBuilder();

		findViewById(R.id.tt_start_test).setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				startTest();
			}
		});

		// Find the spinner that will be used to specify number of questions
		m_spinnerNumQuestions 	= (Spinner) findViewById(R.id.tt_spinner_no_questions);
		m_listSelectedTables 	= (GridView) findViewById(R.id.tt_tables_to_test);
		m_totalQuestions 		= (TextView) findViewById(R.id.tt_total_questions);
		
		bindQuestionCount();
		bindTimesTables();
		
		updateTotalQuestionsLabel();
	}

	private void bindTimesTables()
	{
		String[] availableTables = this.getResources().getStringArray(R.array.tt_available_tables);
		ArrayAdapter<String> ouradapter = new ArrayAdapter<String>(this, R.layout.li_table, android.R.id.text1, availableTables);
		m_listSelectedTables.setAdapter(ouradapter);
		m_listSelectedTables.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);

		m_listSelectedTables.setOnItemClickListener(new AdapterView.OnItemClickListener()
		{
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id)
			{
				updateTotalQuestionsLabel();
				m_listSelectedTables.refreshDrawableState();
				
				view.animate().rotationYBy(360.0f).setDuration(600).start();

			}
		});
	}

	private void bindQuestionCount()
	{
		ArrayAdapter<CharSequence> numQuestionsAdapter = ArrayAdapter.createFromResource(this, R.array.tt_number_questions, android.R.layout.simple_spinner_item);
		numQuestionsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		m_spinnerNumQuestions.setAdapter(numQuestionsAdapter);

		m_spinnerNumQuestions.setSelection(DEFAULT_NUM_QUESTIONS - 1);

		m_spinnerNumQuestions.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
		{

			@Override
			public void onItemSelected(AdapterView<?> parentView, View selectedView, int position, long id)
			{
				updateTotalQuestionsLabel();
			}

			@Override
			public void onNothingSelected(AdapterView<?> adapterView)
			{
				return;
			}
		});
	}

	/** Called when the user presses the start button */
	public void startTest()
	{
		SparseBooleanArray checkedItems = m_listSelectedTables.getCheckedItemPositions();

		if (getNumberCheckedItems() > 0)
		{

			// Create an intent to start the test activity and pass in the
			// currently select times table
			Intent intent = new Intent(this, TestActivity.class);

			int numberOfQuestions = Integer.parseInt((String)m_spinnerNumQuestions.getSelectedItem());

			m_testBuilder.setNumberOfQuestions(numberOfQuestions);
			
			for (int i = 0; i < checkedItems.size(); i++)
			{
				String itemAtPosition = (String) m_listSelectedTables.getItemAtPosition(i);
				m_testBuilder.addTable(Integer.parseInt(itemAtPosition));
			}
			
			try
			{
				startActivity(TestActivity.intentForTest(this, m_testBuilder.build()));
			}
			catch (TestBuilderConfigurationException e)
			{
				e.printStackTrace();
				// FLW: Should never happen
				Toast.makeText(this, "Please select tables and a number of questions", Toast.LENGTH_LONG).show();
			}
		}
	}

	private void updateTotalQuestionsLabel()
	{
		// String totalQuestionsText =
		// this.getResources().getString(R.string.tt_total_questions);
		String questionsSetBySpinner = (String) m_spinnerNumQuestions.getSelectedItem();

		Integer totalQuestions = Integer.valueOf(questionsSetBySpinner) * getNumberCheckedItems();

		m_totalQuestions.setText(" " + totalQuestions.toString());
	}

	private Integer getNumberCheckedItems()
	{
		SparseBooleanArray checkedItems = m_listSelectedTables.getCheckedItemPositions();
		Integer numCheckedItems = 0;

		for (int i = 0; i < checkedItems.size(); i++)
		{
			if (checkedItems.valueAt(i))
			{
				numCheckedItems++;
			}
		}

		return numCheckedItems;
	}

}
