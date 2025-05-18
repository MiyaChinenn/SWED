package webmonitor.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a user who can create and manage subscriptions.
 */
public class User {
    private String userId;
    private String name;
    private String email; // Added email
    private String phone; // Added phone
    private List<Subscription> subscriptions = new ArrayList<>();

    /**
     * Constructs a new user.
     * @param userId The ID of the user.
     * @param name The name of the user.
     * @param email The email of the user.
     * @param phone The phone number of the user.
     */
    public User(String userId, String name, String email, String phone) {
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.phone = phone;
    }

    /**
     * Creates a new subscription for the user.
     * @param subscriptionId The ID for the new subscription.
     * @param url The URL to monitor.
     * @param frequency The checking frequency.
     * @param commChannel The communication channel.
     * @return The created Subscription object.
     */
    public Subscription createSubscription(String subscriptionId, String url, String frequency, String commChannel) {
        Subscription subscription = new Subscription(subscriptionId, url, frequency, commChannel);
        this.subscriptions.add(subscription);
        System.out.println("User " + name + " created subscription for: " + url + " via " + commChannel);
        return subscription;
    }

    /**
     * Prints out all subscriptions managed by the user.
     * (This is a simple implementation for demonstration)
     */
    public void manageSubscriptions() {
        System.out.println("User " + name + " is managing subscriptions:");
        if (subscriptions.isEmpty()) {
            System.out.println("No subscriptions found.");
            return;
        }
        for (Subscription sub : subscriptions) {
            System.out.println("- URL: " + sub.getURL() + ", Channel: " + sub.getNotificationChannel());
        }
    }

    // Getters for user fields if needed
    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    /**
     * Gets the name of the user.
     * @return The name of the user.
     */
    public String getName() { // Added getter for name
        return name;
    }
}
