package webmonitor.observer;

public class DisplayObserver implements Observer {
    private String observerName;

    public DisplayObserver(String name) {
        this.observerName = name;
    }

    @Override
    public void update(String url, String newContent) {
        System.out.println("[" + observerName + "] Update for URL: " + url);
        if (newContent != null && !newContent.startsWith("ERROR_FETCHING_CONTENT:")) {
            System.out.println("New content (first 100 chars): " + newContent.substring(0, Math.min(100, newContent.length())) + "...");
        } else if (newContent != null) {
            System.out.println("Could not fetch new content: " + newContent);
        } else {
            System.out.println("New content is null.");
        }
    }
}