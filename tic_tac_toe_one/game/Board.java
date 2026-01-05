package tic_tac_toe_one.game;

import tic_tac_toe_one.utils.Position;
import tic_tac_toe_one.utils.Symbol;

public class Board {
    private int size;
    private Symbol grid[][];

    public Board(int size) {
        this.size = size;
        grid = new Symbol[size][size];

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                grid[i][j] = Symbol.EMPTY;
            }
        }
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public Symbol getCell(int row, int col) {
        if (isValidCell(row, col)) {
            return grid[row][col];
        }
        return Symbol.EMPTY;
    }

    public void setCell(Position position, Symbol symbol) {
        if (isValidCell(position.row, position.col)) {
            grid[position.row][position.col] = symbol;
        }
    }

    public boolean isValidCell(int row, int col) {
        return row >= 0 && row >= 0 && row < size && col < size;
    }

    public boolean isCellEmpty(int row, int col) {
        if (!isValidCell(row, col))
            return false;
        return grid[row][col] == Symbol.EMPTY;
    }

    public void display() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                Symbol symbol = grid[i][j];
                switch (symbol) {
                    case X:
                        System.out.print(" X ");
                        break;
                    case O:
                        System.out.print(" O ");
                        break;
                    case EMPTY:
                    default:
                        System.out.print(" . ");
                }

                if (j < size - 1) {
                    System.out.print("|");
                }
            }
            System.out.println();
            if (i < size - 1) {
                printSeparator();
            }
        }
        System.out.println();
    }

    private void printSeparator() {
        for (int i = 0; i < size; i++) {
            System.out.print("---");
            if (i < size - 1) {
                System.out.print("+");
            }
        }
        System.out.println();
    }

}