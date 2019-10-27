package model;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javafx.scene.image.Image;

/**
 * @author Pal Shah
 * @author Mahima Sharma
 *
 */
/**
 * @author User
 *
 */
public class Album implements Serializable{

	/**
	 * 
	 */
	//private static final long serialVersionUID = -4143935150417416554L;
	
	//public static final String storeDir = "dat";
	//public static final String storeFile = "infoAlbum.dat";
	
	private String albumName;
	private List<Photo> photos; 
	private int noOfPhotos;
	private String earliestDate;
	private String oldestDate;
	
	
	/**
	 * @param albumName
	 * constructor that intializes album properties
	 */
	public Album(String albumName) {
		this.albumName = albumName;
		photos = new ArrayList<Photo>();
		noOfPhotos = 0;
		earliestDate = null;
		oldestDate = null;
		
	}
	
	/**
	 * 
	 * overrides tostring method
	 */
	public String toString(){
		return albumName + "";
	}
	
	/**
	 * @param albumName
	 * sets album
	 */
	public void setAlbumName(String albumName) {
		this.albumName = albumName;
	}
	
	/**
	 * @return string 
	 * returns an album name
	 */
	public String getAlbumName() {
		return albumName;
	}
	
	/**
	 * @return int
	 * returns number of photos
	 */
	public int getNoOfPhotos() {
		return noOfPhotos;
	}
	
	/**
	 * @return String
	 * returns string of number of students
	 */
	public String getNoOfPhotosString() {
		return String.valueOf(noOfPhotos);
	}
	
	/**
	 * @param photo
	 * add photo to photo list
	 */
	public void addPhoto(Photo photo) {
		photos.add(photo);
		++noOfPhotos;
		
		Photo temp1 = photos.get(0); //iterator for earliest photo
		Photo temp2 = photos.get(0); //iterator for oldest photo
		
		for(Photo p : photos) {
			if(p.getCal().compareTo(temp1.getCal())>0) 
				temp1= p;
			if(p.getCal().compareTo(temp1.getCal())<0)
				temp2 = p;
			}
		
		Date date1 = temp1.getCal().getTime();
		Date date2 = temp2.getCal().getTime();
		
		SimpleDateFormat sdf = new SimpleDateFormat("MM.dd.yyyy");
		earliestDate = sdf.format(date1);
		oldestDate = sdf.format(date2);
			
	}
	
	/**
	 * @param photo
	 * removes photo from photolist
	 */
	public void removePhoto(Photo photo) {
		photos.remove(photo);
		--noOfPhotos;
		
		if(!photos.isEmpty()) {
			
			Photo temp1 = photos.get(0); //iterator for earliest photo
			Photo temp2 = photos.get(0); //iterator for oldest photo
		
			for(Photo p : photos) {
				if(p.getCal().compareTo(temp1.getCal())>0) 
					temp1= p;
				if(p.getCal().compareTo(temp1.getCal())<0)
					temp2 = p;
			}
		
			Date date1 = temp1.getCal().getTime();
			Date date2 = temp2.getCal().getTime();
		
			SimpleDateFormat sdf = new SimpleDateFormat("MM.dd.yyyy");
			earliestDate = sdf.format(date1);
			oldestDate = sdf.format(date2);
		}
		
	}
	
	/**
	 * @return String
	 * returns the date range in the format of a string
	 */
	public String getDateRange() {
		return oldestDate + "-" + earliestDate;
	}
	
	/**
	 * @return List<Photo>
	 * returns a list of photos for the users
	 */
	public List<Photo> getPhotos(){
		if(albumName.equals("stock")){
			Photo stock1 = new Photo("file:///C:/Users/User/workspace/Photos61/stockPhotos/cat.jpg");
			Photo stock2 = new Photo("file:///C:/Users/User/workspace/Photos61/stockPhotos/cutie.jpg");
			Photo stock3 = new Photo("file:///C:/Users/User/workspace/Photos61/stockPhotos/dog.jpg");
			Photo stock4 = new Photo("file:///C:/Users/User/workspace/Photos61/stockPhotos/lizard.jpg");
			Photo stock5 = new Photo("file:///C:/Users/User/workspace/Photos61/stockPhotos/tiger2.jpg");
			photos.add(stock1);
			photos.add(stock2);
			photos.add(stock3);
			photos.add(stock4);
			photos.add(stock5);
			return photos;
			
			
		}
		else{
			return photos;
		}
	}
//	
//	
//public static void write(List<Album> list) throws IOException {
//		
//		ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(storeDir + File.separator + storeFile));
//		
//		for(Album a: list){
//			oos.writeObject(a);
//		}
//
//		oos.close();
//	
//	}
//	
//	public static List<Album> read(){
//		List<Album> list = new ArrayList<>();
//		
//		ObjectInputStream inputStream = null;
//		
//		try{
//			
//			inputStream = new ObjectInputStream(new FileInputStream(storeDir + File.separator + storeFile));
//			
//			while(true){
//				Album a1 = (Album)inputStream.readObject();
//				list.add(a1);
//			}
//			
//		}catch(EOFException e){
//			return list;
//		}catch (ClassNotFoundException e1){
//			e1.printStackTrace();
//		}catch (IOException e2){
//			e2.printStackTrace();
//		}
//		
//		return list;
//	}
//	
	
	
	
	
	
}
