package uk.co.burchy.timestable.controllers;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import uk.co.burchy.timestable.controllers.AnswerNotifierController.AnswerNotifierCompleteListener;
import uk.co.burchy.timestable.controllers.AnswerNotifierController.AnswerNotifierControllerListener;
import uk.co.burchy.timestable.controllers.AnswerNotifierController.AnswerNotifierView;

public class AnswerNotifierControllerTest
{

	private static class FakeAnswerNotifierView implements AnswerNotifierView
	{
		public boolean	displayAnswerCorrect_called;
		public boolean	displayAnswerIncorrect_called;
		public AnswerNotifierCompleteListener	answerCompleteListener;

		@Override
		public void displayAnswerCorrect()
		{
			displayAnswerCorrect_called = true;
		}

		@Override
		public void displayAnswerIncorrect()
		{
			displayAnswerIncorrect_called = true;
		}

		@Override
		public void setNotificationCompleteListener(AnswerNotifierCompleteListener listener)
		{
			answerCompleteListener = listener;
		}
		
	}
	
	private class StubAnswerNotifierControllerListener implements AnswerNotifierControllerListener
	{
		public boolean	notificationComplete_called;
		public boolean	displayingNotificationForIncorrectAnswer_called;
		public boolean	displayingNotificationForCorrectAnswer_called;

		@Override
		public void notificationComplete()
		{
			notificationComplete_called = true;
		}
		
		@Override
		public void displayingNotificationForIncorrectAnswer()
		{
			displayingNotificationForIncorrectAnswer_called = true;
		}
		
		@Override
		public void displayingNotificationForCorrectAnswer()
		{
			displayingNotificationForCorrectAnswer_called = true;
		}
	};
	
	private FakeAnswerNotifierView					view;
	private AnswerNotifierController				controller;
	private StubAnswerNotifierControllerListener	listener;
	
	@Before
	public void setup()
	{
		view = new FakeAnswerNotifierView();
		listener = new StubAnswerNotifierControllerListener();
		controller = new AnswerNotifierController(view, listener);
	}
	
	@Test
	public void whenAQuestionIsAnsweredCorrectly_viewDisplaysAnswerCorrect() throws Exception
	{
		controller.testQuestionAnsweredCorrectly(null, null);
		assertTrue(view.displayAnswerCorrect_called);
	}

	
	@Test
	public void whenAQuestionIsAnsweredIncorrectly_viewDisplaysAnswerIncorrect() throws Exception
	{
		controller.testQuestionAnsweredIncorrectly(null);
		assertTrue(view.displayAnswerIncorrect_called);
	}
	
	@Test
	public void theAnswerNotifierController_SetsACompleteListener_onTheViewt() throws Exception
	{
		assertNotNull(view.answerCompleteListener);
	}
	
	@Test
	public void whenTheViewNotifiesTheControllerThatItHasCompletedNotifying_theControllerListenerIsNotified() throws Exception
	{
		view.answerCompleteListener.answerNotificationComplete();
		assertTrue(listener.notificationComplete_called);
	}

	@Test
	public void whenATestStarts_thereAreNoInteractionsWithTheView() throws Exception
	{
		controller.testStarted();
		assertFalse(view.displayAnswerCorrect_called);
		assertFalse(view.displayAnswerIncorrect_called);
	}

	@Test
	public void whenATestFinishes_thereAreNoInteractionsWithTheView() throws Exception
	{
		controller.testFinished();
		assertFalse(view.displayAnswerCorrect_called);
		assertFalse(view.displayAnswerIncorrect_called);
	}

	@Test
	public void whenAQuestionIsAsked_thereAreNoInteractionsWithTheView() throws Exception
	{
		controller.testQuestionAsked(null, 1, 1);
		assertFalse(view.displayAnswerCorrect_called);
		assertFalse(view.displayAnswerIncorrect_called);
	}
}
