package Game;

import java.util.ArrayList;
import java.util.List;

/**
* Description: Subclass of Mode. Implements Simulation Mode. 
* <p>
* Name: SimulationMode.java
* @author Afonso Alemão, Rui Daniel, Tomás Fonseca
* @version 20/06/2022
*/
public class SimulationMode extends Mode {
	private int ndeals;
	private int bet;
	
	/**
     * Description: Constructor of Simulation Mode. Checks if bet is valid.
     * @param ndeals (int) - number of deals to be made 
     * @param bet (double) - amount of credit to bet in each deal
     * @param init_credit (double) - initial credit of player
     * @param var (Variant) - Video Poker Game variant to be played
     */
	public SimulationMode(int ndeals, double bet, double init_credit, Variant var) {
		if (!((int)bet <= 5 && (int)bet >= 1) || bet % 1 != 0) {
			System.out.println("Invalid bet argument");
			System.exit(0);
		}
		this.ndeals = ndeals;
		this.bet = (int)bet;
		this.var = var;
	}

	/**
     * Description: Plays a game using the best strategies possible and display stats.
     */
	public void play() {
		initState();
		
		for (int k = 0; k < ndeals; k++) {
			if (!var.bet(state, (int) Math.floor(this.bet), false)) {
				System.exit(0);
			}
			updateState();
			if (!var.deal(state, false)) {
				System.exit(0);
			}
			updateState();
			List<Integer> newHoldStrategy = new ArrayList<Integer>(var.getArrayBestStrategy());
			if (!var.hold(state, newHoldStrategy, (int) Math.floor(this.bet), false)) {
				System.exit(0);
			}
			updateState();
		}
		var.statistics(state);
	}

}