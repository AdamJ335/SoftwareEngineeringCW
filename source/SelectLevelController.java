import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;

/**
 * Controls the Select Level scene.
 * @author Josiah Richards
 * @version 2.0
 */
public class SelectLevelController extends Controller {
	
	//Level container generation properties
	private static double CONTAINER_HEIGHT = 160d;
	private static double GAP_BETWEEN_CONTAINERS = 10d;
	private static String CONTAINER_STYLE = "-fx-border-color: black; -fx-background-color: #f6fff4;";
	
	//Level name label generation properties
	private static double LABEL_FONT_SIZE = 50d;
	private static double LABEL_LEFT_MARGIN = 100d;
	private static String LABEL_STYLE = "-fx-text-fill: #0a3603;";
	
	//Level button generation properties
	private static String BUTTON_TEXT = "Select";
	private static double BUTTON_FONT_SIZE = 50d;
	private static double BUTTON_VERTICAL_MARGIN= 30d;
	private static double BUTTON_HORIZONTAL_R_MARGIN= 200d;
	private static String BUTTON_STYLE = "-fx-text-fill: white;"
									+ " -fx-background-color: red;"
									+ " -fx-border-color: black;";
	
	//FXML UI Objects
	@FXML ScrollPane scrollPane;
	@FXML AnchorPane scrollPaneAnchor;
	@FXML Button btnBack;
	
	@Override
	public void initialize() {
	}

	@Override
	public void manualInitialize() {
		
		//Disable horizontal scrolling
		scrollPane.setHmax(0);
		
		//Try loading the current profile
		try {
			Profile curProfile = ProfileManager.loadProfile(main.getGlobalInfo().getSelectedProfileID());
			
			//Create a pane for each level, up to the highest level the profile has reached,
			//(plus 1 so they can play the next level)
			int curY = 0;
			for (int mapID = 1; mapID <= curProfile.getHighestLevel()+1; mapID++) {
				try {
					MapInfo mapInfo = MapInfo.loadMapInfo(mapID);
					AnchorPane levelContainer = createLevelPane(mapInfo, curY);
					scrollPaneAnchor.getChildren().add(levelContainer);
					
					curY += CONTAINER_HEIGHT + GAP_BETWEEN_CONTAINERS;
				} catch (ParsingException e) {
					
				}
			}
		} catch (ParsingException e) {
			//Couldn't read profile, go back to select profile
			main.switchToScene(Main.SceneType.SelectProfile);
		}
		
		//Set up back button
		btnBack.setOnAction(e -> {
			main.switchToScene(Main.SceneType.Menu);
		});
	}
	
	/**
	 * Creates an anchor pane consisting of a label and button
	 * for a given map info.
	 * @param mapInfo The MapInfo this pane is for.
	 * @param yPos The y position of this pane
	 * @return The new AnchorPane that we generated.
	 */
	private AnchorPane createLevelPane(MapInfo mapInfo, int yPos) {
		AnchorPane levelContainer = new AnchorPane();
		
		Label label = new Label(mapInfo.getMapName());
		Button button = new Button(BUTTON_TEXT);
		
		//Set up Container
		levelContainer.setPrefWidth(scrollPane.getPrefWidth());
		levelContainer.setPrefHeight(CONTAINER_HEIGHT);
		levelContainer.setLayoutY(yPos);
		levelContainer.setStyle(CONTAINER_STYLE);
		levelContainer.getChildren().add(label);
		levelContainer.getChildren().add(button);
		
		//Set up label
		label.setFont(new Font(LABEL_FONT_SIZE));
		label.setPrefHeight(levelContainer.getPrefHeight());
		label.setAlignment(Pos.CENTER_LEFT);
		label.setTextAlignment(TextAlignment.LEFT);
		label.setStyle(LABEL_STYLE);
		AnchorPane.setLeftAnchor(label, LABEL_LEFT_MARGIN);
		AnchorPane.setTopAnchor(label, 0d);
		AnchorPane.setBottomAnchor(label, 0d);
		
		//Set up button
		button.setStyle(BUTTON_STYLE);
		button.setAlignment(Pos.CENTER_RIGHT);
		button.setFont(new Font(BUTTON_FONT_SIZE));
		AnchorPane.setRightAnchor(button, BUTTON_HORIZONTAL_R_MARGIN);
		AnchorPane.setBottomAnchor(button, BUTTON_VERTICAL_MARGIN);
		AnchorPane.setTopAnchor(button, BUTTON_VERTICAL_MARGIN);
		button.setOnAction(e -> {
			main.getGlobalInfo().setCurrentLevelID(mapInfo.getMapID());
			main.switchToScene(Main.SceneType.LevelMenu);
		});
		
		return levelContainer;
	}
}
