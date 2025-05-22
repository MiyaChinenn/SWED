package webmonitor.observer;

/**
 * Interface defining the Observer role in the Observer pattern.
 * Observers register with a Subject to receive notifications of state changes.
 *
 * Defines a single method:
 * - update(): Called by the Subject when its state changes
 *
 * In this system, DisplayObserver implements this interface to react
 * to website content changes detected by WebsiteMonitor.
 */

// This interface defines the Subject in the Observer pattern.
public interface Observer {
    // url The URL that was updated.
    void update(String url, String newContent);
}
