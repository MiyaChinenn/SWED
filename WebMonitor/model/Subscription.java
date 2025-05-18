package webmonitor.model;

/**
 * Represents a website subscription with notification preferences.
 */
public class Subscription {
    private String subscriptionId;
    private String url;
    private String frequency;
    private String commChannel; // Added communication channel

    /**
     * Constructs a new Subscription.
     * @param subscriptionId The ID of the subscription.
     * @param url The URL to monitor.
     * @param frequency How often to check (e.g., "daily").
     * @param commChannel The communication channel (e.g., "email", "sms").
     */
    public Subscription(String subscriptionId, String url, String frequency, String commChannel) {
        this.subscriptionId = subscriptionId;
        this.url = url;
        this.frequency = frequency;
        this.commChannel = commChannel;
    }

    /**
     * Gets the URL of the subscription.
     * @return The URL string.
     */
    public String getURL() {
        return url;
    }

    /**
     * Gets the notification channel (e.g., email, sms).
     * @return The communication channel string.
     */
    public String getNotificationChannel() {
        return commChannel;
    }

    /**
     * Updates the subscription preferences.
     */
    public void updatePreferences(String frequency, String commChannel) {
        this.frequency = frequency;
        this.commChannel = commChannel;
    }

    /**
     * Cancels the subscription.
     */
    public void cancel() {
        System.out.println("Subscription " + subscriptionId + " has been cancelled.");
    }
}
