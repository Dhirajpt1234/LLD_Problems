package food_delivery_app;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

class MenuItem {
    String code;
    String name;
    int price;

    public MenuItem(String code, String name, int price) {
        this.code = code;
        this.name = name;
        this.price = price;
    }

    public int getPrice() {
        return this.price;
    }

    public String getName() {
        return this.name;
    }

    public String getCode() {
        return this.code;
    }
}

class Restaurant {
    int restoId;
    String name;
    String locn;
    List<MenuItem> menu;

    public Restaurant(int id, String name, String locn) {
        this.restoId = id;
        this.name = name;
        this.locn = locn;
        this.menu = new ArrayList<MenuItem>();
    }

    public Restaurant(int id, String name, String locn, List<MenuItem> menu) {
        this.restoId = id;
        this.name = name;
        this.locn = locn;
        this.menu = menu;
    }

    public int getId() {
        return this.restoId;
    }

    public String getName() {
        return this.name;
    }

    public String getLocation() {
        return this.locn;
    }

    public List<MenuItem> getMenu() {
        return this.menu;
    }

    public void addMenuItem(MenuItem menuItem) {
        this.menu.add(menuItem);
    }
}

class RestaurantManager {
    private static RestaurantManager instance;
    List<Restaurant> restos;

    private RestaurantManager() {
        this.restos = new ArrayList<>();
    }

    public static RestaurantManager getInstance() {
        if (instance == null) {
            instance = new RestaurantManager();
        }
        return instance;
    }

    public void addRestaurant(Restaurant restaurant) {
        restos.add(restaurant);
    }

    public void removeRestaurant(Restaurant restaurant) {
        int restaurantId = restaurant.getId();
        restos.removeIf(currrestaurant -> currrestaurant.getId() == restaurantId);
    }

    public List<Restaurant> searchByLocation(String location) {
        return restos.stream().filter(restaurant -> restaurant.getLocation().equals(location))
                .collect(Collectors.toList());
    }
}

class Cart {
    // Only one restaurant can be in cart in real app - so simplify logic
    private Restaurant restaurant;
    private List<MenuItem> items;

    public Cart() {
        this.items = new ArrayList<>();
        this.restaurant = null;
    }

    public void setRestaurant(Restaurant r) {
        if (restaurant != r) {
            // If new restaurant, clear previous items
            this.restaurant = r;
            this.items.clear();
        }
    }

    public Restaurant getRestaurant() {
        return this.restaurant;
    }

    public void addItem(MenuItem item) {
        if (this.restaurant == null) {
            System.out.println("Please select a restaurant first!");
            return;
        }
        this.items.add(item);
    }

    public List<MenuItem> getItems() {
        return this.items;
    }

    public boolean isEmpty() {
        return this.items.isEmpty();
    }

    public double getTotalCost() {
        double total = 0;
        for (MenuItem mi : items)
            total += mi.getPrice();
        return total;
    }

    public void clear() {
        this.restaurant = null;
        this.items.clear();
    }
}

class User {
    String name;
    String location;
    Cart cart;

    public User(String name, String location) {
        this.name = name;
        this.location = location;
        this.cart = new Cart();
    }

    public String getName() {
        return this.name;
    }

    public String getLocation() {
        return this.location;
    }

    public Cart getCart() {
        return this.cart;
    }
}

interface PaymentStrategy {
    void pay(double total);
}

class CashPayment implements PaymentStrategy {
    @Override
    public void pay(double total) {
        System.out.println("Payment of ₹" + total + " successful using Cash");
    }
}

class CreditCardPayment implements PaymentStrategy {
    @Override
    public void pay(double total) {
        System.out.println("Payment of ₹" + total + " successful using Credit Card");
    }
}

abstract class Order {
    protected int orderId;
    protected User user;
    protected Restaurant restaurant;
    protected List<MenuItem> items;
    protected PaymentStrategy paymentStrategy;
    protected double total;

    public Order() {
    }

    public abstract String getType();

    public boolean processPayment() {
        if (paymentStrategy != null) {
            paymentStrategy.pay(total);
            return true;
        } else {
            System.out.println("Please choose a payment mode first");
            return false;
        }
    }

    public void setOrderId(int id) {
        this.orderId = id;
    }

    public void setUser(User u) {
        user = u;
    }

    public void setRestaurant(Restaurant r) {
        restaurant = r;
    }

    public void setItems(List<MenuItem> its) {
        items = its;
    }

    public void setPaymentStrategy(PaymentStrategy p) {
        paymentStrategy = p;
    }

    public void setTotal(double t) {
        total = t;
    }

    public double getTotal() {
        return total;
    }

    public List<MenuItem> getItems() {
        return items;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }
}

class NowOrder extends Order {
    public NowOrder(User usr, Restaurant rest, List<MenuItem> items, PaymentStrategy payStrat, double total,
            String orderType) {
        this.user = usr;
        this.restaurant = rest;
        this.items = items;
        this.paymentStrategy = payStrat;
        this.total = total;
    }

    @Override
    public String getType() {
        return "NOW";
    }
}

class ScheduledOrder extends Order {
    String scheduleTime;

    public ScheduledOrder(User usr, Restaurant rest, List<MenuItem> items, PaymentStrategy payStrat, double total,
            String orderType, String scheduleTime) {
        this.user = usr;
        this.restaurant = rest;
        this.items = items;
        this.paymentStrategy = payStrat;
        this.total = total;
        this.scheduleTime = scheduleTime;
    }

    @Override
    public String getType() {
        return "SCHEDULED(" + scheduleTime + ")";
    }
}

interface OrderFactory {
    Order createOrder(User user, Cart cart, Restaurant restaurant, List<MenuItem> items,
            PaymentStrategy paymentStrategy, double totalCost, String orderType);
}

class NowOrderFactory implements OrderFactory {
    @Override
    public Order createOrder(User user, Cart cart, Restaurant restaurant, List<MenuItem> items,
            PaymentStrategy paymentStrategy, double totalCost, String orderType) {
        return new NowOrder(user, restaurant, items, paymentStrategy, totalCost, orderType);
    }
}

class ScheduledOrderFactory implements OrderFactory {
    String schTime;

    public ScheduledOrderFactory(String schTime) {
        this.schTime = schTime;
    }

    @Override
    public Order createOrder(User user, Cart cart, Restaurant restaurant, List<MenuItem> items,
            PaymentStrategy paymentStrategy, double totalCost, String orderType) {
        return new ScheduledOrder(user, restaurant, items, paymentStrategy, totalCost, orderType, schTime);
    }
}

class OrderManager {
    private static OrderManager instance;
    private List<Order> orders;
    private int idCounter = 1;

    private OrderManager() {
        orders = new ArrayList<>();
    }

    public static OrderManager getInstance() {
        if (instance == null)
            instance = new OrderManager();
        return instance;
    }

    public void addOrder(Order order) {
        order.setOrderId(idCounter++);
        orders.add(order);
        System.out.println("Order placed! Order ID: " + order.orderId);
    }

    public List<Order> getOrders() {
        return orders;
    }
}

class NotificationService {
    public static void notify(Order order) {
        System.out.println("Notification: Order #" + order.orderId + " for user " + order.user.getName()
                + " has been placed at " + order.restaurant.getName());
    }
}

class TomatoApp {
    public TomatoApp() {
        initializeRestaurants();
    }

    public void initializeRestaurants() {
        Restaurant restaurant1 = new Restaurant(0, "Bikaner", "Delhi");
        restaurant1.addMenuItem(new MenuItem("P1", "Chole Bhature", 120));
        restaurant1.addMenuItem(new MenuItem("P2", "Samosa", 15));
        Restaurant restaurant2 = new Restaurant(1, "Haldiram", "Kolkata");
        restaurant2.addMenuItem(new MenuItem("P1", "Raj Kachori", 80));
        restaurant2.addMenuItem(new MenuItem("P2", "Pav Bhaji", 100));
        restaurant2.addMenuItem(new MenuItem("P3", "Dhokla", 50));
        Restaurant restaurant3 = new Restaurant(2, "Saravana Bhavan", "Chennai");
        restaurant3.addMenuItem(new MenuItem("P1", "Masala Dosa", 90));
        restaurant3.addMenuItem(new MenuItem("P2", "Idli Vada", 60));
        restaurant3.addMenuItem(new MenuItem("P3", "Filter Coffee", 30));
        RestaurantManager restaurantManager = RestaurantManager.getInstance();
        restaurantManager.addRestaurant(restaurant1);
        restaurantManager.addRestaurant(restaurant2);
        restaurantManager.addRestaurant(restaurant3);
    }

    public List<Restaurant> searchRestaurants(String location) {
        return RestaurantManager.getInstance().searchByLocation(location);
    }

    public void selectRestaurant(User user, Restaurant restaurant) {
        Cart cart = user.getCart();
        cart.setRestaurant(restaurant);
    }

    public void addToCart(User user, String itemCode) {
        Restaurant restaurant = user.getCart().getRestaurant();
        if (restaurant == null) {
            System.out.println("Please select a restaurant first.");
            return;
        }
        for (MenuItem item : restaurant.getMenu()) {
            if (item.getCode().equals(itemCode)) {
                user.getCart().addItem(item);
                System.out.println("Added to cart: " + item.getName());
                break;
            }
        }
    }

    public Order checkoutNow(User user, String orderType, PaymentStrategy paymentStrategy) {
        return checkout(user, orderType, paymentStrategy, new NowOrderFactory());
    }

    public Order checkoutScheduled(User user, String orderType, PaymentStrategy paymentStrategy, String scheduleTime) {
        return checkout(user, orderType, paymentStrategy, new ScheduledOrderFactory(scheduleTime));
    }

    public Order checkout(User user, String orderType, PaymentStrategy paymentStrategy, OrderFactory orderFactory) {
        if (user.getCart().isEmpty())
            return null;
        Cart userCart = user.getCart();
        Restaurant orderedRestaurant = userCart.getRestaurant();
        List<MenuItem> itemsOrdered = userCart.getItems();
        double totalCost = userCart.getTotalCost();
        Order order = orderFactory.createOrder(user, userCart, orderedRestaurant, itemsOrdered, paymentStrategy,
                totalCost, orderType);
        OrderManager.getInstance().addOrder(order);
        return order;
    }

    public void payForOrder(User user, Order order) {
        boolean isPaymentSuccess = order.processPayment();
        if (isPaymentSuccess) {
            NotificationService.notify(order);
            user.getCart().clear();
        }
    }

    public void printUserCart(User user) {
        System.out.println("Items in cart:");
        System.out.println("------------------------------------");
        for (MenuItem item : user.getCart().getItems()) {
            System.out.println(item.getCode() + " : " + item.getName() + " : ₹" + item.getPrice());
        }
        System.out.println("------------------------------------");
        System.out.println("Grand total : ₹" + user.getCart().getTotalCost());
    }
}

public class Main {
    public static void main(String[] args) {
        TomatoApp app = new TomatoApp();
        User user = new User("Rohan", "Delhi");
        System.out.println(
                "\nWelcome, " + user.getName() + "! Searching for restaurants in " + user.getLocation() + "\n");
        List<Restaurant> available = app.searchRestaurants(user.getLocation());
        if (available.isEmpty()) {
            System.out.println("No restaurants found in your area.");
            return;
        }
        System.out.println("Available restaurants:");
        for (int i = 0; i < available.size(); i++) {
            System.out.println((i + 1) + ". " + available.get(i).getName());
        }
        Restaurant chosen = available.get(0); // Simulate user choosing the first restaurant
        app.selectRestaurant(user, chosen);
        System.out.println("\nSelected: " + chosen.getName());
        System.out.println("Menu:");
        for (MenuItem mi : chosen.getMenu()) {
            System.out.println(mi.getCode() + ". " + mi.getName() + " : ₹" + mi.getPrice());
        }
        // Simulate adding menu items
        app.addToCart(user, "P1");
        app.addToCart(user, "P2");
        app.printUserCart(user);
        // Simulate checkout
        System.out.println("\nProceeding to checkout...");
        Order order = app.checkoutNow(user, "ONLINE", new CreditCardPayment());
        if (order == null) {
            System.out.println("Nothing to checkout.");
            return;
        }
        app.payForOrder(user, order);
        System.out.println("\nThank you for ordering!\n");
    }
}
