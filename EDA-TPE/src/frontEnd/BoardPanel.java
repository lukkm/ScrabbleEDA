package frontEnd;

import java.awt.Color;
import java.awt.Graphics;
import java.util.HashSet;
import java.util.Set;

import javax.swing.JPanel;

import backEnd.Board;
import backEnd.Letter;

public class BoardPanel extends JPanel{

	private static final long serialVersionUID = 1L;
	private final int cellSize = 30;
	private final int spanLeft = 12, spanBot = 10;
	private final int rows = 15, columns = 15;
	private final Color gridColor = Color.PINK;
	private Set<Letter> letterList = new HashSet<Letter>();	
	
	public BoardPanel() {
		setSize(columns * (cellSize + 1), rows * (cellSize + 1));
	}
	
	public void paint(Graphics g) {
		super.paint(g);
		g.setColor(gridColor);
		for (int i = 0; i <= rows; i++) {
			g.drawLine(0, i * cellSize, columns * cellSize, i * cellSize);
		}
		for (int j = 0; j <= columns; j++) {
			g.drawLine(j * cellSize, 0, j * cellSize, rows * cellSize);
		}
		for (Letter l : letterList)
			g.drawString(String.valueOf(l.getValue()), (l.getY()) * cellSize + spanLeft, (l.getX()+1) * cellSize - spanBot );
	}
	
	
	public void setBoard(Board b){
		this.letterList = b.getLettersList();
		this.repaint();
	}
	
}
