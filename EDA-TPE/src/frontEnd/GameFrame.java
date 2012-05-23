package frontEnd;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JFrame;

import backEnd.Board;

public class GameFrame extends JFrame {
	
	private static final long serialVersionUID = 1L;
	private BoardPanel panel;
	
	public GameFrame(){
		this.panel = new BoardPanel();
		add(panel, BorderLayout.CENTER);
		setSize(465, 490);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public void printBoard(Board b){
		panel.setBoard(b);
	}
}
