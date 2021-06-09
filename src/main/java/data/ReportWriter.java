package data;

import java.io.*;

/**
 * class used to generate reports in text format files
 */
public class ReportWriter {
    private PrintWriter myWriter;
    private File myFile;

    public ReportWriter(String fileName) {
        super();
        this.myFile = new File(fileName);
        try {
            this.myWriter = new PrintWriter(myFile, "UTF-8");
            BufferedWriter out = new BufferedWriter(myWriter);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void writeToFile(Object o) {
        myWriter.print(o);
    }

    public void writeLineToFile(Object o) {
        myWriter.println(o);
    }

    public PrintWriter getMyWriter() {
        return myWriter;
    }

    public File getMyFile() {
        return myFile;
    }
}