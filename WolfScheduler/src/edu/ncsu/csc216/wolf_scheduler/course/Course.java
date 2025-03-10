package edu.ncsu.csc216.wolf_scheduler.course;

/**
 * Program for managing courses
 */
public class Course extends Activity {
	
	/** Minimum length for course name */
	public static final int MIN_NAME_LENGTH = 5;
	/** Maximum length for course name */
	public static final int MAX_NAME_LENGTH = 8;
	/** Minimum letter count within course name */
	public static final int MIN_LETTER_COUNT = 1;
	/** Maximum letter count within course name */
	public static final int MAX_LETTER_COUNT = 4;
	/** Digit count within course name */
	public static final int DIGIT_COUNT = 3;
	/** Length of course section */
	public static final int SECTION_LENGTH = 3;
	/** Minimum number of credits possible for a course */
	public static final int MIN_CREDITS = 1;
	/** Maximum number of credits possible for a course */
	public static final int MAX_CREDITS = 5;
	/** Course's name. */
	private String name;
	/** Course's section. */
	private String section;
	/** Course's credit hours */
	private int credits;
	/** Course's instructor */
	private String instructorId;
	/**
	 * Constructs a Course object with values for all fields
	 * @param name name of course
	 * @param title title of course
	 * @param section course section
	 * @param credits credit hours of course
	 * @param instructorId instructor's unity id
	 * @param meetingDays meeting days for course as series of chars
	 * @param startTime start time of course
	 * @param endTime end time of course
	 */
	public Course(String name, String title, String section, int credits, String instructorId, String meetingDays,
	        int startTime, int endTime) {
        super(title, meetingDays, startTime, endTime);
		setName(name);
	    setSection(section);
	    setCredits(credits);
	    setInstructorId(instructorId);
	}

	/**
	 * Constructs a Course with the given name, title, section, credit hours, instructor id,
	 * and meeting days for courses that are arranged
	 * @param name name of course
	 * @param title title of course
	 * @param section course section
	 * @param credits credit hours of course
	 * @param instructorId instructor's unity id
	 * @param meetingDays meeting days for course as series of chars
	 */
	public Course(String name, String title, String section, int credits, String instructorId, String meetingDays) {
	    this(name, title, section, credits, instructorId, meetingDays, 0, 0);
	}

	/**
	 * Returns the course's name
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Sets the course's name
	 * @param name to be set
	 * @throws IllegalArgumentException if the name parameter is invalid
	 */
	private void setName(String name) {
		//Throw exception if name is null
		if (name == null) {
			throw new IllegalArgumentException("Invalid course name.");
		}
		
		//Throw exception if the name is an empty string
		//Throw exception if the name contains less than 5 or greater than 8 characters
		if (name.length() < MIN_NAME_LENGTH || name.length() > MAX_NAME_LENGTH) {
			throw new IllegalArgumentException("Invalid course name.");
		}
		
		//Check for pattern of L[LLL} NNN
		int letters = 0;
		int digits = 0;
		boolean spaceFound = false;
		for (int i = 0; i < name.length(); i++) {
			if (!spaceFound) {
				if (Character.isLetter(name.charAt(i))) {
					letters++;
				} else if (name.charAt(i) == ' ') {
					spaceFound = true;
				} else {
					throw new IllegalArgumentException("Invalid course name.");
				}
			} else if (spaceFound) {
				if (Character.isDigit(name.charAt(i))) {
					digits++;
				} else {
					throw new IllegalArgumentException("Invalid course name.");
				}
			}
		}
		
		if (letters < MIN_LETTER_COUNT || letters > MAX_LETTER_COUNT) {
			throw new IllegalArgumentException("Invalid course name.");
		}
		
		if (digits != DIGIT_COUNT) {
			throw new IllegalArgumentException("Invalid course name.");
		}
		
		this.name = name;
	}
	
	/**
	 * Returns the course section
	 * @return the section
	 */
	public String getSection() {
		return section;
	}
	
	/**
	 * Sets the course section
	 * @param section to be set
	 * @throws IllegalArgumentException if the section parameter is not three digits
	 */
	public void setSection(String section) {
		//Throw exception if invalid length
		if (section == null || section.length() != 3) {
			throw new IllegalArgumentException("Invalid section.");
		}
		
		//Checking for all digits
		for (int i = 0; i < 3; i++) {
			if (!Character.isDigit(section.charAt(i))) {
				throw new IllegalArgumentException("Invalid section.");
			}
		}
		
		this.section = section;
	}
	
	/**
	 * Returns the course's credits
	 * @return the credits
	 */
	public int getCredits() {
		return credits;
	}
	
	/**
	 * Sets the course's credits
	 * @param credits to be set
	 * @throws IllegalArgumentException for invalid credit value
	 */
	public void setCredits(int credits) {
		//Throws exception if value is not an integer
		try {
			Integer.toString(credits);
		} catch (Exception e) {
			throw new IllegalArgumentException("Invalid credits.");
		}
		//Checks for valid value 
		if (credits < MIN_CREDITS || credits > MAX_CREDITS) {
			throw new IllegalArgumentException("Invalid credits.");
		}
		this.credits = credits;
	}
	
	/**
	 * Returns the instructor's ID
	 * @return the instructor's ID
	 */
	public String getInstructorId() {
		return instructorId;
	}
	
	/**
	 * Sets the instructor's ID
	 * @param instructorId to be set
	 * @throws IllegalArgumentException for invalid Instructor ID
	 */
	public void setInstructorId(String instructorId) {
		//Check for empty or null instructor id
		if ("".equals(instructorId) || instructorId == null) {
			throw new IllegalArgumentException("Invalid instructor id.");
		}
		this.instructorId = instructorId;
	}
	
	/**
	 * Sets meeting days and times of course
	 * @param meetingDays days event will occur
	 * @param startTime start time of event
	 * @param endTime end time of event
	 * @throws IllegalExceptionArgument for invalid days or times
	 */
	@Override
	public void setMeetingDaysAndTime(String meetingDays, int startTime, int endTime) {
		if ("A".equals(meetingDays)) {
			if (startTime != 0 || endTime != 0) {
				throw new IllegalArgumentException("Invalid meeting days and times.");
			}
		} else if (meetingDays != null){
			int m = 0;
			int t = 0;
			int w = 0;
			int h = 0;
			int f = 0;
			
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
				//Check for any other letter
				default:
					throw new IllegalArgumentException("Invalid meeting days and times.");
				}
			}
			
			//Check for duplicates
			if (m > 1 || t > 1 || w > 1 || h > 1 || f > 1) {
				throw new IllegalArgumentException("Invalid meeting days and times.");
			}
		}
		
		super.setMeetingDaysAndTime(meetingDays, startTime, endTime);
	}

	/**
	 * Generates hashcode for courses
	 * @return hashcode value for course
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + credits;
		result = prime * result + ((instructorId == null) ? 0 : instructorId.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((section == null) ? 0 : section.hashCode());
		return result;
	}

	/**
	 * Determines if two courses are equal
	 * @return true if courses are equal, false if any fields are unequal
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Course other = (Course) obj;
		if (credits != other.credits)
			return false;
		if (instructorId == null) {
			if (other.instructorId != null)
				return false;
		} else if (!instructorId.equals(other.instructorId))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (section == null) {
			if (other.section != null)
				return false;
		} else if (!section.equals(other.section))
			return false;
		return true;
	}

	/**
	 * Checks to see if an activity is the same as the provided course
	 * @param activity activity to check for duplicate of
	 * @return true if both are the same course
	 */
	public boolean isDuplicate(Activity activity) {
		try {
			Course other = (Course) activity;
			if (this.getName().equals(other.getName())) {
				return true;
			}
		} catch (Exception e) {
			return false;
		}
		return false;
	}
	
	/**
	 * Returns a comma separated value String of all Course fields.
	 * @return String representation of Course
	 */
	@Override
	public String toString() {
	    if ("A".equals(getMeetingDays())) {
	        return name + "," + getTitle() + "," + section + "," + credits + "," + instructorId + "," + getMeetingDays();
	    }
	    return name + "," + getTitle() + "," + section + "," + credits + "," + instructorId + "," 
	    + getMeetingDays() + "," + getStartTime() + "," + getEndTime(); 
	}

	/**
	 * Retrieve short display array of course
	 * @return brief string array of course
	 */
	@Override
	public String[] getShortDisplayArray() {
		String shortDisplay[] = new String[4];
		shortDisplay[0] = getName();
		shortDisplay[1] = getSection();
		shortDisplay[2] = getTitle();
		shortDisplay[3] = super.getMeetingString();
		return shortDisplay;
	}

	/**
	 * Retrieve long array display of course
	 * @return string array of elements of course
	 */
	@Override
	public String[] getLongDisplayArray() {
		String longDisplay[] = new String[7];
		longDisplay[0] = getName();
		longDisplay[1] = getSection();
		longDisplay[2] = getTitle();
		longDisplay[3] = Integer.toString(getCredits());
		longDisplay[4] = getInstructorId();
		longDisplay[5] = super.getMeetingString();
		longDisplay[6] = "";
		return longDisplay;
	}
}
	