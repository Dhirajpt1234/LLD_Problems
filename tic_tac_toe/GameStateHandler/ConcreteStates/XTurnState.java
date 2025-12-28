package tic_tac_toe.GameStateHandler.ConcreteStates;

import tic_tac_toe.Enums.Symbol;
import tic_tac_toe.GameStateHandler.GameContext;
import tic_tac_toe.GameStateHandler.GameState;
import tic_tac_toe.Utils.Player;

public class XTurnState implements GameState {

    @Override
    public void next(GameContext context, Player player, boolean hasWon) {
        if (hasWon) {
            context.setCurrentGameState(player.getSymbol() == Symbol.X ? new XWonState() : new OWonState());
        } else {
            context.setCurrentGameState(new OTurnState());
        }
    }

    @Override
    public boolean isGameOver() {
        return false;
    }

}
