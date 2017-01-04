package indexer;

import tokenizer.CommandTokenizer;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Interprets a command given either in script or by command line
 *
 * @author <b>dardam</b>, michaldarda1@gmail.com
 * <br><b>starczyr</b>, radek.starczynowski@gmail.com
 */
public class CommandLine {
    
    private String currentWord;
    private Searcher searcher = new Searcher();
    private Printer printer = new Printer();
    private String script;

   
    /**
     * Starts a command line.
     * @throws IOException
     */
    public void startCommandLine() throws IOException {

        BufferedReader bufferedReader;

        if(script != null) {
            try {
                bufferedReader = new BufferedReader((new InputStreamReader(new FileInputStream(script))));
            } catch(Exception e) {
                Printer.printErr("Cannot find following file with commands: " + script + ". Reading from stdin.");
                bufferedReader = new BufferedReader(new InputStreamReader(System.in));
                script = null;
            }
        } else {
            bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        }

        String command;
        currentWord = null;
        while(true) {
            if(script == null) {
                System.out.print("indexer_$> ");
            }

            command = bufferedReader.readLine();
            if(command != null && command.equals("!") || command == null) {
                break;
            }
            interpretCommand(command);
        }

    }

    /**
     * Interprets a command.
     * @param command command to interpret
     */
    private void interpretCommand(String command) throws IOException {
        if( !isCommandCorrect(command) ) {
            Printer.printErr("Bad command. Type right command and press enter.");
            return;
        }

        CommandTokenizer cmdtok = new CommandTokenizer();
        String[] tokenizedCommand = cmdtok.split(command);
        String cmd = tokenizedCommand[0];

        if(cmd == null) {
            Printer.printErr("Bad command. Type right command and press enter.");
            return;
        }

        boolean isCommandParametrized = tokenizedCommand[1] != null && tokenizedCommand[2] != null;
        boolean isCurrentWord = (currentWord != null);

        if(cmd.equals(">") && isCurrentWord) {
            int[] results;

            if(isCommandParametrized) {

                int documentNumber = Integer.parseInt(tokenizedCommand[1]);
                int wordNumber = Integer.parseInt(tokenizedCommand[2]);
                results = searcher.findNextOccurrence(documentNumber, wordNumber);
            } else {
                results = searcher.findNextOccurrence();
            }

            printer.printResults(currentWord, results);
        }
        else if(cmd.equals("<") && isCurrentWord) {
            int[] results;

            if(isCommandParametrized) {
                int documentNumber = Integer.parseInt(tokenizedCommand[1]);
                int wordNumber = Integer.parseInt(tokenizedCommand[2]);
                results = searcher.findPreviousOccurrence(documentNumber, wordNumber);
            } else {
                results = searcher.findPreviousOccurrence();
            }

            printer.printResults(currentWord, results);
        }
        else if(cmd.equals("@")) {
            Indexer.printIndex();
        }
        else if((cmd.equals(">") || cmd.equals("<")) && !isCurrentWord) {
            Printer.printErr("You must enter a word to search first.");
        }
        else {
            currentWord = cmd;
            int[] results;

            if(isCommandParametrized) {
                searcher.findFirstOccurrence(currentWord);
                int documentNumber = Integer.parseInt(tokenizedCommand[1]);
                int wordNumber = Integer.parseInt(tokenizedCommand[2]);
                results = searcher.findNextOccurrence(documentNumber, wordNumber);
            } else {
                results = searcher.findFirstOccurrence(currentWord);
            }
            printer.printResults(currentWord, results);
        }

    }



    private boolean isCommandCorrect(String command) {

        if(command.equals("")) {
            return false;
        }

        int words = 1;

        char[] cmd = command.toCharArray();

        for(int i = 0; i < cmd.length; i++) {
            if(words > 3) {
                return false;
            }

            if(cmd[i] == ' ' || cmd[i] == ':') {
                words++;
            }
        }

        return (words == 1 || words == 3);
    }

    public Printer getPrinter()  {
        return printer;
    }

    public void setScript(String script) {
        this.script = script;
    }
}
