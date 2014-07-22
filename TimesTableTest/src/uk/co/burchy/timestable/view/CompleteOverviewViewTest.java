package uk.co.burchy.timestable.view;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import uk.co.burchy.timestable.R;
import android.view.LayoutInflater;
import android.widget.TextView;

@RunWith(RobolectricTestRunner.class)
@Config(manifest="../AndroidManifest.xml")
public class CompleteOverviewViewTest
{

	private static final int	WRONG_COUNT	= 4;
	private static final int	CORRECT_COUNT	= 5;
	private static final int	ONE_HOUR_ONE_SECOND_123MS	= 3601123;

	@Test
	public void whenATestCompletes_TheCompleteOverViewDisplaysAnOverOfTheResults() throws Exception
	{
		CompleteOverviewView overviewView = (CompleteOverviewView) LayoutInflater.from(Robolectric.application).inflate(R.layout.cv_complete_overview, null);
		
		overviewView.setOverview(1234, ONE_HOUR_ONE_SECOND_123MS, CORRECT_COUNT, WRONG_COUNT);

		assertEquals(((TextView)overviewView.findViewById(R.id.tt_correct)).getText(), 	"5");
		assertEquals(((TextView)overviewView.findViewById(R.id.tt_wrong)).getText(), 	"4");
		assertEquals(((TextView)overviewView.findViewById(R.id.tt_score)).getText(), 	"1234");
		assertEquals(((TextView)overviewView.findViewById(R.id.tt_time)).getText(), 	"01:00:01.123");
	}
}
