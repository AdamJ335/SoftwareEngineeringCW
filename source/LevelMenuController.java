import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

/**
 * Controls the Level Menu scene.
 * @author Josiah Richards
 * @version 1.0
 */
public class LevelMenuController extends Controller {
	//FXML UI objects
	@FXML Button btnNew;
	@FXML Button btnContinue;
	@FXML Button btnLeaderboard;
	@FXML Button btnBack;
	@FXML Label lblLvlName;
	
	@Override
	public void initialize() {
	}

	@Override
	public void manualInitialize() {
		//Tries to load the current level info
		int curLevelID = main.getGlobalInfo().getCurrentLevelID();
		try {
			MapInfo mapInfo = MapInfo.loadMapInfo(curLevelID);
			//Update the level name in the UI.
			lblLvlName.setText(mapInfo.getMapName());
		} catch (ParsingException e) {
			e.printStackTrace();
		}
		
		//Set up the back button to go back to Select Level.
		btnBack.setOnAction(e -> {
			main.switchToScene(Main.SceneType.SelectLevel);
		});
		
		//Set up the new button to start a new game.
		btnNew.setOnAction(e -> {
			main.getGlobalInfo().setContinueLevel(false);
			main.switchToScene(Main.SceneType.Level);
		});
		
		//If a save file exists, enable the continue button
		if (FileManager.fileExists(FileManager.getSavedMapFile(
									main.getGlobalInfo().getSelectedProfileID(), curLevelID))) {
			btnContinue.setDisable(false);
			//Set up the continue button to continue the level.
			btnContinue.setOnAction(e -> {
				main.getGlobalInfo().setContinueLevel(true);
				main.switchToScene(Main.SceneType.Level);
			});
		}
		
		//Set up the leaderboard button to take you to the leaderboard.
		btnLeaderboard.setOnAction(e -> {
			main.switchToScene(Main.SceneType.Leaderboard);
		});
		//Set up the back button to take you to the select level scene.
		btnBack.setOnAction(e -> {
			main.switchToScene(Main.SceneType.SelectLevel);
		});
	}
}
