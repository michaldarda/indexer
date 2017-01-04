package tokenizer;

/**
 * Splits a string into array of tokens (strings)
 *
 * @author <b>dardam</b>, michaldarda1@gmail.com
 * <br><b>starczyr</b>, radek.starczynowski@gmail.com
 */
public class Tokenizer {

    private int maxTokensInLine;
    private String[] tokens;

    public Tokenizer() {
        maxTokensInLine = 20;
        
    }
    
    /**
     * <pre>
     * <b>Splits line of text into an array of strings</b><br><br>
     * Line:
     *      "<b>Hey! How are you? Why don't we meet sometimes?<b/>"
     * <br>
     * will be split into:
     *      <b>Hey" "How" "are" "you" "Why" "don" "t" "we" "meet" "sometimes"</b>
     * </pre>
     *
     * @param lineFromDocument line of text
     * @return an array of tokens
     */
    public String[] split(String lineFromDocument) {

        boolean inWord = false;

        int wordLetter = 0;
        int whichWord = 0;

        int maxWordLength = 100;
                
        char[] lineBuffer = lineFromDocument.toCharArray();
        char[] wordBuffer = new char[maxWordLength];

        tokens = new String[maxTokensInLine];

        char currentChar;

        for(int i = 0; i < lineBuffer.length; i++) {
            currentChar = lineBuffer[i];
            
            boolean letter = false;
            
            if(isLetter(currentChar)) {
                letter = true;
            }
            if(wordLetter == maxWordLength - 1) {
                wordBuffer = resizeWordBuffer(wordBuffer);
            }

            // ...xxXxx... letter somewhere in the string
            if(letter && inWord && (i < (lineBuffer.length - 1))) {
                wordBuffer[wordLetter++] = currentChar;

                // xxX| last letter
            } else if(letter && (i == lineBuffer.length - 1)) {
                wordBuffer[wordLetter] = currentChar;                
                tokens[whichWord] = new String(wordBuffer).trim();
                
                // ...Xxxx... letter at the beggining of the string
            } else if(letter && !inWord) {
                inWord = true;
                wordBuffer[wordLetter++] = currentChar;
                
                // xxx_.. first non-letter char after string
            } else if(!letter && inWord) {
                tokens[whichWord++] = new String(wordBuffer).trim();
                wordBuffer = new char[maxWordLength];
                wordLetter = 0;
                inWord = false;

            }
            if(whichWord == maxTokensInLine) {
                resizeTokensBuffer();
            }
        }
        return tokens;
    }
    

    protected boolean isLetter(char c) {
        // 'A' = 65, 'Z' = 90, 'a' = 97, 'z' = 122
        return !(c < 65 || c > 90 && c < 97 || c > 122);
    }

    protected void resizeTokensBuffer() {
        maxTokensInLine *= 2;

        String[] temp = new String[maxTokensInLine];
        System.arraycopy(tokens, 0, temp, 0, tokens.length);
        tokens = temp;
    }

    protected char[] resizeWordBuffer(char[] wordBuffer) {
        char[] temp = new char[wordBuffer.length * 2];
        System.arraycopy(wordBuffer, 0, temp, 0, wordBuffer.length);
        return temp;
    }
}
