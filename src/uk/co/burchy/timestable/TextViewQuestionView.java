package uk.co.burchy.timestable;

import uk.co.burchy.timestable.controllers.QuestionViewController.QuestionView;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.TextView;

public class TextViewQuestionView extends LinearLayout implements QuestionView
{
	private TextView m_questionTextView;
	private TextView m_answerTextView;
	
	public TextViewQuestionView(Context context, AttributeSet attrs, int defStyle)
	{
		super(context, attrs, defStyle);
	}

	public TextViewQuestionView(Context context, AttributeSet attrs)
	{
		this(context, attrs, 0);
	}

	public TextViewQuestionView(Context context)
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
	
	@Override
	public void displayQuestion(String question)
	{
		m_questionTextView.setText(question);
	}
	
	@Override
	public void displayAnswer(String answer)
	{
		m_answerTextView.setText(answer);
	}

}
