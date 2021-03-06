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
		public void testQuestionAsked(QuestionRecord question, int questionNumber, int totalQuestions);
	}
	
	//private HashSet<TestRunnerObserver> m_observers = new HashSet<TestRunnerObserver>();
	
	private Announcer<TestRunnerObserver> m_observers = new Announcer<TestRunnerObserver>(TestRunnerObserver.class);
	
	private Test m_test;
	
	private TestRunnerState	m_state	= new TestRunnerState();

	public TestRunner(Test test)
	{
		m_test = test;
	}
	
	public void addObserver(TestRunnerObserver ...observer) {
		for(TestRunnerObserver obs : observer)
		{
			m_observers.addListener(obs);
		}
		
		if(m_state.noQuestionsAsked())
		{
			broadcastTestStarted();
		}
		else if(m_state.getCurrentQuestion() < m_test.size())
		{
			broadcastCurrentQuestion(m_state.getCurrentQuestionRecord());
		}
		else
		{
			broadcastTestFinished();
		}
		
	}
	
	public void removeObserver(TestRunnerObserver observer) {
		m_observers.removeListener(observer);
	}
	
	public void answerQuestion(int answer)
	{
		QuestionRecord questionRecord = m_state.getCurrentQuestionRecord();
		boolean correct = questionRecord.getQuestion().getCorrectAnswer() == answer;
		Answer answerObject = new Answer(correct, System.currentTimeMillis()- questionRecord.getStartTime());
		questionRecord.setAnswer(answerObject);
		
		if(correct)
		{
			m_observers.announce().testQuestionAnsweredCorrectly(questionRecord.getQuestion(), answerObject);
		}
		else
		{
			m_observers.announce().testQuestionAnsweredIncorrectly(questionRecord.getQuestion());
		}
	}
	
	public void startNextQuestion()
	{
		if(m_state.noQuestionsAsked())
		{
			broadcastTestStarted();
		}
		
		if(m_state.getCurrentQuestion() < m_test.size())
		{
			Question question = m_test.get(m_state.getCurrentQuestion());
			QuestionRecord questionRecord = new QuestionRecord(question, System.currentTimeMillis());
			m_state.addQuestionRecord(questionRecord);
			m_state.incrementCurrentQuestion();
			broadcastCurrentQuestion(questionRecord);
		}
		else
		{
			broadcastTestFinished();
		}
	}

	private void broadcastTestFinished() {
		m_observers.announce().testFinished();
	}

	private void broadcastTestStarted() {
		m_observers.announce().testStarted();
	}

	private void broadcastCurrentQuestion(QuestionRecord questionRecord) {
		m_observers.announce().testQuestionAsked(questionRecord, m_state.getCurrentQuestion(), m_test.size());
	}
	
	public void setState(TestRunnerState state)
	{
		m_state = state;
	}
	
	public TestRunnerState getState()
	{
		return m_state;
	}
	
	public boolean isComplete()
	{
		return m_state.getCurrentQuestion() == m_test.size();
	}

	public long getTotalDuration()
	{
		return m_state.getTotalDuration();
	}
	
	public int getCorrectCount()
	{
		return m_state.getCorrectCount();
	}
	
	public int getWrongCount()
	{
		return m_state.getWrongCount();
	}
	
}
