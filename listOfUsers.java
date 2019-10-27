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

import javafx.stage.Stage;

/**
 * This class maintains a list of users which can be used at the time of login and serialized to maintain the user data 
 * across various invocations of the app. The user list serializes user data to info.dat in dat folder.
 * 
 * @author Mahima Sharma
 * @author Pal Shah
 *
 */
public class listOfUsers implements Serializable{

	/**we use an auto-generated serializeable ID for the same
	 * 
	 */
	private static final long serialVersionUID = 7237442122585083698L;
	
	/*
	 * Now we maintain the folder and the file where all our user info is going to be stored
	 * which can be retrieved when the app is opened at a later time
	 */
	public static final String storeDir = "dat";
	public static final String storeFile = "infoUser.dat";
	
	private List<User> users;
	
	/**
	 * constructor which only creates an instance of a new user list for this class to operate on
	 */
	public listOfUsers(){
		users = new ArrayList<User>();
		users.add(new User("Stock user", "stock"));
	}
	
	/**
	 * list of current users for the last
	 * saved application state
	 * 
	 * @return a list of users which can be null
	 */
	public List<User> getListOfUsers() {
		return users;
	}
	
	/**
	 * @param takes the user object as input
	 * @return void because only adds user to the current list of users
	 */
	public void addUserToList(User u) {
		users.add(u);
	}
	
	/**
	 * @param takes the user object as input
	 * @return void because only adds user to the current list of users
	 */
	public void removeUserFromList(User u) {
		if(users==null) {
			return;
		}
		else {
			users.remove(u);
		}
	}
	
	/**
	 * @param takes the user object as input
	 * @return false if the user is not in the list or the list is null
	 * @return true if the user is in the list
	 */
	public boolean isUserinList(String u) {
		if(users==null)
			return false;
		else {
			for(User u1 :users) {
				if(u1.getUsername().equals(u)){
					return true;
				}
			}
		}
		return false;
	}
	
	/**
	 * @param username
	 * @return User
	 * getting the user by username
	 */
	public User getUserByUsername(String username) {
		  for (User u : users)
		  {
			  if (u.getUsername().equals(username))
				  return u;
		  }
		  return null;
	  }

	
	/**
	 * @return the list of users which are read from the storage serialized file
	 * @throws IOException - exception for serialization
	 * @throws ClassNotFoundException - exception for serialization
	 */
	public static listOfUsers read() throws IOException, ClassNotFoundException{
		listOfUsers listu = null;
		ObjectInputStream ois = null;
		try{
			ois = new ObjectInputStream(new FileInputStream(storeDir + File.separator + storeFile));
		
		listu = (listOfUsers)ois.readObject();
		ois.close();
		}
		catch(EOFException e){
			return listu;
		}
		return listu;
	}
	
	/**
	 * @param takes a list of users which is serialized into the info.dat file and overwrites anything there
	 * @throws IOException exception for serialization
	 */
	public static void write (listOfUsers listu) throws IOException {
		   ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(storeDir + File.separator + storeFile));
		   oos.writeObject(listu);
		   oos.close();
	   }


	
}
