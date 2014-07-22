package uk.co.burchy.timestable.view;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import uk.co.burchy.timestable.R;
import uk.co.burchy.timestable.view.NumPadView.NumPadViewListener;
import android.view.LayoutInflater;

@RunWith(RobolectricTestRunner.class)
@Config(manifest="../AndroidManifest.xml")
public class NumPadViewTest
{
	private NumPadView numPadView;
	private StubbedNumPadViewListener	npvListener;
	
	private class StubbedNumPadViewListener implements NumPadViewListener
	{
		public int		numPadClicked_number;
		public boolean	numPadanswer_called;
		public boolean	numPadClear_called;
		public boolean	numPadBackspace_called;

		@Override
		public void numPadClicked(int aNumber)
		{
			numPadClicked_number = aNumber;
		}

		@Override
		public void numPadAnswer()
		{
			numPadanswer_called = true;
		}

		@Override
		public void numPadClear()
		{
			numPadClear_called = true;
		}

		@Override
		public void numPadBackspace()
		{
			numPadBackspace_called = true;
		}		
	};
	
	@Before
	public void setup()
	{
		numPadView = (NumPadView) LayoutInflater.from(Robolectric.application).inflate(R.layout.cv_numpad, null);
		npvListener = new StubbedNumPadViewListener();
		
		numPadView.setNumPadViewListener(npvListener);
	}
	
	@Test
	public void whenANumberIsPressed_theNumPadViewListener_numPadClickedMethodIsInvoked_withCorrectNumberArgument() throws Exception
	{
		numPadView.findViewById(R.id.key_0).performClick();
		assertEquals(npvListener.numPadClicked_number, 0);
		
		numPadView.findViewById(R.id.key_1).performClick();
		assertEquals(npvListener.numPadClicked_number, 1);
		
		numPadView.findViewById(R.id.key_2).performClick();
		assertEquals(npvListener.numPadClicked_number, 2);
		
		numPadView.findViewById(R.id.key_3).performClick();
		assertEquals(npvListener.numPadClicked_number, 3);
		
		numPadView.findViewById(R.id.key_4).performClick();
		assertEquals(npvListener.numPadClicked_number, 4);
		
		numPadView.findViewById(R.id.key_5).performClick();
		assertEquals(npvListener.numPadClicked_number, 5);
		
		numPadView.findViewById(R.id.key_6).performClick();
		assertEquals(npvListener.numPadClicked_number, 6);
		
		numPadView.findViewById(R.id.key_7).performClick();
		assertEquals(npvListener.numPadClicked_number, 7);
		
		numPadView.findViewById(R.id.key_8).performClick();
		assertEquals(npvListener.numPadClicked_number, 8);
		
		numPadView.findViewById(R.id.key_9).performClick();
		assertEquals(npvListener.numPadClicked_number, 9);
	}

	@Test
	public void whenClearIsClicked_TheListenerClearMethodIsInvoked() throws Exception
	{
		numPadView.findViewById(R.id.key_clr).performClick();
		assertTrue(npvListener.numPadClear_called);
	}
	
	@Test
	public void whenBackspaceIsClicked_TheListenerBackspaceMethodIsInvoked() throws Exception
	{
		numPadView.findViewById(R.id.key_bkspc).performClick();
		assertTrue(npvListener.numPadBackspace_called);
	}
	
	@Test
	public void whenAnswerIsClicked_TheListenerAnswerMethodIsInvoked() throws Exception
	{
		numPadView.findViewById(R.id.key_answer).performClick();
		assertTrue(npvListener.numPadanswer_called);
	}
}
