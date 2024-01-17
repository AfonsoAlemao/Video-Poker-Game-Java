package Cards;

import java.util.Objects;
import java.util.Optional;

/**
* Description: Implementation of interface ICard.
* <p>
* Name: Cards.java
* @author Afonso Alemão, Rui Daniel, Tomás Fonseca
* @version 20/06/2022
*/
public class Card implements ICard {
	private char value;
	private char suit;
	private Optional<Value> value1;
	private Optional<Suit> suit1;

	/**
     * Description: Constructor for Card. Checks if value and suit are valid.
     * @param value (char) - card rank
     * @param suit (char) - card suit
     */
	public Card(char value, char suit) {
		if (!(Suit.SuitisValid(suit) && Value.ValueisValid(value))) {
			System.out.println("Invalid Card " + value + suit);
			System.exit(0);
		}
		this.value = value;
		this.suit = suit;
		value1 = Value.getValueByName(this.value);
		suit1 = Suit.getSuitByName(this.suit);
	}

	/**
     * Description: Gets card value.
     * @return char
     */
	public char getValue() {
		return value;
	}

	/**
     * Description: Gets card suit.
     * @return char
     */
	public char getSuit() {
		return suit;
	}
	
	/**
     * Description: Polymorphism overriding the card's hash code condition.
     * @return int
     */
	@Override
	public int hashCode() {
		return Objects.hash(suit, value);
	}
	
	/**
     * Description: Polymorphism overriding the card's equals condition.
     * @param obj (Object) - Object that will be compared to the object that called this method
     * @return boolean
     */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Card other = (Card) obj;
		return suit == other.suit && value == other.value;
	}
	
	/**
     * Description: Compares two cards and returns the difference between the 
	 * 	hierarchy value of their values. If a card value is worth more than 
	 *  the other then his hierarchy value is bigger. For instance, an Ace 
	 *  is worth more than a King, hence, the Ace hierarchy value (13) is 
	 *  bigger than the King hierarchy value (12).  
     * @param obj (Object) - Object that will be compared with the object that called this method
     * @return int
     */
	public int CompareValue(Object obj) {
		Card other = (Card) obj;
		Optional<Value> value2 = Value.getValueByName(other.value);
		int aux = 0;
		try {
			aux = value1.get().getHValue() - value2.get().getHValue();
		}
		catch(Exception e) {
			System.out.println("Illegal card value");
			System.exit(0);
		}
		return aux;
	}
	
	/**
     * Description: Compares two cards and returns the difference between the 
	 * 	hierarchy value of their values. 
     * @param value (char) - value to be compared with card´s value.
     * @return int
     */
	public int CompareValue(char value) {
		Optional<Value> value2 = Value.getValueByName(value);
		int aux = 0;
		try {
			aux = value1.get().getHValue() - value2.get().getHValue();
		}
		catch(Exception e) {
			System.out.println("Illegal card value");
			System.exit(0);
		}
		return aux;
	}
	
	/**
     * Description: Compares two cards and returns the difference between the 
	 * 	hierarchy value of their suit. For example, we established that Clubs 
	 *  worth more than Diamonds, hence, the Clubs hierarchy value (4) is bigger 
	 *  than the Diamonds hierarchy value (3).
     * @param obj (Object) - Object that will be compared to the object that called this method
     * @return int
     */
	public int CompareSuit(Object obj) {
		Card other = (Card) obj;
		Optional<Suit> suit2 = Suit.getSuitByName(other.suit);
		int aux = 0;
		try {
			aux = suit1.get().getHValue() - suit2.get().getHValue();
		}
		catch(Exception e) {
			System.out.println("Illegal card suit");
			System.exit(0);
		}
		return aux;
	}
	
	/**
     * Description: Compares two cards and returns the difference between the 
	 * 	hierarchy value of their suit. 
     * @param suit (char) - suit to be compared with card´s suit.
     * @return int
     */
	public int CompareSuit(char suit) {
		Optional<Suit> suit2 = Suit.getSuitByName(suit);
		int aux = 0;
		try {
			aux = suit1.get().getHValue() - suit2.get().getHValue();
		}
		catch(Exception e) {
			System.out.println("Illegal card suit");
			System.exit(0);
		}
		return aux;
	}
	
	/**
     * Description: Verify if card is a highcard.
     * @return boolean
     */
	public boolean highcard() {
		if (CompareValue('T') > 0) {
			return true;
		}
		else {
			return false;
		}
	}
	
	/**
     * Description: Verify if card is valid.
     * @return boolean
     */
	public boolean isValid() {
		if (Suit.SuitisValid(this.suit) && Value.ValueisValid(this.value)) {
			return true;
		}
		else {
			return false;
		}
	}
	
	/**
     * Description: Convert card description into string.
     * @return String
     */
	@Override
	public String toString() {
		char[] arraychar = new char[2];
		arraychar[0] = this.value;
		arraychar[1] = this.suit;
		return String.valueOf(arraychar);
	}
	
	/* Tests that have been made to verify if class is working as expected */
	/*
	public static void main(String[] args) {
		Card card1 = new Card('8','S');
		if (card1.highcard()) {
			System.out.println("Highcard");
		}
		else {
			System.out.println("Not highcard");
		}
		Card card2 = new Card('T','S');
		if (card2.highcard()) {
			System.out.println("Highcard");
		}
		Card card3 = new Card('i','S');
		if (!card3.isValid()) {
			System.out.println("Not Valid");
		}
		System.out.println(card3.toString()); 
	}
	*/

}
