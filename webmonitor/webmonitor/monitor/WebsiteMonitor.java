package webmonitor.monitor;

// Imports for FileWriter and IOException are removed as they are now in DifferenceReporter
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import webmonitor.model.*;
import webmonitor.notifier.*;
import webmonitor.observer.*;

/**
 * Core coordinator class implementing the Subject role in the Observer pattern.
 * Responsible for:
 * - Managing website subscriptions and their owner users
 * - Performing website content checks using UpdateChecker
 * - Detecting changes in website content
 * - Notifying registered observers about changes
 * - Sending targeted notifications to subscription owners
 * - Storing and managing website content through WebsiteContentStore
 * - Analyzing and reporting content differences using DifferenceReporter
 *
 * This class acts as the central hub connecting the monitoring, notification,
 * and reporting components of the system.
 */

// Coordinates website monitoring and user notifications.
// It manages subscriptions, checks for updates, and notifies users of changes.
public class WebsiteMonitor implements Subject {
    private List<Subscription> subscriptions = new ArrayList<>();
    private List<Observer> observers = new ArrayList<>();
    private UpdateChecker updateChecker = new UpdateChecker();
    private Map<String, User> subscriptionOwners = new HashMap<>();
    private WebsiteContentStore contentStore = new WebsiteContentStore();
    private DifferenceReporter differenceReporter = new DifferenceReporter(); // Instance of DifferenceReporter

    // Adds a subscription to the monitor.
    // It also associates the subscription with a user for notification purposes.
    public void addSubscription(Subscription subscription, User user) {
        this.subscriptions.add(subscription);
        this.subscriptionOwners.put(subscription.getURL(), user); // Store owner for notification
        System.out.println("WebsiteMonitor: Added subscription for " + subscription.getURL());
    }

    // Removes a subscription from the monitor.
    @Override
    public void attach(Observer o) {
        this.observers.add(o);
    }

    // Detaches an observer from the monitor.
    @Override
    public void detach(Observer o) {
        this.observers.remove(o);
    }

    // Notifies all observers about a change in website content.
    @Override
    public void notifyObservers(String namePage, String newContent) {
        for (Observer observer : this.observers) {
            observer.update(namePage, newContent);
        }
    }

    // The showDifferences method is now removed from WebsiteMonitor.
    // Its functionality is handled by the DifferenceReporter class.

// Checks all subscriptions for website updates and notifies users if changes are detected.
// It reads the old content from a file and compares it with the new content fetched from the website.
public void checkUpdates() {
    System.out.println("\nWebsiteMonitor: Checking for updates for ALL subscriptions...");
    for (Subscription sub : subscriptions) {
        // Call the new targeted check method for each subscription
        checkUpdatesForSubscription(sub);
    }
    System.out.println("WebsiteMonitor: ALL Update checks complete.");
}

    // Checks for updates for a specific subscription.
    public void checkUpdatesForSubscription(Subscription sub) {
        if (sub == null) {
            System.err.println("WebsiteMonitor: Cannot check updates for a null subscription.");
            return;
        }
        String url = sub.getURL();
        String name = sub.getName();
        System.out.println("Checking for website: " + name);
        String baselineContent = contentStore.getBaselineContent();
        String newFetchedContent = updateChecker.fetchWebsiteContent(url);

        if (newFetchedContent == null) {
            System.err.println("Failed to fetch new content for " + name + ". Skipping check.");
            return;
        }

        if (updateChecker.detectChanges(baselineContent, newFetchedContent)) {
            System.out.println("Change detected for website: " + name);
            notifyObservers(name, newFetchedContent); // Notify general observers

            // Use DifferenceReporter to analyze and report differences
            differenceReporter.analyzeAndReport(baselineContent, newFetchedContent);

            // CHANGE: Only call saveCurrentSnapshot - it now handles baseline updates too
            contentStore.saveCurrentSnapshot(newFetchedContent);
            // REMOVE THIS LINE: contentStore.updateBaselineContent(newFetchedContent);

            // Notify the specific user owner of the subscription
            User owner = subscriptionOwners.get(url);
            if (owner != null) { // Check if owner context is available
                notifyUser(owner, sub, "Website content changed!");
            } else {
                System.out.println("No specific user owner found for " + name + " to send targeted notification. General observers notified.");
            }
        } else {
            System.out.println("No change detected for website: " + name);
        }
    }

    // Notifies a user based on their subscription preferences.
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
            System.out.println("Unknown or unsupported notification channel: " + subscription.getNotificationChannel() + " for " + user.getName());
            return;
        }

        if (recipient != null && !recipient.isEmpty()) {
            notifier.sendNotification(recipient, fullMessage);
        } else {
            System.out.println("No recipient address/phone for " + user.getName() + " on channel " + subscription.getNotificationChannel());
        }
    }


    // Run the monitor cycle
    public void runMonitor() {
        System.out.println("\nWebsiteMonitor: runMonitor cycle started.");
        checkUpdates();
        System.out.println("WebsiteMonitor: runMonitor cycle finished.");
    }
}