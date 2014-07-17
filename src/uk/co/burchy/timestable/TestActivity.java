package uk.co.burchy.timestable;

import java.util.ArrayList;

import uk.co.burchy.timestable.StreakViewController.StreakView;
import uk.co.burchy.timestable.view.NumPadView;
import uk.co.burchy.timestable.view.NumPadView.NumPadViewListener;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.view.animation.ScaleAnimation;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class TestActivity extends Activity {
	
	private static final String KEY_TEST = "test";
	public final static 	String 	INCORRECT_ANSWERS 			= "uk.co.burchy.timestable.INCORRECT_ANSWERS";
	public final static 	String 	NUM_INCORRECT_ANSWERS 		= "uk.co.burchy.timestable.NUM_INCORRECT_ANSWERS";
	public final static 	Integer	TEST_ON_INCORRECT_ANSWERS	= 0;
	private final static	Integer	DELAY_AFTER_ANIMATION		= 250;

	private	Integer				m_selectedNumQuestions;
	private ArrayList<Integer>	m_selectedTables;
	private	TestFactory			m_testFactory;
	private	Test				m_test;
	private	TextView			m_questionNumber;
	private TextView			m_questionField;
	private	TextView			m_correctAnswers;
	private	TextView			m_incorrectAnswers;
	private	EditText			m_answerField;
	private	Question			m_currentQuestion;
	private	Button				m_restartButton;
	private NumPadView			m_numPad;
	private Button				m_newTestButton;
	private Button				m_showIncorrectAnswersButton;
	private Animation			m_animator;
	private ScaleAnimation		m_zoomAnimator;
	private	Handler 			m_handler;
	private	Runnable			m_runnableSetNextQuestion;
	
	private StreakViewController m_streakViewController;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.test_activity);
		// Show the Up button in the action bar.
		// getActionBar().setDisplayHomeAsUpEnabled(true);
		
		// Serialise all the data back out of the intent
		Intent intent = getIntent ();
		m_selectedNumQuestions = Integer.parseInt(intent.getStringExtra (MainActivity.SELECTED_NUM_QUESTIONS));
		m_selectedTables = intent.getIntegerArrayListExtra(MainActivity.SELECTED_TIMES_TABLES);
		
		bindView();
		
		m_numPad.setNumPadViewListener(new NumPadViewListener(){

			@Override
			public void numPadClicked(int number) {
				m_answerField.append(number+"");
			}

			@Override
			public void numPadClear() {
				m_answerField.setText(null);
			}

			@Override
			public void numPadBackspace() {
				Editable answerText = m_answerField.getText();
				if(answerText.length()>0)
				{
					m_answerField.setText(answerText.subSequence(0, answerText.length()-1));
				}
			}

			@Override
			public void numPadAnswer() {
				answerQuestion();
			}
			
		});
		
		createCallbackHandler();

		createAnimations();
		
		m_streakViewController = new StreakViewController((StreakView) findViewById(R.id.test_streak), new EmojiStreakAdapter(this));

		if(savedInstanceState == null)
		{
			generateTest();
			m_streakViewController.initialQuestion();
		}
		else
		{
			deserialiseTest(savedInstanceState);
		}
		
	
		startTest();
	}
	
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putParcelable(KEY_TEST, m_test);
	}

	private void deserialiseTest(Bundle savedInstanceState) {
		m_test = savedInstanceState.getParcelable(KEY_TEST);
	}

	private void generateTest() {
		m_testFactory = new TestFactory ();

		// Set up the test factory.  Iterate through and add each times table requested to be tested on
		for (int i=0; i < m_selectedTables.size(); i++)
		{
			m_testFactory.setQuestions(m_selectedNumQuestions, m_selectedTables.get(i));
		}
		
		m_test = m_testFactory.createRandomTest();
	}

	private void createCallbackHandler() {
		// This handler is so we can post a runnable object onto the UX thread queue for processing
		m_handler = new Handler();
		// This runnable object is for running the 'setNextQuestion' method, we're doing it this way so we can add a delay after the animation completes
		m_runnableSetNextQuestion = new Runnable () {
			@Override
			public void run () {
				TestActivity.this.setNextQuestion ();
			}
		};
	}

	private void bindView() {
		// Bind all the necessary fields to instance variables
		m_questionNumber				= (TextView) 			findViewById (R.id.test_question_num);
		m_questionField					= (TextView) 			findViewById (R.id.test_question);
		m_answerField					= (EditText) 			findViewById (R.id.test_answer);
		m_correctAnswers				= (TextView) 			findViewById (R.id.test_correct_answers);
		m_incorrectAnswers				= (TextView) 			findViewById (R.id.test_incorrect_answers);
		m_restartButton					= (Button) 	 			findViewById (R.id.test_restart);
		m_numPad						= (NumPadView)	 		findViewById (R.id.test_numpad);
		m_newTestButton					= (Button)	 			findViewById (R.id.test_new);
		m_showIncorrectAnswersButton	= (Button)				findViewById (R.id.test_show_incorrect);
	}

	private void createAnimations() {
		m_animator 		= AnimationUtils.loadAnimation(this, R.anim.slide_from_right_anim);
		m_zoomAnimator	= (ScaleAnimation) AnimationUtils.loadAnimation(this, R.anim.zoom_out);
		
		// Create an animation listener object so when the zoom animation completes we can set the next question
		m_zoomAnimator.setAnimationListener (new AnimationListener () {
			@Override
			public void onAnimationEnd (Animation animation) {				
				// Post the 'setNextQuestion' method to be run a delay after the animation completes				
				m_handler.postDelayed(m_runnableSetNextQuestion, DELAY_AFTER_ANIMATION);
			}
			
			// We don't need but have to declare as part of AnimationListener class
			@Override
			public void onAnimationRepeat (Animation animation) {}
			@Override
			public void onAnimationStart  (Animation animation) {}
			}
		);
	}
	
    /** Called when the user presses the submit answer button */
    public void answerQuestion () {
    	if (m_currentQuestion != null)
    	{
    		String stringAnswer = m_answerField.getText ().toString();
    		
    		// Don't bother if nothing was entered
    		if (stringAnswer.length() > 0)
    		{
    			// Make sure we store the answer
    			try {
    				
    				Integer	answer = Integer.parseInt(stringAnswer);
    				
        			m_currentQuestion.setAnswer(answer);
        			m_answerField.setText("");
        			if (m_currentQuestion.isAnswerCorrect())
        			{
        				m_streakViewController.questionRight();
        				m_answerField.setVisibility(EditText.INVISIBLE);
        				m_questionField.setText(this.getResources().getString(R.string.tt_correct_zoom));
        				m_questionField.startAnimation(m_zoomAnimator);
        			}
        			else {        			
        				m_streakViewController.questionWrong();
        				updateIncorrectAnswers ();
        				setNextQuestion ();
        			}

        			
    			}
    			
    			// Exception will be thrown if entry is an integer, i.e. in the first line of try block (parseInt) 
    			catch (Exception anException) {
    				// Invalid answer, so blank out answer field
    				m_answerField.setText("");
    			}
    			
    		}
    	}
    	else
    	{
    		finishTest ();
    	}
    }
    
    /** Called when the user presses the Restart View button, will re-run the test with same questions */
    public void rerunTest (View view)
    {
    	m_test.reRunTest();
    	startTest ();
    }
    
    /** Called when the user presses the New Test button */
    public void newTest (View view)
    {
    	this.finish();
    }
    
    /** Called when the user presses the Show Incorrect Answers button */
    public void showIncorrect (View view) {    	
    	Intent 				intent = new Intent (this, IncorrectAnswersActivity.class);
    	ArrayList<Question>	incorrectAnswerList = new ArrayList<Question> (m_test.GetIncorrectAnswers());
    	
    	intent.putParcelableArrayListExtra(INCORRECT_ANSWERS, incorrectAnswerList);   	
    	startActivityForResult (intent, TEST_ON_INCORRECT_ANSWERS);
    
    }
    
    /** Called if the incorrect answers activity requests a re-test on those questions answered incorrectly */
    @Override
	protected void onActivityResult (int requestCode, int resultCode, Intent Data) {
    	if (requestCode == TEST_ON_INCORRECT_ANSWERS) {
    		if (resultCode == RESULT_OK) {
    			m_test.SetTestWithIncorrectAnswers();
    			startTest ();
    		}
    	}
    }

    private void setNextQuestion () {
		// If there are some more questions, then ask the next question
		if (m_test.hasMoreQuestions()) {
			if (m_currentQuestion.isAnswerCorrect())
			{
				updateCorrectAnswers ();
			}

			m_currentQuestion = m_test.askNextQuestion ();
			m_questionField.setText (m_currentQuestion.getQuestionAsString());
			updateQuestionNumber ();
			m_answerField.setVisibility(EditText.VISIBLE);
		}
		else
		{
			if (m_currentQuestion.isAnswerCorrect())
			{
				updateCorrectAnswers ();
			}

			finishTest ();
		}
		
    }    
    
    private void updateQuestionNumber () {
    	m_questionNumber.setText(getString(R.string.tt_try_question__of__, m_test.GetCurrentQuestion(),  m_test.GetNumberQuestions()));
    }
    
    private void updateCorrectAnswers () {
    	String correctAnswers = this.getResources().getString(R.string.tt_correct_answers);
    	Integer numCorrectAnswers = m_test.GetCorrectAnswers ().size();
    	 	
    	m_correctAnswers.setText(correctAnswers + numCorrectAnswers.toString());
    	m_correctAnswers.startAnimation(m_animator);
    }
    
    private void updateIncorrectAnswers () {
    	String incorrectAnswers = this.getResources().getString(R.string.tt_incorrect_answers);
    	Integer numIncorrectAnswers = m_test.GetIncorrectAnswers().size ();    	
    	m_incorrectAnswers.setText(incorrectAnswers + numIncorrectAnswers.toString());
    	m_incorrectAnswers.startAnimation(m_animator);
    }
    
    private void startTest () {
		// Set the first question
		if (m_test.hasMoreQuestions())
		{
			m_restartButton.setVisibility(Button.INVISIBLE);
			m_newTestButton.setVisibility(Button.INVISIBLE);
			m_showIncorrectAnswersButton.setVisibility(Button.INVISIBLE);
			//m_answerButton.setEnabled(true);
			m_answerField.setVisibility (TextView.VISIBLE);			
			m_currentQuestion = m_test.askNextQuestion();
			m_questionField.setText (m_currentQuestion.getQuestionAsString());
		}
		
		// Set the question number and answer indicators
		updateQuestionNumber ();
		updateCorrectAnswers ();
		updateIncorrectAnswers ();

    }
    
    private void finishTest () {
    	m_numPad.setVisibility(View.GONE);
    	m_answerField.setVisibility(TextView.INVISIBLE);
    	//m_answerButton.setEnabled(false);
    	m_restartButton.setVisibility(Button.VISIBLE);
    	m_newTestButton.setVisibility(Button.VISIBLE);
    	showIncorrectAnswers ();
    	
    	
    	String testCompleted = this.getResources().getString(R.string.tt_test_complete);
    	m_questionField.setText(testCompleted);
    }
    
    private void showIncorrectAnswers () {
    	if (m_test.GetIncorrectAnswers().size() > 0)
		{
			m_showIncorrectAnswersButton.setVisibility(Button.VISIBLE);
		}    		    	    		
    }
    
}
