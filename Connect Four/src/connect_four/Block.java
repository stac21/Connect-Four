package connect_four;

import static connect_four.World.BLOCKSIZE;
import static org.lwjgl.opengl.GL11.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;

public class Block {
	// the blocktype defaults to air
	private BlockType type = BlockType.POKEBALL;
	private float x, y;
	private Texture texture;
	
	public Block(BlockType type, float x, float y) {
		super();
		this.type = type;
		this.x = x;
		this.y = y;
		try {
			this.texture = TextureLoader.getTexture("PNG", new FileInputStream(
					new File(type.location)));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	// for later
	public void draw() {
		texture.bind();
		glLoadIdentity();
		glTranslatef(x, y, 0);
		glBegin(GL_QUADS);
			glTexCoord2f(0, 0);
			glVertex2f(0, 0);
			glTexCoord2f(1, 0);
			glVertex2f(BLOCKSIZE, 0);
			glTexCoord2f(1, 1);
			glVertex2f(BLOCKSIZE, BLOCKSIZE);
			glTexCoord2f(0, 1);
			glVertex2f(0, BLOCKSIZE);
		glEnd();
	}
	
	public BlockType getType() {
		return type;
	}
	
	public void setType(BlockType type) {
		this.type = type;
	}
	
	public float getX() {
		return x;
	}
	
	public void setX(float x) {
		this.x = x;
	}
	
	public float getY() {
		return y;
	}
	
	public void setY(float y) {
		this.y = y;
	}
	
	public void bind() {
		// TODO Auto-generated method stub
		texture.bind();
	}
}