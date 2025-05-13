public class UpdateChecker {
    public String fetchWebsiteContent(String url) {
        // Simulate fetching website content
        return "<html>Website Content</html>";
    }

    public boolean detectChanges(String oldContent, String newContent) {
        return !oldContent.equals(newContent);
    }
}
