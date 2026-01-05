package tic_tac_toe_one.player;

import tic_tac_toe_one.game.Board;
import tic_tac_toe_one.utils.Position;
import tic_tac_toe_one.utils.Symbol;

public interface PlayerStrategy {
    Position makeMove(Board board);

    String getName();

    Symbol getSymbol();
}
