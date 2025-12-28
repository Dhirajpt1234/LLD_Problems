package tic_tac_toe;

import tic_tac_toe.Controller.GameController.TicTacToeGame;
import tic_tac_toe.PlayerStrategies.PlayerStrategy;
import tic_tac_toe.PlayerStrategies.ConcreateStrategies.HumanPlayerStrategy;

public class Main {
    public static void main(String[] args) {
        PlayerStrategy playerXStrategy = new HumanPlayerStrategy("Player X");
        PlayerStrategy playerOStrategy = new HumanPlayerStrategy("Player O");
        TicTacToeGame game = new TicTacToeGame(playerXStrategy, playerOStrategy, 3, 3);
        game.play();
    }
}
