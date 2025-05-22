package webmonitor.monitor;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Manages the persistence of website content for comparison purposes.
 * Responsible for:
 * - Reading baseline content from old_content.html
 * - Saving the current website snapshot to current_content.html
 * - Moving the previous current content to old_content.html before saving new content
 *
 * This class abstracts away all file I/O operations related to storing
 * website content, allowing the WebsiteMonitor to focus on coordination
 * rather than file operations.
 */

// This class is responsible for managing the storage of website content.
public class WebsiteContentStore {

    private static final String BASELINE_FILE = "old_content.html";
    private static final String CURRENT_SNAPSHOT_FILE = "current_content.html";

    // Reads the content of a file and returns it as a string.
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

    // Writes the given content to a file.
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
     * Saves the newly fetched content to "current_content.html" and moves the
     * previous content from "current_content.html" to "old_content.html".
     * 
     * @param newContent The new website content to save.
     */
    public void saveCurrentSnapshot(final String newContent) {
        try {
            // First read the current content (if any)
            String currentContent = readFileContent(CURRENT_SNAPSHOT_FILE);
            
            // Save the new content to current_content.html
            writeFileContent(CURRENT_SNAPSHOT_FILE, newContent);
            System.out.println("WebsiteContentStore: New content saved to " + CURRENT_SNAPSHOT_FILE);
            
            // If there was existing content, move it to old_content.html
            if (currentContent != null) {
                writeFileContent(BASELINE_FILE, currentContent);
                System.out.println("WebsiteContentStore: Previous content from " + 
                                  CURRENT_SNAPSHOT_FILE + " moved to " + BASELINE_FILE);
            } else {
                // For the first run when there's no current_content.html yet,
                // also save the new content as the baseline to avoid future false positives
                writeFileContent(BASELINE_FILE, newContent);
                System.out.println("WebsiteContentStore: First run - also saving new content to " + BASELINE_FILE);
            }
        } catch (final IOException e) {
            System.err.println("WebsiteContentStore: Error managing content files: " + e.getMessage());
        }
    }

    /**
     * This method is now deprecated as the content movement is handled in saveCurrentSnapshot.
     * Kept for backward compatibility with existing code.
     * 
     * @param newBaseline The new content to set as the baseline.
     * @deprecated Use saveCurrentSnapshot instead, which now handles updating both files.
     */
    @Deprecated
    public void updateBaselineContent(final String newBaseline) {
        System.out.println("WebsiteContentStore: Warning - updateBaselineContent is deprecated. " +
                          "The baseline is now automatically updated when saving a new snapshot.");
        // No action needed as saveCurrentSnapshot now handles this
    }
}
