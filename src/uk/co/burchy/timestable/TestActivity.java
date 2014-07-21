package uk.co.burchy.timestable;

import uk.co.burchy.timestable.TestFragment.TestFragmentListener;
import uk.co.burchy.timestable.model.Test;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.FragmentActivity;

public class TestActivity extends FragmentActivity
{

	private static final String KEY_TEST = "test";
	public final static String INCORRECT_ANSWERS = "uk.co.burchy.timestable.INCORRECT_ANSWERS";
	public final static String NUM_INCORRECT_ANSWERS = "uk.co.burchy.timestable.NUM_INCORRECT_ANSWERS";
	public final static Integer TEST_ON_INCORRECT_ANSWERS = 0;

	private Test m_test;
	
	private TestFragmentListener m_testFragmentListener = new TestFragmentListener() {
		
		@Override
		public void testComplete(long score, long totalDuration) {
			getSupportFragmentManager().beginTransaction().replace(android.R.id.content, new FinishedFragment(), "finished").commit();
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		
		if(savedInstanceState != null)
		{
			m_test = deserialiseTest(savedInstanceState);
		}
		else
		{
			m_test = deserialiseTest(getIntent().getExtras());
			
		}
		
		if(savedInstanceState == null)
		{
			getSupportFragmentManager().beginTransaction().add(android.R.id.content, new TestFragment(), "test").commit(); 
		}
		
	}
	
	@Override
	public void onAttachFragment(android.support.v4.app.Fragment fragment) {
		super.onAttachFragment(fragment);

		if(fragment instanceof TestFragment)
		{
			TestFragment testFrag = (TestFragment) fragment;
			
			testFrag.setTest(m_test);
			testFrag.setListener(m_testFragmentListener);
		}
	}
	
	@Override
	protected void onSaveInstanceState(Bundle outState)
	{
		super.onSaveInstanceState(outState);
		outState.putParcelableArrayList(KEY_TEST, m_test);
	}

	private Test deserialiseTest(Bundle bundle)
	{
		return bundle.getParcelable(KEY_TEST);
	}
	

	public static Intent intentForTest(Context context, Test test)
	{
		return new Intent(context, TestActivity.class).putExtra(KEY_TEST, (Parcelable)test);
	}

}
