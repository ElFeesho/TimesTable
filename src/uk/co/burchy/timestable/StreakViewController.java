package uk.co.burchy.timestable;

import uk.co.burchy.timestable.TestRunner.TestRunnerObserver;
import uk.co.burchy.timestable.model.Question;

public class StreakViewController implements TestRunnerObserver
{
	public interface StreakView
	{
		public void streakViewShowStreakIcon(String icon);
	}
	
	public interface StreakAdapter
	{
		public String iconForRightAnswer();
		public String iconForWrongAnswer();
		public String iconForFirstQuestion();
	}

	private StreakView m_streakView;
	private StreakAdapter m_streakAdapter;

	public StreakViewController(StreakView streakView, StreakAdapter streakAdapter)
	{
		m_streakView = streakView;
		m_streakAdapter = streakAdapter;
	}
	
	@Override
	public void testQuestionAnsweredCorrectly()
	{		
		m_streakView.streakViewShowStreakIcon(m_streakAdapter.iconForRightAnswer());
	}

	@Override
	public void testQuestionAnsweredIncorrectly()
	{		
		m_streakView.streakViewShowStreakIcon(m_streakAdapter.iconForWrongAnswer());
	}

	@Override
	public void testStarted()
	{
		m_streakView.streakViewShowStreakIcon(m_streakAdapter.iconForFirstQuestion());		
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
