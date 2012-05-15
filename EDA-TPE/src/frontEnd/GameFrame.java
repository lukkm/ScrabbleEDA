package frontEnd;

import java.awt.BorderLayout;

import javax.swing.JFrame;

import backEnd.Board;

public class GameFrame extends JFrame {
	
	private static final long serialVersionUID = 1L;
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
