import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;

import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;

/**
 * Controls the Select Profile scene.
 * @author Josiah Richards
 * @version 1.5
 */
public class SelectProfileController extends Controller {

	//Edit button generation properties
	private static final String EDIT_BUTTON_TEXT = "Edit";
	private static final String SELECT_BUTTON_TEXT = "Select";
	private static final String NEW_BUTTON_TEXT = "New";
	
	//Profile container generation properties
	private static final double CONTAINER_HEIGHT = 200d;
	private static final double GAP_BETWEEN_CONTAINERS = 1d;
	private static final String CONTAINER_STYLE = "-fx-border-color: black; "
												+ "-fx-background-color: #f6fff4;";
	private static final String SELECTED_CONTAINER_STYLE = "-fx-border-color: black; "
													+ "-fx-background-color: #e6eee4;";
	
	//Profile image generation properties
	private static final double IMAGE_LEFT_MARGIN = 10d;
	
	//Profile name label generation properties
	private static final double LABEL_FONT_SIZE = 50d;
	private static final double LABEL_LEFT_MARGIN = 250d;
	private static final String LABEL_STYLE = "-fx-text-fill: #0a3603;";
	
	//General button generation properties
	private static final double BUTTON_FONT_SIZE = 50d;
	private static final double BUTTON_VERTICAL_MARGIN= 30d;
	private static final String BUTTON_STYLE = "-fx-text-fill: white;"
									+ " -fx-background-color: red;"
									+ " -fx-border-color: black;";
	
	//Specific button generation properties
	private static final double SELECT_BUTTON_HORIZONTAL_R_MARGIN= 400d;
	private static final double EDIT_BUTTON_HORIZONTAL_R_MARGIN= 200d;
	private static final double NEW_BUTTON_VERTICAL_MARGIN= 10d;
	
	//FXML UI Objects
	@FXML ScrollPane scrollPane;
	@FXML AnchorPane scrollPaneAnchor;
	@FXML Button btnBack;
	
	@Override
	public void initialize() {
	}

	@Override
	public void manualInitialize() {
		
		//Set up back button
		try {
			ProfileManager.loadProfile(main.getGlobalInfo().getSelectedProfileID());
			btnBack.setVisible(true);
		} catch (Exception e) {
			//No valid profile selected, hide the back button as they must
			//select a profile.
			btnBack.setVisible(false);
		}
		btnBack.setOnAction(e -> {
			main.switchToScene(Main.SceneType.Menu);
		});

		//Disable horizontal scrolling
		scrollPane.setHmax(0);
		int curY = 0;
		try {
			//Load all the profiles
			ArrayList<Profile> existingProfiles = ProfileManager.loadAllProfiles();
			for (Profile profile : existingProfiles) {
				//Generate an anchor pane for each profile
				if (profile != null) {
					AnchorPane newPane = createProfilePane(profile, curY);
					scrollPaneAnchor.getChildren().add(newPane);
					curY += CONTAINER_HEIGHT + GAP_BETWEEN_CONTAINERS;
				}
			}
		} catch (ParsingException e) {

		}
		//Create the new profile button.
		AnchorPane newProfilePane = createNewProfilePane(curY);
		scrollPaneAnchor.getChildren().add(newProfilePane);
		
	}
	
	/**
	 * Creates an anchor pane consisting of a label and 2 buttons
	 * for a profile.
	 * @param mapInfo The Profile this pane is for.
	 * @param yPos The y position of this pane(relative to the inside of the scrollPane)
	 * @return The new AnchorPane that we generated.
	 */
	private AnchorPane createProfilePane(Profile profileInfo, int yPos) {
		//Initialize objects
		AnchorPane profileContainer = new AnchorPane();
		Label label = new Label(profileInfo.getName());
		Button buttonSelect = new Button(SELECT_BUTTON_TEXT);
		Button buttonEdit = new Button(EDIT_BUTTON_TEXT);
		ImageView imageView = new ImageView();
		
		//Set up Container
		profileContainer.setPrefWidth(scrollPane.getPrefWidth());
		profileContainer.setPrefHeight(CONTAINER_HEIGHT);
		profileContainer.setLayoutY(yPos);
		//Colour the selected profile differently
		if (profileInfo.getID() == main.getGlobalInfo().getSelectedProfileID()) {
			profileContainer.setStyle(SELECTED_CONTAINER_STYLE);
		} else {
			profileContainer.setStyle(CONTAINER_STYLE);
		}
		profileContainer.getChildren().add(label);
		profileContainer.getChildren().add(buttonSelect);
		profileContainer.getChildren().add(buttonEdit);
		profileContainer.getChildren().add(imageView);
		
		//Set up image view
		//Try loading the custom image
		Image image = null; 
		try {
			if (FileManager.fileExists(profileInfo.getImage())) {
				image = new Image(FileManager.filePathToURL(profileInfo.getImage()).toString());
			}
		} catch (MalformedURLException e) {
			image = null;
			e.printStackTrace();
		}
		//Try loading the default image instead
		if (image == null) {
			try {
				image = new Image(FileManager.filePathToURL(FileManager.getImagePath(Profile.DEFAULT_IMAGE)).toString());
			} catch (MalformedURLException e2) {
				e2.printStackTrace();
				image = null;
			}
		}
		imageView.setImage(image);
		imageView.setFitHeight(profileContainer.getPrefHeight());
		imageView.setFitWidth(profileContainer.getPrefHeight());
		AnchorPane.setLeftAnchor(imageView, IMAGE_LEFT_MARGIN);
		AnchorPane.setTopAnchor(imageView, 0d);
		AnchorPane.setBottomAnchor(imageView, 0d);
		
		//Set up label
		label.setFont(new Font(LABEL_FONT_SIZE));
		label.setPrefHeight(profileContainer.getPrefHeight());
		label.setAlignment(Pos.CENTER_LEFT);
		label.setTextAlignment(TextAlignment.LEFT);
		label.setStyle(LABEL_STYLE);
		AnchorPane.setLeftAnchor(label, LABEL_LEFT_MARGIN);
		AnchorPane.setTopAnchor(label, 0d);
		AnchorPane.setBottomAnchor(label, 0d);
		
		//Set up select button
		//If this profile is already selected, disable the select button
		if (profileInfo.getID() == main.getGlobalInfo().getSelectedProfileID()) {
			buttonSelect.setDisable(true);
		}
		buttonSelect.setStyle(BUTTON_STYLE);
		buttonSelect.setAlignment(Pos.CENTER_RIGHT);
		buttonSelect.setFont(new Font(BUTTON_FONT_SIZE));
		AnchorPane.setRightAnchor(buttonSelect, SELECT_BUTTON_HORIZONTAL_R_MARGIN);
		AnchorPane.setBottomAnchor(buttonSelect, BUTTON_VERTICAL_MARGIN);
		AnchorPane.setTopAnchor(buttonSelect, BUTTON_VERTICAL_MARGIN);
		buttonSelect.setOnAction(e -> {
			main.getGlobalInfo().setSelectedProfileID(profileInfo.getID());
			main.switchToScene(Main.SceneType.Menu);
		});
		
		//Set up edit button
		buttonEdit.setStyle(BUTTON_STYLE);
		buttonEdit.setAlignment(Pos.CENTER_RIGHT);
		buttonEdit.setFont(new Font(BUTTON_FONT_SIZE));
		AnchorPane.setRightAnchor(buttonEdit, EDIT_BUTTON_HORIZONTAL_R_MARGIN);
		AnchorPane.setBottomAnchor(buttonEdit, BUTTON_VERTICAL_MARGIN);
		AnchorPane.setTopAnchor(buttonEdit, BUTTON_VERTICAL_MARGIN);
		buttonEdit.setOnAction(e -> {
			main.getGlobalInfo().setEditingProfileID(profileInfo.getID());
			main.switchToScene(Main.SceneType.EditProfile);
		});
		
		return profileContainer;
	}
	
	
	/**
	 * Creates an anchor pane consisting of 1 button for a new profile
	 * @param yPos The y position of this pane(relative to the inside of the scrollPane)
	 * @return The new AnchorPane that we generated.
	 */
	private AnchorPane createNewProfilePane(int yPos) {
		AnchorPane profileContainer = new AnchorPane();
		Button buttonNew = new Button(NEW_BUTTON_TEXT);
		
		//Set up container
		profileContainer.setPrefWidth(scrollPane.getPrefWidth());
		profileContainer.setPrefHeight(CONTAINER_HEIGHT);
		profileContainer.setLayoutY(yPos);
		profileContainer.getChildren().add(buttonNew);
		
		
		//Set up button
		buttonNew.setStyle(BUTTON_STYLE);
		buttonNew.setFont(new Font(BUTTON_FONT_SIZE));
		AnchorPane.setRightAnchor(buttonNew, 0d);
		AnchorPane.setLeftAnchor(buttonNew, 0d);
		AnchorPane.setBottomAnchor(buttonNew, NEW_BUTTON_VERTICAL_MARGIN);
		AnchorPane.setTopAnchor(buttonNew, NEW_BUTTON_VERTICAL_MARGIN);
		buttonNew.setOnAction(e -> {
			handleNewProfileButtonClick();
		});
		
		return profileContainer;
	}
	
	/**
	 * Handles the new profile button being clicked,
	 * creating a new profile and switching to the
	 * edit profile scene.
	 */
	private void handleNewProfileButtonClick() {
		try {
			Profile newProfile = ProfileManager.createNewProfile();
			main.getGlobalInfo().setEditingProfileID(newProfile.getID());
			main.switchToScene(Main.SceneType.EditProfile);
		} catch (IOException e) {
			
		}
	}
}
