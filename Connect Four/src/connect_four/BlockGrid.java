package connect_four;

import static connect_four.World.*;

import org.jdom2.*;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.XMLOutputter;
import org.lwjgl.opengl.Display;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;

public class BlockGrid {
	private Block[][] blocks = new Block[BLOCKWIDTH][BLOCKHEIGHT];
	public static int choice;
	
	private enum Stages {
		ONE, TWO, THREE, FOUR, DONE
	}
	
	public BlockGrid(File file) {
	}
	
	public BlockGrid() {
		for (int i = 0; i < BLOCKWIDTH - 1 ; i++) {
			for (int n = 0; n < BLOCKHEIGHT - 1; n++) {
				blocks[i][n] = new Block(BlockType.POKEBALL, i * BLOCKSIZE, n  * BLOCKSIZE);
			}
		}
	}
	
	public void setAt(int x, int y, BlockType b) {
		blocks[x][y] = new Block(b, x * BLOCKSIZE, y * BLOCKSIZE);
	}
	
	public Block getAt(int x, int y) {
		return blocks[x][y];
	}
	
	public void draw() {
		for (int i = 0; i < BLOCKWIDTH - 1 ; i++) {
			for (int n = 0; n < BLOCKHEIGHT - 1; n++) {
				blocks[i][n].draw();
			}
		}
	}
	
	public void clear() {
		 for (int x = 0; x < BLOCKWIDTH; x++) {
	            for (int y = 0; y < BLOCKHEIGHT; y++) {
	                blocks[x][y] = new Block(BlockType.POKEBALL, x * BLOCKSIZE, y * BLOCKSIZE);
	            }
	     }
	}
	
	/*
	 * same as fall, just brings the block down to the
	 * desired spot without the falling effect
	 */
	public void place(int x) {
		int i = 0;
		   
	    if (blocks[x][0].getType() == BlockType.POKEBALL) {
	        while (blocks[x][i + 1].getType() == BlockType.POKEBALL && i != 5) {
	            i++;
	        }
	       
	        setAt(x, i, Main.selection);
	    } else {
	        JOptionPane.showMessageDialog(null, "Choose a valid location");
	       
	        Main.selection = (Main.selection == Main.firstPlayer) ? Main.secondPlayer :
	                Main.firstPlayer;
	    }
	   
	    /*
	     *  check for winner. I don't want to do this because at this
	     *  point it's just mindless labor.
	     */	    
	    /*
	     * check for a winner by looping over the entire board.
	     * 
	     * For efficiencie's sake.
	     */
	    boolean winner = false;
	    
	    // horizontal
	    for (byte j = 0; j < 10; j++) {
	    	for (byte n = 0; n < 10; n++) {
	    		if (blocks[j][n].getType() == blocks[j + 1][n].getType()
	    				&& blocks[j + 1][n].getType() == blocks[j + 2][n].getType()
	    				&& blocks[j + 2][n].getType() == blocks[j + 3][n].getType()
	    				&& blocks[j][n].getType() != BlockType.POKEBALL) {
	    			winner = true;
	    			break;
	    		}
	    	}
	    }
	    
	    // vertical
	    if (!winner) {
	    	for (byte n = 0; n < 7; n++) {
	    		for (byte j = 0; j < 12; j++) {
	    			if (blocks[j][n].getType() == blocks[j][n + 1].getType()
	    					&& blocks[j][n + 1].getType() == blocks[j][n + 2].getType()
	    					&& blocks[j][n + 2].getType() == blocks[j][n + 3].getType()
	    					&& blocks[j][n].getType() != BlockType.POKEBALL) {
	    				winner = true;
	    				break;
	    			}
	    		}
	    	}
	    }
	    
	    // diagonal
	    if (!winner) {
	    	int x1 = x;
	    	int y1 = i;
	    	int count = 0;
	    	Stages stage = Stages.ONE;
	    	//BlockType playerSelection = (Main.selection == Main.firstPlayer) ? Main.secondPlayer :
              //  Main.firstPlayer;
	    	
	    	while (stage != Stages.DONE) {
	    		if (count == 4) {
	    			stage = Stages.DONE;
	    			winner = true;
	    		} else if (stage == Stages.ONE && x1 != -1 && y1 != -1 &&
	    				blocks[x1][y1].getType() == Main.selection) {
	    			count++;
	    			x1--;
	    			y1--;
	    		} else if (stage == Stages.ONE) {
	    			stage = Stages.TWO;
	    			x1 = x + 1;
	    			y1 = i + 1;
	    		} else if (stage == Stages.TWO && x1 != 8 && y1 != 6 &&
	    				blocks[x1][y1].getType() == Main.selection) {
	    			count++;
	    			x1++;
	    			y1++;
	    		} else if (stage == Stages.TWO) {
	    			stage = Stages.THREE;
	    			count = 0;
	    			x1 = x;
	    			y1 = i;
	    		} else if (stage == Stages.THREE && x1 != -1 && y1 != 6 &&
	    				blocks[x1][y1].getType() == Main.selection) {
	    			count++;
	    			x1--;
	    			y1++;
	    		} else if (stage == Stages.THREE) {
	    			stage = Stages.FOUR;
	    			x1 = x + 1;
	    			y1 = i - 1;
	    		} else if (stage == Stages.FOUR && x1 != 8 && y1 != -1 &&
	    				blocks[x1][y1].getType() == Main.selection) {
	    			count++;
	    			x1++;
	    			y1--;
	    		} else if (stage == Stages.FOUR) {
	    			stage = Stages.DONE;
	    		}
	    	}
	    	/*
	    	if (x == 0) {
	    		if (Main.grid.getAt(1, i + 1).getType() == Main.selection ||
    				Main.grid.getAt(1, i - 1).getType() == Main.selection) {
	    			if (Main.selection == BlockType.JIGGLYPUFF)
	    				jiggsCount++;
	    			else
	    				pikaCount++;
	    			
	    			if (jiggsCount == 4 || pikaCount == 4)
	    				winner = true;
	    		}
	    	}
	    	else if (x == 7) {
	    		if (Main.grid.getAt(6, i + 1).getType() == Main.selection ||
	    			Main.grid.getAt(6, i - 1).getType() == Main.selection)
	    			winner = true;
	    	}
	    	else {
	    		if (Main.grid.getAt(x - 1, i - 1).getType() == Main.selection ||
	    			Main.grid.getAt(x - 1, i + 1).getType() == Main.selection ||
	    			Main.grid.getAt(x + 1, i - 1).getType() == Main.selection ||
	    			Main.grid.getAt(x + 1, i + 1).getType() == Main.selection)
	    		winner = true;
	    	}
	    	*/
	    }

	    if (winner) {
	    	Main.grid.clear();
	    	
	    	Main.state = Main.GameStates.WON;
	    	
	    	choice = JOptionPane.showConfirmDialog(null, "Winner!\nPlay Again?", "Connect Four",
	    			JOptionPane.YES_NO_OPTION,
	    			JOptionPane.QUESTION_MESSAGE,
	    			new ImageIcon(Main.selection.getLocation()));
	    }
	}
	
	public void save(File saveFile) {
		Document document = new Document();
		Element root = new Element("blocks");
		
		document.setRootElement(root);
		
		for (int i = 0; i < BLOCKWIDTH - 1 ; i++) {
			for (int n = 0; n < BLOCKHEIGHT - 1; n++) {
				Element block = new Element("block");
				
				block.setAttribute("x", String.valueOf((int) (blocks[i][n].getX() 
						/ BLOCKSIZE)));
				block.setAttribute("y", String.valueOf((int) (blocks[i][n].getY() 
						/ BLOCKSIZE)));
				block.setAttribute("type", String.valueOf( blocks[i][n].getType()));
				
				root.addContent(block);
			}
		}
		
		XMLOutputter output = new XMLOutputter();
		try {
			output.output(document, new FileOutputStream(saveFile));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void load(File loadFile) {
		try {
			SAXBuilder builder = new SAXBuilder();
			Document document = builder.build(loadFile);
			Element root = document.getRootElement();
			
			for (Element block : root.getChildren()) {
				int x = Integer.parseInt(block.getAttributeValue("x"));
				int y = Integer.parseInt(block.getAttributeValue("y"));
				
				blocks[x][y] = new Block(BlockType.valueOf(block.getAttributeValue("type")),
						x * BLOCKSIZE, y * BLOCKSIZE);
			}
		} catch (JDOMException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}