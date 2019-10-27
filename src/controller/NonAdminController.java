package controller;


import java.awt.Event;
import java.io.File;
import java.io.IOException;
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
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Callback;
import model.*;


/**@author Pal Shah
 * @author Mahima Sharma
 * 
 *
 */
public class NonAdminController {	
	@FXML private ListView<Album> albumView;
	
	@FXML private Label labName;
	
	@FXML private Label labNo;
	
	@FXML private Label labDate;
	
	@FXML private Label labUser;
	
	@FXML private Button createAlbum;
	
	@FXML private Button deleteAlbum;
	
	@FXML private Button renameAlbum;
	
	@FXML private Button openAlbum;
	
	@FXML private Button searchAlbum;
	
	@FXML private Button updatePicture;
	
	@FXML private ImageView userImage; 
	
	@FXML private Button logout;
	
	
	private User user;
	private listOfUsers users;
	
	private ObservableList<Album> obsList;
	private List<Album> albums = new ArrayList<Album>();
	private boolean stock;
	
	//serialization done for albums for users too
	
	/**
	 * @param takes in the Stage window and initializes the Non-Admin subsystem.
	 */
	public void start (Stage window) {
		labUser.setText("Hi, " + user.getName());
		//First need to load all the files from the serializable list right now using mahima, pal for testing 
		if(stock==false){
		albums = user.getAlbums();
		
		obsList = FXCollections.observableArrayList(albums);
		
		
		albumView.setItems(obsList);
		
		if(!obsList.isEmpty()) {
			albumView.getSelectionModel().select(0);
			labName.setText(obsList.get(0).getAlbumName());
			labNo.setText(obsList.get(0).getNoOfPhotosString());
			if(obsList.get(0).getNoOfPhotos()==0) {
				labDate.setText("NA");
			}
			else {
				labDate.setText(obsList.get(0).getDateRange());
			}
		}

		if(albumView.getSelectionModel().getSelectedIndex()>0) {
			albumView.getSelectionModel().selectedIndexProperty().addListener((obs,oldVal, newVal)->showAlbum(window));
		}
		
		final FileChooser fileChooser = new FileChooser();
		Image image1 = new Image("/docs/download.png");
		userImage.setImage(image1);
		
		
		updatePicture.setOnAction((final ActionEvent e) -> {
            File file = fileChooser.showOpenDialog(window);
            if (file != null) {
                Image image2 = new Image(file.toURI().toString());
                userImage.setImage(image2);
                
            }
        }); 
		
		
		window.setOnCloseRequest(event -> {
			albums = obsList;
			
			//try {
//				user.setAlbums(albums);
//				User utemp = users.get(0);
//				for(User u: users) {
//					if(u.getUsername().equals(user.getUsername())){
//						utemp = u;
//						System.out.println(u.getAlbums());
//						break;
//					}
//				}
//				System.out.println(user.getAlbums());
//				System.out.println(utemp.getAlbums());
				
//				//User.write(users);
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
			
		});
		}
		else{
			albums = user.getAlbums();
			
			obsList = FXCollections.observableArrayList(albums);
			albumView.setItems(obsList);
			
			if(!obsList.isEmpty()) {
				albumView.getSelectionModel().select(0);
				labName.setText(obsList.get(0).getAlbumName());
				labNo.setText(obsList.get(0).getNoOfPhotosString());
				if(obsList.get(0).getNoOfPhotos()==0) {
					labDate.setText("NA");
				}
				else {
					labDate.setText(obsList.get(0).getDateRange());
				}
			}

			if(albumView.getSelectionModel().getSelectedIndex()>0) {
				albumView.getSelectionModel().selectedIndexProperty().addListener((obs,oldVal, newVal)->showAlbum(window));
			}
			
			final FileChooser fileChooser = new FileChooser();
			Image image1 = new Image("/docs/download.png");
			userImage.setImage(image1);
			
			
			updatePicture.setOnAction((final ActionEvent e) -> {
	            File file = fileChooser.showOpenDialog(window);
	            if (file != null) {
	                Image image2 = new Image(file.toURI().toString());
	                userImage.setImage(image2);
	                
	            }
	        }); 
			
		}
		
	}
	
	/**
	 * @param window
	 * Takes in a Stage window as a parameter and shows all the albums in the listview.
	 */
	
	public void showAlbum(Stage window) {
		int index = albumView.getSelectionModel().getSelectedIndex();
		if(index>=0) {
		Album a = obsList.get(index);
		labName.setText(a.getAlbumName());
		labNo.setText(a.getNoOfPhotosString());
		
		if(a.getNoOfPhotos()==0) {
			labDate.setText("NA");
		}
		else {
			labDate.setText(a.getDateRange());
		}
		}

	}
	
	/**This function handles the create user button.
	 * Opens a dialog box which prompts for album name and keeps on opening until valid user name entered or user decides to quit
	 * @param takes an onclick button request from "Create Album" button
	 * @throws ClassNotFoundException if the class Album or supported classes not found
	 * @throws IOException 
	 */
	public void createAlbumEvent(ActionEvent e) throws ClassNotFoundException, IOException{
		if(stock==false){
		Dialog<ButtonType> dialog = new Dialog<>();
		DialogPane dialogPane = dialog.getDialogPane();
		dialog.setTitle("Create a New Album");
		dialog.setHeaderText("Add a new album to Picasso!                                                ");
		dialogPane.getStylesheets().add(getClass().getResource("../docs/non-admin.css").toExternalForm());
		dialog.setResizable(false);
		   
		Label albumLabel = new Label("Album Name: ");
		TextField albumTextField = new TextField();
		Label warningCreate = new Label("");
		
		albumTextField.setPromptText("Album Name");
		   
		GridPane grid = new GridPane();
		grid.add(albumLabel, 1, 1);
		grid.add(albumTextField, 2, 1);
		grid.add(warningCreate, 1, 3);

//		grid.add(node0, 1, 2);
//		grid.add(node1, 2, 2);
		   
		dialog.getDialogPane().setContent(grid);
		   
		ButtonType Ok = new ButtonType("Create", ButtonData.OK_DONE);
		ButtonType cancel = new ButtonType("Cancel", ButtonData.CANCEL_CLOSE);
		
		
		dialog.getDialogPane().getButtonTypes().addAll(Ok, cancel);


		Optional<ButtonType> result = dialog.showAndWait();

		
		if(result.get()==Ok) {
			while (result.get()==Ok) {

				dialog.setOnCloseRequest(event -> {
					//to finish that
					
				});

			if(albumTextField.getText().equals("")) {
				warningCreate.setText("Enter a valid Album Name");
				result = dialog.showAndWait();
				continue;
				
			}
			else {
				String aname = albumTextField.getText();
				if(obsList.isEmpty()) {
					Album a = new Album(aname);
					obsList.add(a);
					albums.add(a);
					try {
						listOfUsers.write(users);
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
					albumView.getSelectionModel().select(0);
					labName.setText(a.getAlbumName());
					labNo.setText(a.getNoOfPhotosString());
					
					if(a.getNoOfPhotos()==0) {
						labDate.setText("NA");
					}
					else {
						labDate.setText(a.getDateRange());
					}
					
				}
				else {
					boolean b = true;
					for(Album a :obsList) {
						if(a.getAlbumName().equals(aname)) {
							b = false;
						}
					}
					if(b==false) {
						warningCreate.setText("Album name exists. Enter a valid Album Name");
						result = dialog.showAndWait();
						continue;
					}
					else {
						Album a = new Album(aname);
						obsList.add(a);
						albums.add(a);
						
						listOfUsers.write(users);
						
						int index = 0;
						for(Album a1:obsList) {
							if(a1.getAlbumName().equals(a.getAlbumName())) {
								break;
							}
							index++;
						}
						albumView.getSelectionModel().select(index);
						labName.setText(a.getAlbumName());
						labNo.setText(a.getNoOfPhotosString());
						
						if(a.getNoOfPhotos()==0) {
							labDate.setText("NA");
						}
						else {
							labDate.setText(a.getDateRange());
						}

						dialog.close();
						break;
					}
					
					
				}
			}
		}
		}
		else {
			dialog.close();
		}
		}
		else{
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setHeaderText("Sorry, operate photos through stock album");
			alert.showAndWait();
			return;
		}
		
		
		
	}
	
	
	/**
	 * @param event
	 * @throws IOException
	 * This method deletes the albums on the user's request. 
	 * 
	 */
	public void deleteAlbumEvent(ActionEvent event) throws IOException {
		if(stock==false){
		Dialog<ButtonType> dialog = new Dialog<>();
		DialogPane dialogPane = dialog.getDialogPane();
		dialogPane.getStylesheets().add(getClass().getResource("../docs/non-admin.css").toExternalForm());
		dialog.setResizable(false);
		
		int index = albumView.getSelectionModel().getSelectedIndex();
		Album a = obsList.get(index);
		
 		dialog.setTitle("Delete Album");
 		String content = "Are you sure you want to delete the album " + a.getAlbumName() + "?";
 		
 		ButtonType Ok = new ButtonType("Delete", ButtonData.OK_DONE);
 		
 		dialog.getDialogPane().getButtonTypes().add(Ok);

 		dialog.setContentText(content);

 		Optional<ButtonType> result = dialog.showAndWait();
 		   
 		if(result.isPresent())
 		  {
 			  obsList.remove(a);
 			  user.removeAlbum(a);
 			  listOfUsers.write(users);
 			  
 			  
 			  if(!obsList.isEmpty()) {
 				  if(index<=obsList.size()-1 && obsList.size()>1) {
 					  	albumView.getSelectionModel().select(index);
 					  	Album a1 = obsList.get(index);
						labName.setText(a1.getAlbumName());
						labNo.setText(a1.getNoOfPhotosString());
						
						if(a1.getNoOfPhotos()==0) {
							labDate.setText("NA");
						}
						else {
							labDate.setText(a1.getDateRange());
						}
 				  }
 			  }
 			  else {
 				  	labName.setText(" ");
					labNo.setText(" ");
					labDate.setText("NA");
					
 			  }
 		  }
		}
		else{
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setHeaderText("Sorry, operate photos through stock album");
			alert.showAndWait();
			return;
		}
	}
	
	/**
	 * @param e
	 * This method renames the album
	 */
	public void renameAlbumEvent(ActionEvent e) {
		if(stock==false){
		Dialog<ButtonType> dialog = new Dialog<>();
		DialogPane dialogPane = dialog.getDialogPane();
		dialogPane.getStylesheets().add(getClass().getResource("../docs/non-admin.css").toExternalForm());
		dialog.setResizable(false);
		
		int index = albumView.getSelectionModel().getSelectedIndex();
		Album a = obsList.get(index);
		
 		dialog.setTitle("Rename Album");
 		
 		Label albumLabel = new Label("Album Name: ");
		TextField albumTextField = new TextField();
		Label warningCreate = new Label("");
		
		albumTextField.setPromptText("Album Name");
		   
		GridPane grid = new GridPane();
		grid.add(albumLabel, 1, 1);
		grid.add(albumTextField, 2, 1);
		grid.add(warningCreate, 1, 3);

//		grid.add(node0, 1, 2);
//		grid.add(node1, 2, 2);
		   
		dialog.getDialogPane().setContent(grid);
		   
		ButtonType Ok = new ButtonType("Rename", ButtonData.OK_DONE);
		ButtonType cancel = new ButtonType("Cancel", ButtonData.CANCEL_CLOSE);
		
		
		dialog.getDialogPane().getButtonTypes().addAll(Ok, cancel);


		Optional<ButtonType> result = dialog.showAndWait();

		
		if(result.get()==Ok) {
			while (result.get()==Ok) {

				dialog.setOnCloseRequest(event -> {
					//to finish that
					
				});

			if(albumTextField.getText().equals("")) {
				warningCreate.setText("Enter a valid Album Name");
				result = dialog.showAndWait();
				continue;
				
			}
			else {
				String aname = albumTextField.getText();
				boolean b = true;
				int indextemp =0;
				for(Album a1 :obsList) {
					if(a1.getAlbumName().equals(aname) && indextemp!=index) {
						b = false;
					}
				}
				if(b==false) {
					warningCreate.setText("Album name exists. Enter a valid Album Name");
					result = dialog.showAndWait();
					continue;
				}
				else {
					a.setAlbumName(aname);
					albumView.setItems(obsList);
					albumView.getSelectionModel().select(index);
					labName.setText(a.getAlbumName());
					labNo.setText(a.getNoOfPhotosString());
					if(a.getNoOfPhotos()==0) {
						labDate.setText("NA");
					}
					else {
						labDate.setText(a.getDateRange());
					}
						break;
					}
					
					
				}
			}
		}
	
		else {
			dialog.close();
		}
		}
		else{
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setHeaderText("Sorry, operate photos through stock album");
			alert.showAndWait();
			return;
		}
		
	}
	
	/**
	 * @param e
	 * @throws IOException
	 * This method logs the user out of the current scene.
	 */
	public void logout(ActionEvent e) throws IOException {
		
		//have to make sure to serialize stuff before logging out
		albums = obsList;
		//User.write(users);
		
		Parent parent;
   	 
		try {
						
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/login.fxml"));
			parent = (Parent) loader.load();
		
			Scene scene = new Scene(parent);
							
			Stage app_stage = (Stage) ((Node) e.getSource()).getScene().getWindow();			             
			app_stage.setScene(scene);
			app_stage.show();  
						
			 
		
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} 
	}
	
	/**
	 * @param e
	 * @throws IOException
	 * @throws ClassNotFoundException
	 * This method shifts the scene to open Album. It opens the albums and shows the user all the pictures
	 */
	public void openAlbumEvent(ActionEvent e) throws IOException, ClassNotFoundException {
		
		//albums = obsList;
		//Album.write(albums);
		
		
		if(obsList.isEmpty()!=true){
		int index = albumView.getSelectionModel().getSelectedIndex();
		Album a = obsList.get(index);
		
		
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/open.fxml"));
		Parent parent = loader.load();
		
		
	
		OpenAlbumController admin = loader.getController();
		Scene adm = new Scene(parent);
		
		admin.setAlbum(a);
		admin.setUser(user);
        admin.setListOfUsers(users);
        if(stock==true){
        	admin.isStock(true);
        }
        else{
        	admin.isStock(false);
        }
	        
		Stage window = (Stage) ((Node)e.getSource()).getScene().getWindow();	
                
		admin.start(window);
		window.setScene(adm);
		window.setTitle(a.getAlbumName());
		window.show();  
		
		}
		
	}
	
/**
 * @param e
 * @throws IOException
 * @throws ClassNotFoundException
 * This method searches the album in the album list of the user.
 */
public void searchAlbumEvent(ActionEvent e) throws IOException, ClassNotFoundException {

		
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/search.fxml"));
		Parent parent = loader.load();
		
		
	
		searchController admin = loader.getController();
		Scene adm = new Scene(parent);

		admin.setUser(user);
        admin.setUlist(users);
        if(stock==true) {
        	admin.isStock(true);
        }
        else {
        	admin.isStock(false);
        }
	        
		Stage window = (Stage) ((Node)e.getSource()).getScene().getWindow();	
                
		admin.start(window);
		window.setScene(adm);
		window.show();  
	}
	
	/**
	 * @param u
	 * sets current user
	 */
	public void setUser(User u) {
		this.user = u;
	}
	
	/**
	 * @param users
	 * sets current list of users
	 */
	public void setUlist(listOfUsers users) {
		this.users = users;
	}
	
	/**
	 * @param stock
	 * Checks if the user is a stock user or not
	 */
	public void isStock(boolean stock){
		this.stock =stock;
	}
	
	

}
