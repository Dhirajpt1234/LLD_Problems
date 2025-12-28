package tic_tac_toe.Utils;

import tic_tac_toe.Enums.Symbol;
import tic_tac_toe.PlayerStrategies.PlayerStrategy;

public class Player {
    public Symbol symbol;
    PlayerStrategy playerStrategy;

    public Player(Symbol symbol, PlayerStrategy strategy) {
        this.playerStrategy = strategy;
        this.symbol = symbol;
    }

    public Symbol getSymbol() {
        return symbol;
    }

    public PlayerStrategy getPlayerStrategy() {
        return playerStrategy;
    }
}
