package uk.co.burchy.timestable;

import java.util.HashSet;

/**
 * This class will eventually handle all the state of a {@link Test}.
 * 
 * This will adapt the model for the controllers and views.
 *
 */
public class TestRunner {
	public interface TestRunnerObserver
	{
		public void testQuestionAnsweredCorrectly();
		public void testQuestionAnsweredIncorrectly();
		public void testStarted();
		public void testFinished();
	}
	
	private HashSet<TestRunnerObserver> m_observers = new HashSet<TestRunnerObserver>();
	
	private Test m_test;
	
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
	
	
}
