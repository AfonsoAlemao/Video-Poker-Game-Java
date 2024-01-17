package Game;

/**
* Description: Implementation of interface IVideoPokerGame.
* <p>
* Name: VideoPokerGame.java
* @author Afonso Alemão, Rui Daniel, Tomás Fonseca
* @version 20/06/2022
*/
public class VideoPokerGame implements IVideoPokerGame {
	private Variant variant;
	private Mode mode;
	
	/**
     * Description: Print an error message and the program terminates.
     */
	private void ErrorMessage() {
		System.out.println("Mode or variant unavailable");
		System.exit(0);
	}
	
	/**
     * Description: Constructor for Video Poker Game with Debug Mode and DoubleBonus10and7.
     * @param initial_credit (double) - player's initial credit
     * @param cmd (String) - command file name
     * @param cards (String) - cards file name
     * @param IdMode (int) - Mode's ID
     * @param IdVariant (int) - Variant's ID
     */
	public VideoPokerGame(double initial_credit, String cmd, String cards, int IdMode, int IdVariant) {
		if (IdMode == GetIDs.DebugModeId() && IdVariant == GetIDs.DoubleBonus10and7Id()) {
			variant = new DoubleBonus10and7(initial_credit, cards, IdMode);
			mode = new DebugMode(cmd, variant);
		}
		else {
			ErrorMessage();
		}
	}
	
	/**
     * Description: Constructor for Video Poker Game with Simulation Mode and DoubleBonus10and7.
     * @param initial_credit (double) - player's initial credit
     * @param bet (double) - amount to bet in every match of Simulation Mode
     * @param nbdeals (int) - number of deals of Simulation Mode to be played
     * @param IdMode (int) - Mode's ID
     * @param IdVariant (int) - Variant's ID
     */
	public VideoPokerGame(double initial_credit, double bet, int nbdeals, int IdMode, int IdVariant) {
		if (IdMode == GetIDs.SimulationModeId() && IdVariant == GetIDs.DoubleBonus10and7Id()) {
			variant = new DoubleBonus10and7(initial_credit, IdMode);
			mode = new SimulationMode(nbdeals, bet, initial_credit, variant);
		}
		else {
			ErrorMessage();
		}
	}
	
	/**
     * Description: Plays the game with the established mode.
     */
	public void play() {
		mode.play();
	}

}
