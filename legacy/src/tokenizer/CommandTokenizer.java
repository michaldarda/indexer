package tokenizer;


public class CommandTokenizer extends Tokenizer {
    @Override
    /*
    This method is used while breaking command into tokens, and tells us which characters should be treat as separators
    We are using an ASCII chars table to do it
     */

    protected boolean isLetter(char c) {
        if((c > 63 && c < 91) || (c > 96 && c < 123) || c == 62 || c == 60 || (c > 47 && c < 58) || c == 33) {
            return true;
        }
        return false;
    }

}