import java.util.Observable;
import java.util.Observer;

/*
 * TicTacToeModel: Controls the contents of the application
 */
public class TicTacToeModel extends Observable{

	public static final int SIZE = 3;
	private char[][] grid;
	private boolean turn = true;
	private int[] lastMove = new int[2];

	public TicTacToeModel(Observer view){
		grid = new char[SIZE][SIZE];
		addObserver(view);
	}

	/**
	 * getSize:			Get the size of the boards row and column
	 * 
	 * @return			The size of the boards row and column
	 */
	public int getSize() {
		return SIZE;
	}

	/**
	 * getGrid:			Gets the tic-tac-toe board
	 * 
	 * @return			The 2D char array of the board
	 */
	public char[][] getGrid() {
		return grid;
	}

	/**
	 * setGrid:			Sets the tic-tac-toe board
	 * 			
	 * @param grid		The 2D char array to set the board to 
	 */
	public void setGrid(char[][] grid) {
		this.grid = grid;
	}
	
	/**
	 * getRow:			Figures out which row was clicked based on the button number
	 *
	 * @param buttonNumber	The integer value assigned to the button that was pressed
	 * @return				The row value of the button that was pressed
	 */
	public int getRow(int buttonNumber){
		return (buttonNumber/SIZE);		
	}
	
	/**
	 * getCol:			Figures out which column was clicked based on the button number
	 *
	 * @param buttonNumber	The integer value assigned to the button that was pressed
	 * @return				The column value of the button that was pressed
	 */
	public int getCol(int buttonNumber){
		return (buttonNumber%SIZE);	
	}
	
	/**
	 * switchTurn:		Changes the turn
	 * 
	 */
	public void switchTurn(){
		turn = !turn;
	}
	
	/**
	 * getTurn:				Gets the current turn
	 * 
	 * @return				The boolean value of whose turn it is (True if 'o' and False if 'x')
	 */
	public boolean getTurn(){
		return turn;
	}
	
	/**
	 * getPlayer:			Gets the player based on whose turn it is
	 * 
	 * @return				The char value of whose turn it is ('o' if true and 'x' if false)
	 */
	public char getPlayer(){
		if (turn){
			return 'o';
		}
		return 'x';
	}
	
	/**
	 * nextPlayer:			Gets whose turn is next
	 * 
	 * @return				The char value of whose turn is next
	 */
	public char nextPlayer(){
		if (!turn){
			return 'o';
		}
		return 'x';
	}
	
	/**
	 * setMove:				Sets the Move (Places the 'x' or 'o' on the board)
	 * 
	 * @param row			The row that got clicked
	 * @param col			The column that got clicked
	 */
	public void setMove(int row, int col){
		grid[row][col] = getPlayer();
		lastMove[0] = row;
		lastMove[1] = col;
	}
	
	/**
	 * getMove:				Gets the position of the last move
	 * 
	 * @return				An integer array with the last move (row and column)
	 */
	public int[] getMove(){
		return lastMove;
	}
	
	/**
	 * isDone:				Checks to see if anyone won
	 * 
	 * @return				The boolean value true if there is a winner and false otherwise				
	 */
	public boolean isDone(){
		int row_count = 0;
		int col_count = 0;
		int d1_count = 0;
		int d2_count = 0;
		for (int r = 0; r < SIZE; r++){
			for (int c = 0; c < SIZE; c++){
				if (grid[r][c] == getPlayer()){
					row_count++; 
				}
				if (grid[c][r] == getPlayer()){
					col_count++;
				}
				if (grid[c][c] == getPlayer()){
					d1_count++;
				}
				if (grid[c][SIZE - 1 - c] == getPlayer()){
					d2_count++;
				}
			}
			if (row_count == SIZE || col_count == SIZE || d1_count == SIZE || d2_count == SIZE){
				return true;
			}
			row_count = 0;
			col_count = 0;
			d1_count = 0;
			d2_count = 0;
		}
		return false;
	}
	
	/**
	 * isTie:				Checks to see if there is a tie
	 * 
	 * @return				The boolean value true if there is a tie and false otherwise
	 */
	public boolean isTie(){
		int totalCount = 0;
		for (int r = 0; r < SIZE; r++){
			for (int c = 0; c < SIZE; c++){
				if (grid[r][c] == 'x' || grid[r][c] == 'o'){
					totalCount++;
				}
			}
		}
		if (totalCount == SIZE*SIZE){
			return true;
		}
		return false;
	}
	
	/**
	 * playGame:			Plays the players move and uses the Observer/Observable Pattern to update the View
	 * 
	 * @param buttonNumber	The button number clicked on by the user
	 */
	public void playGame(int buttonNumber){
		int row = getRow(buttonNumber);
		int col = getCol(buttonNumber);
		setMove(row, col);
		setChanged();
		notifyObservers();
		if (isDone()){
			setChanged();
			notifyObservers();
		}
		else if (isTie()){
			setChanged();
			notifyObservers();
		}
		switchTurn();
	}		
	
}
