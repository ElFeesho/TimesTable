package uk.co.burchy.timestable;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.TextView;

public class QuestionView extends LinearLayout
{
	private TextView m_questionTextView;
	private TextView m_answerTextView;
	
	public QuestionView(Context context, AttributeSet attrs, int defStyle)
	{
		super(context, attrs, defStyle);
	}

	public QuestionView(Context context, AttributeSet attrs)
	{
		this(context, attrs, 0);
	}

	public QuestionView(Context context)
	{
		this(context, null, 0);
	}
	
	@Override
	protected void onFinishInflate()
	{
		super.onFinishInflate();
		m_questionTextView = (TextView) findViewById(R.id.test_question);
		m_answerTextView = (TextView) findViewById(R.id.test_answer);
	}
	
	public void displayQuestion(String question)
	{
		m_questionTextView.setText(question);
	}
	
	public void displayAnswer(String answer)
	{
		m_answerTextView.setText(answer);
	}

}
