package uk.co.burchy.timestable.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

public class StreakView extends TextView implements uk.co.burchy.timestable.controllers.StreakViewController.StreakView
{

	public StreakView(Context aContext)
	{
		this(aContext, null, 0);
	}

	public StreakView(Context aContext, AttributeSet aAttrs)
	{
		this(aContext, aAttrs, 0);
	}

	public StreakView(Context aContext, AttributeSet aAttrs, int aDefStyle)
	{
		super(aContext, aAttrs, aDefStyle);
	}

	@Override
	public void streakViewShowStreakIcon(String aIcon)
	{
		setText(aIcon);
	}

}
