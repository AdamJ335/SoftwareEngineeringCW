import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 * The first class to be run, handles switching between scenes.
 * @author Josiah Richards
 * @version 1.5
 */
public class Main extends Application {

	//The different type of scenes.
	public enum SceneType {
		Menu("Menu"),
		SelectLevel("SelectLevel"),
		LevelMenu("LevelMenu"),
		Level("Level"),
		Leaderboard("Leaderboard"),
		SelectProfile("SelectProfile"),
		EditProfile("EditProfile");
		
		//The file name of the scene without its extension. 
		private String fileName;
		
		/**
		 * Creates a SceneType enum with a given file name.
		 * @param fileName The namet of the FXML file for this scene type.
		 */
		private SceneType(String fileName) {
			this.fileName = fileName;
		}
		
		@Override
		public String toString() {
			return fileName;
		}
	}
	
	//Application constants.
	private static final String WINDOW_TITLE = "Santa's Journey";
	private static final SceneType STARTING_SCENE = SceneType.SelectProfile;

	//Stores information thats constant between scenes.
	private GlobalInfo globalInfo;
	
	//Information about the current scene.
	private Stage primaryStage;
	private Pane curRootPane;
	private Scene curScene;
	private SceneType currentSceneType;
	private SceneType previousSceneType;
	
	/**
	 * The method to run when the program stars.
	 * @param args The arguments passed to this program.
	 */
	public static void main(String[] args) {
		//Launch this application.
		launch(args);
	}
	
	@Override
	public void start(Stage primaryStage) {
		//Initialize
		globalInfo = new GlobalInfo();
		currentSceneType = STARTING_SCENE;
		
		//Set up the stage
		this.primaryStage = primaryStage;
		primaryStage.setMaximized(true);
		primaryStage.setTitle(WINDOW_TITLE);
		
		// Switch to the starting scene and show it.
		switchToScene(STARTING_SCENE);
		primaryStage.show();
	}
	
	/**
	 * Loads a scene FXML file and switches the primary stage's scene to it.
	 * @param sceneName The name of the scene to switch to(should have a matching FXML file).
	 */
	public void switchToScene(SceneType sceneType) {
		previousSceneType = currentSceneType;
		currentSceneType = sceneType;
		try {
			//Get the file location of the scene's FXML file.
			String pathUI = FileManager.getUIPath(sceneType.toString());
			// Create a FXML loader for loading the FXML file.
			FXMLLoader fxmlLoader = new FXMLLoader(FileManager.filePathToURL(pathUI));     
			
			// Load the scene.
			curRootPane = (Pane)fxmlLoader.load();
			curScene = new Scene(curRootPane, primaryStage.getWidth(), primaryStage.getHeight());
			
			// Access the controller that was created by the FXML loader.
			Controller newController = fxmlLoader.<Controller>getController();
			newController.setMain(this);
			
			//Switch to the new scene.
			primaryStage.setScene(curScene);
			
			newController.manualInitialize();
			
		} catch (IOException e) {
			e.printStackTrace();
		}  
	}
	
	/**
	 * Get's the curScene private variable.
	 * @return The curScene.
	 */
	public Scene getCurScene(){
		return curScene;
	}
	
	/**
	 * Get's the type of the previous scene we were on.
	 * @return The type of the previous scene we were on.
	 */
	public SceneType getPreviousSceneName() {
		return previousSceneType;
	}
	
	/**
	 * Gets the global information object for this application.
	 * @return The global information object for this application.
	 */
	public GlobalInfo getGlobalInfo() {
		return globalInfo;
	}
	
	/**
	 * Get's the curRootPane private variable.
	 * @return The curRootPane.
	 */
	public Pane getCurRootPane(){
		return curRootPane;
	}
}

