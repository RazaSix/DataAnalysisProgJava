package tests;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import classes.statsCalc;

class TestStatsCalc {
	
	@Test
	void testGetMean() {
		//fail("Not yet implemented");

		statsCalc statsCalc = new statsCalc();
		ArrayList<Double> inList = new ArrayList<Double>();
		
		for(double x = 1; x < 6; x++) {
			inList.add(x);
		}
		
		double result = 3.0;
		assertEquals(result, statsCalc.getMean(inList));
	}

	@Test
	void testGetSampleMean() {
		statsCalc statsCalc = new statsCalc();
		ArrayList<Double> inList = new ArrayList<Double>();
		
		for(double x = 1; x < 6; x++) {
			inList.add(x);
		}
		
		double result = 3.75;
		assertEquals(result, statsCalc.getSampleMean(inList));
	}

	@Test
	void testGetModeReturnOne() {
		statsCalc statsCalc = new statsCalc();
		ArrayList<Double> inList = new ArrayList<Double>();
		
		for(double x = 1; x < 4; x++) {
			inList.add(x);
		}
		
		inList.add(5.0);
		inList.add(5.0);
		inList.add(5.0);
		
		for(double x = 5; x < 7; x++) {
			inList.add(x);
		}
		
		ArrayList<Double> result = new ArrayList<Double>();
		result.add(5.0);
		assertEquals(result, statsCalc.getMode(inList));
		
	}
	
	@Test
	void testGetModeReturnMore() {
		statsCalc statsCalc = new statsCalc();
		ArrayList<Double> inList = new ArrayList<Double>();
		
		for(double x = 1; x < 4; x++) {
			inList.add(x);
		}
		
		inList.add(5.0);
		inList.add(5.0);
		inList.add(5.0);
		
		for(double x = 6; x < 9; x++) {
			inList.add(x);
		}
		
		inList.add(9.0);
		inList.add(9.0);
		inList.add(9.0);
		
		ArrayList<Double> result = new ArrayList<Double>();
		result.add(5.0);
		result.add(9.0);
		
		assertEquals(result, statsCalc.getMode(inList));
		
	}

	@Test
	void testGetMedian() {
		statsCalc statsCalc = new statsCalc();
		ArrayList<Double> inList = new ArrayList<Double>();
		
		for(double x = 11; x < 20; x++) {
			inList.add(x);
		}
		
		double result = 15.0;
		assertEquals(result, statsCalc.getMedian(inList));
	}

	@Test
	void testGetQuartile1() {
		statsCalc statsCalc = new statsCalc();
		ArrayList<Double> inList = new ArrayList<Double>();
		
		inList.add(5.0);
		inList.add(7.0);
		inList.add(4.0);
		inList.add(4.0);
		inList.add(6.0);
		inList.add(2.0);
		inList.add(8.0);
		
		
		double result = 4.0;
		assertEquals(result, statsCalc.getQuartile1(inList));
		
	}

	@Test
	void testGetQuartile2() {
		statsCalc statsCalc = new statsCalc();
		ArrayList<Double> inList = new ArrayList<Double>();
		
		inList.add(5.0);
		inList.add(7.0);
		inList.add(4.0);
		inList.add(4.0);
		inList.add(6.0);
		inList.add(2.0);
		inList.add(8.0);
		
		double result = 7.0;
		assertEquals(result, statsCalc.getQuartile2(inList));
	}

	@Test
	void testGetVariance() {
		statsCalc statsCalc = new statsCalc();
		ArrayList<Double> inList = new ArrayList<Double>();
		
		inList.add(25.0);
		inList.add(26.0);
		inList.add(27.0);
		inList.add(30.0);
		inList.add(32.0);
		double result = 6.8;
		assertEquals(result, statsCalc.getVariance(inList));
	}

	@Test
	void testGetPopStandardDeviation() {
		statsCalc statsCalc = new statsCalc();
		ArrayList<Double> inList = new ArrayList<Double>();
		
		inList.add(25.0);
		inList.add(26.0);
		inList.add(27.0);
		inList.add(30.0);
		inList.add(32.0);
		
		double result = 2.6076809620810595;
		assertEquals(result, statsCalc.getPopStandardDeviation(inList));
	}

	@Test
	void testGetSampleStandardDeviation() {
		statsCalc statsCalc = new statsCalc();
		ArrayList<Double> inList = new ArrayList<Double>();
		
		inList.add(1.0);
		inList.add(2.0);
		inList.add(4.0);
		inList.add(5.0);
		inList.add(8.0);
		
		double result = 2.7386127875258306;
		assertEquals(result, statsCalc.getSampleStandardDeviation(inList));
	}

	@Test
	void testRoundNum() {
		statsCalc statsCalc = new statsCalc();
		double result = 2.345;
		assertEquals(result, statsCalc.roundNum(2.3451, 3));
		
	}

	@Test
	void testGetNumberOfDecimalPlaces() {
		statsCalc statsCalc = new statsCalc();
		int result = 4;
		
		BigDecimal bigDec = new BigDecimal("7.5434");
		assertEquals(result, statsCalc.getNumberOfDecimalPlaces(bigDec));
		
	}

	@Test
	void testGetRange() {
		statsCalc statsCalc = new statsCalc();
		ArrayList<Double> inList = new ArrayList<Double>();
		
		inList.add(25.0);
		inList.add(26.0);
		inList.add(27.0);
		inList.add(30.0);
		inList.add(32.0);
		
		double result = (32-25);
		assertEquals(result, statsCalc.getRange(inList));
	}

}
