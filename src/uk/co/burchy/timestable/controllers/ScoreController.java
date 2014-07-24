package uk.co.burchy.timestable.controllers;

import uk.co.burchy.timestable.TestRunner.TestRunnerObserver;
import uk.co.burchy.timestable.model.Answer;
import uk.co.burchy.timestable.model.Question;
import uk.co.burchy.timestable.model.QuestionRecord;

public class ScoreController implements TestRunnerObserver {

	public interface ScoreView
	{
		public void displayScore(long score, long bonus, long multiplier);
	}
	
	public interface ScoreCalculator
	{
		/**
		 * This method returns the actual score the player currently has
		 * @return A long representing the current score
		 */
		public long calculateScore(long questionAnswer);
		/**
		 * This method gives an implementation of {@link ScoreCalculator} the chance
		 * to calculate the time bonus earned for the current question.
		 * 
		 * @param answer The answer to the question, the score is based off of this
		 * @param duration The time it took the player to answer the question
		 * @return The bonus that will be added to the player's score
		 */
		public long calculateTimeBonus(long answer, long duration);
		
		/**
		 * This method gives an implementation of {@link ScoreCalculator} the
		 * chance to calculate a multiplier that has been earnt from answering
		 * several questions correctly in series.
		 * @return The multiplier that will be applied to the points earned for 
		 * a question
		 */
		public long calculateMultiplier();
		public void questionAnsweredCorrectly();
		public void questionAnsweredIncorrectly();
		
		public long getScore();
	}

	private ScoreView m_view;
	private ScoreCalculator m_calculator;
	
	public ScoreController(ScoreView view, ScoreCalculator calculator)
	{
		m_view = view;
		m_calculator = calculator;
	}
	
	@Override
	public void testQuestionAnsweredCorrectly(Question question, Answer answer) {
		m_calculator.questionAnsweredCorrectly();
		long multiplier = m_calculator.calculateMultiplier();
		long bonus = m_calculator.calculateTimeBonus(question.getCorrectAnswer(), answer.duration);
		long score = m_calculator.calculateScore(question.getCorrectAnswer());
		m_view.displayScore(score, bonus, multiplier);
	}

	@Override
	public void testQuestionAnsweredIncorrectly(Question question) {
		m_calculator.questionAnsweredIncorrectly();
	}

	@Override
	public void testStarted() {
		m_view.displayScore(0, 0, 0);
	}

	@Override public void testFinished() {}

	@Override public void testQuestionAsked(QuestionRecord question, int questionNumber, int totalQuestions) {}
	
	public long getScore()
	{
		return m_calculator.getScore();
	}

}
