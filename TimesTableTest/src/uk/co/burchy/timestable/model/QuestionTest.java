package uk.co.burchy.timestable.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import android.os.Parcel;

@RunWith(RobolectricTestRunner.class)
@Config(manifest="../AndroidManifest.xml")
public class QuestionTest
{

	@Test
	public void twoQuestionsWithTheSameConfigurationAreEqual() throws Exception
	{
		Question one = new Question(1, 1);
		Question two = new Question(1, 1);
		
		assertTrue(one.equals(two));
	}

	@Test
	public void twoQuestionsWithTheSameTable_ButDifferentQuestionAreNotEqual() throws Exception
	{
		Question one = new Question(1, 1);
		Question two = new Question(2, 1);
		
		assertFalse(one.equals(two));
	}
	
	@Test
	public void twoQuestionsWithTheSameQuestion_ButDifferentTableAreNotEqual() throws Exception
	{
		Question one = new Question(1, 1);
		Question two = new Question(1, 2);
		
		assertFalse(one.equals(two));
	}

	
	@Test
	public void twoQuestionsWithTheDifferentConfigurationsAreNotEqual() throws Exception
	{
		Question one = new Question(1, 1);
		Question two = new Question(1, 2);
		
		assertFalse(one.equals(two));
	}

	@Test
	public void aQuestionComparedToNullIsNotEqual() throws Exception
	{
		Question one = new Question(1, 1);
		
		assertFalse(one.equals(null));
	}

	@Test
	public void aQuestionComparedToANonQuestionInstanceIsNotEqual() throws Exception
	{
		Question one = new Question(1, 1);
		
		assertFalse(one.equals(new Object()));
	}
	
	@Test
	public void whenAQuestionIsCreated_TheCorrectAnswerIsTheProductOfTheTableAndTheQuestion() throws Exception
	{
		Question testQuestion = new Question(2, 4);
		assertEquals(8, testQuestion.getCorrectAnswer());
	}
	
	@Test
	public void whenAQuestionIsParcelled_TheQuestionsTableIsRetainedCorrectly() throws Exception
	{
		Parcel parcel = Parcel.obtain();
		
		Question toBeParcelled = new Question(2, 3);
		toBeParcelled.writeToParcel(parcel, toBeParcelled.describeContents());
		
		Question toBeUnparcelled = Question.CREATOR.createFromParcel(parcel);
		
		assertEquals(toBeParcelled, toBeUnparcelled);
	}
	
	@Test
	public void theCreatorCanBeUsedToCreateAnArrayCapableOfHoldingQuetions() throws Exception
	{
		Question[] questionArray = Question.CREATOR.newArray(5);
		
		assertEquals(5, questionArray.length);
	}
	
}
