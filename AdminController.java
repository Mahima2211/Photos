package controller;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.User;
import model.listOfUsers;


/**
 * @author Pal Shah
 * @author Mahima Sharma
 * 
 * This is the AdminController class. It allows the Admin to create and delete users.
 *
 */
public class AdminController {

	@FXML
	private ListView<User> listView;

	@FXML
	private Button logout;

	@FXML
	private Button createUser;

	@FXML
	private Button deleteUser;

	@FXML
	private TextField tf1 = new TextField();

	@FXML
	private TextField tf2 = new TextField();

	private ObservableList<User> obsList;
	private List<User> users = new ArrayList<User>();
	private listOfUsers userlist = new listOfUsers();

	public void start(Stage window) throws ClassNotFoundException, IOException {

		userlist = listOfUsers.read();
		
		
		users = userlist.getListOfUsers();
		
		//users.add(new User("Pal","pas324"));

		
		obsList = FXCollections.observableArrayList(users);
		listView.setItems(obsList);
		listView.getSelectionModel().select(0);
		
		
		//before closing----should we also serialize or is it not necessary?
		
		window.setOnCloseRequest(event -> {
			//users = obsList;
			
			try {
				listOfUsers.write(userlist);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		});

	}

	/**
	 * @param e
	 * This method adds users to the admin list. This allows users to login. 
	 */
	public void addUsers(ActionEvent e) {

		// System.out.println("in the add users method");

		if (tf1.getText().trim().isEmpty() || tf2.getText().trim().isEmpty()) {
			Alert fail = new Alert(AlertType.ERROR);
			fail.setHeaderText("Please enter a name and a username for the User");
			fail.showAndWait();
		} else {
			String tf1val = tf1.getText();
			String tf2val = tf2.getText();

			//System.out.println(tf1val);
			//System.out.println(tf2val);
			User user = new User(tf1val, tf2val);

			// check here for no duplicates

			if (isDuplicate(user) == true) {
				Alert fail = new Alert(AlertType.ERROR);
				fail.setHeaderText("This name and username already exits");
				fail.showAndWait();
			} else {
				
				obsList.add(user);
				users.add(user);
				
				// highlighting added user
				
				if (obsList.size() == 1) {
					   listView.getSelectionModel().select(0);
				   }
				else{
					
					int select = 0; 
					
					for (User s: obsList){
						if(s.getName().equals(user.getName())&& s.getUsername().equals(user.getUsername())){
							break;
						}
						select++;
					}
					
					listView.getSelectionModel().select(select);
					
				}
				 
				
				//end highlighting added user

				
			}

		}
		
	}
	
	
	
	/**
	 * @param e
	 * Deletes users from the admin list.
	 */
	public void deleteUsers(ActionEvent e){
		
		tf1.clear();
		tf2.clear();
		
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setHeaderText("Are you sure you want to delete this user?");
		
		Optional<ButtonType> result = alert.showAndWait();
		if(result.get()==ButtonType.CANCEL){
			return;
		}
		
		//User item = listView.getSelectionModel().getSelectedItem();
		int index = listView.getSelectionModel().getSelectedIndex();
		obsList.remove(index);
		users.remove(index);
	}
	
	/**
	 * @param e
	 * @throws IOException
	 * Log's the admin out of the application.
	 */
	public void loggingOut(ActionEvent e) throws IOException{
		
		//serializing to file
		//users = obsList;
		listOfUsers.write(userlist);
		
		
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/login.fxml"));
		Parent p = loader.load();
		
		Scene lgin = new Scene(p);
		
		//access the controller
		
		loginController login = loader.getController();
		
		Stage window = (Stage)((Node)e.getSource()).getScene().getWindow();
		login.start(window);
		window.setScene(lgin);
		window.show();
		
	}

	/**
	 * @param user
	 * @return boolean
	 * Takes in a user parameter and checks if the obsList has a duplicate value. Returns true if there is a duplicate user
	 * and returns false otherwise.
	 */
	private boolean isDuplicate(User u1) {

		for (User u : obsList) {
			if (u1.getName().toLowerCase().trim().equals(u.getName().toLowerCase().trim())
					&& u1.getUsername().toLowerCase().trim().equals(u.getUsername().toLowerCase().trim())) {
				return true;
			}
		}

		return false;
	}

}
