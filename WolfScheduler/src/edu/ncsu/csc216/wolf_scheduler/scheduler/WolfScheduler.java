package edu.ncsu.csc216.wolf_scheduler.scheduler;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import edu.ncsu.csc216.wolf_scheduler.course.Activity;
import edu.ncsu.csc216.wolf_scheduler.course.ConflictException;
import edu.ncsu.csc216.wolf_scheduler.course.Course;
import edu.ncsu.csc216.wolf_scheduler.course.Event;
import edu.ncsu.csc216.wolf_scheduler.io.ActivityRecordIO;
import edu.ncsu.csc216.wolf_scheduler.io.CourseRecordIO;

/**
 * Class for creating a schedule given a catalog of courses
 * @author Samay Bhinge
 */
public class WolfScheduler {
	
	/** Catalog of courses */
	private ArrayList<Course> catalog;
	/** Individual schedule for a student */
	private ArrayList<Activity> schedule;
	/** Title for schedule */
	private String title;

	/**
	 * Constructor for storing all possible courses into a catalog, and storing the client's
	 * schedule and schedule title
	 * @param filename to read all courses
	 */
	public WolfScheduler(String filename) {
		this.schedule = new ArrayList<Activity>();
		this.title = "My Schedule";
		try {
			this.catalog = CourseRecordIO.readCourseRecords(filename);
		} catch (FileNotFoundException e) {
			throw new IllegalArgumentException("Cannot read file.");
		}
	}	

	
	/**
	 * Exports the schedule into an external file
	 * @param filename name of file to create
	 * @throws IllegalArgumentException if file cannot be exported
	 */
	public void exportSchedule(String filename) {
		try {
			ActivityRecordIO.writeActivityRecords(filename, schedule);
		} catch (IOException e) {
			throw new IllegalArgumentException("The file cannot be saved.");
		}
	}

	/**
	 * Retrieves a 2D array version of the courses in student's schedule
	 * @return brief overview of courses in schedule
	 */
	public String[][] getScheduledActivities() {
        String [][] scheduledArray = new String[schedule.size()][4];
        for (int i = 0; i < schedule.size(); i++) {
            Activity c = schedule.get(i);
            scheduledArray[i] = c.getShortDisplayArray();
        }
        return scheduledArray;
	}

	/**
	 * Sets user's schedule title to anything they wish
	 * @param title title to be set for schedule
	 */
	public void setScheduleTitle(String title) {
		if (title == null) {
			throw new IllegalArgumentException("Title cannot be null.");
		}
		this.title = title;
	}

	/**
	 * Retrieve the schedule title
	 * @return title of schedule
	 */
	public String getScheduleTitle() {
		return this.title;
	}

	/**
	 * Retrieve 2D array of all courses in schedule, with more information available
	 * @return a more concise list of schedule courses
	 */
	public String[][] getFullScheduledActivities() {
        String [][] scheduledArray = new String[schedule.size()][7];
        for (int i = 0; i < schedule.size(); i++) {
            Activity c = schedule.get(i);
            scheduledArray[i] = c.getLongDisplayArray();
        }
        return scheduledArray;
	}

	/**
	 * Include a course in the schedule if valid and not already in it
	 * @param name name of course to include
	 * @param section section of course to include
	 * @return true if course can be successfully included in schedule
	 * @throws IllegalArgumentException if course already exists in schedule
	 */
	public boolean addCourseToSchedule(String name, String section){
		// Check to see that desired course is available in catalog
		if (getCourseFromCatalog(name, section) == null) {
			return false;
		}
		Course courseToAdd = getCourseFromCatalog(name, section);
		for (Activity a : schedule) {
			try {
				courseToAdd.checkConflict(a);
			}
			catch (ConflictException e) {
				throw new IllegalArgumentException("The course cannot be added due to a conflict.");
			}
			if (courseToAdd.isDuplicate(a)) {
				throw new IllegalArgumentException("You are already enrolled in " + name);
			}
		}
		schedule.add(schedule.size(), courseToAdd);
		return true;
	}

	/**
	 * Include an event in the schedule if valid and not already in it
	 * @param eventTitle title of event
	 * @param eventMeetingDays meeting days for event
	 * @param eventStartTime start times of event
	 * @param eventEndTime end times of event
	 * @param eventDetails details regarding event
	 * @throws IllegalArgumentException if event already exists in schedule
	 */
	public void addEventToSchedule(String eventTitle, String eventMeetingDays, int eventStartTime, int eventEndTime, String eventDetails){
		Event newEvent = new Event(eventTitle, eventMeetingDays, eventStartTime, eventEndTime, eventDetails);
		for (Activity a: schedule) {
			// Check to see if such event already exists
			if (newEvent.isDuplicate(a)) {
				throw new IllegalArgumentException("You have already created an event called " + newEvent.getTitle());
			}
			// Check for conflict now that event is validated
			try {
				a.checkConflict(newEvent);
			} catch (ConflictException e) {
				throw new IllegalArgumentException("The event cannot be added due to a conflict.");
			}
		}
		schedule.add(newEvent);
	}	
	
	/**
	 * Retrieves full catalog of courses into a 2D array
	 * @return array of courses with name, section, and title
	 */
	public String[][] getCourseCatalog() {
        String [][] catalogArray = new String[catalog.size()][3];
        for (int i = 0; i < catalog.size(); i++) {
            Course c = catalog.get(i);
            catalogArray[i] = c.getShortDisplayArray();
        }
        return catalogArray;
	}

	/**
	 * Retrieve a course from the catalog
	 * @param name name of course to search for
	 * @param section section of course to search for
	 * @return the course that user requested for
	 */
	public Course getCourseFromCatalog(String name, String section) {
		for (int i = 0; i < catalog.size(); i++) {
			if (name.equals(catalog.get(i).getName()) 
				&& section.equals(catalog.get(i).getSection())) {
					return catalog.get(i);
			}
		}
		return null;
	}

	/**
	 * Remove an already added course from schedule
	 * @param idx TODO
	 * @return true if course is valid and is removed, false otherwise
	 */
	public boolean removeActivityFromSchedule(int idx) {
		try {
			schedule.remove(idx);		
		} catch (IndexOutOfBoundsException e) {
			return false;		
		}
		return true;
	}

	/**
	 * Creates an empty ArrayList for schedule
	 */
	public void resetSchedule() {
		schedule.clear();
	}

}
