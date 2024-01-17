package Game;

/**
* Description: Abstract class for video poker game mode's implementation.
* <p>
* Name: Mode.java
* @author Afonso Alemão, Rui Daniel, Tomás Fonseca
* @version 20/06/2022
*/
public abstract class Mode {
	/* Possible states:
	 *     ‘h’ - waiting for bet            *
	 *     ‘b’ - waiting for deal           *
	 *     ‘d’ - waiting for hold or advice */
	protected char state;
	protected Variant var;
	
	/**
     * Description: Default Constructor for Mode.
     */
	public Mode() {
	}
	
	/**
     * Description: Abstract method to play different game modes.
     */
	public abstract void play();
	
	/**
     * Description: Updates state. Used after some player's valid move.
     */
	public void updateState() {
		if (state == 'h') {
			state = 'b';
		}
		else if (state == 'b') {
			state = 'd';
		}
		else if (state == 'd') {
			state = 'h';
		}
		return;
	}
	
	/**
     * Description: Initialize state. Initially we are waiting for a bet.
     */
	public void initState() {
		state = 'h';
	}
	
}
