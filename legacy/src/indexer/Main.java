package indexer;

import hash.Hash;

import java.io.IOException;

/**
 * Main class of project
 * contains index
 *
 * @author <b>dardam</b>, michaldarda1@gmail.com <br><b>starczyr</b>,
 * radek.starczynowski@gmail.com
 */
public class Main {

    public static void main(String[] args) throws IOException {
        FileReader fileReader = new FileReader();
        Hash.estimateHashSize(args);
        CommandLine commandLine = new CommandLine();
        Optionizer optionizer = new Optionizer(commandLine);

        optionizer.handleOptions(args);
        fileReader.readFiles(args);
        commandLine.startCommandLine();
    }
}
