package controllers;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import javax.imageio.ImageIO;

import classes.NumFileProcessing;
import classes.TextClustering;
import classes.TextFileProcessing;
import classes.kMeans;
import classes.statsCalc;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.Chart;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.ScatterChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.FileChooser.ExtensionFilter;

public class progGUIController {

	/*
	 * Number Processing Panel elements
	 */
	@FXML
    private Button numOpenFileBtn;

    @FXML
    private RadioButton numSampleRdioBtn;

    @FXML
    private ToggleGroup dataType;

    @FXML
    private RadioButton numPopulationRdioBtn;

    @FXML
    private Button numRunStatsBtn;

    @FXML
    private TextField numKnumTxtField;

    @FXML
    private ChoiceBox<String> numColXdropdown;

    @FXML
    private ChoiceBox<String> numColYdropdown;

    @FXML
    private Button numRunKMeansBtn;

    @FXML
    private TextArea numResultsTxtArea;

    @FXML
    private Label numFileNameLabel;

    @FXML
    private TableView<ObservableList<String>> numTestTableView;

    @FXML
    private Button numExprtRsultsBtn;

    @FXML
    private Label fileLoadResultLabel;
	
	/*
	 * Text Processing Panel elements
	 */
    @FXML
    private Button txtRunAnalysisBtn;

    @FXML
    private Button txtRunClusteringBtn;

    @FXML
    private Button txtImportTextBtn;
    
    @FXML
    private Button txtRunSummaryBtn;

    @FXML
    private TextArea txtImportTextField;

    @FXML
    private TextArea txtAnalysisTextField;

    @FXML
    private Button txtExportResults;
	
    @FXML
    private Label textFileLoadResult;
    
    @FXML
    private Label textFileNameLabel;
    
    
    /*
     * Chart Panel Elements
     */
    @FXML
    private Tab chartTab;
    
    @FXML
    private Button loadNumClusteringBtn;

    @FXML
    private Button loadNumAnalysisBtn;

    @FXML
    private Button loadTextClusteringBtn;

    @FXML
    private ChoiceBox<String> xAxisChoiceBox;

    @FXML
    private ChoiceBox<String> yAxisChoiceBox;

    @FXML
    private Button chartBtn;

    @FXML
    private ToggleGroup chartType;  

    @FXML
    private RadioButton lineChartRdioBtn;

    @FXML
    private RadioButton areaChartRdioBtn;

    @FXML
    private RadioButton scatterChartRdioBtn;

    @FXML
    private RadioButton barChartRdioBtn;

    @FXML
    private Pane chartPanel;
    
    @FXML
    private ScrollPane chartScrollPane;
    
    @FXML
    private Canvas clusterCanvas;

    @FXML
    private Button loadOGNumDataBtn;

    @FXML
    private CheckBox chartAllDataCheckBox;

    @FXML
    private Button loadTextAnalysisBtn;
    
    @FXML
    private TextArea dataLoadedTxtField;   

    @FXML
    private Button chartExprtImgBtn;
	
	/*
	 * Num Panel Methods
	 */
	private NumFileProcessing fileData;
    private statsCalc statsCalc = new statsCalc();
    private kMeans kMeans;
    
    private String loadedFilename = "";
    private String resultTxtOutput = "";
    
    
    //For use in chart panel
    private ArrayList<String> numFileColumnNames = new ArrayList<String>();
    private ArrayList<ArrayList<Double>> numAllColData = new ArrayList<ArrayList<Double>>();
    private ArrayList<String> numSpreadDataLabels = new ArrayList<String>();
    
    
    public void numOpenFile(ActionEvent e) throws IOException {
    		
	      Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
	     /* // Display our window, using the scene graph.
	      stage.setTitle("Administrator"); 
	      stage.setScene(scene);
	      stage.show(); 
    	*/
	      
	    /* Get the filename+extension
	     * Create a file with headers
	     * Then use fileProcessing to see if it works
	     * And that it can get the file headers
	     * 
	     */
	      
    	try {
	    	FileChooser fileChooser = new FileChooser();
	    	fileChooser.setTitle("Open Data File");
	    	fileChooser.getExtensionFilters().addAll(
	    	         new ExtensionFilter("Text or Csv Files", "*.txt", "*.csv"));
	    	
	    	fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
	    	
	    	//fileChooser.showOpenDialog(stage);
	    	File selectedFile = fileChooser.showOpenDialog(stage);
	    	
	    	
	    	//System.out.println("FileName: "+selectedFile.getName()+"(From controller)");
	    	
	    	//Set filename to lable and reposition it a little to the left to account for the filename
	    	numFileNameLabel.setLayoutX(631);
	    	numFileNameLabel.setLayoutY(40);
	    	numFileNameLabel.setText("File Name: "+selectedFile.getName());
	    	loadedFilename = selectedFile.getName();
	    	//System.out.println(selectedFile.getAbsoluteFile());
	    	NumFileProcessing fileProcessing = new NumFileProcessing(selectedFile.getAbsoluteFile().toString());
	    	
	    	//Copied the file data for use in other methods without messing with the data in this method
	    	fileData = fileProcessing;
	    	//If there is an error reading the file, output the reason
	    	if(fileProcessing.getErrorCheck() == true) {
	    		//System.out.println("Error: "+fileProcessing.getErrorCheck());
	    		//System.out.println(fileProcessing.getErrorReason());
	    		
	    		popupWarning("Error Opening File, Check there is no text except for headers (If Applicable)");
	    	}
	    	//If no error
	    	else {
	    		//resultsTxtArea.setText(fileProcessing.getHeaders().toString());
	    		
	    		//Display success label
	    		fileLoadResultLabel.setText("File Loaded Successfully");
		    	fileLoadResultLabel.setVisible(true);
		    	numResultsTxtArea.clear();
		    	resultTxtOutput = "";
		    	//Enable buttons that run data analysis (disabled before file is loaded)
		    	numRunStatsBtn.setDisable(false);
		    	numRunKMeansBtn.setDisable(false);
	    		
	    		//Clearing previous columns and data info if they exist
	    		numTestTableView.getColumns().clear();
	    		numTestTableView.getItems().clear();
	    		
	    		//System.out.println("Size should be: "+fileProcessing.getFinalData().get(0).size());
	    		
	    		//Creating columns with headers if they exist
	    		List<String> columnNames = new ArrayList<String>();
	    		numFileColumnNames.clear();
	    		
	    		for(int j = 0; j < fileProcessing.getRowData().get(0).size(); j++) {

	    			if(fileProcessing.getCheckIfFileHasHeaders()) {
	    				//System.out.println("Test col names:"+fileProcessing.getHeaders());
	    				columnNames.add(fileProcessing.getHeaders().get(j));
	    				
	    				numFileColumnNames.add(fileProcessing.getHeaders().get(j));
	    			}
	    			else {
	    				//System.out.println("File has no headers so use the numbers");
	    				columnNames.add("Column "+(j+1));
	    				
	    				numFileColumnNames.add("Column "+(j+1));
	    			}
	    			
	    			
	    				    			
	    			/*
	    			TableColumn<String, Person> column1 = new TableColumn<>("Column "+(j+1));
			        column1.setCellValueFactory(new PropertyValueFactory<>("firstName"));
	    			
			        testTableView.getColumns().add(column1);
			        */
	    			//columnNames.add("Column "+(j+1));
	    			
	    			
	    			final int finalIdx = j;
	    		    TableColumn<ObservableList<String>, String> column = new TableColumn<>(
	    		            columnNames.get(j)
	    		    );
	    		    column.setCellValueFactory(param ->
	    		            new ReadOnlyObjectWrapper<>(param.getValue().get(finalIdx))
	    		    );
	    		    numTestTableView.getColumns().add(column);
	    			
	    		}
	    		
	    		
	    		//Enable chart load button
	    		loadOGNumDataBtn.setDisable(false);
	    		
	    		//Dropdowns
	    		
	    		ObservableList<String> dropdownList = FXCollections.observableArrayList(columnNames);
	    		
	    		numColXdropdown.getItems().setAll(dropdownList);
	    		numColXdropdown.getSelectionModel().selectFirst();
	    		
	    		numColYdropdown.getItems().setAll(dropdownList);
	    		numColYdropdown.getSelectionModel().select(1);
	    		
	    		numKnumTxtField.setText("3");
	    		
	    		String dataDisp = "";
	    		List<String> initIncomingData = new ArrayList<>();
	    		
	    		for(int i = 0; i < fileProcessing.getRowData().size(); i++) {
	    			for(int j = 0; j < fileProcessing.getRowData().get(i).size(); j++) {
	    				initIncomingData.add(fileProcessing.getRowData().get(i).get(j).toString());
	    				}
	    			
	    			//testTableView.getItems().add(new Person(testStringDisp));
	    			dataDisp += "\n";
	    			
	    			
	    			
	    			numTestTableView.getItems().add(
	                        FXCollections.observableArrayList(
	                                //Observable arraylist that holds the amount of data up to the no. of cols
	                        		initIncomingData
	                        )
	                );
	    			
	    			initIncomingData.clear();

	    		}

	    	}
	    	
    	}catch(RuntimeException re) {
    		//do nothing
    	}
    	
    }
    
    public void numRunStats() {
    	//chartTab.setDisable(false); --Disable the relevant buttons instead of the tab
    	loadNumAnalysisBtn.setDisable(false);
    	
    	numAllColData.clear();
        numSpreadDataLabels.clear();
        
    	
    	String disp = "";
    	resultTxtOutput = "";
    	List<String> columnNames = new ArrayList<String>();
    	for(int x = 0; x < fileData.getNumofCols(); x++) {
    		if(fileData.getCheckIfFileHasHeaders()) {
				//System.out.println("Test col names:"+fileProcessing.getHeaders());
				columnNames.add(fileData.getHeaders().get(x));
				
				//For use in charts
				numSpreadDataLabels.add(fileData.getHeaders().get(x));
			}
			else {
				//System.out.println("File has no headers so use the numbers");
				String combName = "Column "+(x+1);
				columnNames.add(combName);
				
				//For use in charts
				numSpreadDataLabels.add(combName);
			}
    	}
    	
    	
    	for(int n = 0; n < fileData.getNumofCols(); n++) {
    		
	    	double calcMean = statsCalc.getMean(fileData.getColumnsSorted().get(n));
			double calcMedian = statsCalc.getMedian(fileData.getColumnsSorted().get(n));
			String calcMode = statsCalc.getMode(fileData.getColumnsSorted().get(n)).toString();
			double calcRange = statsCalc.getRange(fileData.getColumnsSorted().get(n));
			double calcQ1 = statsCalc.getQuartile1(fileData.getColumnsSorted().get(n));
			double calcQ2 = statsCalc.getQuartile2(fileData.getColumnsSorted().get(n));
			double calcIQR = statsCalc.getInterQuartileRange(fileData.getColumnsSorted().get(n));
			double calcVariance = statsCalc.getVariance(fileData.getColumnsSorted().get(n));
			double calcStdDeviation = 0;
			
			int maxSize = fileData.getColumnsSorted().get(n).size();

			
	    	//System.out.println("Column Names: "+columnNames.toString());
	    	
	    	if(numSampleRdioBtn.isSelected()) {
	    		//System.out.println("Data is a sample");
	    		//System.out.println(fileData.getNumofCols());
	    		
	    		calcStdDeviation = statsCalc.getSampleStandardDeviation(fileData.getColumnsSorted().get(n)); 		
	    	}
	    	else if(numPopulationRdioBtn.isSelected()) {
	    		calcStdDeviation = statsCalc.getPopStandardDeviation(fileData.getColumnsSorted().get(n));
    			
	    	}
	    	else {
	    		System.out.println("Population or Sample Selection Error");
	    	}
	    	
	    	//Collecting Original Data for use in charts
	    	ArrayList<Double> numSpreadDataValues = new ArrayList<Double>();
	    	numSpreadDataValues.add(fileData.getColumnsSorted().get(n).get(0));
	    	numSpreadDataValues.add(fileData.getColumnsSorted().get(n).get(maxSize-1));
	    	numSpreadDataValues.add(calcMean);
	    	numSpreadDataValues.add(calcMedian);
	    	numSpreadDataValues.add(statsCalc.getMean(fileData.getColumnsSorted().get(n)));
	    	numSpreadDataValues.add(calcRange);
	    	numSpreadDataValues.add(calcQ1);
	    	numSpreadDataValues.add(calcQ2);
	    	numSpreadDataValues.add(calcIQR);
	    	numSpreadDataValues.add(calcVariance);
	    	numSpreadDataValues.add(calcStdDeviation);
	    	//Add to arraylist for use in charts
	    	numAllColData.add(numSpreadDataValues);
	    	//numSpreadDataValues.clear();
	    	
	    	
	    	disp+= columnNames.get(n).toUpperCase()
	    			+"\n--------------------------------"
	    			+"\nSmallest Number: "+fileData.getColumnsSorted().get(n).get(0)
	    			+"\nLargest Number:  "+fileData.getColumnsSorted().get(n).get(maxSize-1)
	    			+"\nMean: "+calcMean
	    			+"\nMedian: "+calcMedian
	    			+"\nMode: "+calcMode
	    			+"\nRange: "+calcRange
	    			+"\n1st Quartile: "+calcQ1
	    			+"\n2nd Quartile: "+calcQ2
	    			+"\nInter Quartile Range: "+calcQ2
	    			+"\nVariance: "+calcVariance
	    			+"\nStandard Deviation: "+calcStdDeviation+"\n\n";
	    	
	    	
	    	resultTxtOutput+= columnNames.get(n).toUpperCase()
	    			+System.getProperty("line.separator")+"--------------------------------"
	    			+System.getProperty("line.separator")+"Smallest Number: "+fileData.getColumnsSorted().get(n).get(0)
	    			+System.getProperty("line.separator")+"Largest Number:  "+fileData.getColumnsSorted().get(n).get(maxSize-1)
	    			+System.getProperty("line.separator")+"Mean: "+calcMean
	    			+System.getProperty("line.separator")+"Median: "+calcMedian
	    			+System.getProperty("line.separator")+"Mode: "+calcMode
	    			+System.getProperty("line.separator")+"Range: "+calcRange
	    			+System.getProperty("line.separator")+"1st Quartile: "+calcQ1
	    			+System.getProperty("line.separator")+"2nd Quartile: "+calcQ2
	    			+System.getProperty("line.separator")+"Inter Quartile Range: "+calcQ2
	    			+System.getProperty("line.separator")+"Variance: "+calcVariance
	    			+System.getProperty("line.separator")+"Standard Deviation: "+calcStdDeviation
	    			+System.getProperty("line.separator")+System.getProperty("line.separator");
	    	
	    	
	    	
	    			
    	}
    	
    	//Set results to display text area
    	numResultsTxtArea.setText(disp);
    	
    	
    	
    }
    
    
    public void numExportResults(ActionEvent e) {
    	TextInputDialog td = new TextInputDialog("Filename");
    	td.setContentText("Enter Filename to Save as");
    	td.showAndWait();
    	
    	//System.out.println("Result:"+ td.getResult());
    	//System.out.println(td.getEditor().getText());
    	
    	
    	
    	try{
    		
    		if(td.getResult().equals(null)) {
    			
    		}
    		else {
    			String infileSaveName = td.getEditor().getText().toString();
        		//System.out.println("Exporting Results");
            	
            	Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
            	
            	DirectoryChooser directoryChooser = new DirectoryChooser();
            	// Set title for DirectoryChooser
                directoryChooser.setTitle("Select Some Directories");
                directoryChooser.setInitialDirectory(new File(System.getProperty("user.home")));
                
                
                //File selectedFile = fileChooser.showOpenDialog(stage);
            	
            	try {
            		File dir = directoryChooser.showDialog(stage);
                	//System.out.println(dir.getAbsolutePath());
                	//System.out.println("File Path????"+dir.getAbsoluteFile());
                	
                	//String test = resultsTxtArea.getText();
                	String test = resultTxtOutput;
                	//System.out.println(test);
                	
                	//byte[] strToBytes = test.getBytes();
                	
                	int filenameLength = loadedFilename.toString().length();
                	String outFilename = "";
                	if(infileSaveName.length() < 3) {
                		outFilename = loadedFilename.toString().substring(0, (filenameLength-4));
                	}
                	else {
                		outFilename = infileSaveName;
                	}
                	
                	//System.out.println("OutFilename: "+outFilename);
                	//System.out.println("Loaded Filename: "+loadedFilename);
                	//Path file = Paths.get(dir.getAbsolutePath(),loadedFilename+"Stats_Analysis.txt");
                	Path file = Paths.get(dir.getAbsolutePath(),outFilename+".txt");
                	//System.out.println("Path: "+dir.getAbsolutePath()+outFilename+"-Stats_Analysis.txt");
            		
            		
            		String newline = String.valueOf('\n');
        			test.replaceAll(newline, System.getProperty("line.separator"));
        			Files.write(file, test.getBytes(StandardCharsets.UTF_8));
        			popupInfo("File saved");
        			//System.out.println("Tried to write");
        		} catch (IOException e1) {
        			// TODO Auto-generated catch block
        			//e1.printStackTrace();
        			//System.out.println("Failed write");
        		} catch (NullPointerException npe) {
        			//npe.printStackTrace();
        			//Didn't select a folder or cancelled popup
        		} catch (InvalidPathException ipe) {
        			popupWarning("Alpha Numeric Names Only");
        		}
    		}
    		
    		
    		
    	}
    	catch(NullPointerException npe){
    		//System.out.println("Input was null");
    		//Cancelled entering a filename with the text dialog box
    	}
    	
    	
		
        
    }
    
    //For use in chart panel
    private String chartSelectedKNum = null;
    private String chartSelectedXCol = null, chartSelectYCol = null;
    private ArrayList<ArrayList<Double>> numClusterXCol = null;
    private ArrayList<ArrayList<Double>> numClusterYCol = null;
    private ArrayList<String> numClusterNames = new ArrayList<String>();
    
    public void numRunKmeansAnalysis(ActionEvent e) {
    	//chartTab.setDisable(false);
    	loadNumClusteringBtn.setDisable(false);
    	
    	//Get k number value from input text
    	int inKNumber = 0;
    	try {
    		 inKNumber = Integer.valueOf(numKnumTxtField.getText());
    		
    		 
    		//Check k number is < than total no. of elements but > than 1
	    	if(inKNumber < 2  ||  inKNumber > fileData.getColumnData().get(0).size()) {
	    		popupWarning("Check K number input: \nMust be greater than 1 \nMust be less than the number of elements");
	    	}
	    	else 
	    	{
	    		//inKNumber = 3;
	    		
	    		int xArrPos = numColXdropdown.getSelectionModel().getSelectedIndex();
	        	int yArrPos = numColYdropdown.getSelectionModel().getSelectedIndex();
	        	
	        	
	        	ArrayList<Double> inColX = fileData.getColumnData().get(xArrPos);
	        	ArrayList<Double> inColY = fileData.getColumnData().get(yArrPos);
	        	
	        	String popUpConfirm = "\nK Number: "+inKNumber
	        			+"\nX: "+numColXdropdown.getSelectionModel().getSelectedItem()
	        			+"\nY: "+numColYdropdown.getSelectionModel().getSelectedItem();
	        	
	        	
	        	

	        	//Alert Popup
	        	Alert alert = new Alert(AlertType.CONFIRMATION);
	        	alert.setTitle("Run Kmeans");
	        	alert.setContentText("Run KMeans Analysis with the following: "+popUpConfirm);


	        	ButtonType buttonTypeYes = new ButtonType("Yes");
	        	ButtonType buttonTypeNo = new ButtonType("No");
	        	alert.getButtonTypes().setAll(buttonTypeYes,buttonTypeNo);
	        	
	        	Optional<ButtonType> result = alert.showAndWait();
	        	if (result.get() == buttonTypeYes){
	        		
	        		//System.out.println("Pressed Yes");
	        		/*
	        		System.out.println("X Data"+inColX.toString());
	        		System.out.println("Y Data"+inColY.toString());
	        		*/
	        		
	        		//int kNum, ArrayList<Double> inColX, ArrayList<Double> inColY
	        		kMeans = new kMeans(inKNumber, inColX, inColY);
	        		
	        		//System.out.println("No. of Iterations: "+kMeans.getIterationNum());
	        		
	        		String finalCentroidsOutput = "";
	        		for(int centroidCount = 0; centroidCount < kMeans.getFinalCentroidsX().size(); centroidCount++) {
	        			finalCentroidsOutput += "("+kMeans.getFinalCentroidsX().get(centroidCount)+", "+kMeans.getFinalCentroidsY().get(centroidCount)+")   ";
	        		}
	        		
	        		//System.out.println("Final Centroids: "+finalCentroidsOutput);
	        		
	        		//String variables for display
	        		String finalClustersOutput = "", finalClusterPoints = "";
	        		
	        		//String variable for text export (uses system line separator for lines are preserved when saving
	        		String outFinalClustersOutput = "";
	        		
	        		
	        		//Adding to outisde varaibles for access in chart
	        		chartSelectedKNum = String.valueOf(inKNumber);
	        		chartSelectedXCol = numColXdropdown.getSelectionModel().getSelectedItem();
	        		chartSelectYCol = numColYdropdown.getSelectionModel().getSelectedItem();
	        		numClusterXCol = new ArrayList<ArrayList<Double>>(kMeans.getFoundClustersX());
	        		numClusterYCol = new ArrayList<ArrayList<Double>>(kMeans.getFoundClustersY());
	        		
	        		
	        		
	        		
	        		//Creating readable coodinates as clusters by combining (X,Y) together
	        		for(int i = 0; i < kMeans.getFoundClustersX().size(); i++) {
	        			//finalClustersOutput += "\nCluster "+(i+1)
	        					//+"\n("+kMeans.getFoundClustersX().get(i)+", "+kMeans.getFoundClustersY().get(i)+") ";
	        			
	        			for(int j = 0; j < kMeans.getFoundClustersX().get(i).size(); j++) {
	        				finalClusterPoints += "("+kMeans.getFoundClustersX().get(i).get(j)+","+kMeans.getFoundClustersY().get(i).get(j)+")   ";
	        			}
	        			
	        			String clusLabel = "Cluster "+(i+1);
	        			numClusterNames.add(clusLabel);
	        			finalClustersOutput += "Cluster "+(i+1)
	        					+"\n"+finalClusterPoints+"\n\n";
	        			
	        			outFinalClustersOutput+= "Cluster "+(i+1)
	        					+System.getProperty("line.separator")+finalClusterPoints+System.getProperty("line.separator");
	        					
	        			finalClusterPoints = "";
	        		}

	        		//System.out.println("Final Clusters: \n"+finalClustersOutput);
	        		String resultsDisp =  "No. of Iterations: "+kMeans.getIterationNum() +"\nFinal Centroids: "+finalCentroidsOutput
	        				+"\n\nFinal Clusters: \n"+finalClustersOutput+"\n";
	        		numResultsTxtArea.setText(resultsDisp);
	        		
	        		resultTxtOutput = "No. of Iterations: "+kMeans.getIterationNum() 
	        							+System.getProperty("line.separator")+"Final Centroids: "+finalCentroidsOutput
	        							+System.getProperty("line.separator")+System.getProperty("line.separator")+"Final Clusters: "
	        							+System.getProperty("line.separator")+outFinalClustersOutput+System.getProperty("line.separator");
	        		
	        	}
	        	else {
	        		//do nothing
	        	}
    	    		
    	    		
	    	}
    	}catch(NullPointerException npe) {
    		//npe.printStackTrace();
    		popupWarning("Check K number input is not blank");
    		
    	}
    	catch(NumberFormatException nfe) {
    		//nfe.printStackTrace();
    		popupWarning("Check K number input: \nMust be a whole number");
    	}
    	
    	
    	
    	
    }
    



	
	/*
	 * 
	 * Text Panel Methods
	 */
    
    private String textResOutput = "";
    private String textLoadedFilename = "";
    private ArrayList<String> readTrimmedLowCase = new ArrayList<String>();
    private ArrayList<String> readTrimmedOGCase = new ArrayList<String>();
    
    
    public void textOpenFile(ActionEvent e) throws IOException {
		
	      Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
	     /* // Display our window, using the scene graph.
	      stage.setTitle("Administrator"); 
	      stage.setScene(scene);
	      stage.show(); 
	*/
	      
	     // readTrimmedLowCase = new ArrayList<String>();
	      
	      readTrimmedLowCase.clear();
	      readTrimmedOGCase.clear();
	try {
	    	FileChooser fileChooser = new FileChooser();
	    	fileChooser.setTitle("Open Data File");
	    	fileChooser.getExtensionFilters().addAll(
	    	         new ExtensionFilter("Text or Csv Files", "*.txt"));
	    	
	    	fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
	    	
	    	//fileChooser.showOpenDialog(stage);
	    	File selectedFile = fileChooser.showOpenDialog(stage);
	    	
	    	
	    	//System.out.println("FileName: "+selectedFile.getName()+"(From controller)");
	    	textFileNameLabel.setVisible(true);
	    	//Set filename to label and reposition it a little to the left to account for the filename
	    	textFileNameLabel.setText("File Name: "+selectedFile.getName());
	    	textLoadedFilename = selectedFile.getName();
	    	/* Getting the extension */
	    	int dotPosition = selectedFile.getName().indexOf('.');
	    	int fileSize = selectedFile.getName().length();
	    	String extension = selectedFile.getName().substring(dotPosition, fileSize);
	    	//System.out.println("Filename: "+selectedFile.getName());
	    	//System.out.println("Dot Position :"+dotPosition+ "\nFile Length: "+fileSize+"\nExtension: "+extension);
	    	//importTextField.setText(selectedFile.get);
	    	
	    	//System.out.println(selectedFile.getAbsoluteFile());
	    	TextFileProcessing txtFileProcessing = new TextFileProcessing(selectedFile.getAbsoluteFile().toString());
	    	
	    	//Copied the file data for use in other methods without messing with the data in this method
	    	//fileData = fileProcessing;
	    	//This "If" checks if there is an error reading the file, popup with the reason
	    	if(txtFileProcessing.getErrorCheck() == true) {
	    		//System.out.println("Error: "+fileProcessing.getErrorCheck());
	    		//System.out.println(fileProcessing.getErrorReason());
	    		
	    		popupWarning("Error Opening Text File");
	    	}
	    	//If no error load text
	    	else {
	    		
	    		
	    		//Display success label
	    		textFileLoadResult.setText("File Loaded Successfully");
	    		textFileLoadResult.setVisible(true);
	    		txtImportTextField.clear();
	    		//importTextField.setText("");
		    	
	    		
	    		String readLinefromFile = "";
	    		
	    		for(int t = 0 ; t < txtFileProcessing.getRecords().size(); t++) {
	    			//readLinefromFile += txtFileProcessing.getRecords().get(t).get(0)+"\n";
	    			//System.out.println(txtFileProcessing.getRecords().get(t).toString()+"\n");
	    			try {
	    				for(int addLCount = 0; addLCount < txtFileProcessing.getRecords().get(t).size(); addLCount++) {
	    					readLinefromFile += txtFileProcessing.getRecords().get(t).get(addLCount);
	    				}
	    				readLinefromFile += "\n";
	    			}catch(Exception eLineAdd) {
	    				readLinefromFile += "\n";
	    			}
  			
	    		}
	    		txtImportTextField.setText(readLinefromFile);
	    		
	    		
	    		/*
	    			Remove punctuation and change to lower case
	    		*/
	    		String[] inTextRead = readLinefromFile.split(" ");
	    		
	    		for(int i = 0; i < inTextRead.length; i++) {
	    			inTextRead[i] = inTextRead[i].trim();
	    			
	    			readTrimmedOGCase.add(inTextRead[i]);
	    			
	    			//remove punctuation line
	    			for(int x = 0; x < inTextRead[i].length(); x++) {
	    				inTextRead[i] = inTextRead[i].replaceAll("\\p{Punct}", " ");
	    				
	    			}
	    			
	    			
	    			//Removing line breaks
	    			inTextRead[i] = inTextRead[i].replace("\n", ",").replace("\r", ",");
	    			inTextRead[i] = inTextRead[i].replaceAll("\\s+","");
	    			inTextRead[i] = inTextRead[i].trim();

					
	    			//removing leftover commas created from removing line breaks
	    			boolean needsFinalSplit = false;
	    			String[] finalSplit = null;
	    			if(inTextRead[i].contains(",")) {
	    				finalSplit = inTextRead[i].split(",");
	    				needsFinalSplit = true;
	    			}
	    			//skipping if "word" is a space
	    			if(inTextRead[i].equals(" ")) {
	    				
	    			}
	    			else {
	    				//if split then add both results to the list
	    				if(needsFinalSplit == true) {
		    				for(int z = 0; z < finalSplit.length; z++) {
		    					finalSplit[z] = finalSplit[z].replaceAll("\\s+","");
		    					finalSplit[z] = finalSplit[z].trim();
		    					readTrimmedLowCase.add(finalSplit[z].toLowerCase());
		    					//readTrimmedOGCase.add(finalSplit[z]);
		    				}
		    			}
		    			else {
		    				readTrimmedLowCase.add(inTextRead[i].toLowerCase());
		    				//readTrimmedOGCase.add(inTextRead[i]);
		    			}
	    			}
	    			
	    			
	    			
	    		}
	    		
	    		// text = text.replace("\n", "").replace("\r", "");
	    		
	    		// readTrimmedLowCase = readTrimmedLowCase
	    		
	    		//Enable buttons that run data analysis (disabled before file is loaded)
	    		txtRunAnalysisBtn.setDisable(false);
	    		txtRunSummaryBtn.setDisable(false);
	    		txtRunClusteringBtn.setDisable(false);
	    		txtAnalysisTextField.clear();

	    	}
	    	
	    	
	    	
	    	
	}catch(RuntimeException re) {
		//do nothing
		//System.out.println("Error in Displaying Text");
		//re.printStackTrace();
	}
	
}
    
    // For Use in Chart
    private ArrayList<String> chrtUniqKeywords;
    private ArrayList<Integer> chrtKeywordFreq;
    
    public void textRunAnalysis(ActionEvent e) {
    	//chartTab.setDisable(false);
    	
    	//Clear previous everything
    	txtAnalysisTextField.clear();
    	classes.textStats textStats = new classes.textStats();
    	
    	
    	//System.out.println("Readtrimmed Output: \n"+readTrimmedLowCase.toString());
    	textStats.getSpeechParts(readTrimmedLowCase);
    	textStats.getLongestWords(readTrimmedLowCase);
    	textStats.findKeywordsB(readTrimmedLowCase);
    	textStats.findSubSpecificTerms(readTrimmedLowCase);
    	
    	int totalWordCount = textStats.totalWordCount(readTrimmedLowCase);
    	int significantWordCount = textStats.totalSignificantWordCount(readTrimmedLowCase);
    	
    	String keywords = "", adjectives = "", verbs = "", adverbs = "", subjectSpecificTerms = "";
    	
    	ArrayList<String> mostFrequentWords = new ArrayList<String>(textStats.mostFrequentWords(readTrimmedLowCase));
    	ArrayList<String> leastFrequentWords  = new ArrayList<String>(textStats.leastFrequentWords(readTrimmedLowCase));
    	ArrayList<String> longestWords = new ArrayList<String>(textStats.getLongestWords(readTrimmedLowCase));
    	ArrayList<String> keyWordsArrList = new ArrayList<String>(textStats.getUniqueKeywords(readTrimmedLowCase));
    	ArrayList<Integer> keywordFreq = textStats.keywordFreqCount(keyWordsArrList, readTrimmedLowCase);
    	
    	//For use in chart
    	chrtUniqKeywords = new ArrayList<String>(keyWordsArrList);
    	chrtKeywordFreq = new ArrayList<Integer>(keywordFreq);
    	int lineBreakKeywords = 0;
    	for(int i = 0; i < keyWordsArrList.size(); i++) {
    		String breakPart = "";
    		
    		if(lineBreakKeywords == 10) {
    			breakPart = "\n";
    			lineBreakKeywords = 0;
    		}
    		
    		keywords += keyWordsArrList.get(i)+ " ,"+breakPart; 
    	}
    	
    	
    	int lineBreakAdj = 0;
    	for(int i = 0; i < textStats.getFoundAdjectives().size(); i++) {
    		String breakPart = "";
    		
    		if(lineBreakAdj == 10) {
    			breakPart = "\n";
    			lineBreakAdj = 0;
    		}
    		
    		adjectives += textStats.getFoundAdjectives().get(i)+ " ,"+breakPart; 
    	}
    	
    	
    	int lineBreakVbs = 0;
    	for(int i = 0; i < textStats.getFoundVerbs().size(); i++) {
    		String breakPart = "";
    		
    		if(lineBreakVbs == 10) {
    			breakPart = "\n";
    			lineBreakVbs = 0;
    		}
    		
    		verbs += textStats.getFoundVerbs().get(i)+ " ,"+breakPart; 
    	}
    	
    	
    	int lineBreakAdVbs = 0;
    	for(int i = 0; i < textStats.getFoundAdverbs().size(); i++) {
    		String breakPart = "";
    		
    		if(lineBreakAdVbs == 10) {
    			breakPart = "\n";
    			lineBreakAdVbs = 0;
    		}
    		
    		adverbs += textStats.getFoundAdverbs().get(i)+ " ,"+breakPart; 
    	}
    	
    	
    	int lineBreakSubSpTerms = 0;
    	for(int i = 0; i < textStats.getSubjectSpecificTerms().size(); i++) {
    		String breakPart = "";
    		
    		if(lineBreakSubSpTerms == 10) {
    			breakPart = "\n";
    			lineBreakSubSpTerms = 0;
    		}
    		
    		subjectSpecificTerms += textStats.getSubjectSpecificTerms().get(i)+ " ,"+breakPart; 
    	}
    	
    	
    	
    	String textAnalysisDisplay = "Keywords: \n"+keywords+"\n"+
    			"\nSubject Specific Terms: "+subjectSpecificTerms+
    			"\n\nAdjectives: "+adjectives+"\n"+
    			"Verbs: "+verbs+"\n"+
    			"Adverbs: "+adverbs+"\n"+
    			"\n"+
    			"Most Frequent Words: "+mostFrequentWords.toString()+"\n"+
    			"Least Frequent Words: "+leastFrequentWords.toString()+"\n"+
    			"Longest Words: "+longestWords.toString()+"\n\n"+
    			"Total Word Count: "+ totalWordCount+ "\t Significant Word Count: "+significantWordCount;
    	
    	
    	//System.getProperty("line.separator")
    	textResOutput = "Keywords: "+System.getProperty("line.separator")+keywords+System.getProperty("line.separator")+
    			System.getProperty("line.separator")+"Subject Specific Terms: "+subjectSpecificTerms+
    			System.getProperty("line.separator")+System.getProperty("line.separator")+"Adjectives: "+adjectives+System.getProperty("line.separator")+
    			"Verbs: "+verbs+System.getProperty("line.separator")+
    			"Adverbs: "+adverbs+System.getProperty("line.separator")+
    			System.getProperty("line.separator")+
    			"Most Frequent Words: "+mostFrequentWords.toString()+System.getProperty("line.separator")+
    			"Least Frequent Words: "+leastFrequentWords.toString()+System.getProperty("line.separator")+
    			"Longest Words: "+longestWords.toString()+System.getProperty("line.separator")+System.getProperty("line.separator")+
    			"Total Word Count: "+ totalWordCount+ "\t Significant Word Count: "+significantWordCount;

    	
    	txtAnalysisTextField.setText(textAnalysisDisplay);
    	loadTextAnalysisBtn.setDisable(false);
    }
    
    
    
    
    public void textRunSummarise(ActionEvent e) {
    	txtAnalysisTextField.clear();
    	
    	
    	//Moved Summary from textStats to TextSummarising
    	//classes.textStats textStats = new classes.textStats();
    	//String setSummary = textStats.textSummary(readTrimmedOGCase);
    	//txtAnalysisTextField.setText(setSummary);
    	
    	
    	classes.TextSummarising TextSummarising = new classes.TextSummarising(readTrimmedOGCase);
    	String setSummary = TextSummarising.getSummary();
    	
    	//textResOutput = TextSummarising.getSummary().replaceAll("\n", "line.separator");
    	textResOutput = TextSummarising.getSummary().replaceAll("\\r?\\n", System.getProperty("line.separator"));
    	txtAnalysisTextField.setText(setSummary);
    }
    
    //For use in chart
    ArrayList<String> wordSynonymsClusters = new ArrayList<String>();
    ArrayList<ArrayList<String>> wordOGClusters = new ArrayList<ArrayList<String>>();
    
    public void textRunClustering(ActionEvent e) {
    	txtAnalysisTextField.clear();
    	wordSynonymsClusters.clear();
    	wordOGClusters.clear();
    	
    	loadTextClusteringBtn.setDisable(false);

    	classes.textStats textStats = new classes.textStats();
    	textStats.getSpeechParts(readTrimmedLowCase);
    	textStats.findKeywordsB(readTrimmedLowCase);
    	textStats.findSubSpecificTerms(readTrimmedLowCase);
    	//ArrayList<String> keyWordsArrList = new ArrayList<String>(textStats.getUniqueKeywords(readTrimmedLowCase));
    	
    	
    	ArrayList<String> readNouns = new ArrayList<String>(textStats.getFoundNouns());
    	
    	
    	classes.TextClustering TextClustering = new classes.TextClustering();
    	
    	ArrayList<String> foundCommonWords = TextClustering.findCommonWords(readNouns);
    	ArrayList<ArrayList<String>> foundWordClusters = TextClustering.findClusters(foundCommonWords, readNouns);
    	foundCommonWords = TextClustering.remRepeatSynonymClus(foundCommonWords, foundWordClusters);
		foundWordClusters = TextClustering.removeDuplicateClusters(foundWordClusters);
		
		wordSynonymsClusters = new ArrayList<String>(foundCommonWords);
		wordOGClusters = new ArrayList<ArrayList<String>>(foundWordClusters);
		
		//String commonSynonyms = foundCommonWords.toString(), 
			//	foundClusters = foundWordClusters.toString();
		
		//String clusteringDisp = "Common Synonyms: "+commonSynonyms+ "\n\n"+
			//				"Text Clusters: \n"+foundClusters;
		String clusteringDisp = "";
		for(int clusCount = 0; clusCount < foundCommonWords.size(); clusCount++) {
			clusteringDisp += foundCommonWords.get(clusCount)+"\n"+foundWordClusters.get(clusCount).toString()+"\n\n";
		}
		//textResOutput = clusteringDisp.replaceAll("\n", "line.separator");
		textResOutput = clusteringDisp.replaceAll("\\r?\\n", System.getProperty("line.separator"));
		txtAnalysisTextField.setText(clusteringDisp);
    }
    
    
    public void textExportResults(ActionEvent e) {
    	TextInputDialog td = new TextInputDialog("Filename");
    	td.setContentText("Enter Filename to Save as");
    	td.showAndWait();
    	
    	//System.out.println("Result:"+ td.getResult());
    	//System.out.println(td.getEditor().getText());
    	
    	
    	
    	try{
    		
    		if(td.getResult().equals(null)) {
    			
    		}
    		else {
    			String infileSaveName = td.getEditor().getText().toString();
        		//System.out.println("Exporting Results");
            	
            	Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
            	
            	DirectoryChooser directoryChooser = new DirectoryChooser();
            	// Set title for DirectoryChooser
                directoryChooser.setTitle("Select Some Directories");
                directoryChooser.setInitialDirectory(new File(System.getProperty("user.home")));
                
                
                //File selectedFile = fileChooser.showOpenDialog(stage);
            	
            	try {
            		File dir = directoryChooser.showDialog(stage);
                	//System.out.println(dir.getAbsolutePath());
                	//System.out.println("File Path????"+dir.getAbsoluteFile());
                	
                	//String test = resultsTxtArea.getText();
                	String test = textResOutput;
                	//System.out.println(test);
                	
                	//byte[] strToBytes = test.getBytes();
                	
                	int filenameLength = textLoadedFilename.toString().length();
                	String outFilename = "";
                	if(infileSaveName.length() < 3) {
                		outFilename = textLoadedFilename.toString().substring(0, (filenameLength-4));
                	}
                	else {
                		outFilename = infileSaveName;
                	}
                	
                	//System.out.println("OutFilename: "+outFilename);
                	//System.out.println("Loaded Filename: "+loadedFilename);
                	//Path file = Paths.get(dir.getAbsolutePath(),loadedFilename+"Stats_Analysis.txt");
                	Path file = Paths.get(dir.getAbsolutePath(),outFilename+".txt");
                	//System.out.println("Path: "+dir.getAbsolutePath()+outFilename+"-Stats_Analysis.txt");
            		
            		
            		String newline = String.valueOf('\n');
        			//test.replaceAll("\\r?\\n", " --linebreak-- ");
        			Files.write(file, test.getBytes(StandardCharsets.UTF_8));
        			popupInfo("File saved");
        			//System.out.println("Tried to write");
        		} catch (IOException e1) {
        			// TODO Auto-generated catch block
        			//e1.printStackTrace();
        			//System.out.println("Failed write");
        		} catch (NullPointerException npe) {
        			//npe.printStackTrace();
        			//Didn't select a folder or cancelled popup
        		} catch (InvalidPathException ipe) {
        			popupWarning("Alpha Numeric Names Only");
        		}
    		}
    		
    		
    		
    	}
    	catch(NullPointerException npe){
    		//System.out.println("Input was null");
    		//Cancelled entering a filename with the text dialog box
    	}
    }
    
    
    /*
     * Chart Panel Methods
     */
    //Export Details
    private String inFilename = null;
    
    
    private String inComingDataType = null;
    public void loadOGNumData() {
    	inComingDataType = "OGData";
    	chartAllDataCheckBox.setSelected(false);
    	chartAllDataCheckBox.setDisable(false);
    	
    	chartPanel.getChildren().clear();
    	chartPanel.setDisable(false);
    	chartScrollPane.setDisable(true);
    	chartScrollPane.setVisible(false);
    	
    	dataLoadedTxtField.clear();
    	dataLoadedTxtField.setText("Original \nUploaded \nData");
    	xAxisChoiceBox.getItems().clear();
    	yAxisChoiceBox.getItems().clear();
    	
    	
		//Dropdowns
		
		ObservableList<String> chartColChoiceList = FXCollections.observableArrayList(numFileColumnNames);
		
		
		
		xAxisChoiceBox.getItems().setAll(chartColChoiceList);
		xAxisChoiceBox.getSelectionModel().select(1);
		
		yAxisChoiceBox.getItems().setAll(chartColChoiceList);
		yAxisChoiceBox.getSelectionModel().selectFirst();
		
		//Ensure required elements are enabled
		xAxisChoiceBox.setDisable(false);
		yAxisChoiceBox.setDisable(false);
		scatterChartRdioBtn.setDisable(false);
		lineChartRdioBtn.setDisable(false);
		areaChartRdioBtn.setDisable(false);
		
		//Disable irrelevent chart options
		//chartAllDataCheckBox.setSelected(false);
		//chartAllDataCheckBox.setDisable(true);
		barChartRdioBtn.setDisable(true);
    }
    
    public void loadNumAnalysisSpread() {
    	inComingDataType = "NumSpread";
    	
    	chartPanel.setDisable(false);
    	chartPanel.getChildren().clear();
    	chartScrollPane.setDisable(true);
    	chartScrollPane.setVisible(false);
    	
    	areaChartRdioBtn.setDisable(false);
    	scatterChartRdioBtn.setDisable(false);
    	lineChartRdioBtn.setDisable(false);
    	scatterChartRdioBtn.setSelected(true);
    	//Disable irrelevant chart and options
    	xAxisChoiceBox.setDisable(true);
		//yAxisChoiceBox.setDisable(true);
		barChartRdioBtn.setDisable(true);
		
		chartAllDataCheckBox.setDisable(false);
		
		scatterChartRdioBtn.setSelected(true);
		
		
		String dataLoaded = "Data Loaded: "+ 
				"Smallest Number\n" + 
				"Largest Number\n" + 
				"Mean\n" + 
				"Median\n" + 
				"Mode\n" + 
				"Range\n" + 
				"1st Quartile\n" + 
				"2nd Quartile\n" + 
				"Variance\n" + 
				"Standard Deviation";
		dataLoadedTxtField.setText(dataLoaded);
		
		
		ObservableList<String> chartColChoiceList = FXCollections.observableArrayList(numFileColumnNames);
		yAxisChoiceBox.getItems().setAll(chartColChoiceList);
		yAxisChoiceBox.getSelectionModel().selectFirst();
		
		
		
    }
    
    public void loadNumClusteringData() {
    	inComingDataType = "NumClusters";
    	
    	chartPanel.setDisable(false);
    	chartPanel.getChildren().clear();
    	chartScrollPane.setDisable(true);
    	chartScrollPane.setVisible(false);
    	
    	chartAllDataCheckBox.setSelected(true);
		chartAllDataCheckBox.setDisable(true);	
		scatterChartRdioBtn.setSelected(true);
		
		//disable unused elements
		lineChartRdioBtn.setDisable(true);
		areaChartRdioBtn.setDisable(true);
		barChartRdioBtn.setDisable(true);
		yAxisChoiceBox.setDisable(true);
		xAxisChoiceBox.setDisable(true);
    	
		//chartSelectedKNum = String.valueOf(inKNumber);
		//chartSelectedXCol = numColXdropdown.getSelectionModel().getSelectedItem();
		//chartSelectYCol = numColYdropdown.getSelectionModel().getSelectedItem();
		//numClusterXCol = new ArrayList<ArrayList<Double>>(kMeans.getFoundClustersX());
		//numClusterYCol = new ArrayList<ArrayList<Double>>(kMeans.getFoundClustersY());
    	
		String dataLoaded = "Number Clustering: "+
							"\nK Number: "+chartSelectedKNum+
							"\nColumns- \nX: "+chartSelectedXCol+
							"\nY: "+chartSelectYCol;
		dataLoadedTxtField.setText(dataLoaded);
    	
    }
    
    
    public void loadTextKeyFreq() {
    	inComingDataType = "textKeyFreq";
    	
    	chartPanel.setDisable(false);
    	chartPanel.getChildren().clear();
    	chartScrollPane.setDisable(true);
    	chartScrollPane.setVisible(false);
    	
    	//Disable elements
    	chartAllDataCheckBox.setSelected(true);
		chartAllDataCheckBox.setDisable(true);	
		scatterChartRdioBtn.setDisable(true);
		lineChartRdioBtn.setDisable(true);
		areaChartRdioBtn.setDisable(true);
		xAxisChoiceBox.setDisable(true);
		yAxisChoiceBox.setDisable(true);
		
		
		barChartRdioBtn.setDisable(false);
		barChartRdioBtn.setSelected(true);
    }
    
    
    public void loadTextClustering() {
    	inComingDataType = "textSynClustering";
    	
    	chartPanel.getChildren().clear();
    	chartPanel.setDisable(true);
    	chartScrollPane.setVisible(true);
    	chartScrollPane.setDisable(false);
    	
    	//Disable elements
    	chartAllDataCheckBox.setSelected(true);
		chartAllDataCheckBox.setDisable(true);	
		scatterChartRdioBtn.setDisable(true);
		lineChartRdioBtn.setDisable(true);
		areaChartRdioBtn.setDisable(true);
		xAxisChoiceBox.setDisable(true);
		yAxisChoiceBox.setDisable(true);
		barChartRdioBtn.setDisable(true);
    	
		dataLoadedTxtField.clear();
		dataLoadedTxtField.setText("Text Clustering \nData");
    	
    }
    
    
    
    public void disableColumnChoice() {
    	
    	if(chartAllDataCheckBox.isSelected() == true) {
    		xAxisChoiceBox.setDisable(true);
    		yAxisChoiceBox.setDisable(true);
    		barChartRdioBtn.setDisable(true);
    		
    		
    		lineChartRdioBtn.setDisable(false);
    		scatterChartRdioBtn.setDisable(false);
    		areaChartRdioBtn.setDisable(false);
    		
    		scatterChartRdioBtn.setSelected(true);
    	}
    	else {
    		xAxisChoiceBox.setDisable(false);
    		yAxisChoiceBox.setDisable(false);
    		
    		barChartRdioBtn.setDisable(true);
    		areaChartRdioBtn.setDisable(true);
    		lineChartRdioBtn.setDisable(true);
    		scatterChartRdioBtn.setDisable(true);
    	}
    	
    }
   
	public void LoadChart() {
		
		//if radio button is not "pie chart", get x and y values from choicebox
		//i.e. use choicebox to get the relevant data
		
		chartPanel.getChildren().clear();
		
		statsCalc chartStatsCalc = new statsCalc();
		int xArrPos;
		int yArrPos;
		ArrayList<Double> inColX = null;
		ArrayList<Double> inColY = null;
		double xLowerBound = 0, yLowerBound = 0, xUpperBound = 0, yUpperBound = 0, xTickSteps = 0, yTickSteps = 0;
		/*
			numAllColData.clear();
        	numSpreadDataLabels.clear();
        	numSpreadDataValues.clear();
		*/
		try {
			if(chartAllDataCheckBox.isSelected()) {
				//charts should take an arraylist of arraylist doubles that would chart each as a separate "series"
				if(inComingDataType.equals("OGData")) {
					//fileData.getColumnData()
					if(scatterChartRdioBtn.isSelected()) {
						scatterChartA(fileData.getColumnData(), numFileColumnNames,"X - Axis","Y - Axis");
					}
					else if(lineChartRdioBtn.isSelected()) {
						lineChartA(fileData.getColumnData(), numFileColumnNames,"X - Axis","Y - Axis");
					}
					else if(areaChartRdioBtn.isSelected()) {
						areaChartA(fileData.getColumnData(), numFileColumnNames,"X - Axis","Y - Axis");
					}
				}
				
				
				if(inComingDataType.equals("NumSpread")) {
					//System.out.println("NUM SPREAD DATA CHART");
					if(scatterChartRdioBtn.isSelected()) {
						scatterChartA(numAllColData, numSpreadDataLabels,chartSelectedXCol,chartSelectYCol);
					}
					else if(lineChartRdioBtn.isSelected()) {
						lineChartA(numAllColData, numSpreadDataLabels,chartSelectedXCol,chartSelectYCol);
					}
					else if(areaChartRdioBtn.isSelected()) {
						areaChartA(numAllColData, numSpreadDataLabels,chartSelectedXCol,chartSelectYCol);
					}
				}
				
				if(inComingDataType.equals("NumClusters")) {
					//fileData.getColumnData()
					
					if(scatterChartRdioBtn.isSelected()) {
						scatterChartCluster(numClusterXCol, numClusterYCol, numClusterNames,chartSelectedXCol,chartSelectYCol);
					}
					
				}
				
				
				if(inComingDataType.equals("textKeyFreq")) {
					if(barChartRdioBtn.isSelected()) {
						//Bar chart
						barChartA(chrtUniqKeywords, chrtKeywordFreq);
						 
					}
				}
				
				
				if(inComingDataType.equals("textSynClustering")) {
					/*chartScrollPane
			    	 *  wordSynonymsClusters = new ArrayList<String>();
			    ArrayList<ArrayList<String>> wordOGClusters = new ArrayList<ArrayList<String>>();
			    	 */
					//clusterCanvas
		        	//Canvas canvas = new Canvas(chartPanel.getWidth(), chartPanel.getHeight());
					
					GraphicsContext gc = clusterCanvas.getGraphicsContext2D();
					gc.clearRect(0, 0, clusterCanvas.getWidth(), clusterCanvas.getHeight());
					
					drawTextClusters(wordSynonymsClusters, wordOGClusters, gc);
					//chartPanel.getChildren().add(clusterCanvas);
					chartScrollPane.setContent(clusterCanvas);
				}
			}
			//NOT CHARTING ALL
			else {
				
				if(inComingDataType.equals("OGData")) {
					//System.out.println("OG DATA CHART");
		    		xArrPos = xAxisChoiceBox.getSelectionModel().getSelectedIndex();
		        	yArrPos = yAxisChoiceBox.getSelectionModel().getSelectedIndex();
		        	
		        	String seriesLabel = xAxisChoiceBox.getSelectionModel().getSelectedItem()+"/"+yAxisChoiceBox.getSelectionModel().getSelectedItem();
		        	inColX = fileData.getColumnData().get(xArrPos);
		        	inColY = fileData.getColumnData().get(yArrPos);
		        	
		        	
		        	xUpperBound = Collections.max(inColX).intValue() + (Collections.max(inColX).intValue() * 0.25);
		        	yUpperBound = Collections.max(inColY).intValue() + (Collections.max(inColY).intValue() * 0.25);
		        	
		        	xLowerBound = Collections.min(inColX).intValue() - (Collections.min(inColX).intValue() * 0.15);
		        	yLowerBound = Collections.min(inColY).intValue() - (Collections.min(inColY).intValue() * 0.15);
		        	
		        	//xTickSteps = chartStatsCalc.getMean(inColX) * 0.10;
		        	xTickSteps = (xUpperBound - xLowerBound)/inColX.size();
		        	
		        	//yTickSteps = chartStatsCalc.getMean(inColY) * 0.10;
		        	yTickSteps = (yUpperBound - yLowerBound)/inColY.size();
		        	
		        	
		        	String colXLabel = xAxisChoiceBox.getSelectionModel().getSelectedItem();
		        	String colYLabel = yAxisChoiceBox.getSelectionModel().getSelectedItem();
		        	//xLowerBound, double yLowerBound, double xUpperBound, double yUpperBound
					if(lineChartRdioBtn.isSelected()) {
						//lineChart();
						lineChart(inColX, inColY, xLowerBound, yLowerBound, xUpperBound, yUpperBound, xTickSteps, yTickSteps, seriesLabel,colXLabel,colYLabel);
					}
					else if(scatterChartRdioBtn.isSelected()) {
						//scatterChart();
						scatterChart(inColX, inColY, xLowerBound, yLowerBound, xUpperBound, yUpperBound, xTickSteps, yTickSteps, seriesLabel,colXLabel,colYLabel);
					}
					else if(areaChartRdioBtn.isSelected()) {
						//areaChart();
						areaChart(inColX, inColY, xLowerBound, yLowerBound, xUpperBound, yUpperBound, xTickSteps, yTickSteps, seriesLabel,colXLabel,colYLabel);
					}
				}
				else if(inComingDataType.equals("NumSpread")) {
					//System.out.println("NUM SPREAD DATA CHART");
					
					yArrPos = yAxisChoiceBox.getSelectionModel().getSelectedIndex();
		        	
		        	String seriesLabel = yAxisChoiceBox.getSelectionModel().getSelectedItem();
		        	
		        	ArrayList<Double> xAxisVals = new ArrayList<Double>();
		        	for(double popX = 0; popX < numAllColData.get(0).size(); popX++) {
		        		xAxisVals.add(popX);
		        	}
		        	inColX = xAxisVals;
		        	inColY = numAllColData.get(yArrPos);
		        	
		        	xUpperBound = Collections.max(inColX).intValue() + (Collections.max(inColX).intValue() * 0.25);
		        	yUpperBound = Collections.max(inColY).intValue() + (Collections.max(inColY).intValue() * 0.25);
		        	
		        	xLowerBound = Collections.min(inColX).intValue() - (Collections.min(inColX).intValue() * 0.15);
		        	yLowerBound = Collections.min(inColY).intValue() - (Collections.min(inColY).intValue() * 0.15);
		        	
		        	//xTickSteps = chartStatsCalc.getMean(inColX) * 0.10;
		        	xTickSteps = (xUpperBound - xLowerBound)/inColX.size();
		        	
		        	//yTickSteps = chartStatsCalc.getMean(inColY) * 0.10;
		        	yTickSteps = (yUpperBound - yLowerBound)/inColY.size();
					
					if(scatterChartRdioBtn.isSelected()) {
						//numAllColData.add(numSpreadDataValues);
				    	//numSpreadDataLabels
						//xArrPos = xAxisChoiceBox.getSelectionModel().getSelectedIndex();
			        	scatterChart(inColX, inColY, xLowerBound, yLowerBound, xUpperBound, yUpperBound, xTickSteps, yTickSteps, seriesLabel,"Number Values",chartSelectYCol);
					}
					else if(lineChartRdioBtn.isSelected()) {
						//scatterChart();
						lineChart(inColX, inColY, xLowerBound, yLowerBound, xUpperBound, yUpperBound, xTickSteps, yTickSteps, seriesLabel,"Number Values",chartSelectYCol);
					}
					else if(areaChartRdioBtn.isSelected()) {
						//areaChart();
						areaChart(inColX, inColY, xLowerBound, yLowerBound, xUpperBound, yUpperBound, xTickSteps, yTickSteps, seriesLabel,"Number Values",chartSelectYCol);
					}
					
				}
			}
		
		}catch(NullPointerException npe) {
			//Catching if nothing is loaded, chart does nothing
			popupWarning("Load Some Data");
		}
		
		
		
		//if piechart, disable y choicebox? and use choicebox to get the right data 
    }
    
     @SuppressWarnings("unchecked")
    public void lineChart(ArrayList<Double> inXvalues, ArrayList<Double> inYvalues, double xLowerBound, 
    		double yLowerBound, double xUpperBound, double yUpperBound, double xTickSteps, double yTickSteps, String seriesLabel, String axisXName, String axisYName) {
    	 //int lowerBound = 0;
    	 NumberAxis xAxis = new NumberAxis(axisXName, xLowerBound, xUpperBound, xTickSteps);
         NumberAxis yAxis = new NumberAxis(axisYName, yLowerBound, yUpperBound, yTickSteps);
         ScatterChart.Series<Double, Double> series = new XYChart.Series<Double, Double>();
         series.setName(seriesLabel);
         ObservableList<XYChart.Series<Double,Double>> lineChartData = FXCollections.observableArrayList();
    	
         

         for (int x = 0; x < inXvalues.size(); x++) {
             series.getData().add(new XYChart.Data<Double, Double>(inXvalues.get(x), inYvalues.get(x)));
             
             
         }
         lineChartData.add(series);
         
         LineChart chart = new LineChart(xAxis, yAxis, lineChartData);
         
         
         chartPanel.getChildren().add(chart);
         chart.setMinSize(chartPanel.widthProperty().doubleValue(), chartPanel.heightProperty().doubleValue());
         chart.setId("chartDisp");
     }
     
     public void lineChartA(ArrayList<ArrayList<Double>> inData, ArrayList<String> colLabelNames, String axisXName, String axisYName) {
     	statsCalc statsCalc = new statsCalc();
     	//ArrayList<Double> inColX = null;
     	ArrayList<Double> inColY = null;
     	Double maxUpperBound = Double.MIN_VALUE, totalYTicks = 5.0;
    	Double minLowerBound = Double.MAX_VALUE;
     	
     	for(int allCount = 0; allCount < inData.size(); allCount++) {
     		double checkMax = Collections.max(inData.get(allCount));
     		if(maxUpperBound < checkMax) {
     			maxUpperBound = checkMax;
     		}
     		
     		
     		/*
     		double tickSize = statsCalc.getMean(inData.get(allCount)) * 0.10;
     		if(totalYTicks < tickSize) {
     			totalYTicks = tickSize;
     		}
     		*/
     		double checkMin = Collections.min(inData.get(allCount));
     		if(minLowerBound > checkMin) {
     			minLowerBound = checkMin;
     		}
     	
     	}
     	
     	Double xUpperBound = 0.0, yUpperBound = 0.0, xLowerBound = 0.0, yLowerBound = 0.0, xTickSteps = 0.0, yTickSteps = 0.0;
     	
 		//xUpperBound = maxUpperBound + (maxUpperBound * 0.25);
     	//xUpperBound = (double) inData.get(0).size();
 		yUpperBound = maxUpperBound + (maxUpperBound * 0.25);
 		
 		xLowerBound = minLowerBound - (minLowerBound * 0.25);
 		yLowerBound = minLowerBound - (minLowerBound * 0.25);
 		
 		//xTickSteps = 1.0;
     	//yTickSteps = totalYTicks;
 		
    	int numTotalElements = 0;
    	int highest = Integer.MIN_VALUE;
    	for(int elemsA = 0; elemsA < inData.size(); elemsA++) {
    		for(int elemsB = 0; elemsB < inData.get(elemsA).size(); elemsB++) {
    			numTotalElements++;
    		}
    		
    		if(highest < numTotalElements) {
    			highest = numTotalElements;
    		}
    		numTotalElements = 0;
    	}
    	
    	xUpperBound = (double) highest;
 		
 		//xTickSteps = chartStatsCalc.getMean(inColX) * 0.10;
    	xTickSteps = (xUpperBound - xLowerBound)/highest;
    	
    	//yTickSteps = chartStatsCalc.getMean(inColY) * 0.10;
    	yTickSteps = (yUpperBound - yLowerBound)/highest;
     	
     	NumberAxis xAxis = new NumberAxis(axisXName, xLowerBound, xUpperBound, xTickSteps);
         NumberAxis yAxis = new NumberAxis(axisYName, yLowerBound, yUpperBound, yTickSteps);
         ObservableList<XYChart.Series<Double,Double>> lineChartDataA = FXCollections.observableArrayList();
         
         
         LineChart.Series<Double, Double> seriesA = new XYChart.Series<Double, Double>();
         seriesA.setName(colLabelNames.get(0));
         for(int xA = 0; xA < inData.get(0).size(); xA++) {
         	seriesA.getData().add(new XYChart.Data<Double, Double>(Double.valueOf(xA), inData.get(0).get(xA)));
         }
         lineChartDataA.add(seriesA);
         LineChart chart = new LineChart(xAxis, yAxis, lineChartDataA);
         
     	for(int colNum = 1; colNum < inData.size(); colNum++) {
     		ObservableList<XYChart.Series<Double,Double>> lineChartData = FXCollections.observableArrayList();
     		//inColX = inData
 			inColY = inData.get(colNum);
 			LineChart.Series<Double, Double> series = new XYChart.Series<Double, Double>();
 	        series.setName(colLabelNames.get(colNum));
 	        
 			for(int seriesCount = 0; seriesCount < inColY.size(); seriesCount++) {
 				
 	            series.getData().add(new XYChart.Data<Double, Double>(Double.valueOf(seriesCount), inColY.get(seriesCount)));
 		            
 		        }
 				lineChartData.add(series);
 		        chart.getData().add(series);
 			}
 			

     	//ScatterChart chart = new ScatterChart(xAxis, yAxis, scatterChartData);
         
     	
         chartPanel.getChildren().add(chart);
         chart.setMinSize(chartPanel.widthProperty().doubleValue(), chartPanel.heightProperty().doubleValue());
         chart.setId("chartDisp");
    	
    }
    
    public void scatterChart(ArrayList<Double> inXvalues, ArrayList<Double> inYvalues, double xLowerBound, 
    		double yLowerBound, double xUpperBound, double yUpperBound, double xTickSteps, double yTickSteps, String seriesLabel, String axisXName, String axisYName) {
    	 //int lowerBound = 0;
    	 NumberAxis xAxis = new NumberAxis(axisXName, xLowerBound, xUpperBound, xTickSteps);
         NumberAxis yAxis = new NumberAxis(axisYName, yLowerBound, yUpperBound, yTickSteps);
         ScatterChart.Series<Double, Double> series = new XYChart.Series<Double, Double>();
         series.setName(seriesLabel);
         ObservableList<XYChart.Series<Double,Double>> scatterChartData = FXCollections.observableArrayList();
    	
         

         for (int x = 1; x < inXvalues.size(); x++) {
             series.getData().add(new XYChart.Data<Double, Double>(inXvalues.get(x), inYvalues.get(x)));
             
             
         }
         scatterChartData.add(series);
         
         ScatterChart chart = new ScatterChart(xAxis, yAxis, scatterChartData);
         
         
         chartPanel.getChildren().add(chart);
         chart.setMinSize(chartPanel.widthProperty().doubleValue(), chartPanel.heightProperty().doubleValue());
         chart.setId("chartDisp");
    }
    
    //Scatter Chart for charting multiple analysis at once
    public void scatterChartA(ArrayList<ArrayList<Double>> inData, ArrayList<String> colLabelNames, String axisXName, String axisYName) {
    	statsCalc statsCalc = new statsCalc();
    	//ArrayList<Double> inColX = null;
    	ArrayList<Double> inColY = null;
    	Double maxUpperBound = Double.MIN_VALUE, totalYTicks = 5.0;
    	Double minLowerBound = Double.MAX_VALUE;
    	
    	for(int allCount = 0; allCount < inData.size(); allCount++) {
    		double checkMax = Collections.max(inData.get(allCount));
    		if(maxUpperBound < checkMax) {
    			maxUpperBound = checkMax;
    		}
    		
    		/*
    		double tickSize = statsCalc.getMean(inData.get(allCount)) * 0.10;
    		if(totalYTicks < tickSize) {
    			totalYTicks = tickSize;
    		}
    		*/
    		double checkMin = Collections.min(inData.get(allCount));
    		if(minLowerBound > checkMin) {
    			minLowerBound = checkMin;
    		}
    	
    	}
    	
    	
    	Double xUpperBound = 0.0, yUpperBound = 0.0, xLowerBound = 0.0, yLowerBound = 0.0, xTickSteps = 0.0, yTickSteps = 0.0;
    	
		//xUpperBound = maxUpperBound + (maxUpperBound * 0.25);
    	//xUpperBound = (double) inData.get(0).size();
    	
    	
		yUpperBound = maxUpperBound + (maxUpperBound * 0.25);
		
		//xLowerBound = minLowerBound - (minLowerBound * 0.25);
    	xLowerBound = 0.0;
		yLowerBound = minLowerBound - (minLowerBound * 0.25);
		
		//xTickSteps = 1.0;
    	//yTickSteps = totalYTicks;
		
    	int numTotalElements = 0;
    	int highest = Integer.MIN_VALUE;
    	for(int elemsA = 0; elemsA < inData.size(); elemsA++) {
    		for(int elemsB = 0; elemsB < inData.get(elemsA).size(); elemsB++) {
    			numTotalElements++;
    		}
    		
    		if(highest < numTotalElements) {
    			highest = numTotalElements;
    		}
    		numTotalElements = 0;
    	}
    	
    	xUpperBound = (double) highest;
    	
 		//xTickSteps = chartStatsCalc.getMean(inColX) * 0.10;
    	//xTickSteps = (xUpperBound - xLowerBound)/numTotalElements;
 		xTickSteps = (xUpperBound - xLowerBound)/highest;
    	
    	//yTickSteps = chartStatsCalc.getMean(inColY) * 0.10;
    	//yTickSteps = (yUpperBound - yLowerBound)/numTotalElements;
 		yTickSteps = (yUpperBound - yLowerBound)/highest;
    	
    	NumberAxis xAxis = new NumberAxis(axisXName, xLowerBound, xUpperBound, xTickSteps);
        NumberAxis yAxis = new NumberAxis(axisYName, yLowerBound, yUpperBound, yTickSteps);
        ObservableList<XYChart.Series<Double,Double>> scatterChartDataA = FXCollections.observableArrayList();
        
        
        ScatterChart.Series<Double, Double> seriesA = new XYChart.Series<Double, Double>();
        seriesA.setName(colLabelNames.get(0));
        for(int xA = 0; xA < inData.get(0).size(); xA++) {
        	seriesA.getData().add(new XYChart.Data<Double, Double>(Double.valueOf(xA), inData.get(0).get(xA)));
        	
        }
        scatterChartDataA.add(seriesA);
        ScatterChart chart = new ScatterChart(xAxis, yAxis, scatterChartDataA);
        
    	for(int colNum = 1; colNum < inData.size(); colNum++) {
    		ObservableList<XYChart.Series<Double,Double>> scatterChartData = FXCollections.observableArrayList();
    		//inColX = inData
			inColY = inData.get(colNum);
			ScatterChart.Series<Double, Double> series = new XYChart.Series<Double, Double>();
	        series.setName(colLabelNames.get(colNum));
	        
			for(int seriesCount = 0; seriesCount < inColY.size(); seriesCount++) {
				
	            series.getData().add(new XYChart.Data<Double, Double>(Double.valueOf(seriesCount), inColY.get(seriesCount)));
		            
		        }
		        scatterChartData.add(series);
		        chart.getData().add(series);
			}
			

    	//ScatterChart chart = new ScatterChart(xAxis, yAxis, scatterChartData);
        
    	
        chartPanel.getChildren().add(chart);
        chart.setMinSize(chartPanel.widthProperty().doubleValue(), chartPanel.heightProperty().doubleValue());
        chart.setId("chartDisp");
   	
   }
    
    
    
    //Scatter for Num clusters
    public void scatterChartCluster(ArrayList<ArrayList<Double>> inDataX, ArrayList<ArrayList<Double>> inDataY, ArrayList<String> colLabelNames, String axisXName, String axisYName) {
    	statsCalc statsCalc = new statsCalc();
    	ArrayList<Double> inColX = null;
    	ArrayList<Double> inColY = null;
    	Double maxXUpperBound = 0.0, maxYUpperBound = 0.0, totalYTicks = 5.0;
    	Double minXLowerBound = 0.0, minYLowerBound = 0.0;
    	//System.out.println(inDataX.toString());
    	/*for(int allCount = 0; allCount < inDataX.size(); allCount++) {
    		
    		
    		double tickSize = statsCalc.getMean(inDataX.get(allCount)) * 0.10;
    		if(totalYTicks < tickSize) {
    			totalYTicks = tickSize;
    		}
    		
    	
    	}*/
    	
    	int numTotalElements = 0;
    	for(int elemsA = 0; elemsA < inDataX.size(); elemsA++) {
    		for(int elemsB = 0; elemsB < inDataX.get(elemsA).size(); elemsB++) {
    			numTotalElements++;
    		}
    	}
    	
    	Double xUpperBound = 0.0, yUpperBound = 0.0, xLowerBound = 0.0, yLowerBound = 0.0, xTickSteps = 0.0, yTickSteps = 0.0;
    	
    	int maxSize = inDataX.get(inDataX.size()-1).size();
    	
    	maxXUpperBound = inDataX.get(inDataX.size()-1).get(maxSize-1);
    	xUpperBound = maxXUpperBound + (maxXUpperBound * 0.25);
		//xUpperBound = maxUpperBound + (maxUpperBound * 0.25);
    	//System.out.println("X Upper: "+xUpperBound);
    	//xUpperBound = 3.0;
    	
    	//xUpperBound = (double) inDataX.get(0).size();
		//yUpperBound = maxUpperBound + (maxUpperBound * 0.25);
		//System.out.println("Y Upper: "+yUpperBound);
    	//yUpperBound = 4.5;
    	maxYUpperBound =  inDataY.get(inDataY.size()-1).get(maxSize-1);
    	yUpperBound = maxYUpperBound + (maxYUpperBound * 0.25);
    	
    	
    	//double minSize = inDataX.get(0).get(0);
		//xLowerBound = minLowerBound - (minLowerBound * 0.25);
    	//xLowerBound = 0.0;
    	minXLowerBound = inDataX.get(0).get(0);
    	xLowerBound = minXLowerBound - (minXLowerBound * 0.25);
    	
		//yLowerBound = minLowerBound - (minLowerBound * 0.25);
    	//yLowerBound = 0.0;
    	minYLowerBound = inDataY.get(0).get(0);
    	yLowerBound = minYLowerBound - (minYLowerBound * 0.25);
    			
    			
		//xTickSteps = 1.0;
    	xTickSteps = ((maxXUpperBound -  minXLowerBound)/numTotalElements);
    	
    	
    	//yTickSteps = totalYTicks;
		//yTickSteps = 0.5;
    	yTickSteps = ((maxYUpperBound -  minYLowerBound)/numTotalElements);
    	
    	NumberAxis xAxis = new NumberAxis(axisXName, xLowerBound, xUpperBound, xTickSteps);
        NumberAxis yAxis = new NumberAxis(axisYName, yLowerBound, yUpperBound, yTickSteps);
        ObservableList<XYChart.Series<Double,Double>> scatterChartDataA = FXCollections.observableArrayList();
        
        
        ScatterChart.Series<Double, Double> seriesA = new XYChart.Series<Double, Double>();
        int kNumSize = Integer.valueOf(chartSelectedKNum);
        seriesA.setName(colLabelNames.get(0));
        for(int xA = 0; xA < kNumSize; xA++) {
        	seriesA.getData().add(new XYChart.Data<Double, Double>(inDataX.get(0).get(xA), inDataY.get(0).get(xA)));
        }
        scatterChartDataA.add(seriesA);
        ScatterChart chart = new ScatterChart(xAxis, yAxis, scatterChartDataA);

    	for(int colNum = 1; colNum < inDataX.size(); colNum++) {
    		ObservableList<XYChart.Series<Double,Double>> scatterChartData = FXCollections.observableArrayList();
    		inColX = inDataX.get(colNum);
			inColY = inDataY.get(colNum);
			ScatterChart.Series<Double, Double> series = new XYChart.Series<Double, Double>();
	        series.setName(colLabelNames.get(colNum));
	        
			for(int seriesCount = 0; seriesCount < inColY.size(); seriesCount++) {
				
	            series.getData().add(new XYChart.Data<Double, Double>(inColX.get(seriesCount), inColY.get(seriesCount)));
		            
		        }
		        scatterChartData.add(series);
		        chart.getData().add(series);
			}
			

    	//ScatterChart chart = new ScatterChart(xAxis, yAxis, scatterChartData);
        
    	
        chartPanel.getChildren().add(chart);
        chart.setMinSize(chartPanel.widthProperty().doubleValue(), chartPanel.heightProperty().doubleValue());
        chart.setId("chartDisp");
   	
   }
    
    public void areaChart(ArrayList<Double> inXvalues, ArrayList<Double> inYvalues, double xLowerBound, 
    		double yLowerBound, double xUpperBound, double yUpperBound, double xTickSteps, double yTickSteps, String seriesLabel, String axisXName, String axisYName) {
    	//int lowerBound = 0;
   	 	NumberAxis xAxis = new NumberAxis(axisXName, xLowerBound, xUpperBound, xTickSteps);
        NumberAxis yAxis = new NumberAxis(axisYName, yLowerBound, yUpperBound, yTickSteps);
        ScatterChart.Series<Double, Double> series = new XYChart.Series<Double, Double>();
        series.setName(seriesLabel);
        ObservableList<XYChart.Series<Double,Double>> areaChartData = FXCollections.observableArrayList();
   	
        

        for (int x = 0; x < inXvalues.size(); x++) {
            series.getData().add(new XYChart.Data<Double, Double>(inXvalues.get(x), inYvalues.get(x)));
            
            
        }
        areaChartData.add(series);
        
        AreaChart chart = new AreaChart(xAxis, yAxis, areaChartData);
        
        
        chartPanel.getChildren().add(chart);
        chart.setMinSize(chartPanel.widthProperty().doubleValue(), chartPanel.heightProperty().doubleValue());
        chart.setId("chartDisp");
    }
    
    public void areaChartA(ArrayList<ArrayList<Double>> inData, ArrayList<String> colLabelNames, String axisXName, String axisYName) {
    	statsCalc statsCalc = new statsCalc();
    	//ArrayList<Double> inColX = null;
    	ArrayList<Double> inColY = null;
    	Double maxUpperBound = Double.MIN_VALUE, totalYTicks = 5.0;
    	Double minLowerBound = Double.MAX_VALUE;
    	
    	for(int allCount = 0; allCount < inData.size(); allCount++) {
    		double checkMax = Collections.max(inData.get(allCount));
    		if(maxUpperBound < checkMax) {
    			maxUpperBound = checkMax;
    		}
    		
    		/*
    		double tickSize = statsCalc.getMean(inData.get(allCount)) * 0.10;
    		if(totalYTicks < tickSize) {
    			totalYTicks = tickSize;
    		}
    		*/
    		double checkMin = Collections.min(inData.get(allCount));
    		if(minLowerBound > checkMin) {
    			minLowerBound = checkMin;
    		}
    	
    	}
    	
    	Double xUpperBound = 0.0, yUpperBound = 0.0, xLowerBound = 0.0, yLowerBound = 0.0, xTickSteps = 0.0, yTickSteps = 0.0;
    	
		//xUpperBound = maxUpperBound + (maxUpperBound * 0.25);
    	//xUpperBound = (double) inData.get(0).size();
		yUpperBound = maxUpperBound + (maxUpperBound * 0.25);
		
		xLowerBound = minLowerBound - (minLowerBound * 0.25);
		yLowerBound = minLowerBound - (minLowerBound * 0.25);
		
		//xTickSteps = 1.0;
    	//yTickSteps = totalYTicks;
		
    	int numTotalElements = 0;
    	int highest = Integer.MIN_VALUE;
    	for(int elemsA = 0; elemsA < inData.size(); elemsA++) {
    		for(int elemsB = 0; elemsB < inData.get(elemsA).size(); elemsB++) {
    			numTotalElements++;
    		}
    		
    		if(highest < numTotalElements) {
    			highest = numTotalElements;
    		}
    		numTotalElements = 0;
    	}
    	
    	xUpperBound = (double) highest;
 		
 		//xTickSteps = chartStatsCalc.getMean(inColX) * 0.10;
    	xTickSteps = (xUpperBound - xLowerBound)/highest;
    	
    	//yTickSteps = chartStatsCalc.getMean(inColY) * 0.10;
    	yTickSteps = (yUpperBound - yLowerBound)/highest;
    	
    	NumberAxis xAxis = new NumberAxis(axisXName, xLowerBound, xUpperBound, xTickSteps);
        NumberAxis yAxis = new NumberAxis(axisYName, yLowerBound, yUpperBound, yTickSteps);
        ObservableList<XYChart.Series<Double,Double>> areaChartDataA = FXCollections.observableArrayList();
        
        
        AreaChart.Series<Double, Double> seriesA = new XYChart.Series<Double, Double>();
        seriesA.setName(colLabelNames.get(0));
        for(int xA = 0; xA < inData.get(0).size(); xA++) {
        	seriesA.getData().add(new XYChart.Data<Double, Double>(Double.valueOf(xA), inData.get(0).get(xA)));
        }
        areaChartDataA.add(seriesA);
        AreaChart chart = new AreaChart(xAxis, yAxis, areaChartDataA);
        
    	for(int colNum = 1; colNum < inData.size(); colNum++) {
    		ObservableList<XYChart.Series<Double,Double>> areaChartData = FXCollections.observableArrayList();
    		//inColX = inData
			inColY = inData.get(colNum);
			AreaChart.Series<Double, Double> series = new XYChart.Series<Double, Double>();
	        series.setName(colLabelNames.get(colNum));
	        
			for(int seriesCount = 0; seriesCount < inColY.size(); seriesCount++) {
				
	            series.getData().add(new XYChart.Data<Double, Double>(Double.valueOf(seriesCount), inColY.get(seriesCount)));
		            
		        }
				areaChartData.add(series);
		        chart.getData().add(series);
			}
			

    	//ScatterChart chart = new ScatterChart(xAxis, yAxis, scatterChartData);
        
    	
        chartPanel.getChildren().add(chart);
        chart.setMinSize(chartPanel.widthProperty().doubleValue(), chartPanel.heightProperty().doubleValue());
        chart.setId("chartDisp");
   	
   }
    
     
     
 	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void barChartA(ArrayList<String> inBarNames, ArrayList<Integer> inOccurences) {
 		double tickSize = Collections.max(inOccurences)/inBarNames.size();
 		double upperBound = Collections.max(inOccurences) + Collections.min(inOccurences);
 		//double lowerBound = 
 		CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis("Y-Axis", 0, upperBound, tickSize);
        XYChart.Series series = new XYChart.Series();
        series.setName("Keyword Frequency");
        ObservableList<XYChart.Series<String,Integer>> barChartData = FXCollections.observableArrayList();
        
        for (int x = 0; x < inBarNames.size(); x++) {
            series.getData().add(new XYChart.Data<String, Integer>(inBarNames.get(x), inOccurences.get(x)));
            
        }
        //barChartData.addAll(series);
        
        BarChart<String,Number> chart = new BarChart<String,Number>(xAxis, yAxis);
        chart.getData().add(series);
        
        chartPanel.getChildren().add(chart);
        chart.setMinSize(chartPanel.widthProperty().doubleValue(), chartPanel.heightProperty().doubleValue());
        chart.setId("chartDisp");
   }
   
 	
 	public void drawTextClusters(ArrayList<String> inWordSynonymsClusters, ArrayList<ArrayList<String>> inWordOGClusters, GraphicsContext gc) {
 		
        // Set the width of the Canvas
        //canvas.setWidth(400);
        // Set the height of the Canvas
        //canvas.setHeight(200);
         
        // Get the graphics context of the canvas
        
         
        // Draw a Text
        String msgDisp = "Hello Canvas";
        //gc.strokeText(msgDisp, 150, 100);
        //gc.strokeRoundRect(140, 80, msgDisp.length()*8, 30, 10, 10);
        
        //300 for x outerDirection for middle of canvas
    	int xOuterDirection = 20;
    	int yOuterDirection = 70;
        for(int outer = 0; outer < inWordSynonymsClusters.size(); outer++) {
        	gc.setStroke(Color.BLACK);
        	gc.strokeText(inWordSynonymsClusters.get(outer), xOuterDirection, yOuterDirection);
        	gc.strokeRoundRect(xOuterDirection-10, yOuterDirection-15, (inWordSynonymsClusters.get(outer).length()*8)+10, 30, 10, 10);
        	int xInnerDirection = 20;
        	int yInnerDirection = yOuterDirection + 50;
        	for(int inner = 0; inner < inWordOGClusters.get(outer).size(); inner++) {
        		
            	
            	gc.setStroke(Color.BLACK);
        		gc.strokeText(inWordOGClusters.get(outer).get(inner), xInnerDirection, yInnerDirection);
        		gc.setStroke(Color.BLUE);
        		gc.strokeRoundRect(xInnerDirection-10, yInnerDirection-15, (inWordOGClusters.get(outer).get(inner).length()*8)+10, 30, 10, 10);
        		//xInnerDirection += 100;
        		xInnerDirection += (inWordOGClusters.get(outer).get(inner).length()*8)+20;
        	}
        	gc.setStroke(Color.RED);
        	gc.strokeLine(20, yInnerDirection+25, xInnerDirection+100, yInnerDirection+25);
        	
        	//xOuterDirection+=10;
        	yOuterDirection+=100;
        	
        }
        // this.x, this.y, this.width, this.height,OUTLINE_ROUND_RADIUS, OUTLINE_ROUND_RADIUS
        
        //chartScrollPane.getChildrenUnmodifiable().add(canvas);
        //Change pane and canvas sizes to this 614, 538
       // chartPanel.getChildren().add(canvas);
 	}
    
     
     public void saveAsPng(Node node, String path) {
     //public void saveAsPng(Chart chart, String path) {
         WritableImage image = node.snapshot(new SnapshotParameters(), null);
    	 //WritableImage image = chart.snapshot(new SnapshotParameters(), null);
         File file = new File(path);
         try {
             ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", file);
             popupInfo("File saved");
         } catch (IOException e) {
             e.printStackTrace();
         }
     }
    
    
    public void exportImage(ActionEvent e) {
    	//inFilename = null;
        //inComingDataType
    	
    	TextInputDialog td = new TextInputDialog("Filename");
    	td.setContentText("Enter Filename to Save as");
    	td.showAndWait();
    	
    	//System.out.println("Result:"+ td.getResult());
    	//System.out.println(td.getEditor().getText());
    	
    	
    	
    	try{
    		
    		if(td.getResult().equals(null)) {
    			
    		}
    		else {
    			String infileSaveName = td.getEditor().getText().toString();
            	
            	Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
            	
            	DirectoryChooser directoryChooser = new DirectoryChooser();
            	// Set title for DirectoryChooser
                directoryChooser.setTitle("Select Some Directories");
                directoryChooser.setInitialDirectory(new File(System.getProperty("user.home")));
                
                
                //File selectedFile = fileChooser.showOpenDialog(stage);
            	
            	try {
            		File dir = directoryChooser.showDialog(stage);
                	
                	
                	String outFilename = "";
                	if(infileSaveName.length() < 3) {
                		outFilename = inComingDataType;
                	}
                	else {
                		outFilename = infileSaveName;
                	}
                	
                	
                	String fileLocPath = dir.getAbsolutePath()+"\\"+outFilename+".png";
                	
                	if(chartScrollPane.isDisabled() == true) {
                		saveAsPng(chartPanel.getChildren().get(0), fileLocPath);
                	}
                	else {
                		saveAsPng(chartScrollPane.getContent(), fileLocPath);
                	}
                	//saveAsPng(chartPanel.getChildren().get(0), fileLocPath);
                	//saveAsPng(chartDisp, fileLocPath);
                	
        			//System.out.println("Tried to write");
        		} catch (NullPointerException npe) {
        			//npe.printStackTrace();
        			//Didn't select a folder or cancelled popup
        		} catch (InvalidPathException ipe) {
        			popupWarning("Alpha Numeric Names Only");
        		}
    		}
    		
    		
    		
    	}
    	catch(NullPointerException npe){
    		//System.out.println("Input was null");
    		//Cancelled entering a filename with the text dialog box
    	}
    	
    	
    	
    	
    }
    
    /*
     * General Useful Methods
     */
	public void popupWarning(String warningMsg) {
    	Alert alert = new Alert(AlertType.WARNING);
    	alert.setTitle("Warning");
    	alert.setContentText(warningMsg);


    	ButtonType buttonTypeOk = new ButtonType("Ok");
    	alert.getButtonTypes().setAll(buttonTypeOk);
    	
    	Optional<ButtonType> result = alert.showAndWait();
    	if (result.get() == buttonTypeOk){
    		//do nothing
    	}
	}
	
	public void popupInfo(String successMsg) {
    	Alert alert = new Alert(AlertType.CONFIRMATION);
    	alert.setTitle("Confirmation");
    	alert.setContentText(successMsg);


    	ButtonType buttonTypeOk = new ButtonType("Ok");
    	alert.getButtonTypes().setAll(buttonTypeOk);
    	
    	Optional<ButtonType> result = alert.showAndWait();
    	if (result.get() == buttonTypeOk){
    		//do nothing
    	}
	}
}
