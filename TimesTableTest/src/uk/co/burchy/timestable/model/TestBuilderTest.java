package uk.co.burchy.timestable.model;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

@RunWith(RobolectricTestRunner.class)
@Config(manifest="../AndroidManifest.xml")
public class TestBuilderTest {

	@Test
	public void whenGivenATimesTable_RequestingATest_ResultsInATestWithTheGivenTimesTable() throws Exception {
		TestBuilder testTestBuilder = new TestBuilder();
		
		testTestBuilder.addTable(1);
		testTestBuilder.setNumberOfQuestions(1);
		testTestBuilder.setSeed(0);
		
		uk.co.burchy.timestable.model.Test builtTest = testTestBuilder.build();
		
		assertEquals(1, builtTest.get(0).getTable());
	}


	@Test
	public void whenGivenANumberOfQuestions_RequestingATest_ResultsInATestWithTheGivenCountOfQuestions() throws Exception {
		TestBuilder testTestBuilder = new TestBuilder();
		
		testTestBuilder.addTable(1);
		testTestBuilder.setNumberOfQuestions(3);
		testTestBuilder.setSeed(0);
		
		uk.co.burchy.timestable.model.Test builtTest = testTestBuilder.build();
		
		assertEquals(3, builtTest.size());
	}

	@Test
	public void whenGivenMultipleTables_TheNumberOfQuestionsIsTheProductOfTheRequestQuestionCount_andTheNumberOfTables() throws Exception {
		TestBuilder testTestBuilder = new TestBuilder();

		testTestBuilder.addTable(1);
		testTestBuilder.addTable(2);
		testTestBuilder.addTable(3);
		testTestBuilder.setNumberOfQuestions(3);
		testTestBuilder.setSeed(0);
		
		uk.co.burchy.timestable.model.Test builtTest = testTestBuilder.build();
		
		assertEquals(9, builtTest.size());
	}
	
	@Test
	public void whenTwoTestsAreBuiltWithSeparateBuilders_WithTheSameSeedAndConfiguration_TheTestsAreTheSame() throws Exception {
		TestBuilder testBuilderOne = new TestBuilder();
		TestBuilder testBuilderTwo = new TestBuilder();

		testBuilderOne.addTable(1);
		testBuilderTwo.addTable(1);
		testBuilderOne.addTable(2);
		testBuilderTwo.addTable(2);
		testBuilderOne.addTable(3);
		testBuilderTwo.addTable(3);
		

		testBuilderOne.setNumberOfQuestions(10);
		testBuilderTwo.setNumberOfQuestions(10);
		

		testBuilderOne.setSeed(0);
		testBuilderTwo.setSeed(0);
		
		uk.co.burchy.timestable.model.Test testOne = testBuilderOne.build();
		uk.co.burchy.timestable.model.Test testTwo = testBuilderTwo.build();
		
		assertTestsAreEqual(testOne, testTwo);
	}
	
	@Test
	public void whenTwoTestsAreBuiltWithSeparateBuilders_WithTheSameSeedAndConfiguration_TheOrderTablesAreAddedIsNotImportant() throws Exception {
		TestBuilder testBuilderOne = new TestBuilder();
		TestBuilder testBuilderTwo = new TestBuilder();

		testBuilderOne.addTable(1);
		testBuilderOne.addTable(2);
		testBuilderOne.addTable(3);

		testBuilderTwo.addTable(3);
		testBuilderTwo.addTable(2);
		testBuilderTwo.addTable(1);
		

		testBuilderOne.setNumberOfQuestions(10);
		testBuilderTwo.setNumberOfQuestions(10);
		

		testBuilderOne.setSeed(0);
		testBuilderTwo.setSeed(0);
		
		uk.co.burchy.timestable.model.Test testOne = testBuilderOne.build();
		uk.co.burchy.timestable.model.Test testTwo = testBuilderTwo.build();
		
		assertTestsAreEqual(testOne, testTwo);
	}
	
	@Test
	public void creatingATestWhenNoSeedHasBeenSet_theCurrentTimeIsUsedAsTheSeed() throws Exception {
		long currentTime = System.currentTimeMillis();
		// FIXME!
	}

	private void assertTestsAreEqual(uk.co.burchy.timestable.model.Test testOne,
			uk.co.burchy.timestable.model.Test testTwo) {
		assertEquals(testOne.size(), testTwo.size());
		
		for(int i = 0; i < testOne.size(); i++)
		{
			assertEquals(testOne.get(i), testTwo.get(i));
		}
		
	}
}
