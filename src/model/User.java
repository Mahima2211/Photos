package model;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Mahima Sharma
 * @author Pal Shah
 * this is the model java file which handles the user and implements serializable through the list of users
 *
 */
public class User implements Serializable{

	private static final long serialVersionUID = 8503635800214898388L;
	
	public static final String storeDir = "dat";
	public static final String storeFile = "infoUser.dat";

	private String fullName;
	private String username;
	private List<Album> albums;
	private List<Tag> tagTypes;
	private List<TagValue> tagValue;
	
	/**
	 * @param fullName name of user
	 * @param username username
	 * creates a user object based on given parameters
	 */
	public User(String fullName, String username) {
		this.fullName = fullName;
		this.username = username;
		albums = new ArrayList<Album>();
		tagTypes = new ArrayList<Tag>();
		tagTypes.add(new Tag("location"));
		tagTypes.add(new Tag("people"));
		tagValue = new ArrayList<TagValue>();
		
		tagValue.add(new TagValue("person","pal"));
	}
	
	/**
	 * @return list of tag values for the current user based onw ahteevr he is manipulated
	 * also adds the predefined tags
	 */
	public List<TagValue> getTagValueList(){
		//System.out.println(tagValue.get(0) + "---------------");
		return tagValue;
	}
	
	/**
	 * @param takes the tagValue list for the current user as a paramter
	 * and sets the global variable to the set one
	 */
	public void setTagValueList(List<TagValue>tagValueList){
		tagValue = tagValueList;
	}
	
	
	
	/**
	 * @return the list of tag types for the current user
	 * including the predefined ones
	 */
	public List<Tag> getTagList(){
		return tagTypes;
	}
	
	/**
	 * @param takes the tagsList as parameter and sets it to the user;s global variables
	 */
	public void setTagList(List<Tag>tagList){
		tagTypes = tagList;
	}
	
	/**
	 * @return the username of the user
	 */
	public String getUsername() {
		return username;
	}
	
	/**
	 * @return the name of the User as string
	 */
	public String getName() {
		return fullName;
	}
	
    /**
     * @param takes the name of the user as parameter and sets it to the global var
     */
    public void setName(String Name) {
        this.fullName = Name;
    }
    
    /**
     * @param takes the username of the user as parameter and sets it to the global var
     */
    public void setUsername(String username) {
        this.username = username;
    }
    
    /**
     * @param takes the list of albums for the current user as a parameter
     */
    public void setAlbums(List<Album> albums) {
    	this.albums = albums;
    }
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString(){
		return fullName + "";
	}
	
	/**
	 * @return the current list of albums for the current user
	 */
	public List<Album> getAlbums() {
		if(username.equals("stock")){
			albums.add(new Album("stock"));
			return albums;
		}
		else{
			return albums;
		}
	}
	
	/**
	 * @param removes the album from the list of albums
	 */
	public void removeAlbum(Album a) {
		albums.remove(a);
	}
	
/**
 * @param list
 * @throws IOException
 * serializes and writes the list of users to the ulist in the user
 */
public static void write(List<User> list) throws IOException {
		
		ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(storeDir + File.separator + storeFile));
		
		for(User u: list){
			oos.writeObject(u);
		}

		oos.close();
	
	}
	
	/**
	 * @return to read and serialize the user class and read it for the app calling it
	 */
	public static List<User> read(){
		List<User> list = new ArrayList<>();
		
		ObjectInputStream inputStream = null;
		
		try{
			
			inputStream = new ObjectInputStream(new FileInputStream(storeDir + File.separator + storeFile));
			
			while(true){
				User u1 = (User)inputStream.readObject();
				list.add(u1);
			}
			
		}catch(EOFException e){
			return list;
		}catch (ClassNotFoundException e1){
			e1.printStackTrace();
		}catch (IOException e2){
			e2.printStackTrace();
		}
		
		return list;
	}
	

}
