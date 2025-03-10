package edu.ncsu.csc216.wolf_scheduler.course;

/**
 * Interface for ensuring there are no activity conflicts.
 * @author Samay Bhinge
 */
public interface Conflict {
	/**
	 * Checks for conflict
	 * @param possibleConflictingActivity Activity to check for possible activity conflict
	 * @throws ConflictException for conflicting times
	 */
	void checkConflict(Activity possibleConflictingActivity) throws ConflictException;
}
