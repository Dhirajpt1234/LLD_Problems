package notification_engine;

import java.util.ArrayList;
import java.util.List;

// notification creation flow using decorator pattern
interface INotification {

    String getContent();
}

class SimpleNotification implements INotification {

    private String content;

    public SimpleNotification(String content) {
        this.content = content;
    }

    @Override
    public String getContent() {
        return content;
    }

}

abstract class INotificationDecorator implements INotification {

    protected INotification wrapped;

    public INotificationDecorator(INotification wrapped) {
        this.wrapped = wrapped;
    }

    @Override
    public String getContent() {
        return wrapped.getContent();
    }

}

class TimeStampNotificationDecorator extends INotificationDecorator {

    public TimeStampNotificationDecorator(INotification wrapped) {
        super(wrapped);
    }

    @Override
    public String getContent() {
        return "[2026-01-13 00:00:000]: " + wrapped.getContent();
    }

}

class SignatureNotificationDecorator extends INotificationDecorator {

    private String signature;

    public SignatureNotificationDecorator(INotification wrapped, String signature) {
        super(wrapped);
        this.signature = signature;
    }

    @Override
    public String getContent() {
        return wrapped.getContent() + "\n -- " + signature;
    }

}

// observation and notify

interface ISubject {

    void subscribe(IObserver observer);

    void unsubscribe(IObserver observer);

    void notifyObservers();

}

interface IObserver {
    void update(String message);
}

interface INotificationStrategy {
    void sendNotification(String msg);
}

// observers
class EmailNotificationStrategy implements INotificationStrategy {

    private String emailAddress;

    public EmailNotificationStrategy(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    @Override
    public void sendNotification(String msg) {
        System.out.println("Sending the msg via email to " + emailAddress + ": " + msg);
    }

}

class SMSNotificationStrategy implements INotificationStrategy {

    private String phoneNumber;

    public SMSNotificationStrategy(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Override
    public void sendNotification(String msg) {
        System.out.println("Sending msg via SMS to " + phoneNumber + ": " + msg);
    }

}

class PopupNotificationStrategy implements INotificationStrategy {

    @Override
    public void sendNotification(String msg) {
        System.out.println("Showing the msg over POPUP: " + msg);
    }

}

class Logger implements IObserver {

    public Logger() {
        // Default constructor
    }

    @Override
    public void update(String message) {
        System.out.println("[LOG] " + message);
    }

}

class NotificationEngine implements IObserver {

    List<INotificationStrategy> strategies;

    public NotificationEngine() {
        strategies = new ArrayList<>();
    }

    public void addNotificationStrategy(INotificationStrategy strategy) {
        strategies.add(strategy);
    }

    public void removeStrategy(INotificationStrategy strategy) {
        strategies.removeIf(currentStrategy -> currentStrategy.equals(strategy));
    }

    @Override
    public void update(String message) {
        for (INotificationStrategy strategy : strategies) {
            strategy.sendNotification(message);
        }
    }

}

class NotificationObservable implements ISubject {

    List<IObserver> observers;
    INotification currentNotification;

    public NotificationObservable() {
        observers = new ArrayList<>();
    }

    @Override
    public void subscribe(IObserver observer) {
        observers.add(observer);
    }

    public void addObserver(IObserver observer) {
        subscribe(observer);
    }

    @Override
    public void unsubscribe(IObserver observer) {
        observers.removeIf(currentobserver -> currentobserver.equals(observer));
    }

    @Override
    public void notifyObservers() {
        for (IObserver observer : observers) {
            observer.update(currentNotification.getContent());
        }
    }

    public void setNotification(INotification notification) {
        this.currentNotification = notification;
        notifyObservers();
    }

    public INotification getNotification() {
        return currentNotification;
    }

    public String getNotificationContent() {
        return currentNotification.getContent();
    }

}

class NotificationService {
    private NotificationObservable observable;
    private static NotificationService instance;
    private List<INotification> notifications = new ArrayList<>();

    private NotificationService() {
        observable = new NotificationObservable();
    }

    public static NotificationService getInstance() {
        if (instance == null) {
            instance = new NotificationService();
        }
        return instance;
    }

    public NotificationObservable getObservable() {
        return observable;
    }

    public void sendNotification(INotification notification) {
        notifications.add(notification);
        observable.setNotification(notification);
    }
}

public class Main {

    public static void main(String[] args) {

        // Create NotificationService.
        NotificationService notificationService = NotificationService.getInstance();

        // Get Observable
        NotificationObservable notificationObservable = notificationService.getObservable();

        // Create Logger Observer
        Logger logger = new Logger();

        // Create NotificationEngine observers.
        NotificationEngine notificationEngine = new NotificationEngine();

        notificationEngine.addNotificationStrategy(new EmailNotificationStrategy("random.person@gmail.com"));
        notificationEngine.addNotificationStrategy(new SMSNotificationStrategy("+91 9876543210"));
        notificationEngine.addNotificationStrategy(new PopupNotificationStrategy());

        // Attach these observers.
        notificationObservable.addObserver(logger);
        notificationObservable.addObserver(notificationEngine);

        // Create a notification with decorators.
        INotification notification = new SimpleNotification("Your order has been shipped!");
        notification = new TimeStampNotificationDecorator(notification);
        notification = new SignatureNotificationDecorator(notification, "Customer Care");

        notificationService.sendNotification(notification);
    }

}
