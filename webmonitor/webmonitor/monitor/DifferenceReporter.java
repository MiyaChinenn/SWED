package webmonitor.monitor;

import java.io.FileWriter;
import java.io.IOException;

public class DifferenceReporter {

    private static final String DIFFERENCE_REPORT_FILE = "updated_content.txt";

    /**
     * Analyzes and reports differences between old and new website content.
     * Responsibilities:
     * - Comparing content line by line
     * - Counting the number of changed lines
     * - Creating a detailed report of the changes
     * - Saving the differences report to a file (updated_content.txt)
     *
     * This class encapsulates the logic for analyzing what specifically changed
     * between website versions, supporting the monitoring process with detailed
     * change information.
     */
    
    /// This method compares the old and new content of a website and reports the differences.
    public int analyzeAndReport(String oldContent, String newContent) {
        String[] oldLines = oldContent == null ? new String[0] : oldContent.split("\n");
        String[] newLines = newContent == null ? new String[0] : newContent.split("\n");

        int changedLinesCount = 0;
        StringBuilder differencesForFile = new StringBuilder();
        differencesForFile.append("Differences found:\n");

        // Compare line by line
        int max = Math.max(oldLines.length, newLines.length);
        for (int i = 0; i < max; i++) {
            String oldLine = i < oldLines.length ? oldLines[i].trim() : "<no line>";
            String newLine = i < newLines.length ? newLines[i].trim() : "<no line>";

            if (!oldLine.equals(newLine)) {
                changedLinesCount++;
                String diffDetail = "Line " + (i + 1) + " changed:\n" +
                                    "  Old: " + oldLine + "\n" +
                                    "  New: " + newLine + "\n\n";
                differencesForFile.append(diffDetail);
            }
        }

        // Report the number of changed lines
        if (changedLinesCount > 0) {
            System.out.println(changedLinesCount + " line(s) changed.");
            try (FileWriter writer = new FileWriter(DIFFERENCE_REPORT_FILE)) {
                writer.write(differencesForFile.toString());
                System.out.println("DifferenceReporter: Detailed differences saved to " + DIFFERENCE_REPORT_FILE);
            } catch (IOException e) {
                System.err.println("DifferenceReporter: Error writing differences to " + DIFFERENCE_REPORT_FILE + ": " + e.getMessage());
            }
        } else {
            System.out.println("DifferenceReporter: No line-by-line differences found.");
        }
        return changedLinesCount;
    }
}