package Cards;

import java.util.List;

/**
* Description: Interface for hand.
* <p>
* Name: IHand.java
* @author Afonso Alemão, Rui Daniel, Tomás Fonseca
* @version 20/06/2022
*/
public interface IHand {
	
	/**
     * Description: Get card of hand in specific index.
     * @param position (int) - index 
     * @return Card
     */
	public Card getCard(int position);
	
	/**
     * Description: Get card of hand ordered in that index.
     * @param position (int) - index
     * @return Card
     */
	public Card getCardOrdered(int position);
	
	/**
     * Description: Replace a card in hand.
     * @param card (Card) card to replace
     * @param position (int) - index of card to be replaced
     * @return boolean
     */
	public boolean setCard(Card card, int position);
	
	/**
     * Description: Gets card index in hand.
     * @param c (Card) - card to search
     * @return int
     */
	public int getCardIndex(Card c);
	
	/**
     * Description: Sort cards sorted.
     * @return List&lt;Card&gt;
     */
	public List<Card> Sort(); 
	
	/**
     * Description: Get hand's type.
     * @return int
     */
	public int type();
	
	/**
     * Description: Check if hand's cards have equal suit.
     * @return boolean
     */
	public boolean isEqualSuit();
	
	/**
     * Description: Check if hand is in sequence.
     * @return boolean
     */
	public boolean isInSequence();
	
	/**
     * Description: Check if hand is four of a kind. If it is, return 1 for Four Aces, 
	 * 				return 2 for Four 5–K or return 3 for Four 2–4. 
     * @return int
     */
	public int FourOfAKind();
	
	/**
     * Description: Check if hand is three of a kind.
     * @return boolean
     */
	public boolean isThreeOfAKind();
	
	/**
     * Description: Check if hand is two pair.
     * @return boolean
     */
	public boolean isTwoPair();
	
	/**
     * Description: Check if hand is jacks or better.
     * @return boolean
     */
	public boolean isJacksOrBetter();
	
	/**
     * Description: Check if hand is one pair.
     * @return boolean
     */
	public boolean isOnePair();
	
	/**
     * Description: Check if hand is full house.
     * @return boolean
     */
	public boolean IsFullHouse();
	
	/**
     * Description: Get best strategy for this hand, and check for failure.
     * @param print (boolean) - control the terminal output like "player should hold ..."
     * @param printKeep (boolean) - control the terminal output like "Keep Ace"
     * @return List&lt;Integer&gt; - index of cards player should keep
     */
	public List<Integer> getHoldStrategy(boolean print, boolean printKeep);
	
	/**
     * Description: Check if there are illegal card duplicates in hand.
     * @return boolean
     */
	public int countHighCards();
}
