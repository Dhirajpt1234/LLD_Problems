package tic_tac_toe_one.game;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;

import tic_tac_toe_one.notification.ConsoleNotifier;
import tic_tac_toe_one.notification.Observer;
import tic_tac_toe_one.notification.Subject;
import tic_tac_toe_one.player.HumanPlayerStrategy;
import tic_tac_toe_one.player.PlayerProcessor;
import tic_tac_toe_one.player.PlayerStrategy;
import tic_tac_toe_one.rules.RulesProcessor;
import tic_tac_toe_one.rules.StandardRulesStrategy;
import tic_tac_toe_one.utils.Symbol;

public class TicTacToe implements Boardgame, Subject {

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

    public ArrayList<Observer> loadObservers() {
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

    @Override
    public void subscribe(Observer notifier) {
        observers.add(notifier);
    }

    @Override
    public void unsubscribe(Observer observer) {
        observers.remove(observer);
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

    @Override
    public void notify(String msg) {
        for (Observer observer : observers) {
            observer.notify(msg);
        }
    }
}
