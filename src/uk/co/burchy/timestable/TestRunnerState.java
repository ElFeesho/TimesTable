package uk.co.burchy.timestable;

import java.util.ArrayList;
import java.util.List;

import uk.co.burchy.timestable.model.QuestionRecord;
import android.os.Parcel;
import android.os.Parcelable;

public class TestRunnerState implements Parcelable
{
	private int						m_currentQuestion = 0;
	private List<QuestionRecord>	questionRecords = new ArrayList<QuestionRecord>();

	public TestRunnerState()
	{
		
	}
	
	private TestRunnerState(Parcel source)
	{
		m_currentQuestion = source.readInt();
		source.readList(questionRecords, getClass().getClassLoader());
	}

	public int getCurrentQuestion()
	{
		return m_currentQuestion;
	}

	public void setCurrentQuestion(int currentQuestion)
	{
		m_currentQuestion = currentQuestion;
	}

	public void addQuestionRecord(QuestionRecord questionRecord)
	{
		questionRecords.add(questionRecord);
	}

	public QuestionRecord getCurrentQuestionRecord()
	{
		return questionRecords.get(m_currentQuestion-1);
	}

	public void incrementCurrentQuestion()
	{
		m_currentQuestion++;
	}

	@Override
	public int describeContents()
	{
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags)
	{
		dest.writeInt(m_currentQuestion);
		dest.writeList(questionRecords);
	}
	
	public static final Creator<TestRunnerState> CREATOR = new Creator<TestRunnerState>()
	{
		
		@Override
		public TestRunnerState[] newArray(int size)
		{
			return new TestRunnerState[size];
		}
		
		@Override
		public TestRunnerState createFromParcel(Parcel source)
		{
			return new TestRunnerState(source);
		}
	};

	public boolean noQuestionsAsked()
	{
		return m_currentQuestion == 0;
	}

	public long getTotalDuration()
	{
		long total = 0;
		for(QuestionRecord record : questionRecords)
		{
			total += record.getAnswer().duration;
		}
		return total;
	}
}