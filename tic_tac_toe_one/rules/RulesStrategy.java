package tic_tac_toe_one.rules;

import tic_tac_toe_one.game.Board;
import tic_tac_toe_one.utils.Position;
import tic_tac_toe_one.utils.Symbol;

public interface RulesStrategy {
    boolean checkWinBoard(Board board, Symbol symbol);

    boolean checkDraw(Board board);

    boolean isValidMove(Board board, Position position);

}