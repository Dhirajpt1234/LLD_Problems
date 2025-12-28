package tic_tac_toe.GameStateHandler.ConcreteStates;

import tic_tac_toe.GameStateHandler.GameContext;
import tic_tac_toe.GameStateHandler.GameState;
import tic_tac_toe.Utils.Player;

public class XWonState implements GameState{

    @Override
    public void next(GameContext context, Player player, boolean hasWon) {
        // no next move.
    }

    @Override
    public boolean isGameOver() {
        return true;
    }
    
}
