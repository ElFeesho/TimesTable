package uk.co.burchy.timestable;

import uk.co.burchy.timestable.controllers.ScoreController;

public class ScoreCalculator implements ScoreController.ScoreCalculator {

	private long m_score = 0;
	private long m_multiplier = 1;
	private long m_timeBonus = 0;
	private long m_streak = 0;
	private long m_timeBonusThreshold = 10000;

	@Override
	public long calculateScore(long questionAnswer) {
		m_score += questionAnswer * m_multiplier + m_timeBonus;
		return m_score;
	}

	@Override
	public long calculateTimeBonus(long questionAnswer, long duration) {
		long result = 0;
		if (duration < m_timeBonusThreshold) {
			// Calculate which 5th of the time threshold the duration enters
			long delta = m_timeBonusThreshold - duration;
			float fraction = (float) delta / (float) m_timeBonusThreshold;
			// TODO: Brain. no worky.
		}
		return result;
	}

	@Override
	public long calculateMultiplier() {
		if (m_streak != 0 && m_streak % 3 == 0) {
			m_multiplier++;
		}
		return m_multiplier;
	}

	@Override
	public void questionAnsweredCorrectly() {
		m_streak++;
	}

	@Override
	public void questionAnsweredIncorrectly() {
		m_multiplier = 1;
		m_timeBonusThreshold = 10000;
	}

	@Override
	public long getScore()
	{
		return m_score;
	}
}
