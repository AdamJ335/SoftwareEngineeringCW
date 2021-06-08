import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
/**
 * Controls the edit profile UI.
 * @author Josiah Richards
 * @version 1.1
 */
public class EditProfileController extends Controller {

	//The title for the select image file open dialog.
	private static final String SELECT_IMAGE_TITLE = "Select a new profile image";
	//The filter for the profile image selection dialog.
	private static final ExtensionFilter IMAGE_FILTER = new ExtensionFilter("Image Files", 
														"*.png","*.jpg","*.jpeg");
	
	//FXML UI Objects
	@FXML ImageView imgProfileImage;
	@FXML TextField fieldID;
	@FXML TextField fieldName;
	@FXML TextField fieldLevel;
	@FXML Button btnChangeImage;
	@FXML Button btnSave;
	@FXML Button btnCancel;
	@FXML Button btnReset;
	@FXML Button btnDelete;
	
	//The current state of the profile we're editing
	private Profile currentProfile;
	
	@Override
	public void initialize() {
	}

	@Override
	public void manualInitialize() {
		
		//Try loading the profile we're editing
		try {
			currentProfile = ProfileManager.loadProfile(main.getGlobalInfo().getEditingProfileID());
		} catch (ParsingException e) {
			//Switch back to select profile if we couldn't load this profile.
			currentProfile = null;
			e.printStackTrace();
			main.getGlobalInfo().setEditingProfileID(-1);
			main.switchToScene(Main.SceneType.SelectProfile);
		}
		
		//Update the UI to match the current profile and set up
		//the UI buttons
		if (currentProfile != null) {
			updateProfileUI(currentProfile);
			setUpButtons();
		}
	}
	
	/**
	 * If the selected profile is this profile, set the selected
	 * profile to none(-1).
	 * Used for when this profile is deleted.
	 */
	private void clearSelectedProfileIfSame() {
		if (currentProfile != null) {
			if (main.getGlobalInfo().getSelectedProfileID() == currentProfile.getID()) {
				main.getGlobalInfo().setSelectedProfileID(-1);
			}
		}
	}
	
	/**
	 * Updates the properties of the current profile to match
	 * our text fields.
	 */
	private void updateCurProfileToFields() {
		currentProfile.setHighestLevel(Integer.parseInt(fieldLevel.getText()));
		currentProfile.setName(fieldName.getText());
	}
	
	/**
	 * Prompts the user to select a new image for the profile.
	 */
	private void selectNewImage() {
		//Choose a file.
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle(SELECT_IMAGE_TITLE);
        fileChooser.getExtensionFilters().add(IMAGE_FILTER);
		File file = fileChooser.showOpenDialog(main.getCurScene().getWindow());
		//Update the profile and display the new info.
		if (file.exists()) {
			updateCurProfileToFields();
			currentProfile.setImage(file.getAbsolutePath());
			updateProfileUI(currentProfile);
		}
	}
	
	/**
	 * Creates handlers for the buttons on this UI.
	 */
	private void setUpButtons() {
		//Save
		btnSave.setOnAction(e -> {
			//When save clicked, try saving the profile 
			//and return the the select profile scene.
			updateCurProfileToFields();
			try {
				ProfileManager.saveProfile(currentProfile);
			} catch (IOException e1) {
				e1.printStackTrace();
				clearSelectedProfileIfSame();
			}
			main.getGlobalInfo().setEditingProfileID(-1);
			main.switchToScene(Main.SceneType.SelectProfile);
		});
		//Cancel
		btnCancel.setOnAction(e -> {
			//When cancel clicked, 
			//return the the select profile scene
			main.getGlobalInfo().setEditingProfileID(-1);
			main.switchToScene(Main.SceneType.SelectProfile);
		});
		//Reset
		btnReset.setOnAction(e -> {
			//When reset clicked, reset the highest level
			//reached on the current profile to 0.
			currentProfile.setHighestLevel(0);
			updateProfileUI(currentProfile);
		});
		btnChangeImage.setOnAction(e -> {
			//When change image clicked, prompt the user
			//to change the profile image.
			selectNewImage();
		});
		//Delete
		btnDelete.setOnAction(e -> {
			//When the delete button is clicked,
			//get the profile folder and delete it
			String profileFolderStr = FileManager.getProfileFolderPath(currentProfile.getID());
			File profileFolder = new File(profileFolderStr);
			if (profileFolder.exists()) {
				FileManager.deleteFolder(profileFolder);
			}
			//Return to the select profile scene
			clearSelectedProfileIfSame();
			main.getGlobalInfo().setEditingProfileID(-1);
			main.switchToScene(Main.SceneType.SelectProfile);
		});
	}
	
	/**
	 * Updates the UI to display the information stored in
	 * a given profile.
	 * @param profile The profile who's information we want to display.
	 */
	private void updateProfileUI(Profile profile) {
		//Update the UI fields. 
		//""+ to convert to string.
		fieldID.setText(""+profile.getID());
		fieldName.setText(profile.getName());
		fieldLevel.setText(""+profile.getHighestLevel());
		
		//Try loading the custom image
		Image image = null; 
		try {
			if (FileManager.fileExists(currentProfile.getImage())) {
				image = new Image(FileManager.filePathToURL(profile.getImage()).toString());
			}
		} catch (MalformedURLException e) {
			image = null;
			e.printStackTrace();
		}
		//Try loading the default image instead
		if (image == null) {
			try {
				String defaultImage = FileManager.getImagePath(Profile.DEFAULT_IMAGE);
				image = new Image(FileManager.filePathToURL(defaultImage).toString());
				profile.setImage(defaultImage);
			} catch (MalformedURLException e2) {
				e2.printStackTrace();
				image = null;
			}
		}
		//Update the image
		imgProfileImage.setImage(image);
	}
	
}
