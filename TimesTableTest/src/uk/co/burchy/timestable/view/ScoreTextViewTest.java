package uk.co.burchy.timestable.view;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

@RunWith(RobolectricTestRunner.class)
@Config(manifest="../AndroidManifest.xml")
public class ScoreTextViewTest
{
	private ScoreTextView scoreTextView;
	
	@Before
	public void setup()
	{
		scoreTextView = new ScoreTextView(Robolectric.application, null);
	}

	@Test
	public void whenScoreTextViewDisplaysAScoreOfZero_theTextIsSetCorrectly() throws Exception
	{
		scoreTextView.displayScore(0, 0, 0);
		assertEquals(scoreTextView.getText().toString(), "Score: 0 0x + 0");
	}
	
	@Test
	public void whenScoreTextViewDisplaysAScoreOfTen_theTextIsSetCorrectly() throws Exception
	{
		scoreTextView.displayScore(10, 0, 0);
		assertEquals(scoreTextView.getText().toString(), "Score: 10 0x + 0");
	}
	@Test
	public void whenScoreTextViewDisplaysAMultiplierOfTen_theTextIsSetCorrectly() throws Exception
	{
		scoreTextView.displayScore(0, 0, 10);
		assertEquals(scoreTextView.getText().toString(), "Score: 0 10x + 0");
	}

	@Test
	public void whenScoreTextViewDisplaysABonusOfTen_theTextIsSetCorrectly() throws Exception
	{
		scoreTextView.displayScore(0, 10, 0);
		assertEquals(scoreTextView.getText().toString(), "Score: 0 0x + 10");
	}

}
