package notification_engine;

import java.util.ArrayList;
import java.util.List;

/**
 * Notification Engine System
 * 
 * This system demonstrates multiple design patterns working together:
 * - Decorator Pattern: For enhancing notifications with additional features
 * - Observer Pattern: For notifying multiple observers when notifications are sent
 * - Strategy Pattern: For different notification delivery methods
 * - Singleton Pattern: For ensuring single instance of NotificationService
 */

// Decorator Pattern: Component Interface
// Defines the interface for objects that can have responsibilities added to them dynamically
interface INotification {

    String getContent();
}

// Decorator Pattern: Concrete Component
// Represents a basic notification with simple content
// This is the core object that can be decorated with additional features
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

// Decorator Pattern: Abstract Decorator
// Maintains a reference to a component object and defines an interface
// that conforms to the component's interface. All concrete decorators
// will extend this class.
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

// Decorator Pattern: Concrete Decorator
// Adds timestamp functionality to any notification
// Enhances the notification by prepending a timestamp to the content
class TimeStampNotificationDecorator extends INotificationDecorator {

    public TimeStampNotificationDecorator(INotification wrapped) {
        super(wrapped);
    }

    @Override
    public String getContent() {
        return "[2026-01-13 00:00:000]: " + wrapped.getContent();
    }

}

// Decorator Pattern: Concrete Decorator
// Adds signature functionality to any notification
// Enhances the notification by appending a signature to the content
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

// Observer Pattern: Subject Interface
// Defines methods for attaching, detaching, and notifying observers
// This is the core of the Observer pattern implementation
interface ISubject {

    void subscribe(IObserver observer);

    void unsubscribe(IObserver observer);

    void notifyObservers();

}

// Observer Pattern: Observer Interface
// Defines the update method that observers must implement
// to receive notifications from the subject
interface IObserver {
    void update(String message);
}

// Strategy Pattern: Strategy Interface
// Defines the common interface for all notification delivery strategies
// Allows different notification methods to be used interchangeably
interface INotificationStrategy {
    void sendNotification(String msg);
}

// Strategy Pattern: Concrete Strategy
// Implements email notification delivery
// Encapsulates the algorithm for sending notifications via email
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

// Strategy Pattern: Concrete Strategy
// Implements SMS notification delivery
// Encapsulates the algorithm for sending notifications via SMS
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

// Strategy Pattern: Concrete Strategy
// Implements popup notification delivery
// Encapsulates the algorithm for showing notifications as popup messages
class PopupNotificationStrategy implements INotificationStrategy {

    @Override
    public void sendNotification(String msg) {
        System.out.println("Showing the msg over POPUP: " + msg);
    }

}

// Observer Pattern: Concrete Observer
// Implements logging functionality for notifications
// Gets notified when a new notification is sent and logs it to console
class Logger implements IObserver {

    public Logger() {
        // Default constructor
    }

    @Override
    public void update(String message) {
        System.out.println("[LOG] " + message);
    }

}

// Observer Pattern: Concrete Observer + Strategy Pattern: Context
// Implements notification engine that uses multiple strategies
// Acts as an observer that receives notifications and forwards them
// to various notification strategies for delivery
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

// Observer Pattern: Concrete Subject
// Maintains a list of observers and notifies them of state changes
// Holds the current notification and broadcasts it to all observers
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

// Singleton Pattern: Singleton Class
// Ensures only one instance of NotificationService exists
// Provides global access point to the notification service
// Coordinates between the observable and notification sending
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

/**
 * Main Class - Client Code
 * 
 * Demonstrates the integration of all design patterns:
 * 1. Singleton: Gets the single instance of NotificationService
 * 2. Observer: Sets up observers (Logger, NotificationEngine)
 * 3. Strategy: Configures notification strategies (Email, SMS, Popup)
 * 4. Decorator: Enhances notification with timestamp and signature
 * 
 * Flow:
 * - Create notification service (Singleton)
 * - Set up observers (Observer pattern)
 * - Configure notification strategies (Strategy pattern)
 * - Create and decorate notification (Decorator pattern)
 * - Send notification which triggers all observers
 */
public class Main {

    /**
     * Main method - Entry point for the notification system demonstration
     * Shows how all patterns work together to create a flexible notification system
     */
    public static void main(String[] args) {

        // Create NotificationService using Singleton pattern
        NotificationService notificationService = NotificationService.getInstance();

        // Get Observable from the service
        NotificationObservable notificationObservable = notificationService.getObservable();

        // Create Logger Observer (Observer pattern)
        Logger logger = new Logger();

        // Create NotificationEngine observer (Observer pattern)
        // This engine will use Strategy pattern for different delivery methods
        NotificationEngine notificationEngine = new NotificationEngine();

        // Configure notification strategies using Strategy pattern
        notificationEngine.addNotificationStrategy(new EmailNotificationStrategy("random.person@gmail.com"));
        notificationEngine.addNotificationStrategy(new SMSNotificationStrategy("+91 9876543210"));
        notificationEngine.addNotificationStrategy(new PopupNotificationStrategy());

        // Attach observers to the observable (Observer pattern)
        notificationObservable.addObserver(logger);
        notificationObservable.addObserver(notificationEngine);

        // Create a notification and enhance it using Decorator pattern
        INotification notification = new SimpleNotification("Your order has been shipped!");
        notification = new TimeStampNotificationDecorator(notification);  // Add timestamp
        notification = new SignatureNotificationDecorator(notification, "Customer Care");  // Add signature

        // Send notification which triggers all observers
        notificationService.sendNotification(notification);
    }

}
