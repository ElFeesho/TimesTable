package uk.co.burchy.timestable;

import android.os.Parcel;
import android.os.Parcelable;

/** Class to hold a single times table question, the answer provided and provides the correct answer */
public class Question implements Parcelable {

	public final static Integer NO_ANSWER = null;
	
	/** Must provide the question and the times table to construct the object, e.g. question × table */
	public Question (Integer question, Integer table) {
		setQuestion (question, table, true);
	}
	
	/** Parcelable constructor, calls private function to serialise data back into class variables */
	public Question (Parcel in) {
		readFromParcel (in);
	}
	
	public static final Parcelable.Creator<Question> CREATOR = new Parcelable.Creator<Question>() {
		public Question createFromParcel (Parcel in) {
			return new Question (in);
		}
		
		public Question[] newArray (int size) {
			return new Question[size];
		}
	};
	
	@Override
	public int describeContents () {
		return 0;
	}
	
	/** Serialise class data out to parcel */
	@Override
	public void writeToParcel (Parcel dest, int flags) {
		dest.writeInt(m_question);
		dest.writeInt(m_table);
		dest.writeInt(m_answer);
	}
	
	/** Can be used to reset the question after object construction */ 
	public void setQuestion (Integer question, Integer table) {
		m_question = question;
		m_table = table;
	}
	
	/** Overloaded to allow the answer to be optionally reset when setting the question post construction */
	public void setQuestion (Integer question, Integer table, Boolean resetAnswer) {
		setQuestion (question, table);
		if (resetAnswer)
			setAnswer (NO_ANSWER);
	}
	
	/** Use to provide the answer to the question */
	public void setAnswer (Integer answer) {
		m_answer = answer;
	}
	
	/** Use to reset the answer in the question */
	public void resetAnswer () {
		m_answer = NO_ANSWER;
	}
	
	/** Retrieve the question part of the 'sum' e.g. if sum is 3 x 5, returns 3 */
	public Integer getQuestion () {
		return m_question;
	}
	
	/** Retrieve the table part of the 'sum' e.g. if sum is 3 x 5, returns 5 */
	public Integer getTable () {
		return m_table;
	}
	
	/** Returns the entire sum as a string, e.g. if sum is 3 x 5, returns '3 x 5' */
	public String getQuestionAsString () {
		String	question;
		question = getQuestion () + " × " + getTable () + " = ";
		
		return question;
	}
	
	/** Returns the entire sum as a string, e.g. if sum is 3 x 5, returns '3 × 5'.  Setting format true, includes tabs in formatting */
	public String getQuestionAsString (boolean format) {
		String	question;
		if (format)
			question = getQuestion () + "\t×\t" + getTable () + "\t=\t";
		else
			question = getQuestion () + " × " + getTable () + " = ";
		
		return question;
	}
	
	
	/** Use to retrieve the answer provided to the question, if not answered NO_ANSWER is returned */
	public Integer getAnswerGiven () {
		return m_answer;
	}
	
	/** Use to retrieve the correct answer to the question */
	public Integer getCorrectAnswer () {
		return (m_question * m_table);
	}
	
	/** Use to determine if the answer provided was correct */
	public Boolean isAnswerCorrect () {
		if (m_answer != null && m_answer.equals (getCorrectAnswer ()))
			return true;
		
		return false;
	}
	
	/* Serialise class data back in from Parcel */
	private void readFromParcel (Parcel in) {
		m_question	= in.readInt();
		m_table		= in.readInt();
		m_answer	= in.readInt();
	}
	
	private Integer	m_question;
	private Integer m_table;
	private Integer m_answer;
}
