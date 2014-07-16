package uk.co.burchy.timestable;

public class StreakViewController
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
	
	public void questionRight()
	{
		m_streakView.streakViewShowStreakIcon(m_streakAdapter.iconForRightAnswer());
	}
	
	public void questionWrong()
	{
		m_streakView.streakViewShowStreakIcon(m_streakAdapter.iconForWrongAnswer());
	}
	
	public void initialQuestion()
	{
		m_streakView.streakViewShowStreakIcon(m_streakAdapter.iconForFirstQuestion());
	}

}
