package uk.co.burchy.timestable.controllers;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import uk.co.burchy.timestable.controllers.CurrentQuestionController.CurrentQuestionView;

public class CurrentQuestionControllerTest
{

	public static class FakeCurrentQuestionView implements CurrentQuestionView
	{
		public int	m_questionNumber;
		public int	m_totalQuestions;
		
		@Override
		public void showCurrentQuestion(int aQuestionNumber, int aTotalQuestions)
		{
			m_questionNumber = aQuestionNumber;
			m_totalQuestions = aTotalQuestions;
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


}
