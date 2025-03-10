package edu.ncsu.csc216.wolf_scheduler.course;

/** 
 * Superclass Activity for handling courses and events
 * @author Samay Bhinge
 */
public abstract class Activity implements Conflict {
	/** Hours that make up a day */
	public static final int UPPER_HOURS = 24;
	/** Minutes that make up an hour */
	public static final int UPPER_MINUTES = 60;
	/** PM boundary for standard time */
	public static final int PM_BOUND = 12;
	/** Course's title. */
	private String title;
	/** Course's meeting days */
	private String meetingDays;
	/** Course's starting time */
	private int startTime;
	/** Course's ending time */
	private int endTime;

	/**
	 * Constructor for setting title and timings of courses and events
	 * @param title title of event or course
	 * @param meetingDays days during week which activity will meet
	 * @param startTime start time of activity
	 * @param endTime end time of activity
	 */
    public Activity(String title, String meetingDays, int startTime, int endTime) {
        super();
        setTitle(title);
        setMeetingDaysAndTime(meetingDays, startTime, endTime);
    }

    /**
     * Method for comparing two activities and determine if they're duplicates
     * @param activity activity to compare to
     * @return true if duplicates
     */
    public abstract boolean isDuplicate(Activity activity);
    
	/**
	 * Returns the course's title
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/** 
	 * Sets the course's title
	 * @param title to be set
	 * @throws IllegalArgumentException if the title parameter is empty or null
	 */
	public void setTitle(String title) {
		if ("".equals(title) || title == null) {
			throw new IllegalArgumentException("Invalid title.");
		}
		this.title = title;
	}

	/**
	 * Returns the course's meeting days
	 * @return the meeting days
	 */
	public String getMeetingDays() {
		return meetingDays;
	}

	/** Returns the course's start time
	 * @return the start time
	 */
	public int getStartTime() {
		return startTime;
	}

	/**
	 * Returns the course's end time
	 * @return the end time
	 */
	public int getEndTime() {
		return endTime;
	}

	/**
	 * Sets the course's meeting days, start and end times
	 * @param meetingDays meeting days to be set
	 * @param startTime start time to be set
	 * @param endTime end time to be set
	 * @throws IllegalArgumentException invalid meeting days and times
	 */
	public void setMeetingDaysAndTime(String meetingDays, int startTime, int endTime) {
		if (meetingDays == null || "".equals(meetingDays)) {
			throw new IllegalArgumentException("Invalid meeting days and times.");
		}
			
		int startHour = startTime / 100;
		int startMin = startTime % 100;
		int endHour = endTime / 100;
		int endMin = endTime % 100;
		
		//Check for valid times
		if (startHour < 0 || startHour >= UPPER_HOURS) {
			throw new IllegalArgumentException("Invalid meeting days and times.");
		} else if (startMin < 0 || startMin >= UPPER_MINUTES) {
			throw new IllegalArgumentException("Invalid meeting days and times.");
		} else if (endHour < 0 || endHour >= UPPER_HOURS) {
			throw new IllegalArgumentException("Invalid meeting days and times.");
		} else if (endMin < 0 || endMin >= UPPER_MINUTES) {
			throw new IllegalArgumentException("Invalid meeting days and times.");
		}
		
		//Check to see that end time is after start time
		if (!"A".equals(meetingDays)) {
			if (endHour < startHour) {
				throw new IllegalArgumentException("Invalid meeting days and times.");
			} else if (endHour == startHour && endMin <= startMin) {
				throw new IllegalArgumentException("Invalid meeting days and times.");
			}
		}
		
		this.meetingDays = meetingDays;
		this.startTime = startTime;
		this.endTime = endTime;
	}

	/**
	 * Retrieve standard time from inputted time
	 * @param time Time of start or end of class
	 * @return time in standard format
	 */
	private String getTimeString(int time) {
		int hour = time / 100;
		int min = time % 100;
		String period;
		String minString = "";
		
		if (hour >= PM_BOUND) {
			period = "PM";
		} else {
			period = "AM";
		}
		if (hour > PM_BOUND) {
			hour -= 12;
		}
		if (min < 10) {
			minString = "0" + min;
		} else {
			minString = Integer.toString(min);
		}
		String timeString = Integer.toString(hour) + ":" + minString + period;
		return timeString;
	}

	/**
	 * Throws ConflictException if two activities overlap
	 * @param possibleConflictingActivity activity to check for conflict
	 */
	@Override
	public void checkConflict(Activity possibleConflictingActivity) throws ConflictException {
		String thisMeeting = this.getMeetingDays();
		String thatMeeting = possibleConflictingActivity.getMeetingDays();
		if (!"A".equals(thisMeeting) || !"A".equals(thatMeeting)) {
			boolean overlapDays = false;
			// Check for any overlapping days
			for (int i = 0; i < thisMeeting.length(); i++) {
				for (int j = 0; j < thatMeeting.length(); j++) {
					if (thisMeeting.charAt(i) == thatMeeting.charAt(j)) {
						overlapDays = true;
						break;
					}
				}
			}
			// Check for overlapping times if days overlap
			if (overlapDays && this.startTime >= possibleConflictingActivity.getStartTime() 
				&& this.startTime <= possibleConflictingActivity.getEndTime() ||
				overlapDays && possibleConflictingActivity.getStartTime() >= this.startTime 
				&& possibleConflictingActivity.getStartTime() <= this.endTime) {
					throw new ConflictException();
			}
		}
	}

	/**
	 * Retrieve course's weekly timings in a string description
	 * @return Description of course's days and times of session
	 */
	public String getMeetingString() {
		String meetingToString;
		if ("A".equals(meetingDays)) {
			meetingToString = "Arranged";
		} else {
			String officialStart = getTimeString(startTime);
			String officialEnd = getTimeString(endTime);
			meetingToString = meetingDays + " " + officialStart + "-" + officialEnd;
		}
		return meetingToString;
	}

	/**
	 * Generates a hashcode for activity
	 * @return hashcode of activity
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + endTime;
		result = prime * result + ((meetingDays == null) ? 0 : meetingDays.hashCode());
		result = prime * result + startTime;
		result = prime * result + ((title == null) ? 0 : title.hashCode());
		return result;
	}

	/** Populates the rows of the course catalog and student schedule 
	 * @return short display array of student schedule
	 */
	public abstract String[] getShortDisplayArray();
	
	/** Displays the final schedule 
	 * @return final schedule
	 */
	public abstract String[] getLongDisplayArray();
	
	/**
	 * Determine if two activities are equal
	 * @param obj object to compare to
	 * @return true if activities are equal
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Activity other = (Activity) obj;
		if (endTime != other.endTime)
			return false;
		if (meetingDays == null) {
			if (other.meetingDays != null)
				return false;
		} else if (!meetingDays.equals(other.meetingDays))
			return false;
		if (startTime != other.startTime)
			return false;
		if (title == null) {
			if (other.title != null)
				return false;
		} else if (!title.equals(other.title))
			return false;
		return true;
	}

	
}