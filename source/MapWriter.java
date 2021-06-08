import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Class for saving a map to a Map save file.
 * @author Josiah Richards
 * @version 1.0
 */
public class MapWriter {

	/**
	 * Saves all the necessary information about a map to
	 * a given save file. 
	 * Sets the spawn location to the position of the player.
	 * @param saveFile The file to save to.
	 * @param map The map to save.
	 * @throws IOException If the save file is a folder.
	 */
	public static void saveMapToFile(File saveFile, Map map) throws IOException
	{
		//Create the save file
		FileWriter writer = new FileWriter(saveFile);
		
		//Write map dimensions
		Vector2 dimensions = map.getGridDimensions();
		writer.write(dimensions.toString());
		writer.write(GlobalInfo.NEW_LINE);
		
		String cellMetaInfos = "";
		
		//Write cells by looping through the map grid
		for (int y = 0; y < dimensions.getY(); y++) {
			for (int x = 0; x < dimensions.getX(); x++) {
				Cell cell = map.getCellAt(x, y);
				writer.write(cell.getSymbol());
				//If theres meta info for this cell add it to the cell meta infos
				//string for later
				String cellMetaInfo = cell.getMetaInfo();
				if (cellMetaInfo != null) {
					cellMetaInfos += cellMetaInfo;
					cellMetaInfos += GlobalInfo.NEW_LINE;
				}
			}
			writer.write(GlobalInfo.NEW_LINE);
		}
		//Write meta infos of cells
		writer.write(cellMetaInfos);
		
		//Write meta info of entities
		ArrayList<Entity> entities = map.getEntities();
		for (Entity entity : entities) {
			//If theres meta info for this entity, write it
			String entityMetaInfo = entity.getMetaInfo();
			if (entityMetaInfo != null){
				writer.write(entityMetaInfo);
				writer.write(GlobalInfo.NEW_LINE);
			}
		}
		
		//Finally write the last time
		long lastTime = map.getLastTime();
		writer.write(String.format("0,0,%s,%d", Map.LAST_TIME_META_KEYWORD, lastTime));
		
		writer.close();
	}
	
	/**
	 * Saves all the necessary information about a map to
	 * a given save file. 
	 * @param saveFileLoc The file to save to.
	 * @param map The map to save.
	 * @throws IOException If the save file is a folder.
	 */
	public static void saveMapToFile(String saveFileLoc, Map map) throws IOException {
		File saveFile = new File(saveFileLoc);
		//Overwrite by deleting any existing file
		if(saveFile.exists()) {
			saveFile.delete();
		}
		saveMapToFile(saveFile, map);
	}
	
}
