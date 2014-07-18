package uk.co.burchy.timestable.model;

import android.os.Parcel;
import android.os.Parcelable;

/** This class represents a question that the user will answer*/
public class Question implements Parcelable {
	
	private int	m_question;
	private int m_table;
	
	/** Must provide the question and the times table to construct the object, e.g. question × table */
	public Question (int question, int table) {
		setQuestion (question, table);
	}
	
	/** Can be used to reset the question after object construction */ 
	public void setQuestion (int question, int table) {
		m_question = question;
		m_table = table;
	}
	
	/** Retrieve the question part of the 'sum' e.g. if sum is 3 x 5, returns 3 */
	public int getQuestion () {
		return m_question;
	}
	
	/** Retrieve the table part of the 'sum' e.g. if sum is 3 x 5, returns 5 */
	public int getTable () {
		return m_table;
	}
	
	/** Use to retrieve the correct answer to the question */
	public int getCorrectAnswer () {
		return (m_question * m_table);
	}
	
	/** Parcelable constructor, calls private function to serialise data back into class variables */
	public Question (Parcel in) {
		readFromParcel (in);
	}
	
	public static final Parcelable.Creator<Question> CREATOR = new Parcelable.Creator<Question>() {
		@Override
		public Question createFromParcel (Parcel in) {
			return new Question (in);
		}
		
		@Override
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
	}
	
	/* Serialise class data back in from Parcel */
	private void readFromParcel (Parcel in) {
		m_question	= in.readInt();
		m_table		= in.readInt();
	}
	
}
