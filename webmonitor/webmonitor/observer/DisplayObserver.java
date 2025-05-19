package webmonitor.observer;

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