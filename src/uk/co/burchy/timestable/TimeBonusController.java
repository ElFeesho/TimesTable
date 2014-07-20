package uk.co.burchy.timestable;

import uk.co.burchy.timestable.TestRunner.TestRunnerObserver;
import uk.co.burchy.timestable.model.Answer;
import uk.co.burchy.timestable.model.Question;
import android.os.Handler;

public class TimeBonusController implements TestRunnerObserver
{
	
	public interface TimeBonus 
	{
		public void timeBonusDisplay(float timeLeft);
	}
	
	public interface TimeBonusAdapter
	{
		public void timeBonusStartBonus();
		public float timeBonusTimeLeft();
		public boolean timeBonusEarnt();
		
		public void timeBonusQuestionCorrect();
		public void timeBonusQuestionIncorrect();
	}
	
	private Handler m_updateHandler = new Handler();

	private Runnable m_updateRunnable = new Runnable()
	{

		@Override
		public void run()
		{
			update();
			m_updateHandler.postDelayed(this, 10);
		}
	};
	
	private TimeBonus m_timeBonus;
	private TimeBonusAdapter m_timeBonusAdapter;

	public TimeBonusController(TimeBonus timeBonus, TimeBonusAdapter adapter)
	{
		m_timeBonus = timeBonus;
		m_timeBonusAdapter = adapter;
	}
	
	private void update()
	{
		m_timeBonus.timeBonusDisplay(m_timeBonusAdapter.timeBonusTimeLeft());
	}

	@Override
	public void testQuestionAnsweredCorrectly(Question question, Answer answer)
	{
		m_timeBonusAdapter.timeBonusQuestionCorrect();
		// return m_timeBonusAdapter.timeBonusEarnt();
		m_updateHandler.removeCallbacks(m_updateRunnable);
	}

	@Override
	public void testQuestionAnsweredIncorrectly(Question question)
	{
		m_timeBonusAdapter.timeBonusQuestionIncorrect();
		m_updateHandler.removeCallbacks(m_updateRunnable);
	}

	@Override
	public void testStarted()
	{
	}

	@Override
	public void testFinished()
	{
		m_updateHandler.removeCallbacks(m_updateRunnable);
	}

	@Override
	public void testQuestionAsked(Question question)
	{
		m_timeBonusAdapter.timeBonusStartBonus();
		m_timeBonus.timeBonusDisplay(1.0f);
		m_updateRunnable.run();		
	}
	

}
