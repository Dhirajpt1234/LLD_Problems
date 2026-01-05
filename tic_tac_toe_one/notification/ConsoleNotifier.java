package tic_tac_toe_one.notification;

public class ConsoleNotifier implements Observer {

    @Override
    public void notify(String msg) {
        System.out.println("[Notification] : " + msg);
    }

}