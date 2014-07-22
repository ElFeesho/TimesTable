package uk.co.burchy.timestable.view;

import android.content.Context;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

public class SuperTextView extends TextView
{

	public SuperTextView(Context context, AttributeSet attrs)
	{
		this(context, attrs, 0);
	}

	public SuperTextView(Context context, AttributeSet attrs, int defStyle)
	{
		super(context, attrs, defStyle);
		
		Typeface tf =Typeface.createFromAsset(getContext().getAssets(), "albas.ttf");
		if(tf != null)
		{
			setTypeface(tf);
			setPaintFlags(getPaintFlags() | Paint.SUBPIXEL_TEXT_FLAG);
		}
	}
	
}
