package uk.co.burchy.timestable.controllers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import org.junit.Before;
import org.junit.Test;

import uk.co.burchy.timestable.controllers.CurrentQuestionController.CurrentQuestionView;

public class CurrentQuestionControllerTest
{


	public static class FakeCurrentQuestionView implements CurrentQuestionView
	{
		public int	m_questionNumber;
		public int	m_totalQuestions;
		public boolean	showCurrentQuestion_called;
		
		@Override
		public void showCurrentQuestion(int aQuestionNumber, int aTotalQuestions)
		{
			m_questionNumber = aQuestionNumber;
			m_totalQuestions = aTotalQuestions;
			showCurrentQuestion_called = true;
		}		
	}

	private FakeCurrentQuestionView	view;
	private CurrentQuestionController	currentQuestionController;
	
	@Before
	public void setup()
	{
		view = new FakeCurrentQuestionView();
		currentQuestionController = new CurrentQuestionController(view);
	}

	@Test
	public void whenAQuestionIsAsked_theCurrentQuestionControllerNotifiesTheCurrentQuestionViewOfTheCurrentQuestion() throws Exception
	{
		currentQuestionController.testQuestionAsked(null, 1, 10);
		assertEquals(1, view.m_questionNumber);
	}
	
	@Test
	public void whenAQuestionIsAsked_theCurrentQuestionControllerNotifiesTheCurrentQuestionViewOfTheTotalQuestions	() throws Exception
	{
		currentQuestionController.testQuestionAsked(null, 1, 10);
		assertEquals(10, view.m_totalQuestions);
	}

	@Test
	public void whenATestStarts_TheCurrentQuestionControllerDoesNotNotifyTheCurrentQuestionView() throws Exception
	{
		currentQuestionController.testStarted();
		assertFalse(view.showCurrentQuestion_called);
	}

	@Test
	public void whenATestFinished_TheCurrentQuestionControllerDoesNotNotifyTheCurrentQuestionView() throws Exception
	{
		currentQuestionController.testFinished();
		assertFalse(view.showCurrentQuestion_called);
	}
	
	@Test
	public void whenATestQuestionAnsweredIncorrectly_TheCurrentQuestionControllerDoesNotNotifyTheCurrentQuestionView() throws Exception
	{
		currentQuestionController.testQuestionAnsweredIncorrectly(null);
		assertFalse(view.showCurrentQuestion_called);
	}
	
	@Test
	public void whenATestQuestionAnsweredCorrectly_TheCurrentQuestionControllerDoesNotNotifyTheCurrentQuestionView() throws Exception
	{
		currentQuestionController.testQuestionAnsweredCorrectly(null, null);
		assertFalse(view.showCurrentQuestion_called);
	}

}
