package Cards;

import java.io.File;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

/**
* Description: Implementation of interface IDeck. 
* <p>
* Name: Deck.java
* @author Afonso Alemão, Rui Daniel, Tomás Fonseca
* @version 20/06/2022
*/
public class Deck implements IDeck {
	private List<Card> deck = null;
	private List<Card> OutOfDeck = null;
	private int nbCards = 0;

	/**
     * Description: Constructor for Deck using a card file.
     * @param fileName (String) - name of the card-file containing all the cards that will be used
     */
	public Deck(String fileName) {
		deck = new LinkedList<Card>(); 
		OutOfDeck = new LinkedList<Card>();  
		String Save;
		Scanner x = null;

		/* Opens file */
		try {
			x = new Scanner(new File(fileName));
		}
		catch(Exception e) {
			System.out.println("File not found");
			System.exit(0);
		}
		/* Reads file into deck */
		while (x.hasNext()) {
			Save = x.next();
			if (Save.length() != 2) {
				System.out.println("File with card not valid");
				System.exit(0);
			}
			deck.add(new Card(Save.charAt(0), Save.charAt(1)));
			nbCards++;
			if (!getCardAndNotRemove().isValid()) {
				System.out.println("File with card not valid");
				System.exit(0);
			}
		}
		/* Closes file */
		try {
			x.close();
		}
		catch(Exception e) {
			System.out.println("File not found");
			System.exit(0);
		}	
	}
	
	/**
     * Description: Constructor for Deck using a standard deck.
     * @param DecknbCards (int) - number of cards that will be part of the deck
     */
	public Deck(int DecknbCards) {
		if (DecknbCards == 52) {
			deck = new LinkedList<Card>(); 
			OutOfDeck = new LinkedList<Card>();
			for (Suit suit1 : Suit.values()) {
				for (Value value1 : Value.values()) {
		    		deck.add(new Card(value1.getName(), suit1.getName()));
		    		nbCards++;
		    	}
	    	}
		}
		else {
			/* In future implementations jokers can be included */
			System.out.println("Deck with that number of cards unavailable");
			System.exit(0);
		}
	}
	
	/**
     * Description: Gets the first Card of the deck without removing it.
     * @return Card
     */
	private Card getCardAndNotRemove() {
		Card card1 = null;
		if (!deck.isEmpty()) {
			card1 = new Card(deck.get(0).getValue(), deck.get(0).getSuit());
		}
		return card1;
	}
	
	/**
     * Description: Gets the first element of the deck, removing it.
     * @return Card (or null if deck is empty)
     */
	public Card getCard() {
		/* Gets the first element of the deck */
		Card card1 = null;
		if (!deck.isEmpty()) {
			card1 = new Card(deck.get(0).getValue(), deck.get(0).getSuit());
			/* Removes card from deck */
			deck.remove(0);
			OutOfDeck.add(card1);
			nbCards--;
		}
		else {
			return null;
		}
		return card1;
	}

	/**
     * Description: Restore cards that were previously removed, by returning them to the deck.
     */
	public void restoreDeck() {
		Iterator<Card> outofDeck = OutOfDeck.iterator();
		while (outofDeck.hasNext()) {
			Card card = outofDeck.next();
			deck.add(0, new Card(card.getValue(), card.getSuit()));
			nbCards++;
			outofDeck.remove();
		}
	}
	
	/**
     * Description: Gets the number of cards the deck has.
     * @return int
     */
	public int getnbCards() {
		return this.nbCards;
	}
	
	/**
     * Description: Adds a card to deck.
     * @param card (Card) - card that will be added to deck
     */
	public void addCardBegining(Card card) {
		deck.add(0, card);
		nbCards++;
	}
	
	/**
     * Description: Shuffles deck.
     */
	public void shuffle() {
		Collections.shuffle(deck);
	}
	
	/* Tests that have been made to verify if class is working as expected */
	/*   
	public static void main(String[] args) {
		Deck deck1;
		String filename = "card-file.txt";
		deck1 = new Deck(filename);
		
		System.out.println("Show first 5 cards of card file:");
		for (int i = 1; i <= 5; i++) {
			System.out.print(deck1.getCard().toString() + " ");
		}
		System.out.println();
		// Removes a card from deck
		deck1.getCard();
		System.out.println("Show 7th to 11th of card file:");
		for (int i = 1; i <=5; i++) {
			System.out.print(deck1.getCard().toString() + " ");
		}
	}
	*/
}
