package uk.co.burchy.timestable;

import java.util.ArrayList;
import java.util.Collection;

import uk.co.burchy.timestable.StreakViewController.StreakView;
import uk.co.burchy.timestable.TimeBonusController.TimeBonus;
import uk.co.burchy.timestable.model.Question;
import uk.co.burchy.timestable.model.Test;
import uk.co.burchy.timestable.view.NumPadView;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.widget.Button;
import android.widget.TextView;

public class TestActivity extends Activity
{

	private static final int POPUP_DISPLAY_TIME = 500;
	private static final String KEY_TEST = "test";
	public final static String INCORRECT_ANSWERS = "uk.co.burchy.timestable.INCORRECT_ANSWERS";
	public final static String NUM_INCORRECT_ANSWERS = "uk.co.burchy.timestable.NUM_INCORRECT_ANSWERS";
	public final static Integer TEST_ON_INCORRECT_ANSWERS = 0;

	private Test m_test;
	private Handler m_handler;

	private StreakViewController m_streakViewController;
	private TimeBonusController m_timeBonusController;

	private Runnable m_updateRunnable = new Runnable()
	{
		@Override
		public void run()
		{
			m_timeBonusController.update();
			m_handler.postDelayed(this, 10);
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.test_activity);

		bindView();

		m_streakViewController = new StreakViewController((StreakView) findViewById(R.id.test_streak), new EmojiStreakAdapter());
		m_timeBonusController = new TimeBonusController((TimeBonus) findViewById(R.id.tt_time_bonus), new CurrentTimeTimeBonusAdapter());

		if(savedInstanceState != null)
		{
			m_test = deserialiseTest(savedInstanceState);
		}
		else
		{
			m_test = deserialiseTest(getIntent().getExtras());
		}

	}
	
	@Override
	protected void onSaveInstanceState(Bundle outState)
	{
		super.onSaveInstanceState(outState);
		outState.putParcelableArrayList(KEY_TEST, m_test);
	}

	@SuppressWarnings("unchecked")
	private Test deserialiseTest(Bundle bundle)
	{
		Test result = new Test();
		ArrayList<Parcelable> questions = bundle.getParcelableArrayList(KEY_TEST);
		result.addAll((Collection<? extends Question>) questions);
		return result;
	}
	
	private void bindView()
	{
		// Bind all the necessary fields to instance variables
		m_questionNumber = (TextView) findViewById(R.id.test_question_num);
		m_questionField = (TextView) findViewById(R.id.test_question);
		m_answerField = (TextView) findViewById(R.id.test_answer);
		m_correctAnswers = (TextView) findViewById(R.id.test_correct_answers);
		m_incorrectAnswers = (TextView) findViewById(R.id.test_incorrect_answers);
		m_restartButton = (Button) findViewById(R.id.test_restart);
		m_numPad = (NumPadView) findViewById(R.id.test_numpad);
		m_newTestButton = (Button) findViewById(R.id.test_new);
		m_showIncorrectAnswersButton = (Button) findViewById(R.id.test_show_incorrect);
	}

	public static Intent intentForTest(Context context, Test test)
	{
		return new Intent(context, TestActivity.class).putExtra(KEY_TEST, test);
	}

}
