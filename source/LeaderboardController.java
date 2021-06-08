import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * Controls the Leaderboard scene.
 * @author Josiah Richards
 * @version 1.0
 */
public class LeaderboardController extends Controller {

	//The format of the title where %s is the map name.
	private static final String TITLE_FORMAT = "Fastest Times - %s";
	
	//FXML UI objects
	@FXML Button btnBack;
	@FXML Label lblTitle;
	@FXML TableView tblScores;
	@FXML TableColumn colRank;
	@FXML TableColumn colName;
	@FXML TableColumn colTime;
	
	@Override
	public void initialize() {
	}

	@Override
	public void manualInitialize() {
		//Setup back button
		btnBack.setOnAction(e -> {
			main.switchToScene(Main.SceneType.LevelMenu);
		});
		
		//Try loading the map name and update the title text to match.
		MapInfo info = null;
		try {
			info = MapInfo.loadMapInfo(main.getGlobalInfo().getCurrentLevelID());
			lblTitle.setText(String.format(TITLE_FORMAT, info.getMapName()));
		} catch (ParsingException e) {
			e.printStackTrace();
			//Go back to the level menu if failed to load.
			main.switchToScene(Main.SceneType.LevelMenu);
		}
		
		if (info != null) {
			//Set the properties for the columns
			colRank.setCellValueFactory(new PropertyValueFactory<>("rank"));
			colName.setCellValueFactory(new PropertyValueFactory<>("name"));
			colTime.setCellValueFactory(new PropertyValueFactory<>("score"));
			
			//Add a row for each high score
			ProfileIDScorePair[] highScores = info.getHighScores();
			for (int i = 0; i < highScores.length; i++) {
				try {
					//Try and get the profile for the high score
					Profile profile = ProfileManager.loadProfile(highScores[i].getProfileID());
					//Format the milliseconds time nicely
					String time = TimerFormatter.formatMilliseconds(highScores[i].getScore());
					//Add the row (i+1 to make the rank start at 1)
					LeaderboardRow newRow = new LeaderboardRow(i+1, profile.getName(), time);
					tblScores.getItems().add(newRow);
				} catch (Exception e) {
					e.printStackTrace();
				}
				
			}
		}
		
	}

}
