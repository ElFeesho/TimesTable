package uk.co.burchy.timestable.view;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import uk.co.burchy.timestable.R;

@RunWith(RobolectricTestRunner.class)
@Config(manifest="../AndroidManifest.xml")
public class CurrentQuestionTextViewTest
{
	@Test
	public void whenCurrentQuestionIsSet_TextIsDisplayedAsExpected() throws Exception
	{
		CurrentQuestionTextView currentQuestion = new CurrentQuestionTextView(Robolectric.application, null);
		currentQuestion.showCurrentQuestion(2, 3);
		String expected = Robolectric.application.getString(R.string.tt_try_question__of__, 2, 3);
		assertEquals(currentQuestion.getText(), expected);
	}
}
