package model;

import java.io.Serializable;

/**
 * @author Mahima Sharma
 * @author Pal Shah
 * 
 * maintains a tagValue class for the tag value pairs for the cuurrent user
 *
 */
public class TagValue implements Serializable {
	
	private String tag;
	private String value;
	
	/**
	 * @param tag
	 * @param value
	 * takes the string value of the type and the string and create a tag object
	 */
	public TagValue(String tag, String value){
		this.tag = tag;
		this.value = value;
	}
	
	/**
	 * @return the string element and the tag type part of it
	 */
	public String getTag(){
		return tag;
	}
	
	/**
	 * @return the value part of the current tag object
	 */
	public String getValue(){
		return value;
	}
	
	/**
	 * @param takes the tag type as a string and sets it to the global tag type object
	 */
	public void setTag(String tag){
		this.tag = tag;
	}
	
	/**
	 * @param takes the value part of the tag object and sets it to the global variable
	 */
	public void setValue(String value){
		this.value = value;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString(){
		return tag + " , " + value; 
	}
	
	
	

}
