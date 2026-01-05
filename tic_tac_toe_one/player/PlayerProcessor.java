package tic_tac_toe_one.player;

import tic_tac_toe_one.game.Board;
import tic_tac_toe_one.utils.Position;
import tic_tac_toe_one.utils.Symbol;

public class PlayerProcessor {
    private PlayerStrategy strategy;

    public PlayerProcessor(PlayerStrategy playerStrategy) {
        this.strategy = playerStrategy;
    }

    // void setStrategy(PlayerStrategy playerStrategy) {
    // this.strategy = playerStrategy;
    // }

    public PlayerStrategy getPlayerStrategy() {
        return this.strategy;
    }

    public Position makeMove(Board board) {
        return strategy.makeMove(board);
    }

    public String getName() {
        return strategy.getName();
    }

    public Symbol getSymbol() {
        return strategy.getSymbol();
    }

}
