package Cards;

import java.util.Arrays;
import java.util.Optional;

/**
* Description: Implementation of enumeration Value.
* <p>
* Name: Value.java
* @author Afonso Alemão, Rui Daniel, Tomás Fonseca
* @version 20/06/2022
*/
public enum Value {
	/* Possible valid card value (rank) and their hierarchy value */
	
	/**
	* Rank is equal to 2
	*/
	Two('2', 1),
	
	/**
	* Rank is equal to 3
	*/
    Three('3', 2),
    
    /**
	* Rank is equal to 4
	*/
    Four('4', 3),
    
    /**
	* Rank is equal to 5
	*/
	Five('5', 4),
	
	/**
	* Rank is equal to 6
	*/
	Six('6', 5),
	
	/**
	* Rank is equal to 7
	*/
    Seven('7', 6),
    
    /**
	* Rank is equal to 8
	*/
    Eight('8', 7),
    
    /**
	* Rank is equal to 9
	*/
    Nine('9', 8),
    
    /**
	* Rank is equal to 10
	*/
	Ten('T', 9),
	
	/**
	* Rank is equal to Jack
	*/
	Jack('J', 10),
	
	/**
	* Rank is equal to Queen
	*/
    Queen('Q', 11),
    
    /**
	* Rank is equal to King
	*/
    King('K', 12),
    
    /**
	* Rank is equal to Ace
	*/
	Ace('A', 13);
	
    private final char Name;
    private final int HValue;

    /**
     * Description: Constructor for Value.
     * @param Name (char) - card rank (value)
     * @param HValue (char) - hierarchy value of card rank (value)
     * @return None
     */
    Value(char Name, int HValue) {
        this.Name = Name;
        this.HValue = HValue;
    }
    
    /**
     * Description: Gets card's value. 
     * @return char
     */
    public char getName() {
        return Name;
    }
    
    /**
     * Description: Gets card's hierarchy value. 
     * @return int
     */
    public int getHValue() {
        return HValue;
    }
    
    /**
     * Description: Verify if card value is valid.
     * @param value1 (char) - value to check if is valid
     * @return boolean
     */
    public static boolean ValueisValid(char value1) {
    	for (Value var : Value.values()) {
    		if (var.Name == value1) {
    			return true;
    		}
    	}
    	return false;
    }
    
    /**
     * Description: Gets Value.
     * @param Name (char) - name of card value
     * @return Optional&lt;Value&gt;
     */
    public static Optional<Value> getValueByName(char Name) {
        return Arrays.stream(Value.values())
            .filter(value1 -> value1.Name == Name)
            .findFirst();
    }
    
}
