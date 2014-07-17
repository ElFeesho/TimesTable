package uk.co.burchy.timestable;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import android.os.Parcel;
import android.os.Parcelable;

public class Test implements Parcelable {
	
	public Test ()
	{
		m_testQuestions = new ArrayList<Question> ();
		Initialise ();
	}
	
	/** Used when deserialising a Test from a bundle */
	private Test(Parcel source) {
		m_testQuestions = new ArrayList<Question>();
		source.readList(m_testQuestions, getClass().getClassLoader());
		m_positionInTest = source.readInt();
	}
	
	/** Use to reset all the answers, keep current questions and start over */
	public void reRunTest () {
		Iterator<Question>	it = m_testQuestions.iterator();
		
		while (it.hasNext())
		{
			Question	aQuestion = it.next();
			aQuestion.resetAnswer ();
		}
		
		Initialise ();		
	}
	
	/** Use to check if there are more questions left in the test to answer */
	public boolean hasMoreQuestions () {
		return m_positionInTest < m_testQuestions.size()-1;
	}
	
	/** Use to get the next question to get an answer for */
	public Question askNextQuestion () {
		if (hasMoreQuestions())
		{
			if(m_testQuestions.get(m_positionInTest).getAnswerGiven() != Question.NO_ANSWER)
			{
				return m_testQuestions.get(++m_positionInTest);
			}
			else
			{
				return m_testQuestions.get(m_positionInTest);
			}
		}
		
		return null;
	}
	
	/** Returns the current score of the test */
	public int getScore () {
		return GetCorrectAnswers().size();
	}
	
	/** Returns a list of all the questions with correct answers */
	public List<Question> GetCorrectAnswers () {
		return GetAnswers (AnswerType.CORRECT_ANSWERS);
	}
	
	/** Returns a list of all the questions with incorrect answers */
	public List<Question> GetIncorrectAnswers () {
		return GetAnswers (AnswerType.INCORRECT_ANSWERS);
	}
	
	/** Returns the number of questions currently in the test */	
	public Integer GetNumberQuestions () {
		return m_testQuestions.size();
	}
	
	//** Returns the current question number */
	public Integer GetCurrentQuestion () {
		return m_positionInTest+1;
	}	
	
	/** Use to add questions to the test.  CANNOT use after you ask the first question (askNextQuestion method).  Must reRunTest or emptyTest before any new questions */
	public void addQuestion (Question aQuestion) {
		if (m_positionInTest == 0)
			m_testQuestions.add(aQuestion);
		
		//TO DO - THROW EXCEPTION IF TRY TO addQuestion after starting a test
	}
	
	public void SetTestWithIncorrectAnswers () {
		List<Question> 		incorrectAnswers = GetIncorrectAnswers ();
		
		for(Question aQuestion : incorrectAnswers)
		{
			aQuestion.resetAnswer();
		}
		
		m_positionInTest = 0;
		
		m_testQuestions = incorrectAnswers;
		
	}
	
	private List<Question> GetAnswers (AnswerType checkType) {
		List<Question>		answers = new ArrayList<Question> ();
		
		for(Question aQuestion : m_testQuestions)
		{
			if ( (checkType == AnswerType.CORRECT_ANSWERS && aQuestion.isAnswerCorrect()) || (checkType == AnswerType.INCORRECT_ANSWERS && aQuestion.getAnswerGiven() != Question.NO_ANSWER && !aQuestion.isAnswerCorrect()) )
			{				
				answers.add(aQuestion);
			}
		}
		
		return answers;
	}
	
	private void Initialise () {
		m_positionInTest = 0;
	}
	
	private enum AnswerType {
		CORRECT_ANSWERS,
		INCORRECT_ANSWERS
	}

	@Override
	public int describeContents() {
		return 0;
	}
	
	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeList(m_testQuestions);
		dest.writeInt(m_positionInTest);
	}
	
	public static Creator<Test> CREATOR = new Creator<Test>()
	{
		@Override
		public Test createFromParcel(Parcel source) {
			return new Test(source);
		}

		@Override
		public Test[] newArray(int size) {
			return new Test[size];
		}
	};
	
	List<Question>				m_testQuestions;
	int							m_positionInTest;
}
