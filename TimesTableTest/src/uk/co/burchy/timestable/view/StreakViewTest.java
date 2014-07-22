package uk.co.burchy.timestable.view;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

@RunWith(RobolectricTestRunner.class)
@Config(manifest=Config.NONE)
public class StreakViewTest
{
	@Test
	public void whenTheStreakViewIsAskedToDisplayAnIcon_TheTextIsSetToTheIconValue() throws Exception
	{
		StreakView streakView = new StreakView(Robolectric.application);
		streakView.streakViewShowStreakIcon("test");
		assertEquals(streakView.getText(), "test");
	}

}
