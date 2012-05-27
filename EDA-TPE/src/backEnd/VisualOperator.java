package backEnd;

import frontEnd.GameFrame;

public class VisualOperator {
	
	private GameFrame gameFrame;
	
	public VisualOperator(GameFrame gameFrame){
		this.gameFrame = gameFrame;
		if (gameFrame != null){
			gameFrame.setVisible(true);
		}
	}
	
	public void printBoard(Board b){
		if(gameFrame != null){
			try {
				Thread.sleep(25);
			} catch (InterruptedException e) {
			}
			gameFrame.printBoard(b);
		}
	}

}
