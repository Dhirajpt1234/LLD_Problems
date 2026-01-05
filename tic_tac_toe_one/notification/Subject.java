package tic_tac_toe_one.notification;

public interface Subject {
    void notify(String msg);

    void subscribe(Observer observer);

    void unsubscribe(Observer observer);
}
