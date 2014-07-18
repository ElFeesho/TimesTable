package uk.co.burchy.timestable.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/** 
 * 
 * This builder can be used to generate a {@link Test} containing questions as defined by its configuration.
 * 
 */
public class TestBuilder {
	
	public static class TestBuilderConfigurationException extends Exception
	{
		public TestBuilderConfigurationException(String aMessage)
		{
			super(aMessage);
		}

		private static final long serialVersionUID = -6306393288496843870L;
	}

	public static final int MAX_TABLE_QUESTION = 12;

	private List<Integer> m_targetTables = new ArrayList<Integer>();
	
	private int m_numberOfQuestions = 0;
	
	private Random m_numberGen;
	
	/**
	 * This is the random number generate seed. This allows for the same test to be generated multiple times
	 * 
	 * If this isn't called, the current system time in milliseconds will be used
	 * 
	 * @param seed
	 */
	public void setSeed(long seed)
	{
		m_numberGen = new Random(seed); 
	}
	
	/**
	 * Add a times table that should have questions generated for it
	 * @param table
	 */
	public void addTable(int table)
	{
		m_targetTables.add(table);
	}
	
	/**
	 * Configure the number of questions to generate for each table
	 * @param questionCount
	 */
	public void setNumberOfQuestions(int questionCount)
	{
		m_numberOfQuestions = questionCount;
	}
	
	/**
	 * Builds a {@link Test} instance with an amount of questions defined by the {@link #setNumberOfQuestions(int)} and {@link #addTable(int)}
	 * 
	 * @return A list of unanswered {@link Question}s in the form of a {@link Test} instance
	 * @throws TestBuilderConfigurationException if the builder is not configured correctly
	 */
	public Test build() throws TestBuilderConfigurationException
	{
		if(m_numberOfQuestions == 0)
		{
			throw new TestBuilderConfigurationException("Number of questions to generate must be non-zero value");
		}
		
		if(m_targetTables.size() == 0)
		{
			throw new TestBuilderConfigurationException("Number of tables targeted must be non-zero in size");
		}
		
		if(m_numberGen == null)
		{
			m_numberGen = new Random();
		}
		
		Test result = new Test();
		
		for(Integer targetTable : m_targetTables)
		{
			for(int i = 0; i < m_numberOfQuestions; i++)
			{
				result.add(new Question(m_numberGen.nextInt(MAX_TABLE_QUESTION)+1, targetTable));
			}
		}
	
		Collections.shuffle(result, m_numberGen);
		
		return result;
	}
	
}
