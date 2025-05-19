package webmonitor.observer;

// This interface defines the Subject in the Observer pattern.
public interface Observer {
    // url The URL that was updated.
    void update(String url, String newContent);
}
