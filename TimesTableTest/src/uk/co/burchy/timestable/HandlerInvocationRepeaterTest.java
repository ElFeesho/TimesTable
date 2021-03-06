package uk.co.burchy.timestable;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

@RunWith(RobolectricTestRunner.class)
@Config(manifest=Config.NONE)
public class HandlerInvocationRepeaterTest
{
	private int testRepeatingRunnable_callCount = 0;
	
	private HandlerInvocationRepeater	handlerInvocationRepeater;
	private Runnable	testRepeatingRunnable = new Runnable()
	{
		@Override
		public void run()
		{
			testRepeatingRunnable_callCount++;
		}
	};

	@Before
	public void setup()
	{
		handlerInvocationRepeater = new HandlerInvocationRepeater();
		testRepeatingRunnable_callCount = 0;	
	}

	@Test
	public void whenARunnableHas_startRepeating_itShouldBeInvoked_afterDelay() throws Exception
	{
		handlerInvocationRepeater.startRepeating(testRepeatingRunnable, 1000);

		Robolectric.getUiThreadScheduler().advanceBy(3500);
		assertEquals(3, testRepeatingRunnable_callCount);
	}
	
	@Test
	public void whenARunnableHasBeen_stopRepeating_itShouldNotBeInvoked_afterDelay() throws Exception
	{
		handlerInvocationRepeater.startRepeating(testRepeatingRunnable, 1000);
		handlerInvocationRepeater.stopRepeating();
		Robolectric.getUiThreadScheduler().advanceBy(3500);
		assertEquals(0, testRepeatingRunnable_callCount);
	}

}
