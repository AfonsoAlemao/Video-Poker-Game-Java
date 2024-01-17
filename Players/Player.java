package Players;

import Cards.*;
import java.util.List;

/**
* Description: Implementation of IPlayer. 
* <p>
* Name: Player.java
* @author Afonso Alemão, Rui Daniel, Tomás Fonseca
* @version 20/06/2022
*/
public class Player implements IPlayer {
	private Hand hand;
	private final double initial_credit;
	private double credit;
	
	/**
     * Description: Constructor of Player.
     * @param init_credit (double) - player's initial credit
     */
	public Player(double init_credit) {
		this.initial_credit = init_credit;
		this.credit = init_credit;
	}

	/**
     * Description: Calls the constructor of Hand, initializing player´s hand.
     * @param card (List&lt;Card&gt;) - cards that will belong to player's hand
     */
	public void setHand(List<Card> card) {
		hand = new Hand(card); 
	}
	
	/**
     * Description: Get player´s current credit.
     * @return double
     */
	public double getCredit() {
		return credit;
	}

	/**
     * Description: Updates player´s credit.
     * @param credit (double) - player's credit to set
     */
	public void setCredit(double credit) {
		this.credit = credit;
	}

	/**
     * Description: Gets player´s initial credit.
     * @return double
     */
	public double getInitialCredit() {
		return initial_credit;
	}
	
	/**
     * Description: Print player's hand description.
     */
	public void printHand() {
		System.out.println(hand.toString());
	}
	
	/**
     * Description: Deducts money from player´s credit.
     * @param money (double) - money to be deducted from player's credit
     */
	public void debit(double money) {
		this.credit -= money;
	}

	/**
     * Description: Increases player´s credit.
     * @param money (double) - money to be added to player's credit
     */
	public void credit(double money) {
		this.credit += money;
	}
	
	/**
     * Description: Gets hand of player.
     * @return Hand
     */
	public Hand getHand() {
		return hand;
	}

	/**
     * Description: Replace a card in hand in a certain position.
     * @param card (Card) - card to replace 
     * @param position (int) - index of card to be replaced
     * @return boolean
     */
	public boolean setHandCard(Card card, int position) {
		if (hand.setCard(card,position)) {
			return true;
		}
		return false;
	}
	
}
