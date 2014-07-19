package uk.co.burchy.timestable;

import java.util.ArrayList;
import java.util.List;

import uk.co.burchy.timestable.model.QuestionRecord;

public class TestRunnerState
{
	private int						m_currentQuestion = 0;
	private List<QuestionRecord>	questionRecords = new ArrayList<QuestionRecord>();


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
}