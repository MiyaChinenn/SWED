package webmonitor;

import webmonitor.EnumType.Frequency;
import webmonitor.model.*;
import webmonitor.monitor.*;
import webmonitor.observer.*;


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