import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

// MainTest.java

public class Main {
    public static void main(String[] args) {
        String smsOutput = testSMSNotification();
        System.out.println("=== SMS Notification Test Output ===");
        System.out.print(smsOutput);

        String emailOutput = testEmailNotification();
        System.out.println("=== Email Notification Test Output ===");
        System.out.print(emailOutput);
    }

    private static String testSMSNotification() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outContent));

        // Step 1: Create a user
        User user = new User("U001", "Thien Nguyen", "thien@example.com", "0123456789");

        // Step 2: User creates a subscription (SMS)
        Subscription subscription = user.createSubscription(
                "S001",
                "https://example.com/news",
                "daily",
                "sms"
        );

        // Step 3: User can manage subscriptions (just prints them for now)
        user.manageSubscriptions();

        // Step 4: Add the subscription to the WebsiteMonitor
        WebsiteMonitor monitor = new WebsiteMonitor();
        monitor.addSubscription(subscription);

        // Step 5: Run the monitor (simulate one cycle)
        monitor.runMonitor();

        System.setOut(originalOut);

        String output = outContent.toString();
        assert output.contains("Managing subscription: https://example.com/news") : "manageSubscriptions output missing";
        assert output.contains("Sending SMS: Update for https://example.com/news: Website content changed!") : "SMS notification missing";
        return output;
    }

    private static String testEmailNotification() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outContent));

        // Step 1: Create a user
        User user = new User("U002", "Jane Doe", "jane@example.com", "0987654321");

        // Step 2: User creates a subscription (Email)
        Subscription subscription = user.createSubscription(
                "S002",
                "https://example.com/updates",
                "weekly",
                "email"
        );

        // Step 3: User can manage subscriptions (just prints them for now)
        user.manageSubscriptions();

        // Step 4: Add the subscription to the WebsiteMonitor
        WebsiteMonitor monitor = new WebsiteMonitor();
        monitor.addSubscription(subscription);

        // Step 5: Run the monitor (simulate one cycle)
        monitor.runMonitor();

        System.setOut(originalOut);

        String output = outContent.toString();
        assert output.contains("Managing subscription: https://example.com/updates") : "manageSubscriptions output missing";
        assert output.contains("Sending Email: Update for https://example.com/updates: Website content changed!") : "Email notification missing";
        return output;
    }
}