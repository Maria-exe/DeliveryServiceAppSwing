package data;

import java.io.*;

/**
 * @author Tirlea Maria Cristina
 * class used for generating a bill in a text format, for each order placed
 */
public class FileWriter {
    private static final FileWriter inst = new FileWriter();
    private PrintWriter myWriter;
    private File myFile;
   // private PrintWriter reportWriter;
    private FileWriter() {
        super();
        this.myFile = new File("bill.txt");
        try {
            this.myWriter = new PrintWriter(myFile, "UTF-8");
            BufferedWriter out = new BufferedWriter(myWriter);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void writeToFile(Object o) {
        synchronized (inst) {
            myWriter.print(o);
        }
    }

    public void writeLineToFile(Object o) {
        synchronized (inst) {
            myWriter.println(o);
        }
    }
    public static FileWriter getInst(){ return inst;}

    public PrintWriter getMyWriter() {
        return myWriter;
    }

    public File getMyFile() {
        return myFile;
    }

    public static void writeReportFile(String fileName, Object o){
        File reportFile = new File(fileName);
        try {
            PrintWriter reportWriter = new PrintWriter(reportFile, "UTF-8");
            BufferedWriter out = new BufferedWriter(reportWriter);
            reportWriter.print(o);
            reportWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}