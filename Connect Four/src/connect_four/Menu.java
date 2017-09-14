package connect_four;

import javax.swing.JFrame;
import javax.swing.JTextField;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;

public class Menu extends JFrame {
	private static final long serialVersionUID = 1L;
	private JButton pikachu, jigglypuff;
	private JTextField textField;
	
	public Menu() {
		super("Menu");
		
		setLayout(new FlowLayout());
		
		pikachu = new JButton("Pikachu");
		pikachu.setIcon(new ImageIcon(BlockType.PIKACHU.getLocation()));
		pikachu.setMnemonic(KeyEvent.VK_P);
		
		add(pikachu);
		
		jigglypuff = new JButton("Jigglypuff");
		jigglypuff.setIcon(new ImageIcon(BlockType.JIGGLYPUFF.getLocation()));
		jigglypuff.setMnemonic(KeyEvent.VK_J);
		
		add(jigglypuff);
		
		Handler handler = new Handler();
		
		pikachu.addActionListener(handler);
		jigglypuff.addActionListener(handler);
		
		textField = new JTextField("Player 1, choose your character. Player 2, you get "
				+ "whichever character that Player 1 does not choose.");
		textField.setEditable(false);
		
		add(textField);
	}
	
	private class Handler implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == pikachu) {
				Main.firstPlayer = BlockType.PIKACHU;
				Main.secondPlayer = BlockType.JIGGLYPUFF;
				
				Main.selection = Main.firstPlayer;
				
				setVisible(false);
			} else if (e.getSource() == jigglypuff) {
				Main.firstPlayer = BlockType.JIGGLYPUFF;
				Main.secondPlayer = BlockType.PIKACHU;
				
				Main.selection = Main.firstPlayer;
				
				setVisible(false);
			}
		}
	}
}