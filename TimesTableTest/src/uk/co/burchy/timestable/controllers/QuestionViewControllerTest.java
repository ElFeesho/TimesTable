package uk.co.burchy.timestable.controllers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import uk.co.burchy.timestable.controllers.QuestionViewController.QuestionView;
import uk.co.burchy.timestable.model.Question;
import uk.co.burchy.timestable.model.QuestionRecord;

@RunWith(RobolectricTestRunner.class)
@Config(manifest="../AndroidManifest.xml")
public class QuestionViewControllerTest
{
	public static class FakeQuestionView implements QuestionView
	{

		public boolean	displayQuestion_called;
		public boolean	displayAnswer_called;
		public String	currentAnswer;
		public String	questionText;

		@Override
		public void displayQuestion(String aQuestion)
		{
			displayQuestion_called = true;
			questionText = aQuestion;
		}

		@Override
		public void displayAnswer(String aAnswer)
		{
			displayAnswer_called = true;
			currentAnswer = aAnswer;
		}
		
	}

	private FakeQuestionView		view;
	private QuestionViewController	controller;
	
	@Before
	public void setup()
	{
		view = new FakeQuestionView();
		controller = new QuestionViewController("%d x %d", view);
	}
	
	@Test
	public void whenAddAnswerNumberCalled_questionViewDisplayAnswerCalled() throws Exception
	{
		controller.addAnswerNumber(1);
		assertTrue(view.displayAnswer_called);
	}
	
	@Test
	public void whenBackSpaceAnswerCalled_displayAnswerCalledOnView() throws Exception
	{
		controller.backspaceAnswer();
		assertTrue(view.displayAnswer_called);
	}
	
	@Test
	public void whenClearAnswerCalled_displayAnswerCalled()
	{
		controller.clearAnswer();
		assertTrue(view.displayAnswer_called);
	}
	
	@Test
	public void whenBackSpaceAnswerCalled_AnAnswerIsCleared() throws Exception
	{
		controller.addAnswerNumber(1);
		controller.backspaceAnswer();
		assertEquals("", view.currentAnswer);
	}
	
	@Test
	public void whenBackSpaceAnsweredCalled_WhenNoAnswerSet_currentAnswerRemainsEmpty() throws Exception
	{
		controller.backspaceAnswer();
		assertEquals("", view.currentAnswer);
	}
	
	@Test
	public void whenAnAnswerHasBeenEntered_callingClearAnswerClearsTheAnswer() throws Exception
	{
		controller.addAnswerNumber(1);
		controller.addAnswerNumber(2);
		controller.clearAnswer();
		assertEquals("",controller.getAnswerBuffer());
	}
	
	@Test
	public void whenAddAnswerHasBeenEntered_getAnswerBufferRetrievesTheCorrectData() throws Exception
	{
		controller.addAnswerNumber(1);
		controller.addAnswerNumber(2);
		controller.addAnswerNumber(3);
		
		assertEquals("123", controller.getAnswerBuffer());
	}
	
	@Test
	public void whenAQuestionIsAsked_theViewIsGivenAFormattedStringRepresentingTheQuestion() throws Exception
	{
		QuestionRecord questionRecord = new QuestionRecord(new Question(3, 4), 1000);
		controller.testQuestionAsked(questionRecord, 1, 1);
		assertEquals("3 x 4", view.questionText);
	}

	@Test
	public void whenAQuestionIsAnsweredCorrectly_nothingIsUpdated() throws Exception
	{
		controller.testQuestionAnsweredCorrectly(null, null);
		assertFalse(view.displayAnswer_called);
		assertFalse(view.displayQuestion_called);
	}

	@Test
	public void whenAQuestionIsAnsweredIncorrectly_nothingIsUpdated() throws Exception
	{
		controller.testQuestionAnsweredIncorrectly(null);
		assertFalse(view.displayAnswer_called);
		assertFalse(view.displayQuestion_called);
	}

	@Test
	public void whenATestIsStarted_nothingIsUpdated() throws Exception
	{
		controller.testStarted();
		assertFalse(view.displayAnswer_called);
		assertFalse(view.displayQuestion_called);
	}

	@Test
	public void whenATestIsFinished_nothingIsUpdated() throws Exception
	{
		controller.testFinished();
		assertFalse(view.displayAnswer_called);
		assertFalse(view.displayQuestion_called);
	}
}
