package uk.co.burchy.timestable;

import uk.co.burchy.timestable.TestRunner.TestRunnerObserver;
import uk.co.burchy.timestable.model.Question;

public class QuestionViewController implements TestRunnerObserver
{
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
	
	@Override public void testQuestionAnsweredCorrectly()	{}
	@Override public void testQuestionAnsweredIncorrectly() {}
	@Override public void testStarted() {}
	@Override public void testFinished() {}

	@Override
	public void testQuestionAsked(Question question)
	{
		m_view.displayQuestion(String.format(m_questionFormat, question.getQuestion(), question.getTable()));
	}

}
