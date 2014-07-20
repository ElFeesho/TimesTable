package uk.co.burchy.timestable.controllers;

import uk.co.burchy.timestable.TestRunner.TestRunnerObserver;
import uk.co.burchy.timestable.model.Answer;
import uk.co.burchy.timestable.model.Question;

public class AnswerNotifierController implements TestRunnerObserver
{
	public interface AnswerNotifierControllerListener
	{
		public void displayingNotificationForCorrectAnswer();
		public void displayingNotificationForIncorrectAnswer();
		public void notificationComplete();
	}

	public interface AnswerNotifierCompleteListener
	{
		public void answerNotificationComplete();
	}

	public interface AnswerNotifierView
	{
		public void displayAnswerCorrect();
		public void displayAnswerIncorrect();
		public void setNotificationCompleteListener(AnswerNotifierCompleteListener listener);
	}
	
	private AnswerNotifierView m_view;
	private AnswerNotifierControllerListener m_listener;
	
	private AnswerNotifierCompleteListener m_completeListener = new AnswerNotifierCompleteListener()
	{
		@Override
		public void answerNotificationComplete()
		{
			m_listener.notificationComplete();
		}
	};
	
	public AnswerNotifierController(AnswerNotifierView notifierView, AnswerNotifierControllerListener listener)
	{
		m_view = notifierView;
		m_listener = listener;
		
		m_view.setNotificationCompleteListener(m_completeListener);
	}
	
	@Override
	public void testQuestionAnsweredCorrectly(Question question, Answer answer)
	{
		m_view.displayAnswerCorrect();
		m_listener.displayingNotificationForCorrectAnswer();
	}

	@Override
	public void testQuestionAnsweredIncorrectly(Question question)
	{
		m_view.displayAnswerIncorrect();
		m_listener.displayingNotificationForIncorrectAnswer();
	}

	@Override public void testStarted() {}
	@Override public void testFinished() {}
	@Override public void testQuestionAsked(Question question, int questionNumber, int totalQuestions) {}

}
