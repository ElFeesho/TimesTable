package uk.co.burchy.timestable;

public class TimeBonusController
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
	
	public void questionStarted()
	{
		m_timeBonusAdapter.timeBonusStartBonus();
		m_timeBonus.timeBonusDisplay(1.0f);
	}
	
	public boolean questionAnsweredCorrectly()
	{
		m_timeBonusAdapter.timeBonusQuestionCorrect();
		return m_timeBonusAdapter.timeBonusEarnt();
	}
	
	public void questionAnsweredIncorrectly()
	{
		m_timeBonusAdapter.timeBonusQuestionIncorrect();
	}
	
	public void update()
	{
		m_timeBonus.timeBonusDisplay(m_timeBonusAdapter.timeBonusTimeLeft());
	}
	

}
