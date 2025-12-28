package tic_tac_toe.GameStateHandler.ConcreteStates;

import tic_tac_toe.GameStateHandler.GameContext;
import tic_tac_toe.GameStateHandler.GameState;
import tic_tac_toe.Utils.Player;

public class OWonState implements GameState {

    @Override
    public void next(GameContext context, Player player, boolean hasWon) {
       //no new state.
    }

    @Override
    public boolean isGameOver() {
        return true;
    }
    
}
