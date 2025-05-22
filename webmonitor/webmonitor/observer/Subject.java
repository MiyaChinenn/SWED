package webmonitor.observer;

/**
 * Interface defining the Subject role in the Observer pattern.
 * Subjects maintain a list of observers and notify them of state changes.
 *
 * Defines three key methods:
 * - attach(Observer): Register an observer to receive notifications
 * - detach(Observer): Remove an observer from the notification list
 * - notifyObservers(): Inform all registered observers of state changes
 *
 * In this system, WebsiteMonitor implements this interface to notify
 * observers (like DisplayObserver) about website content changes.
 */

// This interface defines the Subject in the Observer pattern.
public interface Subject {
    void attach(Observer o);
    void detach(Observer o);
    void notifyObservers(String url, String newContent);
}