package tic_tac_toe_one;

import java.util.*;

//obeserver for notification. 
interface Observer {
    void notify(String msg);
}

// concrete notifier.
class ConsoleNotifier implements Observer {

    @Override
    public void notify(String msg) {
        System.out.println("[Notification] : " + msg);
    }

}

// enum to denote the symbols.
enum Symbol {
    X,
    O,
    EMPTY;
}

class Position {
    public int row;
    public int col;

    public Position(int row, int col) {
        this.row = row;
        this.col = col;
    }

    @Override
    public String toString() {
        return " ( " + row + " - " + col + " ) ";
    }
}

class Board {
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

    // public void setSize(int size) {
    // this.size = size;
    // }

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

interface PlayerStrategy {
    Position makeMove(Board board);

    String getName();

    Symbol getSymbol();
}

class HumanPlayerStrategy implements PlayerStrategy {
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
                System.out.print("Enter your move : row[0-2] , col[0-2] : ");
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

class PlayerProcessor {
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
// Rules section. Strategy Pattern.

interface RulesStrategy {
    boolean checkWinBoard(Board board, Symbol symbol);

    boolean checkDraw(Board board);

    boolean isValidMove(Board board, Position position);

}

// todo.
class StandardRulesStrategy implements RulesStrategy {

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

class RulesProcessor {
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

interface Boardgames {

    void play();

}

class TicTacToe implements Boardgames {

    private Board board;
    private RulesProcessor rules;
    private Deque<PlayerProcessor> players;
    private ArrayList<Observer> observers;
    private boolean isGameOver;

    public TicTacToe(int boardSize) {
        board = new Board(boardSize);
        rules = new RulesProcessor(new StandardRulesStrategy());
        players = new ArrayDeque<PlayerProcessor>();
        observers = new ArrayList<Observer>();
    }

    public Deque<PlayerProcessor> loadPlayers() {
        PlayerStrategy playerStrategy1 = new HumanPlayerStrategy("Dhiraj", Symbol.X);
        PlayerStrategy playerStrategy2 = new HumanPlayerStrategy("Suraj", Symbol.O);

        PlayerProcessor player1 = new PlayerProcessor(playerStrategy1);
        PlayerProcessor player2 = new PlayerProcessor(playerStrategy2);

        Deque<PlayerProcessor> players = new ArrayDeque<PlayerProcessor>();
        players.add(player1);
        players.add(player2);

        return players;
    }

    public ArrayList<Observer> laodObservers() {
        Observer observer = new ConsoleNotifier();
        ArrayList<Observer> observers = new ArrayList<>();
        observers.add(observer);

        return observers;
    }

    public void addPlayer(PlayerProcessor player) {
        this.players.add(player);
    }

    public void addPlayer(Deque<PlayerProcessor> players) {
        this.players.addAll(players);
    }

    public void addNotifier(Observer notifier) {
        observers.add(notifier);
    }

    public void addNotifier(ArrayList<Observer> notifiers) {
        observers.addAll(notifiers);
    }

    public RulesProcessor loadRules() {
        RulesStrategy strategy = new StandardRulesStrategy();
        RulesProcessor rules = new RulesProcessor(strategy);
        return rules;
    }

    public void addRules(RulesProcessor rules) {
        this.rules = rules;
    }

    @Override
    public void play() {
        notify("game has started");
        do {
            // take the current player out.
            PlayerProcessor player = players.getFirst();

            // print board.
            board.display();

            notify(player.getName() + " turn");

            // let player make move
            board.setCell(player.makeMove(board), player.getSymbol());

            // check if game is winning.
            if (rules.checkWinBoard(board, player.getSymbol())) {
                notify(player.getName() + " has won the game !!!!!!");
                isGameOver = true;
                break;
            }

            if (rules.checkDraw(board)) {
                notify("Game is Draw");
                isGameOver = true;
                break;
            }

            players.removeFirst();
            players.addLast(player);

        } while (!isGameOver);

    }

    void notify(String msg) {
        for (Observer observer : observers) {
            observer.notify(msg);
        }
    }

}

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
            Deque<PlayerProcessor> players = game.loadPlayers();
            game.addPlayer(players);

            // load observer
            game.addNotifier(game.laodObservers());

            // add the rules.
            // game.addRules(game.loadRules());

            // play game.
            game.play();

            scanner.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
