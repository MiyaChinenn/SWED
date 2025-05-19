package webmonitor.notifier;

// This class implements the Notifier interface.
public class SMSNotifier implements Notifier {
    @Override
    public void sendNotification(String recipientPhone, String message) {
        System.out.println("Sending SMS to " + recipientPhone + ": " + message);
    }
}

