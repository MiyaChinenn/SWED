package webmonitor;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import webmonitor.model.*;
import webmonitor.monitor.*;
import webmonitor.observer.*;
import java.io.IOException; // Added for file operations
import java.io.BufferedReader; // Added for reading files
import java.io.FileReader; // Added for reading files
import java.io.FileWriter; // Added for writing files
import java.io.BufferedWriter; // Added for writing files
import java.text.SimpleDateFormat; // Added for timestamp
import java.util.Date; // Added for timestamp

/**
 * Entry point for the WebMonitor application.
 * Demonstrates core monitoring and notification flows.
 */
public class Main {
    /**
     * Runs a basic monitoring demonstration and test flows for notifications.
     * @param args command-line arguments (not used)
     */
    public static void main(String[] args) {
        System.out.println("=== Starting WebMonitor Demonstration ===");

        WebsiteMonitor siteMonitor = new WebsiteMonitor(); // Renamed for clarity
        UpdateChecker updateChecker = new UpdateChecker(); // Instance for fetching

        // Setup a subscription directly for the main demonstration
        Subscription googleSub = new Subscription("S000", "https://www.google.com", "on-demand", "console");
        siteMonitor.addSubscription(googleSub);

        DisplayObserver consoleDisplay = new DisplayObserver("MainConsoleDisplay");
        siteMonitor.attach(consoleDisplay);

        // Initial download for WebsiteMonitor's internal state (optional, as checkUpdates will fetch)
        // System.out.println("\nPerforming initial content fetch for " + googleSub.getURL() + " for monitor setup...");
        // String initialContentForMonitor = updateChecker.fetchWebsiteContent(googleSub.getURL());
        // if (!initialContentForMonitor.startsWith("ERROR_FETCHING_CONTENT:")) {
        //     // Optionally, you could pre-populate the monitor's cache if desired,
        //     // but its checkUpdates() will handle fetching anyway.
        //     System.out.println("Initial content fetched successfully for monitor.");
        // } else {
        //     System.err.println("Failed to fetch initial content for monitor.");
        // }


        System.out.println("\nRunning initial update check for " + googleSub.getURL() + "...");
        siteMonitor.checkUpdates();

        System.out.println("\nRunning second update check for " + googleSub.getURL() + " (should show no change if content is same)...");
        siteMonitor.checkUpdates();

        // New demonstration using UpdateChecker to fetch and save versioned files
        demonstrateVersionedFetchingAndComparison("https://www.google.com", "google_content", updateChecker);
        demonstrateVersionedFetchingAndComparison("https://www.bing.com", "bing_content", updateChecker);


        System.out.println("\n=== SMS Notification Test ===");
        String smsOutput = testSMSNotification(siteMonitor);
        System.out.println("--- SMS Test Output ---");
        System.out.println(smsOutput);
        System.out.println("--- End SMS Test Output ---");

        System.out.println("\n=== Email Notification Test ===");
        String emailOutput = testEmailNotification(siteMonitor);
        System.out.println("--- Email Test Output ---");
        System.out.println(emailOutput);
        System.out.println("--- End Email Test Output ---");

        System.out.println("\n=== WebMonitor Demonstration Finished ===");
    }

    /**
     * Demonstrates fetching website content, saving it to versioned files,
     * and detecting changes using UpdateChecker.
     * @param url The URL to fetch.
     * @param baseFileName The base name for the stored files.
     * @param checker The UpdateChecker instance to use for fetching and detection.
     */
    private static void demonstrateVersionedFetchingAndComparison(String url, String baseFileName, UpdateChecker checker) {
        System.out.println("\n=== Demonstrating Versioned Fetching & Comparison for: " + url + " ===");
        
        // Create unique filenames using a simple versioning scheme or timestamps
        // Option 1: Simple versioning
        String version1FileName = baseFileName + "_v1.html";
        String version2FileName = baseFileName + "_v2.html";

        // Option 2: Timestamp based (more unique for multiple runs)
        // String timestampFormat = "yyyyMMdd_HHmmss_SSS";
        // String v1Timestamp = new SimpleDateFormat(timestampFormat).format(new Date());
        // String version1FileName = baseFileName + "_" + v1Timestamp + ".html";
        // String v2Timestamp;


        try {
            // 1. Fetch and save version 1
            System.out.println("Fetching version 1 for " + url + "...");
            String contentV1 = checker.fetchWebsiteContent(url);
            if (!contentV1.startsWith("ERROR_FETCHING_CONTENT:")) {
                saveContentToFile(contentV1, version1FileName);
                System.out.println("Version 1 saved to " + version1FileName);
            } else {
                System.err.println("Failed to fetch version 1 from " + url);
                return; // Stop if first fetch fails
            }

            // Simulate some time or a change
            System.out.println("Waiting a few seconds before fetching version 2...");
            try {
                Thread.sleep(3000); // Wait 3 seconds
            } catch (InterruptedException ie) {
                Thread.currentThread().interrupt();
            }
            
            // v2Timestamp = new SimpleDateFormat(timestampFormat).format(new Date());
            // version2FileName = baseFileName + "_" + v2Timestamp + ".html";


            // 2. Fetch and save version 2
            System.out.println("Fetching version 2 for " + url + "...");
            String contentV2 = checker.fetchWebsiteContent(url);
            if (!contentV2.startsWith("ERROR_FETCHING_CONTENT:")) {
                saveContentToFile(contentV2, version2FileName);
                System.out.println("Version 2 saved to " + version2FileName);
            } else {
                System.err.println("Failed to fetch version 2 from " + url);
                // We can still try to compare with v1 if v1 was successful
                // but for this demo, we'll assume if v2 fails, comparison isn't meaningful
                // with a non-existent v2 file.
                // If you want to compare fetched v1 with an error state v2, adjust logic.
                return;
            }

            // 3. Load contents from stored files (or use fetched content directly)
            // For this demo, we'll re-read to strictly follow "store then detect from storage"
            System.out.println("Loading content from stored files for comparison...");
            String storedContentV1 = readFileContent(version1FileName);
            String storedContentV2 = readFileContent(version2FileName);

            // 4. Compare the contents using UpdateChecker
            boolean changed = checker.detectChanges(storedContentV1, storedContentV2);

            if (changed) {
                System.out.println("Changes DETECTED between " + version1FileName + " and " + version2FileName);
            } else {
                System.out.println("NO changes detected between " + version1FileName + " and " + version2FileName);
            }

        } catch (IOException e) {
            System.err.println("Error during versioned fetching/comparison for " + url + ": " + e.getMessage());
        }
        System.out.println("=== Versioned Fetching & Comparison for " + url + " Finished ===\n");
    }

    /**
     * Saves the given string content to a file.
     * @param content The string content to save.
     * @param filePath The path to the file where content will be saved.
     * @throws IOException If an I/O error occurs.
     */
    private static void saveContentToFile(String content, String filePath) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            writer.write(content);
        }
    }

    /**
     * Reads the entire content of a file into a String.
     * @param filePath The path to the file.
     * @return The content of the file as a String.
     * @throws IOException If an I/O error occurs.
     */
    private static String readFileContent(String filePath) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String sCurrentLine;
            while ((sCurrentLine = br.readLine()) != null) {
                contentBuilder.append(sCurrentLine).append(System.lineSeparator());
            }
        }
        return contentBuilder.toString();
    }

    /**
     * Simulates an SMS notification scenario.
     * @param monitor The WebsiteMonitor instance to use.
     * @return Captured output of the SMS notification flow.
     */
    private static String testSMSNotification(WebsiteMonitor monitorInstance) {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outContent)); // Capture console output

        System.out.println("* Starting SMS Notification Test Flow *");
        User smsUser = new User("U001", "Sms User", "smsuser@example.com", "1234567890");
        Subscription smsSub = smsUser.createSubscription(
                "S001_SMS",
                "https://www.example.com", // Use a different URL for distinct testing
                "daily",
                "sms"
        );
        // smsUser.manageSubscriptions(); // Not strictly needed if just adding to monitor
        monitorInstance.addSubscription(smsSub, smsUser); // Add with user context
        monitorInstance.checkUpdatesForSubscription(smsSub); // More targeted check

        System.setOut(originalOut); // Restore original System.out
        System.out.println("* SMS Notification Test Flow Complete *");
        return outContent.toString();
    }

    /**
     * Simulates an Email notification scenario.
     * @param monitor The WebsiteMonitor instance to use.
     * @return Captured output of the Email notification flow.
     */
    private static String testEmailNotification(WebsiteMonitor monitorInstance) {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outContent)); // Capture console output

        System.out.println("* Starting Email Notification Test Flow *");
        User emailUser = new User("U002", "Email User", "emailuser@example.com", "0987654321");
        Subscription emailSub = emailUser.createSubscription(
                "S002_EMAIL",
                "https://www.bing.com", // Use another different URL
                "weekly",
                "email"
        );
        // emailUser.manageSubscriptions(); // Not strictly needed if just adding to monitor
        monitorInstance.addSubscription(emailSub, emailUser); // Add with user context
        monitorInstance.checkUpdatesForSubscription(emailSub); // More targeted check


        System.setOut(originalOut); // Restore original System.out
        System.out.println("* Email Notification Test Flow Complete *");
        return outContent.toString();
    }
}