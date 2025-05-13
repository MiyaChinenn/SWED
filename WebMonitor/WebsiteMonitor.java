import java.util.ArrayList;
import java.util.List;

public class WebsiteMonitor {
    private List<Subscription> subscriptions = new ArrayList<>();
    private UpdateChecker updateChecker = new UpdateChecker();

    public void addSubscription(Subscription subscription) {
        subscriptions.add(subscription);
    }

    public void notifyUser(Subscription subscription, String message) {
        Notifier notifier;

        if ("email".equalsIgnoreCase(subscription.getNotificationChannel())) {
            notifier = new EmailNotifier();
        } else if ("sms".equalsIgnoreCase(subscription.getNotificationChannel())) {
            notifier = new SMSNotifier();
        } else {
            System.out.println("Unsupported notification channel.");
            return;
        }

        notifier.sendNotification("Update for " + subscription.getURL() + ": " + message);
    }

    public void checkUpdates() {
        for (Subscription sub : subscriptions) {
            String oldContent = "<html>Old Content</html>";
            String newContent = "<html>Website Content</html>"; // simulate change

            if (updateChecker.detectChanges(oldContent, newContent)) {
                notifyUser(sub, "Website content changed!");
            }
        }
    }

    public void runMonitor() {
        checkUpdates();
    }
}
