package uk.co.burchy.timestable.controllers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import uk.co.burchy.timestable.controllers.StreakViewController.StreakAdapter;
import uk.co.burchy.timestable.controllers.StreakViewController.StreakView;

@RunWith(RobolectricTestRunner.class)
@Config(manifest="../AndroidManifest.xml")
public class StreakViewControllerTest
{
	private static final String	RIGHT_ANSWER	= "right_answer";
	private static final String	FIRST_ANSWER	= "first";
	private static final String	WRONG_ANSWER	= "wrong_answer";

	private static class FakeStreakView implements StreakView
	{
		public String	lastSetIcon	= null;
		public boolean	streakViewShowStreakIcon_called = false;

		@Override
		public void streakViewShowStreakIcon(String aIcon)
		{
			streakViewShowStreakIcon_called  = true;
			lastSetIcon = aIcon;
		}
	}

	private static class FakeStreakAdapter implements StreakAdapter
	{
		public boolean	iconForRightAnswer_called	= false;
		public boolean	iconForWrongAnswer_called	= false;
		public boolean	iconForFirstQuestion_called	= false;

		@Override
		public String iconForRightAnswer()
		{
			iconForRightAnswer_called = true;
			return RIGHT_ANSWER;
		}

		@Override
		public String iconForWrongAnswer()
		{
			iconForWrongAnswer_called = true;
			return WRONG_ANSWER;
		}

		@Override
		public String iconForFirstQuestion()
		{
			iconForFirstQuestion_called = true;
			return FIRST_ANSWER;
		}

	}

	private FakeStreakView			fakeView;
	private FakeStreakAdapter		adapter;
	private StreakViewController	controller;

	@Before
	public void setup()
	{
		fakeView = new FakeStreakView();
		adapter = new FakeStreakAdapter();
		controller = new StreakViewController(fakeView, adapter);
	}

	@Test
	public void whenATestStarts_theStreakAdapterIsAskedForAnIconForTheFirstQuestion()
	{
		controller.testStarted();

		assertTrue(adapter.iconForFirstQuestion_called);
	}

	@Test
	public void whenATestStarts_theStreakViewHasTheFirstQuestionIconSet()
	{
		controller.testStarted();

		assertEquals(fakeView.lastSetIcon, FIRST_ANSWER);
	}

	@Test
	public void whenAQuestionIsAnsweredCorrectly_theStreakadapterIsAskedForTheIconForARightAnswer() throws Exception
	{
		controller.testQuestionAnsweredCorrectly(null, null);

		assertTrue(adapter.iconForRightAnswer_called);
	}
	
	@Test
	public void whenAQuestionIsAnsweredCorrectly_theStreakViewHasTheRightQuestionIconSet()
	{
		controller.testQuestionAnsweredCorrectly(null, null);

		assertEquals(fakeView.lastSetIcon, RIGHT_ANSWER);
	}

	@Test
	public void whenAQuestionIsAnsweredIncorrectly_theStreakadapterIsAskedForTheIconForAWrongAnswer() throws Exception
	{
		controller.testQuestionAnsweredIncorrectly(null);

		assertTrue(adapter.iconForWrongAnswer_called);
	}
	
	@Test
	public void whenAQuestionIsAnsweredIncorrectly_theStreakViewHasTheRightQuestionIconSet()
	{
		controller.testQuestionAnsweredIncorrectly(null);

		assertEquals(fakeView.lastSetIcon, WRONG_ANSWER);
	}

	// Code coverage? Yes please!
	@Test
	public void whenATestCompletes_thereIsNoInteractionWithTheStreakView()
	{
		controller.testFinished();
		
		assertFalse(fakeView.streakViewShowStreakIcon_called);
	}
	
	@Test
	public void whenAQuestionIsAsked_thereIsNoInteractionWithTheStreakView()
	{
		controller.testQuestionAsked(null, 1, 2);;
		
		assertFalse(fakeView.streakViewShowStreakIcon_called);
	}
}
