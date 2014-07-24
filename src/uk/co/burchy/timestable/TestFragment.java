package uk.co.burchy.timestable;

import uk.co.burchy.timestable.TestRunner.TestRunnerObserver;
import uk.co.burchy.timestable.controllers.AnswerNotifierController;
import uk.co.burchy.timestable.controllers.AnswerNotifierController.AnswerNotifierControllerListener;
import uk.co.burchy.timestable.controllers.CurrentQuestionController;
import uk.co.burchy.timestable.controllers.CurrentQuestionController.CurrentQuestionView;
import uk.co.burchy.timestable.controllers.QuestionViewController;
import uk.co.burchy.timestable.controllers.QuestionViewController.QuestionView;
import uk.co.burchy.timestable.controllers.ScoreController;
import uk.co.burchy.timestable.controllers.ScoreController.ScoreView;
import uk.co.burchy.timestable.controllers.StreakViewController;
import uk.co.burchy.timestable.controllers.StreakViewController.StreakView;
import uk.co.burchy.timestable.controllers.TimeBonusController;
import uk.co.burchy.timestable.controllers.TimeBonusController.TimeBonus;
import uk.co.burchy.timestable.model.Answer;
import uk.co.burchy.timestable.model.Question;
import uk.co.burchy.timestable.model.QuestionRecord;
import uk.co.burchy.timestable.model.Test;
import uk.co.burchy.timestable.view.NumPadView;
import uk.co.burchy.timestable.view.NumPadView.NumPadViewListener;
import uk.co.burchy.timestable.view.PopupWindowAnswerNotifierView;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class TestFragment extends Fragment implements TestRunnerObserver {

	private static final String	KEY_STATE	= "state";
	
	public interface TestFragmentListener
	{
		public void testComplete(long score, long totalDuration, int correct, int wrong);
	}
	
	private TestRunner	m_testRunner;
	
	private StreakViewController 		m_streakViewController;
	private TimeBonusController 		m_timeBonusController;
	private QuestionViewController		m_questionViewController;
	private AnswerNotifierController	m_answerNotifierController;
	private CurrentQuestionController	m_currentQuestionController;
	private ScoreController 			m_scoreController;
	
	private TestFragmentListener m_listener;
	
	private AnswerNotifierControllerListener m_answerNotifierListener = new AnswerNotifierControllerListener()
	{
		@Override
		public void notificationComplete()
		{
			m_questionViewController.clearAnswer();
			if(!m_testRunner.isComplete())
			{
				m_testRunner.startNextQuestion();
			}
			else
			{
				m_listener.testComplete(m_scoreController.getScore(), m_testRunner.getTotalDuration(), m_testRunner.getCorrectCount(), m_testRunner.getWrongCount());
			}
			
		}
		
		@Override
		public void displayingNotificationForIncorrectAnswer()
		{
			
		}
		
		@Override
		public void displayingNotificationForCorrectAnswer()
		{
			
		}
	};
	
	private NumPadViewListener m_numPadListener = new NumPadViewListener()
	{
		@Override
		public void numPadClicked(int number)
		{
			m_questionViewController.addAnswerNumber(number);
		}
		
		@Override
		public void numPadClear()
		{
			m_questionViewController.clearAnswer();
		}
		
		@Override
		public void numPadBackspace()
		{
			m_questionViewController.backspaceAnswer();
		}
		
		@Override
		public void numPadAnswer()
		{
			if(m_questionViewController.getAnswerBuffer().length()>0)
			{
				m_testRunner.answerQuestion(Integer.parseInt(m_questionViewController.getAnswerBuffer()));
			}
		}
	};

	private Test m_test;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.frag_test, container, false);
		NumPadView numPad = (NumPadView) view.findViewById(R.id.test_numpad);
		numPad.setNumPadViewListener(m_numPadListener);
		
		PopupWindowAnswerNotifierView notifierView = new PopupWindowAnswerNotifierView(getActivity().findViewById(android.R.id.content), inflater.inflate(R.layout.cv_answer_toast, container, false), inflater.inflate(R.layout.cv_answer_incorrect_toast, container, false));
		
		m_streakViewController = new StreakViewController((StreakView) view.findViewById(R.id.test_streak), new EmojiStreakAdapter());
		m_timeBonusController = new TimeBonusController((TimeBonus) view.findViewById(R.id.tt_time_bonus), new CurrentTimeTimeBonusAdapter());
		m_questionViewController = new QuestionViewController(getString(R.string.tt_question_fmt), (QuestionView) view.findViewById(R.id.tt_question_view));
		m_currentQuestionController = new CurrentQuestionController((CurrentQuestionView) view.findViewById(R.id.test_question_num));
		m_scoreController = new ScoreController((ScoreView)view.findViewById(R.id.tt_score), new ScoreCalculator());		
		m_answerNotifierController = new AnswerNotifierController(notifierView, m_answerNotifierListener);
		
		if(savedInstanceState != null)
		{
			TestRunnerState state = savedInstanceState.getParcelable(KEY_STATE);
			m_testRunner = new TestRunner(m_test);
			m_testRunner.setState(state);
		}
		else
		{
			m_testRunner = new TestRunner(m_test);
		}
		
		m_testRunner.addObserver(m_streakViewController, m_timeBonusController, m_questionViewController, m_currentQuestionController, m_scoreController, m_answerNotifierController);
		
		m_testRunner.startNextQuestion();
		
		return view;
	}
	
	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putParcelable(KEY_STATE, m_testRunner.getState());
	}

	public void setListener(TestFragmentListener listener)
	{
		m_listener = listener;
	}

	public void setTest(Test test) {
		m_test = test;
	}

	@Override
	public void testFinished() {
		m_listener.testComplete(m_scoreController.getScore(), m_testRunner.getTotalDuration(), m_testRunner.getCorrectCount(), m_testRunner.getWrongCount());
	}

	@Override public void testQuestionAnsweredCorrectly(Question question, Answer answer) {}
	@Override public void testQuestionAnsweredIncorrectly(Question question) {}
	@Override public void testStarted() {}
	@Override public void testQuestionAsked(QuestionRecord question, int questionNumber, int totalQuestions) {}
}
