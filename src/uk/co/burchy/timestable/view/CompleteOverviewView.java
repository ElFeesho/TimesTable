package uk.co.burchy.timestable.view;

import uk.co.burchy.timestable.R;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.TableLayout;
import android.widget.TextView;

public class CompleteOverviewView extends TableLayout {

	private TextView m_score;
	private TextView m_time;
	private TextView m_correct;
	private TextView m_wrong;

	public CompleteOverviewView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	protected void onFinishInflate() {
		super.onFinishInflate();
		
		m_score = (TextView)findViewById(R.id.tt_score);
		m_time 	= (TextView)findViewById(R.id.tt_time);
		m_correct = (TextView)findViewById(R.id.tt_correct);
		m_wrong = (TextView)findViewById(R.id.tt_wrong);
	}
	
	public void setOverview(long score, long time, int correct, int wrong)
	{
		m_score.setText(score+"");
		m_time.setText(formatTime(time));
		m_correct.setText(correct+"");
		m_wrong.setText(wrong+"");
	}

	private CharSequence formatTime(long ms)
	{
		long hours = ms / 3600000;
		ms -= 3600000 * hours;
		long minutes = ms / 60000;
		ms -= minutes * 60000;
		long seconds = ms / 1000;
		ms -= seconds * 1000;
		
		return String.format("%02d:%02d:%02d.%d", hours, minutes, seconds, ms);
	}
}
