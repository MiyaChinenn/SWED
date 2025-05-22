package webmonitor.monitor;

import java.io.*;
import java.net.*;

/**
 * Handles the technical aspects of fetching website content and detecting changes.
 * Responsibilities:
 * - Fetching content from websites via HTTP connections
 * - Handling network errors and connection issues
 * - Comparing previous and new content to detect changes
 *
 * This class is focused solely on the mechanics of content retrieval and comparison,
 * without concern for how the content is stored or how changes are reported.
 */

public class UpdateChecker {
    // Fetches website content from the given URL.
    // It handles HTTP connections and reads the response.
    // If an error occurs, it returns a message indicating the error.
    public String fetchWebsiteContent(String urlString) {
        StringBuilder content = new StringBuilder();
        try {
            URL url = new URL(urlString);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("User-Agent", "Mozilla/5.0"); 

            // Set a timeout for the connection
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
            return "ERROR_FETCHING_CONTENT: " + e.getMessage();
        }
        return content.toString();
    }

    // Detects changes between old and new website content.
    // It compares the two strings and returns true if they are different.
    // If either string is null, it checks if they are not equal.
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

