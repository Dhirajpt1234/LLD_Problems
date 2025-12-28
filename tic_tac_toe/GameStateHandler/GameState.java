package tic_tac_toe.GameStateHandler;

import tic_tac_toe.Utils.Player;

public interface GameState {
    void next(GameContext context, Player player, boolean hasWon);

    boolean isGameOver();

}