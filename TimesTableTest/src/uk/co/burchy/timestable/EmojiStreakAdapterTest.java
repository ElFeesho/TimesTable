package uk.co.burchy.timestable;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

public class EmojiStreakAdapterTest
{
	private static final String	EMOJI_RIGHT_THREE	= "\uD83D\uDE4C";
	private static final String	EMOJI_RIGHT_TWO	= "\uD83D\uDE04";
	private static final String	EMOJI_RIGHT_ONE	= "\uD83D\uDE03";
	private static final String	EMOJI_NEUTRAL	= "\uD83D\uDE10";
	private static final String	EMOJI_WRONG_ONE	= "\uD83D\uDE15";
	private static final String	EMOJI_WRONG_TWO	= "\uD83D\uDE1E";
	private static final String	EMOJI_WRONG_THREE	= "\uD83D\uDE2D";
	
	private EmojiStreakAdapter adapter;
	
	@Before
	public void setup()
	{
		adapter = new EmojiStreakAdapter();
	}

	@Test
	public void whenAskedForFirstIcon_NeutralIsReturned() throws Exception
	{
		assertEquals(adapter.iconForFirstQuestion(), EMOJI_NEUTRAL);
	}

	@Test
	public void whenOneQuestionAnsweredCorrect_EmojiForOneRightIsReturned() throws Exception
	{
		assertEquals(adapter.iconForRightAnswer(), EMOJI_RIGHT_ONE);
	}
	
	@Test
	public void whenTwoQuestionsAnsweredCorrectly_EmojiForTwoRightIsReturned() throws Exception
	{
		// First right answer
		adapter.iconForRightAnswer();
		
		assertEquals(adapter.iconForRightAnswer(), EMOJI_RIGHT_TWO);
	}
	
	@Test
	public void whenThreeQuestionsAnsweredCorrectly_EmojiForThreeRightIsReturned() throws Exception
	{
		// First right answer
		adapter.iconForRightAnswer();
		// Second right answer
		adapter.iconForRightAnswer();
		
		assertEquals(adapter.iconForRightAnswer(), EMOJI_RIGHT_THREE);
	}

	@Test
	public void whenOneQuestionAnsweredIncorrect_EmojiForOneWrongtIsReturned() throws Exception
	{
		assertEquals(adapter.iconForWrongAnswer(), EMOJI_WRONG_ONE);
	}
	
	@Test
	public void whenTwoQuestionsAnsweredIncorrectly_EmojiForTwoWrongIsReturned() throws Exception
	{
		// First wrong answer
		adapter.iconForWrongAnswer();
		
		assertEquals(adapter.iconForWrongAnswer(), EMOJI_WRONG_TWO);
	}

	@Test
	public void whenThreeQuestionsAnsweredIncorrectly_EmojiForThreeWrongIsReturned() throws Exception
	{
		// First wrong answer
		adapter.iconForWrongAnswer();
		// Second wrong answer
		adapter.iconForWrongAnswer();
		
		assertEquals(adapter.iconForWrongAnswer(), EMOJI_WRONG_THREE);
	}
	

	@Test
	public void whenMoreThanThreeQuestionsAnsweredIncorrectly_OutputIsCapped_EmojiForThreeWrongIsReturned() throws Exception
	{
		// First wrong answer
		adapter.iconForWrongAnswer();
		// Second wrong answer
		adapter.iconForWrongAnswer();
		// Third wrong answer
		adapter.iconForWrongAnswer();
		
		assertEquals(adapter.iconForWrongAnswer(), EMOJI_WRONG_THREE);
	}

	@Test
	public void whenMoreThanThreeQuestionsAnsweredCorrectly_OutputIsCapped_EmojiForThreeRightIsReturned() throws Exception
	{
		// First wrong answer
		adapter.iconForRightAnswer();
		adapter.iconForRightAnswer();
		adapter.iconForRightAnswer();
		
		assertEquals(adapter.iconForRightAnswer(), EMOJI_RIGHT_THREE);
	}

	@Test
	public void whenAWrongAnswerIsGiven_AfterAStreakOfCorrectAnswers_EmojiForWrongOneIsReturned() throws Exception
	{
		adapter.iconForRightAnswer();
		adapter.iconForRightAnswer();
		adapter.iconForRightAnswer();
		
		assertEquals(adapter.iconForWrongAnswer(), EMOJI_WRONG_ONE);
	}

	@Test
	public void whenARightAnswerIsGiven_AfterAStreakOfIncorrectAnswers_EmojiForWrongTwoIsReturned() throws Exception
	{
		adapter.iconForWrongAnswer();
		adapter.iconForWrongAnswer();
		adapter.iconForWrongAnswer();
		
		assertEquals(adapter.iconForRightAnswer(), EMOJI_WRONG_TWO);
	}
}
