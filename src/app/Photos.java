package app;



import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;


/**
 * @author Pal Shah
 * @author Mahima Sharma
 *
 *This is Photos. The main application starts running from here.
 *
 */
public class Photos extends Application {

		Stage mainStage;
		@Override
		public void start(Stage primaryStage) throws IOException, ClassNotFoundException {
			
			mainStage = primaryStage;
			try {
				FXMLLoader loader = new FXMLLoader();
				loader.setLocation(getClass().getResource("/view/login.fxml"));
				AnchorPane root = (AnchorPane)loader.load();
				Scene scene = new Scene(root);
				
				
				mainStage.setScene(scene);
				mainStage.setTitle("Picasso Album Manager");
				mainStage.setResizable(false);
				mainStage.show();
				
			} catch(Exception e) {
				e.printStackTrace();
			}		
		}
		
		public static void main(String[] args) {
			launch(args);
		}
	}

