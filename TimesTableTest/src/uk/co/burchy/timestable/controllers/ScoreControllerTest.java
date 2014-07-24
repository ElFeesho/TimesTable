package uk.co.burchy.timestable.controllers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import uk.co.burchy.timestable.controllers.ScoreController.ScoreCalculator;
import uk.co.burchy.timestable.controllers.ScoreController.ScoreView;
import uk.co.burchy.timestable.model.Answer;
import uk.co.burchy.timestable.model.Question;

public class ScoreControllerTest
{
	private ScoreController controller;
	private FakeScoreView	view;
	private FakeScoreCalculator	calculator;
	
	private static class FakeScoreView implements ScoreView
	{
		public long	score;
		public long	bonus;
		public long	multiplier;
		public boolean	displayScore_called;

		@Override
		public void displayScore(long aScore, long aBonus, long aMultiplier)
		{
			score = aScore;
			bonus = aBonus;
			multiplier = aMultiplier;
			displayScore_called = true;
		}		
	}
	
	private static class FakeScoreCalculator implements ScoreCalculator
	{
		public long STATE_score;
		public boolean	questionAnsweredIncorrectly_called;
		public boolean	questionAnsweredCorrectly_called;
		public boolean	calculateMultiplier_called;
		public boolean	calculateTimeBonus_called;
		public boolean	calculateScore_called;

		@Override
		public long calculateScore(long aQuestionAnswer)
		{
			calculateScore_called = true;
			return 0;
		}

		@Override
		public long calculateTimeBonus(long aAnswer, long aDuration)
		{
			calculateTimeBonus_called = true;
			return 0;
		}

		@Override
		public long calculateMultiplier()
		{
			calculateMultiplier_called = true;
			return 0;
		}

		@Override
		public void questionAnsweredCorrectly()
		{
			questionAnsweredCorrectly_called = true;
		}

		@Override
		public void questionAnsweredIncorrectly()
		{
			questionAnsweredIncorrectly_called = true;
		}

		@Override
		public long getScore()
		{
			return STATE_score;
		}
	}
	
	@Before
	public void setup()
	{
		view = new FakeScoreView();
		calculator = new FakeScoreCalculator();
		controller = new ScoreController(view, calculator);
	}
	
	@Test
	public void whenATestIsStarted_theScoreViewIsAskedToDisplayZeroScore() throws Exception
	{
		controller.testStarted();
		assertEquals(0, view.score);
		assertEquals(0, view.bonus);
		assertEquals(0, view.multiplier);
	}

	@Test
	public void whenGetScoreIsCalled_calculatorReturnsItsScore() throws Exception
	{
		calculator.STATE_score = 500;
		assertEquals(500, controller.getScore());
	}
	
	@Test
	public void whenAQuestionIsAsked_ScoreViewIsLeftIntact()
	{
		controller.testQuestionAsked(null, 1, 1);
		assertFalse(view.displayScore_called);
	}
	
	@Test
	public void whenATestFinishes_theScoreViewIsLeftIntact()
	{
		controller.testFinished();
		assertFalse(view.displayScore_called);
	}
	
	@Test
	public void whenAQuestionIsAnsweredIncorrectly_theScoreCalculatorIsNotified() throws Exception
	{
		controller.testQuestionAnsweredIncorrectly(null);
		assertTrue(calculator.questionAnsweredIncorrectly_called);
	}

	@Test
	public void whenAQuestionIsAnsweredCorrectly_theScoreCalculatorIsNotified() throws Exception
	{
		controller.testQuestionAnsweredCorrectly(new Question(2, 2), new Answer(true, 1000));
		assertTrue(calculator.questionAnsweredCorrectly_called);
	}

	@Test
	public void whenAQuestionIsAnsweredCorrectly_scoreCalculatorCalculatesMultiplier() throws Exception
	{
		controller.testQuestionAnsweredCorrectly(new Question(2, 2), new Answer(true, 1000));
		assertTrue(calculator.calculateMultiplier_called);
	}
	@Test
	public void whenAQuestionIsAnsweredCorrectly_scoreCalculatorCalculatesScore() throws Exception
	{
		controller.testQuestionAnsweredCorrectly(new Question(2, 2), new Answer(true, 1000));
		assertTrue(calculator.calculateScore_called);
	}
	
	@Test
	public void whenAQuestionIsAnsweredCorrectly_scoreCalculatorCalculateTimeBonus() throws Exception
	{
		controller.testQuestionAnsweredCorrectly(new Question(2, 2), new Answer(true, 1000));
		assertTrue(calculator.calculateTimeBonus_called);
	}
}
