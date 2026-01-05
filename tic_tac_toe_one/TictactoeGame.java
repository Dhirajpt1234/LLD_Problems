package tic_tac_toe_one;

import java.util.*;

import tic_tac_toe_one.game.TicTacToe;
import tic_tac_toe_one.notification.Observer;

public class TictactoeGame {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Create the board.
        try {
            System.out.println("Please the enter the size of the board");
            int size = scanner.nextInt();

            System.out.println("Size of the board will be " + size);

            TicTacToe game = new TicTacToe(size);

            // load players.
            game.addPlayer(game.loadPlayers());

            // load observers
            for (Observer observer : game.loadObservers()) {
                game.subscribe(observer);
            }

            // play game.
            game.play();

            scanner.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
