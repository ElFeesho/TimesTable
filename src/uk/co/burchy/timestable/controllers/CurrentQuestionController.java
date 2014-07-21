package uk.co.burchy.timestable.controllers;

import uk.co.burchy.timestable.TestRunner.TestRunnerObserver;
import uk.co.burchy.timestable.model.Answer;
import uk.co.burchy.timestable.model.Question;
import uk.co.burchy.timestable.model.QuestionRecord;

public class CurrentQuestionController implements TestRunnerObserver
{

	public interface CurrentQuestionView
	{
		void showCurrentQuestion(int questionNumber, int totalQuestions);
	}

	private CurrentQuestionView	m_view;

	public CurrentQuestionController(CurrentQuestionView view)
	{
		m_view = view;
	}
	
	@Override public void testQuestionAnsweredCorrectly(Question question, Answer answer) {}
	@Override public void testQuestionAnsweredIncorrectly(Question question) {}
	@Override public void testStarted() {}
	@Override public void testFinished() {}

	@Override
	public void testQuestionAsked(QuestionRecord question, int questionNumber, int totalQuestions)
	{
		m_view.showCurrentQuestion(questionNumber, totalQuestions);
	}

}
