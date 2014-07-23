package uk.co.burchy.timestable.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import android.os.Parcel;

@RunWith(RobolectricTestRunner.class)
@Config(manifest="../AndroidManifest.xml")
public class AnswerTest
{
	@Test
	public void givenTwoAnswersWithDifferentData_equalsReturnsFalse() throws Exception
	{	
		Answer one = new Answer(true, 1000);
		Answer two = new Answer(false, 0);
		assertNotEquals(one, two);
	}

	@Test
	public void givenTwoAnswersWithDifferentDurations_equalsReturnsFalse() throws Exception
	{	
		Answer one = new Answer(true, 1000);
		Answer two = new Answer(true, 0);
		assertNotEquals(one, two);
	}
	
	@Test
	public void givenTwoAnswersWithDifferentCorrectValues_equalsReturnsFalse() throws Exception
	{	
		Answer one = new Answer(true, 1000);
		Answer two = new Answer(false, 1000);
		assertNotEquals(one, two);
	}

	@Test
	public void givenAnAnswerComparedToANullObject_equalsReturnsFalse() throws Exception
	{	
		Answer one = new Answer(true, 1000);
		assertFalse(one.equals(null));
	}

	@Test
	public void givenAnAnswerComparedToANonAnswerInstance_equalsReturnsFalse() throws Exception
	{	
		Answer one = new Answer(true, 1000);
		assertFalse(one.equals(new Object()));
	}
	
	@Test
	public void givenTwoAnswersWithTheSameData_equalsReturnsTrue() throws Exception
	{
		Answer one = new Answer(true, 1000);
		Answer two = new Answer(true, 1000);
		assertEquals(one, two);
	}

	@Test
	public void whenACorrectAnswerIsParcelled_theAnswerIsUnparcelledWithTheCorrectData() throws Exception
	{
		Parcel parcel = Parcel.obtain();
		
		Answer toBeParcelled = new Answer(true, 1000);
		toBeParcelled.writeToParcel(parcel, toBeParcelled.describeContents());
		
		Answer toBeUnparcelled = Answer.CREATOR.createFromParcel(parcel);
		assertEquals(toBeParcelled, toBeUnparcelled);
	}
	
	@Test
	public void whenAIncorrectAnswerIsParcelled_theAnswerIsUnparcelledWithTheCorrectData() throws Exception
	{
		Parcel parcel = Parcel.obtain();
		
		Answer toBeParcelled = new Answer(false, 1000);
		toBeParcelled.writeToParcel(parcel, toBeParcelled.describeContents());
		
		Answer toBeUnparcelled = Answer.CREATOR.createFromParcel(parcel);
		assertEquals(toBeParcelled, toBeUnparcelled);
	}
	

	@Test
	public void theCreatorCanBeUsedToCreateAnArrayCapableOfHoldingAnswers() throws Exception
	{
		Answer[] answerArray = Answer.CREATOR.newArray(5);
		
		assertEquals(5, answerArray.length);
	}
}
