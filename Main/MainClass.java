package Main;

import Game.*;

/**
* Description: Main Class that allows acceptance of Command Line Arguments to run the program.
* <p>
* Input program arguments:
* <p>
* -Debug Mode: java -jar &lt;&lt;YOUR-JAR-NAME&gt;&gt;.jar -d credit cmd-file card-file
* <p> 
* -Simulation Mode: java -jar &lt;&lt;YOUR-JAR-NAME&gt;&gt;.jar -s credit bet nbdeals
* <p>
* Name: MainClass.java
* @author Afonso Alemão, Rui Daniel, Tomás Fonseca
* @version 20/06/2022
*/
public class MainClass {
	
	/**
     * Description: Default Constructor for MainClass.
     */
	public MainClass() {
	}
	
	/**
     * Description: Validate the arguments and play the game.
     * @param args (String[]) - Command Line Arguments
     */
	public static void main(String[] args) {
		if (args.length != 4) {
			Error();
		}
		try {
			if (args[0].equals("-d")) {
				double initialCredit = Double.parseDouble(args[1]);
				VideoPokerGame game = new VideoPokerGame(initialCredit, args[2], args[3], 
						GetIDs.DebugModeId(), GetIDs.DoubleBonus10and7Id());
				game.play();
			}
			else if (args[0].equals("-s")) {
				double initialCredit = Double.parseDouble(args[1]);
				double bet = Double.parseDouble(args[2]);
				if (bet <= 0) {
					Error();
				}
				int nbdeals = Integer.parseInt(args[3]);
				if (nbdeals <= 0) {
					Error();
				}
				VideoPokerGame game = new VideoPokerGame(initialCredit, bet, nbdeals,
						GetIDs.SimulationModeId(), GetIDs.DoubleBonus10and7Id());
				game.play();
			}
			else {
				Error();
			}
		}
		catch (Exception e) {
			Error();
		}
		System.exit(0);
	}
	
	/**
     * Description: Print an error message and the program terminates.
     */
	private static void Error() {
		System.out.println("Invalid program arguments, run as:\n"
				+ "Debug Mode: java -jar <<YOUR-JAR-NAME>>.jar -d credit cmd-file card-file\n"
				+ "Simulation Mode: java -jar <<YOUR-JAR-NAME>>.jar -s credit bet nbdeals");
		System.exit(0);
	}
}
