package uk.co.burchy.timestable;

import uk.co.burchy.timestable.StreakViewController.StreakAdapter;
import android.content.Context;
import android.content.res.Resources;

public class EmojiStreakAdapter implements StreakAdapter
{
	private static final int MIN_STREAK = -3;
	private static final int MAX_STREAK = 3;

	private static final int NEUTRAL = 3;

	private int m_streakCount = 0;
	
	private String[] m_streakIcons;
	
	public EmojiStreakAdapter(Context forResources)
	{
		Resources resources = forResources.getResources();
		
		m_streakIcons = resources.getStringArray(R.array.tt_streak_emoji);
		
		m_streakIcons[NEUTRAL] = "\uD83D\uDE01";
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
