package backEnd;

import frontEnd.GameFrame;

public class VisualOperator {
	
	private GameFrame gameFrame;
	
	public VisualOperator(GameFrame gameFrame){
		this.gameFrame = gameFrame;
	}
	
	public void printBoard(Board b){
		if(gameFrame != null){
			try {
				Thread.sleep(25);
			} catch (InterruptedException e) {
				e.printStackTrace();
				return;
			}
			gameFrame.printBoard(b);
		}
	}

}
