package webmonitor.monitor;

import java.util.ArrayList;
import java.util.HashMap; // Assuming User info is needed for notifications
import java.util.List;
import java.util.Map;
import webmonitor.model.Subscription;
import webmonitor.model.User;
import webmonitor.notifier.EmailNotifier;
import webmonitor.notifier.Notifier;
import webmonitor.notifier.SMSNotifier;
import webmonitor.observer.Observer;
import webmonitor.observer.Subject;

/**
 * Coordinates website monitoring and user notifications.
 */
public class WebsiteMonitor implements Subject {
    private List<Subscription> subscriptions = new ArrayList<>();
    private Map<String, String> lastContents = new HashMap<>(); // Stores last known content for each URL
    private List<Observer> observers = new ArrayList<>();
    private UpdateChecker updateChecker = new UpdateChecker();
    // Map to associate subscription URL with the user who owns it for notifications
    private Map<String, User> subscriptionOwners = new HashMap<>();

    /**
     * Adds a subscription to the monitor.
     */
    public void addSubscription(Subscription subscription, User user) {
        this.subscriptions.add(subscription);
        this.subscriptionOwners.put(subscription.getURL(), user); // Store owner for notification
        System.out.println("WebsiteMonitor: Added subscription for " + subscription.getURL());
    }
    // Overload for Main.java's current direct usage if User is not passed yet
    public void addSubscription(Subscription subscription) {
        this.subscriptions.add(subscription);
        // For this simple case, we might not have user context directly
        System.out.println("WebsiteMonitor: Added subscription for " + subscription.getURL() + " (owner context not provided here).");
    }


    @Override
    public void attach(Observer o) {
        this.observers.add(o);
    }

    @Override
    public void detach(Observer o) {
        this.observers.remove(o);
    }

    @Override
    public void notifyObservers(String url, String newContent) {
        for (Observer observer : this.observers) {
            observer.update(url, newContent);
        }
    }

    /**
     * Checks all subscriptions for website updates and notifies users if changes are detected.
     * For demo: stores last content in memory (not persistent).
     */
    public void checkUpdates() {
        System.out.println("\nWebsiteMonitor: Checking for updates for ALL subscriptions...");
        for (Subscription sub : subscriptions) {
            // Call the new targeted check method for each subscription
            checkUpdatesForSubscription(sub);
        }
        System.out.println("WebsiteMonitor: ALL Update checks complete.");
    }

    /**
     * Checks for updates for a specific subscription.
     * @param sub The subscription to check.
     */
    public void checkUpdatesForSubscription(Subscription sub) {
        if (sub == null) {
            System.err.println("WebsiteMonitor: Cannot check updates for a null subscription.");
            return;
        }
        String url = sub.getURL();
        System.out.println("Checking (single): " + url);
        String oldContent = lastContents.get(url); // Get last known content
        String newContent = updateChecker.fetchWebsiteContent(url);

        if (updateChecker.detectChanges(oldContent, newContent)) {
            System.out.println("Change detected for (single): " + url);
            lastContents.put(url, newContent); // Update last known content
            notifyObservers(url, newContent); // Notify general observers

            // Notify the specific user via their preferred channel
            User owner = subscriptionOwners.get(url);
            if (owner != null) { // Check if owner context is available
                 notifyUser(owner, sub, "Website content changed!");
            } else {
                // Fallback for subscriptions added without explicit user context
                // This part might need refinement based on how you want to handle notifications
                // for subscriptions without a directly mapped user in subscriptionOwners.
                // For now, we can assume if no owner, general observers are enough,
                // or log a specific message.
                System.out.println("No specific user owner found for " + url + " to send targeted notification. General observers notified.");
                 // If the subscription itself has a channel like "email" or "sms" but no owner,
                 // it's ambiguous who the recipient is.
                 // The current notifyUser method requires a User object.
            }
        } else {
            System.out.println("No change detected for (single): " + url);
             if (oldContent == null && !newContent.startsWith("ERROR_FETCHING_CONTENT:")) {
                lastContents.put(url, newContent); // Store initial content
            }
        }
    }


    /**
     * Notifies a user based on their subscription preferences.
     * @param user The user to notify.
     * @param subscription The subscription that triggered the notification.
     * @param message The core message of the notification.
     */
    public void notifyUser(User user, Subscription subscription, String message) {
        Notifier notifier;
        String recipient;
        String fullMessage = "Update for " + subscription.getURL() + ": " + message;

        if ("email".equalsIgnoreCase(subscription.getNotificationChannel())) {
            notifier = new EmailNotifier();
            recipient = user.getEmail();
        } else if ("sms".equalsIgnoreCase(subscription.getNotificationChannel())) {
            notifier = new SMSNotifier();
            recipient = user.getPhone();
        } else {
            System.out.println("Unknown or unsupported notification channel: " + subscription.getNotificationChannel() + " for " + user.getName()); // Changed to user.getName()
            return;
        }

        if (recipient != null && !recipient.isEmpty()) {
            notifier.sendNotification(recipient, fullMessage);
        } else {
            System.out.println("No recipient address/phone for " + user.getName() + " on channel " + subscription.getNotificationChannel()); // Changed to user.getName()
        }
    }


    /**
     * Simulates running the monitor, typically called by test methods.
     * In a real application, this might be a scheduled task.
     */
    public void runMonitor() {
        System.out.println("\nWebsiteMonitor: runMonitor cycle started.");
        checkUpdates(); // This will now call checkUpdatesForSubscription for each
        System.out.println("WebsiteMonitor: runMonitor cycle finished.");
    }
}