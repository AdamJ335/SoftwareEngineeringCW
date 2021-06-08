/**
 * Formats milliseconds in the form (minutes:seconds:milliseconds)
 * @author Adam Jennings 955770
 * @version 1.0
 */
public class TimerFormatter {
	/**
	 * Formats milliseconds in the form (minutes:seconds:milliseconds).
	 * @param milliseconds The milliseconds to format into a string.
	 * @return The milliseconds formatted as a string in the form minutes:seconds:milliseconds.
	 */
	public static String formatMilliseconds(long milliseconds) {
		//Calculate the number of minutes
	    long minutes = (milliseconds / 60000) % 60;
	    //Calculate the number of seconds
	    long seconds = (milliseconds/ 1000) % 60;
	    //Calculate the number of milliseconds
	    long milli = (milliseconds - seconds*1000) % 1000;

	    return String.format("%02d:%02d:%03d", minutes,seconds,milli);
	}
}
