import java.util.ArrayList;
import java.util.List;

public class User {
    private String userId;
    private String name;
    private String email;
    private String phone;
    private List<Subscription> subscriptions = new ArrayList<>();

    public User(String userId, String name, String email, String phone) {
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.phone = phone;
    }

    public Subscription createSubscription(String subscriptionId, String url, String frequency, String commChannel) {
        Subscription subscription = new Subscription(subscriptionId, url, frequency, commChannel);
        subscriptions.add(subscription);
        return subscription;
    }

    public void manageSubscriptions() {
        for (Subscription sub : subscriptions) {
            System.out.println("Managing subscription: " + sub.getURL());
        }
    }
}
