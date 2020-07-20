package classes;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;

public class kMeans {
	
	//k number
	private int k;
	
	private int iterationNum;
	
	// x and y points 
	private ArrayList<Double> colX = new ArrayList<Double>();
	private ArrayList<Double> colY = new ArrayList<Double>();
	
	//Found clusters
	private ArrayList<ArrayList<Double>> foundClustersX = new ArrayList<ArrayList<Double>>();
	private ArrayList<ArrayList<Double>> foundClustersY = new ArrayList<ArrayList<Double>>();
	
	//Final Centroids
	private ArrayList<Double> finalCentroidsX = new ArrayList<Double>();
	private ArrayList<Double> finalCentroidsY = new ArrayList<Double>();
	
	//Using getMean and roundNum from statscalc
	private statsCalc calcMethods = new statsCalc();
	
	//Constructor
	public kMeans(int kNum, ArrayList<Double> inColX, ArrayList<Double> inColY) {
		this.k = kNum;
		this.colX = inColX;
		this.colY = inColY;
		kMeansAlgorithm();
	}

	
	//The final calculated clusters: X coordinates
	public ArrayList<ArrayList<Double>> getFoundClustersX(){
		return this.foundClustersX;
	}
	
	//The final calculated clusters: Y coordinates
	public ArrayList<ArrayList<Double>> getFoundClustersY(){
		return this.foundClustersY;
	}
	
	//Getting the number of iterations that the process took
	public int getIterationNum() {
		return this.iterationNum;
	}
	
	//Returns the X coordinate of the final centroids
	public ArrayList<Double> getFinalCentroidsX(){
		return this.finalCentroidsX;
	}
	
	//Returns the Y coordinate of the final centroids
	public ArrayList<Double> getFinalCentroidsY(){
		return this.finalCentroidsY;
	}
	
	
	// get the distance between two points
	public double getDistance(double centroidXa, double centroidYa, double Xb, double Yb) {
		 double distance = 0, calcX = 0, calcY = 0;
		 //distance = sqrt of [(Xa-Xb)^2 + (Ya-Yb)^2]
		 calcX = Math.pow((centroidXa-Xb),2);
		 calcY = Math.pow((centroidYa-Yb),2);
		 distance = Math.sqrt(calcX + calcY);
		 
		 return distance;	 
	}
	
	public void getFirstCentroid(ArrayList<Double> addToArrList, int inKNum, ArrayList<Double> inColumnData){
		
		
		//Sublist limit (Divides arraylist by k cluster to split data in parts to find centroids
		int limit = inColumnData.size()/inKNum;
		//Beginning and end of each sublist to get mean which will be the starting centroids
		int start = 0, end = 0;
		
		double calcCentroid = 0;
		
		for(int i = 0; i < inKNum; i++) {
			
			//Divide column by k and get mean of each part
			start = end;
			end+=limit;
			
			
			ArrayList<Double> subArrList = new ArrayList<Double>(inColumnData.subList(start, end));
			calcCentroid = calcMethods.getMean(subArrList);
			addToArrList.add(calcCentroid);
		}
		
		
		
		
	}
	
	
	public void kMeansAlgorithm() {
		
		//Copy incoming data and sort so as not to mess up og data
		ArrayList<Double> copyColX = new ArrayList<Double>(this.colX);
		ArrayList<Double> copyColY = new ArrayList<Double>(this.colY);
		//Sorted so the centroid calculations are more even when data is split by k number
		Collections.sort(copyColX);
		Collections.sort(copyColY);
		
		
		
		//Create and add "empty" arraylists based on k number to hold the clusters
		for(int x = 0; x < this.k; x++) {
			ArrayList<Double> clusterGroupX = new ArrayList<Double>();
			ArrayList<Double> clusterGroupY = new ArrayList<Double>();
			this.foundClustersX.add(clusterGroupX);
			this.foundClustersY.add(clusterGroupY);
		}
		
		
		//Holds the previous iteration of centroids
		ArrayList<Double> oldCentroidXs = new ArrayList<Double>();
		ArrayList<Double> oldCentroidYs = new ArrayList<Double>();
		//Holds the new calculated centroids
		ArrayList<Double> newCentroidXs = new ArrayList<Double>();
		ArrayList<Double> newCentroidYs = new ArrayList<Double>();

		
		//Finds the first centroids for colX and colY
		getFirstCentroid(newCentroidXs, this.k, copyColX);
		getFirstCentroid(newCentroidYs, this.k, copyColY);
		
		
		
		//Find the number of decimal places the centroids have to avoid unnecessary precision
		//This will be used to limit the number of decimal places used when calculating centroids later
		//Also helps when comparing the centroids later on (new vs the old)
		double firstCentroidDecPlaces = newCentroidXs.get(0);
		//BigDecimal convCentroid = new BigDecimal(Double.toString(calcCentroidX));
		BigDecimal convCentroid = new BigDecimal(Double.toString(firstCentroidDecPlaces));
		int decimalPrecision = calcMethods.getNumberOfDecimalPlaces(convCentroid);
		
		
		int loopCount = 0;
		boolean kMeansDone = false;
		
		//Loop to repeat process till centroid calculation does not change significantly
		while(kMeansDone == false) {
			loopCount++;
			
			
		//Arraylist of arraylists to hold each centroid's distances
		ArrayList<ArrayList<Double>> centroidDistancesArrayList = new ArrayList<ArrayList<Double>>();
		
		//Creates arraylists for each cluster to hold their distances
		//For each found centroid, calculate distance and add to distancesInfo
		//Which will then in turn be added to centroidDistancesArrayList
		for(int n = 0; n < this.k; n++) {
			ArrayList<Double> distancesInfo = new ArrayList<Double>();
			
			for(int p = 0; p < this.colX.size(); p++) {
				//Find all the distances for each point to a centroid
				//Here we get one position at a time i.e. cluster1.pos1 v cluster2.pos1 v cluster3.pos1 etc. depending on k number
				//This is then added to an array which has just their distances to the centroids
				//e.g. Distance Comparison: [0.7453559924999298, 5.185449728701349, 10.883218478209672]
				//e.g. Distance Comparison: [centroid1		   , centroid2		  , centroid3		  ]
				distancesInfo.add(getDistance(newCentroidXs.get(n), newCentroidYs.get(n), this.colX.get(p), this.colY.get(p)));
			}
			
			//Test print out
			//System.out.println("Cluster "+(n+1)+" distances: \n"+distancesInfo);
			
			//Adding the sub arrays to the super array
			//centroidDistancesArrayList holds [{clus1 distances}, {clus2 distances}, {clus3 distances}]
			centroidDistancesArrayList.add(distancesInfo);
		}
	
			
			
			int elementPosition = 0;
			//Accessing the individual arraylist that holds the distances for each point
			//Is no. of elements in the distances arraylists
			
				
			ArrayList<Double> distComparison = new ArrayList<Double>();
			
			//Compare the distances of each plot point to the centroids to find which are closest and "belong" to which centroid
			//Here we then compare the values
			//The position of the lowest number corresponds with the centroid it belongs to i.e. posi1 = centroid1
			for(int r = 0; r < centroidDistancesArrayList.get(0).size(); r++) {
					//System.out.println("Distances Comparison Loop Count: "+r);
					//c is the number of clusters
					for(int c = 0; c < centroidDistancesArrayList.size(); c++) {
					//System.out.println("Test- Cluster: "+(c+1)+" Number position: "+r+"  "+centroidDistancesArrayList.get(c).get(r));
					distComparison.add(centroidDistancesArrayList.get(c).get(r));
					elementPosition = r;
					}
	
					//System.out.println("Distance Comparison: "+distComparison.toString());
					
					double minNum = Double.MAX_VALUE;
					int relatedCluster = 0;
					for(int q = 0; q < distComparison.size(); q++) {
						if(distComparison.get(q) < minNum ) {
							minNum = distComparison.get(q);
							relatedCluster = q;
						}
					}
					
					//System.out.println("Lowest number is at Position "+elementPosition+" belongs to cluster "+(relatedCluster+1));
					
					//Here is where you add the elements to their found clusters
					this.foundClustersX.get(relatedCluster).add(this.colX.get(elementPosition));
					this.foundClustersY.get(relatedCluster).add(this.colY.get(elementPosition));
					distComparison.clear();
					
					

				}
		
		
		
		
		//Compares the newly calculated centroids in the latest iteration with ones from the previous iteration
		//If there is no change, then the loop stops as the data has been clustered as much as can be done
		if ((oldCentroidXs.equals(newCentroidXs) == true) && (oldCentroidYs.equals(newCentroidYs) == true)) {
			//System.out.println("Iteration No:"+ (loopCount+1));
			this.iterationNum = (loopCount+1);
			
			//System.out.println("Final Centroids X: "+newCentroidXs.toString()+"\nFinal  Centroids Y: "+newCentroidYs.toString());
			this.finalCentroidsX = new ArrayList<>(newCentroidXs);
			this.finalCentroidsY = new ArrayList<>(newCentroidYs);
			//System.out.println("X \n"+foundClustersX.toString());
			//System.out.println("Y \n"+foundClustersY.toString());
			
			
			kMeansDone = true;
		}
		else 
		{
			oldCentroidXs.clear();
			oldCentroidYs.clear();
			
			//Copy over the now old centroids to the oldcentroids arraylist
			for(int copyList = 0; copyList < newCentroidXs.size(); copyList++) {
				oldCentroidXs.add(newCentroidXs.get(copyList));
				oldCentroidYs.add(newCentroidYs.get(copyList));
			}
			
			
			//Collections.copy(oldCentroidXs, newCentroidXs);
			//Collections.copy(oldCentroidYs, newCentroidYs);
			
			
			//Clear the arraylist so the new centroids can be calculated and added
			newCentroidXs.clear();
			newCentroidYs.clear();
			
			for(int newC = 0; newC < this.foundClustersX.size(); newC++) {
				double meanClusterX = calcMethods.roundNum(calcMethods.getMean(this.foundClustersX.get(newC)), decimalPrecision);
				double meanClusterY = calcMethods.roundNum(calcMethods.getMean(this.foundClustersY.get(newC)), decimalPrecision);
				
				newCentroidXs.add(meanClusterX);
				newCentroidYs.add(meanClusterY);
			}
			
			//Clearing data
			for(int clearX = 0; clearX < this.k; clearX++) {
				this.foundClustersX.get(clearX).clear();
				this.foundClustersY.get(clearX).clear();
			}
			
		}
		/*
			List<Integer> source = Arrays.asList(1,2,3);
			List<Integer> dest = Arrays.asList(4,5,6);
			Collections.copy(dest, source);
		*/
		
		
		

		}
		
	}
	
	
}
