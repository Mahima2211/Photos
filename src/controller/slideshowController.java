package controller;

import java.util.ArrayList;
import java.util.List;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import model.*;

/**
 * @author Pal Shah
 * @author Mahima Sharma
 * 
 * Views photos in slideshow
 *
 */

public class slideshowController {
	@FXML private Label name;
	
	@FXML private Button prev;
	
	@FXML private Button next;
	
	@FXML private Button back;
	
	@FXML private ImageView view;
	
	private Album album;
	private User user;
	private listOfUsers users;
	private List<Photo> photos = new ArrayList<Photo>();
	private boolean stock;

	
	
	
	/**
	 * @param window
	 * initializes slideshow scene
	 */
	public void start(Stage window) {
		photos = album.getPhotos();
		String loc = photos.get(0).getLoc();
		
		Image image = new Image(loc);
		view.setImage(image);
	}
	
	/**
	 * @param e
	 * views the previous photos
	 */
	public void previous(ActionEvent e) {
		Image i = view.getImage();
		if(i.toString().equals(photos.get(0).getLoc())){
			return;
		}
		String locCurrent = i.toString();
		
		int index =0;
		for(Photo p:photos) {
			if(locCurrent.equals(p.getLoc())) {
				break;
			}
			++index;
		}
		
		String newLoc = photos.get(--index).getLoc();
		Image image = new Image(newLoc);
		view.setImage(image);
	}
	
	/**
	 * @param e
	 * goes to the next photo
	 */
	public void next(ActionEvent e) {
		Image i = view.getImage();
		int last = photos.size();
		
		Photo lastpic = photos.get(last-1);
		if(i.toString().equals(lastpic.getLoc())){
			return;
		}
		
		String locCurrent = i.toString();
		
		int index =0;
		for(Photo p:photos) {
			if(locCurrent.equals(p.getLoc())) {
				break;
			}
			++index;
		}
		
		String newLoc = photos.get(++index).getLoc();
		Image image = new Image(newLoc);
		view.setImage(image);
	}
	
	
	
	/**
	 * @param album
	 * passing album
	 */
	public void setAlbum(Album album) {
		this.album = album;
	}
	
	/**
	 * @param user
	 * setting user
	 */
	public void setUser(User user) {
		this.user = user;
	}
	
	/**
	 * @param users
	 * setting list of users
	 */
	public void setListOfUsers(listOfUsers users) {
		this.users = users;
	}
	
	/**
	 * @param stock
	 * passing stock
	 */
	public void isStock(boolean stock) {
		this.stock = stock;
	}
	

}

