package uk.co.burchy.timestable;

import uk.co.burchy.timestable.StreakViewController.StreakAdapter;

public class EmojiStreakAdapter implements StreakAdapter
{
	private static final int MIN_STREAK = -3;
	private static final int MAX_STREAK = 3;

	private static final int NEUTRAL = 3;

	private int m_streakCount = 0;
	
	private String[] m_streakIcons = new String[7];
	
	public EmojiStreakAdapter()
	{
		m_streakIcons[NEUTRAL-3] = "\uD83D\uDE2D";
		m_streakIcons[NEUTRAL-2] = "\uD83D\uDE1E";
		m_streakIcons[NEUTRAL-1] = "\uD83D\uDE15";
		m_streakIcons[NEUTRAL] 	 = "\uD83D\uDE10";
		m_streakIcons[NEUTRAL+1] = "\uD83D\uDE03";
		m_streakIcons[NEUTRAL+2] = "\uD83D\uDE04";
		m_streakIcons[NEUTRAL+3] = "\uD83D\uDE4C";
	}

	@Override
	public String iconForRightAnswer()
	{
		m_streakCount++;
		if(m_streakCount > MAX_STREAK)
		{
			m_streakCount = MAX_STREAK;
		}
		
		return iconForStreak();
	}

	private String iconForStreak()
	{
		return m_streakIcons[NEUTRAL+m_streakCount];
	}

	@Override
	public String iconForWrongAnswer()
	{
		m_streakCount--;
		if(m_streakCount < MIN_STREAK)
		{
			m_streakCount = MIN_STREAK;
		}
		return iconForStreak();
	}

	@Override
	public String iconForFirstQuestion()
	{
		m_streakCount = 0;
		return iconForStreak();
	}

}
