package webmonitor.notifier;

/**
 * Concrete implementation of the Notifier interface for SMS notifications.
 * Responsible for formatting and sending SMS text messages to users
 * when website changes are detected.
 *
 * In this demonstration version, it simulates sending SMS by printing
 * to the console, but could be enhanced to use real SMS gateway services.
 */

// This class implements the Notifier interface.
public class SMSNotifier implements Notifier {
    @Override
    public void sendNotification(String recipientPhone, String message) {
        System.out.println("Sending SMS to " + recipientPhone + ": " + message);
    }
}

