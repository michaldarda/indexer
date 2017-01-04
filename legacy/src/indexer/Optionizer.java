package indexer;

/**
 * Handle program options.
 * @author <b>dardam</b>, michaldarda1@gmail.com <br><b>starczyr</b>,
 * radek.starczynowski@gmail.com
 */
public class Optionizer {

    private CommandLine cmdline;

    public Optionizer(CommandLine cmdline) {
        this.cmdline = cmdline;
    }

    /**
     * Handling program options
     *
     * @param args program arguments
     */
    public void handleOptions(String[] args) {

        for(int i = 0; i < args.length; i++) {

            if(args[i].equalsIgnoreCase("-h"))  {
                Printer.usage();
                System.exit(0);
            } else if(args[i].equalsIgnoreCase("-o") && i+1 < args.length && args[i+1] != null) {
                Printer printer = cmdline.getPrinter();
                String printSource = args[i+1];
                printer.setPrintSource(printSource);
            } else if(args[i].equalsIgnoreCase("-b") && i+1 < args.length && args[i+1] != null) {
                cmdline.setScript(args[i+1]);
            } else if(args[i].startsWith("-")) {
                Printer.printErr("Unknown option: " + args[i]);
            }

        }
    }
    
}
