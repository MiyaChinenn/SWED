package webmonitor.model;
import webmonitor.EnumType.Frequency;

//Represents a website subscription
public class Subscription {
    private String subscriptionId;
    private String url;
    private Frequency frequency;
    private String commChannel;
    private String name;

    // Constructs Subscription.
    // frequency: how often to check
    // commChannel: The communication channel (e.g., "email", "sms", "console").
    public Subscription(String subscriptionId, String url, Frequency frequency, String commChannel, String name) {
        this.subscriptionId = subscriptionId;
        this.url = url;
        this.frequency = frequency;
        this.commChannel = commChannel;
        this.name = name;
    }

    // Getters for subscription fields
    public String getURL() {
        return url;
    }

    public String getNotificationChannel() {
        return commChannel;
    }

    public String getName() {
        return name;
    }

    //Updates the subscription preferences.
    public void updatePreferences(Frequency frequency, String commChannel) {
        this.frequency = frequency;
        this.commChannel = commChannel;
    }

    public void cancel() {
        System.out.println("Subscription " + subscriptionId + " has been cancelled.");
    }
}
