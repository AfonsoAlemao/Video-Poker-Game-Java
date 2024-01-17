package Players;

import java.util.List;

import Cards.Card;
import Cards.Hand;

/**
* Description: Interface for player. 
* <p>
* Name: IPlayer.java
* @author Afonso Alemão, Rui Daniel, Tomás Fonseca
* @version 20/06/2022
*/
public interface IPlayer {
	
	/**
     * Description: Initialize player´s hand.
     * @param card (List&lt;Card&gt;) - cards that will belong to player's hand
     */
	public void setHand(List<Card> card);
	
	/**
     * Description: Get player´s current credit.
     * @return double
     */
	public double getCredit();
	
	/**
     * Description: Updates player´s credit.
     * @param credit (double) - player's credit to set
     */
	public void setCredit(double credit);
	
	/**
     * Description: Gets player´s initial credit.
     * @return double
     */
	public double getInitialCredit();
	
	/**
     * Description: Print player's hand description.
     */
	public void printHand();
	
	/**
     * Description: Deducts money from player´s credit.
     * @param money (double) - money to be deducted from player's credit
     */
	public void debit(double money);
	
	/**
     * Description: Increases player´s credit.
     * @param money (double) - money to be added to player's credit
     */
	public void credit(double money);
	
	/**
     * Description: Gets hand of player.
     * @return Hand
     */
	public Hand getHand();
	
	/**
     * Description: Replace a card in hand in a certain position.
     * @param card (Card) - card to replace 
     * @param position (int) - index of card to be replaced
     * @return boolean
     */
	public boolean setHandCard(Card card, int position);
}
