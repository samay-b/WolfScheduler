/**
 * 
 */
package edu.ncsu.csc216.wolf_scheduler.course;

/**
 * Program for managing events
 */
public class Event extends Activity {

	/** Details regarding event */
	private String eventDetails;
	
	/**
	 * Constructor for setting the details and information regarding the event
	 * @param title title of event
	 * @param meetingDays days of the week event will meet
	 * @param startTime start time of event
	 * @param endTime end time of event
	 * @param eventDetails details regarding event
	 */
    public Event(String title, String meetingDays, int startTime, int endTime, String eventDetails) {
        super(title, meetingDays, startTime, endTime);
        setEventDetails(eventDetails);
    }
	
	/**
	 * Retrieves event details
	 * @return the eventDetails
	 */
	public String getEventDetails() {
		return eventDetails;
	}

	/**
	 * Sets event details
	 * @param eventDetails the eventDetails to set
	 */
	public void setEventDetails(String eventDetails) {
		if (eventDetails == null) {
			throw new IllegalArgumentException("Invalid event details.");
		}
		this.eventDetails = eventDetails;
	}

	/**
	 * Sets meeting days and times of event
	 * @param meetingDays days event will occur
	 * @param startTime start time of event
	 * @param endTime end time of event
	 * @throws IllegalExceptionArgument for invalid days or times
	 */
	@Override
	public void setMeetingDaysAndTime(String meetingDays, int startTime, int endTime) {
		if (meetingDays != null ) {
			int m = 0;
			int t = 0;
			int w = 0;
			int h = 0;
			int f = 0;
			int s = 0;
			int u = 0;
			
			for (int i = 0; i < meetingDays.length(); i++) {
				switch (meetingDays.charAt(i)) {
				case 'M':
					m++;
					break;
				case 'T': 
					t++;
					break;
				case 'W':
					w++;
					break;
				case 'H':
					h++;
					break;
				case 'F':
					f++;
					break;
				case 'S':
					s++;
					break;
				case 'U':
					u++;
					break;
				//Check for any other letter
				default:
					throw new IllegalArgumentException("Invalid meeting days and times.");
				}
			}
			
			//Check for duplicates
			if (m > 1 || t > 1 || w > 1 || h > 1 || f > 1 || s > 1 || u > 1) {
				throw new IllegalArgumentException("Invalid meeting days and times.");
			}
		}
		super.setMeetingDaysAndTime(meetingDays, startTime, endTime);
	}
	
	/**
	 * Checks to see if an activity is the same as the provided course
	 * @param activity activity to check for duplicate of
	 * @return true if both are the same course
	 */
	public boolean isDuplicate(Activity activity) {
		try {
			Event other = (Event) activity;
			if (this.getTitle().equals(other.getTitle())) {
				return true;
			}
		} catch (Exception e) {
			return false;
		}
		return false;
	}
	
	/**
	 * Retrieve short display of event
	 * @return brief string array of event
	 */
	@Override
	public String[] getShortDisplayArray() {
		String shortDisplay[] = new String[4];
		shortDisplay[0] = "";
		shortDisplay[1] = "";
		shortDisplay[2] = getTitle();
		shortDisplay[3] = getMeetingString();
		return shortDisplay;
	}

	/**
	 * Retrieve long display of event
	 * @return string array of elements of activity
	 */
	@Override
	public String[] getLongDisplayArray() {
		String longDisplay[] = new String[7];
		longDisplay[0] = "";
		longDisplay[1] = "";
		longDisplay[2] = getTitle();
		longDisplay[3] = "";
		longDisplay[4] = "";
		longDisplay[5] = getMeetingString();
		longDisplay[6] = getEventDetails();
		return longDisplay;
	}

	/**
	 * Updated description of Event activity
	 * @return string description of event
	 */
	@Override
	public String toString() {
		return getTitle() + "," + getMeetingDays() + "," + getStartTime() + "," 
				+ getEndTime() + "," + getEventDetails();
	}
}
