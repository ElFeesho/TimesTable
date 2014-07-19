package uk.co.burchy.timestable;

import uk.co.burchy.timestable.TestRunner.TestRunnerObserver;
import uk.co.burchy.timestable.model.Question;

public class TimeBonusController implements TestRunnerObserver
{
	/**
	 * Something that implements
	 * @author chris
	 *
	 */
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

	private TimeBonus m_timeBonus;
	private TimeBonusAdapter m_timeBonusAdapter;

	public TimeBonusController(TimeBonus timeBonus, TimeBonusAdapter adapter)
	{
		m_timeBonus = timeBonus;
		m_timeBonusAdapter = adapter;
	}
	
	public void update()
	{
		m_timeBonus.timeBonusDisplay(m_timeBonusAdapter.timeBonusTimeLeft());
	}

	@Override
	public void testQuestionAnsweredCorrectly()
	{
		m_timeBonusAdapter.timeBonusQuestionCorrect();
		// return m_timeBonusAdapter.timeBonusEarnt();
	}

	@Override
	public void testQuestionAnsweredIncorrectly()
	{
		m_timeBonusAdapter.timeBonusQuestionIncorrect();
	}

	@Override
	public void testStarted()
	{
		m_timeBonusAdapter.timeBonusStartBonus();
		m_timeBonus.timeBonusDisplay(1.0f);
	}

	@Override
	public void testFinished()
	{
		
	}

	@Override
	public void testQuestionAsked(Question question)
	{
		
	}
	

}
