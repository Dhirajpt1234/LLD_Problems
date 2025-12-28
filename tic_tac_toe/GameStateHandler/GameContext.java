package tic_tac_toe.GameStateHandler;

import tic_tac_toe.GameStateHandler.ConcreteStates.XTurnState;
import tic_tac_toe.Utils.Player;

public class GameContext {
    private GameState currentGameState;

    public GameContext() {
        this.currentGameState = new XTurnState();
    }

    public GameState getCurrentGameState() {
        return currentGameState;
    }

    public void setCurrentGameState(GameState newState) {
        this.currentGameState = newState;
    }

    // do something.
    public void next(Player player, boolean hasWon) {
        this.currentGameState.next(this, player, hasWon);
    }

    public boolean isGameOver() {
        return this.currentGameState.isGameOver();
    }
}
