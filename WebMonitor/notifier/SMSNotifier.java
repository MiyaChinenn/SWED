package webmonitor.notifier;

public class SMSNotifier implements Notifier {
    /**
     * Sends an SMS notification.
     */
    @Override
    public void sendNotification(String recipientPhone, String message) {
        System.out.println("Sending SMS to " + recipientPhone + ": " + message);
        // Actual SMS sending logic would go here
    }
}

