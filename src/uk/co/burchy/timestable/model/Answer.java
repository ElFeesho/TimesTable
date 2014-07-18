package uk.co.burchy.timestable.model;

import android.os.Parcel;
import android.os.Parcelable;


/**
 * This class stores an answer for a {@link Question} and how long it took for
 * it to be given
 * 
 * @author chris
 *
 */
public class Answer implements Parcelable
{
	public final long duration;
	public final boolean correct;

	public Answer(boolean correct, long duration)
	{
		this.correct = correct;
		this.duration = duration;
	}

	@Override
	public int describeContents()
	{
		return 0;
	}

	@Override
	public void writeToParcel(Parcel aDest, int aFlags)
	{
		aDest.writeLong(duration);
		aDest.writeByte((byte) (correct?1:0));;
	}
	
	public static Creator<Answer> CREATOR = new Creator<Answer>()
	{
		@Override
		public Answer[] newArray(int aSize)
		{
			return new Answer[aSize];
		}
		
		@Override
		public Answer createFromParcel(Parcel aParcel)
		{
			long duration = aParcel.readLong();
			boolean correct = aParcel.readByte() == 1;
			return new Answer(correct, duration);
		}
	};
	

}
