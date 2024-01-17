package Cards;

/**
* Description: Interface for deck. 
* <p>
* Name: IDeck.java
* @author Afonso Alemão, Rui Daniel, Tomás Fonseca
* @version 20/06/2022
*/
public interface IDeck {
	
	/**
     * Description: Gets the first element of the deck, removing it.
     * @return Card
     */
	public Card getCard();
	
	/**
     * Description: Restore cards that were previously removed by returning them to the deck.
     */
	public void restoreDeck();
	
	/**
     * Description: Gets the number of cards the deck has.
     * @return int
     */
	public int getnbCards();
	
	/**
     * Description: Adds a card to deck.
     * @param card (Card) - card that will be added to deck
     */
	public void addCardBegining(Card card);
	
	/**
     * Description: Shuffles deck.
     */
	public void shuffle();
}
