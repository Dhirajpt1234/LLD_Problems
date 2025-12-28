package tic_tac_toe.Controller.GameController;

import tic_tac_toe.Controller.BoardGames;
import tic_tac_toe.Enums.Symbol;
import tic_tac_toe.GameStateHandler.GameContext;
import tic_tac_toe.GameStateHandler.GameState;
import tic_tac_toe.GameStateHandler.ConcreteStates.OWonState;
import tic_tac_toe.GameStateHandler.ConcreteStates.XWonState;
import tic_tac_toe.PlayerStrategies.PlayerStrategy;
import tic_tac_toe.Utils.Board;
import tic_tac_toe.Utils.Player;
import tic_tac_toe.Utils.Position;

public class TicTacToeGame implements BoardGames {

    private Board board;
    private Player playerX;
    private Player playerO;
    private Player currentPlayer;
    private GameContext gameContext;

    public TicTacToeGame(PlayerStrategy xStrategy, PlayerStrategy oStrategy,
            int rows, int columns) {
        board = new Board(rows, columns);
        playerX = new Player(Symbol.X, xStrategy);
        playerO = new Player(Symbol.O, oStrategy);
        currentPlayer = playerX;
        gameContext = new GameContext();
    }

    @Override
    public void play() {
        do {

            board.printBoard();

            Position move = currentPlayer.getPlayerStrategy().makeMove(board);
            board.makeMove(move, currentPlayer.getSymbol());
            board.checkGameState(gameContext, currentPlayer);

            switchPlayer();

        } while (!gameContext.isGameOver());
        announceResult();

    }

    private void switchPlayer() {
        currentPlayer = (currentPlayer == playerX) ? playerO : playerX;
    }

    // Displays the outcome of the game based on the final game state.
    private void announceResult() {
        GameState state = gameContext.getCurrentGameState();
        board.printBoard();
        if (state instanceof XWonState) {
            System.out.println("Player X wins!");
        } else if (state instanceof OWonState) {
            System.out.println("Player O wins!");
        } else {
            System.out.println("It's a draw!");
        }
    }

}
