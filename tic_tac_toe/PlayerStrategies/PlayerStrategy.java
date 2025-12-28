package tic_tac_toe.PlayerStrategies;

import tic_tac_toe.Utils.Board;
import tic_tac_toe.Utils.Position;

public interface PlayerStrategy {
    Position makeMove(Board board);
}