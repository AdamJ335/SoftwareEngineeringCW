import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * This class retrieves the message of the day.
 * @author Daniel Miles 973755
 * @version 1.2
 */
public class MessageOfTheDay {
	
	//The URL to get the puzzle code from.
	private static final String PUZZLE_URL = "http://cswebcat.swan.ac.uk/puzzle";
	//The URL to submit our solution in return for the message of the day.
	private static final String SOLUTION_URL_FORMAT = "http://cswebcat.swan.ac.uk/message?solution=%s";
	
	/**
	 * Sends a GET request and returns the response as a string.
	 * @param stringURL The get request URL as a string.
	 * @return The string response from the request.
	 * @throws IOException If the response couldn't be read.
	 * @throws MalformedURLException If the given string request URL is invalid.
	 */
	public static String sendGETRequest(String stringURL) throws IOException, MalformedURLException {
	 	//Send GET request.
		URL url = new URL(stringURL);
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setRequestMethod("GET");
		
		//Read response.
		BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
		String inputLine = null;
		String content = "";
		while ((inputLine = in.readLine()) != null) {
			content += inputLine;
		}
		in.close();
		connection.disconnect();
		
		return content;
	}
	
	/**
	 * Solves the puzzle and returns the solution.
	 * @param puzzleCode The puzzle code to decode.
	 * @return The solution to the puzzle.
	 */
	private static String getPuzzleSolution(String puzzleCode) {
		String solution = "";
		for (int i = 0; i < puzzleCode.length(); i++){
			int temp = 0;
			if ((i % 2) == 1){
				temp = ((int) puzzleCode.charAt(i) - 1);
				if (temp < 65) {
					temp = 90;
				}
				solution += (char) temp;
			} else {
				temp = ((int) puzzleCode.charAt(i) + 1);
				if (temp > 90) {
					temp = 65;
				}
				solution += (char) temp;
			}
		}
		return solution;
	}

	/**
	 * Gets the message of the day.
	 * @return Message of the day.
	 * @throws MalformedURLException If one of the URLs(PUZZLE_URL or SOLUTION_URL_FORMAT) were invalid. 
	 * @throws IOException If one of the responses couldn't be read
	 */
	public static String getMessage() throws IOException, MalformedURLException {
		String puzzleCode = sendGETRequest(PUZZLE_URL);
		String solutionURL = String.format(SOLUTION_URL_FORMAT, getPuzzleSolution(puzzleCode));
		return sendGETRequest(solutionURL);
	}
}
