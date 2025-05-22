package webmonitor.model;

import java.util.*;
import webmonitor.EnumType.Frequency;

/**
 * Represents a user of the WebMonitor system.
 * Users can create and manage website monitoring subscriptions.
 * Each user has personal information (name, email, phone) used for notifications.
 *
 * Users maintain a list of their subscriptions and can:
 * - Create new subscriptions
 * - View all their current subscriptions
 *
 * This class is part of the model layer and doesn't depend on monitor or notification logic.
 */

//Represents a user who can create and manage subscriptions.
public class User {
    private String userId;
    private String name;
    private String email;
    private String phone;
    private List<Subscription> subscriptions = new ArrayList<>();

    // Constructs a new user.
    public User(String userId, String name, String email, String phone) {
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.phone = phone;
    }


    public Subscription createSubscription(String subscriptionId, String url, Frequency frequency, String commChannel, String name) {
        Subscription subscription = new Subscription(subscriptionId, url, frequency, commChannel, name);
        this.subscriptions.add(subscription);
        System.out.println("User " + name + " created subscription for: " + name + " via " + commChannel);
        return subscription;
    }

    //List all subscriptions for the user
    public void manageSubscriptions() {
        System.out.println("User " + name + " is managing subscriptions:");
        if (subscriptions.isEmpty()) {
            System.out.println("No subscriptions found.");
            return;
        }
        for (Subscription sub : subscriptions) {
            System.out.println(" Name of website: " + sub.getName() + " - URL: " + sub.getURL() + ", Channel: " + sub.getNotificationChannel());
        }
    }

    // Getters for user fields if needed
    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public String getName() { // Added getter for name
        return name;
    }
}
