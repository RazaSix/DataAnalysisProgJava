package tests;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import classes.kMeans;

class TestKMeans {

	@Test
	void testGetFoundClustersX() {
		ArrayList<Double> xColumn = new ArrayList<Double>();
		ArrayList<Double> yColumn = new ArrayList<Double>();
		ArrayList<ArrayList<Double>> result = new ArrayList<ArrayList<Double>>();
		//Create subclusters to hold data
		for(int arr = 0; arr < 3; arr++) {
			ArrayList<Double> cluster = new ArrayList<Double>();
			result.add(cluster);
		}
		
		
		for(double a = 0; a < 3; a++) {
			xColumn.add(a);
			yColumn.add(a+1);
			
			result.get(0).add(a);
		}
		
		for(double b = 5; b < 10; b++) {
			xColumn.add(b);
			result.get(1).add(b);
			yColumn.add(b+1);
		}
		
		for(double c = 15; c < 20; c++) {
			xColumn.add(c);
			result.get(2).add(c);
			yColumn.add(c+1);
		}
		
		kMeans kMeans = new kMeans(3, xColumn, yColumn);
		
		assertEquals(result, kMeans.getFoundClustersX());
	}

	@Test
	void testGetFoundClustersY() {
		ArrayList<Double> xColumn = new ArrayList<Double>();
		ArrayList<Double> yColumn = new ArrayList<Double>();
		ArrayList<ArrayList<Double>> result = new ArrayList<ArrayList<Double>>();
		//Create subclusters to hold data
		for(int arr = 0; arr < 3; arr++) {
			ArrayList<Double> cluster = new ArrayList<Double>();
			result.add(cluster);
		}
		
		
		for(double a = 0; a < 3; a++) {
			xColumn.add(a);
			yColumn.add(a+1);
			
			result.get(0).add(a+1);
		}
		
		for(double b = 5; b < 10; b++) {
			xColumn.add(b);
			yColumn.add(b+1);
			
			result.get(1).add(b+1);
		}
		
		for(double c = 15; c < 20; c++) {
			xColumn.add(c);
			yColumn.add(c+1);
			
			result.get(2).add(c+1);
		}
		
		kMeans kMeans = new kMeans(3, xColumn, yColumn);
		
		assertEquals(result, kMeans.getFoundClustersY());
	}

	@Test
	void testGetFinalCentroidsX() {
		ArrayList<Double> xColumn = new ArrayList<Double>();
		ArrayList<Double> yColumn = new ArrayList<Double>();
		ArrayList<Double> result = new ArrayList<Double>();
		
		for(double a = 0; a < 3; a++) {
			xColumn.add(a);
			yColumn.add(a+1);
		}
		
		for(double b = 5; b < 10; b++) {
			xColumn.add(b);
			yColumn.add(b+1);
		}
		
		for(double c = 15; c < 20; c++) {
			xColumn.add(c);
			yColumn.add(c+1);
		}
		
		kMeans kMeans = new kMeans(3, xColumn, yColumn);
		
		result.add(1.0);
		result.add(7.0);
		result.add(17.0);
		
		assertEquals(result, kMeans.getFinalCentroidsX());
	}

	@Test
	void testGetFinalCentroidsY() {
		ArrayList<Double> xColumn = new ArrayList<Double>();
		ArrayList<Double> yColumn = new ArrayList<Double>();
		ArrayList<Double> result = new ArrayList<Double>();
		
		for(double a = 0; a < 3; a++) {
			xColumn.add(a);
			yColumn.add(a+1);
		}
		
		for(double b = 5; b < 10; b++) {
			xColumn.add(b);
			yColumn.add(b+1);
		}
		
		for(double c = 15; c < 20; c++) {
			xColumn.add(c);
			yColumn.add(c+1);
		}
		
		kMeans kMeans = new kMeans(3, xColumn, yColumn);
		
		result.add(2.0);
		result.add(8.0);
		result.add(18.0);
		
		assertEquals(result, kMeans.getFinalCentroidsY());
	}

	@Test
	void testGetDistance() {
		double result = 7.0710678118654755;
		
		ArrayList<Double> xColumn = new ArrayList<Double>();
		ArrayList<Double> yColumn = new ArrayList<Double>();
		
		for(double a = 0; a < 3; a++) {
			xColumn.add(a);
			yColumn.add(a+1);
		}
		
		for(double b = 5; b < 10; b++) {
			xColumn.add(b);
			yColumn.add(b+1);
		}
		
		for(double c = 15; c < 20; c++) {
			xColumn.add(c);
			yColumn.add(c+1);
		}
		
		kMeans kMeans = new kMeans(3, xColumn, yColumn);
		
		assertEquals(result, kMeans.getDistance(2, 3, 7, 8));
	}

}
