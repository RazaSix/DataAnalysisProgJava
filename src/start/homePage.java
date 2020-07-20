package start;

import classes.Resources;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class homePage extends Application {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		Resources Resources =  new Resources();
		launch(args);
	}

	
	@Override
	public void start(Stage stage) throws Exception {
		  // Load the FXML file.
	      Parent parent = FXMLLoader.load(
	    		  getClass().getResource("/controllers/progGUI.fxml")); 
	               //getClass().getResource("/controllers/testPage.fxml")); 
	      
	      
	      // Build the scene graph.
	      Scene scene = new Scene(parent); 
	      
	      // Display our window, using the scene graph.
	      stage.setTitle("Data Analysis Program(D Sam)"); 
	      stage.setScene(scene);
	      stage.show();
	      
		
	}
}
