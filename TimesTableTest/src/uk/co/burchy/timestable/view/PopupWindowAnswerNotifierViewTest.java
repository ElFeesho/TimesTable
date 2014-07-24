package uk.co.burchy.timestable.view;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.util.ActivityController;

import uk.co.burchy.timestable.R;
import android.app.Activity;
import android.view.View;

@RunWith(RobolectricTestRunner.class)
@Config(manifest="../AndroidManifest.xml")
public class PopupWindowAnswerNotifierViewTest
{
	private Activity activity;
	private ActivityController activityController;
	private PopupWindowAnswerNotifierView view;
	private View	correctView;
	private View	incorrectView;
	
	@Before
	public void setup()
	{
		activityController = Robolectric.buildActivity(Activity.class).create().resume().start().visible();
		activity = activityController.get();
		activity.setContentView(new View(Robolectric.application));

		correctView = activity.getLayoutInflater().inflate(R.layout.cv_answer_toast, null);
		incorrectView = activity.getLayoutInflater().inflate(R.layout.cv_answer_incorrect_toast, null);
		view = new PopupWindowAnswerNotifierView(Robolectric.shadowOf(activity).getContentView(), correctView, incorrectView);
	}
	
	@After
	public void teardown()
	{
		activityController.destroy();
	}
	
	@Test
	public void whenTheNotifierViewIsToldToDisplayaCorrectNotification_theCorrectViewIsAddedToAPopUpWindow() throws Exception
	{
		//view.displayAnswerCorrect();
		//Robolectric.runUiThreadTasks();
		//assertTrue(Robolectric.shadowOf(correctView).isAttachedToWindow());
	}

}
