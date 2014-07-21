package uk.co.burchy.timestable.controllers;

import uk.co.burchy.timestable.TestRunner;
import uk.co.burchy.timestable.TestRunner.TestRunnerObserver;
import uk.co.burchy.timestable.model.Answer;
import uk.co.burchy.timestable.model.Question;
import uk.co.burchy.timestable.model.QuestionRecord;

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
	public void testQuestionAnsweredCorrectly(Question question, Answer answer)
	{		
		m_streakView.streakViewShowStreakIcon(m_streakAdapter.iconForRightAnswer());
	}

	@Override
	public void testQuestionAnsweredIncorrectly(Question question)
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
	public void testQuestionAsked(QuestionRecord question, int questionNumber, int totalQuestions)
	{
		
	}

}
