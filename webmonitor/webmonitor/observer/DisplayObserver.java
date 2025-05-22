package webmonitor.observer;

/**
 * Concrete implementation of the Observer interface for console display.
 * Responsible for displaying website update information to the console
 * when notified of changes by the Subject (WebsiteMonitor).
 *
 * This class demonstrates a simple observer that prints notifications
 * but doesn't modify the system state. It represents a typical passive
 * observer in the Observer pattern.
 */

// This interface defines the Observer in the Observer pattern.
public class DisplayObserver implements Observer {
    private String observerName;

    public DisplayObserver(String name) {
        this.observerName = name;
    }

    // This method is called when the subject notifies its observers
    @Override
    public void update(String namePage, String newContent) {
        System.out.println("The Observer [" + observerName + "] update for Website: " + namePage);
        if (newContent != null && !newContent.startsWith("ERROR_FETCHING_CONTENT:")) {
            System.out.println("New content is updated for The Observer [" + observerName + "]");
        } else if (newContent != null) {
            System.out.println("Could not fetch new content: " + newContent);
        } else {
            System.out.println("New content is null.");
        }
    }
}