package uk.co.burchy.timestable.controllers;

import uk.co.burchy.timestable.TestRunner.TestRunnerObserver;
import uk.co.burchy.timestable.model.Answer;
import uk.co.burchy.timestable.model.Question;
import uk.co.burchy.timestable.model.QuestionRecord;

public class QuestionViewController implements TestRunnerObserver
{
	public interface QuestionView
	{
		public void displayQuestion(String question);

		public void displayAnswer(String answer);

	}
	
	private QuestionView	m_view;
	private String m_questionFormat;
	private String	m_answerBuffer = "";

	public QuestionViewController(String questionFormat, QuestionView view)
	{
		m_questionFormat = questionFormat;
		m_view = view;
	}
	
	public void addAnswerNumber(int num)
	{
		m_answerBuffer += num;
		displayAnswerBuffer();
	}
	
	public void backspaceAnswer()
	{
		if(m_answerBuffer.length()>0)
		{
			m_answerBuffer = m_answerBuffer.substring(0, m_answerBuffer.length()-1);
		}
		displayAnswerBuffer();
	}
	
	public void clearAnswer()
	{
		m_answerBuffer = "";
		displayAnswerBuffer();
	}

	private void displayAnswerBuffer()
	{
		m_view.displayAnswer(m_answerBuffer);
	}
	
	public String getAnswerBuffer()
	{
		return m_answerBuffer;
	}
	
	@Override public void testQuestionAnsweredCorrectly(Question question, Answer answer)	{}
	@Override public void testQuestionAnsweredIncorrectly(Question question) {}
	@Override public void testStarted() {}
	@Override public void testFinished() {}

	@Override
	public void testQuestionAsked(QuestionRecord question, int questionNumber, int totalQuestions)
	{
		m_view.displayQuestion(String.format(m_questionFormat, question.getQuestion().getQuestion(), question.getQuestion().getTable()));
	}

}
