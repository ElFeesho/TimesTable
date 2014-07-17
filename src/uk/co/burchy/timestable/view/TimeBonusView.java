package uk.co.burchy.timestable.view;

import uk.co.burchy.timestable.TimeBonusController.TimeBonus;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.ProgressBar;

public class TimeBonusView extends ProgressBar implements TimeBonus
{

	public TimeBonusView(Context aContext)
	{
		this(aContext, null, 0);
	}

	public TimeBonusView(Context aContext, AttributeSet aAttrs)
	{
		this(aContext, aAttrs, 0);
	}

	public TimeBonusView(Context aContext, AttributeSet aAttrs, int aDefStyle)
	{
		super(aContext, aAttrs, aDefStyle);
		setMax(10000);
	}

	@Override
	public void timeBonusDisplay(float aTimeLeft)
	{
		setProgress((int) (getMax()*aTimeLeft));
	}

}
