package indexer;

import tokenizer.Tokenizer;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 *
 * @author <b>dardam</b>, michaldarda1@gmail.com <br><b>starczyr</b>,
 * radek.starczynowski@gmail.com
 */
public class FileReader {
    private String[] files;
    /**
     * Read files given as program arguments
     * @param files given files
     * @throws IOException
     */
    public void readFiles(String[] files) throws IOException {

        String lineFromDocument;
        BufferedReader input;
        int whichDocument = 0;
        this.files = files;

        for(int i = 0; i < files.length; i++) {

            if(files[i].startsWith("-") || (i - 1 > 0 && files[i-1].startsWith("-"))) {
                continue;
            }

            try {
                input = new BufferedReader(new InputStreamReader(new FileInputStream(files[i])));
            } catch(Exception e) {
                Printer.printErr("Cannot find file with following name: " + files[i]);
                continue;
            }

            whichDocument++;
            
            while((lineFromDocument = input.readLine()) != null) {

                Tokenizer tokenizer = new Tokenizer();
                String[] tokensInLine = tokenizer.split(lineFromDocument);

                Indexer.addToIndex(tokensInLine, whichDocument);
            }
        }

        if(whichDocument == 0) {
            Printer.printErr("You didn't give any correct files. Program will terminate now.");
            Printer.usage();
            System.exit(1);
        }
    }

}