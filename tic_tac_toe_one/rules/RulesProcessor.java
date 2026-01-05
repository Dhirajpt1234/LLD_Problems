package tic_tac_toe_one.rules;

import tic_tac_toe_one.game.Board;
import tic_tac_toe_one.utils.Position;
import tic_tac_toe_one.utils.Symbol;

public class RulesProcessor {
    private RulesStrategy strategy;

    public RulesProcessor(RulesStrategy strategy) {
        this.strategy = strategy;
    }

    public boolean checkWinBoard(Board board, Symbol symbol) {
        return strategy.checkWinBoard(board, symbol);
    }

    public boolean checkDraw(Board board) {
        return strategy.checkDraw(board);
    }

    public boolean isValidMove(Board board, Position position) {
        return strategy.isValidMove(board, position);
    }

}