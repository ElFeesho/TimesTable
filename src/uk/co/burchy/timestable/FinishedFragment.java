package uk.co.burchy.timestable;

import uk.co.burchy.timestable.view.CompleteOverviewView;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

public class FinishedFragment extends Fragment {

	public static final String KEY_SCORE = "score";
	public static final String KEY_TIME = "time";
	public static final String KEY_CORRECT = "correct";
	public static final String KEY_WRONG = "wrong";
	
	private long m_score;
	private long m_time;
	private int m_correct;
	private int m_wrong;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		m_score = getArguments().getLong(KEY_SCORE);
		m_time = getArguments().getLong(KEY_TIME);
		m_correct = getArguments().getInt(KEY_CORRECT);
		m_wrong = getArguments().getInt(KEY_WRONG);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.frag_finished, container, false);
	}
	
	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		
		CompleteOverviewView overview = (CompleteOverviewView) view.findViewById(R.id.tt_complete_overview);
		overview.setOverview(m_score, m_time, m_correct, m_wrong);
		
		view.findViewById(R.id.tt_take_another).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				getActivity().startActivity(new Intent(getActivity(), MainActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
			}
		});
		
		view.findViewById(R.id.tt_complete_overview);
	}
	
}
