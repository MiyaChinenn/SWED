package webmonitor.monitor;

import java.io.FileWriter;
import java.io.IOException;

public class DifferenceReporter {

    private static final String DIFFERENCE_REPORT_FILE = "updated_content.txt";

    /**
     * Analyzes the differences between old and new content, writes a detailed report to a file,
     * and returns the number of lines that changed.
     *
     * @param oldContent The previous content.
     * @param newContent The new content.
     * @return The number of lines that have changed.
     */
    public int analyzeAndReport(String oldContent, String newContent) {
        String[] oldLines = oldContent == null ? new String[0] : oldContent.split("\n");
        String[] newLines = newContent == null ? new String[0] : newContent.split("\n");

        int changedLinesCount = 0;
        StringBuilder differencesForFile = new StringBuilder();
        differencesForFile.append("Differences found:\n");

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