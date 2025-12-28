package tic_tac_toe.PlayerStrategies.ConcreateStrategies;

import java.util.Scanner;

import tic_tac_toe.PlayerStrategies.PlayerStrategy;
import tic_tac_toe.Utils.Board;
import tic_tac_toe.Utils.Position;

public class HumanPlayerStrategy implements PlayerStrategy {

    public String playerName;
    Scanner scanner;

    public HumanPlayerStrategy(String name) {
        playerName = name;
        scanner = new Scanner(System.in);
    }

    // take input from human.
    @Override
    public Position makeMove(Board board) {
        while (true) {
            try {
                System.out.println("Enter your move : row[0-2] , col[0-2] : ");
                int row = scanner.nextInt();
                int col = scanner.nextInt();

                Position position = new Position(row, col);

                if (board.isValidMove(position)) {
                    return position;
                }

                System.out.println("Invalid move. Please try again..");

            } catch (Exception e) {
                System.out.println("Please input the valid moves..");
                scanner.nextLine();
            }
        }
    }
}
