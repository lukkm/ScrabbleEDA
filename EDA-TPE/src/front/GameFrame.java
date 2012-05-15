package front;

import gui.BoardPanel;
import gui.BoardPanelListener;
import gui.ImageUtils;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import back.*;
import back.cells.*;
import back.parser.*;
import back.saveLoadGame.*;

public class GameFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	
	private static final int CELL_SIZE = 30;
	private BoardPanel panel;
	private JMenuBar menuBar;
	private JMenu fileMenu;
	private JMenuItem newGame;
	private JMenuItem loadGame;
	private JMenuItem saveGame;
	private JMenuItem quitGame;
	private JLabel score;
	private Game game;
	private boolean hasWon = true;
	
	private Map<Class<?>,Image> images = new HashMap<Class<?>,Image>();
	
	public GameFrame() throws IOException{
		super("Lasers & Mirrors");
		setSize(10 * CELL_SIZE, 10 * CELL_SIZE+10);
		menuBar = new JMenuBar();
		fileMenu = new JMenu("Lasers & Mirrors");
		newGame = fileMenu.add("New Game");
		loadGame = fileMenu.add("Load Game");
		saveGame = fileMenu.add("Save Game");
		saveGame.setEnabled(false);
		quitGame = fileMenu.add("Quit");
		
		menuBar.add(fileMenu);
		setJMenuBar(menuBar);
		loadImages();
		initializeJMenuActionListeners();
		
	}
	
	/**
	 * Initializes the map with the type of Cell as key and the associated image as value.
	 * It also saves the ray image.
	 * 
	 * @throws IOException
	 */
	private void loadImages() {
		try{
			images.put(Filter.class, ImageUtils.loadImage("./resources/filter.png"));
			images.put(SimpleMirror.class, ImageUtils.loadImage("./resources/simple-mirror.png"));
			images.put(Origin.class, ImageUtils.loadImage("./resources/source.png"));
			images.put(MovableOrigin.class, ImageUtils.loadImage("./resources/source.png"));
			images.put(Wall.class, ImageUtils.loadImage("./resources/wall.png"));
			images.put(DoubleMirror.class, ImageUtils.loadImage("./resources/double-mirror.png"));
			images.put(SemiMirror.class, ImageUtils.loadImage("./resources/split-mirror.png"));
			images.put(Destination.class, ImageUtils.loadImage("./resources/target.png"));	
			images.put(Ray.class, ImageUtils.loadImage("./resources/laser.png"));
			images.put(HalfRay.class, ImageUtils.loadImage("./resources/half-laser.png"));
		}catch(IOException e){
			JOptionPane.showMessageDialog(null, "Unexpected Error", "Error",
					JOptionPane.ERROR_MESSAGE);
		}
	}
	
	
	private void initializeJMenuActionListeners(){
		
		newGame(new ActionListener(){

			public void actionPerformed(ActionEvent a) {
				try{
					File file = null;
					JFileChooser fc = new JFileChooser();
					fc.setCurrentDirectory(new File("./boards"));
					fc.showOpenDialog(GameFrame.this);
					file = fc.getSelectedFile();
					if(file!=null){
						try{
							if (!file.getAbsolutePath().endsWith(".board"))
								throw new NotABoardException ();
							game = new Game(file);
						}catch(Exception e){
							JOptionPane.showMessageDialog(null, "Game File is corrupt",
									"Error", JOptionPane.ERROR_MESSAGE);
						}
					}
					
					if(game!=null){
						startGame();
					}
				} catch(Exception e2){
					JOptionPane.showMessageDialog(null, "Game File is corrupt",
							"Error", JOptionPane.ERROR_MESSAGE);
				}
			}
			});
		
		
		loadGame(new ActionListener(){

			public void actionPerformed(ActionEvent e) {
				File file;
				LoadGame loadGame = null;
				JFileChooser fc = new JFileChooser();
				fc.setCurrentDirectory(new File("./savedGames"));
				fc.showOpenDialog(GameFrame.this);
				file = fc.getSelectedFile();
				if (file == null) {
					JOptionPane.showMessageDialog(null, "Please select a file.");
				}else{
					try {
						loadGame = new LoadGame(file);
						game = loadGame.getGame();
					} catch (IOException e1) {
							JOptionPane.showMessageDialog(null, "Game File is corrupt",
								"Error", JOptionPane.ERROR_MESSAGE);
						} catch (ClassNotFoundException e1) {
							JOptionPane.showMessageDialog(null, "File not found",
									"Error", JOptionPane.ERROR_MESSAGE);
					}
					if(game!=null){
						startGame();
					}
				}
			}
			
		});
		
		saveGame(new ActionListener(){

			public void actionPerformed(ActionEvent e) {
				game.clearGame();
				File fileName;
				JFileChooser fc = new JFileChooser();
				fc.setCurrentDirectory(new File("./savedGames"));
				fc.showSaveDialog(GameFrame.this);
				fileName = fc.getSelectedFile();
				if (fileName != null) {
					try {
						new SaveGame(game, fileName);
					} catch (IOException e1) {
						JOptionPane.showMessageDialog(null, "Game File is corrupt81",
								"Error", JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		});
		
		quitGame(new ActionListener(){

			public void actionPerformed(ActionEvent e) {
				try{
					setVisible(false);
					dispose();
				}catch(Exception e3){
					JOptionPane.showMessageDialog(null, "Exit Fault",
							"Error", JOptionPane.ERROR_MESSAGE);
				}
			}
			
		});
	}

	
	/**
	 * private method that initializes the game's panel and gives the order to
	 * start creating rays 
	 */
	
	private void startGame(){
		saveGame.setEnabled(true);
		loadGame.setEnabled(false);
		panel = new BoardPanel(game.getX(), game.getY(),CELL_SIZE);
		panel.setListener(new BoardPanelListenerImp());
		panel.setGridColor(Color.GRAY);
		score = new JLabel("Score: " + game.getScore());
		add(panel, BorderLayout.CENTER);
		add(score, BorderLayout.SOUTH);
		drawGame();
		panel.updateUI();
	}
	
	/**
	 * calls game's method play(), draws the board and update the score
	 */
	
	private void drawGame(){
		game.play();
		updateScore();
		drawBoard(images);
		if (hasWon) {
			JOptionPane.showMessageDialog(this, 
					"Felicitaciones! has ganado!" + "\n" + "Score: " + game.getScore(), 
					"Felicitaciones", JOptionPane.INFORMATION_MESSAGE);
		}
	}
	
	/**
	 * draws the board on the panel
	 * @param map
	 */
	
	private void drawBoard(Map<Class<?>, Image> map){
		for(int i=0; i<game.getX(); i++){
			for(int j=0; j<game.getY(); j++){
				Cell cell = game.getBoard().getCell(new Point(i,j));
				if (cell instanceof Destination) {
					if (!cell.getRays().hasColor(((Destination)cell).getColor())){
						hasWon=false;
					}
				}
				if(!cell.getRays().isEmpty()){
					for (Ray e: cell.getRays()){
						Image rayImage = images.get(e.getClass());
						rayImage = ImageUtils.replaceColor(rayImage, Color.BLUE, e.getColor());
						rayImage = ImageUtils.rotateImage(rayImage,e.getOrientation().index());
						panel.appendImage(i, j, rayImage);
					}
				}
				Image image = map.get(cell.getClass());
				if (cell instanceof Turnable || cell instanceof Origin)
					image = ImageUtils.rotateImage(image, cell.orientationIndex());
				if(cell instanceof Coloreable){
					image = ImageUtils.replaceColor(image, Color.BLUE, 
									((Coloreable)cell).getColor());
				}
				panel.appendImage(i, j, image);
			}
		}
	}
	
	/**
	 * asks to the game for the new score and shows it on the screen
	 */
	
	private void updateScore(){
		remove(score);
		score = new JLabel("Score: " + game.getScore());
		add(score, BorderLayout.SOUTH);
	}
	
	/**
	 * clears the game and the board, then it starts the game again (used when the user
	 * made a mouse action)
	 */
	
	private void restart(){
		hasWon = true;
		game.clearGame();
		clearBoard();
		drawGame();
	}
	
	/**
	 * erases the panel's images
	 */
	
	private void clearBoard(){
		for (int i = 0; i<game.getX(); i++)
			for (int j = 0; j<game.getY(); j++)
				panel.clearImage(i, j);
	}
	
	
	
	private void quitGame(ActionListener actionListener) {
		quitGame.addActionListener(actionListener);
	}

	private void loadGame(ActionListener actionListener) {
		loadGame.addActionListener(actionListener);
	}

	private void newGame(ActionListener actionListener) {
		newGame.addActionListener(actionListener);
	}
	
	private void saveGame(ActionListener actionListener) {
		saveGame.addActionListener(actionListener);
	}

	
	
	private class BoardPanelListenerImp implements BoardPanelListener{

		/**
		 * listener for a clicked cell, when the click is made on a Turnable cell,
		 * it turns the cell and re-draws the board.
		 */
		
		public void cellClicked(int x, int y) {
			Cell cell = game.getBoard().getCell(new Point(x, y)); 
			if(cell instanceof Turnable){
				((Turnable)cell).turn();
				restart();
				panel.updateUI();
			}
		}

		/**
		 * listener for a dragged cell, when the dragged cell is a Movable one, 
		 * it moves the cell and re-draws the board.
		 */
		
		public void cellDragged(int sourceRow, int sourceColumn, int targetRow,
				int targetColumn) {			
			Cell cell = game.getBoard().getCell(new Point(sourceRow, sourceColumn));
			if (cell instanceof Movable){
				try{
					game.getBoard().moveCell(cell, new Point(targetRow, targetColumn));
				} catch (NotAllowedMoveException e) {
					System.out.println("You can't move it there");
					return;
				}
				panel.setImage(targetRow, targetColumn, images.get(cell));
				restart();
				panel.updateUI();
			}	
		}		
	}

}
