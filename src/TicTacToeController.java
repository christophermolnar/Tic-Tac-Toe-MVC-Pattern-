import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

/*
 * TicTacToeController: Handles the user input
 */
public class TicTacToeController implements ActionListener{
	private TicTacToeModel ticTacToeModel;
	private TicTacToeView ticTacToeView;
	
	public TicTacToeController(TicTacToeModel ticTacToeModel, TicTacToeView ticTacToeView){
		this.ticTacToeModel = ticTacToeModel;
		this.ticTacToeView = ticTacToeView;
	}
	
	/**
	 * actionPerformed:		When an action is performed the Controller must react properly
	 * 
	 * @param e:			The action event that just happened 
	 */
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() instanceof JButton){
			JButton b = (JButton) e.getSource();
			int buttonNumber = Integer.parseInt(b.getActionCommand());
			ticTacToeModel.playGame(buttonNumber);
		}
		
		else if (e.getSource() instanceof JMenuItem){
			String command = ((JMenuItem) e.getSource()).getActionCommand();
			if (command.equalsIgnoreCase("quit")){
				ticTacToeView.quit();			
			}
			else if (command.equalsIgnoreCase("new")){
				ticTacToeView.newGame();	
			}
		}		
	}
	
}
