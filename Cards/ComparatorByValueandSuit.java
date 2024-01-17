package Cards;

import java.util.Comparator;

/**
* Description: Implementation of Comparator interface, creating a compare function to help sorting cards. 
* <p>
* Name: ComparatorByValueandSuit.java
* @author Afonso Alemão, Rui Daniel, Tomás Fonseca
* @version 20/06/2022
*/
public class ComparatorByValueandSuit implements Comparator<Card> {
	
	/**
     * Description: Default Constructor for ComparatorByValueandSuit.
     */
	public ComparatorByValueandSuit() {
	}
	
	/**
     * Description: Compares using the hierarchy value, suit.
     * @param o1 (Card) - First Card to be compared
     * @param o2 (Card) - 2nd Card to be compared
     * @return int - Result of comparison
     */
	@Override
	public int compare(Card o1, Card o2) {
		if (o1.CompareValue(o2) > 0) {
			return -1;
		}
		else if (o1.CompareValue(o2) < 0) {
			return 1;
		}
		else {
			if (o1.CompareSuit(o2) > 0) {
				return -1;
			}
			else if (o1.CompareSuit(o2) < 0) {
				return 1;
			}
		}
		return 0;
	}
	
}
