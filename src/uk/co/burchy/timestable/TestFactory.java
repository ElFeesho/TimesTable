package uk.co.burchy.timestable;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.Stack;
import java.util.Vector;

/** Use this bad boy to specify the number of questions for each times table you could like, then to create a test object */
public class TestFactory {
	
	public final Integer MAX_TABLE_QUESTION = 12;

	public TestFactory () {
		
		// I want a linked list to hold Stacks of questions.  Each stack will hold a set of questions for a single times table
		m_Questions = new LinkedList<Stack<Question>> ();
		// Used to generate random numbers
		m_RandomGenerator = new Random (System.currentTimeMillis());
}
	
	/** Used to set the number of questions for a specific times table */
	public void	setQuestions (Integer numQuestions, Integer timesTable)
	{
		// Create a new stack to hold a set of question for a specific times table
		Stack<Question>	specificTTQuestions = new Stack<Question>();
		
		// Get the requested number of questions in a unique random sequence
		Stack<Integer> uniqueQuestions = generateUniqueRandomSums (numQuestions);
		
		
		// Populate stack object with random questions for the requested times table
		for (int i=1; i <= numQuestions; i++)
		{
			Question question = new Question (uniqueQuestions.pop(), timesTable);
			specificTTQuestions.push(question);
		}
		
		// Now add the stack to the list
		m_Questions.add(specificTTQuestions);
	}
	
		/** Use this to generate your test object.  The factory will be empty ready for the next test */
	public Test createRandomTest () {
		Test	tableTest = new Test ();
		
		while (m_Questions.size() > 0)
		{			
			// Randomly select a stack in list and extract the reference to the stack of questions
			Stack<Question> specificTT = m_Questions.get(getRandNumber (m_Questions.size()));
			
			// Now grap the next question off the stack
			Question aQuestion = specificTT.pop();
			
			// If that was the last question on the stack then remove the stack from the list
			if (specificTT.empty())
				m_Questions.remove(specificTT);
			
			// Finally add the question to our test
			tableTest.addQuestion (aQuestion);
		}
			
		return tableTest;
	}
	
	/** This method is used to generate a unique sequence of random questions, returned in a stack */
	private Stack<Integer> generateUniqueRandomSums (Integer numQuestions)
	{
		Stack<Integer> 	randomSums;
		Vector<Integer>	mySums;
		Integer			numQuestionsLeft = numQuestions;
		Integer			numSet = 0;
		
		randomSums 		= new Stack<Integer> ();
		mySums			= new Vector<Integer> ();
		
		// Perform this loop x times until we have the requested amount of questions in our returned stack 
		while (numQuestionsLeft > 0) {
			mySums.clear();
			
			// First determine how many unique questions are going into our set.  No more than max table question
			if (numQuestionsLeft >= MAX_TABLE_QUESTION)
				numSet = MAX_TABLE_QUESTION;
			else
				numSet = numQuestionsLeft;
			
			// Populate a vector with questions 1 - max question number
			for (int i = 1; i <= MAX_TABLE_QUESTION; i++)
				mySums.add(i);
			
			// Get the first sequence of unique questions
			while (numSet-- > 0) {
				randomSums.add (mySums.remove((int)getRandNumber(mySums.size ())));
				numQuestionsLeft--;
			}
		}
		
		return randomSums;
	}
		
	/** Creates a candidate random number for a question */
	private Integer getRandNumber (Integer maxNumber)
	{
		return m_RandomGenerator.nextInt(maxNumber);
	}
	
	private List<Stack<Question>> 	m_Questions;
	private	Random					m_RandomGenerator;
}
