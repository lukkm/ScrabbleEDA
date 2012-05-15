package frontEnd;

import java.awt.BorderLayout;

import javax.swing.JFrame;

import backEnd.Board;
import backEnd.Letter;

public class GameFrame extends JFrame {
	
	private BoardPanel panel;
	
	public GameFrame(){
		this.panel = new BoardPanel();
		add(panel, BorderLayout.CENTER);
		setSize(465, 490);
	}
	
	public void printBoard(Board b){
		panel.setBoard(b);
	}
}
