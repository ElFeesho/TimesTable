package uk.co.burchy.timestable.view;

import uk.co.burchy.timestable.controllers.AnswerNotifierController.AnswerNotifierCompleteListener;
import uk.co.burchy.timestable.controllers.AnswerNotifierController.AnswerNotifierView;
import android.view.Gravity;
import android.view.View;
import android.view.View.MeasureSpec;
import android.widget.PopupWindow;

public class PopupWindowAnswerNotifierView implements AnswerNotifierView
{
	private AnswerNotifierCompleteListener	m_notificationCompleteListener;
	
	private View	m_anchor;
	private View	m_correctView;
	private View	m_incorrectView;

	public PopupWindowAnswerNotifierView(View anchorView, View correctView, View incorrectView)
	{
		m_anchor = anchorView;
		m_correctView = correctView;
		m_incorrectView = incorrectView;
	}
	
	@Override
	public void displayAnswerCorrect()
	{
		displayPopupContainingView(m_correctView);
	}

	@Override
	public void displayAnswerIncorrect()
	{
		displayPopupContainingView(m_incorrectView);
	}

	@Override
	public void setNotificationCompleteListener(AnswerNotifierCompleteListener listener)
	{
		m_notificationCompleteListener = listener;
	}

	private void displayPopupContainingView(View v)
	{
		final PopupWindow popup = new PopupWindow();
		popup.setContentView(v);
		
		v.measure(MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED), MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
		
		popup.setWidth(v.getMeasuredWidth());
		popup.setHeight(v.getMeasuredHeight());
		popup.setAnimationStyle(android.R.style.Animation_Dialog);
		popup.update();
		
		popup.showAtLocation(m_anchor, Gravity.CENTER, 0, 0);;
		
		m_anchor.postDelayed(new Runnable()
		{
			@Override
			public void run()
			{
				popup.dismiss();
				m_notificationCompleteListener.answerNotificationComplete();
			}
		}, 1000);
	}

}
