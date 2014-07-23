package uk.co.burchy.timestable.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import android.os.Parcel;

@RunWith(RobolectricTestRunner.class)
@Config(manifest="../AndroidManifest.xml")
public class QuestionRecordTest
{
	private final static Question TEST_QUESTION_2_3 = new Question(2, 3);
	private final static Question TEST_QUESTION_2_4 = new Question(2, 4);
	private final static Answer TEST_CORRECT_ANSWER = new Answer(true, 1000);
	private final static Answer TEST_INCORRECT_ANSWER = new Answer(false, 1000);
	
	@Test
	public void whenTwoQuestionRecordsContainDifferentQuestions_andNoAnswers_equalsReturnsFalse() throws Exception
	{
		QuestionRecord one = new QuestionRecord(TEST_QUESTION_2_3, System.currentTimeMillis());
		QuestionRecord two = new QuestionRecord(TEST_QUESTION_2_4, System.currentTimeMillis());
		
		assertNotEquals(one, two);
	}

	@Test
	public void whenTwoQuestionRecordsContainDifferentQuestions_andDifferentAnswers_equalsReturnsFalse() throws Exception
	{
		QuestionRecord one = new QuestionRecord(TEST_QUESTION_2_3, System.currentTimeMillis());
		QuestionRecord two = new QuestionRecord(TEST_QUESTION_2_4, System.currentTimeMillis());
		
		one.setAnswer(TEST_CORRECT_ANSWER);
		two.setAnswer(TEST_INCORRECT_ANSWER);
		
		assertNotEquals(one, two);
	}
	
	@Test
	public void whenTwoQuestionRecordsContainDifferentQuestions_andSameAnswers_equalsReturnsFalse() throws Exception
	{
		QuestionRecord one = new QuestionRecord(TEST_QUESTION_2_3, System.currentTimeMillis());
		QuestionRecord two = new QuestionRecord(TEST_QUESTION_2_4, System.currentTimeMillis());
		
		one.setAnswer(TEST_CORRECT_ANSWER);
		two.setAnswer(TEST_CORRECT_ANSWER);
		
		assertNotEquals(one, two);
	}
	
	@Test
	public void whenTwoQuestionRecordsContainDifferentAnswers_equalsReturnsFalse() throws Exception
	{
		QuestionRecord one = new QuestionRecord(TEST_QUESTION_2_3, System.currentTimeMillis());
		QuestionRecord two = new QuestionRecord(TEST_QUESTION_2_3, System.currentTimeMillis());
		
		one.setAnswer(TEST_CORRECT_ANSWER);
		two.setAnswer(TEST_INCORRECT_ANSWER);
		
		assertNotEquals(one, two);
	}

	@Test
	public void whenAQuestionRecordIsComparedToNull_equalsReturnsFalse() throws Exception
	{
		QuestionRecord record = new QuestionRecord(TEST_QUESTION_2_3, System.currentTimeMillis());
		
		assertNotEquals(record, null);
	}
	
	@Test
	public void whenAQuestionRecordIsComparedToNANonQuestionRecord_equalsReturnsFalse() throws Exception
	{
		QuestionRecord record = new QuestionRecord(TEST_QUESTION_2_3, System.currentTimeMillis());
		
		assertNotEquals(record, new Object());
	}
	
	@Test
	public void whenTwoQuestionRecordsHaveDifferentStartTimes_equalsReturnsFalse() throws Exception
	{
		QuestionRecord one = new QuestionRecord(TEST_QUESTION_2_3, System.currentTimeMillis());
		QuestionRecord two = new QuestionRecord(TEST_QUESTION_2_3, System.currentTimeMillis()-10);

		one.setAnswer(TEST_CORRECT_ANSWER);
		two.setAnswer(TEST_CORRECT_ANSWER);
		
		assertNotEquals(one, two);
	}
	
	@Test
	public void whenAQuestionRecordWithoutAnAnswerIsComparedToAQuestionWithAnAnswer_equalsReturnsFalse() throws Exception
	{
		QuestionRecord one = new QuestionRecord(TEST_QUESTION_2_3, System.currentTimeMillis());
		QuestionRecord two = new QuestionRecord(TEST_QUESTION_2_3, System.currentTimeMillis());
		two.setAnswer(TEST_CORRECT_ANSWER);

		assertNotEquals(one, two);
		assertNotEquals(two, one);
	}
	
	@Test
	public void whenAQuestionRecordIsComparedToAQuestionRecord_WithSameQuestion_andNoAnswers_equalsReturnsTrue() throws Exception
	{
		QuestionRecord one = new QuestionRecord(TEST_QUESTION_2_3, System.currentTimeMillis());
		QuestionRecord two = new QuestionRecord(TEST_QUESTION_2_3, System.currentTimeMillis());
		
		assertEquals(one, two);
	}

	@Test
	public void whenAQuestionRecordIsParcelled_containingOnlyAQuestion_whenItIsUnparcelledItContainsTheSameData() throws Exception
	{
		QuestionRecord toBeParcelled = new QuestionRecord(TEST_QUESTION_2_3, System.currentTimeMillis());
		Parcel parcel = Parcel.obtain();
		
		toBeParcelled.writeToParcel(parcel, toBeParcelled.describeContents());
		
		QuestionRecord toBeUnparcelled = QuestionRecord.CREATOR.createFromParcel(parcel);
		
		assertEquals(toBeParcelled, toBeUnparcelled);
	}

	@Test
	public void whenAQuestionRecordIsParcelled_containingAQuestionAndAnswer_whenItIsUnparcelledItContainsTheSameData() throws Exception
	{
		QuestionRecord toBeParcelled = new QuestionRecord(TEST_QUESTION_2_3, System.currentTimeMillis());
		toBeParcelled.setAnswer(TEST_CORRECT_ANSWER);
		Parcel parcel = Parcel.obtain();
		
		toBeParcelled.writeToParcel(parcel, toBeParcelled.describeContents());
		
		QuestionRecord toBeUnparcelled = QuestionRecord.CREATOR.createFromParcel(parcel);
		
		assertEquals(toBeParcelled, toBeUnparcelled);
	}

	@Test
	public void theCreatorCanBeUsedToCreateAnArrayCapableOfHoldingQuestionRecords() throws Exception
	{
		QuestionRecord[] recordArray = QuestionRecord.CREATOR.newArray(5);
		
		assertEquals(5, recordArray.length);
	}
}
