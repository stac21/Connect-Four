package connect_four;

import java.net.MalformedURLException;
import java.net.URL;

public enum BlockType implements BlockTypeInterface {
	PIKACHU("C:\\Users\\Grant\\Desktop\\Oprah\\GameFiles\\Images\\pikachu.png"), 
	JIGGLYPUFF("C:\\Users\\Grant\\Desktop\\Oprah\\GameFiles\\Images\\jigglypuff.png"),
	POKEBALL("C:\\Users\\Grant\\Desktop\\Oprah\\GameFiles\\Images\\air.png"),
	STONE("C:\\Users\\Grant\\Desktop\\Oprah\\GameFiles\\Images\\stone.png");
	
	public final String location;
	
	// makes the constants able to save the directory of the images
	BlockType(String location) {
		this.location = location;
	}
	
	public String getLocation() {
		return location;
	}
}
