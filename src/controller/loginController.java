package controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javafx.scene.*;
import javafx.scene.Node;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import model.*;




/**
 * @author Pal Shah
 * @author Mahima Sharma
 * 
 * Logs a previously saved user into the application.
 *
 */
/**
 * @author User
 *
 */
public class loginController {

	@FXML
	private Label label;
	@FXML
	private Button login;
	@FXML
	private TextField username;
	@FXML
	private Label warning;

	public void login(ActionEvent e) throws ClassNotFoundException, IOException {

		String name = username.getText();
		listOfUsers users= null;
		
		users = listOfUsers.read();


		if (name.equals("admin")) {
			
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/admin.fxml"));
			Parent parent = loader.load();
			
			Scene adm = new Scene(parent);
			
			AdminController admin = loader.getController();
			
			Stage window = (Stage)((Node)e.getSource()).getScene().getWindow();
			admin.start(window);
			window.setScene(adm);
			window.show();
			
		}
		else {
			boolean b = false;
			if(users.isUserinList(name)) {
				b=true;
			}
			if(b==true) {
				FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/non-admin.fxml"));
				Parent parent = loader.load();
			
				NonAdminController admin = loader.getController();
				
				admin.setUser(users.getUserByUsername(name));
				admin.setUlist(users);
				
				if(name.equals("stock")){
					admin.isStock(true);
				}
				else{
					admin.isStock(false);
				}
				
				Scene adm = new Scene(parent);
			        
				Stage window = (Stage) ((Node)e.getSource()).getScene().getWindow();	
		                
				admin.start(window);
				window.setScene(adm);
				window.show();  
			}
			else {

				warning.setText("Please enter a valid username");
			}
		}

	}

	/**
	 * @param window
	 * Takes in a Stage window as a parameter and does not initialize anything at the start of the window
	 */
	public void start(Stage window) {
		// TODO Auto-generated method stub
		
	}

}
