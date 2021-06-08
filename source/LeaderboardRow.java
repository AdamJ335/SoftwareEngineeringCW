/**
 * A row in the leaderboard.
 * @author Josiah Richards
 * @version 1.0
 */
public class LeaderboardRow {
	//The rank 1-3 of this row
	private int rank;
	//The name of the profile that achieved this score.
	private String name;
	//The score the profile achieved(time taken in milliseconds).
	private String score;
	
	/**
	 * Creates a new LeaderboardRow object and sets its 3 attributes.
	 * @param rank The rank attribute for this row.
	 * @param name The name attribute for this row.
	 * @param score The score(time taken in milliseconds) attribute for this row.
	 */
	public LeaderboardRow(int rank, String name, String score) {
		this.rank = rank;
		this.name = name;
		this.score = score;
	}
	
	/**
	 * Gets the value of rank.
	 * @return The value of rank.
	 */
	public int getRank() {
		return rank;
	}
	
	/**
	 * Gets the value of name.
	 * @return The value of name.
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Gets the value of score.
	 * @return The value of score.
	 */
	public String getScore() {
		return score;
	}
}
