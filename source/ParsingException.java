/**
 * A custom exception for when something goes wrong while parsing data.
 * @author Josiah Richards
 * @version 1.0
 */
public class ParsingException extends Exception{

	//Exceptions require a version ID
	private static final long serialVersionUID = 1L;

	/**
     * Creates a ParsingException.
     * @param errorMsg The error message to display with this exception
     */
	public ParsingException(String errorMsg) {
        super(errorMsg);
    }
}
