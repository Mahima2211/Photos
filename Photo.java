package model;

import java.io.File;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

//import javafx.scene.image.Image;

/**
 * @author Pal Shah
 * @author Mahima Sharma
 *
 */
public class Photo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6955723612371190680L;

	// private Image image;
	private String location;
	private String caption;
	private List<Tag> tags;
	private Calendar cal;

	// comment out later
	// public Photo() {
	// caption = ""; //user can choose not to have a caption for a photo
	// cal = Calendar.getInstance();
	// cal.set(Calendar.MILLISECOND, 0);
	// tags = new ArrayList<Tag>();
	//
	// }
	//

	/**
	 * @param String
	 * contructor which takes in the location of the photo and initializes photo to default values that need to be used
	 */
	public Photo(String s) {
		// System.out.println("photo");
		caption = ""; // if not caption then assign an empty string to caption
						// in the constructor
		cal = Calendar.getInstance();
		cal.set(Calendar.MILLISECOND, 0);
		tags = new ArrayList<Tag>();
		location = s;
		// File file = new File(s);
		// image = new Image(file.toURI().toString());
	}

	// temporary constructor --- delete later
	// public Photo(String str){
	// caption = str;
	// }

	/**
	 * @return calendar
	 * returns calendar instance of date the photo was modified
	 */
	public Calendar getCal() {
		return cal;
	}

	// public String toString(){
	// return caption;
	//
	// }

	// public Image getImage() {
	// File file = new File(location);
	// Image image = new Image(file.toURI().toString());
	// return image;
	// }

	/**
	 * @return String
	 * returns string location of photo
	 */
	public String getLoc() {
		return location;
	}

	/**
	 * @return String
	 * gets caption associated with photo
	 */
	public String getCaption() {
		return caption;
	}

	// public String getDateTime(){
	// return cal + "";
	// }

	/**
	 * @return Date
	 * returns the date the photo was taken/modified
	 */
	public String getDateTime() {
		String strdate = null;

		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");

		if (cal != null) {
			strdate = sdf.format(cal.getTime());
		}
		return strdate;
	}

	/**
	 * @param String
	 * takes in the caption of the photo as a string
	 */
	public void setCaption(String caption) {
		this.caption = caption;
	}

	
	/**
	 * @param fromDate
	 * @param toDate
	 * @return date
	 * checks if date is within range
	 */
	public boolean isWithinDateRange(LocalDate fromDate, LocalDate toDate) {
		LocalDate date = cal.getTime().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		
		return date.isBefore(toDate) && date.isAfter(fromDate) || date.equals(fromDate) || date.equals(toDate);
	}

}
