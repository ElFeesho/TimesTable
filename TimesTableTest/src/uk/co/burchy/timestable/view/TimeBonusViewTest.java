package uk.co.burchy.timestable.view;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

@RunWith(RobolectricTestRunner.class)
@Config(manifest=Config.NONE)
public class TimeBonusViewTest
{
	@Test
	public void whenTheTimeLeftForATimebonusIsSet_TheProgressBarReflectsTHeValue() throws Exception
	{
		TimeBonusView tbv = new TimeBonusView(Robolectric.application);
		tbv.timeBonusDisplay(0.5f);;
		assertEquals(tbv.getProgress(), tbv.getMax()/2);
	}

}
