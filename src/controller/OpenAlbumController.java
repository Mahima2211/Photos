package controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Optional;

//import javax.security.auth.callback.Callback;

import javafx.util.Callback;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.*;

/**
 * @author Pal Shah
 * @author Mahima Sharma
 * 
 * This the open album controller class
 *
 */
public class OpenAlbumController {

	@FXML
	private ListView<Photo> photoListView;

	@FXML
	private ImageView display;

	@FXML
	private Button addPhoto;

	@FXML
	private Button deletePhoto;

	@FXML
	private Button captionPhoto;

	@FXML
	private Button copyPhoto;

	@FXML
	private Button movePhoto;

	@FXML
	private Button slideshow;

	@FXML
	private Label caption;

	@FXML
	private Label dateTime;

	@FXML
	private Label tags;

	@FXML
	private TextField captiontf = new TextField();

	@FXML
	private TextField dateTimetf = new TextField();

	 @FXML
	 private ListView<TagValue> tagValueListView;

	@FXML
	private ListView<Tag> tagListView;

	@FXML
	private TextField tagtf = new TextField();

	@FXML
	private TextField valuetf = new TextField();

	@FXML
	private Button addTag;

	@FXML
	private Button deleteTag;

	@FXML
	private Button logout;

	private ObservableList<Photo> obsList;
	private ObservableList<Tag> obsTag;
	private ObservableList<TagValue> obsTagVal;

	private List<Photo> photos;
	private List<Tag> tempTagList;
	private List<TagValue> tempTagValList;
	private Album album;
	private User user;
	private listOfUsers users;
	private boolean stock;

	/**
	 * @param window
	 * @throws ClassNotFoundException
	 * @throws IOException
	 * 
	 * Initializes the open album controller class
	 * 
	 */
	public void start(Stage window) throws ClassNotFoundException, IOException {
		// TODO Auto-generated method stub

		// System.out.println("starting");
		photos = album.getPhotos();

		obsList = FXCollections.observableArrayList(photos);
		// obsList.add(new Photo("this is a caption"));

		photoListView.setCellFactory(new Callback<ListView<Photo>, ListCell<Photo>>() {

			@Override
			public ListCell<Photo> call(ListView<Photo> p) {

				return new PhotoCell();
			}
		});

		photoListView.setItems(obsList);



		if (obsList.isEmpty() != true) {

			photoListView.getSelectionModel().select(0);

			Photo p = photos.get(0);
			Image image = new Image(p.getLoc());

			display.setImage(image);

			photoListView.getSelectionModel().selectedIndexProperty().addListener((obs, oldVal, newVal) -> showItem());

		}

	}

	/**
	 * Shows the details of the selected item
	 */
	public void showItem() {

		if (photoListView.getSelectionModel().getSelectedIndex() < 0) {
			return;
		}

		Photo p = photoListView.getSelectionModel().getSelectedItem();
		captiontf.setText(p.getCaption());
		dateTimetf.setText(p.getDateTime());
		String s = p.getLoc();
		Image image = new Image(p.getLoc());

		display.setImage(image);

		obsTag = FXCollections.observableArrayList(user.getTagList());
		// add observable list for tagvalue as well
		
		//obsTagVal = FXCollections.observableArrayList(user.getTagValueList());
		
		obsTagVal = FXCollections.observableArrayList(user.getTagValueList());

		tagListView.setItems(obsTag);

		// set items for tagvaluepairlist
		tagValueListView.setItems(obsTagVal);

	}

	/**
	 * @param e
	 * Adds tag to photo
	 */
	public void addTagT(ActionEvent e) {

		// System.out.println("add Tag method");

		String tag = tagtf.getText();
		String value = valuetf.getText();

	
//just for tag
		boolean b = false;

		for (int i = 0; i < obsTag.size(); i++) {

			String t = obsTag.get(i).toString();
			if (t.equals(tag)) {
				b = true;
			}
		}

		if (b == false) {
			obsTag.add(new Tag(tag));
		}

//for tag and value both
		
		boolean c = false;
		
		for (int i = 0; i < obsTagVal.size(); i++) {

			String t = obsTagVal.get(i).getTag();
			String v = obsTagVal.get(i).getValue();
			if (t.equals(tag) && v.equals(value)) {
				c = true;
			}
		}

		if (c == false) {
			obsTagVal.add(new TagValue(tag,value));
		}
		
		

		// delete after serialization???? maybe

		tempTagList = obsTag; // updating the tempTagList
		user.setTagList(tempTagList); // setting the specific taglist for users

		tempTagValList = obsTagVal;
		user.setTagValueList(tempTagValList);
		
		// serialize
//
//		try {
//			listOfUsers.write(users);
//		} catch (IOException e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		}
		
	}
	
	/**
	 * @param e
	 * deletes a tag from the photo of a user
	 */
	public void deleteTagT(ActionEvent e) {
		
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setHeaderText("Are you sure you want to delete tag value pair?");
		
		TagValue item = tagValueListView.getSelectionModel().getSelectedItem();
		int index = tagValueListView.getSelectionModel().getSelectedIndex();
		
		obsTagVal.remove(index);
		
		tempTagValList = obsTagVal;
		user.setTagValueList(tempTagValList);
		
//		try {
//			listOfUsers.write(users);
//		} catch (IOException e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		}
//		
		
	}
	

	/**
	 * @param e
	 * adds pictures to the user's album
	 */
	public void addPics(ActionEvent e) {

		FileChooser fileChooser = new FileChooser();

		FileChooser.ExtensionFilter extFilterJPG = new FileChooser.ExtensionFilter("JPG files (*.jpg)", "*.JPG");
		FileChooser.ExtensionFilter extFilterPNG = new FileChooser.ExtensionFilter("PNG files (*.png)", "*.PNG");
		fileChooser.getExtensionFilters().addAll(extFilterJPG, extFilterPNG);

		fileChooser.setTitle("Upload Photo");
		Stage app_stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
		File file = fileChooser.showOpenDialog(app_stage);

		if (file == null) {
			return;
		}

		Photo pic1 = new Photo(file.toURI().toString());

		obsList.add(pic1);
		photos.add(pic1);
		try {
			listOfUsers.write(users);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

	}

	/**
	 * @param e
	 * deletes a picture from the user's album
	 */
	public void delete(ActionEvent e) {

		// System.out.println("delete method");

		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setHeaderText("Are you sure you want to delete this photo?");

		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == ButtonType.CANCEL) {
			return;
		}

		Photo item = photoListView.getSelectionModel().getSelectedItem();
		int index = photoListView.getSelectionModel().getSelectedIndex();

		obsList.remove(index);
		photos.remove(index);
		

		try {
			listOfUsers.write(users);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

	}

	/**
	 * @param e
	 * Allows user to caption pictures
	 */
	public void captionPics(ActionEvent e) {

		if (obsList.size() < 1) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setHeaderText("There are no pictures to add captions to add, please add a picture first");
			alert.showAndWait();
		}

		Dialog<ButtonType> dialog = new Dialog<>();
		DialogPane dialogPane = dialog.getDialogPane();
		dialog.setResizable(false);

		int index = photoListView.getSelectionModel().getSelectedIndex();

		Photo p = obsList.get(index);

		dialog.setTitle("Caption");

		Label captionLabel = new Label("Caption: ");
		TextField field = new TextField();
		Label warningCreate = new Label("");

		captiontf.setPromptText(p.getCaption());

		GridPane grid = new GridPane();
		grid.add(captionLabel, 1, 1);
		grid.add(field, 2, 1);
		grid.add(warningCreate, 1, 3);

		dialog.getDialogPane().setContent(grid);

		ButtonType Ok = new ButtonType("Save", ButtonData.OK_DONE);
		ButtonType cancel = new ButtonType("Cancel", ButtonData.CANCEL_CLOSE);

		dialog.getDialogPane().getButtonTypes().addAll(Ok, cancel);

		Optional<ButtonType> result = dialog.showAndWait();

		if (result.get() == Ok) {
			obsList.get(index).setCaption(field.getText());

		}
		// System.out.print(obsList.get(index).getCaption());
		obsList.remove(p);
		obsList.add(index, p);

		try {
			listOfUsers.write(users);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		// System.out.println("captioning");
	}

	/**
	 * @param e
	 * Copies a picture to a different album
	 */
	public void copyPic(ActionEvent e) {

		Dialog<ButtonType> dialog = new Dialog<>();
		DialogPane dialogPane = dialog.getDialogPane();
		dialog.setResizable(false);

		int index = photoListView.getSelectionModel().getSelectedIndex();

		Photo p = obsList.get(index);

		dialog.setTitle("Copy Photo");
		Label albumLabel = new Label("Album Name: ");
		TextField albumTextField = new TextField();
		Label warningCreate = new Label("");

		albumTextField.setPromptText("Album Name");

		GridPane grid = new GridPane();
		grid.add(albumLabel, 1, 1);
		grid.add(albumTextField, 2, 1);
		grid.add(warningCreate, 1, 3);

		dialog.getDialogPane().setContent(grid);

		ButtonType Ok = new ButtonType("Copy", ButtonData.OK_DONE);
		ButtonType cancel = new ButtonType("Cancel", ButtonData.CANCEL_CLOSE);

		dialog.getDialogPane().getButtonTypes().addAll(Ok, cancel);

		Optional<ButtonType> result = dialog.showAndWait();

		String destAlbum = albumTextField.getText();

		List<Album> alb = new ArrayList();

		alb = user.getAlbums();

		for (int i = 0; i < alb.size(); i++) {
			if (alb.get(i).getAlbumName().equals(destAlbum)) {

				String s = p.getLoc();
				alb.get(i).addPhoto(p);

				try {
					listOfUsers.write(users);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

				return;
			}
		}

		Alert alert = new Alert(AlertType.ERROR);
		alert.setHeaderText("No such album exists");
		alert.showAndWait();

		// serialize

		try {
			listOfUsers.write(users);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		// System.out.println("copyPic method");

	}

	/**
	 * @param e
	 * moves picture to a different album
	 */
	public void movePic(ActionEvent e) {

		Dialog<ButtonType> dialog = new Dialog<>();
		DialogPane dialogPane = dialog.getDialogPane();
		dialog.setResizable(false);

		int index = photoListView.getSelectionModel().getSelectedIndex();

		Photo p = obsList.get(index);

		dialog.setTitle("Move Photo");
		Label albumLabel = new Label("Album Name: ");
		TextField albumTextField = new TextField();
		Label warningCreate = new Label("");

		albumTextField.setPromptText("Album Name");

		GridPane grid = new GridPane();
		grid.add(albumLabel, 1, 1);
		grid.add(albumTextField, 2, 1);
		grid.add(warningCreate, 1, 3);

		dialog.getDialogPane().setContent(grid);

		ButtonType Ok = new ButtonType("Move", ButtonData.OK_DONE);
		ButtonType cancel = new ButtonType("Cancel", ButtonData.CANCEL_CLOSE);

		dialog.getDialogPane().getButtonTypes().addAll(Ok, cancel);

		Optional<ButtonType> result = dialog.showAndWait();

		String destAlbum = albumTextField.getText();

		List<Album> alb = new ArrayList();

		alb = user.getAlbums();

		for (int i = 0; i < alb.size(); i++) {
			if (alb.get(i).getAlbumName().equals(destAlbum)) {

				String s = p.getLoc();
				alb.get(i).addPhoto(p);

				obsList.remove(p);

				try {
					listOfUsers.write(users);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

				return;
			}
		}

		Alert alert = new Alert(AlertType.ERROR);
		alert.setHeaderText("No such album exists");
		alert.showAndWait();

		try {
			listOfUsers.write(users);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		// System.out.println("movePic method");

	}

	/**
	 * @author Pal Shah
	 * @author Mahima Sharma
	 * 
	 * Creates a Photocell to display all items in listview
	 *
	 */
	private class PhotoCell extends ListCell<Photo> {

		AnchorPane apane = new AnchorPane();
		StackPane spane = new StackPane();
		ImageView imageView = new ImageView();
		Label captionLabel = new Label();

		public PhotoCell() {
			super();

			imageView.setFitWidth(45.0);
			imageView.setFitHeight(45.0);
			imageView.setPreserveRatio(true);

			StackPane.setAlignment(imageView, Pos.CENTER);

			spane.getChildren().add(imageView);

			spane.setPrefHeight(55.0);
			spane.setPrefWidth(45.0);

			AnchorPane.setLeftAnchor(spane, 0.0);

			AnchorPane.setLeftAnchor(captionLabel, 55.0);
			AnchorPane.setTopAnchor(captionLabel, 0.0);

			apane.getChildren().addAll(spane, captionLabel);

			apane.setPrefHeight(55.0);

			captionLabel.setMaxWidth(300.0);

			setGraphic(apane);
		}

		@Override
		public void updateItem(Photo photo, boolean empty) {
			super.updateItem(photo, empty);
			setText(null);
			if (photo == null) {
				imageView.setImage(null);
				captionLabel.setText("");
			}
			if (photo != null) {
				String s = photo.getLoc();
				imageView.setImage(new Image(s));
				captionLabel.setText("Caption: " + photo.getCaption());
				// System.out.println("hereeeeeeeeeeeee");
				photoListView.getSelectionModel().select(0);
			}

		}

	}

	/**
	 * @param e
	 * @throws IOException
	 * Can view photos of an album in slideshow format
	 */
	public void slideShow(ActionEvent e) throws IOException {
		if (photos.isEmpty()) {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setHeaderText("No photos in album to show.");
			alert.showAndWait();
			return;
		}
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/slideshow.fxml"));
		Parent parent = loader.load();

		slideshowController admin = loader.getController();
		Scene adm = new Scene(parent);

		admin.setUser(user);
		admin.setAlbum(album);
		admin.setListOfUsers(users);
		if (stock == true) {
			admin.isStock(true);
		} else {
			admin.isStock(false);
		}

		Stage window = (Stage) ((Node) e.getSource()).getScene().getWindow();

		admin.start(window);
		window.setScene(adm);
		window.show();
	}

	/**
	 * @param album
	 * Sets album to the album passed
	 */
	public void setAlbum(Album album) {
		this.album = album;
	}

	/**
	 * @param user
	 * sets the user
	 */
	public void setUser(User user) {
		this.user = user;
	}

	/**
	 * @param users
	 * sets the list of users
	 */
	public void setListOfUsers(listOfUsers users) {
		this.users = users;
	}

	/**
	 * @param stock
	 * gets the stock value
	 */
	public void isStock(boolean stock) {
		this.stock = stock;
	}

	/**
	 * @param e
	 * @throws IOException
	 * logs out of current scene.
	 */
	public void logout(ActionEvent e) throws IOException {

		// have to make sure to serialize stuff before logging out
		// photos = obsList;
		// listOfUsers.write(users);

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
}
