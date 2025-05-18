package webmonitor.monitor;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Checks for website updates by comparing content.
 */
public class UpdateChecker {
    /**
     * Fetches website content from the given URL.
     * @param urlString The URL to fetch content from.
     * @return The content of the website as a String.
     */
    public String fetchWebsiteContent(String urlString) {
        StringBuilder content = new StringBuilder();
        try {
            URL url = new URL(urlString);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("User-Agent", "Mozilla/5.0"); // Good practice

            try (BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()))) {
                String inputLine;
                while ((inputLine = in.readLine()) != null) {
                    content.append(inputLine).append(System.lineSeparator());
                }
            } finally {
                conn.disconnect();
            }
        } catch (Exception e) {
            System.err.println("Error fetching website content from " + urlString + ": " + e.getMessage());
            // Return a distinct error marker or throw a custom exception
            return "ERROR_FETCHING_CONTENT: " + e.getMessage();
        }
        return content.toString();
    }

    /**
     * Detects changes between old and new website content.
     * @param oldContent The previously fetched content.
     * @param newContent The newly fetched content.
     * @return true if changes are detected, false otherwise.
     */
    public boolean detectChanges(String oldContent, String newContent) {
        if (oldContent == null || newContent == null) {
            return oldContent != newContent; // If one is null and other isn't, it's a change
        }
        // Avoid notifying if fetch failed
        if (newContent.startsWith("ERROR_FETCHING_CONTENT:")) {
            return false;
        }
        return !oldContent.equals(newContent);
    }
}

