package uk.co.burchy.timestable;

import java.util.HashSet;

import uk.co.burchy.timestable.model.Answer;
import uk.co.burchy.timestable.model.Question;
import uk.co.burchy.timestable.model.QuestionRecord;
import uk.co.burchy.timestable.model.Test;

/**
 * This class will eventually handle all the state of a {@link Test}.
 * 
 * This will adapt the model for the controllers and views.
 *
 */
public class TestRunner {
	public interface TestRunnerObserver
	{
		public void testQuestionAnsweredCorrectly(Question question, Answer answer);
		public void testQuestionAnsweredIncorrectly(Question question);
		public void testStarted();
		public void testFinished();
		public void testQuestionAsked(Question question, int questionNumber, int totalQuestions);
	}
	
	private HashSet<TestRunnerObserver> m_observers = new HashSet<TestRunnerObserver>();
	
	private Test m_test;
	
	private TestRunnerState	m_state	= new TestRunnerState();

	public TestRunner(Test test)
	{
		m_test = test;
	}
	
	public void addObserver(TestRunnerObserver observer) {
		m_observers.add(observer);
	}
	
	public void removeObserver(TestRunnerObserver observer) {
		m_observers.remove(observer);
	}
	
	public void answerQuestion(int answer)
	{
		QuestionRecord questionRecord = m_state.getCurrentQuestionRecord();
		boolean correct = questionRecord.getQuestion().getCorrectAnswer() == answer;
		Answer answerObject = new Answer(correct, System.currentTimeMillis()- questionRecord.getStartTime());
		questionRecord.setAnswer(answerObject);
		
		if(correct)
		{
			for(TestRunnerObserver observer : m_observers)
			{
				observer.testQuestionAnsweredCorrectly(questionRecord.getQuestion(), answerObject);
			}
		}
		else
		{
			for(TestRunnerObserver observer : m_observers)
			{
				observer.testQuestionAnsweredIncorrectly(questionRecord.getQuestion());
			}
		}
	}
	
	public void startNextQuestion()
	{
		if(m_state.noQuestionsAsked())
		{
			for(TestRunnerObserver observer : m_observers)
			{
				observer.testStarted();
			}
		}
		if(m_state.getCurrentQuestion() < m_test.size()-1)
		{
			Question question = m_test.get(m_state.getCurrentQuestion());
			m_state.addQuestionRecord(new QuestionRecord(question));
			m_state.incrementCurrentQuestion();
			for(TestRunnerObserver observer : m_observers)
			{
				observer.testQuestionAsked(question, m_state.getCurrentQuestion(), m_test.size());
			}
		}
		else
		{
			for(TestRunnerObserver observer : m_observers)
			{
				observer.testFinished();
			}
		}
	}
	
	public void setState(TestRunnerState state)
	{
		m_state = state;
	}
	
	public TestRunnerState getState()
	{
		return m_state;
	}
	
}
