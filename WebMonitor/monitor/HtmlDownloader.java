package webmonitor.monitor;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class HtmlDownloader {
    /**
     * Downloads the HTML content from a given URL and saves it to a file.
     * 
     * @param urlString The URL to download from.
     * @param fileName  The name of the file to save the HTML to.
     * @throws IOException If an I/O error occurs.
     */
    public static void download(String urlString, String fileName) throws IOException {
        URL url = new URL(urlString);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        // It's good practice to set user-agent
        conn.setRequestProperty("User-Agent", "Mozilla/5.0");

        StringBuilder content = new StringBuilder();
        try (BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()))) {
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine).append(System.lineSeparator());
            }
        } finally {
            conn.disconnect();
        }

        try (BufferedWriter out = new BufferedWriter(new FileWriter(fileName))) {
            out.write(content.toString());
        }
        System.out.println("Successfully downloaded content from " + urlString + " to " + fileName);
    }
}