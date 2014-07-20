package uk.co.burchy.timestable;

import uk.co.burchy.timestable.controllers.TimeBonusController.TimeBonusAdapter;

public class CurrentTimeTimeBonusAdapter implements TimeBonusAdapter
{
	private static final long MAX_TIME = 10000;
	private static final long MIN_TIME = 3000;
	private static final long DECREMENT = 500;
	private long m_startTime = 0;

	private long m_maxTime = MAX_TIME;
	
	@Override
	public void timeBonusStartBonus()
	{
		m_startTime = System.currentTimeMillis();
	}

	@Override
	public float timeBonusTimeLeft()
	{
		return (m_maxTime - (System.currentTimeMillis() - m_startTime))/(float)m_maxTime;
	}

	@Override
	public boolean timeBonusEarnt()
	{
		return timeBonusTimeLeft() > 0.0f;
	}

	@Override
	public void timeBonusQuestionCorrect()
	{
		if(m_maxTime > MIN_TIME)
		{
			m_maxTime -= DECREMENT;
		}	
		else
		{
			m_maxTime = MAX_TIME;
		}
	}

	@Override
	public void timeBonusQuestionIncorrect()
	{
		m_maxTime = MAX_TIME;
	}

}
