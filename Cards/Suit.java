package Cards;

import java.util.Arrays;
import java.util.Optional;

/**
* Description: Implementation of enumeration Suit.
* <p>
* Name: Suit.java
* @author Afonso Alemão, Rui Daniel, Tomás Fonseca
* @version 20/06/2022
*/
public enum Suit {
	/* Possible valid card suit and their hierarchy value */
	
	/**
	* Suit is equal to Clubs
	*/
	Clubs('C', 4),
	
	/**
	* Suit is equal to Diamonds
	*/
    Diamonds('D', 3),
    
    /**
	* Suit is equal to Hearts
	*/
    Hearts('H', 2),
    
    /**
	* Suit is equal to Spades
	*/
	Spades('S', 1);

    private final char Name;
    private final int HValue;

    /**
     * Description: Constructor for Suit.
     * @param Name (char) - card´s suit
     * @param HValue (char) - hierarchy value of card´s suit
     * @return None
     */
    Suit(char Name, int HValue) {
        this.Name = Name;
        this.HValue = HValue;
    }

    /**
     * Description: Gets card suit. 
     * @return char
     */
    public char getName() {
        return Name;
    }

    /**
     * Description: Gets card suit hierarchy value.
     * @return int
     */
    public int getHValue() {
        return HValue;
    }
    
    /**
     * Description: Verify if card suit is valid.
     * @param suit1 (char) - suit to check if is valid
     * @return boolean
     */
    public static boolean SuitisValid(char suit1) {
    	for (Suit var : Suit.values()) {
    		if (var.Name == suit1 && Character.isUpperCase(suit1)) {
    			return true;
    		}
    	}
    	return false;
    }
    
    /**
     * Description: Gets Suit.
     * @param Name (char) - name of card suit
     * @return Optional&lt;Suit&gt;
     */
    public static Optional<Suit> getSuitByName(char Name) {
        return Arrays.stream(Suit.values())
            .filter(suit1 -> suit1.Name == Name)
            .findFirst();
    }
    
}
