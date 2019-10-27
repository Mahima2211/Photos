package model;

import java.io.Serializable;


/**
 * @author Pal Shah
 * @author Mahima Sharma
 *
 */
/**
 * @author User
 *
 */
public class Tag implements Serializable{
	
	private String tagType;
	
	/**
	 * @param String
	 * constructor for Tag
	 */
	public Tag(String type){
		tagType = type;
	}
	
	/**
	 * @return String
	 * getting the tag type
	 */
	public String getTagType(){
		return tagType;
	}
	
	/**
	 * @param type
	 * assigning tag type to the string passed
	 */
	public void setTagType(String type){
		tagType = type;
	}
	
	/**
	 * @param type
	 * assigning tag type to the string passed
	 */
	public String toString(){
		return tagType + "";
	}

}
