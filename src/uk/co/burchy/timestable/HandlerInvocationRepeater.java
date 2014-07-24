package uk.co.burchy.timestable;

import uk.co.burchy.timestable.controllers.TimeBonusController.InvocationRepeater;
import android.os.Handler;

public class HandlerInvocationRepeater implements InvocationRepeater
{
	private Handler m_handler = new Handler();
	
	private Runnable m_updateRunnable;
	private long m_delay;
	
	private Runnable m_updateWrapperRunnable = new Runnable()
	{
		@Override
		public void run()
		{
			m_updateRunnable.run();
			m_handler.postDelayed(m_updateWrapperRunnable, m_delay);
		}
	};
	
	@Override
	public void startRepeating(Runnable aRunnable, long aDelay)
	{
		m_delay = aDelay;
		m_updateRunnable = aRunnable;
		m_handler.postDelayed(m_updateWrapperRunnable, m_delay);
	}

	@Override
	public void stopRepeating()
	{
		m_handler.removeCallbacks(m_updateWrapperRunnable);
	}
}