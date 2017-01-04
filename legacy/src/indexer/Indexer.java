package indexer;

import hash.Hash;
import occurrences.OccurrencesList;

/**
 * Building the index
 *
 * @author <b>dardam</b>, michaldarda1@gmail.com
 * <br><b>starczyr</b>, radek.starczynowski@gmail.com
 */
public class Indexer {

    private static Hash hash = new Hash();

    private static int lastAddedPosition = 1;
    private static int lastAddedDocument = 1;
    /**
     * Add a portion of tokens to the index with necessary offset
     * 
     * @param tokensBuffer portion of tokens to add
     * @param whichDocument from which document tokens are 
     */
    public static void addToIndex(String[] tokensBuffer, int whichDocument) {
        if(whichDocument != lastAddedDocument) {
            lastAddedDocument = whichDocument;
            lastAddedPosition = 1;
        }

        for(int i = 0; i < tokensBuffer.length; i++) {

            if(tokensBuffer[i] != null) {
                if(tokensBuffer[i] != null)  {
                    OccurrencesList occurs = hash.get(tokensBuffer[i]);

                    if(occurs == null) {
                        occurs = new OccurrencesList(lastAddedDocument, lastAddedPosition);
                        hash.put(tokensBuffer[i], occurs);
                    } else {
                        occurs.addOccurrence(lastAddedDocument, lastAddedPosition);
                    }

                    lastAddedPosition++;
                }
            }
        }
    }
    
    public static void printIndex() {
        hash.printHash();
    }
    
    public static Hash getHash() {
        return hash;
    }
}
