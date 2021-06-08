/**
 * A controller for a UI
 * @author Josiah Richards
 * @version 1.1
 */
public abstract class Controller {
	
	//The Main controlling the UI this controller is for.
	protected Main main;
	
	/**
	 * Sets the reference to the Main class.
	 */
	public void setMain(Main main){
		this.main = main;
	}
	
	/**
	 * The initialize called automatically by javafx.
	 */
	public abstract void initialize();
	
	/**
	 * An initialize that we call manually a bit later than the automatic 
	 * javafx initialize when all the variables are ready.
	 */
	public abstract void manualInitialize();
}
