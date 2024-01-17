package Game;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import Cards.*;
import Players.*;

/**
* Description: Implementation of variant Double Bonus 10/7. 
* <p>
* Name: DoubleBonus10and7.java
* @author Afonso Alemão, Rui Daniel, Tomás Fonseca
* @version 20/06/2022
*/
public class DoubleBonus10and7 extends Variant {
	private Deck cardDeck;
	private Player player1;
	private List <Integer> stats;
	private int modeID;
	private double allGain;
	private double allBeted;
	
	private final double[][] payment = new double[12][5];
	
	/**
     * Description: Constructor for Simulation Mode. Creates 52 card typical deck.
     * @param init_credit (double) - player's initial credit
     * @param modeID (int) -  Mode's ID
     */
	public DoubleBonus10and7(double init_credit, int modeID) {
		if (GetIDs.SimulationModeId() != modeID) {
			System.exit(0);	
		}
		this.modeID = modeID;
		cardDeck = new Deck(52);
		player1 = new Player(init_credit);
		stats = new ArrayList<Integer>(Collections.nCopies(12, 0));
		paymentInit();
		allGain = 0;
	}
	
	/**
     * Description: Constructor for Debug Mode. Creates deck from card file.
     * @param init_credit (double) - player's initial credit
     * @param fileName (String) - card file name
     * @param modeID (int) -  Mode's ID
     */
	public DoubleBonus10and7(double init_credit, String fileName, int modeID) {
		if (GetIDs.DebugModeId() != modeID) {
			System.exit(0);	
		}
		this.modeID = modeID;
		cardDeck = new Deck(fileName);
		player1 = new Player(init_credit);
		stats = new ArrayList<Integer>(Collections.nCopies(12, 0));
		paymentInit();
		allGain = 0;
	}
	
	/**
     * Description: Initializes payment table for this variant.
     */
	private void paymentInit() {
		payment[0][0] = 1;
		payment[0][1] = 2;
		payment[0][2] = 3;
		payment[0][3] = 4;
		payment[0][4] = 5;
		
		payment[1][0] = 1;
		payment[1][1] = 2;
		payment[1][2] = 3;
		payment[1][3] = 4;
		payment[1][4] = 5;
		
		payment[2][0] = 3;
		payment[2][1] = 6;
		payment[2][2] = 9;
		payment[2][3] = 12;
		payment[2][4] = 15;
		
		payment[3][0] = 5;
		payment[3][1] = 10;
		payment[3][2] = 15;
		payment[3][3] = 20;
		payment[3][4] = 25;
		
		payment[4][0] = 7;
		payment[4][1] = 14;
		payment[4][2] = 21;
		payment[4][3] = 28;
		payment[4][4] = 35;
		
		payment[5][0] = 10;
		payment[5][1] = 20;
		payment[5][2] = 30;
		payment[5][3] = 40;
		payment[5][4] = 50;
		
		payment[6][0] = 50;
		payment[6][1] = 100;
		payment[6][2] = 150;
		payment[6][3] = 200;
		payment[6][4] = 250;
		
		payment[7][0] = 80;
		payment[7][1] = 160;
		payment[7][2] = 240;
		payment[7][3] = 320;
		payment[7][4] = 400;
		
		payment[8][0] = 160;
		payment[8][1] = 320;
		payment[8][2] = 480;
		payment[8][3] = 640;
		payment[8][4] = 800;
		
		payment[9][0] = 50;
		payment[9][1] = 100;
		payment[9][2] = 150;
		payment[9][3] = 200;
		payment[9][4] = 250;
		    
		payment[10][0] = 250;
		payment[10][1] = 500;
		payment[10][2] = 750;
		payment[10][3] = 1000;
		payment[10][4] = 4000;
		
		payment[11][0] = 0;
		payment[11][1] = 0;
		payment[11][2] = 0;
		payment[11][3] = 0;
		payment[11][4] = 0;
	}
	
	/**
     * Description: Get the gain of an hand.
     * @param h (Hand) - player's final hand
     * @param bet (int) - player's bet
     * @return double
     */
	public double paytable(Hand h, int bet) {
		return payment[h.type() - 1][bet - 1];
	}
	
	/**
     * Description: Runs command bet.
     * @param state (char) - current state 
     * @param b (int) - amount player want to bet
     * @param print (boolean) - control the terminal output of this command
     * @return boolean
     */
	public boolean bet(char state, int b, boolean print) {
		/* Check if command is valid in current state */
		if (state != 'h') {
			if (print) {
				System.out.println("\n-cmd b");
				System.out.println("b: illegal command");
			}
			return false;
		}
		
		/* Check if bet is valid */
		if (b > 5 || b < 1) {
			System.out.println("\n-cmd b " + Integer.toString(b));
			System.out.println("b: illegal amount");
			return false;
		}

		/* Removes bet amount from player */
		if (print) {
			System.out.println("\n-cmd b");
			System.out.println("player is betting " + Integer.toString(b));
		}
		player1.debit(b);
		allBeted += b;
		return true;
	}

	/**
     * Description: Runs command hold.
     * @param state (char) - current state 
     * @param keep (List&lt;Integer&gt;) - list of cards to hold
     * @param bet (int) - how much the player bet
     * @param print (boolean) - control the terminal output of this command
     * @return boolean
     */
	public boolean hold(char state, List<Integer> keep, int bet, boolean print) {
		/* Check if command is valid in current state */
		if (state != 'd') {
			if (print) {
				System.out.println("\n-cmd h");
				System.out.println("h: illegal command");
			}
			return false;
		}
		
		Collections.sort(keep);
        
        /* Check the player cards to discard and replace by deck cards */
        List<Boolean> trash = new ArrayList<Boolean>(Collections.nCopies(5, true)); 
        Iterator<Boolean> it1 = trash.iterator();
        Iterator<Integer> it = keep.iterator();
        Integer aux1 = 0, aux2 = 0;
        
        while (it.hasNext()) {
            aux1 = it.next();
            if (aux1.intValue() == aux2.intValue()) {
            	if (print) {
    				System.out.println("\n-cmd h");
    				System.out.println("h: illegal command");
    			}
                return false;
            }
            trash.set(aux1.intValue() - 1, false);
            aux2 = aux1;
        }
        
        if (print) {
			System.out.println("\n-cmd h " + Arrays.toString(keep.toArray()).replace("[", "").replace("]", "").replace(",", ""));
        }
				
		int counter = 0;
		while (it1.hasNext()) {
			if (it1.next().booleanValue()) {
				if (!player1.setHandCard(cardDeck.getCard(), counter)) {
					if (print) {
	    				System.out.println("h: deck is empty");
	    				return false;
	    			}
				}
			}
			counter++;
		}
		
		/* Gets the player's final hand type and checks if he win credit in this bet */
		int type = player1.getHand().type();
		
		if (type >= 1 && type <= 6) {
			stats.set(type - 1, stats.get(type - 1) + 1); 
		}
		/* Four of a kind: type between 7 and 9 */
		else if (type >= 7 && type <= 9) {
			stats.set(6, stats.get(6) + 1); 
		}
		else if (type >= 10 && type <= 12) {
			stats.set(type - 3, stats.get(type - 3) + 1); 
		}
		
		player1.credit(paytable(player1.getHand(), bet));
		allGain += paytable(player1.getHand(), bet);
		if (print) {
			System.out.println("player's hand " + player1.getHand().toString());
			if (paytable(player1.getHand(), bet) > 0) {
				System.out.println("player wins with a " + Hand.typeStr(player1.getHand().type()).toUpperCase() +
						" and his credit is " + Integer.toString((int)Math.floor(player1.getCredit())));
			}
			else {
				System.out.println("player loses and his credit is " + Integer.toString((int)Math.floor(player1.getCredit())));
			}
		}
		return true;
	}
	
	/**
     * Description: Runs command deal.
     * @param state (char) - current state 
     * @param print (boolean) - control the terminal output of this command
     * @return boolean
     */
	public boolean deal(char state, boolean print) {
		/* Check if command is valid in current state */
		if (state != 'b') {
			if (print) {
				System.out.println("\n-cmd d");
				System.out.println("d: illegal command");
			}
			return false;
		}
		
		List<Card> initialHand = new ArrayList<Card>();
		
		/* In simulation mode, a new deck is created each round */
		if (modeID == GetIDs.SimulationModeId()) {
			cardDeck.restoreDeck();
			cardDeck.shuffle();
		}
		
		/* Gets 5 cards from deck to create an hand for the player */
		for (int i = 0; i < 5; i++) {
			Card aux = cardDeck.getCard();
			if (aux == null) {
				if (print) {
					System.out.println("\n-cmd d");
					System.out.println("d: end of deck");
				}
				return false;
			}
			initialHand.add(aux);
		}
		
		if (print) {
			System.out.println("\n-cmd d");
		}
		player1.setHand(initialHand);
		
		if (print) {
			System.out.println("player's hand " + player1.getHand().toString());
		}
		return true;
	}
	
	/**
     * Description: Runs command advice. 
     * @param state (char) - current state 
     * @param print (boolean) - control the terminal output of this command
     * @return boolean
     */
	public boolean advice(char state, boolean print) {
		/* Check if command is valid in current state */
		if (state != 'd') {
			if (print) {
				System.out.println("\n-cmd a");
				System.out.println("a: illegal command");
			}
			return false;
		}
		if (player1.getHand() != null) {
			player1.getHand().getHoldStrategy(print, false);
		}
		else {
			return false;
		}
		return true;
	}
	
	/**
     * Description: Gets a list with the cards the player should hold.
     * @return List&lt;Integer&gt;
     */
	public List<Integer> getArrayBestStrategy() {
		return player1.getHand().getHoldStrategy(false, false);
	}
	
	/**
     * Description: Gets player's current credit. 
     * @param print (boolean) - control the terminal output of this command
     * @return double
     */
	public double credit(boolean print) {
		if (print) {
			System.out.println("\n-cmd $");
			System.out.println("player's credit is " 
			+ Integer.toString((int)Math.floor(player1.getCredit())));
		}
 		
		return player1.getCredit();
	}
	
	/**
     * Description: Gets player's initial credit.
     * @return double
     */
	public double getInitialCredit() {
		return player1.getInitialCredit();
	}
	
	/**
     * Description: Displays the stats of the game. 
     * @param state (char) - current state 
     */
	public void statistics(char state) {
		/* Check if command is valid in current state */
		if (state != 'h') {
			System.out.println("\n-cmd s");
			System.out.println("s: illegal command");
			return;
		}
		
		final DecimalFormat df = new DecimalFormat("0.00");
	    		
		System.out.println("\nHand\t\t\t\t\tNb");
		System.out.println("---------------------------------------------------");
		System.out.println(Hand.typeStr(1) + "\t\t\t\t" + Integer.toString(stats.get(0)));
		System.out.println(Hand.typeStr(2) + "\t\t\t\t" + Integer.toString(stats.get(1)));
		System.out.println(Hand.typeStr(3) + "\t\t\t\t" + Integer.toString(stats.get(2)));
		System.out.println(Hand.typeStr(4) + "\t\t\t\t" + Integer.toString(stats.get(3)));
		System.out.println(Hand.typeStr(5) + "\t\t\t\t\t" + Integer.toString(stats.get(4)));
		System.out.println(Hand.typeStr(6) + "\t\t\t\t" + Integer.toString(stats.get(5)));
		System.out.println("Four of a Kind" + "\t\t\t\t" + Integer.toString(stats.get(6)));
		System.out.println(Hand.typeStr(10) + "\t\t\t\t" + Integer.toString(stats.get(7)));
		System.out.println(Hand.typeStr(11) + "\t\t\t\t" + Integer.toString(stats.get(8)));
		System.out.println("Other" + "\t\t\t\t\t" + Integer.toString(stats.get(9)));
		System.out.println("---------------------------------------------------");
		int total = 0;
		for (int i = 0; i < 10; i++) {
			total += stats.get(i);
		}
		System.out.println("Total" + "\t\t\t\t\t" + Integer.toString(total));
		System.out.println("---------------------------------------------------");
		System.out.println("Credit" + "\t\t\t     " 
						   + Integer.toString((int)Math.floor(player1.getCredit())) + "\t(" 
						   + df.format(100 * allGain / allBeted).replace(",", ".") + "%)");
	}
}
