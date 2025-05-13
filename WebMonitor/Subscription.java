public class Subscription {
    private String subscriptionId;
    private String url;
    private String frequency;
    private String commChannel;

    public Subscription(String subscriptionId, String url, String frequency, String commChannel) {
        this.subscriptionId = subscriptionId;
        this.url = url;
        this.frequency = frequency;
        this.commChannel = commChannel;
    }

    public String getURL() {
        return url;
    }

    public String getNotificationChannel() {
        return commChannel;
    }

    public void updatePreferences(String frequency, String commChannel) {
        this.frequency = frequency;
        this.commChannel = commChannel;
    }

    public void cancel() {
        System.out.println("Subscription " + subscriptionId + " has been cancelled.");
    }
}
