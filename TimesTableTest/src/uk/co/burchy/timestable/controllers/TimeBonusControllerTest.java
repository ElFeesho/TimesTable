package uk.co.burchy.timestable.controllers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import uk.co.burchy.timestable.controllers.TimeBonusController.InvocationRepeater;
import uk.co.burchy.timestable.controllers.TimeBonusController.TimeBonus;
import uk.co.burchy.timestable.controllers.TimeBonusController.TimeBonusAdapter;

public class TimeBonusControllerTest
{
	private static class FakeTimeBonus implements TimeBonus
	{
		public float	timeBonusTimeLeft;
		public boolean	timeBonusDisplay_called;

		@Override
		public void timeBonusDisplay(float aTimeLeft)
		{
			timeBonusDisplay_called = true;
			timeBonusTimeLeft = aTimeLeft;
		}
	}
	
	private static class FakeTimeBonusAdapter implements TimeBonusAdapter
	{
		public float STATE_timeBonusTimeLeft = 0.0f;
		public boolean	timeBonusStartBonus_called;
		public boolean	timeBonusTimeLeft_called;
		public boolean	timeBonusQuestionIncorrect_called;
		public boolean	timeBonusQuestionCorrect_called;

		@Override
		public void timeBonusStartBonus()
		{
			timeBonusStartBonus_called = true;
		}

		@Override
		public float timeBonusTimeLeft()
		{
			timeBonusTimeLeft_called = true;
			return STATE_timeBonusTimeLeft;
		}

		@Override
		public void timeBonusQuestionCorrect()
		{
			timeBonusQuestionCorrect_called = true;
		}

		@Override
		public void timeBonusQuestionIncorrect()
		{
			timeBonusQuestionIncorrect_called = true;
		}	
	}
	
	private static class FakeInvocationRepeater implements InvocationRepeater
	{

		public boolean	startRepeating_called;
		public boolean	stopRepeating_called;
		public Runnable	repeatingRunnable;

		@Override
		public void startRepeating(Runnable aRunnable, long aDelay)
		{
			startRepeating_called = true;
			repeatingRunnable = aRunnable;
		}

		@Override
		public void stopRepeating()
		{
			stopRepeating_called = true;
		}

		public void simulateRepeat()
		{
			repeatingRunnable.run();
		}
		
	}
	

	private FakeTimeBonus	timeBonusView;
	private FakeTimeBonusAdapter	timeBonusAdapter;
	private TimeBonusController	controller;
	private FakeInvocationRepeater	invocationRepeater;
	
	@Before
	public void setup()
	{
		timeBonusView = new FakeTimeBonus();
		timeBonusAdapter = new FakeTimeBonusAdapter();
		invocationRepeater = new FakeInvocationRepeater();
		controller = new TimeBonusController(timeBonusView, timeBonusAdapter, invocationRepeater);
	}
	
	@Test
	public void whenAQuestionIsAsked_timeBonusAdapterIsNotified() throws Exception
	{
		controller.testQuestionAsked(null, 1, 1);
		assertTrue(timeBonusAdapter.timeBonusStartBonus_called);
	}

	@Test
	public void whenAQuestionIsAsked_timeBonusViewDisplaysFullTimeLeft() throws Exception
	{
		controller.testQuestionAsked(null, 1, 1);
		assertTrue(timeBonusView.timeBonusDisplay_called);
		assertEquals(1.0f, timeBonusView.timeBonusTimeLeft, 0.1f);
	}
	
	@Test
	public void whenAQuestionIsAsked_invocationRepeaterStartRepeatingCalled() throws Exception
	{
		controller.testQuestionAsked(null, 1, 1);
		assertTrue(invocationRepeater.startRepeating_called);
	}

	@Test
	public void whenAquestionIsAnsweredCorrectly_invocationRepeatedStopsUpdating() throws Exception
	{
		controller.testQuestionAnsweredCorrectly(null, null);
		assertTrue(invocationRepeater.stopRepeating_called);
	}

	@Test
	public void whenAquestionIsAnsweredCorrectly_bonusAdapterQuestionCorrectCalled() throws Exception
	{
		controller.testQuestionAnsweredCorrectly(null, null);
		assertTrue(timeBonusAdapter.timeBonusQuestionCorrect_called);
	}

	@Test
	public void whenAquestionIsAnsweredIncorrectly_invocationRepeatedStopsUpdating() throws Exception
	{
		controller.testQuestionAnsweredIncorrectly(null);
		assertTrue(invocationRepeater.stopRepeating_called);
	}

	@Test
	public void whenAquestionIsAnsweredIncorrectly_bonusAdapterQuestionIncorrectCalled() throws Exception
	{
		controller.testQuestionAnsweredIncorrectly(null);
		assertTrue(timeBonusAdapter.timeBonusQuestionIncorrect_called);
	}
	
	@Test
	public void whenATestIsFinished_invocationRepeaterStopRepeatingCalled() throws Exception
	{
		controller.testFinished();
		assertTrue(invocationRepeater.stopRepeating_called);
	}
	
	@Test
	public void whenATestQuestionIsAsked_andTheInvocationRepeaterRepeats_timeBonusViewIsUpdated_withAdaptersTimeBonusTimeLeft() throws Exception
	{
		timeBonusAdapter.STATE_timeBonusTimeLeft = 0.5f;
		
		controller.testQuestionAsked(null, 1, 1);
		
		invocationRepeater.simulateRepeat();
		
		assertTrue(timeBonusView.timeBonusDisplay_called);
		assertTrue(timeBonusAdapter.timeBonusTimeLeft_called);
		
		assertEquals(0.5f, timeBonusView.timeBonusTimeLeft, 0.1f);
	}

	@Test
	public void whenATestStarts_thereAreNoInteractions() throws Exception
	{
		controller.testStarted();

		assertFalse(timeBonusView.timeBonusDisplay_called);
		assertFalse(timeBonusAdapter.timeBonusQuestionCorrect_called);
		assertFalse(timeBonusAdapter.timeBonusQuestionIncorrect_called);
		assertFalse(timeBonusAdapter.timeBonusStartBonus_called);
		assertFalse(timeBonusAdapter.timeBonusTimeLeft_called);
		assertFalse(invocationRepeater.startRepeating_called);
		assertFalse(invocationRepeater.stopRepeating_called);
	}
}
