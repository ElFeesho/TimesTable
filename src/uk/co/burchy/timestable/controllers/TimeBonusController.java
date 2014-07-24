package uk.co.burchy.timestable.controllers;

import uk.co.burchy.timestable.TestRunner.TestRunnerObserver;
import uk.co.burchy.timestable.model.Answer;
import uk.co.burchy.timestable.model.Question;
import uk.co.burchy.timestable.model.QuestionRecord;

public class TimeBonusController implements TestRunnerObserver
{
	public interface InvocationRepeater
	{
		public void startRepeating(Runnable runnable, long delay);
		public void stopRepeating();
	}
	
	public interface TimeBonus 
	{
		public void timeBonusDisplay(float timeLeft);
	}
	
	public interface TimeBonusAdapter
	{
		public void timeBonusStartBonus();
		public float timeBonusTimeLeft();
		
		public void timeBonusQuestionCorrect();
		public void timeBonusQuestionIncorrect();
	}

	private static final long	UPDATE_FREQUENCY	= 10;
	
	private Runnable m_updateRunnable = new Runnable()
	{
		@Override
		public void run()
		{
			update();
		}
	};
	
	private InvocationRepeater m_invocationRepeater;
	private TimeBonus m_timeBonus;
	private TimeBonusAdapter m_timeBonusAdapter;

	public TimeBonusController(TimeBonus timeBonus, TimeBonusAdapter adapter, InvocationRepeater repeater)
	{
		m_timeBonus = timeBonus;
		m_timeBonusAdapter = adapter;
		m_invocationRepeater = repeater;
	}
	
	private void update()
	{
		m_timeBonus.timeBonusDisplay(m_timeBonusAdapter.timeBonusTimeLeft());
	}

	@Override
	public void testQuestionAnsweredCorrectly(Question question, Answer answer)
	{
		m_timeBonusAdapter.timeBonusQuestionCorrect();
		m_invocationRepeater.stopRepeating();
	}

	@Override
	public void testQuestionAnsweredIncorrectly(Question question)
	{
		m_timeBonusAdapter.timeBonusQuestionIncorrect();
		m_invocationRepeater.stopRepeating();
	}

	@Override public void testStarted() {}

	@Override
	public void testFinished()
	{
		m_invocationRepeater.stopRepeating();
	}

	@Override
	public void testQuestionAsked(QuestionRecord question, int questionNumber, int totalQuestions)
	{
		m_timeBonusAdapter.timeBonusStartBonus();
		m_timeBonus.timeBonusDisplay(1.0f);
		m_invocationRepeater.startRepeating(m_updateRunnable, UPDATE_FREQUENCY);
	}
	

}
