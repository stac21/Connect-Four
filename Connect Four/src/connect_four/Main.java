package connect_four;

import static org.lwjgl.opengl.GL11.*;

import java.awt.Dimension;
import java.io.File;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.*;
import org.lwjgl.*;

public class Main {
	public static enum GameStates {
		MAIN_MENU, GAME, WON;
	}
	
	private static Menu menu;
	public static GameStates state = GameStates.MAIN_MENU;
	public static BlockGrid grid;
	public static BlockType selection = BlockType.PIKACHU;
	public static BlockType firstPlayer, secondPlayer;
	private int selectorX, selectorY;
	
	public Main() {
		menu = new Menu();
		
		menu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		menu.setSize(new Dimension(700, 400));
		menu.setVisible(true);
		
		try {
			Display.setDisplayMode(new DisplayMode(640, 480));
			Display.setTitle("Connect Four");
			Display.create();
		} catch (LWJGLException e) {
			e.printStackTrace();
		}
		
		grid = new BlockGrid();
		
		// initialization code goes here
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		glOrtho(0, 640, 480, 0, 1, -1);
		glMatrixMode(GL_MODELVIEW);
		glEnable(GL_TEXTURE_2D);
		glEnable(GL_BLEND);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
		
		while (!Display.isCloseRequested()) {
			// render
			glClear(GL_COLOR_BUFFER_BIT);
			
			checkState();
			
			Display.update();
			Display.sync(60);
		}
		
		Display.destroy();
		System.exit(0);
	}
	
	private void drawSelectionBox() {
		int x = selectorX * World.BLOCKSIZE;
		int y = selectorY * World.BLOCKSIZE;
		int x2 = x + World.BLOCKSIZE;
		int y2 = x + World.BLOCKSIZE;
		
		// if the block at the desired location is not air or the selection is air
		if (grid.getAt(selectorX, selectorY).getType() != BlockType.POKEBALL || 
				selection == BlockType.POKEBALL) {
			glBindTexture(GL_TEXTURE_2D, 0);
			glColor4f(1f, 1f, 1f, 0.8f); // was .5f
			
			glBegin(GL_QUADS);
				glVertex2i(x, y);
				glVertex2i(x2, y);
				glVertex2i(x2, y2);
				glVertex2i(x, y2);
			glEnd();
			
			glColor4f(1f, 1f, 1f, 1f);
		} else {	// if the block at the desired location is air or the selection is not air
			glColor4f(1f, 1f, 1f, 0.8f);
			new Block(selection, x, y).draw();
			glColor4f(1f, 1f, 1f, 1f);
		}
	}
	
	private void checkState() {
		switch (state) {
		case MAIN_MENU:
			if (!menu.isVisible())
				state = GameStates.GAME;
			break;
		case GAME:
			glColor3f(1.0f, 1.0f, 1.0f);
			input();
			grid.draw();
			drawSelectionBox();
			break;
		case WON:
			if (BlockGrid.choice == JOptionPane.YES_OPTION) {
				state = GameStates.MAIN_MENU;
				menu.setVisible(true);
			} else if (BlockGrid.choice == JOptionPane.NO_OPTION) {
				Display.destroy();
				System.exit(0);
			}
		}
	}

	private void input() {
		while (Keyboard.next()) {
			if (Keyboard.getEventKey() == Keyboard.KEY_S
						&& Keyboard.getEventKeyState()) {
				grid.save(new File("save.xml"));
			}
			else if (Keyboard.getEventKey() == Keyboard.KEY_L
						&& Keyboard.getEventKeyState()) {
				grid.load(new File("save.xml"));
			}
			else if (Keyboard.getEventKey() == Keyboard.KEY_C
						&& Keyboard.getEventKeyState()) {
				grid.clear();
			}
			else if (Keyboard.getEventKey() == Keyboard.KEY_LEFT
						&& Keyboard.getEventKeyState()
						&& selectorX > 0) {
				selectorX--;
			}
			else if (Keyboard.getEventKey() == Keyboard.KEY_RIGHT
						&& Keyboard.getEventKeyState()
						&& selectorX < 7) {
				selectorX++;
			}
			else if (Keyboard.getEventKey() == Keyboard.KEY_SPACE
						&& Keyboard.getEventKeyState()) {
				
				grid.place(selectorX);
				
				// switches the player
				selection = (selection == firstPlayer) ? secondPlayer : firstPlayer;
			}
		}
	}
	
	public static void main(String[] args) {
		new Main();
	}
}