package tic_tac_toe_one.player;

import java.util.Scanner;

import tic_tac_toe_one.game.Board;
import tic_tac_toe_one.utils.Position;
import tic_tac_toe_one.utils.Symbol;

public class HumanPlayerStrategy implements PlayerStrategy {
    private String name;
    private Symbol symbol;
    Scanner scanner;

    public HumanPlayerStrategy(String name, Symbol symbol) {
        scanner = new Scanner(System.in);
        this.name = name;
        this.symbol = symbol;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Symbol getSymbol() {
        return symbol;
    }

    @Override
    public Position makeMove(Board board) {
        while (true) {
            try {
                System.out.print(
                        "Enter your move : row[0- " + board.getSize() + " ] , col[0- " + board.getSize() + "] : ");
                int row = scanner.nextInt();
                int col = scanner.nextInt();

                if (board.isValidCell(row, col)) {
                    Position position = new Position(row, col);
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

