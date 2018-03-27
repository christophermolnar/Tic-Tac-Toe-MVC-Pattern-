import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;

import javax.swing.*;

/*
 * TicTacToeView: The graphical representation of the model
 */
public class TicTacToeView extends JFrame implements Observer {

	private JMenuItem quit, newGame;
	public TicTacToeModel myModel;
	public TicTacToeController myController;
	private JButton[][] board;
	private JButton button;
	private JLabel currentTurn;
	
	public TicTacToeView(){
		
		super("Tic Tac Toe");
		myModel = new TicTacToeModel(this);
		myController = new TicTacToeController(myModel, this);
		
		JMenuBar menubar = new JMenuBar();
		JMenu file = new JMenu("File");
		newGame = new JMenuItem("New Game");
		quit = new JMenuItem("Quit");
		newGame.setActionCommand("new");
		quit.setActionCommand("quit");
		quit.addActionListener(myController);
		newGame.addActionListener(myController);
		file.add(newGame);
		file.add(quit);
		menubar.add(file);
		
		setLayout(new GridLayout(TicTacToeModel.SIZE + 1, TicTacToeModel.SIZE));
		board = new JButton[TicTacToeModel.SIZE][TicTacToeModel.SIZE];
		int position = 0;
		for (int row = 0; row < 3; row++){
			for (int col = 0; col < 3; col++){
				button = new JButton("Press");
				button.setActionCommand("" + position + "");
				button.addActionListener(myController);
				board[row][col] = button;
				add(board[row][col]);
				position++; 
			}
		}
		
		add(currentTurn = new JLabel("The Current turn is: " + myModel.getPlayer()));
		
		setJMenuBar(menubar);
		setSize(400, 400);
		setVisible(true);	
	}
	
	/**
	 * updateBoard:			Updates the board that the player sees
	 * 
	 * @param lastMove:		An array that hold the row and column of the last move
	 * @param player:		The char of the current player
	 * @param nextPlayer	The char of the next player
	 */
	private void updateBoard(int[] lastMove, char player, char nextPlayer){
		int row = lastMove[0];
		int col = lastMove[1];
		board[row][col].setEnabled(false);
		board[row][col].setText("" + player + "");
		currentTurn.setText("The Current turn is: " + nextPlayer);
	}
	
	/**
	 * win:					Congratulate the winner (Disable all the JButtons)
	 * 
	 * @param player		The player that won
	 */
	private void win(char player){
		currentTurn.setText("Player " + player + " Wins!");
		for (int row = 0; row < TicTacToeModel.SIZE; row++){
			for (int col = 0; col < TicTacToeModel.SIZE; col++){
				board[row][col].setEnabled(false);
			}
		}
	}
	
	/**
	 * tie:					The game ended in a tie				
	 * 
	 */
	private void tie() {
		currentTurn.setText("Tie Game");	
	}
	
	/**
	 * quit:				Exit the game
	 * 
	 */
	public void quit(){
		System.exit(0);
	}
	
	/**
	 * newGame:				Close the current game and create a new one
	 * 
	 */
	public void newGame(){
		this.dispose();
		TicTacToeView v = new TicTacToeView();
	}
	
	/**
	 * addButtonActionListener: Add action listeners to the JButtons
	 * 
	 * @param a					The Action Listener being added to the JButton
	 */
	public void addButtonActionListener(ActionListener a){
		button.addActionListener(a);
	}
	
	@Override
	/**
	 * update: 				Update the Observer based on the Observable
	 * 
	 * @param arg0			The Observable 
	 * @param arg1			An Object from the Observable
	 * 
	 */
	public void update(Observable arg0, Object arg1) {
		TicTacToeModel currentModel = ((TicTacToeModel) arg0);
		updateBoard(currentModel.getMove(), currentModel.getPlayer(), currentModel.nextPlayer());
		if (currentModel.isDone()){
			win(currentModel.getPlayer());
		}	
		else if(currentModel.isTie()){
			tie();
		}
		setVisible(true);
	}

}
