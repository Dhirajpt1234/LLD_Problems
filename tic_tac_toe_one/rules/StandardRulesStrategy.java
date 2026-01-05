package tic_tac_toe_one.rules;

import tic_tac_toe_one.game.Board;
import tic_tac_toe_one.utils.Position;
import tic_tac_toe_one.utils.Symbol;

public class StandardRulesStrategy implements RulesStrategy {

    @Override
    public boolean checkWinBoard(Board board, Symbol symbol) {
        // check all the conditions.
        if (isWinByRowCondn(board, symbol))
            return true;
        if (isWinByColCondn(board, symbol))
            return true;
        if (isWinByDiagonalCondn(board, symbol))
            return true;
        if (isWinByAntiDiagonalCondn(board, symbol))
            return true;

        return false;
    }

    @Override
    public boolean checkDraw(Board board) {
        for (int i = 0; i < board.getSize(); i++) {
            for (int j = 0; j < board.getSize(); j++) {
                if (board.getCell(i, j) == Symbol.EMPTY)
                    return false;
            }
        }
        return true;
    }

    @Override
    public boolean isValidMove(Board board, Position position) {
        return board.isValidCell(position.row, position.col);
    }

    private boolean isWinByDiagonalCondn(Board board, Symbol symbol) {
        int cnt = 0;
        for (int i = 0; i < board.getSize(); i++) {
            if (board.getCell(i, i) == symbol)
                cnt++;
        }

        System.out.println("cnt : " + cnt);
        return cnt == board.getSize();
    }

    private boolean isWinByAntiDiagonalCondn(Board board, Symbol symbol) {
        int cnt = 0;
        for (int i = 0; i < board.getSize(); i++) {
            if (board.getCell(i, board.getSize() - i - 1) == symbol)
                cnt++;
        }

        System.out.println("cnt : " + cnt);
        return cnt == board.getSize();
    }

    private boolean isWinByRowCondn(Board board, Symbol symbol) {

        for (int i = 0; i < board.getSize(); i++) {
            int cnt = 0;
            for (int j = 0; j < board.getSize(); j++) {
                if (board.getCell(i, j) == symbol)
                    cnt++;
            }
            System.out.println("cnt : " + cnt);
            if (cnt == board.getSize())
                return true;
        }

        return false;
    }

    private boolean isWinByColCondn(Board board, Symbol symbol) {
        // col condition.
        for (int j = 0; j < board.getSize(); j++) {
            int cnt = 0;
            for (int i = 0; i < board.getSize(); i++) {
                if (board.getCell(i, j) == symbol)
                    cnt++;
            }
            System.out.println("cnt : " + cnt);
            if (cnt == board.getSize())
                return true;
        }
        return false;
    }

}
