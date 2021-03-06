package uk.co.burchy.timestable.view;

import uk.co.burchy.timestable.R;
import uk.co.burchy.timestable.controllers.CurrentQuestionController.CurrentQuestionView;
import android.content.Context;
import android.util.AttributeSet;

public class CurrentQuestionTextView extends SuperTextView implements CurrentQuestionView
{

	public CurrentQuestionTextView(Context context, AttributeSet attrs)
	{
		this(context, attrs, 0);
	}

	public CurrentQuestionTextView(Context context, AttributeSet attrs, int defStyle)
	{
		super(context, attrs, defStyle);
	}

	@Override
	public void showCurrentQuestion(int questionNumber, int totalQuestions)
	{
		setText(getResources().getString(R.string.tt_try_question__of__, questionNumber, totalQuestions));
	}

}
