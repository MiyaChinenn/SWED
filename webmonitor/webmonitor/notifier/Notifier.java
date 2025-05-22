package webmonitor.notifier;

/**
 * Interface defining the contract for notification mechanisms.
 * Defines the method sendNotification() that must be implemented
 * by concrete notification classes.
 *
 * Part of the Strategy pattern for notification delivery, allowing
 * the system to support multiple notification channels without modifying
 * the core monitoring logic.
 */

public interface Notifier {
    /**
     * Sends a notification with the given message.
     * @param recipient The email or phone number of the recipient.
     * @param message The message to send.
     */
    void sendNotification(String recipient, String message);
}
