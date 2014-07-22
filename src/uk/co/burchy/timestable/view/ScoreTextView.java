package uk.co.burchy.timestable.view;

import uk.co.burchy.timestable.controllers.ScoreController.ScoreView;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

public class ScoreTextView extends TextView implements ScoreView {

	public ScoreTextView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public ScoreTextView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	@Override
	public void displayScore(long score, long bonus, long multiplier) {
		setText("Score: "+score + " "+multiplier + "x + "+bonus);
	}

}
