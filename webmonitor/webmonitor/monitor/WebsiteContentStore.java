package webmonitor.monitor;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class WebsiteContentStore {

    private static final String BASELINE_FILE = "old_content.html";
    private static final String CURRENT_SNAPSHOT_FILE = "current_content.html";

    private String readFileContent(final String filePath) {
        try {
            return Files.readString(Paths.get(filePath));
        } catch (final IOException e) {
            // This can happen if the file doesn't exist (e.g., first run for baseline) or other read errors.
            System.err.println("WebsiteContentStore: Info/Error reading file " + filePath + ": " + e.getMessage() +
                               ". This is normal if the file doesn't exist yet (e.g., on first run).");
            return null;
        }
    }

    private void writeFileContent(final String filePath, final String content) throws IOException {
        try (FileWriter writer = new FileWriter(filePath)) {
            writer.write(content);
        }
    }

    /**
     * Retrieves the baseline content, typically from "old_content.html".
     * @return The content of the baseline file, or null if not found or an error occurs.
     */
    public String getBaselineContent() {
        return readFileContent(BASELINE_FILE);
    }

    /**
     * Saves the newly fetched content to "current_content.html".
     * @param content The new website content to save.
     */
    public void saveCurrentSnapshot(final String content) {
        try {
            writeFileContent(CURRENT_SNAPSHOT_FILE, content);
            System.out.println("WebsiteContentStore: New content saved to " + CURRENT_SNAPSHOT_FILE);
        } catch (final IOException e) {
            System.err.println("WebsiteContentStore: Error writing new content to " + CURRENT_SNAPSHOT_FILE + ": " + e.getMessage());
        }
    }

    /**
     * Updates the baseline content file ("old_content.html") with the new content.
     * This new content will serve as the baseline for the next check.
     * @param newBaseline The new content to set as the baseline.
     */
    public void updateBaselineContent(final String newBaseline) {
        try {
            writeFileContent(BASELINE_FILE, newBaseline);
            System.out.println("WebsiteContentStore: Updated " + BASELINE_FILE + " with new content for the next check.");
        } catch (final IOException e) {
            System.err.println("WebsiteContentStore: Error updating " + BASELINE_FILE + " with new content: " + e.getMessage());
        }
    }
}
