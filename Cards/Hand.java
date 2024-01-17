package Cards;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

/**
* Description: Implementation of Hand interface
* <p>
* Name: Hand.java
* @author Afonso Alemão, Rui Daniel, Tomás Fonseca
* @version 20/06/2022
*/
public class Hand implements IHand {
	private int typeSave = 0;
	private int bestStrategySave = 0;
	private boolean isOrdered = false;
	private List<Integer> HoldStrategy;
	private List<Card> cards;
	private List<Card> cardsOrdered;
	
	/**
     * Description: Constructor of hand.
     * @param cards (List&lt;Card&gt;) - cards that will belong to hand
     */
	public Hand(List<Card> cards) {
		if (cards.size() != 5) {
			System.out.println("A hand must have 5 cards");
			System.exit(0);
		}
		this.cards = new ArrayList<Card>(cards);
		this.cardsOrdered = new ArrayList<Card>(cards);
		this.HoldStrategy = new ArrayList<Integer>();
		Collections.sort(cardsOrdered, new ComparatorByValueandSuit());
		isOrdered = true;
		if (haveDuplicates()) {
			System.out.println("player's hand must not have duplicates");
			System.exit(0);
		}
	}
	
	/**
     * Description: Get card of hand in specific index.
     * @param position (int) - index 
     * @return Card
     */
	public Card getCard(int position) {
		return cards.get(position);
	}

	/**
     * Description: Get card of hand ordered in that index.
     * @param position (int) - index
     * @return Card
     */
	public Card getCardOrdered(int position) {
		return cardsOrdered.get(position);
	}
	
	/**
     * Description: Hand description into string.
     * @return String
     */
	@Override
	public String toString() {
		String s = new String();
		
		/* Iterating ArrayList using Iterator */
        Iterator<Card> it = cards.iterator();
 
        /* Holds true till there is single element remaining in the list */
        while (it.hasNext()) {
			s = s.concat(it.next().toString() + " ");
        }
        
		return s;
	}	
	
	/**
     * Description: Replace a card in hand.
     * @param card (Card) card to replace
     * @param position (int) - index of card to be replaced
     * @return boolean
     */
	public boolean setCard(Card card, int position) {
		if (card == null) {
			return false;
		}
		cards.set(position, card);
		isOrdered = false;
		typeSave = 0;
		bestStrategySave = 0;
		HoldStrategy.clear();
		Sort();
		if (haveDuplicates()) {
			System.out.println("player's hand must not have duplicates");
			System.exit(0);
		}
		return true;
	}
	
	/**
     * Description: Gets card index in hand.
     * @param c (Card) - card to search
     * @return int
     */
	public int getCardIndex(Card c) {
		for (int i = 0; i < 5; i++) {
			if (c.equals(cards.get(i))) {
				return i;
			}
		}
		return -1;
	}
	
	/**
     * Description: Get type's official name.
     * @param type (int) - hand's type
     * @return String
     */
	public static String typeStr(int type) {
		String str = new String();
		switch (type) {
			case 1:
				str = "Jacks or Better";
				break;
			case 2:
				str = "Two Pair";
				break;
			case 3:
				str = "Three of a Kind";
				break;
			case 4:
				str = "Straight";
				break;
			case 5:
				str = "Flush";
				break;
			case 6:
				str = "Full House";
				break;
			case 7:
				str = "Four 5-K";
				break;
			case 8:
				str = "Four 2-4";
				break;
			case 9:
				str = "Four Aces";
				break;
			case 10:
				str = "Straight Flush";
				break;
			case 11:
				str = "Royal Flush";
				break;
			case 12:
				str = "Other";
				break;
			default:
				System.out.println("Invalid");
				return null;
		}
		return str;
	}
	
	/**
     * Description: Polymorphism overriding the hand's hash code condition.
     * @return int
     */
	@Override
	public int hashCode() {
		return Objects.hash(cards);
	}

	/**
     * Description: Polymorphism overriding the hand's equals condition.
     * @param obj (Object)
     * @return boolean - Object that will be compared to the object that called this method
     */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Hand other = (Hand) obj;
		return Objects.equals(cards, other.cards);
	}
	
	/**
     * Description: Sort cards sorted.
     * @return List&lt;Card&gt;
     */
	public List<Card> Sort() {
		if (!isOrdered) {
			cardsOrdered.clear();
			cardsOrdered = new ArrayList<Card>(cards);
			Collections.sort(cardsOrdered, new ComparatorByValueandSuit());
			isOrdered = true;
		}
		return cardsOrdered;
	}
	
	/**
     * Description: Get hand's type.
     * @return int
     */
	public int type() {
		
		/* If type is already computed, don't calculate it again */
		if (typeSave != 0) {
			return typeSave;
		}
	    boolean EqualSuit = false, InSequence = false, FiveHigherCards = false;
	    
	    Sort();
	    
	    EqualSuit = isEqualSuit();
	    
	    InSequence = isInSequence();

	    if (InSequence) {
	    	if (getCardOrdered(0).CompareValue('A') == 0 &&
	    		getCardOrdered(1).CompareValue('K') == 0) {
	    		FiveHigherCards = true;
	    	}
	    }		
		
	    if (InSequence) {
	        if (EqualSuit) {
	            if (FiveHigherCards) {
	            	/* 11 : Royal flush */
	            	typeSave = 11;
	                return typeSave; 
	            }
	            else {
	            	/* 10 : Straight Flush */ 
	            	typeSave = 10;
	                return typeSave;
	            }
	        }
	        else {
	        	/* 4 : Straight */
	        	typeSave = 4;
	            return typeSave;
	        }
	    }
	    else {
	        if (EqualSuit) {
	        	/* 5 : Flush */
	        	typeSave = 5;
	            return typeSave;
	        }
	    }
	    
	    int f = FourOfAKind();
	    
	    if (f == 1) {
	    	/* 9 : Four Aces */ 
	    	typeSave = 9;
	    	return typeSave;
	    }
	    else if (f == 2) {
	    	/* 7 : Four 5–K */ 
	    	typeSave = 7;
	    	return typeSave;
	    }
	    else if (f == 3) {
	    	/* 8 : Four 2–4 */ 
	    	typeSave = 8;
	    	return typeSave;
	    }
	    
	    if (IsFullHouse()) {
	    	/* 6 : Full House */
	    	typeSave = 6;
	    	return typeSave;
	    }
	    
	    if (isThreeOfAKind()) {
	    	/* 3 : Three of a Kind */
	    	typeSave = 3;
	    	return typeSave;     
	    }

	    if (isTwoPair()) {
	    	/* 2 : Two Pair */
	    	typeSave = 2;
	    	return typeSave;
	    }
	    
	    /* 1 : Jacks or Better */
	    if (isJacksOrBetter()) {
	    	typeSave = 1;
	    	return typeSave;
	    }
	    
	    typeSave = 12;
	    return typeSave;
	}		

	/**
     * Description: Check if hand's cards have equal suit.
     * @return boolean
     */
	public boolean isEqualSuit() {
		Sort();
		if (getCardOrdered(0).CompareSuit(getCardOrdered(1)) == 0 && 
		    getCardOrdered(1).CompareSuit(getCardOrdered(2)) == 0 && 
			getCardOrdered(2).CompareSuit(getCardOrdered(3)) == 0 && 
			getCardOrdered(3).CompareSuit(getCardOrdered(4)) == 0) {
			return true;
	    }
		return false;
	}
	
	/**
     * Description: Check if hand is in sequence.
     * @return boolean
     */
	public boolean isInSequence() {
		if (getCardOrdered(0).CompareValue(getCardOrdered(1)) == 1 && 
		    getCardOrdered(1).CompareValue(getCardOrdered(2)) == 1 && 
			getCardOrdered(2).CompareValue(getCardOrdered(3)) == 1 && 
			getCardOrdered(3).CompareValue(getCardOrdered(4)) == 1) {
			return true;
		}
		else if (getCardOrdered(0).getValue() == 'A' && getCardOrdered(4).getValue() == '2' &&
				 getCardOrdered(1).CompareValue(getCardOrdered(2)) == 1 && 
				 getCardOrdered(2).CompareValue(getCardOrdered(3)) == 1 && 
				 getCardOrdered(3).CompareValue(getCardOrdered(4)) == 1) {
			return true;
		}
		return false;
	}
	
	/**
     * Description: Check if hand is four of a kind. If it is, return 1 for Four Aces, 
	 * 				return 2 for Four 5–K or return 3 for Four 2–4. 
     * @return int
     */
	public int FourOfAKind() {
		Sort();
		boolean isFourOfAKind = false;
		/* Possibilities aaaab or baaaa */
		if (getCardOrdered(0).CompareValue(getCardOrdered(1)) == 0 && 
			getCardOrdered(1).CompareValue(getCardOrdered(2)) == 0 && 
		 	getCardOrdered(2).CompareValue(getCardOrdered(3)) == 0 && 
		 	getCardOrdered(3).CompareValue(getCardOrdered(4)) != 0) {
			isFourOfAKind = true;
		}
		else if (getCardOrdered(0).CompareValue(getCardOrdered(1)) != 0 && 
				 getCardOrdered(1).CompareValue(getCardOrdered(2)) == 0 && 
			 	 getCardOrdered(2).CompareValue(getCardOrdered(3)) == 0 && 
			 	 getCardOrdered(3).CompareValue(getCardOrdered(4)) == 0) {
			isFourOfAKind = true;
		}
		if (isFourOfAKind) {
			if (getCardOrdered(2).CompareValue('A') == 0) {
				return 1;
			}
			else if (getCardOrdered(2).CompareValue('4') > 0) {
				return 2;
			}
			else {
				return 3;
			}
		}
		return 0;
	}
	
	/**
     * Description: Check if hand is three of a kind.
     * @return boolean
     */
	public boolean isThreeOfAKind() {
		Sort();
		 /* Possibilities aaabc or baaac ou bcaaa */
		if (getCardOrdered(0).CompareValue(getCardOrdered(1)) == 0 && 
			getCardOrdered(1).CompareValue(getCardOrdered(2)) == 0 && 
		 	getCardOrdered(0).CompareValue(getCardOrdered(3)) != 0 && 
		 	getCardOrdered(0).CompareValue(getCardOrdered(4)) != 0 && 
		 	getCardOrdered(3).CompareValue(getCardOrdered(4)) != 0) {
			return true;
		}
		else if (getCardOrdered(1).CompareValue(getCardOrdered(2)) == 0 && 
				 getCardOrdered(2).CompareValue(getCardOrdered(3)) == 0 && 
			 	 getCardOrdered(1).CompareValue(getCardOrdered(0)) != 0 && 
			 	 getCardOrdered(1).CompareValue(getCardOrdered(4)) != 0  && 
				 getCardOrdered(0).CompareValue(getCardOrdered(4)) != 0) {
			return true;
		}
		else if (getCardOrdered(2).CompareValue(getCardOrdered(3)) == 0 && 
				 getCardOrdered(3).CompareValue(getCardOrdered(4)) == 0 && 
			 	 getCardOrdered(2).CompareValue(getCardOrdered(0)) != 0 && 
			 	 getCardOrdered(2).CompareValue(getCardOrdered(1)) != 0  && 
				 getCardOrdered(0).CompareValue(getCardOrdered(1)) != 0) {
			return true;
		}
		
		return false;
	}
	
	/**
     * Description: Check if hand is two pair.
     * @return boolean
     */
	public boolean isTwoPair() {
		Sort();
		/* Possibilities xx-yy or xxyy- or -xxyy */
		
		if (getCardOrdered(0).CompareValue(getCardOrdered(1)) == 0 && 
			getCardOrdered(3).CompareValue(getCardOrdered(4)) == 0 && 
		 	getCardOrdered(0).CompareValue(getCardOrdered(3)) != 0 && 
		 	getCardOrdered(0).CompareValue(getCardOrdered(2)) != 0 && 
		 	getCardOrdered(2).CompareValue(getCardOrdered(3)) != 0) {
			return true;
		}
		else if (getCardOrdered(0).CompareValue(getCardOrdered(1)) == 0 && 
				 getCardOrdered(2).CompareValue(getCardOrdered(3)) == 0 && 
			 	 getCardOrdered(0).CompareValue(getCardOrdered(2)) != 0 && 
			 	 getCardOrdered(0).CompareValue(getCardOrdered(4)) != 0 && 
				 getCardOrdered(2).CompareValue(getCardOrdered(4)) != 0) {
			return true;
		}
		else if (getCardOrdered(1).CompareValue(getCardOrdered(2)) == 0 && 
				 getCardOrdered(3).CompareValue(getCardOrdered(4)) == 0 && 
			 	 getCardOrdered(1).CompareValue(getCardOrdered(3)) != 0 && 
			 	 getCardOrdered(0).CompareValue(getCardOrdered(1)) != 0 && 
				 getCardOrdered(0).CompareValue(getCardOrdered(3)) != 0) {
			return true;
		}
		return false;
	}
	
	/**
     * Description: Check if hand is jacks or better.
     * @return boolean
     */
	public boolean isJacksOrBetter() {
		Sort();
		/* Possibilities xx--- or -xx-- or --xx- or ---xx && (x = J or Q or K or A) */
		if (getCardOrdered(0).CompareValue(getCardOrdered(1)) == 0 && 
			getCardOrdered(0).CompareValue(getCardOrdered(2)) != 0 && 
		 	getCardOrdered(0).CompareValue(getCardOrdered(3)) != 0 && 
		 	getCardOrdered(0).CompareValue(getCardOrdered(4)) != 0) {
			if (getCardOrdered(0).CompareValue('J') >= 0) {
				return true;
			}
		}
		else if (getCardOrdered(1).CompareValue(getCardOrdered(2)) == 0 && 
				 getCardOrdered(1).CompareValue(getCardOrdered(0)) != 0 && 
				 getCardOrdered(1).CompareValue(getCardOrdered(3)) != 0 && 
				 getCardOrdered(1).CompareValue(getCardOrdered(4)) != 0) {
			if (getCardOrdered(1).CompareValue('J') >= 0) {
				return true;
			}
		}
		else if (getCardOrdered(2).CompareValue(getCardOrdered(3)) == 0 && 
				 getCardOrdered(2).CompareValue(getCardOrdered(0)) != 0 && 
				 getCardOrdered(2).CompareValue(getCardOrdered(1)) != 0 && 
				 getCardOrdered(2).CompareValue(getCardOrdered(4)) != 0) {
			if (getCardOrdered(2).CompareValue('J') >= 0) {
				return true;
			}
		}
		else if (getCardOrdered(3).CompareValue(getCardOrdered(4)) == 0 && 
				 getCardOrdered(3).CompareValue(getCardOrdered(0)) != 0 && 
				 getCardOrdered(3).CompareValue(getCardOrdered(1)) != 0 && 
				 getCardOrdered(3).CompareValue(getCardOrdered(2)) != 0) {
			if (getCardOrdered(3).CompareValue('J') >= 0) {
				return true;
			}
		}
		
		return false;
	}
	
	/**
     * Description: Check if hand is one pair.
     * @return boolean
     */
	public boolean isOnePair() {
		Sort();
		/* Possibilities xx--- or -xx-- or --xx- or ---xx */
		if (getCardOrdered(0).CompareValue(getCardOrdered(1)) == 0 && 
			getCardOrdered(0).CompareValue(getCardOrdered(2)) != 0 && 
		 	getCardOrdered(2).CompareValue(getCardOrdered(3)) != 0 && 
		 	getCardOrdered(3).CompareValue(getCardOrdered(4)) != 0) {
				return true;
		}
		else if (getCardOrdered(1).CompareValue(getCardOrdered(2)) == 0 && 
				 getCardOrdered(1).CompareValue(getCardOrdered(0)) != 0 && 
				 getCardOrdered(1).CompareValue(getCardOrdered(3)) != 0 && 
				 getCardOrdered(3).CompareValue(getCardOrdered(4)) != 0) {
				return true;
		}
		else if (getCardOrdered(2).CompareValue(getCardOrdered(3)) == 0 && 
				 getCardOrdered(0).CompareValue(getCardOrdered(1)) != 0 && 
				 getCardOrdered(1).CompareValue(getCardOrdered(2)) != 0 && 
				 getCardOrdered(3).CompareValue(getCardOrdered(4)) != 0) {
				return true;
		}
		else if (getCardOrdered(3).CompareValue(getCardOrdered(4)) == 0 && 
				 getCardOrdered(0).CompareValue(getCardOrdered(1)) != 0 && 
				 getCardOrdered(1).CompareValue(getCardOrdered(2)) != 0 && 
				 getCardOrdered(2).CompareValue(getCardOrdered(3)) != 0) {
				return true;
		}
		
		return false;
	}
	
	/**
     * Description: Check if hand is full house.
     * @return boolean
     */
	public boolean IsFullHouse() {
		Sort();
		/* Possibilities xxxyy yyxxx */
		if (getCardOrdered(0).CompareValue(getCardOrdered(1)) == 0 && 
			getCardOrdered(1).CompareValue(getCardOrdered(2)) == 0 && 
		 	getCardOrdered(3).CompareValue(getCardOrdered(4)) == 0 && 
		 	getCardOrdered(0).CompareValue(getCardOrdered(3)) != 0) {
			return true;
		}
		else if (getCardOrdered(0).CompareValue(getCardOrdered(1)) == 0 && 
				 getCardOrdered(2).CompareValue(getCardOrdered(3)) == 0 && 
			 	 getCardOrdered(3).CompareValue(getCardOrdered(4)) == 0 && 
			 	 getCardOrdered(0).CompareValue(getCardOrdered(3)) != 0) {
			return true;
		}
		return false;
	}
	
	/**
     * Description: Get best strategy for this hand, and check for failure.
     * @param print (boolean) - control the terminal output like "player should hold ..."
     * @param printKeep (boolean) - control the terminal output like "Keep Ace"
     * @return List&lt;Integer&gt; - index of cards player should keep
     */
	public List<Integer> getHoldStrategy(boolean print, boolean printKeep) {
		if (bestStrategy(print, printKeep) == false) {
			System.out.println("Incorrect use of the program");
			System.out.println("Can't find best strategy");
			System.exit(0);
		}
		return HoldStrategy;
	}

	/**
     * Description: Get best strategy for this hand, creating a list of cards to keep (HoldStrategy).
     * @param print (boolean) - control the terminal output of advice
     * @param printKeep (boolean) - control the terminal output of strategy used
     * @return boolean
     */
	private boolean bestStrategy(boolean print, boolean printKeep) {
		
		int NumberOfCardsHold = 0;
		Sort();
		if (print) {
			System.out.println("\n-cmd a");
		}
		if (bestStrategySave != 0) {
			if (printKeep) {
				PrintKeep();
			}
			
			if (print) {
				PrintHoldAdvice(NumberOfCardsHold);
			}
			return true;
		}
		if (typeSave == 0) {
			type();
		}
		HoldStrategy.clear();
		
		if (typeSave >= 7 && typeSave <= 11) {
			/* 1. Straight flush, four of a kind, royal flush */
			bestStrategySave = 1;
			NumberOfCardsHold = 5;
			HoldStrategy.add(1);
			HoldStrategy.add(2);
			HoldStrategy.add(3);
			HoldStrategy.add(4);
			HoldStrategy.add(5);
		}
		if (bestStrategySave == 0 && 
			getCardOrdered(0).CompareValue(getCardOrdered(1)) != 0 && 
			getCardOrdered(1).CompareValue(getCardOrdered(2)) != 0 && 
			getCardOrdered(2).CompareValue(getCardOrdered(3)) != 0 && 
			getCardOrdered(3).CompareValue(getCardOrdered(4)) != 0 &&
			getCardOrdered(3).CompareValue('T') >= 0 &&
			getCardOrdered(0).CompareSuit(getCardOrdered(1)) == 0 &&
			getCardOrdered(1).CompareSuit(getCardOrdered(2)) == 0 &&
			getCardOrdered(2).CompareSuit(getCardOrdered(3)) == 0) {
			/* 2. 4 to a royal flush: keep 4 cards of same suit of the cards ('A','K','Q','J','T') */
			/* Possibilities: All different, 4th card >= T and 5th card < T or 
			 * one high (or T) pair and all cards >= T */
			/* All 4 cards of the same suit */
			NumberOfCardsHold = 4;
			HoldStrategy.add(1);
			HoldStrategy.add(2);
			HoldStrategy.add(3);
			HoldStrategy.add(4);
			bestStrategySave = 2;
		}
		if (bestStrategySave == 0 && getCardOrdered(4).CompareValue('T') >= 0) {
			/* 2. Cont: pair possibilities xx--- or -xx-- or --xx- or ---xx */
			/* xx--- */
			if (getCardOrdered(0).CompareValue(getCardOrdered(1)) == 0 && 
			    getCardOrdered(1).CompareValue(getCardOrdered(2)) != 0 &&
			    getCardOrdered(2).CompareValue(getCardOrdered(3)) != 0 &&
			    getCardOrdered(3).CompareValue(getCardOrdered(4)) != 0) {
				if (getCardOrdered(0).CompareSuit(getCardOrdered(2)) == 0 &&
				    getCardOrdered(2).CompareSuit(getCardOrdered(3)) == 0 &&
				    getCardOrdered(3).CompareSuit(getCardOrdered(4)) == 0) {
					NumberOfCardsHold = 4;
					HoldStrategy.add(1);
					HoldStrategy.add(3);
					HoldStrategy.add(4);
					HoldStrategy.add(5);
					bestStrategySave = 2;
				}
				else if (getCardOrdered(1).CompareSuit(getCardOrdered(2)) == 0 &&
				    getCardOrdered(2).CompareSuit(getCardOrdered(3)) == 0 &&
				    getCardOrdered(3).CompareSuit(getCardOrdered(4)) == 0) {
					NumberOfCardsHold = 4;
					HoldStrategy.add(2);
					HoldStrategy.add(3);
					HoldStrategy.add(4);
					HoldStrategy.add(5);
					bestStrategySave = 2;
				}	
			}
			/* -xx-- */
			else if (getCardOrdered(1).CompareValue(getCardOrdered(2)) == 0 && 
				    getCardOrdered(0).CompareValue(getCardOrdered(1)) != 0 &&
				    getCardOrdered(2).CompareValue(getCardOrdered(3)) != 0 &&
				    getCardOrdered(3).CompareValue(getCardOrdered(4)) != 0) {
					if (getCardOrdered(0).CompareSuit(getCardOrdered(1)) == 0 &&
					    getCardOrdered(1).CompareSuit(getCardOrdered(3)) == 0 &&
					    getCardOrdered(3).CompareSuit(getCardOrdered(4)) == 0) {
						NumberOfCardsHold = 4;
						HoldStrategy.add(1);
						HoldStrategy.add(2);
						HoldStrategy.add(4);
						HoldStrategy.add(5);
						bestStrategySave = 2;
					}
					else if (getCardOrdered(0).CompareSuit(getCardOrdered(2)) == 0 &&
					    getCardOrdered(2).CompareSuit(getCardOrdered(3)) == 0 &&
					    getCardOrdered(3).CompareSuit(getCardOrdered(4)) == 0) {
						NumberOfCardsHold = 4;
						HoldStrategy.add(1);
						HoldStrategy.add(3);
						HoldStrategy.add(4);
						HoldStrategy.add(5);
						bestStrategySave = 2;
					}	
			}
			/* --xx- */
			else if (getCardOrdered(0).CompareValue(getCardOrdered(1)) != 0 && 
				    getCardOrdered(1).CompareValue(getCardOrdered(2)) != 0 &&
				    getCardOrdered(2).CompareValue(getCardOrdered(3)) == 0 &&
				    getCardOrdered(3).CompareValue(getCardOrdered(4)) != 0) {
					if (getCardOrdered(0).CompareSuit(getCardOrdered(1)) == 0 &&
					    getCardOrdered(1).CompareSuit(getCardOrdered(2)) == 0 &&
					    getCardOrdered(2).CompareSuit(getCardOrdered(4)) == 0) {
						NumberOfCardsHold = 4;
						HoldStrategy.add(1);
						HoldStrategy.add(2);
						HoldStrategy.add(3);
						HoldStrategy.add(5);
						bestStrategySave = 2;
					}
					else if (getCardOrdered(0).CompareSuit(getCardOrdered(1)) == 0 &&
					    getCardOrdered(1).CompareSuit(getCardOrdered(3)) == 0 &&
					    getCardOrdered(3).CompareSuit(getCardOrdered(4)) == 0) {
						NumberOfCardsHold = 4;
						HoldStrategy.add(1);
						HoldStrategy.add(2);
						HoldStrategy.add(4);
						HoldStrategy.add(5);
						bestStrategySave = 2;
					}	
			}
			/* ---xx */
			else if (getCardOrdered(0).CompareValue(getCardOrdered(1)) != 0 && 
				    getCardOrdered(1).CompareValue(getCardOrdered(2)) != 0 &&
				    getCardOrdered(2).CompareValue(getCardOrdered(3)) != 0 &&
				    getCardOrdered(3).CompareValue(getCardOrdered(4)) == 0) {
					if (getCardOrdered(0).CompareSuit(getCardOrdered(1)) == 0 &&
					    getCardOrdered(1).CompareSuit(getCardOrdered(2)) == 0 &&
					    getCardOrdered(2).CompareSuit(getCardOrdered(3)) == 0) {
						NumberOfCardsHold = 4;
						HoldStrategy.add(1);
						HoldStrategy.add(2);
						HoldStrategy.add(3);
						HoldStrategy.add(4);
						bestStrategySave = 2;
					}
					else if (getCardOrdered(0).CompareSuit(getCardOrdered(1)) == 0 &&
					    getCardOrdered(1).CompareSuit(getCardOrdered(2)) == 0 &&
					    getCardOrdered(2).CompareSuit(getCardOrdered(4)) == 0) {
						NumberOfCardsHold = 4;
						HoldStrategy.add(1);
						HoldStrategy.add(2);
						HoldStrategy.add(3);
						HoldStrategy.add(5);
						bestStrategySave = 2;
					}	
			}
			/* or AKQJT, one of them with different suit */
			else if (getCardOrdered(0).CompareValue(getCardOrdered(1)) == 1 && 
				     getCardOrdered(1).CompareValue(getCardOrdered(2)) == 1 &&
				     getCardOrdered(2).CompareValue(getCardOrdered(3)) == 1 &&
				     getCardOrdered(3).CompareValue(getCardOrdered(4)) == 1 &&
				     getCardOrdered(0).CompareValue('A') == 0) {
				/* Suit Combinations: */
				/* xxxxy */
				if (getCardOrdered(0).CompareSuit(getCardOrdered(1)) == 0 &&
					getCardOrdered(1).CompareSuit(getCardOrdered(2)) == 0 &&
					getCardOrdered(2).CompareSuit(getCardOrdered(3)) == 0 &&
					getCardOrdered(3).CompareSuit(getCardOrdered(4)) != 0) {
					NumberOfCardsHold = 4;
					HoldStrategy.add(1);
					HoldStrategy.add(2);
					HoldStrategy.add(3);
					HoldStrategy.add(4);
					bestStrategySave = 2;
				}	
				/* xxxyx */
				else if (getCardOrdered(0).CompareSuit(getCardOrdered(1)) == 0 &&
					     getCardOrdered(1).CompareSuit(getCardOrdered(2)) == 0 &&
					     getCardOrdered(2).CompareSuit(getCardOrdered(3)) != 0 &&
					     getCardOrdered(3).CompareSuit(getCardOrdered(4)) != 0 &&
					     getCardOrdered(2).CompareSuit(getCardOrdered(4)) == 0) {
					NumberOfCardsHold = 4;
					HoldStrategy.add(1);
					HoldStrategy.add(2);
					HoldStrategy.add(3);
					HoldStrategy.add(5);
					bestStrategySave = 2;
				}
				/* xxyxx */
				else if (getCardOrdered(0).CompareSuit(getCardOrdered(1)) == 0 &&
					     getCardOrdered(1).CompareSuit(getCardOrdered(2)) != 0 &&
					     getCardOrdered(2).CompareSuit(getCardOrdered(3)) != 0 &&
					     getCardOrdered(3).CompareSuit(getCardOrdered(4)) == 0 &&
					     getCardOrdered(1).CompareSuit(getCardOrdered(3)) == 0) {
					NumberOfCardsHold = 4;
					HoldStrategy.add(1);
					HoldStrategy.add(2);
					HoldStrategy.add(4);
					HoldStrategy.add(5);
					bestStrategySave = 2;
				}
				/* xyxxx */
				else if (getCardOrdered(0).CompareSuit(getCardOrdered(1)) != 0 &&
					     getCardOrdered(1).CompareSuit(getCardOrdered(2)) != 0 &&
					     getCardOrdered(2).CompareSuit(getCardOrdered(3)) == 0 &&
					     getCardOrdered(3).CompareSuit(getCardOrdered(4)) == 0 &&
					     getCardOrdered(0).CompareSuit(getCardOrdered(2)) == 0) {
					NumberOfCardsHold = 4;
					HoldStrategy.add(1);
					HoldStrategy.add(3);
					HoldStrategy.add(4);
					HoldStrategy.add(5);
					bestStrategySave = 2;
				}
				/* yxxxx */
				else if (getCardOrdered(0).CompareSuit(getCardOrdered(1)) != 0 &&
					     getCardOrdered(1).CompareSuit(getCardOrdered(2)) == 0 &&
					     getCardOrdered(2).CompareSuit(getCardOrdered(3)) == 0 &&
					     getCardOrdered(3).CompareSuit(getCardOrdered(4)) == 0) {
					NumberOfCardsHold = 4;
					HoldStrategy.add(2);
					HoldStrategy.add(3);
					HoldStrategy.add(4);
					HoldStrategy.add(5);
					bestStrategySave = 2;
				}
			}
		}
		if (bestStrategySave == 0 &&
			getCardOrdered(0).CompareValue('A') == 0 &&
			getCardOrdered(1).CompareValue('A') == 0 &&
			getCardOrdered(2).CompareValue('A') == 0) {
			/* 3. Three aces */
			HoldStrategy.add(1);
			HoldStrategy.add(2);
			HoldStrategy.add(3);
			NumberOfCardsHold = 3;
			bestStrategySave = 3;
		}
		if (bestStrategySave == 0 && (typeSave == 4 || typeSave == 5 || typeSave == 6)) {
			/* 4. Straight, flush, full house */
			bestStrategySave = 4;
			NumberOfCardsHold = 5;
			HoldStrategy.add(1);
			HoldStrategy.add(2);
			HoldStrategy.add(3);
			HoldStrategy.add(4);
			HoldStrategy.add(5);
		}
		if (typeSave == 3 && bestStrategySave == 0) {
			/* 5. Three of a kind (except aces) */
			if (getCardOrdered(0).CompareValue(getCardOrdered(1)) != 0) {
				if (getCardOrdered(1).CompareValue(getCardOrdered(2)) != 0) {
					HoldStrategy.add(3);
					HoldStrategy.add(4);
					HoldStrategy.add(5);
				}
				else {
					HoldStrategy.add(2);
					HoldStrategy.add(3);
					HoldStrategy.add(4);
				}
			}
			else {
				HoldStrategy.add(1);
				HoldStrategy.add(2);
				HoldStrategy.add(3);
			}
			NumberOfCardsHold = 3;
			bestStrategySave = 5;
		}
		if (bestStrategySave == 0) {
			/* 6. 4 to a straight flush */
			/* Flush analysis */
			boolean suc = false;
			/* SSSSD */
			if (getCardOrdered(0).CompareSuit(getCardOrdered(1)) == 0 &&
				getCardOrdered(0).CompareSuit(getCardOrdered(2)) == 0 &&
				getCardOrdered(0).CompareSuit(getCardOrdered(3)) == 0 &&
				getCardOrdered(0).CompareSuit(getCardOrdered(4)) != 0) {
				HoldStrategy.add(1);
				HoldStrategy.add(2);
				HoldStrategy.add(3);
				HoldStrategy.add(4);
				suc = true;
			}
			/* SSSDS */
			else if (getCardOrdered(0).CompareSuit(getCardOrdered(1)) == 0 &&
					 getCardOrdered(0).CompareSuit(getCardOrdered(2)) == 0 &&
				 	 getCardOrdered(0).CompareSuit(getCardOrdered(4)) == 0 &&
					 getCardOrdered(0).CompareSuit(getCardOrdered(3)) != 0) {
				HoldStrategy.add(1);
				HoldStrategy.add(2);
				HoldStrategy.add(3);
				HoldStrategy.add(5);
				suc = true;
			}
			/* SSDSS */
			else if (getCardOrdered(0).CompareSuit(getCardOrdered(1)) == 0 &&
					 getCardOrdered(0).CompareSuit(getCardOrdered(2)) != 0 &&
					 getCardOrdered(0).CompareSuit(getCardOrdered(3)) == 0 &&
					 getCardOrdered(0).CompareSuit(getCardOrdered(4)) == 0) {
				HoldStrategy.add(1);
				HoldStrategy.add(2);
				HoldStrategy.add(4);
				HoldStrategy.add(5);
				suc = true;
			}			
			/* SDSSS */
			else if (getCardOrdered(0).CompareSuit(getCardOrdered(2)) == 0 &&
					 getCardOrdered(0).CompareSuit(getCardOrdered(3)) == 0 &&
					 getCardOrdered(0).CompareSuit(getCardOrdered(1)) != 0 &&
					 getCardOrdered(0).CompareSuit(getCardOrdered(4)) == 0) {
				HoldStrategy.add(1);
				HoldStrategy.add(3);
				HoldStrategy.add(4);
				HoldStrategy.add(5);
				suc = true;
			}
			/* DSSSS */
			else if (getCardOrdered(1).CompareSuit(getCardOrdered(2)) == 0 &&
					 getCardOrdered(1).CompareSuit(getCardOrdered(3)) == 0 &&
					 getCardOrdered(1).CompareSuit(getCardOrdered(4)) == 0 &&
					 getCardOrdered(1).CompareSuit(getCardOrdered(0)) != 0) {
				HoldStrategy.add(2);
				HoldStrategy.add(3);
				HoldStrategy.add(4);
				HoldStrategy.add(5);
				suc = true;
			}
			
			if (suc) {
				/* Next x conditions: straight: 
				 * Possibilities ex 98765: 9876 or 9875 or 9865 or 9765 */
				/* Include Ace-low A5432: A543 or A542 or A532 or A432 or 5432 */
				if (getCardOrdered(HoldStrategy.get(0) - 1).CompareValue(getCardOrdered(HoldStrategy.get(1) - 1)) == 1 &&
					getCardOrdered(HoldStrategy.get(1) - 1).CompareValue(getCardOrdered(HoldStrategy.get(2) - 1)) == 1 &&
					getCardOrdered(HoldStrategy.get(2) - 1).CompareValue(getCardOrdered(HoldStrategy.get(3) - 1)) == 1) {
					suc = true;
				}
				else if (getCardOrdered(HoldStrategy.get(0) - 1).CompareValue(getCardOrdered(HoldStrategy.get(1) - 1)) == 1 &&
						 getCardOrdered(HoldStrategy.get(1) - 1).CompareValue(getCardOrdered(HoldStrategy.get(2) - 1)) == 1 &&
						 getCardOrdered(HoldStrategy.get(2) - 1).CompareValue(getCardOrdered(HoldStrategy.get(3) - 1)) == 2) {
					suc = true;
				}
				else if (getCardOrdered(HoldStrategy.get(0) - 1).CompareValue(getCardOrdered(HoldStrategy.get(1) - 1)) == 1 &&
						 getCardOrdered(HoldStrategy.get(1) - 1).CompareValue(getCardOrdered(HoldStrategy.get(2) - 1)) == 2 &&
						 getCardOrdered(HoldStrategy.get(2) - 1).CompareValue(getCardOrdered(HoldStrategy.get(3) - 1)) == 1) {
					suc = true;
				}
				else if (getCardOrdered(HoldStrategy.get(0) - 1).CompareValue(getCardOrdered(HoldStrategy.get(1) - 1)) == 1 &&
						 getCardOrdered(HoldStrategy.get(1) - 1).CompareValue(getCardOrdered(HoldStrategy.get(2) - 1)) == 1 &&
						 getCardOrdered(HoldStrategy.get(2) - 1).CompareValue(getCardOrdered(HoldStrategy.get(3) - 1)) == 2) {
					suc = true;
				}
				else if (getCardOrdered(HoldStrategy.get(0) - 1).CompareValue('A') == 0) {
					if (getCardOrdered(HoldStrategy.get(1) - 1).CompareValue('5') == 0) {
						if (getCardOrdered(HoldStrategy.get(1) - 1).CompareValue(getCardOrdered(HoldStrategy.get(2) - 1)) == 1 &&
							getCardOrdered(HoldStrategy.get(2) - 1).CompareValue(getCardOrdered(HoldStrategy.get(3) - 1)) == 1) {
							suc = true;
						}
						else if (getCardOrdered(HoldStrategy.get(1) - 1).CompareValue(getCardOrdered(HoldStrategy.get(2) - 1)) == 1 &&
								 getCardOrdered(HoldStrategy.get(2) - 1).CompareValue(getCardOrdered(HoldStrategy.get(3) - 1)) == 2) {
							suc = true;
						}
						else if (getCardOrdered(HoldStrategy.get(1) - 1).CompareValue(getCardOrdered(HoldStrategy.get(2) - 1)) == 2 &&
								 getCardOrdered(HoldStrategy.get(2) - 1).CompareValue(getCardOrdered(HoldStrategy.get(3) - 1)) == 1) {
							suc = true;
						}
						else {
							suc = false;
						}
					}
					else if (getCardOrdered(HoldStrategy.get(1) - 1).CompareValue('4') == 0) {
						if (getCardOrdered(HoldStrategy.get(1) - 1).CompareValue(getCardOrdered(HoldStrategy.get(2) - 1)) == 1 &&
							getCardOrdered(HoldStrategy.get(2) - 1).CompareValue(getCardOrdered(HoldStrategy.get(3) - 1)) == 1) {
							suc = true;
						}
						else {
							suc = false;
						}
					}
				}
				else {
					suc = false;
				}
				
				if (suc) {
					NumberOfCardsHold = 4;
					bestStrategySave = 6;
				}
				else {
					HoldStrategy.clear();
				}
			}
		}
		if (typeSave == 2 && bestStrategySave == 0) {
			/* 7. Two pair */
			/* Possibilities: ixxyy, xxiyy, xxyyi */
			if (getCardOrdered(0).CompareValue(getCardOrdered(1)) == 0) {
				HoldStrategy.add(1);
				HoldStrategy.add(2);
				if (getCardOrdered(2).CompareValue(getCardOrdered(3)) == 0) {
					/* xxyyi */
					HoldStrategy.add(3);
					HoldStrategy.add(4);
				}
				else {
					/* xxiyy */
					HoldStrategy.add(4);
					HoldStrategy.add(5);
				}	
			}
			else {
				/* ixxyy */
				HoldStrategy.add(2);
				HoldStrategy.add(3);
				HoldStrategy.add(4);
				HoldStrategy.add(5);
			}
			NumberOfCardsHold = 4;
			bestStrategySave = 7;
		}
		if (typeSave == 1 && bestStrategySave == 0) {
			/* 8. High pair */
			if (getCardOrdered(0).CompareValue(getCardOrdered(1)) == 0 && 
				getCardOrdered(0).highcard()) {
				HoldStrategy.add(1);
				HoldStrategy.add(2);
			}
			else if (getCardOrdered(1).CompareValue(getCardOrdered(2)) == 0 && 
					getCardOrdered(1).highcard()) {
				HoldStrategy.add(2);
				HoldStrategy.add(3);
			}
			else if (getCardOrdered(2).CompareValue(getCardOrdered(3)) == 0 && 
					getCardOrdered(2).highcard()) {
				HoldStrategy.add(3);
				HoldStrategy.add(4);
			}
			else {
				HoldStrategy.add(4);
				HoldStrategy.add(5);
			}
			NumberOfCardsHold = 2;
			bestStrategySave = 8;
		}
		if (bestStrategySave == 0) {
			/* 9. 4 to a flush */
			if (getCardOrdered(0).CompareSuit(getCardOrdered(1)) == 0) {
				/* SS */
				if (getCardOrdered(1).CompareSuit(getCardOrdered(2)) == 0) {
					/* SSS */
					if (getCardOrdered(2).CompareSuit(getCardOrdered(3)) == 0) {
						/* SSSS */
						if (getCardOrdered(3).CompareSuit(getCardOrdered(4)) != 0) {
							/* SSSSD */
							HoldStrategy.add(1);
							HoldStrategy.add(2);
							HoldStrategy.add(3);
							HoldStrategy.add(4);
							NumberOfCardsHold = 4;
							bestStrategySave = 9;
						}
					}
					else if (getCardOrdered(0).CompareSuit(getCardOrdered(4)) == 0) {
						/* SSSDS */
						HoldStrategy.add(1);
						HoldStrategy.add(2);
						HoldStrategy.add(3);
						HoldStrategy.add(5);
						NumberOfCardsHold = 4;
						bestStrategySave = 9;
					}
				}
				else if (getCardOrdered(0).CompareSuit(getCardOrdered(3)) == 0 &&
						getCardOrdered(0).CompareSuit(getCardOrdered(4)) == 0) {
					/* SSDSS */
					HoldStrategy.add(1);
					HoldStrategy.add(2);
					HoldStrategy.add(4);
					HoldStrategy.add(5);
					NumberOfCardsHold = 4;
					bestStrategySave = 9;
				}
			}
			else if (getCardOrdered(0).CompareSuit(getCardOrdered(2)) == 0 &&
					getCardOrdered(0).CompareSuit(getCardOrdered(3)) == 0 &&
					getCardOrdered(0).CompareSuit(getCardOrdered(4)) == 0) {
				/* SDSSS */
				HoldStrategy.add(1);
				HoldStrategy.add(3);
				HoldStrategy.add(4);
				HoldStrategy.add(5);
				NumberOfCardsHold = 4;
				bestStrategySave = 9;
			}
			else if (getCardOrdered(1).CompareSuit(getCardOrdered(2)) == 0 &&
					getCardOrdered(1).CompareSuit(getCardOrdered(3)) == 0 &&
					getCardOrdered(1).CompareSuit(getCardOrdered(4)) == 0) {
				/* DSSSS */
				HoldStrategy.add(2);
				HoldStrategy.add(3);
				HoldStrategy.add(4);
				HoldStrategy.add(5);
				NumberOfCardsHold = 4;
				bestStrategySave = 9;
			}
		}
		if (bestStrategySave == 0) {
			/* 10. 3 to a royal flush */
			/* High pair already analised: don't include T */
			int i, NumCardsUpT = 0;
			for (i = 0; i < 5; i++) {
				if (getCardOrdered(i).CompareValue('T') >= 0) {
					NumCardsUpT++;
				}
			}
			if (NumCardsUpT == 3) {
				if (getCardOrdered(0).CompareSuit(getCardOrdered(1)) == 0 &&
					getCardOrdered(1).CompareSuit(getCardOrdered(2)) == 0) {
					HoldStrategy.add(1);
					HoldStrategy.add(2);
					HoldStrategy.add(3);
					NumberOfCardsHold = 3;
					bestStrategySave = 10;
				}			
			}
			else if (NumCardsUpT == 4) {
				/* SSSD SSDS SDSS DSSS */
				if (getCardOrdered(0).CompareSuit(getCardOrdered(1)) == 0 &&
					getCardOrdered(1).CompareSuit(getCardOrdered(2)) == 0 &&
					getCardOrdered(2).CompareSuit(getCardOrdered(3)) != 0) {
					HoldStrategy.add(1);
					HoldStrategy.add(2);
					HoldStrategy.add(3);
					NumberOfCardsHold = 3;
					bestStrategySave = 10;
				}
				else if (getCardOrdered(0).CompareSuit(getCardOrdered(1)) == 0 &&
						 getCardOrdered(1).CompareSuit(getCardOrdered(2)) != 0 &&
						 getCardOrdered(2).CompareSuit(getCardOrdered(3)) != 0 &&
						 getCardOrdered(1).CompareSuit(getCardOrdered(3)) == 0) {
					HoldStrategy.add(1);
					HoldStrategy.add(2);
					HoldStrategy.add(4);
					NumberOfCardsHold = 3;
					bestStrategySave = 10;
				}
				else if (getCardOrdered(0).CompareSuit(getCardOrdered(1)) != 0 &&
						 getCardOrdered(1).CompareSuit(getCardOrdered(2)) != 0 &&
						 getCardOrdered(2).CompareSuit(getCardOrdered(3)) == 0 &&
						 getCardOrdered(0).CompareSuit(getCardOrdered(3)) == 0) {
					HoldStrategy.add(1);
					HoldStrategy.add(3);
					HoldStrategy.add(4);
					NumberOfCardsHold = 3;
					bestStrategySave = 10;
				}
				else if (getCardOrdered(0).CompareSuit(getCardOrdered(1)) != 0 &&
						 getCardOrdered(1).CompareSuit(getCardOrdered(2)) == 0 &&
						 getCardOrdered(2).CompareSuit(getCardOrdered(3)) == 0) {
					HoldStrategy.add(2);
					HoldStrategy.add(3);
					HoldStrategy.add(4);
					NumberOfCardsHold = 3;
					bestStrategySave = 10;
				}
			}
		}
		if (bestStrategySave == 0) {
			/* 11. 4 to an outside straight */
			if (getCardOrdered(0).CompareValue(getCardOrdered(1)) != 1 &&
				getCardOrdered(1).CompareValue(getCardOrdered(2)) == 1 &&
				getCardOrdered(2).CompareValue(getCardOrdered(3)) == 1 &&
				getCardOrdered(3).CompareValue(getCardOrdered(4)) == 1) {
				/* x9876 */
				HoldStrategy.add(2);
				HoldStrategy.add(3);
				HoldStrategy.add(4);
				HoldStrategy.add(5);
				NumberOfCardsHold = 4;
				bestStrategySave = 11;
			}
			else if (getCardOrdered(0).CompareValue(getCardOrdered(2)) == 1 &&
					 getCardOrdered(2).CompareValue(getCardOrdered(3)) == 1 &&
					 getCardOrdered(3).CompareValue(getCardOrdered(4)) == 1) {
				/* 9x876 */
				HoldStrategy.add(1);
				HoldStrategy.add(3);
				HoldStrategy.add(4);
				HoldStrategy.add(5);
				NumberOfCardsHold = 4;
				bestStrategySave = 11;
			}
			else if (getCardOrdered(0).CompareValue(getCardOrdered(1)) == 1 &&
					 getCardOrdered(1).CompareValue(getCardOrdered(3)) == 1 &&
					 getCardOrdered(3).CompareValue(getCardOrdered(4)) == 1) {
				/* 98x76 */
				HoldStrategy.add(1);
				HoldStrategy.add(2);
				HoldStrategy.add(4);
				HoldStrategy.add(5);
				NumberOfCardsHold = 4;
				bestStrategySave = 11;
			}
			else if (getCardOrdered(0).CompareValue(getCardOrdered(1)) == 1 &&
					 getCardOrdered(1).CompareValue(getCardOrdered(2)) == 1 &&
					 getCardOrdered(2).CompareValue(getCardOrdered(4)) == 1) {
				/* 987x6 */
				HoldStrategy.add(1);
				HoldStrategy.add(2);
				HoldStrategy.add(3);
				HoldStrategy.add(5);
				NumberOfCardsHold = 4;
				bestStrategySave = 11;
			}
			else if (getCardOrdered(0).CompareValue(getCardOrdered(1)) == 1 &&
					 getCardOrdered(1).CompareValue(getCardOrdered(2)) == 1 &&
					 getCardOrdered(2).CompareValue(getCardOrdered(3)) == 1 &&
					 getCardOrdered(3).CompareValue(getCardOrdered(4)) != 1 &&
					 getCardOrdered(0).CompareValue('A') != 0) {
				/* 9876x */
				HoldStrategy.add(1);
				HoldStrategy.add(2);
				HoldStrategy.add(3);
				HoldStrategy.add(4);
				NumberOfCardsHold = 4;
				bestStrategySave = 11;
			}
		}
		if (isOnePair() && bestStrategySave == 0) {
			/* 12. Low pair */
			/* Just need to check if is Pair because we already pass High Pair */
			if (getCardOrdered(0).CompareValue(getCardOrdered(1)) == 0 && 
				getCardOrdered(1).CompareValue(getCardOrdered(2)) != 0 && 
			 	getCardOrdered(2).CompareValue(getCardOrdered(3)) != 0 && 
			 	getCardOrdered(3).CompareValue(getCardOrdered(4)) != 0) {
				HoldStrategy.add(1);
				HoldStrategy.add(2);
			}
			else if (getCardOrdered(1).CompareValue(getCardOrdered(2)) == 0 && 
					 getCardOrdered(1).CompareValue(getCardOrdered(0)) != 0 && 
					 getCardOrdered(1).CompareValue(getCardOrdered(3)) != 0 && 
					 getCardOrdered(3).CompareValue(getCardOrdered(4)) != 0) {
				HoldStrategy.add(2);
				HoldStrategy.add(3);
			}
			else if (getCardOrdered(2).CompareValue(getCardOrdered(3)) == 0 && 
					 getCardOrdered(0).CompareValue(getCardOrdered(1)) != 0 && 
					 getCardOrdered(1).CompareValue(getCardOrdered(2)) != 0 && 
					 getCardOrdered(3).CompareValue(getCardOrdered(4)) != 0) {
				HoldStrategy.add(3);
				HoldStrategy.add(4);
			}
			else {
				HoldStrategy.add(4);
				HoldStrategy.add(5);
			}
			
			NumberOfCardsHold = 2;
			bestStrategySave = 12;
		}
		if (getCardOrdered(0).CompareValue('A') == 0 && 
			getCardOrdered(1).CompareValue('K') == 0 && 
			getCardOrdered(2).CompareValue('Q') == 0 && 
			getCardOrdered(3).CompareValue('J') == 0 && 
				bestStrategySave == 0) {
			/* 13. AKQJ unsuited */
			/* We can consider there are no pairs, no 3 of a kind, no 4 of a kind, 
			 * no 4 to a royal flush: they already are unsuited */
			HoldStrategy.add(1);
			HoldStrategy.add(2);
			HoldStrategy.add(3);
			HoldStrategy.add(4);
			NumberOfCardsHold = 4;
			bestStrategySave = 13;
		}
		if (bestStrategySave == 0) {
			/* 14. 3 to a straight flush (type 1) */
			/* Straight flush draw in which the number of high cards equals
			or exceeds the number of gaps (except any ace-low and 234 suited). */
			/* First 4 conditions: flush. */
			/* We know that aren't 4 cards of the same suit */
			boolean suc = false;
			/* DD does not mean same suit */
			/* SSSDD */
			if (getCardOrdered(0).CompareSuit(getCardOrdered(1)) == 0 &&
				getCardOrdered(0).CompareSuit(getCardOrdered(2)) == 0 &&
				getCardOrdered(0).CompareSuit(getCardOrdered(3)) != 0 &&
				getCardOrdered(0).CompareSuit(getCardOrdered(4)) != 0) {
				HoldStrategy.add(1);
				HoldStrategy.add(2);
				HoldStrategy.add(3);
				suc = true;
			}
			/* DDSSS */
			else if (getCardOrdered(2).CompareSuit(getCardOrdered(3)) == 0 &&
					 getCardOrdered(2).CompareSuit(getCardOrdered(4)) == 0 &&
					 getCardOrdered(2).CompareSuit(getCardOrdered(0)) != 0 &&
					 getCardOrdered(2).CompareSuit(getCardOrdered(1)) != 0) {
				HoldStrategy.add(3);
				HoldStrategy.add(4);
				HoldStrategy.add(5);
				suc = true;
			}
			/* SSDSD */
			else if (getCardOrdered(0).CompareSuit(getCardOrdered(1)) == 0 &&
					 getCardOrdered(0).CompareSuit(getCardOrdered(3)) == 0 &&
					 getCardOrdered(0).CompareSuit(getCardOrdered(2)) != 0 &&
					 getCardOrdered(0).CompareSuit(getCardOrdered(4)) != 0) {
				HoldStrategy.add(1);
				HoldStrategy.add(2);
				HoldStrategy.add(4);
				suc = true;
			}			
			/* SDSSD */
			else if (getCardOrdered(0).CompareSuit(getCardOrdered(2)) == 0 &&
					 getCardOrdered(0).CompareSuit(getCardOrdered(3)) == 0 &&
					 getCardOrdered(0).CompareSuit(getCardOrdered(1)) != 0 &&
					 getCardOrdered(0).CompareSuit(getCardOrdered(4)) != 0) {
				HoldStrategy.add(1);
				HoldStrategy.add(3);
				HoldStrategy.add(4);
				suc = true;
			}
			/* DSSSD */
			else if (getCardOrdered(1).CompareSuit(getCardOrdered(2)) == 0 &&
					 getCardOrdered(1).CompareSuit(getCardOrdered(3)) == 0 &&
					 getCardOrdered(1).CompareSuit(getCardOrdered(0)) != 0 &&
					 getCardOrdered(1).CompareSuit(getCardOrdered(4)) != 0) {
				HoldStrategy.add(2);
				HoldStrategy.add(3);
				HoldStrategy.add(4);
				suc = true;
			}
			/* SSDDS */
			else if (getCardOrdered(0).CompareSuit(getCardOrdered(1)) == 0 &&
					 getCardOrdered(0).CompareSuit(getCardOrdered(4)) == 0 &&
					 getCardOrdered(0).CompareSuit(getCardOrdered(2)) != 0 &&
					 getCardOrdered(0).CompareSuit(getCardOrdered(3)) != 0) {
				HoldStrategy.add(1);
				HoldStrategy.add(2);
				HoldStrategy.add(5);
				suc = true;
			}
			/* SDSDS */
			else if (getCardOrdered(0).CompareSuit(getCardOrdered(2)) == 0 &&
					 getCardOrdered(0).CompareSuit(getCardOrdered(4)) == 0 &&
					 getCardOrdered(0).CompareSuit(getCardOrdered(1)) != 0 &&
					 getCardOrdered(0).CompareSuit(getCardOrdered(3)) != 0) {
				HoldStrategy.add(1);
				HoldStrategy.add(3);
				HoldStrategy.add(5);
				suc = true;
			}
			/* DSSDS */
			else if (getCardOrdered(1).CompareSuit(getCardOrdered(2)) == 0 &&
					 getCardOrdered(1).CompareSuit(getCardOrdered(4)) == 0 &&
					 getCardOrdered(1).CompareSuit(getCardOrdered(0)) != 0 &&
					 getCardOrdered(1).CompareSuit(getCardOrdered(3)) != 0) {
				HoldStrategy.add(2);
				HoldStrategy.add(3);
				HoldStrategy.add(5);
				suc = true;
			}
			/* SDDSS */
			else if (getCardOrdered(0).CompareSuit(getCardOrdered(3)) == 0 &&
					 getCardOrdered(0).CompareSuit(getCardOrdered(4)) == 0 &&
					 getCardOrdered(0).CompareSuit(getCardOrdered(1)) != 0 &&
					 getCardOrdered(0).CompareSuit(getCardOrdered(2)) != 0) {
				HoldStrategy.add(1);
				HoldStrategy.add(4);
				HoldStrategy.add(5);
				suc = true;
			}
			/* DSDSS */
			else if (getCardOrdered(1).CompareSuit(getCardOrdered(3)) == 0 &&
					 getCardOrdered(1).CompareSuit(getCardOrdered(4)) == 0 &&
					 getCardOrdered(1).CompareSuit(getCardOrdered(0)) != 0 &&
					 getCardOrdered(1).CompareSuit(getCardOrdered(2)) != 0) {
				HoldStrategy.add(2);
				HoldStrategy.add(4);
				HoldStrategy.add(5);
				suc = true;
			}
			if (suc) {
				int NumberGaps = 0;
				/* Next x conditions: straight: possibilities ex: 987 976 986 965 985 975 */
				/* Except any ace-low and 234 suited */
				/* Include 543 */
				if (getCardOrdered(HoldStrategy.get(0) - 1).CompareValue(getCardOrdered(HoldStrategy.get(1) - 1)) == 1 &&
					getCardOrdered(HoldStrategy.get(1) - 1).CompareValue(getCardOrdered(HoldStrategy.get(2) - 1)) == 1 &&
					getCardOrdered(HoldStrategy.get(0) - 1).CompareValue('4') != 0) {
					NumberGaps = 0;
				}
				else if (getCardOrdered(HoldStrategy.get(0) - 1).CompareValue(getCardOrdered(HoldStrategy.get(1) - 1)) == 2 &&
						 getCardOrdered(HoldStrategy.get(1) - 1).CompareValue(getCardOrdered(HoldStrategy.get(2) - 1)) == 1) {
					NumberGaps = 1;
				}
				else if (getCardOrdered(HoldStrategy.get(0) - 1).CompareValue(getCardOrdered(HoldStrategy.get(1) - 1)) == 1 &&
						 getCardOrdered(HoldStrategy.get(1) - 1).CompareValue(getCardOrdered(HoldStrategy.get(2) - 1)) == 2) {
					NumberGaps = 1;
				}
				else if (getCardOrdered(HoldStrategy.get(0) - 1).CompareValue(getCardOrdered(HoldStrategy.get(1) - 1)) == 3 &&
						 getCardOrdered(HoldStrategy.get(1) - 1).CompareValue(getCardOrdered(HoldStrategy.get(2) - 1)) == 1) {
					NumberGaps = 2;
				}
				else if (getCardOrdered(HoldStrategy.get(0) - 1).CompareValue(getCardOrdered(HoldStrategy.get(1) - 1)) == 1 &&
						 getCardOrdered(HoldStrategy.get(1) - 1).CompareValue(getCardOrdered(HoldStrategy.get(2) - 1)) == 3) {
					NumberGaps = 2;
				}
				else if (getCardOrdered(HoldStrategy.get(0) - 1).CompareValue(getCardOrdered(HoldStrategy.get(1) - 1)) == 2 &&
						 getCardOrdered(HoldStrategy.get(1) - 1).CompareValue(getCardOrdered(HoldStrategy.get(2) - 1)) == 2) {
					NumberGaps = 2;
				}
				else {
					suc = false;
					HoldStrategy.clear();
				}
								
				int NumberHighCards = 0, i;
				/* Number of high cards equals or exceeds the number of gaps. */
				if (suc) {
					for (i = 0; i < 3; i++) {
						if (getCardOrdered(HoldStrategy.get(i) - 1).highcard()) {
							NumberHighCards++;
						}
						else {
							i = 6;
						}
					}
				}
				
				if (suc) {
					if (NumberHighCards >= NumberGaps) {
						NumberOfCardsHold = 3;
						bestStrategySave = 14;
					}
					else {
						HoldStrategy.clear();
					}
				}
			}
		}
		if (bestStrategySave == 0) {
			/* 15. 4 to an inside straight with 3 high cards */
			/* AKQJ or AQJT or AKJT or AKQT or KQJ9 */
			boolean success = false;
			if (getCardOrdered(0).CompareValue('A') == 0) {
				if (getCardOrdered(1).CompareValue('K') == 0) {
					if (getCardOrdered(2).CompareValue('Q') == 0) {
						if (getCardOrdered(3).CompareValue('J') == 0) {
							success = true;
						}
						else if (getCardOrdered(3).CompareValue('T') == 0) {
							success = true;
						}
					}
					else if (getCardOrdered(2).CompareValue('J') == 0) {
						if (getCardOrdered(3).CompareValue('T') == 0) {
							success = true;
						}
					}
				}
				else if (getCardOrdered(1).CompareValue('Q') == 0) {
					if (getCardOrdered(2).CompareValue('J') == 0 &&
						getCardOrdered(3).CompareValue('T') == 0) {
						success = true;
					}
				}
			}
			else if (getCardOrdered(0).CompareValue('K') == 0) {
				if (getCardOrdered(1).CompareValue('Q') == 0 &&
					getCardOrdered(2).CompareValue('J') == 0 &&
					getCardOrdered(3).CompareValue('9') == 0) {
					success = true;
				}
			}
			if (success) {
				HoldStrategy.add(1);
				HoldStrategy.add(2);
				HoldStrategy.add(3);
				HoldStrategy.add(4);
				NumberOfCardsHold = 4;
				bestStrategySave = 15;
			}
		}
		if (bestStrategySave == 0) {
			/* 16. QJ suited */
			/* No pairs */
			for (int i = 0; i < 4; i++) {
				if (getCardOrdered(i).CompareSuit(getCardOrdered(i + 1)) == 0 && 
					getCardOrdered(i).CompareValue('Q') == 0 && 
					getCardOrdered(i + 1).CompareValue('J') == 0) {
					HoldStrategy.add(i + 1);
					HoldStrategy.add(i + 2);
					NumberOfCardsHold = 2;
					bestStrategySave = 16;
					i = 6;
				}
			}			
		}
		if (bestStrategySave == 0) {
			/* 17. 3 to a flush with 2 high cards */
			/* There are no pairs */
			/* I write DD, but they can be different suits */
			if (countHighCards() >= 2) {
				boolean suc = false;
				/* SSSDD */
				if (getCardOrdered(0).CompareSuit(getCardOrdered(1)) == 0 &&
					getCardOrdered(0).CompareSuit(getCardOrdered(2)) == 0 &&
					getCardOrdered(0).CompareSuit(getCardOrdered(3)) != 0 &&
					getCardOrdered(0).CompareSuit(getCardOrdered(4)) != 0 &&
					getCardOrdered(1).highcard()) {
					HoldStrategy.add(1);
					HoldStrategy.add(2);
					HoldStrategy.add(3);
					suc = true;
				}
				/* DDSSS */
				else if (getCardOrdered(2).CompareSuit(getCardOrdered(3)) == 0 &&
						 getCardOrdered(2).CompareSuit(getCardOrdered(4)) == 0 &&
						 getCardOrdered(2).CompareSuit(getCardOrdered(0)) != 0 &&
						 getCardOrdered(2).CompareSuit(getCardOrdered(1)) != 0 &&
						 getCardOrdered(3).highcard()) {
					HoldStrategy.add(3);
					HoldStrategy.add(4);
					HoldStrategy.add(5);
					suc = true;
				}
				/* SSDSD */
				else if (getCardOrdered(0).CompareSuit(getCardOrdered(1)) == 0 &&
						 getCardOrdered(0).CompareSuit(getCardOrdered(3)) == 0 &&
						 getCardOrdered(0).CompareSuit(getCardOrdered(2)) != 0 &&
						 getCardOrdered(0).CompareSuit(getCardOrdered(4)) != 0 &&
						 getCardOrdered(1).highcard()) {
					HoldStrategy.add(1);
					HoldStrategy.add(2);
					HoldStrategy.add(4);
					suc = true;
				}			
				/* SDSSD */
				else if (getCardOrdered(0).CompareSuit(getCardOrdered(2)) == 0 &&
						 getCardOrdered(0).CompareSuit(getCardOrdered(3)) == 0 &&
						 getCardOrdered(0).CompareSuit(getCardOrdered(1)) != 0 &&
						 getCardOrdered(0).CompareSuit(getCardOrdered(4)) != 0 &&
						 getCardOrdered(2).highcard()) {
					HoldStrategy.add(1);
					HoldStrategy.add(3);
					HoldStrategy.add(4);
					suc = true;
				}
				/* DSSSD */
				else if (getCardOrdered(1).CompareSuit(getCardOrdered(2)) == 0 &&
						 getCardOrdered(1).CompareSuit(getCardOrdered(3)) == 0 &&
						 getCardOrdered(1).CompareSuit(getCardOrdered(0)) != 0 &&
						 getCardOrdered(1).CompareSuit(getCardOrdered(4)) != 0 &&
						 getCardOrdered(2).highcard()) {
					HoldStrategy.add(2);
					HoldStrategy.add(3);
					HoldStrategy.add(4);
					suc = true;
				}
				/* SSDDS */
				else if (getCardOrdered(0).CompareSuit(getCardOrdered(1)) == 0 &&
						 getCardOrdered(0).CompareSuit(getCardOrdered(4)) == 0 &&
						 getCardOrdered(0).CompareSuit(getCardOrdered(2)) != 0 &&
						 getCardOrdered(0).CompareSuit(getCardOrdered(3)) != 0 &&
						 getCardOrdered(1).highcard()) {
					HoldStrategy.add(1);
					HoldStrategy.add(2);
					HoldStrategy.add(5);
					suc = true;
				}
				/* SDSDS */
				else if (getCardOrdered(0).CompareSuit(getCardOrdered(2)) == 0 &&
						 getCardOrdered(0).CompareSuit(getCardOrdered(4)) == 0 &&
						 getCardOrdered(0).CompareSuit(getCardOrdered(1)) != 0 &&
						 getCardOrdered(0).CompareSuit(getCardOrdered(3)) != 0 &&
						 getCardOrdered(2).highcard()) {
					HoldStrategy.add(1);
					HoldStrategy.add(3);
					HoldStrategy.add(5);
					suc = true;
				}
				/* DSSDS */
				else if (getCardOrdered(1).CompareSuit(getCardOrdered(2)) == 0 &&
						 getCardOrdered(1).CompareSuit(getCardOrdered(4)) == 0 &&
						 getCardOrdered(1).CompareSuit(getCardOrdered(0)) != 0 &&
						 getCardOrdered(1).CompareSuit(getCardOrdered(3)) != 0 &&
						 getCardOrdered(2).highcard()) {
					HoldStrategy.add(2);
					HoldStrategy.add(3);
					HoldStrategy.add(5);
					suc = true;
				}
				/* SDDSS */
				else if (getCardOrdered(0).CompareSuit(getCardOrdered(3)) == 0 &&
						 getCardOrdered(0).CompareSuit(getCardOrdered(4)) == 0 &&
						 getCardOrdered(0).CompareSuit(getCardOrdered(1)) != 0 &&
						 getCardOrdered(0).CompareSuit(getCardOrdered(2)) != 0 &&
						 getCardOrdered(3).highcard()) {
					HoldStrategy.add(1);
					HoldStrategy.add(4);
					HoldStrategy.add(5);
					suc = true;
				}
				/* DSDSS */
				else if (getCardOrdered(1).CompareSuit(getCardOrdered(3)) == 0 &&
						 getCardOrdered(1).CompareSuit(getCardOrdered(4)) == 0 &&
						 getCardOrdered(1).CompareSuit(getCardOrdered(0)) != 0 &&
						 getCardOrdered(1).CompareSuit(getCardOrdered(2)) != 0 &&
						 getCardOrdered(3).highcard()) {
					HoldStrategy.add(2);
					HoldStrategy.add(4);
					HoldStrategy.add(5);
					suc = true;
				}
				if (suc == true) {
					NumberOfCardsHold = 3;
					bestStrategySave = 17;
				}
			}
		}
		if (bestStrategySave == 0) {
			/* 18. 2 suited high cards */
			/* Impossible to have 3 high cards */
			int aux1 = -1, aux2 = -1;
			for (int i = 0; i < 5; i++) {
				if (getCardOrdered(i).highcard()) {
					if (aux1 == -1) {
						aux1 = i;
					}
					else {
						aux2 = i;
						i = 6;
					}
				}
			}
			if (aux1 != -1 && aux2 != -1) {
				if (getCardOrdered(aux1).CompareSuit(getCardOrdered(aux2)) == 0) {
					HoldStrategy.add(aux1 + 1);
					HoldStrategy.add(aux2 + 1);
					NumberOfCardsHold = 2;
					bestStrategySave = 18;
				}
			}
			
		}
		if (bestStrategySave == 0) {
			/* 19. 4 to an inside straight with 2 high cards */
			/* KJT9 or KQT9 or QJ98(0123 ou 1234) or QJT8(0123 ou 1234) */
			int success = 0;
			if (getCardOrdered(0).CompareValue('K') == 0 && 
				getCardOrdered(1).CompareValue('J') == 0 &&
				getCardOrdered(2).CompareValue('T') == 0 &&
				getCardOrdered(3).CompareValue('9') == 0) {
				success = 1;
			}
			else if (getCardOrdered(0).CompareValue('K') == 0 && 
					 getCardOrdered(1).CompareValue('Q') == 0 &&
					 getCardOrdered(2).CompareValue('T') == 0 &&
					 getCardOrdered(3).CompareValue('9') == 0) {
				success = 1;
			}
			else if (getCardOrdered(0).CompareValue('Q') == 0 && 
					 getCardOrdered(1).CompareValue('J') == 0 &&
					 getCardOrdered(2).CompareValue('9') == 0 &&
					 getCardOrdered(3).CompareValue('8') == 0) {
				success = 1;
			}
			else if (getCardOrdered(1).CompareValue('Q') == 0 && 
					 getCardOrdered(2).CompareValue('J') == 0 &&
					 getCardOrdered(3).CompareValue('9') == 0 &&
					 getCardOrdered(4).CompareValue('8') == 0) {
				success = 2;
			}
			else if (getCardOrdered(0).CompareValue('Q') == 0 && 
					 getCardOrdered(1).CompareValue('J') == 0 &&
					 getCardOrdered(2).CompareValue('T') == 0 &&
					 getCardOrdered(3).CompareValue('8') == 0) {
				success = 1;
			}
			else if (getCardOrdered(1).CompareValue('Q') == 0 && 
					 getCardOrdered(2).CompareValue('J') == 0 &&
					 getCardOrdered(3).CompareValue('T') == 0 &&
					 getCardOrdered(4).CompareValue('8') == 0) {
				success = 2;
			}
			
			if (success == 1) {
				HoldStrategy.add(1);
				HoldStrategy.add(2);
				HoldStrategy.add(3);
				HoldStrategy.add(4);
				NumberOfCardsHold = 4;
				bestStrategySave = 19;
			}
			else if (success == 2) {
				HoldStrategy.add(2);
				HoldStrategy.add(3);
				HoldStrategy.add(4);
				HoldStrategy.add(5);
				NumberOfCardsHold = 4;
				bestStrategySave = 19;
			}	
		}
		if (bestStrategySave == 0) {
			/* 20. 3 to a straight flush (type 2) */
			/* Straight flush draw with one gap, or with two gaps and one
			high card, or any ace-low, or 234 suited */
			/* We know that aren't 4 cards of the same suit */
			boolean suc = false;
			/* DD does not mean same suit */
			/* SSSDD */
			if (getCardOrdered(0).CompareSuit(getCardOrdered(1)) == 0 &&
				getCardOrdered(0).CompareSuit(getCardOrdered(2)) == 0 &&
				getCardOrdered(0).CompareSuit(getCardOrdered(3)) != 0 &&
				getCardOrdered(0).CompareSuit(getCardOrdered(4)) != 0) {
				HoldStrategy.add(1);
				HoldStrategy.add(2);
				HoldStrategy.add(3);
				suc = true;
			}
			/* DDSSS */
			else if (getCardOrdered(2).CompareSuit(getCardOrdered(3)) == 0 &&
					 getCardOrdered(2).CompareSuit(getCardOrdered(4)) == 0 &&
					 getCardOrdered(2).CompareSuit(getCardOrdered(0)) != 0 &&
					 getCardOrdered(2).CompareSuit(getCardOrdered(1)) != 0) {
				HoldStrategy.add(3);
				HoldStrategy.add(4);
				HoldStrategy.add(5);
				suc = true;
			}
			/* SSDSD */
			else if (getCardOrdered(0).CompareSuit(getCardOrdered(1)) == 0 &&
					 getCardOrdered(0).CompareSuit(getCardOrdered(3)) == 0 &&
					 getCardOrdered(0).CompareSuit(getCardOrdered(2)) != 0 &&
					 getCardOrdered(0).CompareSuit(getCardOrdered(4)) != 0) {
				HoldStrategy.add(1);
				HoldStrategy.add(2);
				HoldStrategy.add(4);
				suc = true;
			}			
			/* SDSSD */
			else if (getCardOrdered(0).CompareSuit(getCardOrdered(2)) == 0 &&
					 getCardOrdered(0).CompareSuit(getCardOrdered(3)) == 0 &&
					 getCardOrdered(0).CompareSuit(getCardOrdered(1)) != 0 &&
					 getCardOrdered(0).CompareSuit(getCardOrdered(4)) != 0) {
				HoldStrategy.add(1);
				HoldStrategy.add(3);
				HoldStrategy.add(4);
				suc = true;
			}
			/* DSSSD */
			else if (getCardOrdered(1).CompareSuit(getCardOrdered(2)) == 0 &&
					 getCardOrdered(1).CompareSuit(getCardOrdered(3)) == 0 &&
					 getCardOrdered(1).CompareSuit(getCardOrdered(0)) != 0 &&
					 getCardOrdered(1).CompareSuit(getCardOrdered(4)) != 0) {
				HoldStrategy.add(2);
				HoldStrategy.add(3);
				HoldStrategy.add(4);
				suc = true;
			}
			/* SSDDS */
			else if (getCardOrdered(0).CompareSuit(getCardOrdered(1)) == 0 &&
					 getCardOrdered(0).CompareSuit(getCardOrdered(4)) == 0 &&
					 getCardOrdered(0).CompareSuit(getCardOrdered(2)) != 0 &&
					 getCardOrdered(0).CompareSuit(getCardOrdered(3)) != 0) {
				HoldStrategy.add(1);
				HoldStrategy.add(2);
				HoldStrategy.add(5);
				suc = true;
			}
			/* SDSDS */
			else if (getCardOrdered(0).CompareSuit(getCardOrdered(2)) == 0 &&
					 getCardOrdered(0).CompareSuit(getCardOrdered(4)) == 0 &&
					 getCardOrdered(0).CompareSuit(getCardOrdered(1)) != 0 &&
					 getCardOrdered(0).CompareSuit(getCardOrdered(3)) != 0) {
				HoldStrategy.add(1);
				HoldStrategy.add(3);
				HoldStrategy.add(5);
				suc = true;
			}
			/* DSSDS */
			else if (getCardOrdered(1).CompareSuit(getCardOrdered(2)) == 0 &&
					 getCardOrdered(1).CompareSuit(getCardOrdered(4)) == 0 &&
					 getCardOrdered(1).CompareSuit(getCardOrdered(0)) != 0 &&
					 getCardOrdered(1).CompareSuit(getCardOrdered(3)) != 0) {
				HoldStrategy.add(2);
				HoldStrategy.add(3);
				HoldStrategy.add(5);
				suc = true;
			}
			/* SDDSS */
			else if (getCardOrdered(0).CompareSuit(getCardOrdered(3)) == 0 &&
					 getCardOrdered(0).CompareSuit(getCardOrdered(4)) == 0 &&
					 getCardOrdered(0).CompareSuit(getCardOrdered(1)) != 0 &&
					 getCardOrdered(0).CompareSuit(getCardOrdered(2)) != 0) {
				HoldStrategy.add(1);
				HoldStrategy.add(4);
				HoldStrategy.add(5);
				suc = true;
			}
			/* DSDSS */
			else if (getCardOrdered(1).CompareSuit(getCardOrdered(3)) == 0 &&
					 getCardOrdered(1).CompareSuit(getCardOrdered(4)) == 0 &&
					 getCardOrdered(1).CompareSuit(getCardOrdered(0)) != 0 &&
					 getCardOrdered(1).CompareSuit(getCardOrdered(2)) != 0) {
				HoldStrategy.add(2);
				HoldStrategy.add(4);
				HoldStrategy.add(5);
				suc = true;
			}
			if (suc) {
				int NumberGaps = 0;
				/* Next x conditions: straight: possibilities two gaps ex: 975 */
				/* 1 gap, or with 2 gaps and 1 high card, or any ace-low, or 234 suited */
				/* Next x conditions: straight: possibilities ex: 432 976 986 965 985 975 or 
				 * AceLow: A32 or A43 or A42 or A54 or A53 or A52 or or 542 or 532 or 432*/
				/* Does not include 543 */
				if (getCardOrdered(HoldStrategy.get(0) - 1).CompareValue(getCardOrdered(HoldStrategy.get(1) - 1)) == 1 &&
					getCardOrdered(HoldStrategy.get(1) - 1).CompareValue(getCardOrdered(HoldStrategy.get(2) - 1)) == 1 &&
					getCardOrdered(HoldStrategy.get(0) - 1).CompareValue('4') == 0) {
					NumberGaps = 0;
				}
				else if (getCardOrdered(HoldStrategy.get(0) - 1).CompareValue(getCardOrdered(HoldStrategy.get(1) - 1)) == 2 &&
						 getCardOrdered(HoldStrategy.get(1) - 1).CompareValue(getCardOrdered(HoldStrategy.get(2) - 1)) == 1) {
					NumberGaps = 1;
				}
				else if (getCardOrdered(HoldStrategy.get(0) - 1).CompareValue(getCardOrdered(HoldStrategy.get(1) - 1)) == 1 &&
						 getCardOrdered(HoldStrategy.get(1) - 1).CompareValue(getCardOrdered(HoldStrategy.get(2) - 1)) == 2) {
					NumberGaps = 1;
				}
				else if (getCardOrdered(HoldStrategy.get(0) - 1).CompareValue(getCardOrdered(HoldStrategy.get(1) - 1)) == 3 &&
						 getCardOrdered(HoldStrategy.get(1) - 1).CompareValue(getCardOrdered(HoldStrategy.get(2) - 1)) == 1) {
					NumberGaps = 2;
				}
				else if (getCardOrdered(HoldStrategy.get(0) - 1).CompareValue(getCardOrdered(HoldStrategy.get(1) - 1)) == 1 &&
						 getCardOrdered(HoldStrategy.get(1) - 1).CompareValue(getCardOrdered(HoldStrategy.get(2) - 1)) == 3) {
					NumberGaps = 2;
				}
				else if (getCardOrdered(HoldStrategy.get(0) - 1).CompareValue(getCardOrdered(HoldStrategy.get(1) - 1)) == 2 &&
						 getCardOrdered(HoldStrategy.get(1) - 1).CompareValue(getCardOrdered(HoldStrategy.get(2) - 1)) == 2) {
					NumberGaps = 2;
				}
				else if (getCardOrdered(HoldStrategy.get(0) - 1).CompareValue('A') == 0 &&
						 getCardOrdered(HoldStrategy.get(1) - 1).CompareValue('5') <= 0 ) {
						NumberGaps = 3;
				}
				else {
					suc = false;
					HoldStrategy.clear();
				}
								
				int NumberHighCards = 0, i;
				if (suc) {
					for (i = 0; i < 3; i++) {
						if (getCardOrdered(HoldStrategy.get(i) - 1).highcard()) {
							NumberHighCards++;
						}
						else {
							i = 6;
						}
					}
				}
				
				if (suc) {
					/* NGaps = 0 only for 432: condition above.
					 * NGaps > 2 only for ace-low: condition above */
					if (NumberGaps == 0 || NumberGaps == 1 || (NumberGaps == 2 && NumberHighCards == 1) 
						|| NumberGaps == 3) {
						NumberOfCardsHold = 3;
						bestStrategySave = 20;
					}
					else {
						HoldStrategy.clear();
					}
				}
			}
		}
		if (bestStrategySave == 0) {
			/* 21. 4 to an inside straight with 1 high card */
			/* QT98 (0123 or 1234) or J987 (0123 or 1234) or JT87 (0123 or 1234) or 
			 * JT97 (0123 or 1234) or A543 (0123 or 0234) or A542 (0123 or 0234 or 0124) or 
			 * A532 (0123 or 0234 or 0134) or A432 (0123 or 0234) */
			
			int success = 0;
			if (getCardOrdered(0).CompareValue('Q') == 0 && 
				getCardOrdered(1).CompareValue('T') == 0 &&
				getCardOrdered(2).CompareValue('9') == 0 &&
				getCardOrdered(3).CompareValue('8') == 0) {
				success = 1;
			}
			else if (getCardOrdered(1).CompareValue('Q') == 0 && 
					 getCardOrdered(2).CompareValue('T') == 0 &&
					 getCardOrdered(3).CompareValue('9') == 0 &&
					 getCardOrdered(4).CompareValue('8') == 0) {
				success = 2;
			}
			else if (getCardOrdered(0).CompareValue('J') == 0 && 
					 getCardOrdered(1).CompareValue('9') == 0 &&
					 getCardOrdered(2).CompareValue('8') == 0 &&
					 getCardOrdered(3).CompareValue('7') == 0) {
				success = 1;
			}
			else if (getCardOrdered(1).CompareValue('J') == 0 && 
					 getCardOrdered(2).CompareValue('9') == 0 &&
					 getCardOrdered(3).CompareValue('8') == 0 &&
					 getCardOrdered(4).CompareValue('7') == 0) {
				success = 2;
			}
			else if (getCardOrdered(0).CompareValue('J') == 0 && 
					 getCardOrdered(1).CompareValue('T') == 0 &&
					 getCardOrdered(2).CompareValue('8') == 0 &&
					 getCardOrdered(3).CompareValue('7') == 0) {
				success = 1;
			}
			else if (getCardOrdered(1).CompareValue('J') == 0 && 
					 getCardOrdered(2).CompareValue('T') == 0 &&
					 getCardOrdered(3).CompareValue('8') == 0 &&
					 getCardOrdered(4).CompareValue('7') == 0) {
				success = 2;
			}
			else if (getCardOrdered(0).CompareValue('J') == 0 && 
					 getCardOrdered(1).CompareValue('T') == 0 &&
					 getCardOrdered(2).CompareValue('9') == 0 &&
					 getCardOrdered(3).CompareValue('7') == 0) {
				success = 1;
			}
			else if (getCardOrdered(1).CompareValue('J') == 0 && 
					 getCardOrdered(2).CompareValue('T') == 0 &&
					 getCardOrdered(3).CompareValue('9') == 0 &&
					 getCardOrdered(4).CompareValue('7') == 0) {
				success = 2;
			}
			else if (getCardOrdered(0).CompareValue('A') == 0 && 
					 getCardOrdered(1).CompareValue('5') == 0 &&
					 getCardOrdered(2).CompareValue('4') == 0 &&
					 getCardOrdered(3).CompareValue('3') == 0) {
				success = 1;
			}
			else if (getCardOrdered(0).CompareValue('A') == 0 && 
					 getCardOrdered(2).CompareValue('5') == 0 &&
					 getCardOrdered(3).CompareValue('4') == 0 &&
					 getCardOrdered(4).CompareValue('3') == 0) {
				success = 3;
			}
			else if (getCardOrdered(0).CompareValue('A') == 0 && 
					 getCardOrdered(1).CompareValue('5') == 0 &&
					 getCardOrdered(2).CompareValue('4') == 0 &&
					 getCardOrdered(3).CompareValue('2') == 0) {
				success = 1;
			}
			else if (getCardOrdered(0).CompareValue('A') == 0 && 
					 getCardOrdered(2).CompareValue('5') == 0 &&
					 getCardOrdered(3).CompareValue('4') == 0 &&
					 getCardOrdered(4).CompareValue('2') == 0) {
				success = 3;
			}
			else if (getCardOrdered(0).CompareValue('A') == 0 && 
					 getCardOrdered(1).CompareValue('5') == 0 &&
					 getCardOrdered(2).CompareValue('4') == 0 &&
					 getCardOrdered(4).CompareValue('2') == 0) {
				success = 4;
			}
			else if (getCardOrdered(0).CompareValue('A') == 0 && 
					 getCardOrdered(1).CompareValue('5') == 0 &&
					 getCardOrdered(2).CompareValue('3') == 0 &&
					 getCardOrdered(3).CompareValue('2') == 0) {
				success = 1;
			}
			else if (getCardOrdered(0).CompareValue('A') == 0 && 
					 getCardOrdered(2).CompareValue('5') == 0 &&
					 getCardOrdered(3).CompareValue('3') == 0 &&
					 getCardOrdered(4).CompareValue('2') == 0) {
				success = 3;
			}
			else if (getCardOrdered(0).CompareValue('A') == 0 && 
					 getCardOrdered(1).CompareValue('5') == 0 &&
					 getCardOrdered(3).CompareValue('3') == 0 &&
					 getCardOrdered(4).CompareValue('2') == 0) {
				success = 5;
			}
			else if (getCardOrdered(0).CompareValue('A') == 0 && 
					 getCardOrdered(1).CompareValue('4') == 0 &&
					 getCardOrdered(2).CompareValue('3') == 0 &&
					 getCardOrdered(3).CompareValue('2') == 0) {
				success = 1;
			}
			else if (getCardOrdered(0).CompareValue('A') == 0 && 
					 getCardOrdered(2).CompareValue('4') == 0 &&
					 getCardOrdered(3).CompareValue('3') == 0 &&
					 getCardOrdered(4).CompareValue('2') == 0) {
				success = 3;
			}
			
			if (success >= 1) {
				NumberOfCardsHold = 4;
				bestStrategySave = 21;
			}
			if (success == 1) {
				HoldStrategy.add(1);
				HoldStrategy.add(2);
				HoldStrategy.add(3);
				HoldStrategy.add(4);
			}
			else if (success == 2) {
				HoldStrategy.add(2);
				HoldStrategy.add(3);
				HoldStrategy.add(4);
				HoldStrategy.add(5);
			}
			else if (success == 3) {
				HoldStrategy.add(1);
				HoldStrategy.add(3);
				HoldStrategy.add(4);
				HoldStrategy.add(5);
			}
			else if (success == 4) {
				HoldStrategy.add(1);
				HoldStrategy.add(2);
				HoldStrategy.add(3);
				HoldStrategy.add(5);
			}
			else if (success == 5) {
				HoldStrategy.add(1);
				HoldStrategy.add(2);
				HoldStrategy.add(4);
				HoldStrategy.add(5);
			}
		}
		if (bestStrategySave == 0) {
			/* 22. KQJ unsuited */
			/* We can consider there are no pairs, no 3 of a kind, no 4 of a kind, 
			 * no 4 to a royal flush: they already are unsuited */
			if (getCardOrdered(0).CompareValue('K') == 0 && 
				getCardOrdered(1).CompareValue('Q') == 0 && 
				getCardOrdered(2).CompareValue('J') == 0) {
				HoldStrategy.add(1);
				HoldStrategy.add(2);
				HoldStrategy.add(3);
				NumberOfCardsHold = 3;
				bestStrategySave = 22;
			}
			else if (getCardOrdered(1).CompareValue('K') == 0 && 
					 getCardOrdered(2).CompareValue('Q') == 0 && 
					 getCardOrdered(3).CompareValue('J') == 0) {
				HoldStrategy.add(1);
				HoldStrategy.add(2);
				HoldStrategy.add(3);
				NumberOfCardsHold = 3;
				bestStrategySave = 22;
			}
		}
		if (bestStrategySave == 0) {
			/* 23. JT suited */
			int i;
			for (i = 0; i < 4; i++) {
				if (getCardOrdered(i).CompareSuit(getCardOrdered(i + 1)) == 0 && 
					getCardOrdered(i).CompareValue('J') == 0 && 
					getCardOrdered(i + 1).CompareValue('T') == 0) {
					HoldStrategy.add(i + 1);
					HoldStrategy.add(i + 2);
					i = 6;
					NumberOfCardsHold = 2;
					bestStrategySave = 23;
				}
			}
		}
		if (bestStrategySave == 0) {
			/* 24. QJ unsuited */
			int i;
			for (i = 0; i < 4; i++) {
				if (getCardOrdered(i).CompareValue('Q') == 0 && 
					getCardOrdered(i + 1).CompareValue('J') == 0 && 
					getCardOrdered(i + 1).CompareSuit(getCardOrdered(i)) != 0) {
					HoldStrategy.add(i + 1);
					HoldStrategy.add(i + 2);
					i = 6;
					NumberOfCardsHold = 2;
					bestStrategySave = 24;
				}
			}
			
		}
		if (bestStrategySave == 0) {
			/* 25. 3 to a flush with 1 high card */
			/* DD does not mean same suit */
			if (countHighCards() >= 1) {
				boolean suc = false;
				/* SSSDD */
				if (getCardOrdered(0).CompareSuit(getCardOrdered(1)) == 0 &&
					getCardOrdered(0).CompareSuit(getCardOrdered(2)) == 0 &&
					getCardOrdered(0).CompareSuit(getCardOrdered(3)) != 0 &&
					getCardOrdered(0).CompareSuit(getCardOrdered(4)) != 0 &&
					getCardOrdered(0).highcard()) {
					HoldStrategy.add(1);
					HoldStrategy.add(2);
					HoldStrategy.add(3);
					suc = true;
				}
				/* DDSSS */
				else if (getCardOrdered(2).CompareSuit(getCardOrdered(3)) == 0 &&
						 getCardOrdered(2).CompareSuit(getCardOrdered(4)) == 0 &&
						 getCardOrdered(2).CompareSuit(getCardOrdered(0)) != 0 &&
						 getCardOrdered(2).CompareSuit(getCardOrdered(1)) != 0 &&
						 getCardOrdered(2).highcard()) {
					HoldStrategy.add(3);
					HoldStrategy.add(4);
					HoldStrategy.add(5);
					suc = true;
				}
				/* SSDSD */
				else if (getCardOrdered(0).CompareSuit(getCardOrdered(1)) == 0 &&
						 getCardOrdered(0).CompareSuit(getCardOrdered(3)) == 0 &&
						 getCardOrdered(0).CompareSuit(getCardOrdered(2)) != 0 &&
						 getCardOrdered(0).CompareSuit(getCardOrdered(4)) != 0 &&
						 getCardOrdered(0).highcard()) {
					HoldStrategy.add(1);
					HoldStrategy.add(2);
					HoldStrategy.add(4);
					suc = true;
				}			
				/* SDSSD */
				else if (getCardOrdered(0).CompareSuit(getCardOrdered(2)) == 0 &&
						 getCardOrdered(0).CompareSuit(getCardOrdered(3)) == 0 &&
						 getCardOrdered(0).CompareSuit(getCardOrdered(1)) != 0 &&
						 getCardOrdered(0).CompareSuit(getCardOrdered(4)) != 0 &&
						 getCardOrdered(0).highcard()) {
					HoldStrategy.add(1);
					HoldStrategy.add(3);
					HoldStrategy.add(4);
					suc = true;
				}
				/* DSSSD */
				else if (getCardOrdered(1).CompareSuit(getCardOrdered(2)) == 0 &&
						 getCardOrdered(1).CompareSuit(getCardOrdered(3)) == 0 &&
						 getCardOrdered(1).CompareSuit(getCardOrdered(0)) != 0 &&
						 getCardOrdered(1).CompareSuit(getCardOrdered(4)) != 0 &&
						 getCardOrdered(1).highcard()) {
					HoldStrategy.add(2);
					HoldStrategy.add(3);
					HoldStrategy.add(4);
					suc = true;
				}
				/* SSDDS */
				else if (getCardOrdered(0).CompareSuit(getCardOrdered(1)) == 0 &&
						 getCardOrdered(0).CompareSuit(getCardOrdered(4)) == 0 &&
						 getCardOrdered(0).CompareSuit(getCardOrdered(2)) != 0 &&
						 getCardOrdered(0).CompareSuit(getCardOrdered(3)) != 0 &&
						 getCardOrdered(0).highcard()) {
					HoldStrategy.add(1);
					HoldStrategy.add(2);
					HoldStrategy.add(5);
					suc = true;
				}
				/* SDSDS */
				else if (getCardOrdered(0).CompareSuit(getCardOrdered(2)) == 0 &&
						 getCardOrdered(0).CompareSuit(getCardOrdered(4)) == 0 &&
						 getCardOrdered(0).CompareSuit(getCardOrdered(1)) != 0 &&
						 getCardOrdered(0).CompareSuit(getCardOrdered(3)) != 0 &&
						 getCardOrdered(0).highcard()) {
					HoldStrategy.add(1);
					HoldStrategy.add(3);
					HoldStrategy.add(5);
					suc = true;
				}
				/* DSSDS */
				else if (getCardOrdered(1).CompareSuit(getCardOrdered(2)) == 0 &&
						 getCardOrdered(1).CompareSuit(getCardOrdered(4)) == 0 &&
						 getCardOrdered(1).CompareSuit(getCardOrdered(0)) != 0 &&
						 getCardOrdered(1).CompareSuit(getCardOrdered(3)) != 0 &&
						 getCardOrdered(1).highcard()) {
					HoldStrategy.add(2);
					HoldStrategy.add(3);
					HoldStrategy.add(5);
					suc = true;
				}
				/* SDDSS */
				else if (getCardOrdered(0).CompareSuit(getCardOrdered(3)) == 0 &&
						 getCardOrdered(0).CompareSuit(getCardOrdered(4)) == 0 &&
						 getCardOrdered(0).CompareSuit(getCardOrdered(1)) != 0 &&
						 getCardOrdered(0).CompareSuit(getCardOrdered(2)) != 0 &&
						 getCardOrdered(0).highcard()) {
					HoldStrategy.add(1);
					HoldStrategy.add(4);
					HoldStrategy.add(5);
					suc = true;
				}
				/* DSDSS */
				else if (getCardOrdered(1).CompareSuit(getCardOrdered(3)) == 0 &&
						 getCardOrdered(1).CompareSuit(getCardOrdered(4)) == 0 &&
						 getCardOrdered(1).CompareSuit(getCardOrdered(0)) != 0 &&
						 getCardOrdered(1).CompareSuit(getCardOrdered(2)) != 0 &&
						 getCardOrdered(1).highcard()) {
					HoldStrategy.add(2);
					HoldStrategy.add(4);
					HoldStrategy.add(5);
					suc = true;
				}
				if (suc == true) {
					NumberOfCardsHold = 3;
					bestStrategySave = 25;
				}
			}
		}
		if (bestStrategySave == 0) {
			/* 26. QT suited */
			int i, aux1 = -1, aux2 = -1;
			for (i = 0; i < 5; i++) {
				if (getCardOrdered(i).CompareValue('Q') == 0) {
					aux1 = i;
				}
				else if (getCardOrdered(i).CompareValue('T') == 0) {
					aux2 = i;
					i = 6;
				}
			}
			if (aux1 != -1 && aux2 != -1) {
				if (getCardOrdered(aux1).CompareSuit(getCardOrdered(aux2)) == 0) {
					HoldStrategy.add(aux1 + 1);
					HoldStrategy.add(aux2 + 1);
					NumberOfCardsHold = 2;
					bestStrategySave = 26;
				}
			}
		}
		if (bestStrategySave == 0) {
			/* 27. 3 to a straight flush (type 3) */
			/* Straight flush draw with two gaps and no high cards */
			/* First 4 conditions: flush. */
			/* We know that aren't 4 cards of the same suit */
			boolean suc = false;
			/* DD does not mean same suit */
			/* SSSDD */
			if (getCardOrdered(0).CompareSuit(getCardOrdered(1)) == 0 &&
				getCardOrdered(0).CompareSuit(getCardOrdered(2)) == 0 &&
				getCardOrdered(0).CompareSuit(getCardOrdered(3)) != 0 &&
				getCardOrdered(0).CompareSuit(getCardOrdered(4)) != 0) {
				HoldStrategy.add(1);
				HoldStrategy.add(2);
				HoldStrategy.add(3);
				suc = true;
			}
			/* DDSSS */
			else if (getCardOrdered(2).CompareSuit(getCardOrdered(3)) == 0 &&
					 getCardOrdered(2).CompareSuit(getCardOrdered(4)) == 0 &&
					 getCardOrdered(2).CompareSuit(getCardOrdered(0)) != 0 &&
					 getCardOrdered(2).CompareSuit(getCardOrdered(1)) != 0) {
				HoldStrategy.add(3);
				HoldStrategy.add(4);
				HoldStrategy.add(5);
				suc = true;
			}
			/* SSDSD */
			else if (getCardOrdered(0).CompareSuit(getCardOrdered(1)) == 0 &&
					 getCardOrdered(0).CompareSuit(getCardOrdered(3)) == 0 &&
					 getCardOrdered(0).CompareSuit(getCardOrdered(2)) != 0 &&
					 getCardOrdered(0).CompareSuit(getCardOrdered(4)) != 0) {
				HoldStrategy.add(1);
				HoldStrategy.add(2);
				HoldStrategy.add(4);
				suc = true;
			}			
			/* SDSSD */
			else if (getCardOrdered(0).CompareSuit(getCardOrdered(2)) == 0 &&
					 getCardOrdered(0).CompareSuit(getCardOrdered(3)) == 0 &&
					 getCardOrdered(0).CompareSuit(getCardOrdered(1)) != 0 &&
					 getCardOrdered(0).CompareSuit(getCardOrdered(4)) != 0) {
				HoldStrategy.add(1);
				HoldStrategy.add(3);
				HoldStrategy.add(4);
				suc = true;
			}
			/* DSSSD */
			else if (getCardOrdered(1).CompareSuit(getCardOrdered(2)) == 0 &&
					 getCardOrdered(1).CompareSuit(getCardOrdered(3)) == 0 &&
					 getCardOrdered(1).CompareSuit(getCardOrdered(0)) != 0 &&
					 getCardOrdered(1).CompareSuit(getCardOrdered(4)) != 0) {
				HoldStrategy.add(2);
				HoldStrategy.add(3);
				HoldStrategy.add(4);
				suc = true;
			}
			/* SSDDS */
			else if (getCardOrdered(0).CompareSuit(getCardOrdered(1)) == 0 &&
					 getCardOrdered(0).CompareSuit(getCardOrdered(4)) == 0 &&
					 getCardOrdered(0).CompareSuit(getCardOrdered(2)) != 0 &&
					 getCardOrdered(0).CompareSuit(getCardOrdered(3)) != 0) {
				HoldStrategy.add(1);
				HoldStrategy.add(2);
				HoldStrategy.add(5);
				suc = true;
			}
			/* SDSDS */
			else if (getCardOrdered(0).CompareSuit(getCardOrdered(2)) == 0 &&
					 getCardOrdered(0).CompareSuit(getCardOrdered(4)) == 0 &&
					 getCardOrdered(0).CompareSuit(getCardOrdered(1)) != 0 &&
					 getCardOrdered(0).CompareSuit(getCardOrdered(3)) != 0) {
				HoldStrategy.add(1);
				HoldStrategy.add(3);
				HoldStrategy.add(5);
				suc = true;
			}
			/* DSSDS */
			else if (getCardOrdered(1).CompareSuit(getCardOrdered(2)) == 0 &&
					 getCardOrdered(1).CompareSuit(getCardOrdered(4)) == 0 &&
					 getCardOrdered(1).CompareSuit(getCardOrdered(0)) != 0 &&
					 getCardOrdered(1).CompareSuit(getCardOrdered(3)) != 0) {
				HoldStrategy.add(2);
				HoldStrategy.add(3);
				HoldStrategy.add(5);
				suc = true;
			}
			/* SDDSS */
			else if (getCardOrdered(0).CompareSuit(getCardOrdered(3)) == 0 &&
					 getCardOrdered(0).CompareSuit(getCardOrdered(4)) == 0 &&
					 getCardOrdered(0).CompareSuit(getCardOrdered(1)) != 0 &&
					 getCardOrdered(0).CompareSuit(getCardOrdered(2)) != 0) {
				HoldStrategy.add(1);
				HoldStrategy.add(4);
				HoldStrategy.add(5);
				suc = true;
			}
			/* DSDSS */
			else if (getCardOrdered(1).CompareSuit(getCardOrdered(3)) == 0 &&
					 getCardOrdered(1).CompareSuit(getCardOrdered(4)) == 0 &&
					 getCardOrdered(1).CompareSuit(getCardOrdered(0)) != 0 &&
					 getCardOrdered(1).CompareSuit(getCardOrdered(2)) != 0) {
				HoldStrategy.add(2);
				HoldStrategy.add(4);
				HoldStrategy.add(5);
				suc = true;
			}
			if (suc) {
				int NumberGaps = 0;
				/* Next x conditions: straight: possibilities two gaps ex: 975 or 965 or 985 */
				if (getCardOrdered(HoldStrategy.get(0) - 1).CompareValue(getCardOrdered(HoldStrategy.get(1) - 1)) == 2 &&
					getCardOrdered(HoldStrategy.get(1) - 1).CompareValue(getCardOrdered(HoldStrategy.get(2) - 1)) == 2) {
					NumberGaps = 2;
				}
				else if (getCardOrdered(HoldStrategy.get(0) - 1).CompareValue(getCardOrdered(HoldStrategy.get(1) - 1)) == 1 &&
						 getCardOrdered(HoldStrategy.get(1) - 1).CompareValue(getCardOrdered(HoldStrategy.get(2) - 1)) == 3) {
					NumberGaps = 2;
				}
				else if (getCardOrdered(HoldStrategy.get(0) - 1).CompareValue(getCardOrdered(HoldStrategy.get(1) - 1)) == 3 &&
						 getCardOrdered(HoldStrategy.get(1) - 1).CompareValue(getCardOrdered(HoldStrategy.get(2) - 1)) == 1) {
					NumberGaps = 2;
				}
				else {
					suc = false;
					HoldStrategy.clear();
				}
								
				int NumberHighCards = 0, i;
				/* 2 gaps and no high cards */
				if (suc) {
					for (i = 0; i < 3; i++) {
						if (getCardOrdered(HoldStrategy.get(i) - 1).highcard()) {
							NumberHighCards++;
						}
						else {
							i = 6;
						}
					}
				}
				
				if (suc) {
					if (NumberHighCards == 0 && NumberGaps == 2) {
						NumberOfCardsHold = 3;
						bestStrategySave = 27;
					}
					else {
						HoldStrategy.clear();
					}
				}
			}
		}
		if (bestStrategySave == 0) {
			/* 28. KQ, KJ unsuited */
			if (getCardOrdered(0).CompareValue('K') == 0 &&
				(getCardOrdered(1).CompareValue('Q') == 0 || 
				getCardOrdered(1).CompareValue('J') == 0) &&
				getCardOrdered(0).CompareSuit(getCardOrdered(1)) != 0) {
				HoldStrategy.add(1);
				HoldStrategy.add(2);
				NumberOfCardsHold = 2;
				bestStrategySave = 28;
			}
			else if (getCardOrdered(1).CompareValue('K') == 0 &&
				(getCardOrdered(2).CompareValue('Q') == 0 || 
				getCardOrdered(2).CompareValue('J') == 0) &&
				getCardOrdered(1).CompareSuit(getCardOrdered(2)) != 0) {
				HoldStrategy.add(2);
				HoldStrategy.add(3);
				NumberOfCardsHold = 2;
				bestStrategySave = 28;
			}
		}
		if (bestStrategySave == 0 && getCardOrdered(0).CompareValue('A') == 0) {
			/* 29. Ace */
			HoldStrategy.add(1);
			NumberOfCardsHold = 1;
			bestStrategySave = 29;
		}
		if (bestStrategySave == 0) {
			/* 30. KT suited */
			int i, aux1 = -1, aux2 = -1;
			for (i = 0; i < 5; i++) {
				if (getCardOrdered(i).CompareValue('K') == 0) {
					aux1 = i;
				}
				else if (getCardOrdered(i).CompareValue('T') == 0) {
					aux2 = i;
					i = 6;
				}
			}
			if (aux1 != -1 && aux2 != -1) {
				if (getCardOrdered(aux1).CompareSuit(getCardOrdered(aux2)) == 0) {
					HoldStrategy.add(aux1 + 1);
					HoldStrategy.add(aux2 + 1);
					NumberOfCardsHold = 2;
					bestStrategySave = 30;
				}
			}
		}
		if (bestStrategySave == 0) {
			/* 31. Jack, Queen or King */
			for (int i = 0; i < 5; i++) {
				if (getCardOrdered(i).CompareValue('K') == 0 || 
					getCardOrdered(i).CompareValue('Q') == 0 || 
					getCardOrdered(i).CompareValue('J') == 0) {
					HoldStrategy.add(i + 1);
					i = 6;
					bestStrategySave = 31;
					NumberOfCardsHold = 1;
				}
			}
		}
		if (bestStrategySave == 0) {
			/* 32. 4 to an inside straight with no high cards */
			/* Inside straight – A straight with a missing inside card, such as the cards 679T. */
			/* T876 (0123 or 1234) or T976 (0123 or 1234) or T986 (0123 or 1234) */
			if (getCardOrdered(0).CompareValue(getCardOrdered(1)) == 1 &&
				getCardOrdered(1).CompareValue(getCardOrdered(2)) == 1 &&
				getCardOrdered(2).CompareValue(getCardOrdered(3)) == 2) {
				HoldStrategy.add(1);
				HoldStrategy.add(2);
				HoldStrategy.add(3);
				HoldStrategy.add(4);
				NumberOfCardsHold = 4;
				bestStrategySave = 32;
			}
			else if (getCardOrdered(1).CompareValue(getCardOrdered(2)) == 1 &&
					 getCardOrdered(2).CompareValue(getCardOrdered(3)) == 1 &&
					 getCardOrdered(3).CompareValue(getCardOrdered(4)) == 2) {
				HoldStrategy.add(2);
				HoldStrategy.add(3);
				HoldStrategy.add(4);
				HoldStrategy.add(5);
				NumberOfCardsHold = 4;
				bestStrategySave = 32;
			}
			else if (getCardOrdered(0).CompareValue(getCardOrdered(1)) == 1 &&
					 getCardOrdered(1).CompareValue(getCardOrdered(2)) == 2 &&
					 getCardOrdered(2).CompareValue(getCardOrdered(3)) == 1) {
				HoldStrategy.add(1);
				HoldStrategy.add(2);
				HoldStrategy.add(3);
				HoldStrategy.add(4);
				NumberOfCardsHold = 4;
				bestStrategySave = 32;
			}
			else if (getCardOrdered(1).CompareValue(getCardOrdered(2)) == 1 &&
					 getCardOrdered(2).CompareValue(getCardOrdered(3)) == 2 &&
					 getCardOrdered(3).CompareValue(getCardOrdered(4)) == 1) {
				HoldStrategy.add(2);
				HoldStrategy.add(3);
				HoldStrategy.add(4);
				HoldStrategy.add(5);
				NumberOfCardsHold = 4;
				bestStrategySave = 32;
			}
			else if (getCardOrdered(0).CompareValue(getCardOrdered(1)) == 2 &&
					 getCardOrdered(1).CompareValue(getCardOrdered(2)) == 1 &&
					 getCardOrdered(2).CompareValue(getCardOrdered(3)) == 1) {
				HoldStrategy.add(1);
				HoldStrategy.add(2);
				HoldStrategy.add(3);
				HoldStrategy.add(4);
				NumberOfCardsHold = 4;
				bestStrategySave = 32;
			}
			else if (getCardOrdered(1).CompareValue(getCardOrdered(2)) == 2 &&
					 getCardOrdered(2).CompareValue(getCardOrdered(3)) == 1 &&
					 getCardOrdered(3).CompareValue(getCardOrdered(4)) == 1) {
				HoldStrategy.add(2);
				HoldStrategy.add(3);
				HoldStrategy.add(4);
				HoldStrategy.add(5);
				NumberOfCardsHold = 4;
				bestStrategySave = 32;
			}
		}
		if (bestStrategySave == 0) {
			/* 33. 3 to a flush with no high cards */
			/* DD does not mean same suit */
			boolean suc = false;
			/* SSSDD */
			if (getCardOrdered(0).CompareSuit(getCardOrdered(1)) == 0 &&
				getCardOrdered(0).CompareSuit(getCardOrdered(2)) == 0 &&
				getCardOrdered(0).CompareSuit(getCardOrdered(3)) != 0 &&
				getCardOrdered(0).CompareSuit(getCardOrdered(4)) != 0 &&
				getCardOrdered(0).CompareValue('T') <= 0) {
				HoldStrategy.add(1);
				HoldStrategy.add(2);
				HoldStrategy.add(3);
				suc = true;
			}
			/* DDSSS */
			else if (getCardOrdered(2).CompareSuit(getCardOrdered(3)) == 0 &&
					 getCardOrdered(2).CompareSuit(getCardOrdered(4)) == 0 &&
					 getCardOrdered(2).CompareSuit(getCardOrdered(0)) != 0 &&
					 getCardOrdered(2).CompareSuit(getCardOrdered(1)) != 0 &&
					 getCardOrdered(2).CompareValue('T') <= 0) {
				HoldStrategy.add(3);
				HoldStrategy.add(4);
				HoldStrategy.add(5);
				suc = true;
			}
			/* SSDSD */
			else if (getCardOrdered(0).CompareSuit(getCardOrdered(1)) == 0 &&
					 getCardOrdered(0).CompareSuit(getCardOrdered(3)) == 0 &&
					 getCardOrdered(0).CompareSuit(getCardOrdered(2)) != 0 &&
					 getCardOrdered(0).CompareSuit(getCardOrdered(4)) != 0 &&
					 getCardOrdered(0).CompareValue('T') <= 0) {
				HoldStrategy.add(1);
				HoldStrategy.add(2);
				HoldStrategy.add(4);
				suc = true;
			}			
			/* SDSSD */
			else if (getCardOrdered(0).CompareSuit(getCardOrdered(2)) == 0 &&
					 getCardOrdered(0).CompareSuit(getCardOrdered(3)) == 0 &&
					 getCardOrdered(0).CompareSuit(getCardOrdered(1)) != 0 &&
					 getCardOrdered(0).CompareSuit(getCardOrdered(4)) != 0 &&
					 getCardOrdered(0).CompareValue('T') <= 0) {
				HoldStrategy.add(1);
				HoldStrategy.add(3);
				HoldStrategy.add(4);
				suc = true;
			}
			/* DSSSD */
			else if (getCardOrdered(1).CompareSuit(getCardOrdered(2)) == 0 &&
					 getCardOrdered(1).CompareSuit(getCardOrdered(3)) == 0 &&
					 getCardOrdered(1).CompareSuit(getCardOrdered(0)) != 0 &&
					 getCardOrdered(1).CompareSuit(getCardOrdered(4)) != 0 &&
					 getCardOrdered(1).CompareValue('T') <= 0) {
				HoldStrategy.add(2);
				HoldStrategy.add(3);
				HoldStrategy.add(4);
				suc = true;
			}
			/* SSDDS */
			else if (getCardOrdered(0).CompareSuit(getCardOrdered(1)) == 0 &&
					 getCardOrdered(0).CompareSuit(getCardOrdered(4)) == 0 &&
					 getCardOrdered(0).CompareSuit(getCardOrdered(2)) != 0 &&
					 getCardOrdered(0).CompareSuit(getCardOrdered(3)) != 0 &&
					 getCardOrdered(0).CompareValue('T') <= 0) {
				HoldStrategy.add(1);
				HoldStrategy.add(2);
				HoldStrategy.add(5);
				suc = true;
			}
			/* SDSDS */
			else if (getCardOrdered(0).CompareSuit(getCardOrdered(2)) == 0 &&
					 getCardOrdered(0).CompareSuit(getCardOrdered(4)) == 0 &&
					 getCardOrdered(0).CompareSuit(getCardOrdered(1)) != 0 &&
					 getCardOrdered(0).CompareSuit(getCardOrdered(3)) != 0 &&
					 getCardOrdered(0).CompareValue('T') <= 0) {
				HoldStrategy.add(1);
				HoldStrategy.add(3);
				HoldStrategy.add(5);
				suc = true;
			}
			/* DSSDS */
			else if (getCardOrdered(1).CompareSuit(getCardOrdered(2)) == 0 &&
					 getCardOrdered(1).CompareSuit(getCardOrdered(4)) == 0 &&
					 getCardOrdered(1).CompareSuit(getCardOrdered(0)) != 0 &&
					 getCardOrdered(1).CompareSuit(getCardOrdered(3)) != 0 &&
					 getCardOrdered(1).CompareValue('T') <= 0) {
				HoldStrategy.add(2);
				HoldStrategy.add(3);
				HoldStrategy.add(5);
				suc = true;
			}
			/* SDDSS */
			else if (getCardOrdered(0).CompareSuit(getCardOrdered(3)) == 0 &&
					 getCardOrdered(0).CompareSuit(getCardOrdered(4)) == 0 &&
					 getCardOrdered(0).CompareSuit(getCardOrdered(1)) != 0 &&
					 getCardOrdered(0).CompareSuit(getCardOrdered(2)) != 0 &&
					 getCardOrdered(0).CompareValue('T') <= 0) {
				HoldStrategy.add(1);
				HoldStrategy.add(4);
				HoldStrategy.add(5);
				suc = true;
			}
			/* DSDSS */
			else if (getCardOrdered(1).CompareSuit(getCardOrdered(3)) == 0 &&
					 getCardOrdered(1).CompareSuit(getCardOrdered(4)) == 0 &&
					 getCardOrdered(1).CompareSuit(getCardOrdered(0)) != 0 &&
					 getCardOrdered(1).CompareSuit(getCardOrdered(2)) != 0 &&
					 getCardOrdered(1).CompareValue('T') <= 0) {
				HoldStrategy.add(2);
				HoldStrategy.add(4);
				HoldStrategy.add(5);
				suc = true;
			}
			if (suc == true) {
				NumberOfCardsHold = 3;
				bestStrategySave = 33;
			}
		}
		if (bestStrategySave == 0) {
			/* 34. Discard everything */
			bestStrategySave = 34;
		}
		
		if (NumberOfCardsHold != HoldStrategy.size() || NumberOfCardsHold > 5) {
			bestStrategySave = 0;
			return false;
		}
		
		if (NumberOfCardsHold > 0) {
			for (int i = 0; i < NumberOfCardsHold; i++) {
				HoldStrategy.set(i, 1 + getCardIndex(getCardOrdered(HoldStrategy.get(i) - 1)));
			}
		}
		
		Collections.sort(HoldStrategy);
		
		if (printKeep) {
			PrintKeep();
		}
		
		if (print) {
			PrintHoldAdvice(NumberOfCardsHold);
		}
		return true;
	}
	
	/**
     * Description: Display strategy player should use.
     * @return void
     */
	private void PrintKeep() {
		switch (bestStrategySave) {
			case 1:
				if (typeSave == 11) {
					System.out.println("Keep the royal flush");
				}
				else if (typeSave == 10) {
					System.out.println("Keep the straight flush");
				}
				else {
					System.out.println("Keep the four of a kind");
				}
				break;
			case 2:
				System.out.println("Keep 4 to a royal flush");
				break;
			case 3:
				System.out.println("Keep the three aces");
				break;
			case 4:
				if (typeSave == 4) {
					System.out.println("Keep the straight");
				}
				else if (typeSave == 5) {
					System.out.println("Keep the flush");
				}
				else {
					System.out.println("Keep the Full House");
				}
				break;
			case 5:
				System.out.println("Keep the three of a kind (except aces)");
				break;
			case 6:
				System.out.println("Keep 4 to a straight flush");
				break;
			case 7:
				System.out.println("Keep the two pair");
				break;
			case 8:
				System.out.println("Keep High pair");
				break;
			case 9:
				System.out.println("Keep 4 to a flush");
				break;
			case 10:
				System.out.println("Keep 3 to a royal flush");
				break;
			case 11:
				System.out.println("Keep 4 to an outside straight");
				break;
			case 12:
				System.out.println("Keep the low pair");
				break;
			case 13:
				System.out.println("Keep AKQJ unsuited");
				break;
			case 14:
				System.out.println("Keep 3 to a straight flush (type 1)");
				break;
			case 15:
				System.out.println("Keep 4 to an inside straight with 3 high cards");
				break;
			case 16:
				System.out.println("Keep QJ suited");
				break;
			case 17:
				System.out.println("Keep 3 to a flush with 2 high cards");
				break;
			case 18:
				System.out.println("Keep 2 suited high cards");
				break;
			case 19:
				System.out.println("Keep 4 to an inside straight with 2 high cards");
				break;
			case 20:
				System.out.println("Keep 3 to a straight flush (type 2)");
				break;
			case 21:
				System.out.println("Keep 4 to an inside straight with 1 high card");
				break;
			case 22:
				System.out.println("Keep KQJ unsuited");
				break;
			case 23:
				System.out.println("Keep JT suited");
				break;
			case 24:
				System.out.println("Keep QJ unsuited");
				break;
			case 25:
				System.out.println("Keep 3 to a flush with 1 high card");
				break;
			case 26:
				System.out.println("Keep QT suited");
				break;
			case 27:
				System.out.println("Keep 3 to a straight flush (type 3)");
				break;
			case 28:
				System.out.println("Keep KQ, KJ unsuited");
				break;
			case 29:
				System.out.println("Keep Ace");
				break;
			case 30:
				System.out.println("Keep KT suited");
				break;
			case 31:
				System.out.println("Keep Jack/Queen/King");
				break;
			case 32:
				System.out.println("Keep 4 to an inside straight with no high cards");
				break;
			case 33:
				System.out.println("Keep 3 to a flush with no high cards");
				break;
			case 34:
				System.out.println("Discard everything");
				break;
			default:
				System.out.println("Best Strategy not found");
				break;
		}
	}
	
	/**
     * Description: Display advice.
     * @param NumberOfCardsHold (int) - number of cards player should hold
     * @return void
     */
	private void PrintHoldAdvice(int NumberOfCardsHold) {
		switch (NumberOfCardsHold) {
			case 0:
				System.out.println("player should not hold cards");
				break;
			case 1:
				System.out.println("player should hold card " + HoldStrategy.get(0));
				break;
			case 2:
				System.out.println("player should hold cards " + HoldStrategy.get(0) +  
						" " + HoldStrategy.get(1));
				break;
			case 3:
				System.out.println("player should hold cards " + HoldStrategy.get(0) +  
						" " + HoldStrategy.get(1) + " " + HoldStrategy.get(2));
				break;
			case 4:
				System.out.println("player should hold cards " + HoldStrategy.get(0) +  
						" " + HoldStrategy.get(1) + " " + HoldStrategy.get(2) + " " 
						+ HoldStrategy.get(3));
				break;
			case 5:
				System.out.println("player should hold cards 1 2 3 4 5");
				break;
			default:
				break;
		}
	}
	
	/**
     * Description: Count number of high cards in hand.
     * @return int
     */
	public int countHighCards() {
		Sort();
		int counter = 0, i;
		for (i = 0; i < 5; i++) {
			if (getCardOrdered(i).highcard()) {
				counter++;
			}
			else {
				i = 6;
			}
		}
		return counter;
	}
	
	/**
     * Description: Check if there are illegal card duplicates in hand.
     * @return boolean
     */
	private boolean haveDuplicates() {
		if (getCardOrdered(0).equals(getCardOrdered(1)) ||
			getCardOrdered(1).equals(getCardOrdered(2)) ||	
			getCardOrdered(2).equals(getCardOrdered(3)) ||
			getCardOrdered(3).equals(getCardOrdered(4))) {
			return true;
		}
		return false;
	}
	
	/* Tests that have been made to verify if class is working as expected */
	/*
	public static void main(String[] args) {
		List<Card> c = new ArrayList<Card>();
		
		// 40: 8C 9C TD QC 2H – Keep 3 to a straight flush (type 2) - h 1 2 4
		
		c.add(new Card('8', 'C'));
		c.add(new Card('9', 'C'));
		c.add(new Card('T', 'D'));
		c.add(new Card('Q', 'C'));
		c.add(new Card('2', 'H'));
		
		Hand h1 = new Hand(c);
		h1.getHoldStrategy(true, true);
		//System.out.println(Hand.typeStr(h1.type()));
	}
	*/
}