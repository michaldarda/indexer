package indexer;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;

/**
 * Print results to file.
 * @author <b>dardam</b>, michaldarda1@gmail.com <br><b>starczyr</b>,
 * radek.starczynowski@gmail.com
 */
public class Printer {
    private String printSource;

    /**
     * Usage instructions
     */
    public static void usage() {
        System.out.println("Usage:");
        System.out.println("[Indexer] [files] [-b commands] [-o output]");
        System.out.println("[-b commands] - reading command from file commands");
        System.out.println("[-o output] - program will save output to file output instead of stout");
        System.out.println("Example:");
        System.out.println("Indexer file1 file2 file3 -b commands.txt -o output.txt");
        System.out.println();
    }

    /**
     * Prints an error
     *
     * @param message to print as an error
     */
    public static void printErr(String message) {
        System.out.println("Error: " + message);
    }

    /**
     * Method printing the results to file (if printSource is set) or stdout
     * It is printing in nice human-readable form
     * word n:k or word if haven't found
     *
     * @param word to print
     * @param results to print
     * @throws IOException
     */
    public void printResults(String word, int[] results) throws IOException {
        BufferedWriter bufferedWriter;

        if(printSource != null) {
            bufferedWriter = new BufferedWriter(new FileWriter(printSource,true));
        } else {
            bufferedWriter = new BufferedWriter(new OutputStreamWriter(System.out));
        }

        if(results == null) {
            bufferedWriter.write(word);
            bufferedWriter.newLine();
            bufferedWriter.flush();
            return;
        }

        String resultString = word + " " + results[0] + ":" + results[1] + "\n";
        bufferedWriter.write(resultString);
        bufferedWriter.newLine();
        bufferedWriter.flush();
    }

    /**
     *
     * @param source to print in, other than stdout
     */
    public void setPrintSource(String source) {
        this.printSource = source;
    }
}
