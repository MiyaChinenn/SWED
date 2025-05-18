package webmonitor.observer;

public interface Observer {
    /**
     * Called by the Subject when its state changes.
     * @param url The URL that was updated.
     * @param newContent The new content of the URL (can be null or partial).
     */
    void update(String url, String newContent);
}
