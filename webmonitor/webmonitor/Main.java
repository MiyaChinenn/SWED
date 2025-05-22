package webmonitor;

import webmonitor.EnumType.Frequency;
import webmonitor.model.*;
import webmonitor.monitor.*;
import webmonitor.observer.*;

/**
 * Main entry point for the WebMonitor application.
 * Demonstrates the core functionality of the application by:
 * 1. Creating a website monitor instance
 * 2. Creating a user
 * 3. Creating a subscription for Google's homepage
 * 4. Setting up a console observer to display updates
 * 5. Running the website monitor to check for changes
 *
 * This class serves as a simple demonstration of the Observer pattern
 * where WebsiteMonitor is the Subject and DisplayObserver is the Observer.
 */

// This is the main class for the WebMonitor application.
public class Main {

    public static void main(String[] args) {
        System.out.println("=== Starting WebMonitor Demonstration ===");

        WebsiteMonitor siteMonitor = new WebsiteMonitor();
        User user1 = new User("U001", "Thien Nguyen", "Thien@example.com", "12345-67890");
        
        Subscription googleSub = new Subscription("S000", "https://www.google.com", Frequency.MINUTELY, "email", "Google Homepage");
        siteMonitor.addSubscription(googleSub, user1);

        DisplayObserver consoleDisplay = new DisplayObserver("MainConsoleDisplay");
        siteMonitor.attach(consoleDisplay);


        System.out.println("\nRunning update check for " + googleSub.getURL() + "...");
        siteMonitor.checkUpdates();

        System.out.println("\n=== WebMonitor Demonstration Finished ===");
    }
}