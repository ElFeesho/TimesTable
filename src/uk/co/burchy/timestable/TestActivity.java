package uk.co.burchy.timestable;

import java.util.ArrayList;
import java.util.Collection;

import uk.co.burchy.timestable.controllers.AnswerNotifierController;
import uk.co.burchy.timestable.controllers.AnswerNotifierController.AnswerNotifierControllerListener;
import uk.co.burchy.timestable.controllers.CurrentQuestionController;
import uk.co.burchy.timestable.controllers.CurrentQuestionController.CurrentQuestionView;
import uk.co.burchy.timestable.controllers.QuestionViewController;
import uk.co.burchy.timestable.controllers.StreakViewController;
import uk.co.burchy.timestable.controllers.StreakViewController.StreakView;
import uk.co.burchy.timestable.controllers.TimeBonusController;
import uk.co.burchy.timestable.controllers.TimeBonusController.TimeBonus;
import uk.co.burchy.timestable.model.Question;
import uk.co.burchy.timestable.model.Test;
import uk.co.burchy.timestable.view.NumPadView;
import uk.co.burchy.timestable.view.NumPadView.NumPadViewListener;
import uk.co.burchy.timestable.view.PopupWindowAnswerNotifierView;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;

public class TestActivity extends Activity
{

	private static final int POPUP_DISPLAY_TIME = 500;
	private static final String KEY_TEST = "test";
	public final static String INCORRECT_ANSWERS = "uk.co.burchy.timestable.INCORRECT_ANSWERS";
	public final static String NUM_INCORRECT_ANSWERS = "uk.co.burchy.timestable.NUM_INCORRECT_ANSWERS";
	public final static Integer TEST_ON_INCORRECT_ANSWERS = 0;
	private static final String	KEY_STATE	= "state";

	private Test m_test;
	private Handler m_handler;

	private StreakViewController m_streakViewController;
	private TimeBonusController m_timeBonusController;

	private TestRunner	m_testRunner;
	private QuestionViewController	m_questionViewController;
	private AnswerNotifierController	m_answerNotifierController;
	private CurrentQuestionController	m_currentQuestionController;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.test_activity);
		
		NumPadView numPad = (NumPadView) findViewById(R.id.test_numpad);

		numPad.setNumPadViewListener(new NumPadViewListener()
		{
			@Override
			public void numPadClicked(int number)
			{
				m_questionViewController.addAnswerNumber(number);
			}
			
			@Override
			public void numPadClear()
			{
				m_questionViewController.clearAnswer();
			}
			
			@Override
			public void numPadBackspace()
			{
				m_questionViewController.backspaceAnswer();
			}
			
			@Override
			public void numPadAnswer()
			{
				if(m_questionViewController.getAnswerBuffer().length()>0)
				{
					m_testRunner.answerQuestion(Integer.parseInt(m_questionViewController.getAnswerBuffer()));
				}
			}
		});
		
		m_streakViewController = new StreakViewController((StreakView) findViewById(R.id.test_streak), new EmojiStreakAdapter());
		m_timeBonusController = new TimeBonusController((TimeBonus) findViewById(R.id.tt_time_bonus), new CurrentTimeTimeBonusAdapter());
		m_questionViewController = new QuestionViewController(getString(R.string.tt_question_fmt), (QuestionView) findViewById(R.id.tt_question_view));
		m_currentQuestionController = new CurrentQuestionController((CurrentQuestionView) findViewById(R.id.test_question_num));
		m_answerNotifierController = new AnswerNotifierController(new PopupWindowAnswerNotifierView(findViewById(android.R.id.content), getLayoutInflater().inflate(R.layout.cv_answer_toast, null), getLayoutInflater().inflate(R.layout.cv_answer_incorrect_toast, null)), new AnswerNotifierControllerListener()
		{
			
			@Override
			public void notificationComplete()
			{
				m_questionViewController.clearAnswer();
				m_testRunner.startNextQuestion();
			}
			
			@Override
			public void displayingNotificationForIncorrectAnswer()
			{
				
			}
			
			@Override
			public void displayingNotificationForCorrectAnswer()
			{
				
			}
		});
		
		if(savedInstanceState != null)
		{
			m_test = deserialiseTest(savedInstanceState);
			TestRunnerState state = savedInstanceState.getParcelable(KEY_STATE);
			m_testRunner = new TestRunner(m_test);
			m_testRunner.setState(state);
		}
		else
		{
			m_test = deserialiseTest(getIntent().getExtras());
			m_testRunner = new TestRunner(m_test);
		}
		
		
		m_testRunner.addObserver(m_streakViewController);
		m_testRunner.addObserver(m_timeBonusController);
		m_testRunner.addObserver(m_questionViewController);
		m_testRunner.addObserver(m_answerNotifierController);
		m_testRunner.addObserver(m_currentQuestionController);
		
		m_testRunner.startNextQuestion();
	}
	
	@Override
	protected void onSaveInstanceState(Bundle outState)
	{
		super.onSaveInstanceState(outState);
		outState.putParcelableArrayList(KEY_TEST, m_test);
		outState.putParcelable(KEY_STATE, m_testRunner.getState());
	}

	@SuppressWarnings("unchecked")
	private Test deserialiseTest(Bundle bundle)
	{
		Test result = new Test();
		ArrayList<Parcelable> questions = bundle.getParcelableArrayList(KEY_TEST);
		result.addAll((Collection<? extends Question>) questions);
		return result;
	}
	

	public static Intent intentForTest(Context context, Test test)
	{
		return new Intent(context, TestActivity.class).putExtra(KEY_TEST, test);
	}

}
