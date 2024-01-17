package Game;

import java.util.List;
import Cards.Hand;

/**
* Description: Abstract class for video poker game variant's implementation.
* <p>
* Name: Variant.java
* @author Afonso Alemão, Rui Daniel, Tomás Fonseca
* @version 20/06/2022
*/
public abstract class Variant {

	/**
     * Description: Default Constructor for Variant.
     */
	public Variant() {
	}
	
	/**
     * Description: Abstract method to Get Debug Mode ID.
     * @param h (Hand) - player's final hand
     * @param bet (int) - player's bet
     * @return double
     */
	public abstract double paytable(Hand h, int bet);
	
	/**
     * Description: Abstract method to run bet command.
     * @param state (char) - current state
     * @param b (int) - amount player want to bet
     * @param print (boolean) - control the terminal output of this command
     * @return boolean
     */
	public abstract boolean bet(char state, int b, boolean print);
	
	/**
     * Description: Abstract method to run hold command.
     * @param state (char) - current state
     * @param keep (List&lt;Integer&gt;) - list of cards to hold
     * @param bet (int) - how much the player bet
     * @param print (boolean) - control the terminal output of this command
     * @return boolean
     */
	public abstract boolean hold(char state, List<Integer> keep, int bet, boolean print);
	
	/**
     * Description: Abstract method to run deal command.
     * @param state (char) - current state
     * @param print (boolean) - control the terminal output of this command
     * @return boolean
     */
	public abstract boolean deal(char state, boolean print);
	
	/**
     * Description: Abstract method to run advice command.
     * @param state (char) - current state
     * @param print (boolean) - control the terminal output of this command
     * @return boolean
     */
	public abstract boolean advice(char state, boolean print);
	
	/**
     * Description: Abstract method to get player's initial credit.
     * @param print (boolean) - control the terminal output of this command
     * @return double
     */
	public abstract double credit(boolean print);
	
	/**
     * Description: Abstract method to display the statistics of the game.
     * @param state (char) - current state
     */
	public abstract void statistics(char state);
	
	/**
     * Description: Abstract method to get a list with the cards the player should hold.
     * @return List&lt;Integer&gt;
     */
	public abstract List<Integer> getArrayBestStrategy();
}
