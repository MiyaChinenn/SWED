package webmonitor.notifier;

public class EmailNotifier implements Notifier {
    /**
     * Sends an email notification.
     */
    @Override
    public void sendNotification(String recipientEmail, String message) {
        System.out.println("Sending Email to " + recipientEmail + ": " + message);
        // Actual email sending logic would go here
    }
}