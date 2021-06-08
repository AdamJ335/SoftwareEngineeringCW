import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

/**
 * Controls the Menu scene.
 * @author Josiah Richards
 * @version 1.0
 */
public class MenuController extends Controller {
	
	//The message to display to the user where %s is the current profile name.
	private static final String WELCOME_MESSAGE = "Welcome %s! To play and view leaderboards, "
										+ "click Levels! For everything profile, click Profiles!";
	
	//FXML UI Objects
	@FXML Button btnLevels;
	@FXML Button btnProfiles;
	@FXML Label lblWelcome;
	@FXML Label lblMsgOfDay;
	
	@Override
	public void initialize() {
	}

	@Override
	public void manualInitialize() {
		//Try loading the current profile.
		Profile curProfile;
		try {
			curProfile = ProfileManager.loadProfile(main.getGlobalInfo().getSelectedProfileID());
		} catch (ParsingException e) {
			curProfile = null;
			e.printStackTrace();
		}
		if (curProfile != null) {
			lblWelcome.setText(String.format(WELCOME_MESSAGE, curProfile.getName()));
		} else {
			//If we couldnt load the profile switch back to the select profile scene.
			main.switchToScene(Main.SceneType.SelectLevel);
		}
		
		//Set up the Levels button to take them to the Select Level scene.
		btnLevels.setOnAction(e -> {
			main.switchToScene(Main.SceneType.SelectLevel);
		});
		
		//Set up the Profiles button to take them to the Select Profile scene.
		btnProfiles.setOnAction(e -> {
			main.switchToScene(Main.SceneType.SelectProfile);
		});
		
		//Try and get the message of the day.
		try {
			String message = MessageOfTheDay.getMessage();
			lblMsgOfDay.setText(String.format("\"%s\"",message)); 
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}
