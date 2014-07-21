package uk.co.burchy.timestable.model;

import java.util.ArrayList;

import android.os.Parcel;
import android.os.Parcelable;

public class Test extends ArrayList<Question> implements Parcelable {

	private static final long	serialVersionUID	= 1003980352002751195L;

	private long	m_seed;

	private String 	m_tables;
	
	
	/**
	 * Set the seed that was used to generate this test
	 * @param seed
	 */
	public void setSeed(long seed)
	{
		m_seed = seed;
	}
	
	/**
	 * Gets the seed that was used to generate this test
	 * @return
	 */
	public long getSeed()
	{
		return m_seed;
	}
	
	public void setTables(String tables)
	{
		m_tables = tables;
	}
	
	public String getTables()
	{
		return m_tables;
	}

	@Override
	public int describeContents()
	{
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags)
	{
		dest.writeLong(m_seed);
		dest.writeString(m_tables);
		dest.writeList(this);
	}
	
	public static final Creator<Test> CREATOR = new Creator<Test>()
	{
		
		@Override
		public Test[] newArray(int size)
		{
			return new Test[size];
		}
		
		@Override
		public Test createFromParcel(Parcel source)
		{
			Test test = new Test();
			test.setSeed(source.readLong());
			test.setTables(source.readString());
			source.readList(test, getClass().getClassLoader());
			return test;
		}
	}; 
}
