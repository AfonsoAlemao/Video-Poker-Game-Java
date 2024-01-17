package Cards;

/**
* Description: Interface for card.
* <p>
* Name: ICard.java
* @author Afonso Alemão, Rui Daniel, Tomás Fonseca
* @version 20/06/2022
*/
public interface ICard {
	
	/**
     * Description: Gets card value.
     * @return char
     */
	public char getValue();
	
	/**
     * Description: Gets card suit.
     * @return char
     */
	public char getSuit();
	
	/**
     * Description: Compares two cards and returns the difference between the 
	 * 	hierarchy value of their values.  
     * @param card (Object) - Object that will be compared with the object that called this method
     * @return int
     */
	public int CompareValue(Object card);
	
	/**
     * Description: Compares two cards and returns the difference between the 
	 * 	hierarchy value of their values. 
     * @param value (char) - value to be compared with card´s value.
     * @return int
     */
	public int CompareValue(char value);
	
	/**
     * Description: Compares two cards and returns the difference between the 
	 * 	hierarchy value of their suit. 
     * @param card (Object) - Object that will be compared to the object that called this method
     * @return int
     */
	public int CompareSuit(Object card);
	
	/**
     * Description: Compares two cards and returns the difference between the 
	 * 	hierarchy value of their suit. 
     * @param suit (char) - suit to be compared with card´s suit.
     * @return int
     */
	public int CompareSuit(char suit);
	
	/**
     * Description: Verify if card is a highcard.
     * @return boolean
     */
	public boolean highcard();
	
	/**
     * Description: Verify if card is valid.
     * @return boolean
     */
	public boolean isValid();
}

