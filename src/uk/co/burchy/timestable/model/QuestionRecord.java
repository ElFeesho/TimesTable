package uk.co.burchy.timestable.model;

import android.os.Parcel;
import android.os.Parcelable;

public class QuestionRecord implements Parcelable
{
	private Question m_question;
	private Answer m_answer;
	
	private long m_startTime;
	
	public QuestionRecord(Question question)
	{
		m_question = question;
		m_startTime = System.currentTimeMillis();
	}
	
	public void setAnswer(Answer answer)
	{
		m_answer = answer;
	}
	
	public Answer getAnswer()
	{
		return m_answer;
	}
	
	public Question getQuestion()
	{
		return m_question;
	}
	
	public long getStartTime()
	{
		return m_startTime;
	}
	
	private QuestionRecord(Parcel in)
	{
		m_startTime = in.readLong();
		m_question = in.readParcelable(getClass().getClassLoader());
		m_answer = in.readParcelable(getClass().getClassLoader());
	}
	
	@Override
	public int describeContents()
	{
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags)
	{
		dest.writeLong(m_startTime);
		dest.writeParcelable(m_question, 0);
		dest.writeParcelable(m_answer, 0);
	}
	
	public static final Creator<QuestionRecord> CREATOR = new Creator<QuestionRecord>()
	{
		
		@Override
		public QuestionRecord[] newArray(int size)
		{
			return new QuestionRecord[size];
		}
		
		@Override
		public QuestionRecord createFromParcel(Parcel source)
		{
			return new QuestionRecord(source);
		}
	};
	
}
