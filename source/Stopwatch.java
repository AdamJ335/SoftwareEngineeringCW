/**
 * Measures elapsed time in miliseconds.
 * @author Josiah Richards
 * @version 1.0
 */
public class Stopwatch {
	
	//The total elapsed time of this stopwatch.
	private long elapsedTime;
	
	private long lastSystemStartTime;
	//If the stopwatch had been started
	private boolean timing = false;
	
	/**
	 * Create a new Stopwatch with 0 milliseconds elapsed.
	 * Doesn't automatically start the stopwatch!
	 */
	public Stopwatch() {
		elapsedTime = 0L;
		timing = false;
	}
	
	/**
	 * Create a new Stopwatch with given amount of
	 * milliseconds already elapsed.
	 * Doesn't automatically start the stopwatch!
	 * @param elapsedTime The time that has elapsed since the stopwatch started
	 */
	public Stopwatch(long elapsedTime) {
		this.elapsedTime = elapsedTime;
		timing = false;
	}
	
	/**
	 * Start counting elapsed milliseconds.
	 */
	public void start() {
		lastSystemStartTime = System.currentTimeMillis();
		timing = true;
	}
	
	/**
	 * Pausing/stops the stopwatch, updating the
	 * elapsed time since the stopwatch was started.
	 */
	public void pause() {
		//Update the elapsed time
		getElapsedTime();
		timing = false;
	}
	
	/**
	 * Resets the stopwatch so that it's no longer
	 * timing and that 0 milliseconds have elapsed.
	 */
	public void reset() {
		elapsedTime = 0L;
		timing = false;
	}
	
	/**
	 * Resets the stopwatch and then starts it again.
	 */
	public void restart() {
		reset();
		start();
	}
	
	/**
	 * Gets the time elapsed from when the timer was started and paused +
	 * any elapsed time before that.
	 * (Use pause first to update the elapsed time).
	 * @return The elapsed time.
	 */
	public long getElapsedTime() {
		//if the stopwatch is still timing we need to update the elapsed time
		if (timing) {
			long curTime = System.currentTimeMillis();
			//Add how much time has passed
			elapsedTime += (curTime - lastSystemStartTime);
			//Start the stopwatch from this new point in time
			start();
		}
		return elapsedTime;
	}
	
	/**
	 * Forces the elapsed time to be a value.
	 * @param elapsedTime The new value for elapsedTime
	 */
	public void setElapsedTime(long elapsedTime) {
		this.elapsedTime = elapsedTime;
	}
}
