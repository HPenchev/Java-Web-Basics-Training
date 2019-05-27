package fdmc.utils;

import java.io.*;

public class HtmlReaderImpl implements HtmlReader {
    @Override
    public String readHtmlFile(String htmlFilePath) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(new File(htmlFilePath))));

        StringBuilder content = new StringBuilder();

        String htmlLine;

        while ((htmlLine = reader.readLine()) != null) {
            content.append(htmlLine + System.lineSeparator());
        }

        return content.toString().trim();
    }
}
