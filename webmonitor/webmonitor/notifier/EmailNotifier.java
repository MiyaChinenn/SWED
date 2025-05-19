package webmonitor.notifier;

// This class implements the Notifier interface.
public class EmailNotifier implements Notifier {
    @Override
    public void sendNotification(String recipientEmail, String message) {
        System.out.println("Sending Email to " + recipientEmail + ": " + message);
    }
}