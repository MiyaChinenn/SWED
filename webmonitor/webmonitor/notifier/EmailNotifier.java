package webmonitor.notifier;

/**
 * Concrete implementation of the Notifier interface for email notifications.
 * Responsible for formatting and sending email notifications to users
 * when website changes are detected.
 *
 * In this demonstration version, it simulates sending emails by printing
 * to the console, but could be enhanced to use real email services.
 */

// This class implements the Notifier interface.
public class EmailNotifier implements Notifier {
    @Override
    public void sendNotification(String recipientEmail, String message) {
        System.out.println("Sending Email to " + recipientEmail + ": " + message);
    }
}