package webmonitor.notifier;

public interface Notifier {
    /**
     * Sends a notification with the given message.
     * @param recipient The email or phone number of the recipient.
     * @param message The message to send.
     */
    void sendNotification(String recipient, String message);
}
