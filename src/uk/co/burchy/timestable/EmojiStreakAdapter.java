package uk.co.burchy.timestable;

import uk.co.burchy.timestable.controllers.StreakViewController.StreakAdapter;

public class EmojiStreakAdapter implements StreakAdapter
{
	private static final String	EMOJI_RIGHT_THREE	= "\uD83D\uDE4C";
	private static final String	EMOJI_RIGHT_TWO		= "\uD83D\uDE04";
	private static final String	EMOJI_RIGHT_ONE		= "\uD83D\uDE03";
	private static final String	EMOJI_NEUTRAL		= "\uD83D\uDE10";
	private static final String	EMOJI_WRONG_ONE		= "\uD83D\uDE15";
	private static final String	EMOJI_WRONG_TWO		= "\uD83D\uDE1E";
	private static final String	EMOJI_WRONG_THREE	= "\uD83D\uDE2D";
	
	private static final int MIN_STREAK = -3;
	private static final int MAX_STREAK = 3;

	private static final int NEUTRAL = 3;

	private int m_streakCount = 0;
	
	private String[] m_streakIcons = new String[7];
	
	public EmojiStreakAdapter()
	{
		m_streakIcons[NEUTRAL-3] = EMOJI_WRONG_THREE;
		m_streakIcons[NEUTRAL-2] = EMOJI_WRONG_TWO;
		m_streakIcons[NEUTRAL-1] = EMOJI_WRONG_ONE;
		m_streakIcons[NEUTRAL] 	 = EMOJI_NEUTRAL;
		m_streakIcons[NEUTRAL+1] = EMOJI_RIGHT_ONE;
		m_streakIcons[NEUTRAL+2] = EMOJI_RIGHT_TWO;
		m_streakIcons[NEUTRAL+3] = EMOJI_RIGHT_THREE;
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
		if(m_streakCount > 0)
		{
			m_streakCount = 0;
		}
		
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
