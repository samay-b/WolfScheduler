/**
 * 
 */
package edu.ncsu.csc216.wolf_scheduler.course;

/**
 * Checked exception to throw when two activities have conflicting times
 * @author Samay Bhinge
 */
public class ConflictException extends Exception {

	/**
	 * ConflictException with custom message
	 * @param message custom message to implement
	 */
	public ConflictException(String message) {
		super(message);
	}
	
	/**
	 * ConflictException with default message
	 */
	public ConflictException() {
		this("Schedule conflict.");
	}

	/**
	 * ID used for serialization
	 */
	private static final long serialVersionUID = 1L;

}
