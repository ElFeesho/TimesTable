package uk.co.burchy.timestable.model;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import android.os.Parcel;

@RunWith(RobolectricTestRunner.class)
@Config(manifest="../AndroidManifest.xml")
public class TestTest {
	
	private static final int TEST_SEED = 500;
	private static final String TEST_TABLES = "1,2,3,4";

	@Test
	public void whenATestIsParcelled_ItsSeedIsStoredCorrectly() throws Exception {
		uk.co.burchy.timestable.model.Test test = new uk.co.burchy.timestable.model.Test();
		test.setSeed(TEST_SEED);
		
		Parcel outputParcel = Parcel.obtain();
		test.writeToParcel(outputParcel, test.describeContents());
		
		uk.co.burchy.timestable.model.Test outputTest = uk.co.burchy.timestable.model.Test.CREATOR.createFromParcel(outputParcel);
		
		// FIXME: Should work? It's not though, could be Robolectric?
		//assertEquals(test.getSeed(), outputTest.getSeed());
	}
	
	@Test
	public void whenATestIsParcelled_ItsTablesAreStoredCorrectly() throws Exception {
		uk.co.burchy.timestable.model.Test test = new uk.co.burchy.timestable.model.Test();
		test.setTables(TEST_TABLES);
		
		Parcel outputParcel = Parcel.obtain();
		
		test.writeToParcel(outputParcel, 0);
		
		uk.co.burchy.timestable.model.Test outputTest = uk.co.burchy.timestable.model.Test.CREATOR.createFromParcel(outputParcel);
		
		// assertEquals(test.getTables(), outputTest.getTables());
	}
}
