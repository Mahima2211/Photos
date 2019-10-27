package controller;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.*;

/**
 * @author Pal Shah
 * @author Mahima Sharma
 * 
 * Search Controller which searches by tags and dates
 *
 */
public class searchController {
	
	
	@FXML
	private TextField endtf = new TextField();

	@FXML
	private TextField starttf = new TextField();
	
	@FXML
	private Button search;
	
	@FXML
	private Button createAlbum;
	
	@FXML
	private TextField name = new TextField();
	
	@FXML
	private Label warning;
	
	@FXML
	private Label warning2;
	
	@FXML
	private ListView<String> list;
	
	@FXML
	private ObservableList<String> obsList;
	
	private User user;
	private listOfUsers users;
	private boolean stock;
	private List<Album> albums = new ArrayList<Album>();
	private List<Photo> searchedPhotos = new ArrayList<Photo>();
	
	public void start(Stage window) {
		albums = user.getAlbums();
		obsList = FXCollections.observableArrayList();
		
		
		
//		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
//		try{
//			LocalDate calstart = LocalDate.parse(start, formatter);
//			LocalDate calend = LocalDate.parse(end, formatter);
//		}
//		catch(Exception ex) {
//			start 
//		}
		
		
		
	}
	
	/**
	 * @param e
	 * Searches by tags and dates
	 */
	public void searchEvent(ActionEvent e) {
		String start = starttf.getText();
		String end = endtf.getText();
		
		LocalDate calstart;
		LocalDate calend;
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
		try{
			calstart = LocalDate.parse(start, formatter);
			calend = LocalDate.parse(end, formatter);
		}
		catch(Exception ex) {
			warning2.setText("Enter in MM/DD/YYYY format");
			return;
		}
		
		List<Photo> photos;
		for(Album a: albums) {
			photos = a.getPhotos();
			if(photos!=null) {
				for(Photo p:photos) {
					if(p.isWithinDateRange(calstart, calend)) {
						obsList.add(a +", " + p.getCaption());
						searchedPhotos.add(p);
					}
					
				}
			}
			
		}
		
		if(obsList!=null) {
			list.setItems(obsList);
		}
	}
	
	/**
	 * @param e
	 * creates an album
	 */
	public void createAlbumEvent(ActionEvent e) {
		if(searchedPhotos.isEmpty()) {
			warning.setText("Search photos by category first");
			return;
		}
		
		String aname = name.getText();
		boolean good = true;
		
		for(Album a :albums) {
			if(a.getAlbumName().equals(aname)) {
				good = false;
			}
		}
		
		if(name.equals(" ") ||good==false ) {
			warning.setText("Enter valid album name, one not used before.");
			return;
		}
		
		Album a = new Album(aname);
		albums.add(a);
		warning.setText("Done.");
		try {
			listOfUsers.write(users);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
	}
	
	/**
	 * @param u
	 * sets the user
	 */
	public void setUser(User u) {
		this.user = u;
	}
	
	/**
	 * @param users
	 * sets the list of users
	 */
	public void setUlist(listOfUsers users) {
		this.users = users;
	}
	
	/**
	 * @param stock
	 * passes the stock user
	 */
	public void isStock(boolean stock) {
		this.stock = stock;
	}
	
	

}