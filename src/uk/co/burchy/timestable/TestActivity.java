package uk.co.burchy.timestable;

import java.util.ArrayList;
import java.util.Collection;

import uk.co.burchy.timestable.StreakViewController.StreakView;
import uk.co.burchy.timestable.TimeBonusController.TimeBonus;
import uk.co.burchy.timestable.model.Question;
import uk.co.burchy.timestable.model.Test;
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
	private TestRunner	m_testRunner;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.test_activity);


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
		
		m_testRunner = new TestRunner(m_test);
		
		m_testRunner.addObserver(m_streakViewController);
		m_testRunner.addObserver(m_timeBonusController);
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
	

	public static Intent intentForTest(Context context, Test test)
	{
		return new Intent(context, TestActivity.class).putExtra(KEY_TEST, test);
	}

}
