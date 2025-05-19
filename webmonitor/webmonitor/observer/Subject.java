package webmonitor.observer;

// This interface defines the Subject in the Observer pattern.
public interface Subject {
    void attach(Observer o);
    void detach(Observer o);
    void notifyObservers(String url, String newContent);
}