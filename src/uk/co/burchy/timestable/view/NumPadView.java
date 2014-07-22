package uk.co.burchy.timestable.view;

import uk.co.burchy.timestable.R;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;

public class NumPadView extends RelativeLayout {

	public interface NumPadViewListener
	{
		public void numPadClicked(int number);
		public void numPadAnswer();
		public void numPadClear();
		public void numPadBackspace();
	}
	
	private class NumPadButtonClickListener implements OnClickListener
	{
		private int m_number = -1;
		
		public NumPadButtonClickListener(int number) {
			m_number = number;
		}
		
		@Override
		public void onClick(View v) {
			m_listener.numPadClicked(m_number);
		}
	}
	
	private NumPadViewListener m_listener;
	
	public NumPadView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public NumPadView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public NumPadView(Context context) {
		this(context, null, 0);
	}
	
	@Override
	protected void onFinishInflate() {
		super.onFinishInflate();

		// Loop unrolled for speed! (There is no nice way to loop over sequential id resources)
		findViewById(R.id.key_0).setOnClickListener(new NumPadButtonClickListener(0));
		findViewById(R.id.key_1).setOnClickListener(new NumPadButtonClickListener(1));
		findViewById(R.id.key_2).setOnClickListener(new NumPadButtonClickListener(2));
		findViewById(R.id.key_3).setOnClickListener(new NumPadButtonClickListener(3));
		findViewById(R.id.key_4).setOnClickListener(new NumPadButtonClickListener(4));
		findViewById(R.id.key_5).setOnClickListener(new NumPadButtonClickListener(5));
		findViewById(R.id.key_6).setOnClickListener(new NumPadButtonClickListener(6));
		findViewById(R.id.key_7).setOnClickListener(new NumPadButtonClickListener(7));
		findViewById(R.id.key_8).setOnClickListener(new NumPadButtonClickListener(8));
		findViewById(R.id.key_9).setOnClickListener(new NumPadButtonClickListener(9));
		
		findViewById(R.id.key_answer).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				m_listener.numPadAnswer();
			}
		});

		findViewById(R.id.key_clr).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				m_listener.numPadClear();
			}
		});
		
		findViewById(R.id.key_bkspc).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				m_listener.numPadBackspace();
			}
		});
	}

	public void setNumPadViewListener(NumPadViewListener numPadViewListener) {
		m_listener = numPadViewListener;
	}

}
