package Game;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

/**
* Description: Subclass of Mode. Implements Debug Mode: accept card and command files and process them.
* <p>
* Name: DebugMode.java
* @author Afonso Alemão, Rui Daniel, Tomás Fonseca
* @version 20/06/2022
*/
public class DebugMode extends Mode {
	private String cmdFile;
	
	/**
     * Description: Constructor of Debug Mode.
     * @param cmd (String) - user command file
     * @param var (Variant) - Video Poker Game variant to be played
     */
	public DebugMode(String cmd, Variant var) {
		this.var = var;
		cmdFile = cmd;
	}
	
	/**
     * Description: Plays a game with the command and the card files.
     */
	public void play() {
		int bet = 5;
		int betPrev = 5;
		
		/* Number of Strings already read */
		int commandIndex = 0;
		
		boolean doNotHold = false;
		Integer holdPos;
		Scanner x = null;		
		String save;
		List<Integer> playerHoldStrategy = new ArrayList<Integer>();
		List<String> commands;
		commands = new ArrayList<String>();
		
		initState();
		
		/* Open file */
		try {
			x = new Scanner(new File(cmdFile));
		}
		catch(Exception e) {
			System.out.println("File not found");
			System.exit(0);
		}
		
		/* Read file into String List */
		while (x.hasNext()) {
			save = x.next();
			commands.add(save);
		}
				
		Iterator<String> commandsIt = commands.iterator();
		
		/* Iterates over all commands */
		while (commandsIt.hasNext()) {
			save = commandsIt.next();
			/* Command bet */
			if (save.equals("b")) {
				/* Check amount of bet */
				if ((commandIndex + 1) < commands.size()) {
					if (DebugMode.IsAnInt(commands.get(commandIndex + 1))) {
						bet = Integer.parseInt(commandsIt.next());
						commandIndex++;
					}
					else {
						bet = betPrev;
					}			
					
					if (var.bet(state, bet, true)) {
						updateState();
						betPrev = bet;
					}
				}
			}
			/* Command deal */
			else if (save.equals("d")) {
				if (var.deal(state, true)) {
					updateState();
				}
			}
			/* Command credit */
			else if (save.equals("$")) {
				var.credit(true);
			}
			/* Command advice */
			else if (save.equals("a")) {
				var.advice(state, true);
			}
			/* Command stats */
			else if (save.equals("s")) {
				var.statistics(state);
			}
			/* Command hold */
			else if (save.equals("h")) {
				while ((commandIndex + 1) < commands.size()) {
					/* Check cards to hold */
					if (DebugMode.IsAnInt(commands.get(commandIndex + 1))) {
						holdPos = Integer.parseInt(commandsIt.next());
						if (holdPos.intValue() > 5 || holdPos.intValue() < 1) {
							System.out.println("\n-cmd h");
							System.out.println("h: illegal command");
							doNotHold = true;
							//break;
						}
						else {
							playerHoldStrategy.add(holdPos);
						}
					}
					else {
						break;
					}
					commandIndex++;
				}
				if (!doNotHold) {
					if (var.hold(state, playerHoldStrategy, bet, true)) {
						updateState();
					}
				}
				doNotHold = false;
				playerHoldStrategy.clear();
			}
			else {
				System.out.println("\n-cmd " + save);
				System.out.println(save + ": illegal command");
			}
			commandIndex++;
		}	
		
		/* Close file */
		try {
			x.close();
		}
		catch(Exception e) {
			System.out.println("Could not find file");
			System.exit(0);
		}	
	}
	
	/**
     * Description: Checks if a string can be converted to an integer.
     * @param bet (String) - string to check if has int valid format
     * @return boolean
     */
	private static boolean IsAnInt(String bet) {
		try {
			Integer.parseInt(bet);
		    return true;
		} 
		catch(NumberFormatException e) {
			return false;
		}
	}
	
}